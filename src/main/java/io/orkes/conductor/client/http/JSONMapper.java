package io.orkes.conductor.client.http;

import java.lang.reflect.Type;

public interface JSONMapper {
    String serialize(Object obj) throws Exception;
    <T> T deserialize(String body, Type returnType) throws Exception;
}
