/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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
package org.drools.workbench.screens.guided.rule.client.validator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.drools.workbench.screens.guided.rule.client.editor.validator.PatternBindingValidator;
import org.drools.workbench.screens.guided.rule.client.editor.validator.PatternBindingValidatorUtils;
import org.guvnor.common.services.shared.message.Level;
import org.guvnor.common.services.shared.validation.model.ValidationMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PatternBindingValidatorUtilsTest {

    @Test
    public void getValidationTextToDisplayMatchingVariable() {
        String validationTextToDisplay = PatternBindingValidatorUtils.getValidationTextToDisplay(getPatternBindingValidators(),
                                                                                                 "variable");
        assertEquals("validator\nvalidator", validationTextToDisplay);
    }

    @Test
    public void getValidationTextToDisplayNonMatchingVariable() {
        String validationTextToDisplay = PatternBindingValidatorUtils.getValidationTextToDisplay(getPatternBindingValidators(),
                                                                                                 "nonMatchingVariable");
        assertEquals("", validationTextToDisplay);
    }

    private Collection<PatternBindingValidator> getPatternBindingValidators() {
        PatternBindingValidator validator = variableName -> {
            if ("variable".equals(variableName)) {
                return Arrays.asList(new ValidationMessage(Level.WARNING, "validator"));
            }
            return Collections.emptyList();
        };
        return Arrays.asList(validator, validator);
    }
}
