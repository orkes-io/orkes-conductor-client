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


import io.orkes.conductor.client.http.api.SchemaResourceApi;
import io.orkes.conductor.client.model.Schema;
import io.orkes.conductor.client.model.Tag;

import java.util.List;

public abstract class SchemaClient  {

    protected SchemaResourceApi schemaResourceApi;

    public SchemaClient() {

    }

    public SchemaClient(SchemaResourceApi schemaResourceApi) {
        this.schemaResourceApi = schemaResourceApi;
    }

    public Schema getSchema(String name, Integer version) {
        return schemaResourceApi.getSchemaByNameAndVersion(name, version);
    }

    public Schema createSchema(Schema inputOutputSchema, boolean newVersion) {
        return schemaResourceApi.saveSchema(inputOutputSchema, newVersion);
    }

    public void deleteSchema(String name) {
        schemaResourceApi.deleteSchemaByName(name);
    }

    public void deleteSchema(String name, Integer version) {
        schemaResourceApi.deleteSchemaByNameAndVersion(name, version);
    }

    public void putTag(String name, List<Tag> tag) {
        schemaResourceApi.putTagForSchema(tag, name);
    }

    public void deleteTag(String name, List<Tag> tag) {
        schemaResourceApi.deleteTagForSchema(tag, name);
    }

    public List<Tag> getTags(String name) {
        return schemaResourceApi.getTagsForSchema(name);
    }

    public List<Schema> getAllSchemas(String name, Integer version) {
        return schemaResourceApi.getAllSchemas(name, version);
    }
}
