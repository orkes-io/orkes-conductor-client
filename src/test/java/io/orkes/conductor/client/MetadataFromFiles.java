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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;

import io.orkes.conductor.client.util.ApiUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MetadataFromFiles {
    private final MetadataClient metadataClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MetadataFromFiles() {
        OrkesClients orkesClients = ApiUtil.getOrkesClient();
        metadataClient = orkesClients.getMetadataClient();
    }

    //    @Test
    //    @DisplayName("load workflow from file and store definition in conductor")
    //    public void loadAndStoreWorkflowDefinition() throws IOException {
    //        InputStream inputStream =
    //                MetadataFromFiles.class.getResourceAsStream("/sample_workflow.json");
    //        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    //        WorkflowDef workflowDef = objectMapper.readValue(result, WorkflowDef.class);
    //        // Unregister workflow in case it exist
    //        metadataClient.unregisterWorkflowDef(workflowDef.getName(), workflowDef.getVersion());
    //        metadataClient.registerWorkflowDef(workflowDef);
    //        Assertions.assertEquals(
    //                metadataClient.getWorkflowDef(workflowDef.getName(),
    // workflowDef.getVersion()),
    //                workflowDef);
    //    }

    @Test
    @DisplayName("load tasks from file and store definition in conductor")
    public void loadAndStoreTaskDefinitions() throws IOException {
        InputStream inputStream = MetadataFromFiles.class.getResourceAsStream("/sample_tasks.json");
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        List<TaskDef> taskDefs = objectMapper.readValue(result, List.class);
        metadataClient.registerTaskDefs(taskDefs);
    }
}
