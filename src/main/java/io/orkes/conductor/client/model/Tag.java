package io.orkes.conductor.client.model;

import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag<T> {
    public enum Type {
        METADATA, RATE_LIMIT
    }

    @Builder.Default
    private Type type = Type.METADATA;
    private String key;
    private T value;

    @Override
    public String toString() {
        return String.format("%s:%s", key, value);
    }

}
