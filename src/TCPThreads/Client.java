/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPThreads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Paolo Speziali
 */
public class Client extends Thread{
    
    final String ANSI_GREEN = "\u001B[32m";
    Socket connection;
    String serverAddress;
    int port;
    boolean exit;
    
    public Client(String serverAddress, int port){
        connection = null;
        this.serverAddress = serverAddress;
        this.port = port;
    }
    
    @Override
    public void run(){
        try{
            // Crea il socket per aprire una connessione TCP con il server
            exit = false;
            while(exit == false){
                connection = new Socket(serverAddress, port);
                System.out.println(ANSI_GREEN+"Connessione aperta");
                // Attraverso questo metodo avviene l'inserimento da parte
                // dell'utente del tipo di richiesta che vuole effettuare
                // sperando che il server supporti quel tipo
                String richiesta = userInput(1);
                // Attraverso questo metodo avviene l'inoltro della richiesta al
                // server sfruttando un semplice stream PrintWriter che scrive il
                // contenuto della richiesta nello stream di output del socket
                inviaServer(connection,richiesta);
                // Attraverso questo metodo il client attende la risposta del server
                // ciclando all'interno di una condizione finché nello stream di
                // input del socket non viene messo un valore dal server, a quel
                // punto il contenuto dello stream viene stampato a schermo
                riceviServer(connection);
            }
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
        finally{
            chiudi();
        }
    }
    
    public String userInput(int tipo){
        if(tipo == 1){
            System.out.println(ANSI_GREEN+"Digita il tipo della richiesta che vuoi effettuare (es. orario): ");
        }
        else {
            System.out.println(ANSI_GREEN+"Riconnettersi al server? (Y/n): ");
        }
        BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));
        String richiesta = null;
        try {
            richiesta = tastiera.readLine();
        } catch (IOException ex) {
            System.err.println("Errore nell'input da tastiera!");
        }
        return richiesta;
    }
    
    public void inviaServer(Socket connection, String richiesta){
        PrintWriter out = null;
        try {
            out = new PrintWriter(connection.getOutputStream(), true);
        } catch (IOException ex) {
            System.err.println("Errore di I/O!");
        }
        out.println(richiesta);
    }
    
    public void riceviServer(Socket connection){
        Scanner in = null;
        try {
            in = new Scanner(connection.getInputStream());
        } catch (IOException ex) {
            System.err.println("Errore di I/O!");
        }
        String fromServer = null;
        if ((fromServer = in.nextLine()) != null) {
            System.out.println(ANSI_GREEN+"Server: " + fromServer);
        }
        if(userInput(2).equals("n")){
            exit = true;
        }
    }
    
    public void chiudi(){
        // Chiusura della connessione con il server
        if (connection!=null) {
            try {
                connection.close();
                System.out.println(ANSI_GREEN+"Connessione chiusa!");
            } catch (IOException ex) {
                System.err.println("Errore nella chiusura della connessione!");
            }
        }
    }
    
}
