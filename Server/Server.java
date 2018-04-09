package server;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        
        DatagramSocket skt = null;
        
        try{
            
            skt = new DatagramSocket(9997);
            String finale = "A NOTICIA E QUE PORTO e m4da";
                byte [] buffer = new byte [1000];
            
                DatagramPacket interesse = new DatagramPacket(buffer, buffer.length);
                
                skt.receive(interesse);
                
                String [] arrayMsg = (new String(interesse.getData())).split(" ");
                
                byte [] sendMsg = finale.getBytes();
                
                DatagramPacket reply = new DatagramPacket(sendMsg, sendMsg.length, interesse.getAddress(), 9998);
                
                skt.send(reply);
                
        }   
    
    catch(Exception ex) {
        
    }
    }
    
}
