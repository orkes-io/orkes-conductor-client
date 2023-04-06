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
package io.orkes.conductor.client.util;

import java.util.Arrays;
import java.util.Map;

import com.netflix.conductor.common.metadata.tasks.TaskDef;
import com.netflix.conductor.common.metadata.tasks.TaskType;
import com.netflix.conductor.common.metadata.workflow.SubWorkflowParams;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;

import io.orkes.conductor.client.MetadataClient;

public class RegistrationUtil {

    public static void registerWorkflowDef(String workflowName, String taskName1, String taskName2, MetadataClient metadataClient1) {
        TaskDef taskDef = new TaskDef(taskName1);
        taskDef.setRetryCount(0);
        taskDef.setOwnerEmail("test@orkes.io");
        TaskDef taskDef2 = new TaskDef(taskName2);
        taskDef2.setRetryCount(0);
        taskDef2.setOwnerEmail("test@orkes.io");


        WorkflowTask inline = new WorkflowTask();
        inline.setTaskReferenceName("inline_" + taskName1);
        inline.setName(taskName1);
        inline.setTaskDefinition(taskDef);
        inline.setWorkflowTaskType(TaskType.INLINE);
        inline.setInputParameters(Map.of("evaluatorType", "graaljs", "expression", "true;"));

        WorkflowTask simpleTask = new WorkflowTask();
        simpleTask.setTaskReferenceName(taskName2);
        simpleTask.setName(taskName2);
        simpleTask.setTaskDefinition(taskDef);
        simpleTask.setWorkflowTaskType(TaskType.SIMPLE);
        simpleTask.setInputParameters(Map.of("value", "${workflow.input.value}", "order", "123"));

        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setOwnerEmail("test@orkes.io");
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to monitor order state");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setTasks(Arrays.asList(inline, simpleTask));
        metadataClient1.updateWorkflowDefs(Arrays.asList(workflowDef));
        metadataClient1.registerTaskDefs(Arrays.asList(taskDef, taskDef2));
    }

    public static void registerWorkflowWithSubWorkflowDef(String workflowName, String subWorkflowName, String taskName, MetadataClient metadataClient) {
        TaskDef taskDef = new TaskDef(taskName);
        taskDef.setRetryCount(0);
        taskDef.setOwnerEmail("test@orkes.io");
        TaskDef taskDef2 = new TaskDef(subWorkflowName);
        taskDef2.setRetryCount(0);
        taskDef2.setOwnerEmail("test@orkes.io");

        WorkflowTask inline = new WorkflowTask();
        inline.setTaskReferenceName(taskName);
        inline.setName(taskName);
        inline.setTaskDefinition(taskDef);
        inline.setWorkflowTaskType(TaskType.SIMPLE);
        inline.setInputParameters(Map.of("evaluatorType", "graaljs", "expression", "true;"));

        WorkflowTask subworkflowTask = new WorkflowTask();
        subworkflowTask.setTaskReferenceName(subWorkflowName);
        subworkflowTask.setName(subWorkflowName);
        subworkflowTask.setTaskDefinition(taskDef2);
        subworkflowTask.setWorkflowTaskType(TaskType.SUB_WORKFLOW);
        SubWorkflowParams subWorkflowParams = new SubWorkflowParams();
        subWorkflowParams.setName(subWorkflowName);
        subWorkflowParams.setVersion(1);
        subworkflowTask.setSubWorkflowParam(subWorkflowParams);
        subworkflowTask.setInputParameters(Map.of("subWorkflowName", subWorkflowName, "subWorkflowVersion", "1"));


        WorkflowDef subworkflowDef = new WorkflowDef();
        subworkflowDef.setName(subWorkflowName);
        subworkflowDef.setOwnerEmail("test@orkes.io");
        subworkflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        subworkflowDef.setDescription("Sub Workflow to test retry");
        subworkflowDef.setTimeoutSeconds(600);
        subworkflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        subworkflowDef.setTasks(Arrays.asList(inline));

        WorkflowDef workflowDef = new WorkflowDef();
        workflowDef.setName(workflowName);
        workflowDef.setInputParameters(Arrays.asList("value", "inlineValue"));
        workflowDef.setDescription("Workflow to test retry");
        workflowDef.setTimeoutSeconds(600);
        workflowDef.setTimeoutPolicy(WorkflowDef.TimeoutPolicy.TIME_OUT_WF);
        workflowDef.setTasks(Arrays.asList(subworkflowTask));
        workflowDef.setOwnerEmail("test@orkes.io");
        metadataClient.registerWorkflowDef(workflowDef);
        metadataClient.registerWorkflowDef(subworkflowDef);
        metadataClient.registerTaskDefs(Arrays.asList(taskDef, taskDef2));
    }

}
