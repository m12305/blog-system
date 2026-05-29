package com.my_project.bolg_system.model;

import lombok.Data;

@Data
public class Result <T>{

    private int code;
    private String errMsg;
    private T data;
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<T>();
        //result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setCode(200);
        result.setData(data);
        return result;
    }
    public static <T> Result<T> fail(String errMsg){
        Result<T> result = new Result<T>();
        //result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setCode(-1);
        result.setErrMsg(errMsg);
        return result;
    }
}
