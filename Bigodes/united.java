package hashmap;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class United {
    public static void main(String[] args) throws UnknownHostException {

        //-----------Construção da Content Store-------------
        Map<String,String> contentstorage_test = new HashMap<String, String>();
        contentstorage_test.put("economia","dados aqui algures");

        //De momento PIT (?)
        //List<Map<String,List<String>>> list = new ArrayList<Map<String,List<String>>>();
        //list.add(pit_test);
        Map<String, List<String>> pit_test = new HashMap<String, List<String>>();
        List<String> arraylist1 = new ArrayList<String>();
        List<String> arraylist2 = new ArrayList<String>();
        List<String> arraylist3 = new ArrayList<String>();

        //------Adicionar Notícias,(lista dos values)------
        //De momento a guardar ips de onde vêm pedidos
        arraylist1.add("2001::22:1::1/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist2.add("121:90:20:820::2A/128");
        arraylist3.add("211:90:20::2A/128");

        //------Adicionar notícias aos Temas(key)------
        //De momento a guardar temas pesquisados
        pit_test.put("desporto", arraylist1);
        pit_test.put("cultura", arraylist2);
        pit_test.put("mundo", arraylist3);

        System.out.println("********************************************************");
        //---------Print PIT notícias--------
        System.out.println("------PIT------");
        for (Map.Entry<String, List<String>> entry : pit_test.entrySet()) {
            System.out.println("Tema: " + entry.getKey() + "\t\tIPs: " + entry.getValue());
        }
        System.out.println("------CS------");
        for (Map.Entry<String, String> entry_cs : contentstorage_test.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
        }
        System.out.println("********************************************************");
        Scanner reader = new Scanner(System.in);
        System.out.println("Tipos de pacotes:\n interesse \t- 0 -\n dados \t- 1 - ");
        // TUDO PARA TRATAR A RECEÇÃO DE PACOTES
        while (reader.hasNextLine()) {
            String interesse = reader.nextLine();
            switch (interesse){
                case "0":
                    System.out.println("Introduzir nome do pacote de interesse: ");
                    Scanner reader1 = new Scanner(System.in);
                    String novo = reader1.nextLine();
                    if (novo.isEmpty()){
                        System.out.println("Hasta la vista baby!");
                        break;
                    }
                    // Caso o interesse exista na CS é reenviado
                    if (contentstorage_test.containsKey(novo)) {
                        //criar mensagem de pacote de conteúdo e enviar
                        //byte [] sendpackage = ("1 " + interesse + contentstorage_test.get(interesse)).getBytes();
                        //DatagramPacket resend = new DatagramPacket(sendpackage, sendpackage.length, sendpackage.getAddress());
                        //skt.send(resend);
                        System.out.println("Aqui estão os dados da categoria " + novo + ": " + contentstorage_test.get(novo));
                    }else{
                        // Caso exista o interesse na PIT é adicionado o endereço IP do host que faz o request
                        if (pit_test.containsKey(novo)) {
                            // get do ip proveniente
                            //pit_test.get(interesse).add(String.valueOf(host));
                            pit_test.get(novo).add("100:100:111");
                            System.out.println("O endereço IP, " + pit_test.get(novo) + " foi adicionado ao interesse " + novo);
                        }else{
                            // Não existindo, cria novo interesse na PIT
                            pit_test.put(novo, new ArrayList<String>());
                            pit_test.get(novo).add("100:100:111");
                            System.out.println("Interesse " + novo + " adicionado à PIT");
                        }
                    }
                    System.out.println("********************************************************");
                    break;
                case "1":
                    System.out.println("Introduzir nome do pacote de dados: ");
                    Scanner reader2 = new Scanner(System.in);
                    String novo2 = reader2.nextLine();
                    if (novo2.isEmpty()){
                        System.out.println("See you later alligator!");
                        break;
                    }else // Caso o interesse já exista na CS, pacote descartado
                        if (contentstorage_test.containsKey(novo2)){
                            System.out.println("Interesse já existente na CS");
                        }else // Caso exista interesse na PIT, pacote adicionado à CS e removido da PIT
                            if (pit_test.containsKey(novo2)){
                                // Será o array[2]
                                String dados = "Dados blablabla do interesse";
                                contentstorage_test.put(novo2, dados);
                                pit_test.remove(novo2);
                                System.out.println("Só para avisar que o interesse foi removido da PIT...");
                            }else {
                                System.out.println("Interesse não consta na CS nem na PIT");
                            }
                break;
            }

            System.out.println("********************************************************");
            //---------Print PIT notícias--------
            System.out.println("------PIT------");
            for (Map.Entry<String, List<String>> entry : pit_test.entrySet()) {
                System.out.println("Interesse: " + entry.getKey() + "\tIP: " + entry.getValue());
            }
            System.out.println("------CS------");
            for (Map.Entry<String, String> entry_cs : contentstorage_test.entrySet()) {
                System.out.println("Interesse: " + entry_cs.getKey() + "\tIP: " + entry_cs.getValue());
            }
            System.out.println("########################################################");
            System.out.println("Tipos de pacotes:\n interesse \t- 0 -\n dados \t- 1 - ");
        }
    }

}



/*
public class HashMapIterator {
    public static void main(String[] args) throws UnknownHostException {

        //-----------Construção da Content Store-------------
        Map<String,String> contentstorage_test = new HashMap<String, String>();
        contentstorage_test.put("economia","dados aqui algures");

        //De momento PIT (?)
        //List<Map<String,List<String>>> list = new ArrayList<Map<String,List<String>>>();
        //list.add(pit_test);
        Map<String, List<String>> pit_test = new HashMap<String, List<String>>();
        List<String> arraylist1 = new ArrayList<String>();
        List<String> arraylist2 = new ArrayList<String>();
        List<String> arraylist3 = new ArrayList<String>();

        //------Adicionar Notícias,(lista dos values)------
        //De momento a guardar ips de onde vêm pedidos
        arraylist1.add("2001::22:1::1/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist2.add("121:90:20:820::2A/128");
        arraylist3.add("211:90:20::2A/128");

        //------Adicionar notícias aos Temas(key)------
        //De momento a guardar temas pesquisados
        pit_test.put("desporto", arraylist1);
        pit_test.put("cultura", arraylist2);
        pit_test.put("mundo", arraylist3);



// AQUI ESTÁ O CASO PACOTE DE INTERESSE "0"
while (reader.hasNextLine()) {
        String interesse = reader.nextLine();

        if (interesse.isEmpty()){
        break;
        }
        // Caso exista o interesse na CS é reenviado
        if (contentstorage_test.containsKey(interesse)) {
        //criar mensagem de pacote de conteúdo e enviar
        //byte [] sendpackage = ("1 " + interesse + contentstorage_test.get(interesse)).getBytes();
        //DatagramPacket resend = new DatagramPacket(sendpackage, sendpackage.length, sendpackage.getAddress());
        //skt.send(resend);
        System.out.println("Aqui estão os dados da categoria " + interesse + ": " + contentstorage_test.get(interesse));
        }else{
        // Caso exista o interesse na PIT é adicionado o endereço IP do host que faz o request
        if (pit_test.containsKey(interesse)) {
        // get do ip proveniente
        //pit_test.get(interesse).add(String.valueOf(host));
        pit_test.get(interesse).add("100:100:111");
        }else{
        // Não existindo, cria novo interesse na PIT
        pit_test.put(interesse, new ArrayList<String>());
        pit_test.get(interesse).add("100:100:111");
        }
        System.out.println("Conteudo adicionado à PIT");
        //---------Print mapa notícias--------
        for (Map.Entry<String, List<String>> entry : pit_test.entrySet()) {
        System.out.println("Tema: " + entry.getKey() + "\tIPs: " + entry.getValue());
        }
        }
        System.out.println("********************************************************");
        }


// AQUI ESTÁ O CASO PACOTE DE DADOS "1"
// TUDO PARA TRATAR A RECEÇÃO DE UM PACOTE DE DADOS
while (reader.hasNextLine()) {
    String interesse = reader.nextLine();
    if (interesse.isEmpty()){
        System.out.println("See you later alligator!");
        break;
        }else // Caso o interesse já exista na CS, pacote descartado
             if (contentstorage_test.containsKey(interesse)){
                System.out.println("Interesse já existente na CS");
                }else // Caso exista interesse na PIT, pacote adicionado à CS e removido da PIT
                    if (pit_test.containsKey(interesse)){
                      // Será o array[2]
                      String dados = "Dados blablabla do interesse";
                      contentstorage_test.put(interesse, dados);
                      pit_test.remove(interesse);
                      System.out.println("Só para avisar que o interesse foi removido da PIT...");
                        }else {
                            System.out.println("Interesse não consta na CS nem na PIT");
                            }

// RECEÇÃO DE PACOTES DE DADOS
//verifica a existência da entrada na CS
//sim
    //descarta pacote
//senão se existe interesse na PIT
    //adiciona à CS
    //remove interesse da PIT
//caso não exista nem na PIT nem na CS

        */

/*
        System.out.println("********************************************************");

        for(String key : pit_test.keySet()){
            System.out.println("Key: " + key);
        }
        */

//Imprimir cenas dos hashmaps
/*
        System.out.println("********************************************************");
        Scanner reader = new Scanner(System.in);
        System.out.println("Tema a procurar: ");

        while (reader.hasNextLine()) {
            String tema = reader.nextLine();
            if (tema == null){
                break;
            }
            if (pit_test.containsKey(tema)) {
                List listar = pit_test.get(tema);
                System.out.println("Existem " + listar.size() + " notícias do tema " + tema);
                System.out.println("Lista: " + pit_test.get(tema));
            }
        }
        */
