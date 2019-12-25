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
import java.util.List;

public class HandleClients extends Thread{
private final BufferedReader instream;
private final PrintWriter outstream;
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
                    String type = jsonObject.get("type").getAsString();
                    String id = jsonObject.get("id").getAsString();
                    switch (type) {
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
                            outstream.println(out1);
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
                            outstream.write(out2);
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
                            outstream.write(out3);
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
                            outstream.write(out4);
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
                            outstream.write(out5);
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
                            outstream.write(out6);
                            //concurrentRequests++;
                            break;
                        default:
                            //need to write default
                            break;
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
