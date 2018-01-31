/*
 * Versione dell'applicazione in cui lo stream output è gestito con un oggetto
 * PrintWriter e lo stream di input con BufferedReader
 */
package TCPConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Paolo Speziali
 */
public class ServerTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // porta del server maggiore di 1024 
        int port=2000;
        //oggetto ServerSocket necessario per accettare richieste dal client
        ServerSocket sSocket = null;
        //oggetto da usare per realizzare la connessione TCP
        Socket connection;

        while(true){
            try{
                // il server si mette in ascolto sulla porta voluta
                sSocket = new ServerSocket(port);
                System.out.println("In attesa di connessioni!");
                //si è stabilita la connessione
                connection = sSocket.accept();
                System.out.println("Connessione stabilita!");
                System.out.println("Socket server: " + connection.getLocalSocketAddress());
                System.out.println("Socket client: " + connection.getRemoteSocketAddress());
                // stream in input (BufferedReader) e in output (PrintWriter)
                // che ci permetteranno di scambiare messaggi tra client e server
                Scanner in = new Scanner(connection.getInputStream());
                PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
                String fromClient = null;
                // quando arriva un messaggio dal client, e quindi lo stream non
                // è vuoto, scrivo sullo stream di output l'orario
                while ((fromClient = in.nextLine()) != null) {
                    System.out.println(fromClient);
                    if(fromClient.equals("orario")){
                        out.println(getTime());
                    }
                    break;
                }
            }
               catch(IOException e){
                   System.err.println("Errore di I/O!");
            }
            
            //chiusura della connessione con il client
            try {
                if (sSocket!=null) sSocket.close();
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
            System.out.println("Connessione chiusa!");
        }
      }
    
    static String getTime(){
        DateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss - dd/MM/yyyy");
        Date date = new Date();
        return (dateFormat.format(date));
    }
}
