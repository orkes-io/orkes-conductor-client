package io.orkes.conductor.sdk.DynamicWorkflow.workflow;

import io.orkes.conductor.sdk.workflow.def.ConductorWorkflow;
import io.orkes.conductor.sdk.workflow.def.tasks.SimpleTask;
import io.orkes.conductor.sdk.workflow.executor.WorkflowExecutor;

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