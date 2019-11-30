package cpen221.mp3.server;

import com.google.gson.JsonObject;

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
    public WikiMediatorServer(int port, int n) {

    }

}
