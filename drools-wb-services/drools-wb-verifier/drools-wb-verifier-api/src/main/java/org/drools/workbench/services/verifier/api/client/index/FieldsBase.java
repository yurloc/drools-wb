/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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
package org.drools.workbench.services.verifier.api.client.index;

import java.util.Collection;

import org.drools.workbench.services.verifier.api.client.maps.KeyTreeMap;

public class FieldsBase<T extends FieldBase> {

    public final KeyTreeMap<T> map = new KeyTreeMap<>( Field.keyDefinitions() );

    public FieldsBase() {

    }

    public void merge( final FieldsBase fields ) {
        map.merge( fields.map );
    }

    public FieldsBase( final Collection<T> fields ) {
        for ( final T field : fields ) {
            add( field );
        }
    }

    public void add( final T field ) {
        map.put( field );
    }


}
