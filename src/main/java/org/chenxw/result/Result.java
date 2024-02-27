package org.chenxw.result;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {

    public static final int SUCCESS_CODE = 20000;

    private int code;
    private T data;
    private String message;


    public static <T> Result<T> generateSuccess(T data){
        return new Result<>(SUCCESS_CODE, data, "");
    }

    public static <T> Result<T> generateError(int code, String message){
        return new Result<>(code, null, message);
    }
}
