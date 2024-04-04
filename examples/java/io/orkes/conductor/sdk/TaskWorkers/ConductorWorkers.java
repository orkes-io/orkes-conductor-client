package io.orkes.conductor.sdk.TaskWorkers;

import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.def.tasks.Task;
import com.netflix.conductor.sdk.workflow.task.InputParam;
import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import io.orkes.conductor.sdk.TaskWorkers.pojo.OrderInfo;
import io.orkes.conductor.sdk.TaskWorkers.pojo.UserInfo;

import java.util.Date;
import java.util.Random;

import static com.netflix.conductor.common.metadata.tasks.TaskResult.Status.COMPLETED;

public class ConductorWorkers 
{
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

    @WorkerTask("fail_but_retry")
    public Integer fail_but_retry() {
        Random random = new Random();
        int numx = random.nextInt(11);
        if (numx < 8) {
            throw new RuntimeException("number " + numx + " is less than 4. I am going to fail this task and retry");
        }
        return numx;
    }

    @WorkerTask("failure")
    public void failure() {
        throw new RuntimeException("this worker task will always have a terminal failure");
    }

    @WorkerTask("process_task")
    public TaskResult process_task() {
        TaskResult taskResult = new TaskResult();
        taskResult.setStatus(COMPLETED);
        taskResult.addOutputData("name", "orkes");
        taskResult.addOutputData("complex", new UserInfo("Orkes"));
        taskResult.addOutputData("time", new Date());
        return taskResult;
    }
    
    @WorkerTask("save_order")
        public OrderInfo save_order(OrderInfo orderDetails) {
        orderDetails.setSkuPrice(orderDetails.getQuantity() * orderDetails.getSkuPrice());
        return orderDetails;
    }
}
