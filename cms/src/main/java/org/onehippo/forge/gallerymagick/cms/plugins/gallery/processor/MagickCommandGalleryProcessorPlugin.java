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
package org.onehippo.forge.gallerymagick.cms.plugins.gallery.processor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.Plugin;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.plugins.gallery.imageutil.ScalingParameters;
import org.hippoecm.frontend.plugins.gallery.model.GalleryProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagickCommandGalleryProcessorPlugin extends Plugin {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(MagickCommandGalleryProcessorPlugin.class);

    public MagickCommandGalleryProcessorPlugin(final IPluginContext context, final IPluginConfig config) {
        super(context, config);
        final GalleryProcessor processor = createGalleryProcessor(config);
        final String serviceName = config.getString("gallery.processor.id", "service.magick-command.gallery.processor");
        context.registerService(processor, serviceName);
    }

    private GalleryProcessor createGalleryProcessor(IPluginConfig config) {
        final Map<String, ScalingParameters> initScalingParametersMap = new HashMap<>();

        for (IPluginConfig childConfig : config.getPluginConfigSet()) {
            final String nodeName = StringUtils.substringAfterLast(childConfig.getName(), ".");

            if (StringUtils.isNotBlank(nodeName)) {
                int width = childConfig.getAsInteger("width", 0);
                int height = childConfig.getAsInteger("height", 0);
                final ScalingParameters parameters = new ScalingParameters(width, height, false);
                log.debug("Scaling parameters for {}: {}", nodeName, parameters);
                initScalingParametersMap.put(nodeName, parameters);
            }
        }

        return new MagickCommandGalleryProcessor(initScalingParametersMap);
    }
}