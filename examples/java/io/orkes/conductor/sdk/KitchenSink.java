package io.orkes.conductor.sdk;

import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.*;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;
import com.netflix.conductor.sdk.workflow.def.tasks.JQ;



import java.time.Duration;

public class KitchenSink {
    private final WorkflowExecutor executor;
    public GreetingsWorkflow(WorkflowExecutor executor) {
        this.executor = executor;
    }
    public ConductorWorkflow<WorkflowInput> createWorkflow() {
        ConductorWorkflow<WorkflowInput> workflow = new ConductorWorkflow<>(executor);
        workflow.setName("greetings");
        workflow.setVersion(1);
        //Simple Task
        SimpleTask greetingsTask = new SimpleTask("greet", "greet_ref");
        greetingsTask.input("name", "IDXC"+"${workflow.input.name}");
        workflow.add(greetingsTask);
        //JS Task
        String script = "function greetings(name){return {\"text\": \"Your email is \" + name+\"@workflow.io\",\"url\": \"https://orkes-api-tester.orkesconductor.com/api\"}}greetings(\"'${workflow.input.name}'\");";
        Javascript jstask = new Javascript("hello_script",script);
        workflow.add(jstask);
        //Wait Task
        Wait waitTask = new Wait("wait_for_1_sec", Duration.ofMillis(1000));
        workflow.add(waitTask);//workflow is an object of ConductorWorkflow<WorkflowInput>
        //Set Variable
        SetVariable setVariable = new SetVariable("set_name");
        setVariable.input("Name","${workflow.input.name}");
        workflow.add(setVariable);
        //SubWorkflow
        SubWorkflow subWorkflow = new SubWorkflow("persist_inDB","insertintodb", 1);
        subWorkflow.input("name","{workflow.input.name}");
        workflow.add(subWorkflow);
        //Switch Case
        Wait waitTask2 = new Wait("wait_for_2_sec", Duration.ofMillis(2000));
        Javascript jstask2 = new Javascript("hello_script2",script);
        Switch switchTask = new Switch("Version_switch", "${workflow.input.name}").switchCase("Orkes", jstask2).switchCase("Others", waitTask2);
        workflow.add(switchTask);
        //JQ Task
        /* Sample Input
        {
        "persons": [
          {
            "name": "some",
            "last": "name",
            "email": "mail@mail.com",
            "id": 1
          },
          {
            "name": "some2",
            "last": "name2",
            "email": "mail2@mail.com",
            "id": 2
          }
        ]
      }
         */
        JQ jqtask = new JQ("jq_task", ".persons | map({user:{email,name}})");
        jqtask.input( "persons", "${workflow.input.jqtask}");
        workflow.add(jqtask);
        //HTTP Task
        Http httptask = new Http("HTTPtask");
        httptask.url("https://orkes-api-tester.orkesconductor.com/api");
        workflow.add(httptask);
        return workflow;
    }
}
