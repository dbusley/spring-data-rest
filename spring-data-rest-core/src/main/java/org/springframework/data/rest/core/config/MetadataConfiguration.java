/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.rest.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * Configuration for metadata exposure.
 * 
 * @author Oliver Gierke
 */
public class MetadataConfiguration {

	private final Map<Class<?>, JsonSchemaFormat> schemaFormats = new HashMap<Class<?>, JsonSchemaFormat>();
	private final Map<Class<?>, Pattern> patterns = new HashMap<Class<?>, Pattern>();
	private boolean omitUnresolvableDescriptionKeys = true;
	private boolean alpsEnabled = true;

	/**
	 * Configures whether to omit documentation attributes for unresolvable resource bundle keys. Defaults to
	 * {@literal true}, which means that an unsuccessful attempt to resolve the message will cause no documentation entry
	 * to be rendered for the metadata resources.
	 * 
	 * @param omitUnresolvableDescriptionKeys whether to omit documentation attributes for unresolvable resource bundle
	 *          keys.
	 */
	public void setOmitUnresolvableDescriptionKeys(boolean omitUnresolvableDescriptionKeys) {
		this.omitUnresolvableDescriptionKeys = omitUnresolvableDescriptionKeys;
	}

	/**
	 * Returns whether to omit documentation attributes for unresolvable resource bundle keys.
	 * 
	 * @return the omitUnresolvableDescriptionKeys
	 */
	public boolean omitUnresolvableDescriptionKeys() {
		return omitUnresolvableDescriptionKeys;
	}

	/**
	 * Configures whether to expose the ALPS resources.
	 * 
	 * @param alpsEnabled the alpsEnabled to set
	 */
	public void setAlpsEnabled(boolean enableAlps) {
		this.alpsEnabled = enableAlps;
	}

	/**
	 * Returns whether the ALPS resources are exposed.
	 * 
	 * @return the alpsEnabled
	 */
	public boolean alpsEnabled() {
		return alpsEnabled;
	}

	public void registerJsonSchemaFormat(JsonSchemaFormat format, Class<?>... types) {

		Assert.notNull(format, "JsonSchemaFormat must not be null!");

		for (Class<?> type : types) {
			schemaFormats.put(type, format);
		}
	}

	public JsonSchemaFormat getSchemaFormatFor(Class<?> type) {
		return schemaFormats.get(type);
	}

	public void registerFormattingPatternFor(String pattern, Class<?> type) {

		Assert.hasText(pattern, "Pattern must not be null or empty!");
		Assert.notNull(type, "Type must not be null!");

		this.patterns.put(type, Pattern.compile(pattern));
	}

	public Pattern getPatternFor(Class<?> type) {

		for (Entry<Class<?>, Pattern> entry : this.patterns.entrySet()) {
			if (entry.getKey().isAssignableFrom(type)) {
				return entry.getValue();
			}
		}

		return this.patterns.get(type);
	}
}