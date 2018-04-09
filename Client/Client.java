/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.*;
import java.net.*;

public class Client {
    
    public static void main (String[] args) {
        
        DatagramSocket skt;
        
        try{
            
            skt = new DatagramSocket(9999);            
               
            InetAddress host = InetAddress.getLocalHost();//vai buscar ip do cliente
            String ip = host.getHostAddress(); //vai buscar ip do cliente
            String msg = "Economia " + ip;
            System.out.println("msg: " + msg);
            byte [] b = msg.getBytes();
                 
            //System.out.println("ip: " + host);
            DatagramPacket interesse = new DatagramPacket(b,b.length, host, 9998); //creating packet
            skt.send(interesse);
            
            byte [] buffer = new byte [1000];
            
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            
            skt.receive(reply);
            
            System.out.println("Recebeu noticia " + new String(reply.getData()));
            
            skt.close();    
            
            
        }
        catch(Exception ex){
        }
    }
}
