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
        //int concurrentRequests = 0;
        PrintWriter outStream = null;
        BufferedReader inStream = null;

        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client is connected : " + clientSocket);
                //outStream = new PrintWriter(new FileWriter("output.json"));
                inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                DataOutputStream outStream1 = new DataOutputStream(clientSocket.getOutputStream());
                System.out.println("Assigning new thread for this client");
                Thread thread = new HandleClients(inStream, outStream1);
                thread.start();
            }
            finally{
//                if(inStream!= null){
//                    inStream.close();
//                }
//                if(outStream!= null){
//                    outStream.close();
//                }
            }
            //concurrentRequests++;
        }

    }

    public static void main(String[] args) throws IOException {
        WikiMediatorServer server = new WikiMediatorServer(5056, 5);
    }
    /*
    public static void handleClients(BufferedReader inStream, PrintWriter outStream) throws IOException {
              Gson gson = new Gson();
              //int concurrentRequests = 0;
              try(BufferedReader reader = inStream){
                    JsonParser jsonParser = new JsonParser();
                    System.out.println("here 1");
                    JsonArray jsonArray = (JsonArray) jsonParser.parse(inStream);
                    for(Object o: jsonArray){
                        JsonObject jsonObject = (JsonObject) o;
                        String type = jsonObject.get("type").getAsString();
                        String id = jsonObject.get("id").getAsString();
                        switch(type){
                            case "simpleSearch":
                                String query = jsonObject.get("query").getAsString();
                                int limit = jsonObject.get("limit").getAsInt();
                                List<String> response1 = WikiMediator.simpleSearch(query, limit);
                                String status1 = "success";
                                JsonObject output1 = new JsonObject();
                                output1.addProperty("id", id);
                                output1.addProperty("status", status1);
                                output1.addProperty("response", response1.toString());
                                String out1 = output1.toString();
                                outStream.println(out1);
                                //concurrentRequests++;
                                break;
                            case "getPage":
                                String pageTitle = jsonObject.get("pageTitle").getAsString();
                                String response2 = WikiMediator.getPage(pageTitle);
                                String status2 = "success";
                                JsonObject output2 = new JsonObject();
                                output2.addProperty("id", id);
                                output2.addProperty("status", status2);
                                output2.addProperty("response", response2.toString());
                                String out2 = output2.getAsString();
                                outStream.write(out2);
                                //concurrentRequests++;
                                break;
                            case "getConnectedPages":
                                String pageTitle2 = jsonObject.get("pageTitle").getAsString();
                                int hops = jsonObject.get("hops").getAsInt();
                                List<String> response3 = WikiMediator.getConnectedPages(pageTitle2, hops);
                                String status3 = "success";
                                JsonObject output3 = new JsonObject();
                                output3.addProperty("id", id);
                                output3.addProperty("status", status3);
                                output3.addProperty("response", response3.toString());
                                String out3 = output3.getAsString();
                                outStream.write(out3);
                                //concurrentRequests++;
                                break;
                            case "zeitgeist":
                                int limit2 = jsonObject.get("limit").getAsInt();
                                List<String> response4 = WikiMediator.zeitgeist(limit2);
                                String status4 = "success";
                                JsonObject output4 = new JsonObject();
                                output4.addProperty("id", id);
                                output4.addProperty("status", status4);
                                output4.addProperty("response", response4.toString());
                                String out4 = output4.getAsString();
                                outStream.write(out4);
                                //concurrentRequests++;
                                break;
                            case "trending":
                                int limit3 = jsonObject.get("limit").getAsInt();
                                List<String> response5 = WikiMediator.trending(limit3);
                                String status5 = "success";
                                JsonObject output5 = new JsonObject();
                                output5.addProperty("id", id);
                                output5.addProperty("status", status5);
                                output5.addProperty("response", response5.toString());
                                String out5 = output5.getAsString();
                                outStream.write(out5);
                                //concurrentRequests++;
                                break;
                            case "peakLoad30s":
                                int response6 = WikiMediator.peakLoad30s();
                                String status6 = "success";
                                JsonObject output6 = new JsonObject();
                                output6.addProperty("id", id);
                                output6.addProperty("status", status6);
                                output6.addProperty("response", Integer.toString(response6));
                                String out6 = output6.getAsString();
                                outStream.write(out6);
                                //concurrentRequests++;
                                break;
                            default:
                                //need to write default
                                break;
                        }
                    }
              }catch (IOException e){
                  e.printStackTrace();
              }
              //return concurrentRequests;
          }
          */
    }
