package cpen221.mp3.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

//import org.json.simple.parser.JSONParser;

public class WikiMediatorServer {

    /*
    Representation Invariant:
    -- requests must not be null
    -- response must not be null

    Abstraction Function:
    -- request represents a JsonObject containing the id, type, and other parameters dependant on the type.
       can contain an optional timeout
    -- response represents a JsonObject containing the id, status, and response if the status is "success".
       the "response" is the return value of the "type" from the request
     */

    private JsonObject request;
    private JsonObject response;

    /**
     * @param port the port number to bind the server to
     * @param n the number of concurrent requests the server can handle
     * effects: Start a server at a given port number, with the ability to process
     *          up to n requests concurrently.
     */
    public WikiMediatorServer(int port, int n) throws IOException {
        int concurrentRequests = 0;
        PrintWriter outStream = null;
        BufferedReader inStream = null;

        while (concurrentRequests < n) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                outStream = new PrintWriter(new FileWriter("output.json"));
                inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                handleClients(inStream, outStream);
            }
            finally{
                if(inStream!= null){
                    inStream.close();
                }
                if(outStream!= null){
                    outStream.close();
                }
            }
            concurrentRequests++;
        }

    }

    private void handleClients(BufferedReader inStream, PrintWriter outStream) throws IOException {
        Thread thread = new Thread(new Runnable(){

            @Override
            public void run() {
                try{
                    JsonParser jsonParser = new JsonParser();
                  //  Gson gson = new Gson();
                  //  DataInput gsonObject = gson.fromJson(inStream, DataInput.class);
                    //String json = gson.toJson(inStream);

                    JsonArray requestArray = (JsonArray) jsonParser.parse(inStream);
                    for(Object o: requestArray){
                        JsonObject request = (JsonObject) o;
                        String id = (String) request.get("id");
                    }
                } catch (IOException | ParseException e){
                    e.printStackTrace();
                }


            }

            /*
                String line;
                while(true){
                    try {
                        if ((line = inStream.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/
        });
    }



}
