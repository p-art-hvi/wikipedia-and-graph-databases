package cpen221.mp3.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpen221.mp3.wikimediator.WikiMediator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.List;

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

    //make this private and non-static
    public static void handleClients(BufferedReader inStream, PrintWriter outStream) throws IOException {
     Thread thread = new Thread(new Runnable(){

          @Override
           public void run() {
              Gson gson = new Gson();

              try(BufferedReader reader = inStream){
                    JsonParser jsonParser = new JsonParser();
                    Object object = jsonParser.parse(inStream);
                    JsonObject jsonObject = (JsonObject) object;
                    String type = jsonObject.get("type").getAsString();
                    String id = jsonObject.get("id").getAsString();
                    switch(type){
                        case "simpleSearch":
                            String query = jsonObject.get("query").getAsString();
                            Integer limit = jsonObject.get("limit").getAsInt();
                            List<String> response = WikiMediator.simpleSearch(query, limit);
                            String status = "success";
                            JsonObject output = new JsonObject();
                            output.addProperty("id", id);
                            output.addProperty("status", status);
                            output.addProperty("response", response.toString());
                            String out = output.getAsString();
                            outStream.write(out);
                            break;
                        case "getPage":

                            break;
                        case "getConnectedPages":

                            break;
                        case "zeitgeist":

                            break;
                        case "trending":

                            break;
                        case "mostCommon":

                            break;
                        case "peakLoad30s":

                            break;
                        default:
                            break;
                    }
              }catch (IOException e){
                  e.printStackTrace();
              }
              String line;
              while(true){
                  try {
                      if ((line = inStream.readLine()) == null) break;
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
    });
    }
}
