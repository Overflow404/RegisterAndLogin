package service.login;

public class LoginResult {

    static String NULL_DATA = "Login data cannot be null!";
    static String EMPTY_DATA = "Login data cannot be empty!";
    static String NOT_REGISTERED = "User not registered!";
    static String WRONG_PASSWORD = "Wrong password!";
    static String SUCCESS_LOGIN = "User logged!";

    private Result result;
    private String content;
    private String failureReason;

    public enum Result {
        SUCCESS,
        FAILURE
    }

    private LoginResult(String content, Result result, String failureReason) {
        this.content = content;
        this.result = result;
        this.failureReason = failureReason;
    }

    static LoginResult success(String content) {
        return new LoginResult(content, Result.SUCCESS, null);
    }


    static LoginResult failure(String reason) {
        return new LoginResult(null, Result.FAILURE, reason);
    }


    public boolean success() {
        return result.equals(Result.SUCCESS);
    }

    public String getContent() {
        return content;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
