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
package com.github.britter.beanvalidators.file;

import static com.google.common.collect.Iterables.getLast;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import javax.validation.ConstraintViolation;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import com.github.britter.beanvalidators.ValidationWrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class HiddenTest {

    private FileBean fileBean;
    private ValidationWrapper<FileBean> validator;

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        fileBean = new FileBean();
        validator = new ValidationWrapper<>(fileBean);
    }

    @Test
    public void shouldValidateNull() throws Exception {
        fileBean.file = null;

        Set<ConstraintViolation<FileBean>> violations = validator.validate("file");

        assertThat(violations, is(empty()));
    }

    @Test
    public void shouldNotValidateUnhiddenDirectory() throws Exception {
        fileBean.file = tmpFolder.newFolder();

        Set<ConstraintViolation<FileBean>> violations = validator.validate("file");

        assertThat(violations, hasSize(1));
        ConstraintViolation<FileBean> violation = getLast(violations);
        assertThat(violation.getMessage(), is(equalTo("must be hidden")));
    }

    @Test
    public void shouldNotValidateUnhiddenFile() throws Exception {
        fileBean.file = tmpFolder.newFile();

        Set<ConstraintViolation<FileBean>> violations = validator.validate("file");

        assertThat(violations, hasSize(1));
    }

    @Test
    public void shouldValidateHiddenDirectory() throws Exception {
        fileBean.file = tmpFolder.newFolder(".hidden");

        Set<ConstraintViolation<FileBean>> violations = validator.validate("file");

        assertThat(violations, is(empty()));
    }

    @Test
    public void shouldValidateHiddenFile() throws Exception {
        fileBean.file = tmpFolder.newFile(".hidden");

        Set<ConstraintViolation<FileBean>> violations = validator.validate("file");

        assertThat(violations, is(empty()));
    }

    private static final class FileBean {
        @Hidden
        private File file;
    }
}