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

import com.github.britter.beanvalidators.ValidationWrapper;
import org.junit.Before;
import org.junit.Test;

public class AlphabeticTest {

    private AlphabeticBean alphabeticBean;
    private ValidationWrapper<AlphabeticBean> validator;

    @Before
    public void setUp() {
        alphabeticBean = new AlphabeticBean();
        validator = new ValidationWrapper<>(alphabeticBean, "must be alphabetic");
    }

    @Test
    public void defaultSettingsShouldValidateAlphabeticString() throws Exception {
        alphabeticBean.alphabetic = "abcd";

        validator.assertNoViolations("alphabetic");
    }

    @Test
    public void defaultSettingsShouldValidateNull() throws Exception {
        alphabeticBean.alphabetic = null;

        validator.assertNoViolations("alphabetic");
    }

    @Test
    public void defaultSettingsShouldValidateBlankString() throws Exception {
        alphabeticBean.alphabetic = "";

        validator.assertNoViolations("alphabetic");
    }

    @Test
    public void defaultSettingsShouldNotValidateNonAlphabeticString() throws Exception {
        alphabeticBean.alphabetic = "abcd123";

        validator.assertViolation("alphabetic");
    }

    @Test
    public void defaultSettingsShouldNotValidateAlphabeticStringWithSpaces() throws Exception {
        alphabeticBean.alphabetic = "ab cd";

        validator.assertViolation("alphabetic");
    }

    @Test
    public void allowSpacesSettingsShouldValidateAlphabeticString() throws Exception {
        alphabeticBean.alphabeticSpace = "abcd";

        validator.assertNoViolations("alphabeticSpace");
    }

    @Test
    public void allowSpacesSettingsShouldNotValidateNonAlphabeticString() throws Exception {
        alphabeticBean.alphabeticSpace = "abcd123";

        validator.assertViolation("alphabeticSpace");
    }

    @Test
    public void allowSpacesSettingsShouldValidateAlphabeticStringWithSpaces() throws Exception {
        alphabeticBean.alphabeticSpace = "ab cd";

        validator.assertNoViolations("alphabeticSpace");
    }

    private static class AlphabeticBean {
        @Alphabetic
        private String alphabetic;

        @Alphabetic(allowSpaces = true)
        private String alphabeticSpace;
    }
}
