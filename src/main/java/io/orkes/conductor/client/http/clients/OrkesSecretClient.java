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
package io.orkes.conductor.client.http.clients;

import java.util.List;
import java.util.Set;

import io.orkes.conductor.client.api.SecretClient;
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.model.TagObject;

public class OrkesSecretClient extends OrkesClient implements SecretClient {

    private final SecretResource secretResource;

    public OrkesSecretClient(OrkesHttpClient apiClient) {
        super(apiClient);
        this.secretResource = new SecretResource(apiClient);
    }

    @Override
    public void deleteSecret(String key) throws ApiException {
        secretResource.deleteSecret(key);
    }

    @Override
    public String getSecret(String key) throws ApiException {
        return secretResource.getSecret(key);
    }

    @Override
    public Set<String> listAllSecretNames() throws ApiException {
        return secretResource.listAllSecretNames();
    }

    @Override
    public List<String> listSecretsThatUserCanGrantAccessTo() throws ApiException {
        return secretResource.listSecretsThatUserCanGrantAccessTo();
    }

    @Override
    public void putSecret(String value, String key) throws ApiException {
        secretResource.putSecret(value, key);
    }

    @Override
    public boolean secretExists(String key) throws ApiException {
        return secretResource.secretExists(key);
    }

    @Override
    public void setSecretTags(List<TagObject> tags, String key) {
        secretResource.putTagForSecret(tags, key);
    }

    @Override
    public void deleteSecretTags(List<TagObject> tags, String key) {
        secretResource.deleteTagForSecret(tags, key);
    }

    @Override
    public List<TagObject> getSecretTags(String key) {
        return secretResource.getTags(key);
    }
}
