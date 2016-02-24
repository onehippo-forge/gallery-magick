/*
 * Copyright 2016-2016 Hippo B.V. (http://www.onehippo.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onehippo.forge.gallerymagick.core.gm.command;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class GraphicsMagickCommandUtilsTest extends AbstractGraphicsMagickCommandTest {

    private URL HIPPO_79_JPG = GraphicsMagickCommandUtilsTest.class.getResource("/hippo-79.jpg");

    @Before
    public void before() throws Exception {
        Assume.assumeTrue(isGraphicsMagickAvailable());
    }

    @Test
    public void testGraphicsMagickResizeImage() throws Exception {
        File sourceFile = new File(HIPPO_79_JPG.toURI());
        long sourceLength = sourceFile.length();
        File targetFile = new File("target/" + GraphicsMagickCommandUtilsTest.class.getSimpleName() + "-thumbnail.jpg");

        GraphicsMagickCommandUtils.resizeImage(sourceFile, targetFile, 120, 120, "+profile", "*");

        assertTrue(targetFile.isFile());
        assertTrue(targetFile.length() > 0L);
        assertTrue(targetFile.length() < sourceLength);
    }

}