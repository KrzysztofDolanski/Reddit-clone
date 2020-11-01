package com.example.demo.exeptions;

public class RedditException extends RuntimeException {


    public RedditException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }
    public RedditException(String exMessage) {
        super(exMessage);
    }

}
