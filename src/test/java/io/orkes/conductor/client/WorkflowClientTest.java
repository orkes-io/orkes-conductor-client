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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.GroupResourceApi;
import io.orkes.conductor.client.http.model.Group;
import io.orkes.conductor.client.http.model.UpsertGroupRequest;

public class WorkflowClientTest {

    public static void main(String[] args) {
        ApiClient apiClient =
                new ApiClient(
                        "https://pg-staging.orkesconductor.com/api",
                        "token");

        GroupResourceApi groupResourceApi = new GroupResourceApi(apiClient);
        Group group = groupResourceApi.getGroup("ORG_TEST");
        System.out.println("Group : " + group);

        UpsertGroupRequest upsertGroupRequest = new UpsertGroupRequest();
        Map<String, List<String>> defaultAccess = new HashMap<>();
        defaultAccess.put("WORKFLOW_DEF", Arrays.asList("READ"));
        upsertGroupRequest.setDefaultAccess(defaultAccess);
        upsertGroupRequest.setRoles(Arrays.asList(UpsertGroupRequest.RolesEnum.ADMIN));
        upsertGroupRequest.setDescription("Hello");

        groupResourceApi.upsertGroup(upsertGroupRequest, group.getId());

        //        WorkflowResourceApi workflowClient = new WorkflowResourceApi(apiClient);
        //        StartWorkflowRequest request = new StartWorkflowRequest();
        //        request.setCorrelationId("virenx");
        //        Map<String, Object> input = new HashMap<>();
        //        input.put("input_key", "input value");
        //        request.setInput(input);
        //        request.setName("http_perf_test");
        //        String workflowId = workflowClient.startWorkflow(request);
        //        System.out.println("workflow Id " + workflowId);

    }
}
