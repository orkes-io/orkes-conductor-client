/*
 * Copyright 2022 Orkes, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client;

import java.util.List;

public interface SecretsManager {

    String getSecret(String key);

    void storeSecret(String key, String secret);

    default String getProperty(String propertyName) {
        String property = null;
        List<String> tentativeList =
                List.of(
                        propertyName,
                        propertyName.toUpperCase(),
                        propertyName.replace('.', '_'),
                        propertyName.toUpperCase().replace('.', '_'),
                        propertyName.replace('_', '.'),
                        propertyName.toUpperCase().replace('_', '.'));
        for (String name : tentativeList) {
            if (property == null) {
                property = System.getenv(name);
            }
            if (property == null) {
                property = System.getProperty(name);
            }
        }
        return property;
    }
}
