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


import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.run.Workflow;

public abstract class TaskClient extends com.netflix.conductor.client.http.TaskClient {

    /**
     * Update the task status and output based given workflow id and task reference name
     * @param workflowId Workflow Id
     * @param taskReferenceName Reference name of the task to be updated
     * @param status Status of the task
     * @param output Output for the task
     */
    public abstract void updateTask(String workflowId, String taskReferenceName, TaskResult.Status status, Object output);

    /**
     * Update the task status and output based given workflow id and task reference name and return back the updated workflow status
     * @param workflowId Workflow Id
     * @param taskReferenceName Reference name of the task to be updated
     * @param status Status of the task
     * @param output Output for the task
     * @return Status of the workflow after updating the task
     */
    public abstract Workflow updateTaskSync(String workflowId, String taskReferenceName, TaskResult.Status status, Object output);

}
