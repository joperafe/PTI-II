package hashmap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

public class United {
    //-------FIFO e TTL--------
    static final class vars {
        private final int fifo;
        private final int ttl;
        private vars(int fifo, int ttl) {
            this.fifo = fifo;
            this.ttl = ttl;
        }
        private int getFifo() {
            return fifo;
        }
        public int getTTL() {
            return ttl;
        }
    }
    //------Valores do fifo e do ttl do ficheiro de configuração-------
    private static vars configfiles(){
        int fifo = 0;
        int ttl = 0;
        try {
            File fXmlFile = new File("C:/Users/Asus/Desktop/XML/configfile.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("lista");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    fifo = Integer.parseInt(eElement.getElementsByTagName("fifo").item(0).getTextContent());
                    ttl = Integer.parseInt(eElement.getElementsByTagName("ttl").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new vars(fifo, ttl);
    }
    //
    public static void main(String[] args) throws UnknownHostException {
        vars variables = configfiles();
        //System.out.println("Aqui está o tamanho do fifo :" + variables.getFifo());
        //System.out.println("Aqui está o tamanho do ttl :" + variables.getTTL());
        //-----------Construção da Content Store FIFO com MAX_ENTRIES entradas-------------
        int MAX_ENTRIES = variables.getFifo();
        LinkedHashMap<String,String> contentstore_test = new LinkedHashMap<>(MAX_ENTRIES, 0.75F, true){
            protected boolean removeEldestEntry(Map.Entry eldest){
            return size() > MAX_ENTRIES;
        }};
        //-----------Construção da PIT-----------------------
        Map<String, List<String>> pit_test = new HashMap<>();
        //-----------Construção da FIB-----------------------
        Map<String, List<String>> fib_test = new HashMap<>();
        //-----------Construção da CS TTL-----------------------
        Map<String, Timestamp> cs_ttl = new HashMap<>();
        //-----------Construção da PIT TTL-----------------------
        Map<String, Timestamp> pit_ttl = new HashMap<>();
        // TESTES
        List<String> arraylist1 = new ArrayList<>();
        List<String> arraylist2 = new ArrayList<>();
        List<String> arraylist3 = new ArrayList<>();
        // FAKE NEWS
        //------IPs falsos para testes-----
        arraylist1.add("2001::22:1::1/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist1.add("2001:0690:2180:820::2/126");
        arraylist2.add("121:90:20:820::2A/128");
        arraylist3.add("211:90:20::2A/128");
        //------Adicionar interesses(key) PIT------
        pit_test.put("desporto", arraylist1);
        pit_test.put("cultura", arraylist2);
        pit_test.put("mundo", arraylist3);
        //------Adicionar interesses(key) FIB------
        fib_test.put("cinema", arraylist1);
        fib_test.put("leitura", arraylist2);
        fib_test.put("pintura", arraylist3);
        //------Adicionar interesses(key) PIT------
        contentstore_test.put("economia","dados aqui algures");
        System.out.println("********************************************************");
        //---------Print CS---------
        System.out.println("------CS------");
        for (Map.Entry<String, String> entry_cs : contentstore_test.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
        }
        System.out.println("OIOAIOQIODIEQODIEOIDOEIO " + contentstore_test.get("cultura"));
        //---------Print PIT--------
        System.out.println("------PIT------");
        for (Map.Entry<String, List<String>> entry : pit_test.entrySet()) {
            System.out.println("Tema: " + entry.getKey() + "\t\tConteudo: " + entry.getValue());
        }
        //---------Print FIB--------
        System.out.println("------FIB------");
        for (Map.Entry<String, List<String>> entry : fib_test.entrySet()) {
            System.out.println("Tema: " + entry.getKey() + "\t\tIPs: " + entry.getValue());
        }
        System.out.println("********************************************************");
        Scanner reader = new Scanner(System.in);
        System.out.println("Tipos de pacotes:\n interesse \t- 0 -\n dados \t- 1 - ");
        // TUDO PARA TRATAR A RECEÇÃO DE PACOTES
        while (reader.hasNextLine()) {
            String interesse = reader.nextLine();
            switch (interesse) {
                case "0":
                    // Socket request
                    System.out.println("Introduzir nome do pacote de interesse: ");
                    Scanner reader1 = new Scanner(System.in);
                    String novo = reader1.nextLine();
                    if (novo.isEmpty()) {
                        System.out.println("Hasta la vista... baby!");
                        break;
                    }
                    // Caso o interesse exista na CS é reenviado
                    if (contentstore_test.containsKey(novo)) {
                        //criar mensagem de pacote de conteúdo e enviar
                        //criar trama
                        String aux =" ";
                        // timestamp origem, de fabrico
                        byte [] sendpackage = ("1 " + interesse + aux + contentstore_test.get(interesse) + aux /*+ timestamp*/).getBytes();
                        //DatagramPacket reply = new DatagramPacket(sendpackage, sendpackage.length, request.getAddress(), request.getPort());
                        //skt.send(reply);
                        System.out.println("Aqui estão os dados do interesse - " + novo + " - : " + contentstore_test.get(novo));
                    } else
                        // Caso exista o interesse na PIT é adicionado o endereço IP do host que faz o request
                        if (pit_test.containsKey(novo)) {
                            // Endereço já existente na PIT
                            if(pit_test.containsValue(novo)){
                                Timestamp ttl;
                                //if (ttl.equals())
                                // Ao fim de x segundos o interesse é limpo da PIT, função que verifica TTLs
                                System.out.println("Interesse duplicado! Not much to do, for now...");
                                break;
                            }else { // Adiciona endereço na PIT
                                //pit_test.get(novo).add(String.valueOf(request));
                                String endereco = "100:100:111";
                                pit_test.get(novo).add(endereco);
                                System.out.println("O endereço IP, " + endereco + " foi adicionado ao interesse " + novo);
                            }
                        } else
                            // Existe na FIB!
                            if (fib_test.containsKey(novo)) {
                            // Não existindo, cria novo interesse na PIT e envia o PRIMEIRO pedido para a rede(Boolean, Case True or False! True by default)
                            pit_test.put(novo, new ArrayList<>());
                            pit_test.get(novo).add("100:100:111");
                            String aux = " ";
                            // Aqui é criado o pacote com o IP do host
                            String msg = "0 " + aux + novo + aux + /*InetAddress.getLocalHost();*/"2001:1023:6567::056";// + aux + ttl;// + aux + flag;
                            System.out.println("msg: " + msg);
                            byte[] b = msg.getBytes();
                            // Envia a trama construída para o endereço existente na FIB
                                // BROADCAST AQUI!
                            //DatagramPacket reinteresse = new DatagramPacket(b,b.length, (InetAddress) fib_test.get(novo), 9989); //creating packet
                            //skt.send(reinteresse);
                            // Faltava enviar o interesse ao que existia na fib...
                            System.out.println("Foi enviado o pacote de interesse ao " + fib_test.get(novo));
                            System.out.println("Interesse " + novo + " adicionado à PIT");
                        } else {
                            System.out.println("Nem uma simples FIB... onde está tudo :'( \n ****LIXO****");
                        }
                    System.out.println("********************************************************");
                    break;
                case "1":
                    ArrayList<String> listfib = new ArrayList<>();
                    // Pacote de dados
                    System.out.println("Introduzir nome do pacote de dados: ");
                    Scanner reader2 = new Scanner(System.in);
                    String novo2 = reader2.nextLine();
                    if (novo2.isEmpty()){
                        System.out.println("See you later alligator!");
                        break;
                    }else // Caso exista interesse na PIT: pacote adicionado à CS, enviado a todos os ips existentes na PIT, adicionado à FIB e removido da PIT
                        if (pit_test.containsKey(novo2)){
                            // Dados bluff
                            String dados = "Dados bluff do interesse";
                            // GUARDAR NA CS
                            contentstore_test.put(novo2, dados);
                            // Porque não juntar o útil ao agradável e matar dois coelhos? Enviar para a FIB e enviar pacotes de dados a todos os amiguinhos da PIT
                            // SEND DATA TO FIB
                            // SEND DATA for each PIT'S ADDRESS
                            for (String address : pit_test.get(novo2)) {
                                // send to values
                                System.out.println("Endereço " + address);
                                String aux = " ";
                                // Tipo de pacote "1" que se traduz em pacote de dados
                                // Aqui vai o ttl que vem da trama
                                String msg = "1 " + novo2 + aux + address ;//+ aux + ttl;// + aux + flag;
                                //System.out.println("msg: " + msg);
                                byte [] b = msg.getBytes();
                                // Envio direto da trama para os "clientes" da PIT
                                //DatagramPacket direto = new DatagramPacket(b,b.length, InetAddress.getByName(ip), 9989); //creating packet
                                //skt.send(direto);
                                //System.out.println("Daqui foi o pacote na via-rápida para " + address );
                                // Adicionar na FIB
                                listfib.add(address);
                                System.out.println("List fib " + listfib);
                                System.out.println("Interesse " + novo2 + " introduzido na FIB com interface: " + address);
                                //pit_test.remove(novo2,address);
                                fib_test.put(novo2, listfib);

                                // VER ESTA CONFUSÃO!!
                                // Atenção a este remove, de momento volta a introduzir os dados do array inicial, portanto para já é difícil perceber se saig
                                pit_test.remove(novo2);
                                System.out.println("Interesse removido da PIT");
                            }
                        }else // Conteúdo existente na CS, LIXO
                            if (contentstore_test.containsKey(novo2)){
                                // É preciso ter cuidado com esta suposição, podem ser dados corrompidos!!
                                System.out.println("Interesse já existente na CS");
                            }else { // Conteúdo ausente de todas as tabelas
                                // Pode ser definida uma política para guardar durante algum tempo o pacote...
                                // Who knows...?
                                System.out.println("Interesse não consta na CS nem na PIT");
                            }
                    System.out.println("********************************************************");
                    break;
            }
            System.out.println("------CS------");
            for (Map.Entry<String, String> entry_cs : contentstore_test.entrySet()) {
                System.out.println("Interesse: " + entry_cs.getKey() + "\tConteudo: " + entry_cs.getValue());
            }
            System.out.println("------PIT------");
            for (Map.Entry<String, List<String>> entry : pit_test.entrySet()) {
                System.out.println("Interesse: " + entry.getKey() + "\tIP: " + entry.getValue());
            }
            System.out.println("------FIB------");
            for (Map.Entry<String, List<String>> entry : fib_test.entrySet()) {
                System.out.println("Interesse: " + entry.getKey() + "\tIP: " + entry.getValue());
            }
            System.out.println("\n########################################################\n");
            System.out.println("Tipos de pacotes:\n interesse \t- 0 -\n dados \t- 1 - ");
        }
    }
}
