/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.guided.rule.client.editor;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.drools.workbench.models.datamodel.rule.RuleModel;
import org.drools.workbench.screens.guided.rule.client.editor.plugin.RuleModellerActionPlugin;
import org.drools.workbench.screens.guided.rule.client.editor.validator.GuidedRuleEditorValidator;
import org.drools.workbench.screens.guided.rule.client.editor.validator.PatternBindingValidator;
import org.drools.workbench.screens.guided.rule.client.resources.GuidedRuleEditorResources;
import org.drools.workbench.screens.guided.rule.client.type.GuidedRuleDRLResourceType;
import org.drools.workbench.screens.guided.rule.client.type.GuidedRuleDSLRResourceType;
import org.drools.workbench.screens.guided.rule.model.GuidedEditorContent;
import org.drools.workbench.screens.guided.rule.service.GuidedRuleEditorService;
import org.guvnor.common.services.shared.validation.model.ValidationMessage;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.ManagedInstance;
import org.kie.workbench.common.services.datamodel.model.PackageDataModelOracleBaselinePayload;
import org.kie.workbench.common.services.shared.rulename.RuleNamesService;
import org.kie.workbench.common.widgets.client.datamodel.AsyncPackageDataModelOracle;
import org.kie.workbench.common.widgets.client.datamodel.AsyncPackageDataModelOracleFactory;
import org.kie.workbench.common.widgets.client.datamodel.ImportAddedEvent;
import org.kie.workbench.common.widgets.client.datamodel.ImportRemovedEvent;
import org.kie.workbench.common.widgets.client.popups.validation.ValidationPopup;
import org.kie.workbench.common.widgets.client.resources.i18n.CommonConstants;
import org.kie.workbench.common.widgets.client.source.ViewSourceView;
import org.kie.workbench.common.widgets.configresource.client.widget.bound.ImportsWidgetPresenter;
import org.kie.workbench.common.widgets.metadata.client.KieEditor;
import org.uberfire.backend.vfs.ObservablePath;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.annotations.WorkbenchEditor;
import org.uberfire.client.annotations.WorkbenchMenu;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartTitleDecoration;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.workbench.type.ClientResourceType;
import org.uberfire.ext.widgets.common.client.callbacks.HasBusyIndicatorDefaultErrorCallback;
import org.uberfire.ext.widgets.common.client.common.popups.errors.ErrorPopup;
import org.uberfire.lifecycle.OnClose;
import org.uberfire.lifecycle.OnMayClose;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.Command;
import org.uberfire.mvp.ParameterizedCommand;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.workbench.events.NotificationEvent;
import org.uberfire.workbench.model.menu.Menus;

