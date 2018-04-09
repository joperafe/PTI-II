package routers;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;

public class rout1 {
    
    public void managePit(String [] strArray, HashMap<String, String> hashtable){
        
       String aux1 = strArray[0];
       String aux2 = strArray[1];
       
       if(hashtable.containsKey(aux1)){
           if(hashtable.containsValue(aux2)){
               
           }
           else{
               hashtable.put(aux1, aux2);
           }
       }
       else{
           hashtable.put(aux1, aux2);
       }
    }
            
    
    public static void main(String[] args) {
        
        ArrayList<String> enderecos = new ArrayList<>();
        enderecos.add("2001:0690:2280:0822::42/64");
        enderecos.add("2001:0690:2280:0820::d/126");
        enderecos.add("2001:0690:2280:0820::A/126");
        enderecos.add("2001:0690:2280:0820::11/126");
        /*for(String s : enderecos){
        System.out.print(enderecos);
        }*/
        
        HashMap<String, String> pit = new HashMap<String, String>();
        pit.put("Desporto", "2001:0690:2280:0821::2/128");
        
        HashMap<String, ArrayList<String>> cs = new HashMap<String, ArrayList<String>>();
        ArrayList<String> aux = new ArrayList<String>();
        DatagramSocket skt = null;
        
        try{
            
            skt = new DatagramSocket(9998);
            
            byte [] buffer = new byte [1000];
            InetAddress host = InetAddress.getLocalHost();//vai buscar ip do cliente
            String ip = host.getHostAddress(); //vai buscar ip do cliente
                DatagramPacket interesse = new DatagramPacket(buffer, buffer.length);
                
                skt.receive(interesse);
                //algoritmo ndn
                String [] arrayMsg = (new String(interesse.getData())).split(" ");
                byte [] b = (new String(interesse.getData())).getBytes();
                //for
                DatagramPacket b1 = new DatagramPacket(b,b.length, host, 9997); //creating packet
                skt.send(b1);
                
                byte [] sendMsg = (arrayMsg[0]+" server processed").getBytes();
                
                DatagramPacket reply = new DatagramPacket(sendMsg, sendMsg.length, interesse.getAddress(), 9997);
                
                skt.receive(reply);
                
                String [] arrayMsg2 = (new String(reply.getData())).split(" ");
                byte [] b2 = (new String(reply.getData())).getBytes();
                //for
                DatagramPacket b3 = new DatagramPacket(b2,b2.length, host, 9999); //creating packet
                skt.send(b3);
            
        }   
    
    catch(Exception ex) {
        
    }
    }
    
}
