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
package io.orkes.conductor.client.api;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.http.model.TagObject;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.WorkflowUtil;

public class MetadataClientTests extends ClientTest {
    private final MetadataClient metadataClient;

    public MetadataClientTests() {
        this.metadataClient = super.orkesClients.getMetadataClient();
    }

    @Test
    @DisplayName("tag a workflows and task")
    public void tagWorkflowsAndTasks() {
        registerTask();
        registerWorkflow();
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.METADATA);
        tagObject.setKey("a");
        tagObject.setValue("b");
        ((OrkesMetadataClient) metadataClient).addTaskTag(tagObject, Commons.TASK_NAME);
        ((OrkesMetadataClient) metadataClient).addWorkflowTag(tagObject, Commons.WORKFLOW_NAME);
    }

    void registerTask() {
        TaskDef taskDef = new TaskDef();
        taskDef.setName(Commons.TASK_NAME);
        this.metadataClient.registerTaskDefs(List.of(taskDef));
    }

    void registerWorkflow() {
        WorkflowDef workflowDef = WorkflowUtil.getWorkflowDef();
        metadataClient.registerWorkflowDef(workflowDef);
    }
}
