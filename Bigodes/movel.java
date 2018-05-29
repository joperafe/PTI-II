package movel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class movel {

    private static String ip = "1";
    public static void main(String[] args) {

        //-----------Construção da Content Store FIFO com MAX_ENTRIES entradas-------------
        int MAX_ENTRIES = 50;
        LinkedHashMap<String,String> csX =
                new LinkedHashMap<>(MAX_ENTRIES, 0.75F, true){
                    protected boolean removeEldestEntry(Map.Entry eldest){
                        return size() > MAX_ENTRIES;
                    }};
        //-----------Construção da PIT-----------------------
        Map<String, List<String>> pitX = new HashMap<>();
        //-----------Construção da FIB-----------------------
        Map<String, List<String>> fib_test = new HashMap<>();


        // MAPAS DE TESTE
        Map<String, String> csY = new HashMap<>();
        Map<String, List<String>> pitY = new HashMap<>();
        // TESTES
        List<String> arraylist1 = new ArrayList<>();
        List<String> arraylist2 = new ArrayList<>();
        List<String> arraylist3 = new ArrayList<>();
        // FAKE NEWS
        //------IPs falsos para testes-----
        arraylist1.add("26");
        arraylist1.add("2001::22:1::1/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist1.add("2001:0690:2180:820::2/126");
        arraylist2.add("121:90:20:820::2A/128");
        arraylist3.add("211:90:20::2A/128");
        //------Adicionar interesses(key) PIT------
        pitX.put("Desporto", arraylist1);
        pitX.put("Cultura", arraylist2);
        pitX.put("Mundo", arraylist3);
        pitY.put("OLA",arraylist1);
        //------Adicionar interesses(key) CS------
        List<String> arrayteste = new ArrayList<>();
        List<String> arrayteste1 = new ArrayList<>();
        csY.put("Bola","12345");
        csY.put("Meloa","123");
        csY.put("Cultura", "54321");
        csX.put("Economia","dados aqui algures");
        //------Adicionar conteudo xml------
        try {
            File fXmlFile = new File("C:/Users/Asus/Desktop/XML/lista.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("tema");
            //System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    //System.out.println("Tema: " + eElement.getAttribute("id"));
                    //System.out.println("Conteudo: " + eElement.getElementsByTagName("conteudo").item(0).getTextContent());
                    //System.out.println("\n");
                    csX.put(eElement.getAttribute("id"), eElement.getElementsByTagName("conteudo").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("********************************************************");
        System.out.println("------X------");
        //---------Print CS---------
        System.out.println("------CS------");
        for (Map.Entry<String, String> entry_cs : csX.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tConteudo: " + entry_cs.getValue());
        }
        //---------Print PIT--------
        System.out.println("------PIT------");
        for (Map.Entry<String, List<String>> entry : pitX.entrySet()) {
            System.out.println("Tema: " + entry.getKey() + "\t\tIP: " + entry.getValue());
        }
        System.out.println("------Y------");
        //---------Print CS---------
        System.out.println("------CS------");
        for (Map.Entry<String, String> entry_cs : csY.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tConteudo: " + entry_cs.getValue());
        }
        //---------Print PIT--------
        System.out.println("------PIT------");
        for (Map.Entry<String, List<String>> entry : pitY.entrySet()) {
            System.out.println("Tema: " + entry.getKey() + "\t\tIP: " + entry.getValue());
        }
        System.out.println("********************************************************");
        Scanner reader = new Scanner(System.in);
        System.out.println("Tipos de pacotes:\n dead \t- 0 -\n hello \t- 1 -\n content \t- 2 - ");
        // TUDO PARA TRATAR A RECEÇÃO DE PACOTES
        while (reader.hasNextLine()) {
            String interesse = reader.nextLine();
            switch (interesse) {
                case "0":
                    // Recebeu pacote dead certificate
                    System.out.println("Introduzir nome do pacote de interesse a ser eliminado: ");
                    Scanner reader1 = new Scanner(System.in);
                    String dead = reader1.nextLine();
                    System.out.println("Procura " + dead);
                    System.out.println("Introduzir ip do pacote de interesse a ser eliminado: ");
                    Scanner reader2 = new Scanner(System.in);
                    String dead1 = reader1.nextLine();
                    // Guarda nova pit
                    pitX = deadcertificate(dead, dead1, pitX);
                    System.out.println("Dead certificate tratado!");
                    System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                    break;
                case "1":
                    // Recebeu pacote Hello
                    hello();
                    break;

                case "2":
                    // Recebeu pacote content
                    // x são as tabelas do nó
                    // y são as tabelas recebidas
                    // compareCS(cs do nó, cs recebida)
                    // csX recebe o valor de x retornado pela função compareCS
                    csX = compareCS(csX,csY);
                    System.out.println("PIT e CS analizadas");
                    // Imprime conteúdo da CS do nó
                    System.out.println("------CS x------");
                    for (Map.Entry<String, String> entry_cs : csX.entrySet()) {
                        System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
                    }
                    pitX = comparePIT(pitX,pitY);
                    // Imprime conteúdo da CS do nó
                    System.out.println("------PIT x------");
                    for (Map.Entry<String, List<String>> entry_cs : pitX.entrySet()) {
                        System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
                    }
                    System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                    break;
                case "3":
                    // Testes
                    System.out.println("Introduzir nome: ");
                    Scanner nomehard = new Scanner(System.in);
                    String nomehar = nomehard.nextLine();
                    System.out.println(pitX.get(nomehar));
                    if (nomehar.isEmpty()) {
                        System.out.println("See you later alligator!");
                        break;
                    } else if (pitX.containsKey(nomehar)) {
                        System.out.println(" existe nome ");
                    }
                    System.out.println("Introduzir ip: ");
                    Scanner iphard = new Scanner(System.in);
                    List<String> testes = new ArrayList<>();
                    String iphar = iphard.nextLine();
                    System.out.println(iphar);
                    testes = pitX.get(nomehar);
                    System.out.println("testes : " + testes);
                    if (testes.contains(iphar)) {
                        //pit_test.remove(nomehar);
                        testes.remove(iphar);
                        pitX.put(nomehar, testes);
                        System.out.println(pitX.get(nomehar));
                        System.out.println("IP introduzido");
                    } else {
                        pitX.put(nomehar, testes);
                        System.out.println("Nome " + nomehar + " e ip " + iphar + " adicionados");
                        //System.out.println("Enviar PIT e CS");
                        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                        break;
                    }
            }
            System.out.println("********************************************************");
            System.out.println("------X------");
            //---------Print CS---------
            System.out.println("------CS------");
            for (Map.Entry<String, String> entry_cs : csX.entrySet()) {
                System.out.println("Tema: " + entry_cs.getKey() + "\t\tConteudo: " + entry_cs.getValue());
            }
            //---------Print PIT--------
            System.out.println("------PIT------");
            for (Map.Entry<String, List<String>> entry : pitX.entrySet()) {
                System.out.println("Tema: " + entry.getKey() + "\t\tIP: " + entry.getValue());
            }
            System.out.println("------Y------");
            //---------Print CS---------
            System.out.println("------CS------");
            for (Map.Entry<String, String> entry_cs : csY.entrySet()) {
                System.out.println("Tema: " + entry_cs.getKey() + "\t\tConteudo: " + entry_cs.getValue());
            }
            //---------Print PIT--------
            System.out.println("------PIT------");
            for (Map.Entry<String, List<String>> entry : pitY.entrySet()) {
                System.out.println("Tema: " + entry.getKey() + "\t\tIP: " + entry.getValue());
            }
            System.out.println("********************************************************");
            /*
            System.out.println("\n########################################################\n");
            System.out.println("Tipos de pacotes:\n dead \t- 0 -\n hello \t- 1 -\n content \t- 2 - ");*/
        }
    }

    /** returns the minimum of two numbers */
    public static int minFunction(int n1, int n2) {
        int min;
        if (n1 > n2)
            min = n2;
        else
            min = n1;

        return min;
    }

    private static String tema = "pais";
    //----------- TRATA DEAD CERTIFICATES --------------
    public static Map<String, List<String>> deadcertificate(String nome, String ip, Map<String, List<String>> x){//, String id, long ttl){
        System.out.println("Em análise...");
        System.out.println("------PIT------");
        // Só faz print da tabela
        for (Map.Entry<String, List<String>> entry : x.entrySet()) {
            System.out.println("Interesse: " + entry.getKey() + "\tIP: " + entry.getValue());
        }
        // Real deal
        if(x.containsKey(nome)) {
            for (Map.Entry<String, List<String>> entry : x.entrySet()) 
            {
                List<String> arrayx = entry.getValue();
                // Caso só exista 1 ip no interesse elimina interesse, caso contrário o interesse continua na tabela mas sem ips
                if (arrayx.size()==1){
                    System.out.println("Como só possui 1 endereço IP apaga interesse");
                    x.remove(nome);
                }
                // Percorre arrays
                if (arrayx.contains(ip)) {
                    arrayx.remove(ip);
                    System.out.println("Array x contém IP " + ip + " e foi removido");
                } else {
                    System.out.println("Do nothing");
                }
            }
        }
        System.out.println("********************************************************");
        //
        return (x);
    }
    public static void hello(){
        System.out.println("Envia CS  e PIT");
    }
    // Compare CS
    public static LinkedHashMap<String, String> compareCS(LinkedHashMap<String,String> x, Map<String,String> y){//, Map<String, List<String>> y){
        System.out.println("Estou a analizar a CS");
        // Comparar cs
        System.out.println("Comparar CS");
        for (Map.Entry<String, String> entry : y.entrySet())
        {
            // Se existir nome na CS do nó verifica TTL, futuramente...
            if(x.containsKey(entry.getKey())){
                System.out.println("Nome existe, verificar TTL");
            }else{
                // Caso o nome não exista na CS do nó adiciona
                x.put(entry.getKey(),entry.getValue());
                System.out.println("A chave " + entry.getKey() + " e os dados " + entry.getValue() + " foram adicionados");
            }
        }
        return x;
    }
    // Compare PIT
    public static Map<String, List<String>> comparePIT(Map<String,List<String>> x, Map<String,List<String>> y){//, Map<String, List<String>> y){
        System.out.println("Estou a analizar a PIT");
        // Comparar pit
        System.out.println("Comparar PIT");
        for (Map.Entry<String, List<String>> entry : y.entrySet())
        {
            if(x.containsKey(entry.getKey())){
                // Caso nome exista na PIT destino vai buscar array e adiciona IP's em falta
                List<String> arrayx = entry.getValue();
                List<String> arrayy = x.get(entry.getKey());
                System.out.println("Array x " + arrayx + "\nArray y " + arrayy);
                System.out.println("Chave existe, verificar lista de IP's.");
                for (int i = 0; i<arrayy.size(); i++){
                    if (arrayx.contains(arrayy.get(i))){
                        System.out.println("Array x contém IP " + arrayy.get(i));
                    }else{
                        arrayx.add(arrayy.get(i));
                        System.out.println("IP " + arrayy.get(i) + "foi adicionado");
                    }
                }
            }else{
                x.put(entry.getKey(),entry.getValue());
                System.out.println("A chave " + entry.getKey() + " e os IP " + entry.getValue() + " foram adicionados");
            }
        }
        return x;
    }
}
