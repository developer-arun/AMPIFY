package Networking;

import ampifyServer.Song;
import ampifyServer.requests.SendSongObjToseverRequest;
import ampifyServer.responses.Response;
import ampifyServer.responses.ResponseCode;
import ampifyServer.responses.SendSongObjToserverResponse;

import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import java.nio.file.Path;

public class SocketClient {
    public  static Response sendSongObject(SendSongObjToseverRequest request)
    {
        Song song= request.song;
        try {
            Socket socket = new Socket("localhost", 5555);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        //pass song object here
        System.out.println(song.toString());
        objectOutputStream.writeObject(song);
        objectOutputStream.flush();
        Response response=new SendSongObjToserverResponse(ResponseCode.SUCCESS,"SONG REQUEST SEND");

        Thread playThread=new Thread(new HandleSong());
        playThread.start();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SendSongObjToserverResponse(ResponseCode.FAILURE,"Song object not sent successsfully");
    }


}
