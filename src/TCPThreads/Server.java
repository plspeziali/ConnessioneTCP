/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPThreads;

import static TCPConnection.ServerTCP.inviaRisposta;
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
 * @author paolo
 */
public class Server extends Thread{
    
    int port;
    ServerSocket sSocket = null;
    Socket connection;
    
    public Server(int port){
        this.port=port;
    }
    @Override
    public void run(){
        while(true){
            try{
                // Il server si mette in ascolto sulla porta voluta
                sSocket = new ServerSocket(port);
                System.out.println("In attesa di connessioni!");
                // Si stabilisce la connessione con il client
                connection = sSocket.accept();
                System.out.println("Connessione stabilita!");
                System.out.println("Socket server: " + connection.getLocalSocketAddress());
                System.out.println("Socket client: " + connection.getRemoteSocketAddress());
                // Attraverso questo metodo invia la risposta al client
                inviaRisposta(connection);
            } catch(IOException e){
                   System.err.println("Errore nell'apertura del Socket!");
            }
            chiudi();
        }
    }
    
    public static void inviaRisposta(Socket connection){
        try {
            // Apertura degli stream in input (Scanner) e in Output (PrintWriter)
            Scanner in = new Scanner(connection.getInputStream());
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            String fromClient = null;
            // Quando il client invia un messaggio, quindi scrive nello stream
            // di input del Server, si entra in questa condizione in cui il
            // server scrive, a seconda della risposta, un messaggio nel
            // suo stream di output per farlo arrivare al Client
            if ((fromClient = in.nextLine()) != null) {
                System.out.println(fromClient);
                if(fromClient.equals("orario")){
                    out.println(getTime());
                }
                else {
                    out.println("Funzione non supportata al momento!");
                }
            }
        } catch (IOException ex) {
            System.err.println("Errore di I/O!");
        }
    }
    
    public static String getTime(){
        DateFormat dateFormat = new SimpleDateFormat(" HH:mm:ss - dd/MM/yyyy");
        Date date = new Date();
        return (dateFormat.format(date));
    }
    
    public void chiudi(){
        // Chiusura della connessione con il client
        try {
            if (sSocket!=null) sSocket.close();
            System.out.println("Connessione chiusa!");
        } catch (IOException ex) {
            System.err.println("Errore nella chiusura della connessione!");
        }
    }
}
