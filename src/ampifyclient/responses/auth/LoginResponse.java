package ampifyclient.responses.auth;

import ampifyclient.responses.Response;
import ampifyclient.responses.ResponseCode;

public class LoginResponse extends Response {
    private String userId;
    public LoginResponse(ResponseCode code, String message){
        super(code,message);
    }
    public LoginResponse(ResponseCode code, String message, String userId) {
        super(code, message);
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId='" + userId + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
