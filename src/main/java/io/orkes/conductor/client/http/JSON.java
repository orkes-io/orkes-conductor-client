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
package io.orkes.conductor.client.http;

import java.lang.reflect.Type;

import com.netflix.conductor.common.config.ObjectMapperProvider;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JSON {
    private final ObjectMapper objectMapper;
    public JSON() {
        objectMapper = new ObjectMapperProvider().getObjectMapper();
    }

    public JSON setLenientOnJson(boolean lenientOnJson) {
        return this;
    }

    /**
     * Serialize the given Java object into JSON string.
     *
     * @param obj Object
     * @return String representation of the JSON
     */
    @SneakyThrows
    public String serialize(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * Deserialize the given JSON string to Java object.
     *
     * @param <T> Type
     * @param body The JSON string
     * @param returnType The type to deserialize into
     * @return The deserialized Java object
     */
    @SuppressWarnings("unchecked")
    public <T> T deserialize(String body, Type returnType) {
        try {
            if (returnType.equals(String.class)) {
                return (T) body;
            }

            JavaType javaType = objectMapper.getTypeFactory().constructType(returnType);
            return objectMapper.readValue(body, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
