/*
 * Versione dell'applicazione in cui lo stream output è gestito con un oggetto
 * PrintWriter e lo stream di input con Scanner
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
        // Indirizzo di rete e porta sono inseriti staticamente
        String serverAddress = "localhost";
        int port = 2000;
        try{
            // Crea il socket per aprire una connessione TCP con il server
            connection = new Socket(serverAddress, port);
            System.out.println("Connessione aperta");
            // Attraverso questo metodo avviene l'inserimento da parte
            // dell'utente del tipo di richiesta che vuole effettuare
            // sperando che il server supporti quel tipo
            String richiesta = userInput();
            // Attraverso questo metodo avviene l'inoltro della richiesta al
            // server sfruttando un semplice stream PrintWriter che scrive il
            // contenuto della richiesta nello stream di output del socket
            inviaServer(connection,richiesta);
            // Attraverso questo metodo il client attende la risposta del server
            // ciclando all'interno di una condizione finché nello stream di
            // input del socket non viene messo un valore dal server, a quel
            // punto il contenuto dello stream viene stampato a schermo
            riceviServer(connection);
        } catch (ConnectException e){
            System.err.println("Server non disponibile!");
        }
        catch(UnknownHostException e1){
            System.err.println("Errore DNS!");
        }
        catch(IOException e2){//
            System.err.println(e2);
            e2.printStackTrace();
        }
        
        // Chiusura della connessione con il server
        
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
