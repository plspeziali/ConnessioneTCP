/*
 * Versione dell'applicazione in cui lo stream output è gestito con un oggetto
 * PrintWriter e lo stream di input con BufferedReader
 */
package TCPConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Paolo Speziali
 */
public class ClientTCP {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Socket connection = null;
        try{
            connection = connetti("localhost", 2000);
            String richiesta = userInput();
            inviaServer(connection,richiesta);
            riceviServer(connection);
        }
        finally{
            if (connection!=null) {
                try {
                    connection.close();
                    System.out.println("Connessione chiusa!");
                } catch (IOException ex) {
                    System.out.println("Connessione chiusa!");
                }
            }
        }
    }
    
    public static Socket connetti(String serverAddress, int port){
        Socket connection = null;
        try {
            connection = new Socket(serverAddress, port);
        } catch(ConnectException e){
            System.err.println("Server non disponibile!");
        }
        catch(UnknownHostException e1){
            System.err.println("Errore DNS!");
        }
        catch(IOException e2){//
            System.err.println(e2);
            e2.printStackTrace();
        }
        System.out.println("Connessione aperta");
        return connection;
    }
    
    public static String userInput(){
        System.out.println("Digita il tipo della richiesta che vuoi effettuare (es. orario): ");
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
        String richiesta = null;
        try {
            richiesta = tastiera.readLine();
        } catch (IOException ex) {
            System.err.println("Errore nell'input da tastiera!");
        }
        return richiesta;
    }
    
    public static void inviaServer(Socket connection, String richiesta){
        PrintWriter out = null;
        try {
            out = new PrintWriter(connection.getOutputStream(), true);
        } catch (IOException ex) {
            System.err.println("Errore di I/O!");
        }
        out.println(richiesta);
    }
    
    public static void riceviServer(Socket connection){
        Scanner in = null;
        try {
            in = new Scanner(connection.getInputStream());
        } catch (IOException ex) {
            System.err.println("Errore di I/O!");
        }
        String fromServer = null;
        while ((fromServer = in.nextLine()) != null) {
            System.out.println("Server: " + fromServer);
            break;
        }
    }
}
