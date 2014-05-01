/*
 * Copyright 2014 JBoss, by Red Hat, Inc
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
package org.drools.workbench.screens.drltext.backend.server.indexing;

import java.util.List;

import org.drools.compiler.lang.descr.AccessorDescr;
import org.drools.compiler.lang.descr.AccumulateDescr;
import org.drools.compiler.lang.descr.ActionDescr;
import org.drools.compiler.lang.descr.AndDescr;
import org.drools.compiler.lang.descr.AnnotationDescr;
import org.drools.compiler.lang.descr.AtomicExprDescr;
import org.drools.compiler.lang.descr.AttributeDescr;
import org.drools.compiler.lang.descr.BaseDescr;
import org.drools.compiler.lang.descr.BindingDescr;
import org.drools.compiler.lang.descr.CollectDescr;
import org.drools.compiler.lang.descr.ConstraintConnectiveDescr;
import org.drools.compiler.lang.descr.DeclarativeInvokerDescr;
import org.drools.compiler.lang.descr.ExistsDescr;
import org.drools.compiler.lang.descr.FactTemplateDescr;
import org.drools.compiler.lang.descr.FieldAccessDescr;
import org.drools.compiler.lang.descr.FieldConstraintDescr;
import org.drools.compiler.lang.descr.FieldTemplateDescr;
import org.drools.compiler.lang.descr.ForallDescr;
import org.drools.compiler.lang.descr.FromDescr;
import org.drools.compiler.lang.descr.FunctionDescr;
import org.drools.compiler.lang.descr.FunctionImportDescr;
import org.drools.compiler.lang.descr.GlobalDescr;
import org.drools.compiler.lang.descr.ImportDescr;
import org.drools.compiler.lang.descr.LiteralRestrictionDescr;
import org.drools.compiler.lang.descr.MVELExprDescr;
import org.drools.compiler.lang.descr.MethodAccessDescr;
import org.drools.compiler.lang.descr.NotDescr;
import org.drools.compiler.lang.descr.OrDescr;
import org.drools.compiler.lang.descr.PackageDescr;
import org.drools.compiler.lang.descr.PatternDescr;
import org.drools.compiler.lang.descr.PredicateDescr;
import org.drools.compiler.lang.descr.QueryDescr;
import org.drools.compiler.lang.descr.RelationalExprDescr;
import org.drools.compiler.lang.descr.RuleDescr;
import org.drools.compiler.lang.descr.StringConnectiveDescr;
import org.drools.compiler.lang.descr.TypeDeclarationDescr;
import org.drools.compiler.lang.descr.TypeFieldDescr;
import org.drools.compiler.lang.descr.WindowDeclarationDescr;
import org.kie.workbench.common.services.refactoring.backend.server.indexing.DefaultIndexBuilder;
import org.kie.workbench.common.services.refactoring.model.index.Rule;
import org.kie.workbench.common.services.refactoring.model.index.RuleAttribute;
import org.kie.workbench.common.services.refactoring.model.index.Type;
import org.uberfire.commons.data.Pair;
import org.uberfire.commons.validation.PortablePreconditions;

public class PackageDescrVisitor {

    private final DefaultIndexBuilder builder;
    private final PackageDescr packageDescr;

    public PackageDescrVisitor( final DefaultIndexBuilder builder,
                                final PackageDescr packageDescr ) {
        this.builder = PortablePreconditions.checkNotNull( "builder",
                                                           builder );
        this.packageDescr = PortablePreconditions.checkNotNull( "packageDescr",
                                                                packageDescr );
    }

    public List<Pair<String, String>> visit() {
        visit( packageDescr );
        return builder.build();
    }

    private void visit( final Object descr ) {
        if ( descr instanceof AccessorDescr ) {
            visit( (AccessorDescr) descr );
        } else if ( descr instanceof AccumulateDescr ) {
            visit( (AccumulateDescr) descr );
        } else if ( descr instanceof ActionDescr ) {
            visit( (ActionDescr) descr );
        } else if ( descr instanceof AndDescr ) {
            visit( (AndDescr) descr );
        } else if ( descr instanceof AnnotationDescr ) {
            visit( (AnnotationDescr) descr );
        } else if ( descr instanceof AtomicExprDescr ) {
            visit( (AtomicExprDescr) descr );
        } else if ( descr instanceof AttributeDescr ) {
            visit( (AttributeDescr) descr );
        } else if ( descr instanceof BindingDescr ) {
            visit( (BindingDescr) descr );
        } else if ( descr instanceof CollectDescr ) {
            visit( (CollectDescr) descr );
        } else if ( descr instanceof ConstraintConnectiveDescr ) {
            visit( (ConstraintConnectiveDescr) descr );
        } else if ( descr instanceof ExistsDescr ) {
            visit( (ExistsDescr) descr );
        } else if ( descr instanceof FactTemplateDescr ) {
            visit( (FactTemplateDescr) descr );
        } else if ( descr instanceof FieldAccessDescr ) {
            visit( (FieldAccessDescr) descr );
        } else if ( descr instanceof FieldConstraintDescr ) {
            visit( (FieldConstraintDescr) descr );
        } else if ( descr instanceof FieldTemplateDescr ) {
            visit( (FieldTemplateDescr) descr );
        } else if ( descr instanceof ForallDescr ) {
            visit( (ForallDescr) descr );
        } else if ( descr instanceof FromDescr ) {
            visit( (FromDescr) descr );
        } else if ( descr instanceof FunctionDescr ) {
            visit( (FunctionDescr) descr );
        } else if ( descr instanceof FunctionImportDescr ) {
            visit( (FunctionImportDescr) descr );
        } else if ( descr instanceof GlobalDescr ) {
            visit( (GlobalDescr) descr );
        } else if ( descr instanceof ImportDescr ) {
            visit( (ImportDescr) descr );
        } else if ( descr instanceof LiteralRestrictionDescr ) {
            visit( (LiteralRestrictionDescr) descr );
        } else if ( descr instanceof MethodAccessDescr ) {
            visit( (MethodAccessDescr) descr );
        } else if ( descr instanceof MVELExprDescr ) {
            visit( (MVELExprDescr) descr );
        } else if ( descr instanceof NotDescr ) {
            visit( (NotDescr) descr );
        } else if ( descr instanceof OrDescr ) {
            visit( (OrDescr) descr );
        } else if ( descr instanceof PackageDescr ) {
            visit( (PackageDescr) descr );
        } else if ( descr instanceof PatternDescr ) {
            visit( (PatternDescr) descr );
        } else if ( descr instanceof PredicateDescr ) {
            visit( (PredicateDescr) descr );
        } else if ( descr instanceof QueryDescr ) {
            visit( (QueryDescr) descr );
        } else if ( descr instanceof RelationalExprDescr ) {
            visit( (RelationalExprDescr) descr );
        } else if ( descr instanceof RuleDescr ) {
            visit( (RuleDescr) descr );
        } else if ( descr instanceof StringConnectiveDescr ) {
            visit( (StringConnectiveDescr) descr );
        } else if ( descr instanceof TypeDeclarationDescr ) {
            visit( (TypeDeclarationDescr) descr );
        } else if ( descr instanceof TypeFieldDescr ) {
            visit( (TypeFieldDescr) descr );
        } else if ( descr instanceof WindowDeclarationDescr ) {
            visit( (WindowDeclarationDescr) descr );
        }
    }

    protected void visit( final AccessorDescr descr ) {
        for ( DeclarativeInvokerDescr d : descr.getInvokersAsArray() ) {
            visit( d );
        }
    }

    protected void visit( final AccumulateDescr descr ) {
        visit( descr.getInputPattern() );
        for ( BaseDescr d : descr.getDescrs() ) {
            visit( d );
        }
    }

    protected void visit( final ActionDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final AndDescr descr ) {
        for ( BaseDescr baseDescr : descr.getDescrs() ) {
            visit( baseDescr );
        }
    }

    protected void visit( final AnnotationDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final AtomicExprDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final AttributeDescr descr ) {
        builder.addRuleAttribute( new RuleAttribute( descr.getName(),
                                                     descr.getValue() ) );
    }

    protected void visit( final BindingDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final CollectDescr descr ) {
        visit( descr.getInputPattern() );
        for ( BaseDescr d : descr.getDescrs() ) {
            visit( d );
        }
    }

    protected void visit( final ConstraintConnectiveDescr descr ) {
        for ( BaseDescr d : descr.getDescrs() ) {
            visit( d );
        }
    }

    protected void visit( final ExistsDescr descr ) {
        //ExistsDescr isn't type-safe
        for ( Object o : descr.getDescrs() ) {
            visit( o );
        }
    }

    protected void visit( final FactTemplateDescr descr ) {
        for ( FieldTemplateDescr d : descr.getFields() ) {
            visit( d );
        }
    }

    protected void visit( final FieldAccessDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final FieldConstraintDescr descr ) {
        //FieldConstraintDescr isn't type-safe
        for ( Object o : descr.getRestrictions() ) {
            visit( o );
        }
    }

    protected void visit( final FieldTemplateDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final ForallDescr descr ) {
        visit( descr.getBasePattern() );
        for ( BaseDescr o : descr.getDescrs() ) {
            visit( o );
        }
    }

    protected void visit( final FromDescr descr ) {
        for ( BaseDescr d : descr.getDescrs() ) {
            visit( d );
        }
    }

    protected void visit( final FunctionDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final FunctionImportDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final GlobalDescr descr ) {
        builder.addType( new Type( getFullyQualifiedClassName( packageDescr,
                                                               descr.getType() ) ) );
    }

    protected void visit( final ImportDescr descr ) {
        builder.addType( new Type( descr.getTarget() ) );
    }

    protected void visit( final LiteralRestrictionDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final MethodAccessDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final MVELExprDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final NotDescr descr ) {
        //NotDescr isn't type-safe
        for ( Object o : descr.getDescrs() ) {
            visit( o );
        }
    }

    protected void visit( final OrDescr descr ) {
        for ( BaseDescr d : descr.getDescrs() ) {
            visit( d );
        }
    }

    protected void visit( final PackageDescr descr ) {
        for ( RuleDescr ruleDescr : descr.getRules() ) {
            visit( ruleDescr );
        }
    }

    protected void visit( final PatternDescr descr ) {
        builder.addType( new Type( getFullyQualifiedClassName( packageDescr,
                                                               descr.getObjectType() ) ) );
        visit( descr.getConstraint() );
    }

    protected void visit( final PredicateDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final QueryDescr descr ) {
        visit( descr.getLhs() );
    }

    protected void visit( final RelationalExprDescr descr ) {
        visit( descr.getLeft() );
        visit( descr.getRight() );
    }

    protected void visit( final RuleDescr descr ) {
        builder.addRule( new Rule( descr.getName(),
                                   descr.getParentName() ) );
        for ( AttributeDescr d : descr.getAttributes().values() ) {
            visit( d );
        }
        visit( descr.getLhs() );
    }

    protected void visit( final StringConnectiveDescr descr ) {
        for ( Object o : descr.getRestrictions() ) {
            visit( o );
        }
    }

    protected void visit( final TypeDeclarationDescr descr ) {
        builder.addType( new Type( getFullyQualifiedClassName( packageDescr,
                                                               descr.getTypeName() ) ) );
        if ( !( descr.getSuperTypeName() == null || descr.getSuperTypeName().isEmpty() ) ) {
            builder.addType( new Type( getFullyQualifiedClassName( packageDescr,
                                                                   descr.getSuperTypeName() ) ) );
        }
    }

    protected void visit( final TypeFieldDescr descr ) {
        //TODO - Not yet implemented
        System.out.println( descr + " : " + descr.getClass().getName() );
    }

    protected void visit( final WindowDeclarationDescr descr ) {
        visit( descr.getPattern() );
    }

    private String getFullyQualifiedClassName( final PackageDescr packageDescr,
                                               final String typeName ) {
        for ( ImportDescr importDescr : packageDescr.getImports() ) {
            if ( importDescr.getTarget().endsWith( typeName ) ) {
                return importDescr.getTarget();
            }
        }
        return packageDescr.getName() + "." + typeName;
    }

}
