package com.travel.community.travel_demo.exception;

/**
 * @author w1586
 */
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

//    public CustomizeException(String message){
////        super(message);
//        this.message = message;
//    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode(){
        return code;
    }
}
