package io.orkes.conductor.client;

public interface OrkesClientException {

    /**
     *
     * @return true if the API call failed due to client errors e.g. validation failures etc.  Equivalent to 4xx
     */
    boolean isClientError();

    /**
     *
     * @return Status code.  Maps to the status code of the underlying transport used for clients (HTTP by default)
     */
    int getCode();

    /**
     *
     * @return Detailed error message
     */
    String getMessage();

    /**
     *
     * @return Root Cause if any
     */
    Throwable getCause();
}
