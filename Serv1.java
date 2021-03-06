
package ndn;

import java.net.*;
import java.util.*;
import java.sql.Timestamp;
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
public class Serv1 {
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
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("lista");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                System.out.println("#########################");
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    fifo = Integer.parseInt(eElement.getElementsByTagName("fifo").item(0).getTextContent());
                    ttl = Integer.parseInt(eElement.getElementsByTagName("ttl").item(0).getTextContent());
                    System.out.println("Conteudo: " + fifo);
                    System.out.println("Conteudo: " + eElement.getElementsByTagName("ttl").item(0).getTextContent());
                    System.out.println("\n");
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
               //System.out.println("Ignoring Interface: " + ni.getDisplayName());
               continue;
            }
            Enumeration<InetAddress> addrs = ni.getInetAddresses();//devolve os INETaddresses associado a eth
            while (addrs.hasMoreElements()) {
               InetAddress addr = addrs.nextElement();
               //including addresses of the same type of group address
               if (group.getClass() != addr.getClass()) continue;
               if ((group.isMCLinkLocal() && addr.isLinkLocalAddress())
                  || (!group.isMCLinkLocal() && !addr.isLinkLocalAddress())) {
                  System.out.println("Interface que sera adicionada para envio: " + ni.getDisplayName() + " Address: " +addr);
                  result.add(addr);
               } else {
                  //System.out.println("Ignoring addr: " + addr + " of interface "
 //+ ni.getDisplayName());
               }
            }
         }
      } catch (SocketException ex) {
          System.out.println("Error: " + ex);
      }
      return result;
   }
    
    public static List<InetAddress> obtainEth(InetAddress group) {
      //List<InetAddress> result = new ArrayList<InetAddress>();
      List<InetAddress> result2 = new ArrayList<InetAddress>();
      System.out.println("\nObtain valid ETH");
      //verify if group is a multicast address
      try {
      //obtain the network interfaces list
         Enumeration<NetworkInterface> ifs = NetworkInterface.getNetworkInterfaces();
         while (ifs.hasMoreElements()) {
            NetworkInterface ni = ifs.nextElement();
            Enumeration<InetAddress> addrs = ni.getInetAddresses();//devolve os INETaddresses associado a eth
            while (addrs.hasMoreElements()) {
               InetAddress interf = addrs.nextElement();
               //including addresses of the same type of group address
               //if (group.getClass() != addr.getClass()) continue;
               if ((ni.getName().equals("eth0"))) { //alterar em cada router
                  //System.out.println("Interface: " + ni.getDisplayName() + " Address: " +addr);
                  //System.out.println("INDEX:" + ni.getName());
                  result2.add(interf); 
                  //InetAddress interf2 = interf; 
                  System.out.println("FOI ADICIONADO Interface: " + ni.getDisplayName() + " Address: " +interf);
                  
               } else {
                  //System.out.println("Ignoring addr: " + interf + " of interface "
 //+ ni.getDisplayName());
               }
            }
         }
      } catch (SocketException ex) {
          System.out.println("Error: " + ex);
      }
      return result2;
   }
    
    public static boolean verificaCSI(String [] arrayMsg, LinkedHashMap<String,String> y){

        if(y.containsKey(arrayMsg[1])){
            return true;
        }
        else{
            return false;
        }
    }


   public static void main(String[] args) throws Exception 
   {
       vars variables = configfiles();
       int fifo = variables.getFifo();
       int ttl = variables.getTTL();
       //Ler noticia do XML
      LinkedHashMap<String,String> cs = new LinkedHashMap<String,String>(fifo, (float) 0.75, true);
        try {

            File fXmlFile = new File("C:/Users/Asus/Desktop/XML/lista.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("tema");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    cs.put(eElement.getAttribute("id"),eElement.getElementsByTagName("conteudo").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
      }
       
      //InetAddress group = InetAddress.getByName("FF15::1:2:3");
      InetAddress group = InetAddress.getByName("FF02::1");
      
      List<InetAddress> addrs = obtainValidAddresses(group);
      for (InetAddress addr: addrs) {
         System.out.println("Address " + addr);
      }
      
      List<InetAddress> interf = obtainEth(group);
      for(InetAddress coiso : interf) {
            System.out.println("Interface de saida: " + coiso.getAddress() + " INDEX: " + interf.indexOf(coiso.getAddress()));
        }
      
      InetAddress eth = interf.get(0);
      
      String reply;
      String mensagem;
      
      MulticastSocket ms = new MulticastSocket(9999);
      DatagramSocket ds = new DatagramSocket(9874, eth);
      ms.joinGroup(group);
      System.out.println("interfaces deste router: " + NetworkInterface.getNetworkInterfaces());
      byte[] buffer = new byte[8192];
      while(true){
         System.out.println("Waiting for a multicast message sent to FF02::1");
         DatagramPacket interesse = new DatagramPacket(buffer, buffer.length); 
         ms.receive(interesse); // recebe interesse agora precisas de o tratar
         String s = new String(interesse.getData(), 0, interesse.getLength());
         //String addr = new String(dp.getAddress().toString());
         System.out.println("Recebi interesse : " + s);
         String [] arrayMsg = (new String(interesse.getData())).split(" ");
         if(verificaCSI(arrayMsg, cs)){
                    
                    reply = cs.get(arrayMsg[1]);
                    String tema = arrayMsg[1];
                    Timestamp ttl = new Timestamp(new Date().getTime());
                    System.out.println("fui buscar a noticia ag vou devolver: " + reply);
                    mensagem = "d " + reply + " "+ arrayMsg[2] + " " + tema + " " + ttl;
                    DatagramPacket responde = new DatagramPacket(mensagem.getBytes(), mensagem.length(), group, 9874);
                    ds.send(responde);      
                }
         else{
             System.out.println("nao possuo a noticia!!!");
         }
      }
   }
    
}

