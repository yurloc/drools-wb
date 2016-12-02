/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package org.drools.workbench.verifier.webworker.linker;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.impl.SelectionScriptLinker;

@LinkerOrder(LinkerOrder.Order.PRIMARY)
public class VerifierLinker
        extends SelectionScriptLinker {

    @Override
    public String getDescription() {
        return "Dedicated Web Worker Linker";
    }

    @Override
    protected String getCompilationExtension(TreeLogger logger,
                                             LinkerContext context) throws UnableToCompleteException {
        return ".cache.js";
    }

    @Override
    protected String getModulePrefix(TreeLogger logger,
                                     LinkerContext context,
                                     String strongName) throws UnableToCompleteException {
        return "";
    }

    @Override
    protected String getModuleSuffix2(TreeLogger logger,
                                      LinkerContext context,
                                      String strongName) throws UnableToCompleteException {
        return "";
    }

    @Override
    protected String getSelectionScriptTemplate(TreeLogger logger,
                                                LinkerContext context) throws UnableToCompleteException {
        return "org/drools/workbench/verifier/webworker/linker/ScriptTemplate.js";
    }

}
