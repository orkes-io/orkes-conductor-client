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
import io.orkes.conductor.client.http.ApiException;
import io.orkes.conductor.client.http.OrkesMetadataClient;
import io.orkes.conductor.client.model.TagObject;
import io.orkes.conductor.client.util.Commons;
import io.orkes.conductor.client.util.TestUtil;
import io.orkes.conductor.client.util.WorkflowUtil;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
public class MetadataClientTests extends ClientTest {
    private final MetadataClient metadataClient;

    public MetadataClientTests() {
        metadataClient = orkesClients.getMetadataClient();
        ((OrkesMetadataClient) metadataClient).withReadTimeout(45000);
    }

    @Test
    void taskDefinition() {
        try {
            metadataClient.unregisterTaskDef(Commons.TASK_NAME);
        } catch (ApiException e) {
            if (e.getStatusCode() != 404) {
                throw e;
            }
        }
        TaskDef taskDef = Commons.getTaskDef();
        metadataClient.registerTaskDefs(List.of(taskDef));
        metadataClient.updateTaskDef(taskDef);
        TaskDef receivedTaskDef = metadataClient.getTaskDef(Commons.TASK_NAME);
        assertEquals(taskDef.getName(), receivedTaskDef.getName());
    }

    @Test
    void workflow() {
        try {
            metadataClient.unregisterWorkflowDef(Commons.WORKFLOW_NAME, Commons.WORKFLOW_VERSION);
        } catch (ApiException e) {
            if (e.getStatusCode() != 404) {
                throw e;
            }
        }
        metadataClient.registerTaskDefs(List.of(Commons.getTaskDef()));
        WorkflowDef workflowDef = WorkflowUtil.getWorkflowDef();
        metadataClient.registerWorkflowDef(workflowDef);
        metadataClient.updateWorkflowDefs(List.of(workflowDef));
        metadataClient.updateWorkflowDefs(List.of(workflowDef), true);
        metadataClient.registerWorkflowDef(workflowDef, true);
        ((OrkesMetadataClient) metadataClient)
                .getWorkflowDefWithMetadata(Commons.WORKFLOW_NAME, Commons.WORKFLOW_VERSION);
        WorkflowDef receivedWorkflowDef = metadataClient.getWorkflowDef(Commons.WORKFLOW_NAME,
                Commons.WORKFLOW_VERSION);
        assertEquals(receivedWorkflowDef.getName(), Commons.WORKFLOW_NAME);
        assertEquals(receivedWorkflowDef.getVersion(), Commons.WORKFLOW_VERSION);
    }

    @Test
    void tagTask() throws Exception {
        metadataClient.registerTaskDefs(List.of(Commons.getTaskDef()));
        try {
            metadataClient.deleteTaskTag(Commons.getTagString(), Commons.TASK_NAME);
        } catch (ApiException e) {
            if (e.getStatusCode() != 404) {
                throw e;
            }
        }
        TagObject tagObject = Commons.getTagObject();
        metadataClient.addTaskTag(tagObject, Commons.TASK_NAME);
        metadataClient.setTaskTags(List.of(tagObject), Commons.TASK_NAME);
        assertNotNull(
                TestUtil.retryMethodCall(
                        metadataClient::getTags));
        List<TagObject> tags = (List<TagObject>) TestUtil.retryMethodCall(
                () -> metadataClient.getTaskTags(Commons.TASK_NAME));
        assertIterableEquals(List.of(tagObject), tags);
        metadataClient.deleteTaskTag(Commons.getTagString(), Commons.TASK_NAME);
        tags = (List<TagObject>) TestUtil.retryMethodCall(
                () -> metadataClient.getTaskTags(Commons.TASK_NAME));
        assertIterableEquals(List.of(), tags);
    }

    @Test
    void tagWorkflow() {
        TagObject tagObject = Commons.getTagObject();
        try {
            metadataClient.deleteWorkflowTag(Commons.getTagObject(), Commons.WORKFLOW_NAME);
        } catch (ApiException e) {
            if (e.getStatusCode() != 404) {
                throw e;
            }
        }
        metadataClient.addWorkflowTag(tagObject, Commons.WORKFLOW_NAME);
        metadataClient.setWorkflowTags(List.of(tagObject), Commons.WORKFLOW_NAME);
        List<TagObject> tags = metadataClient.getWorkflowTags(Commons.WORKFLOW_NAME);
        assertIterableEquals(List.of(tagObject), tags);
    }
}