@Dependent
@WorkbenchEditor(identifier = "GuidedRuleEditor", supportedTypes = {GuidedRuleDRLResourceType.class, GuidedRuleDSLRResourceType.class}, priority = 102)
public class GuidedRuleEditorPresenter
        extends KieEditor {

    @Inject
    private ImportsWidgetPresenter importsWidget;

    private GuidedRuleEditorView view;

    @Inject
    private ViewSourceView viewSource;

    @Inject
    private Caller<GuidedRuleEditorService> service;

    @Inject
    private Caller<RuleNamesService> ruleNamesService;

    @Inject
    private GuidedRuleDRLResourceType resourceTypeDRL;

    @Inject
    private GuidedRuleDSLRResourceType resourceTypeDSL;

    @Inject
    private AsyncPackageDataModelOracleFactory oracleFactory;

    @Inject
    private ValidationPopup validationPopup;

    @Inject
    private ManagedInstance<RuleModellerActionPlugin> actionPluginInstance;

    @Inject
    private ManagedInstance<PatternBindingValidator> patternBindingValidatorInstance;

    private boolean isDSLEnabled;

    private RuleModel model;
    private AsyncPackageDataModelOracle oracle;

    @Inject
    public GuidedRuleEditorPresenter(final GuidedRuleEditorView view) {
        super(view);
        this.view = view;
    }

    @OnStartup
    public void onStartup(final ObservablePath path,
                          final PlaceRequest place) {

        super.init(path,
                   place,
                   getResourceType(path));
        this.isDSLEnabled = resourceTypeDSL.accept(path);
    }

    protected void loadContent() {
        view.showLoading();
        getService().call(getModelSuccessCallback(),
                          getNoSuchFileExceptionErrorCallback()).loadContent(getVersionRecordManager().getCurrentPath());
    }

    @Override
    public void onSourceTabSelected() {
        getService().call(new RemoteCallback<String>() {
            @Override
            public void callback(String source) {
                updateSource(source);
            }
        }).toSource(versionRecordManager.getCurrentPath(),
                    model);
    }

    private RemoteCallback<GuidedEditorContent> getModelSuccessCallback() {
        return new RemoteCallback<GuidedEditorContent>() {

            @Override
            public void callback(final GuidedEditorContent content) {
                //Path is set to null when the Editor is closed (which can happen before async calls complete).
                if (versionRecordManager.getCurrentPath() == null) {
                    return;
                }

                GuidedRuleEditorPresenter.this.model = content.getModel();
                final PackageDataModelOracleBaselinePayload dataModel = content.getDataModel();
                oracle = oracleFactory.makeAsyncPackageDataModelOracle(versionRecordManager.getCurrentPath(),
                                                                       model,
                                                                       dataModel);

                resetEditorPages(content.getOverview());

                addSourcePage();

                addImportsTab(importsWidget);

                List<RuleModellerActionPlugin> actionPlugins = new ArrayList<>();
                actionPluginInstance.forEach(actionPlugins::add);

                List<PatternBindingValidator> patternBindingValidators = new ArrayList<>();
                patternBindingValidatorInstance.forEach(patternBindingValidators::add);

                view.setContent(model,
                                actionPlugins,
                                patternBindingValidators,
                                oracle,
                                ruleNamesService,
                                isReadOnly,
                                isDSLEnabled);
                importsWidget.setContent(oracle,
                                         model.getImports(),
                                         isReadOnly);

                view.hideBusyIndicator();

                createOriginalHash(model);
            }
        };
    }

    public void handleImportAddedEvent(@Observes ImportAddedEvent event) {
        if (!event.getDataModelOracle().equals(this.oracle)) {
            return;
        }
        view.refresh();
    }

    public void handleImportRemovedEvent(@Observes ImportRemovedEvent event) {
        if (!event.getDataModelOracle().equals(this.oracle)) {
            return;
        }
        view.refresh();
    }

    protected Command onValidate() {
        return new Command() {
            @Override
            public void execute() {
                getService().call(new RemoteCallback<List<ValidationMessage>>() {
                    @Override
                    public void callback(final List<ValidationMessage> results) {
                        if (results == null || results.isEmpty()) {
                            notification.fire(new NotificationEvent(CommonConstants.INSTANCE.ItemValidatedSuccessfully(),
                                                                    NotificationEvent.NotificationType.SUCCESS));
                        } else {
                            validationPopup.showMessages(results);
                        }
                    }
                }).validate(versionRecordManager.getCurrentPath(),
                            view.getContent());
            }
        };
    }

    protected void save() {
        GuidedRuleEditorValidator validator = new GuidedRuleEditorValidator(model,
                                                                            GuidedRuleEditorResources.CONSTANTS);

        if (validator.isValid()) {
            savePopUpPresenter.show(versionRecordManager.getPathToLatest(),
                                    new ParameterizedCommand<String>() {
                                        @Override
                                        public void execute(final String commitMessage) {
                                            view.showSaving();
                                            save(commitMessage);
                                        }
                                    });

            concurrentUpdateSessionInfo = null;
        } else {
            ErrorPopup.showMessage(validator.getErrors().get(0));
        }
    }

    @Override
    protected void save(String commitMessage) {
        getService().call(getSaveSuccessCallback(model.hashCode()),
                          new HasBusyIndicatorDefaultErrorCallback(view)).save(versionRecordManager.getCurrentPath(),
                                                                               view.getContent(),
                                                                               metadata, commitMessage);
    }

    @OnClose
    public void onClose() {
        this.versionRecordManager.clear();
        this.oracleFactory.destroy(oracle);
    }

    @OnMayClose
    public boolean mayClose() {
        return super.mayClose(view.getContent());
    }

    @WorkbenchPartTitle
    public String getTitleText() {
        return super.getTitleText();
    }

    @WorkbenchPartTitleDecoration
    public IsWidget getTitle() {
        return super.getTitle();
    }

    private ClientResourceType getResourceType(final Path path) {
        if (resourceTypeDRL.accept(path)) {
            return resourceTypeDRL;
        } else {
            return resourceTypeDRL;
        }
    }

    @WorkbenchPartView
    public IsWidget getWidget() {
        return super.getWidget();
    }

    @WorkbenchMenu
    public Menus getMenus() {
        return menus;
    }

    /*
     * Getter due to test purposes
     */
    Caller<GuidedRuleEditorService> getService() {
        return service;
    }
}
