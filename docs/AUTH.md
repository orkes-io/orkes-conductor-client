# Authorization Management APIs
AuthorizationClient allows management of permissions in Conductor.
Please note, all the APIs here requires an `ADMIN` unless otherwise noted.

### Initialize AuthorizationClient
```java
ApiClient apiClient = new ApiClient("https://play.orkes.io/api", KEY, SECRET);
OrkesClients orkesClients = OrkesClients(apiClient);
AuthorizationClient authorizationClient = super.orkesClients.getAuthorizationClient();
```

### Adding User

### Create and Manage Groups

### Create and Manage Applications

### Working with Tags