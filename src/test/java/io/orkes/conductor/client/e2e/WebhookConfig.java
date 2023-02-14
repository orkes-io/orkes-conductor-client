/*
 * Copyright 2020 Orkes, Inc.
 * <p>
 * Licensed under the Orkes Enterprise License (the "License"); you may not use this file except in compliance with
 * the License.
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.orkes.conductor.client.e2e;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
