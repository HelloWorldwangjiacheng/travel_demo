package com.travel.community.travel_demo.exception;


/**
 * @author w1586
 */
public interface ICustomizeErrorCode {
    /**
     * 获取错误信息
     * @return
     */
    String getMessage();

    /**
     * 获取对应的错误码
     * @return
     */
    Integer getCode();
}

