package io.orkes.conductor.client.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchemaDef {

    public enum Type {
        JSON, AVRO, PROTOBUF
    }

    private String name;

    private Integer version = 1;

    private Type type;

    private Map<String, Object> data;

    private String externalRef;

}
