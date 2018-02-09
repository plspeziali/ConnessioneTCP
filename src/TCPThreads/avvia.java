/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPThreads;

/**
 *
 * @author Paolo Speziali
 */
public class avvia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Server s = new Server(2000);
        Client c = new Client("localhost",2000);
        s.start();
        c.start();
    }
    
}
