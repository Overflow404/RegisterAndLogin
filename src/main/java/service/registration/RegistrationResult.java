package service.registration;

public class RegistrationResult {

    static String NULL_DATA = "Registration data cannot be null!";
    static String EMPTY_DATA = "Registration data cannot be empty!";
    static String ALREADY_REGISTERED = "User already registered!";
    static String SUCCESS_REGISTRATION = "User registered!";

    private Result result;
    private String content;
    private String failureReason;

    public enum Result {
        SUCCESS,
        FAILURE
    }

    private RegistrationResult(String content, Result result, String failureReason) {
        this.content = content;
        this.result = result;
        this.failureReason = failureReason;
    }

    static RegistrationResult success(String content) {
        return new RegistrationResult(content, Result.SUCCESS, null);
    }


    static RegistrationResult failure(String reason) {
        return new RegistrationResult(null, Result.FAILURE, reason);
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
