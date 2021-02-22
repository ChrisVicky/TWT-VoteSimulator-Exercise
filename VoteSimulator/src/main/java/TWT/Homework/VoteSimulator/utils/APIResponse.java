package TWT.Homework.VoteSimulator.utils;

import TWT.Homework.VoteSimulator.entity.UserSchema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {

    private int ErrorCode;
    private String Message;
    private Object Result;
    public APIResponse(int error_Code, String message){
        this.ErrorCode = error_Code;
        this.Message = message;
    }
    public APIResponse(int error_code, Object result){
        this.ErrorCode = error_code;
        this.Result = result;
    }
    public APIResponse(String message, Object result){
        this.Message = message;
        this.Result = result;
    }

    public static APIResponse error(int code, String message){
        return new APIResponse(code,message,null);
    }

    public static APIResponse success(Object result){
        return new APIResponse(0,"OK",result);
    }

}
