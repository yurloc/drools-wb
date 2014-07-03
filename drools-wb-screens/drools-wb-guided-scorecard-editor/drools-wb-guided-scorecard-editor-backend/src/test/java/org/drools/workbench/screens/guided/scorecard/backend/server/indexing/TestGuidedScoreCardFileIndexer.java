/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.workbench.screens.guided.scorecard.backend.server.indexing;

import java.util.HashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;

import org.drools.workbench.models.commons.backend.oracle.ProjectDataModelOracleImpl;
import org.drools.workbench.models.datamodel.oracle.DataType;
import org.drools.workbench.models.datamodel.oracle.FieldAccessorsAndMutators;
import org.drools.workbench.models.datamodel.oracle.ModelField;
import org.drools.workbench.models.datamodel.oracle.ProjectDataModelOracle;
import org.drools.workbench.screens.guided.scorecard.type.GuidedScoreCardResourceTypeDefinition;
import org.guvnor.common.services.project.service.ProjectService;
import org.kie.workbench.common.services.refactoring.backend.server.TestIndexer;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.file.Path;

/**
 * Test indexer
 */
@ApplicationScoped
public class TestGuidedScoreCardFileIndexer extends GuidedScoreCardFileIndexer implements TestIndexer<GuidedScoreCardResourceTypeDefinition> {

    @Override
    public void setIOServiceProvider( final Instance<IOService> ioServiceProvider ) {
        this.ioServiceProvider = ioServiceProvider;
    }

    @Override
    public void setProjectServiceProvider( final Instance<ProjectService> projectServiceProvider ) {
        this.projectServiceProvider = projectServiceProvider;
    }

    @Override
    public void setResourceTypeDefinition( final GuidedScoreCardResourceTypeDefinition type ) {
        this.type = type;
    }

    @Override
    protected ProjectDataModelOracle getProjectDataModelOracle( final Path path ) {
        final ProjectDataModelOracle dmo = new ProjectDataModelOracleImpl();
        dmo.addProjectModelFields( new HashMap<String, ModelField[]>() {{
            put( "org.drools.workbench.screens.guided.scorecard.backend.server.indexing.classes.Applicant",
                 new ModelField[]{ new ModelField( "age",
                                                   "java.lang.Integer",
                                                   ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                                   ModelField.FIELD_ORIGIN.DECLARED,
                                                   FieldAccessorsAndMutators.ACCESSOR,
                                                   DataType.TYPE_NUMERIC_INTEGER ) } );
            put( "org.drools.workbench.screens.guided.scorecard.backend.server.indexing.classes.Mortgage",
                 new ModelField[]{ new ModelField( "amount",
                                                   "java.lang.Integer",
                                                   ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                                   ModelField.FIELD_ORIGIN.DECLARED,
                                                   FieldAccessorsAndMutators.ACCESSOR,
                                                   DataType.TYPE_NUMERIC_INTEGER ) } );
        }} );
        return dmo;
    }

}