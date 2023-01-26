package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private final String url;
    //private final ErrorType type;
    private final String type;
    private final String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type.name().toLowerCase().substring(0, 1).toUpperCase() + type.name().substring(1).replace("_", " ").toLowerCase();;
        this.detail = detail;
    }
}