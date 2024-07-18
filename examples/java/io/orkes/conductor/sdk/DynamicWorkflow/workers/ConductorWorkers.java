package io.orkes.conductor.sdk.DynamicWorkflow.workers;

import io.orkes.conductor.sdk.workflow.task.InputParam;
import io.orkes.conductor.sdk.workflow.task.WorkerTask;

public class ConductorWorkers {
    
    @WorkerTask("greet")
    public String greet(@InputParam("name") String name) {
        return "Hello " + name;
    }

    @WorkerTask("get_user_info")
    public UserInfo getUserInfo(@InputParam("userId") String userId) {
        UserInfo userInfo =  new UserInfo("User X", userId);
        userInfo.setEmail(userId + "@example.com");
        userInfo.setPhoneNumber("555-555-5555");
        return userInfo;
    }

    @WorkerTask("send_email")
    public void sendEmail(@InputParam("email") String email) {
    System.out.println("Sending email to " + email);
    }
}
