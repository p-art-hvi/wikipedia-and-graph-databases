package cpen221.mp3.server;

import com.google.gson.JsonParser;

import javax.sound.sampled.Port;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        try{
            Scanner scn = new Scanner(System.in);

            //establish the connection with the server port 5056?
            //why is the port number 5056?

            //String port = scn.nextLine();
            //Integer portNum = Integer.parseInt(port);
            Socket s = new Socket("0.0.0.0", 5056);

            String input ="{\"id\":\"1\",\"type\":\"simpleSearch\",\"query\":\"Frozen\",\"limit\":\"12\"}";

            DataOutputStream outputstream = new DataOutputStream(s.getOutputStream());
            DataInputStream inputstream = new DataInputStream(s.getInputStream());


//            JsonParser jsonParser = new JsonParser();
//            File file = new File("C:\\jsonFiles\\input.json");
//            FileReader reader = new FileReader(file);
//            BufferedReader outputstream = new BufferedReader(reader);

            //exchange of information between the client and handle clients:
            while(true){
                String tosend = "Run";
                outputstream.writeUTF(input);

                if(tosend.equals("Exit")){
                    System.out.println("Closing this connection: " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }
                String recieved = inputstream.readUTF();
                System.out.println(recieved);
            }
            scn.close();
            inputstream.close();
            outputstream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
