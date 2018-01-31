/*
 * Versione dell'applicazione in cui lo stream output è gestito con un oggetto
 * BufferedWriter e lo stream di input con BufferedReader
 */
package TCPConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.net.ConnectException;

/**
 *
 * @author Paolo Speziali
 */
public class ClientTCP {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //oggetto da usare per realizzare la connessione TCP
        Socket connection = null;
        //nome o IP del server
        String serverAddress = "localhost";
        //porta del server in ascolto
        int port = 2000;

        //apertura della connessione al server sulla porta specificata
        try{
            connection = new Socket(serverAddress, port);
            System.out.println("Connessione aperta");
            // richiesta all'utente del tipo della richiesta
            // (unica opzione funzionante: orario)
            System.out.println("Digita il tipo della richiesta che vuoi effettuare (es. orario): ");
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
            String richiesta=tastiera.readLine();
            // stream in input (BufferedReader) e in output (PrintWriter)
            // che ci permetteranno di scambiare messaggi tra client e server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            // invio la richiesta al server con BufferedWriter, il non funzionamento
            // era dato dal fatto che non aggiungevo un carattere di newLine alla fine
            // nemmeno io so perché ma funziona!
            out.write(richiesta);
            out.newLine();
            out.flush();
            String fromServer = null;
            // quando arriva un messaggio dal server, e quindi lo stream non
            // è vuoto, visulaizzo quanto ricevuto e esco dalla condizione ciclica
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                break;
            }
        }
        catch(ConnectException e){
            System.err.println("Server non disponibile!");
        }
        catch(UnknownHostException e1){
            System.err.println("Errore DNS!");
        }

        catch(IOException e2){//
            System.err.println(e2);
            e2.printStackTrace();
        }

        //chiusura della connnessione
        finally{
                try {
            if (connection!=null)
                {
                    connection.close();
                    System.out.println("Connessione chiusa!");
                }
            }
            catch(IOException e){
                System.err.println("Errore nella chiusura della connessione!");
            }
        }
    }
}
