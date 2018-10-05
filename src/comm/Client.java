package comm;

import java.net.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import game.Game;
import json_parse.Parse;
import linkedlist.LinkedList;
import matrix.Matrix;
import people.Player;
import json_conversion.Conversion;

import java.io.*;

/**
 * Administrates communication to and from server.
 *
 * @author Daniel Sing
 *
 */
public class Client {
    private Socket socket;
    private Game game;
    final private int rows = 5;
    final private int columns = 9;
    final private int initialValue = 0;

	/**
	 * Receives as parameters the address and port to locate server.
	 * and creates new socket
     *
	 * @param serverAddress
	 * @param serverPort
	 * @throws Exception
	 */
    public Client(String serverAddress, int serverPort)throws Exception{
    	this.socket = new Socket(serverAddress, serverPort);
    	this.game = new Game(this.rows, this.columns, this.initialValue);
    }

    
    
    private void start() throws IOException, ParseException {

    }

    /**
     * Reads incoming file and attempts to read it and form a JSONObject instance.
     *
     * @throws IOException
     * @throws ParseException
     */
    private void receiveJson(Game game, LinkedList pMouse) throws IOException, ParseException{
    	 BufferedReader brs = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    	  try{
    		  
    	      
    		  
            String JsonString = brs.readLine();
            System.out.println(JsonString);
            JSONParser parserS = new JSONParser();
            JSONObject json = (JSONObject) parserS.parse(new InputStreamReader(new FileInputStream("matrixAsJson.json"))); //de String a Json
            System.out.println(JsonString);
            Parse parserM = new Parse();
            
              JSONObject obj = game.getGameState(pMouse);
              Parse parser = new Parse();
//              LinkedList list = parser.JsonToGameState(obj);
//              Matrix matrix = (Matrix) list.getHead().getData();
//              matrix.printMatrix();
//            
      		LinkedList list = parserM.JsonToGameState(obj);
      		game.updateGameState(list);
            
    	  } 
    	  catch (UnknownHostException ex) {
    	   System.out.println("Server not found: " + ex.getMessage());
    	  } 
    	  catch (IOException ex) {
    	   System.out.println("I/O error: " + ex.getMessage());
    	  }
    	  this.sendJson(game, pMouse);
    }

    /**
     * Sends .json file to the server.
     * @throws ParseException 
     * @throws IOException 
     */
    private void sendJson(Game game, LinkedList pMouse) throws IOException, ParseException {
        JSONObject obj = game.getGameState(pMouse);

          try {
           PrintStream ps = new PrintStream(socket.getOutputStream());
           ps.println("Client: " + obj);
           // socket.close();
          } catch (IOException ex) {
           System.out.println("Server exception: " + ex.getMessage());
           ex.printStackTrace();
          }
          this.receiveJson(game, pMouse);
    	}
    
    /**
     * Initializes client
     *
     * @param args
     * @throws Exception
     */
}