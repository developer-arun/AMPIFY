package ampifyServer.server;

import ampifyServer.requestHandler.RequestHandler;
import ampifyServer.requestHandler.UserRequestsHandler;
import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.auth.LoginResponse;
import commonPackages.responses.auth.SignupResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final HashMap<String, Boolean> avalUsers;
    private final Connection con;
    private boolean isLoggedIn;
    public ClientHandler(
            Socket socket,
            ObjectInputStream ois,
            ObjectOutputStream oos,
            HashMap<String, Boolean> avalUsers,
            Connection con
    ){
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        this.avalUsers = avalUsers;
        this.con = con;
        this.isLoggedIn = false;
    }
    @Override
    public void run() {
        while (!isLoggedIn){
            try {
                Request req = (Request) this.getRequest();
                if(req instanceof LoginRequest)
                {
                    LoginResponse res = UserRequestsHandler.login((LoginRequest) req, con);
                    this.sendReponse(res);
                    if(res.getCode() == ResponseCode.SUCCESS){
                        isLoggedIn = true;
                        break;
                    }
                }
                else if (req instanceof SignupRequest)
                {
                    SignupResponse res = UserRequestsHandler.signup((SignupRequest) req,con);
                    this.sendReponse(res);
                    if(res.getCode() == ResponseCode.SUCCESS){
                        break;
                    }
                }
                else
                {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        while (true){
            try {
                Request req = (Request) this.getRequest();
                Response res = RequestHandler.getResponse(req,con);
                this.sendReponse(res);
                break;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        try {
            // closing resources
            this.ois.close();
            this.oos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendReponse(Response res) throws IOException {
        oos.writeObject(res);
        oos.flush();
    }
    public Object getRequest() throws IOException, ClassNotFoundException {
        Object req = ois.readObject();
        return req;
    }
}