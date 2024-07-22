/*
 * Copyright 2024 Orkes, Inc.
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
package io.orkes.conductor.sdk.examples.dynamicworkflow.workflow;

import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

public class CreateWorkflow {
    private final WorkflowExecutor executor;
    public CreateWorkflow(WorkflowExecutor executor) {
        this.executor = executor;
    }
    public ConductorWorkflow<WorkflowInput> createSimpleWorkflow() {
        ConductorWorkflow<WorkflowInput> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("email_send_workflow");
        workflow.setVersion(1);

        SimpleTask getUserDetails = new SimpleTask("get_user_info", "get_user_info");
        getUserDetails.input("userId", "${workflow.input.userId}");

        // send email
        SimpleTask sendEmail = new SimpleTask("send_email", "send_email");
        // get user details user info, which contains the email field
        sendEmail.input("email", "${get_user_info.output.email}");

        workflow.add(getUserDetails);
        workflow.add(sendEmail);

        return workflow;
    }
}