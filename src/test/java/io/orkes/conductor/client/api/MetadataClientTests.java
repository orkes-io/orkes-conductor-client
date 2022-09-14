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

import org.junit.jupiter.api.Test;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;

import io.orkes.conductor.client.MetadataClient;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.model.TagString;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.WorkflowUtil;

public class MetadataClientTests extends ClientTest {
    private final MetadataClient metadataClient;

    public MetadataClientTests() {
        metadataClient = super.orkesClients.getMetadataClient();
    }

    @Test
    void task() {
        TaskDef taskDef = new TaskDef();
        taskDef.setName(Commons.TASK_NAME);
        metadataClient.unregisterTaskDef(Commons.TASK_NAME);
        metadataClient.registerTaskDefs(List.of(taskDef));
        metadataClient.updateTaskDef(taskDef);
        metadataClient.getTaskDef(Commons.TASK_NAME);
    }

    @Test
    void workflow() {
        WorkflowDef workflowDef = WorkflowUtil.getWorkflowDef();
        metadataClient.unregisterWorkflowDef(
                Commons.WORKFLOW_NAME,
                Commons.WORKFLOW_VERSION);
        metadataClient.registerWorkflowDef(workflowDef);
        metadataClient.updateWorkflowDefs(List.of(workflowDef));
        ((OrkesMetadataClient) metadataClient).updateWorkflowDefs(
                List.of(workflowDef), true);
        ((OrkesMetadataClient) metadataClient).registerWorkflowDef(workflowDef, true);
        metadataClient.getWorkflowDef(
                Commons.WORKFLOW_NAME,
                Commons.WORKFLOW_VERSION);
        ((OrkesMetadataClient) metadataClient).getWorkflowDefWithMetadata(
                Commons.WORKFLOW_NAME,
                Commons.WORKFLOW_VERSION);
    }

    @Test
    void tagTask() {
        TagObject tagObject = getTagObject();
        metadataClient.addTaskTag(
                tagObject,
                Commons.TASK_NAME);
        metadataClient.setTaskTags(
                List.of(tagObject),
                Commons.TASK_NAME);
        metadataClient.deleteTaskTag(
                getTagString(),
                Commons.TASK_NAME);
        metadataClient.getTaskTags(
                Commons.TASK_NAME);
    }

    @Test
    void tagWorkflow() {
        TagObject tagObject = getTagObject();
        metadataClient.addWorkflowTag(
                tagObject,
                Commons.WORKFLOW_NAME);
        metadataClient.setWorkflowTags(
                List.of(tagObject),
                Commons.WORKFLOW_NAME);
        metadataClient.deleteWorkflowTag(
                getTagObject(),
                Commons.WORKFLOW_NAME);
        metadataClient.getWorkflowTags(
                Commons.WORKFLOW_NAME);
    }

    @Test
    void tag() {
        metadataClient.getTags();
    }

    TagObject getTagObject() {
        TagObject tagObject = new TagObject();
        tagObject.setType(TagObject.TypeEnum.METADATA);
        tagObject.setKey("a");
        tagObject.setValue("b");
        return tagObject;
    }

    TagString getTagString() {
        TagString tagString = new TagString();
        tagString.setType(TagString.TypeEnum.METADATA);
        tagString.setKey("a");
        tagString.setValue("b");
        return tagString;
    }
}
