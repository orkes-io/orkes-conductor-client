import io.orkes.conductor.client.http.ApiClient;
import io.orkes.conductor.client.http.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Examples {

    MetadataResourceApi metadataResourceApi;
    GroupResourceApi groupResourceApi;

    ApplicationResourceApi applicationResourceApi;

    WorkflowResourceApi workflowResourceApi;

    UserResourceApi userResourceApi;

    AuthorizationResourceApi authorizationResourceApi;

    TagsApi tagsApi;

    String keyId = "keyId";
    String keySecret = "keySecret";

    @BeforeEach
    public void init() {
        ApiClient apiClient = new ApiClient("https://play.orkes.io/api", keyId, keySecret);
        metadataResourceApi = new MetadataResourceApi(apiClient);
        groupResourceApi = new GroupResourceApi(apiClient);
        applicationResourceApi = new ApplicationResourceApi(apiClient);
        workflowResourceApi = new WorkflowResourceApi(apiClient);
        tagsApi = new TagsApi(apiClient);
        userResourceApi = new UserResourceApi(apiClient);
        authorizationResourceApi = new AuthorizationResourceApi(apiClient);
    }

    @BeforeEach
    public void setup() {
    }


    @Test
    @DisplayName("tag a user and group")
    public void tagUserAndGroup() {
    }

    @Test
    @DisplayName("tag a workflows and task")
    public void tagWorkflowsAndTasks() {

    }

    @Test
    @DisplayName("add auth to tags")
    public void addAuthToTags() {

    }


    @Test
    @DisplayName("create workflow definition")
    public void createWorkflowDef() {

    }

    @Test
    @DisplayName("start workflow")
    public void startWorkflow() {

    }
}
