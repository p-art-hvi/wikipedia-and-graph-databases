package cpen221.mp3.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cpen221.mp3.wikimediator.WikiMediator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class HandleClients extends Thread{
private final BufferedReader instream;
private final PrintWriter outstream;
private String response;
private String status;
private String id;
private String type;
private int timeout;
    HandleClients(BufferedReader instream, PrintWriter outstream){
        this.instream = instream;
        this.outstream = outstream;
    }

    @Override
    public void run(){
        while(true) {
            try (BufferedReader reader = instream){
                Gson gson = new Gson();
                JsonParser jsonParser = new JsonParser();
                System.out.println("here 1");
                JsonArray jsonArray = (JsonArray) jsonParser.parse(instream);
                for (Object o : jsonArray) {
                    JsonObject jsonObject = (JsonObject) o;
                    type = jsonObject.get("type").getAsString();
                    id = jsonObject.get("id").getAsString();
                    if(jsonObject.get("timeout") != null){
                        timeout = jsonObject.get("timeout").getAsInt();
                    }
                    switch (type) {
                        case "simpleSearch":
                            //need to add time out
                            String query = jsonObject.get("query").getAsString();
                            int limit = jsonObject.get("limit").getAsInt();
                            List<String> response1 = WikiMediator.simpleSearch(query, limit);
                            response = response1.toString();
                            status = "success";
                            JsonObject output1 = new JsonObject();
                            output1.addProperty("id", id);
                            output1.addProperty("status", status);
                            output1.addProperty("response", response);
                            String out1 = output1.getAsString();
                            outstream.println(out1);
                            //concurrentRequests++;
                            break;
                        case "getPage":
                            //nee to add time out
                            String pageTitle = jsonObject.get("pageTitle").getAsString();
                            response = WikiMediator.getPage(pageTitle);
                            status = "success";
                            JsonObject output2 = new JsonObject();
                            output2.addProperty("id", id);
                            output2.addProperty("status", status);
                            output2.addProperty("response", response);
                            String out2 = output2.getAsString();
                            outstream.write(out2);
                            //concurrentRequests++;
                            break;
                        case "getConnectedPages":
                            //need to add time out
                            String pageTitle2 = jsonObject.get("pageTitle").getAsString();
                            int hops = jsonObject.get("hops").getAsInt();
                            List<String> response3 = WikiMediator.getConnectedPages(pageTitle2, hops);
                            response = response3.toString();
                            status = "success";
                            JsonObject output3 = new JsonObject();
                            output3.addProperty("id", id);
                            output3.addProperty("status", status);
                            output3.addProperty("response", response);
                            String out3 = output3.getAsString();
                            outstream.write(out3);
                            //concurrentRequests++;
                            break;
                        case "zeitgeist":
                            //need to add time out
                            int limit2 = jsonObject.get("limit").getAsInt();
                            List<String> response4 = WikiMediator.zeitgeist(limit2);
                            response = response4.toString();
                            status = "success";
                            JsonObject output4 = new JsonObject();
                            output4.addProperty("id", id);
                            output4.addProperty("status", status);
                            output4.addProperty("response", response);
                            String out4 = output4.getAsString();
                            outstream.write(out4);
                            //concurrentRequests++;
                            break;
                        case "trending":
                            //need to add time out
                            int limit3 = jsonObject.get("limit").getAsInt();
                            List<String> response5 = WikiMediator.trending(limit3);
                            response = response5.toString();
                            status = "success";
                            JsonObject output5 = new JsonObject();
                            output5.addProperty("id", id);
                            output5.addProperty("status", status);
                            output5.addProperty("response", response);
                            String out5 = output5.getAsString();
                            outstream.write(out5);
                            //concurrentRequests++;
                            break;
                        case "peakLoad30s":
                            //need to add time out
                            Integer response6 = WikiMediator.peakLoad30s();
                            response = response6.toString();
                            status = "success";
                            JsonObject output6 = new JsonObject();
                            output6.addProperty("id", id);
                            output6.addProperty("status", status);
                            output6.addProperty("response", response);
                            String out6 = output6.getAsString();
                            outstream.write(out6);
                            //concurrentRequests++;
                            break;
                        default:
                            //need to write default
                            status = "failed";
                            response = "Invalid client request";
                            JsonObject output7 = new JsonObject();
                            output7.addProperty("id", id);
                            output7.addProperty("status", status);
                            output7.addProperty("response", response);
                            String out7 = output7.getAsString();
                            outstream.write(out7);
                            break;
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
