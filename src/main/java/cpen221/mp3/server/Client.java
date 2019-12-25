package cpen221.mp3.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        try{
            Scanner scn = new Scanner(System.in);

            InetAddress ip = InetAddress.getLocalHost();

            //establish the connection with the server port 5056?
            //why is the port number 5056?
            Socket s = new Socket(ip, 5056);

            DataInputStream inputstream = new DataInputStream(s.getInputStream());
            DataOutputStream outputstream = new DataOutputStream(s.getOutputStream());

            //exchange of information between the client and handle clients:
            while(true){
                System.out.println(inputstream.readUTF());
                String tosend = scn.nextLine();
                outputstream.writeUTF(tosend);

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
