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
package org.drools.workbench.screens.dsltext.backend.server.indexing;

import java.io.IOException;
import java.io.StringReader;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.drools.compiler.lang.dsl.DSLMappingEntry;
import org.drools.compiler.lang.dsl.DSLTokenizedMappingFile;
import org.drools.workbench.screens.dsltext.type.DSLResourceTypeDefinition;
import org.kie.workbench.common.services.refactoring.backend.server.indexing.DefaultIndexBuilder;
import org.kie.workbench.common.services.refactoring.backend.server.util.KObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uberfire.backend.server.util.Paths;
import org.uberfire.io.IOService;
import org.uberfire.java.nio.file.Path;
import org.uberfire.metadata.engine.Indexer;
import org.uberfire.metadata.model.KObject;
import org.uberfire.metadata.model.KObjectKey;

@ApplicationScoped
public class DslFileIndexer implements Indexer {

    private static final Logger logger = LoggerFactory.getLogger( DslFileIndexer.class );

    @Inject
    @Named("ioStrategy")
    protected IOService ioService;

    @Inject
    protected DSLResourceTypeDefinition dslType;

    @Override
    public boolean supportsPath( final Path path ) {
        return dslType.accept( Paths.convert( path ) );
    }

    @Override
    public KObject toKObject( final Path path ) {
        final String dsl = ioService.readAllString( path );
        final DefaultIndexBuilder builder = new DefaultIndexBuilder( path );
        final DSLTokenizedMappingFile dslLoader = new DSLTokenizedMappingFile();
        try {
            if ( dslLoader.parseAndLoad( new StringReader( dsl ) ) ) {
                for ( DSLMappingEntry e : dslLoader.getMapping().getEntries() ) {
                    //TODO Index DSLs!
                    System.out.println( e );
                }
                return KObjectUtil.toKObject( path,
                                              builder.build() );
            }
        } catch ( IOException e ) {
            logger.error( e.getMessage() );
        }
        return null;
    }

    @Override
    public KObjectKey toKObjectKey( final Path path ) {
        return KObjectUtil.toKObjectKey( path );
    }

}
