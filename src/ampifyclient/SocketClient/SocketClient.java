package ampifyclient.SocketClient;

import commonPackages.requests.Request;
import commonPackages.responses.Response;
import ampifyclient.runnable.HandleRequest;
import ampifyclient.runnable.HandleResponse;

import java.io.IOException;
import java.net.Socket;

public class SocketClient {
    static Socket socket;
    static Response response;

    public static void sendRequestObject(Request request) {
        try {
            socket = new Socket("localhost", 5555);
            Thread thread = new Thread(new HandleRequest(socket, request));
            thread.start();
        } catch (IOException e) {
            System.out.print("Request Send Unsuccessfully :::: " + request +
                    "\nError has Occured :::: " + e);
            e.printStackTrace();
        }
    }

    public static Response getResponseObject() {
        try {
            socket = new Socket("localhost", 5555);
            HandleResponse handleResponse=new HandleResponse(socket);
            Thread thread=new Thread(handleResponse);
            thread.start();
            response=handleResponse.getResponse();
        } catch (IOException e) {
            response=null;
            System.out.println("No Response Is Recieved \nError Has Occured :::  "+e);
            e.printStackTrace();
        }
        return response;
    }
}