package io.orkes.conductor.client.util;

public class TestUtil {
    private static int RETRY_ATTEMPT_LIMIT = 3;

    public static void retryMethodCall(VoidRunnableWithException function)
            throws Exception {
        for (int retryCounter = 0; retryCounter < RETRY_ATTEMPT_LIMIT; retryCounter += 1) {
            try {
                function.run();
                return;
            } catch (Exception e) {
                System.out.println("Attempt " + (retryCounter + 1) + " failed: " + e.getMessage());
                try {
                    Thread.sleep(1000 * (1 << retryCounter)); // Sleep for 2^retryCounter second(s) before retrying
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        throw new Exception("Failed to execute method call after " + RETRY_ATTEMPT_LIMIT + " attempts");
    }

    public static Object retryMethodCall(RunnableWithException function)
            throws Exception {
        for (int retryCounter = 0; retryCounter < RETRY_ATTEMPT_LIMIT; retryCounter += 1) {
            try {
                return function.run();
            } catch (Exception e) {
                System.out.println("Attempt " + (retryCounter + 1) + " failed: " + e.getMessage());
                try {
                    Thread.sleep(1000 * (1 << retryCounter)); // Sleep for 2^retryCounter second(s) before retrying
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        throw new Exception("Failed to execute method call after " + RETRY_ATTEMPT_LIMIT + " attempts");
    }

    @FunctionalInterface
    public interface RunnableWithException {
        Object run() throws Exception;
    }

    @FunctionalInterface
    public interface VoidRunnableWithException {
        void run() throws Exception;
    }
}
