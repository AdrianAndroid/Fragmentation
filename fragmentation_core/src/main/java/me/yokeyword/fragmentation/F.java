package me.yokeyword.fragmentation;

import android.util.Log;

/**
 * flannery
 */
public class F {

    private static final boolean DEBUG = true;


    // 打印字符串
    public static void printLogString(StackTraceElement stackTraceElement, Object... o) {
        String fileName = stackTraceElement.getFileName();
        String className = stackTraceElement.getClassName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        StringBuilder sb = new StringBuilder();
        sb.append(methodName).append("(").append(fileName).append(":").append(lineNumber).append(")").append(">").append(className).append("<");
        for (Object value : o) {
            sb.append(value).append(",");
        }
        sb.append("Fragmentation");
        //Log.i(fileName, methodName+"("+fileName+":"+lineNumber+")");
        Log.i(fileName, sb.toString());
    }

    /**
     * 自己提供类名
     */
    public static void m(Class<?> aClass, Object... o) {
        if (DEBUG && aClass != null) {
            StackTraceElement ste = findClassStackTraceElement(aClass);
            if (ste == null) return;
            printLogString(ste, o);
        }
    }

    // 找到当前类的StackTraceElement
    public static StackTraceElement findClassStackTraceElement(Class<?> aClass) {
        if (DEBUG)
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getClassName().startsWith(aClass.getName())) {
                    return stackTraceElement;
                }
            }
        return null;
    }

}
