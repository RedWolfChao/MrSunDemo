package utils;

public class LogUtils {
    public static void logI(String tag, Object obj) {
        System.out.println("[--" + tag + "--][" + obj.toString() + "]");
    }
}
