/*
 * Copyright 2020 Orkes, Inc.
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
package io.orkes.conductor.client.e2e;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class WebhookConfig {

    private String id;

    private String name;

    private Map<String, Integer> receiverWorkflowNamesToVersions;

    private Map<String, Integer> workflowsToStart;

    private Map<String, String> headers;

    private Verifier verifier;

    private String sourcePlatform;

    public enum Verifier {
        SLACK_BASED,
        SIGNATURE_BASED,
        HEADER_BASED,
        TWITTER
    }
}
