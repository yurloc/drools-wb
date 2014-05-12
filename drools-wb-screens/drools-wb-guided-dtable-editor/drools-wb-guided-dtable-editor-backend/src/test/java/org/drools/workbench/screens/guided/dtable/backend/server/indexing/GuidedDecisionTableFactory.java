/*
 * Copyright 2010 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.drools.workbench.screens.guided.dtable.backend.server.indexing;

import java.util.Collection;

import org.drools.workbench.models.datamodel.imports.Import;
import org.drools.workbench.models.datamodel.oracle.DataType;
import org.drools.workbench.models.datamodel.rule.BaseSingleFieldConstraint;
import org.drools.workbench.models.guided.dtable.backend.util.DataUtilities;
import org.drools.workbench.models.guided.dtable.shared.model.ActionInsertFactCol52;
import org.drools.workbench.models.guided.dtable.shared.model.AttributeCol52;
import org.drools.workbench.models.guided.dtable.shared.model.ConditionCol52;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;
import org.drools.workbench.models.guided.dtable.shared.model.Pattern52;

public class GuidedDecisionTableFactory {

    public static GuidedDecisionTable52 makeTableWithAttributeCol( final String packageName,
                                                                   final Collection<Import> imports,
                                                                   final String tableName ) {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        dt.setPackageName( packageName );
        dt.getImports().getImports().addAll( imports );
        dt.setTableName( tableName );

        AttributeCol52 attr = new AttributeCol52();
        attr.setAttribute( "ruleflow-group" );
        dt.getAttributeCols().add( attr );

        dt.setData( DataUtilities.makeDataLists( new String[][]{
                new String[]{ "1", "desc", "myRuleFlowGroup" }
        } ) );

        return dt;
    }

    public static GuidedDecisionTable52 makeTableWithConditionCol( final String packageName,
                                                                   final Collection<Import> imports,
                                                                   final String tableName ) {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        dt.setPackageName( packageName );
        dt.getImports().getImports().addAll( imports );
        dt.setTableName( tableName );

        Pattern52 p1 = new Pattern52();
        p1.setBoundName( "$a" );
        p1.setFactType( "Applicant" );

        ConditionCol52 con = new ConditionCol52();
        con.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        con.setFactField( "age" );
        con.setHeader( "Applicant age" );
        con.setOperator( "==" );
        p1.getChildColumns().add( con );

        dt.getConditions().add( p1 );

        dt.setData( DataUtilities.makeDataLists( new String[][]{
                new String[]{ "1", "desc", "33" }
        } ) );

        return dt;
    }

    public static GuidedDecisionTable52 makeTableWithActionCol( final String packageName,
                                                                final Collection<Import> imports,
                                                                final String tableName ) {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        dt.setPackageName( packageName );
        dt.getImports().getImports().addAll( imports );
        dt.setTableName( tableName );

        ActionInsertFactCol52 ins = new ActionInsertFactCol52();
        ins.setBoundName( "$i" );
        ins.setFactType( "Applicant" );
        ins.setFactField( "age" );
        ins.setType( DataType.TYPE_NUMERIC_INTEGER );
        dt.getActionCols().add( ins );

        dt.setData( DataUtilities.makeDataLists( new String[][]{
                new String[]{ "1", "desc", "33" }
        } ) );

        return dt;
    }

}
