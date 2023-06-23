package io.orkes.conductor.client.util;

import java.lang.reflect.Method;

public class TestUtil {
    private static int RETRY_ATTEMPT_LIMIT = 3;

    public static Object retryMethodCall(Object targetObject, String methodName, Object[] params, int maxRetries)
            throws Exception {
        for (int retryCounter = 0; retryCounter < RETRY_ATTEMPT_LIMIT; retryCounter += 1) {
            try {
                Class<?> targetClass = targetObject.getClass();
                Method method = targetClass.getMethod(methodName, getParameterTypes(params));
                return method.invoke(null, params);
            } catch (Exception e) {
                // Handle the exception (you can log or perform any necessary actions)
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

    private static Class<?>[] getParameterTypes(Object[] params) {
        Class<?>[] parameterTypes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            parameterTypes[i] = params[i].getClass();
        }
        return parameterTypes;
    }
}
