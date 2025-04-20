package StepDefinitions;

public class StepContext {
    private static ThreadLocal<Boolean> stepResult = new ThreadLocal<>();
    private static ThreadLocal<String> stepInfo = new ThreadLocal<>();

    public static void setResult(boolean result) {
        stepResult.set(result);
    }

    public static Boolean getResult() {
        return stepResult.get();
    }

    public static void setInfo(String info) {
        stepInfo.set(info);
    }

    public static String getInfo() {
        return stepInfo.get();
    }

    public static void clear() {
        stepResult.remove();
        stepInfo.remove();
    }
}

