
package ndn;

import java.net.*;
import java.util.*;
/**
 *
 * @author andre
 */
public class Serv2 {
    
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
            //ignoringg loopback, inactive interfaces and the interfaces that do not support multicast
            if (ni.isLoopback() || !ni.isUp() || !ni.supportsMulticast()) {
               System.out.println("Ignoring Interface: " + ni.getDisplayName());
               continue;
            }
            Enumeration<InetAddress> addrs = ni.getInetAddresses();//devolve os INETaddresses associado a eth
            while (addrs.hasMoreElements()) {
               InetAddress addr = addrs.nextElement();
               //including addresses of the same type of group address
               if (group.getClass() != addr.getClass()) continue;
               if ((group.isMCLinkLocal() && addr.isLinkLocalAddress())
                  || (!group.isMCLinkLocal() && !addr.isLinkLocalAddress())) {
                  System.out.println("Interface: " + ni.getDisplayName() + " Address: " +addr);
                  result.add(addr);
               } else {
                  System.out.println("Ignoring addr: " + addr + " of interface "
 + ni.getDisplayName());
               }
            }
         }
      } catch (SocketException ex) {
          System.out.println("Error: " + ex);
      }
      return result;
   }


   public static void main(String[] args) throws Exception 
   {
      //InetAddress group = InetAddress.getByName("FF15::1:2:3");
      InetAddress group = InetAddress.getByName("FF02::1");

      List<InetAddress> addrs = obtainValidAddresses(group);
      for (InetAddress addr: addrs) {
         System.out.println("Address " + addr);
      }
      MulticastSocket ms = new MulticastSocket(9999);
      ms.joinGroup(group);
      byte[] buffer = new byte[8192];
      while(true){
         System.out.println("Waiting for a multicast message sent to FF02::1");
         DatagramPacket dp = new DatagramPacket(buffer, buffer.length); 
         ms.receive(dp);
         String s = new String(dp.getData(), 0, dp.getLength());
         String addr = new String(dp.getAddress().toString());
         System.out.println("Receive message " + s + " from " + addr  );
      }
   }
    
}

