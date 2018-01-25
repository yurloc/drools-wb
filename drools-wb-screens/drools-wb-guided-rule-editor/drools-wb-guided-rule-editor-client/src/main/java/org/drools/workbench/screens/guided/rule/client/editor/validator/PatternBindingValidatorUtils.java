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
package org.drools.workbench.screens.guided.rule.client.editor.validator;

import java.util.Collection;
import java.util.stream.Collectors;

import org.guvnor.common.services.shared.validation.model.ValidationMessage;

public final class PatternBindingValidatorUtils {

    private PatternBindingValidatorUtils() {
    }

    public static String getValidationTextToDisplay(final Collection<PatternBindingValidator> validators,
                                                    final String variableName) {
        return validators
                .stream()
                .flatMap(v -> v.validate(variableName).stream())
                .map(ValidationMessage::getText)
                .collect(Collectors.joining("\n"));
    }

}
