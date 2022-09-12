package io.orkes.conductor.client;

public abstract class OrkesClientException extends RuntimeException {

    public OrkesClientException() {
    }

    public OrkesClientException(String message) {
        super(message);
    }

    public OrkesClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrkesClientException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @return true if the API call failed due to client errors e.g. validation failures etc.  Equivalent to 4xx
     */
    public abstract boolean isClientError();

    /**
     *
     * @return Status code.  Maps to the status code of the underlying transport used for clients (HTTP by default)
     */
    public abstract int getCode();
}
