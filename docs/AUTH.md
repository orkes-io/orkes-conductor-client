# Authorization Management APIs
AuthorizationClient allows management of permissions in Conductor.
Please note, all the APIs here requires an `ADMIN` unless otherwise noted.

### Initialize AuthorizationClient
```java
ApiClient apiClient = new ApiClient("https://play.orkes.io/api", KEY, SECRET);
OrkesClients orkesClients = OrkesClients(apiClient);
AuthorizationClient authorizationClient = super.orkesClients.getAuthorizationClient();
```

### Manage Users

#### Add/Update users
````java
UpsertUserRequest request = new UpsertUserRequest();
request.setName("Orkes User");
request.setGroups(Arrays.asList("Example Group"));
request.setRoles(Arrays.asList(UpsertUserRequest.RolesEnum.USER));
String userId = "user@orkes.io";        //MUST be the email addressed used to login to Conductor
        
//Does an upsert.  A user is created if the user does not exist in the system, or updated for an existing user.        
ConductorUser user = authorizationClient.upsertUser(request, userId);
````


### Create and Manage Groups
```java
UpsertGroupRequest request = new UpsertGroupRequest();

//Default Access for the group.  When specified, any new workflow or task created by the members of this group
//get this default permission inside the group.
Map<String, List<String>> defaultAccess = new HashMap<>();

//Grant READ access to the members of the group for any new workflow created by a member of this group
defaultAccess.put("WORKFLOW_DEF", List.of("READ"));

//Grant EXECUTE access to the members of the group for any new task created by a member of this group
defaultAccess.put("TASK_DEF", List.of("EXECUTE"));
request.setDefaultAccess(defaultAccess);

request.setDescription("Example group created for testing");
request.setRoles(Arrays.asList(UpsertGroupRequest.RolesEnum.USER));

String groupId = "Test Group";
//Creates a new group or updates if the group by the Id alerady exists
Group group = authorizationClient.upsertGroup(request, groupId);
```
### Create and Manage Applications
```java
CreateOrUpdateApplicationRequest request = new CreateOrUpdateApplicationRequest();
request.setName("Test Application for the testing");

//WARNING: Application Name is not a UNIQUE value and if called multiple times, it will create a new application
ConductorApplication application = authorizationClient.createApplication(request);


//Get the list of applications
List<ConductorApplication> apps = authorizationClient.listApplications();


//Create a new access key
CreateAccessKeyResponse accessKey = authorizationClient.createAccessKey(application.getId());
//WARNING: Acess Keys are a secret, Please take care not printing in a log
        
//Delete the application from the system
authorizationClient.deleteApplication(application.getId());
```

### Working with Tags
Tags are used to manage authorization more effectively by tagging multiple metadata with tags.

For example, you can tag multiple workflows with a given tag (e.g. `org:accounting`) and instead of granting permissions 
for each individual workflow, you can grant permissions to a tag.

#### Granting permissions to group
```java
AuthorizationRequest request = new AuthorizationRequest();

//Grant READ permissions
request.access(Arrays.asList(AuthorizationRequest.AccessEnum.READ));

//Create a Group subject
SubjectRef subject = new SubjectRef();
subject.setId("Example Group");
subject.setType(SubjectRef.TypeEnum.GROUP);

request.setSubject(subject);

//Target identifies the resource on which the permission is granted. e.g. Workflow, Task etc.
TargetRef target = new TargetRef();
target.setId("Test_032");
target.setType(TargetRef.TypeEnum.WORKFLOW_DEF);


request.setTarget(target);
authorizationClient.grantPermissions(request);

```