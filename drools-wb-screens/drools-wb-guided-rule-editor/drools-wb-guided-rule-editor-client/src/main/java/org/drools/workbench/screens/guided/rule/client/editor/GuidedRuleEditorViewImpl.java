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

import java.util.Collection;

import javax.inject.Inject;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.SimplePanel;
import org.drools.workbench.models.datamodel.rule.RuleModel;
import org.drools.workbench.screens.guided.rule.client.editor.plugin.RuleModellerActionPlugin;
import org.drools.workbench.screens.guided.rule.client.editor.validator.PatternBindingValidator;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.kie.workbench.common.services.shared.rulename.RuleNamesService;
import org.kie.workbench.common.widgets.client.datamodel.AsyncPackageDataModelOracle;
import org.kie.workbench.common.widgets.metadata.client.KieEditorViewImpl;

public class GuidedRuleEditorViewImpl
        extends KieEditorViewImpl
        implements GuidedRuleEditorView {

    private final EventBus localBus = new SimpleEventBus();
    private final SimplePanel panel = new SimplePanel();
    private RuleModeller modeller = null;

    @Inject
    public GuidedRuleEditorViewImpl() {

        panel.setWidth("100%");
        initWidget(panel);
    }

    @Override
    public void setContent(final RuleModel model,
                           final Collection<RuleModellerActionPlugin> actionPlugins,
                           final Collection<PatternBindingValidator> patternBindingValidators,
                           final AsyncPackageDataModelOracle oracle,
                           final Caller<RuleNamesService> ruleNamesService,
                           final boolean isReadOnly,
                           final boolean isDSLEnabled) {
        this.modeller = new RuleModeller(model,
                                         actionPlugins,
                                         patternBindingValidators,
                                         oracle,
                                         new RuleModellerWidgetFactory(actionPlugins),
                                         localBus,
                                         isReadOnly,
                                         isDSLEnabled);
        panel.setWidget(modeller);

        ruleNamesService.call(new RemoteCallback<Collection<String>>() {
            @Override
            public void callback(Collection<String> ruleNames) {
                modeller.setRuleNamesForPackage(ruleNames);
            }
        }).getRuleNames(oracle.getResourcePath(),
                        model.getPackageName());
    }

    @Override
    public RuleModel getContent() {
        //RuleModeller could be null if the Rule failed to load (e.g. the file was not found in VFS)
        if (modeller == null) {
            return null;
        }
        return modeller.getModel();
    }

    @Override
    public void refresh() {
        modeller.refreshWidget();
    }
}
