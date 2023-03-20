package io.orkes.conductor.client.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class BusinessStateSchema {

    /*
    {
       "dbType" : "POSTGRESQL|MONGODB",
       "dbName" : "Audit",
       "Schema" :
           {
               "orderStatus": "order_status",
               "city" : "order_city",
               "customerId" : "customer_id"
           }
       }
     */

    public enum DataBaseType {
        POSTGRESQL, MONGODB
    }

    private DataBaseType dbType;
    private String dbName;
    private HashMap<String, Object> schema;
}
