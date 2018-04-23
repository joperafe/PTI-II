
package ndn;

import java.net.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
/**
 *
 * @author andre
 */
public class Router4 {
        static final class vars {
        private final int fifo;
        private final int ttl;
        public vars(int fifo, int ttl) {
            this.fifo = fifo;
            this.ttl = ttl;
        }
        public int getFifo() {
            return fifo;
        }
        public int getTTL() {
            return ttl;
        }
    }
    public static vars configfiles(){
        int fifo = 0;
        int ttl = 0;
        try {
            File fXmlFile = new File("C:/Users/Asus/Desktop/XML/configfile.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("lista");
            //System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                //System.out.println("#########################");
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    fifo = Integer.parseInt(eElement.getElementsByTagName("fifo").item(0).getTextContent());
                    ttl = Integer.parseInt(eElement.getElementsByTagName("ttl").item(0).getTextContent());
                    //System.out.println("Conteudo: " + fifo);
                    //System.out.println("Conteudo: " + eElement.getElementsByTagName("ttl").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new vars(fifo, ttl);
    }
        
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
       var variables = configfiles();
       int fifo = variables.getFifo();
       int ttl = variables.getTTL();
       LinkedHashMap<String,String> cs = new LinkedHashMap<String,String>(fifo, (float) 0.75, true);
       Map<String, List<String>> pit = new HashMap<String, List<String>>(); 
       
      //InetAddress group = InetAddress.getByName("FF15::1:2:3");
      InetAddress group = InetAddress.getByName("FF02::1");
      String sucateiro = " ";
      /*List<InetAddress> addrs = obtainValidAddresses(group);
      for (InetAddress addr: addrs) {
         System.out.println("Address " + addr);
      }*/
      MulticastSocket ms = new MulticastSocket(9999);
      ms.joinGroup(group);
      byte[] buffer = new byte[8192];
      while(true){
         System.out.println("Waiting for a multicast message sent to FF02::1");
         DatagramPacket interesse = new DatagramPacket(buffer, buffer.length); 
         ms.receive(interesse);
         String [] arrayMsg = (new String(interesse.getData())).split(" ");
         
         String s = new String(interesse.getData(), 0, interesse.getLength());
         //faz o que tem a fazer ao interesse
         
         String addressi = new String(interesse.getAddress().toString());
         System.out.println("Receive message " + s + "from " + addressi);
         String teste = "teste bem sucedido";
         byte [] sendmsg = teste.getBytes();
         DatagramPacket encaminha = new DatagramPacket(sendmsg, sendmsg.length, group, 9999);
         for(int i = 0; i<arrayMsg.length;i++){
             System.out.println("elemento "+ i + ": " + arrayMsg[i]);
         }
         if(!arrayMsg[5].equals(sucateiro)){//novo pacote
            if(arrayMsg[0].equals("i")) {
                //algoritmo para o pacote de interesse
            }
            if(arrayMsg[0].equals("d")) {
                //algoritmo para o pacote de dados
            }
            List<InetAddress> addrs = obtainValidAddresses(group);
            for (InetAddress addr: addrs) {
                System.out.println("A encaminhar" + addr + "myip: " + encaminha.getAddress());
                ms.setInterface(addr);
                ms.send(encaminha);
            }
           }
        sucateiro = arrayMsg[5];
        System.out.println("sucateiro" + sucateiro);
      }
   }
    
}
