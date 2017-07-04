/*
 * Copyright 2015 Benedikt Ritter
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
package com.github.britter.beanvalidators.strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class AlphabeticConstraintValidator implements ConstraintValidator<Alphabetic, String> {

    private boolean allowSpaces;

    @Override
    public void initialize(final Alphabetic constraintAnnotation) {
        this.allowSpaces = constraintAnnotation.allowSpaces();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        // Don't validate null, empty and blank strings, since these are validated by @NotNull, @NotEmpty and @NotBlank
        return StringUtils.isBlank(value) || (allowSpaces ? StringUtils.isAlphaSpace(value) : StringUtils.isAlpha(value));
    }

}
