
package ndn;

import java.net.*;
import java.sql.Timestamp;
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
public class Router6 {

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
               if ((ni.getName().equals("eth2"))) { //alterar em cada router
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
    
    /*public static boolean verificaTTLcs() {
        
    }
    public static boolean verificaTTLpit() {
        
    }*/
    public static boolean verificaPIT(String [] arrayMsg, Map<String, List<String>> x){
        
        if(x.containsKey(arrayMsg[1])) {
            return true;
        }
        else{
            return false;
        }
    }
    public static boolean verificaPITeth(String [] arrayMsg, Map<String, List<String>> x){
        if(arrayMsg[2].equals("2001:0690:2280:0821::2")) { //ARRANJAR ESTA CONDIÇÃO
            return true;
        }
        else {
            return false;
        }
        
    }

   public static void main(String[] args) throws Exception 
   {
       vars variables = configfiles();
       int fifo = variables.getFifo();
       int ttl = variables.getTTL();
       LinkedHashMap<String,String> cs = new LinkedHashMap<String,String>(3, (float) 0.75, true);
       List<String> interfaces = new ArrayList<String>();
       Map<String, List<String>> pit = new HashMap<String, List<String>>(); 
       
       cs.put("Benfica", "BENFICA ganha folego");
       interfaces.add("2001:0690:2280:0821::2");
       pit.put("Sporting", interfaces);
       
       System.out.println("------PIT------");
            for (Map.Entry<String, List<String>> entry : pit.entrySet()) {
                System.out.println("Interesse: " + entry.getKey() + "\tIP: " + entry.getValue());
            }
            
       System.out.println("------CS------");
            for (Map.Entry<String, String> entry_cs : cs.entrySet()) {
                System.out.println("Interesse: " + entry_cs.getKey() + "\tConteudo: " + entry_cs.getValue());
            }
       
      //InetAddress group = InetAddress.getByName("FF15::1:2:3");
      InetAddress group = InetAddress.getByName("FF02::1");
      //InetAddress group2 = InetAddress.getByName("FF02::1");
      String sucateiro = " ";
      String reply;
      String mensagem;
      /*List<InetAddress> addrs = obtainValidAddresses(group);
      for (InetAddress addr: addrs) {
         System.out.println("Address " + addr);
      }*/
      List<InetAddress> interf = obtainEth(group);
      for(InetAddress coiso : interf) {
            System.out.println("Interface de saida: " + coiso.getAddress() + " INDEX: " + interf.indexOf(coiso.getAddress()));
        }
      InetAddress eth = interf.get(1);
      System.out.println("IPPPPPP: " + eth);
      //System.out.println("FOI BUSCAR: " + interf.nextElement());
      MulticastSocket ms = new MulticastSocket(9999);
      DatagramSocket ds = new DatagramSocket(9877, eth);
      ms.joinGroup(group);
      byte[] buffer = new byte[256];
      while(true){
         System.out.println("Waiting for a multicast message sent to FF02::1");
         DatagramPacket interesse = new DatagramPacket(buffer, buffer.length); 
         ms.receive(interesse);
         String [] arrayMsg = (new String(interesse.getData())).split(" ");
         
         String s = new String(interesse.getData(), 0, interesse.getLength());
         //faz o que tem a fazer ao interesse
         
         //String addressi = new String(interesse.getAddress().toString());
         //System.out.println("Receive message " + s + "from " + addressi);
         //String teste = "teste bem sucedido";
         //byte [] sendmsg = teste.getBytes();
         //DatagramPacket encaminha = new DatagramPacket(sendmsg, sendmsg.length, group, 9999);
         /*for(int i = 0; i<arrayMsg.length;i++){
             System.out.println("elemento "+ i + ": " + arrayMsg[i]);
         }*/
         
         if(!arrayMsg[4].equals(sucateiro)){//novo pacote
            if(arrayMsg[0].equals("i")) {
                //algoritmo para o pacote de interesse
                if(verificaCSI(arrayMsg, cs)){
                    
                    reply = cs.get(arrayMsg[1]);
                    String tema = arrayMsg[1];
                    Timestamp ttl = new Timestamp(new Date().getTime());
                    System.out.println("fui buscar a noticia ag vou devolver: " + reply);
                    mensagem = "d " + reply + " "+ arrayMsg[2] + " " + tema + " " + ttl;
                    DatagramPacket responde = new DatagramPacket(mensagem.getBytes(), mensagem.length(), group, 9876);
                    ds.send(responde);
                    
                }
                else if(verificaPIT(arrayMsg, pit)) {
                    System.out.println("Tem o interesse!!!!!!!" + pit.get(arrayMsg[1]));
                    if(!verificaPITeth(arrayMsg, pit)){
                        pit.get(arrayMsg[1]).add(arrayMsg[2]);//adicona interface ao interesse
                        System.out.println("Associou ao interesse, a interface: " + pit.get(arrayMsg[1]));
                        
                    }
                    else{
                        System.out.println("Interesse e interface já registados!!!!!!! ");
                    }
                    
                }
                else if(!verificaPIT(arrayMsg, pit)) {
                    pit.put(arrayMsg[1], new ArrayList<>());
                    pit.get(arrayMsg[1]).add(arrayMsg[2]);
                    System.out.println("Foi adicionado o interesse: " + arrayMsg[1] + " associado à interface: " + arrayMsg[2]);
                    System.out.println("RENCAMINHAR");
                    String reencaminha = new String(interesse.getData());
                    DatagramPacket reencaminhaInt = new DatagramPacket(reencaminha.getBytes(), reencaminha.length(), group, 9999);
        
                    List<InetAddress> addrs = obtainValidAddresses(group);
                    for (InetAddress addr: addrs) {
                    System.out.println("Sending on " + addr);
                    ms.setInterface(addr);
                    ms.send(reencaminhaInt);
                    System.out.println("RENCAMINHOU");
                    }
                    
                    DatagramPacket dados = new DatagramPacket(buffer, buffer.length); 
                    int i=0;
                    while(true){
                    ms.receive(dados);
                    String [] data = (new String(dados.getData())).split(" ");
                    if(data[0].equals("d")){
                       System.out.println("Guarda na CS: ");
                       cs.put(data[3], data[1]);//tema -noticia
                       System.out.println("Vai reencaminhar"); 
                       String dados2 = new String(dados.getData());
                       DatagramPacket noticiario = new DatagramPacket(dados2.getBytes(), dados2.length(), group, 9876);
                       ds.send(noticiario);
                       System.out.println("RENCAMINHOU");
                       break;
                        }
                    }
                    System.out.println("SAIU DO WHILE");
                    
                }
            }
            if(arrayMsg[0].equals("d")) {
                //algoritmo para o pacote de dados
                System.out.println("NEM DEVERIA TER AQUI ENTRADO!!!!");
            }
            /*List<InetAddress> addrs = obtainValidAddresses(group);
            for (InetAddress addr: addrs) {
                System.out.println("A encaminhar" + addr + "myip: " + encaminha.getAddress());
                ms.setInterface(addr);
                ms.send(encaminha);
            }*/
           }
        sucateiro = arrayMsg[4];
        System.out.println("sucateiro" + sucateiro);
      }
   }
    
}
