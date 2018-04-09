package server;

import java.io.*;
import java.net.*;

public class Server2 {
    public static void main(String[] args) {
        
        DatagramSocket skt = null;
        
        try{
            
            skt = new DatagramSocket(9999);
            
            byte [] buffer = new byte [1000];
            
            while(true) {
                DatagramPacket interesse = new DatagramPacket(buffer, buffer.length);
                
                skt.receive(interesse);
                
                String [] arrayMsg = (new String(interesse.getData())).split(" ");
                
                byte [] sendMsg = (arrayMsg[0]+" server processed").getBytes();
                
                DatagramPacket reply = new DatagramPacket(sendMsg, sendMsg.length, interesse.getAddress(), interesse.getPort());
                
                skt.send(reply);
                
            }
        }
            
    catch(Exception ex) {
        
    }
    }
    
}
