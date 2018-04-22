/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ndn;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.Date;

/**
 *
 * @author andre
 */
public class Router7 {
    
    //public static final String MULTICAST_ADDRESS = "FF15::1:2:3";
   public static final String MULTICAST_ADDRESS = "FF02::1";
   public static final int MULTICAST_PORT = 9999;

   public static List<InetAddress> obtainValidAddresses(InetAddress group) {
      List<InetAddress> result = new ArrayList<InetAddress>();


      System.out.println("\nObtain valid addresses according to group address");
      //verify if group is a multicast address
      if (group == null || !group.isMulticastAddress()) return result;
      try {
      //obtain the network interfaces list
         Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
         while (ifs.hasMoreElements()) {
            NetworkInterface ni = ifs.nextElement();
          //ignoring loopback, inactive interfaces and the interfaces that do not support multicast
            if (ni.isLoopback() || !ni.isUp() || !ni.supportsMulticast()) {
               //System.out.println("Ignoring Interface: " + ni.getDisplayName());
               continue;                     
            }
            Enumeration<InetAddress> addrs = ni.getInetAddresses();
            while (addrs.hasMoreElements()) {
               InetAddress addr = addrs.nextElement();
               //including addresses of the same type of group address
               if (group.getClass() != addr.getClass()) continue;
               if ((group.isMCLinkLocal() && addr.isLinkLocalAddress())
                  || (!group.isMCLinkLocal() && !addr.isLinkLocalAddress())) {
                  //System.out.println("Interface: " + ni.getDisplayName() + " Adress: " +addr);
                  result.add(addr);
               } else {
                  //System.out.println("Ignoring addr: " + addr + " of interface " + ni.getDisplayName());
               }
            }
         }
      } catch (SocketException ex) {
          System.out.println("Error: " + ex);
      }
      return result;
   }

   public static void main(String[] args) throws Exception {

      InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
      String noticia = args[0];
      String endereco = "2001:0690:2280:0821::2";
      String aux = " ";
      String tipo = "i ";
      //String id =
      Timestamp ttl = new Timestamp(new Date().getTime());//ttl de 6 horas max 16-04-2018 16:30:23:213
      System.out.println("\n noticia: " + noticia);
      
      String msg = tipo + noticia + aux + endereco + aux + ttl;
      System.out.println("\nSending by multicast the message: " + msg);
      //InetAddress address = InetAddress.getLocalHost(); 
      //String hostIP = address.getHostAddress() ;
      //System.out.println("this is my host ip: " + hostIP);
      MulticastSocket ms = new MulticastSocket();
      DatagramSocket ds = new DatagramSocket(9877);
      DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), group, MULTICAST_PORT);
        
      List<InetAddress> addrs = obtainValidAddresses(group);
      for (InetAddress addr: addrs) {
         System.out.println("Sending on " + addr + "myip: " + dp.getAddress());
         ms.setInterface(addr);
         ms.send(dp);
      }
      
      while(true) {
          
          byte[] buffer = new byte[256];
          DatagramPacket reply = new DatagramPacket(buffer, buffer.length);            
          ds.receive(reply);
          String [] arrayMsg = (new String(reply.getData())).split(" ");
          //if(arrayMsg[0].equals("d") && arrayMsg[2].equals(anterior)) {             
          
            System.out.println("Recebeu noticia " + new String(reply.getData())); 
            //FALTA METER NA CS DESTE NÓ
            break;
          //}
       
   }
      ms.close();
      ds.close();
    }
}

