package movel;

import java.util.*;

public class comparePIT {
    public static void main(String[] args) {
        // Tabelas CS x e y
        Map<String, List<String>> x = new HashMap<>();
        Map<String, List<String>> y = new HashMap<>();
        //---------Print CS x---------
        System.out.println("------X------");
        for (Map.Entry<String, List<String>> entry_cs : x.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
        }
        //---------Print CS y---------
        System.out.println("------Y------");
        for (Map.Entry<String, List<String>> entry_cs : y.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
        }
        System.out.println("********************************************************");
        Scanner reader = new Scanner(System.in);
        System.out.println("Possibilidades:\n PIT x \t- 0 -\n PIT y \t- 1 -\n Comparar PIT's \t- 2 -\n Limpar PIT's \t- 9 -\n");
        while (reader.hasNextLine()) {
            String interesse = reader.nextLine();
            switch (interesse) {
                case "0":
                    // Mapa x
                    System.out.println("Introduzir nome: ");
                    Scanner reader1 = new Scanner(System.in);
                    String nomex = reader1.nextLine();
                    if (nomex.isEmpty()) {
                        System.out.println("Hasta la vista... baby!");
                        break;
                    }
                    System.out.println("Introduzir IP: ");
                    Scanner reader2 = new Scanner(System.in);
                    String dadosx = reader2.nextLine();
                    if (dadosx.isEmpty()) {
                        System.out.println("Hasta la vista... baby!");
                        break;
                    }
                    if(nomex!=null & dadosx!=null){
                        // Verifica se nome existe na PIT
                        if (x.containsKey(nomex)){
                            // Se existe nome adiciona novo IP
                            System.out.println("Nome existe, adiciona IP");
                            x.get(nomex).add(dadosx);
                            // FALTA TTL
                        }else {
                            // Caso seja um nome que não existe na PIT cria novo array e associa ao nome
                            x.put(nomex, new ArrayList<>());
                            x.get(nomex).add(dadosx);
                            System.out.println("Adicionados");
                        }
                    }
                    System.out.println("------X------");
                    for (Map.Entry<String, List<String>> entry_cs : x.entrySet()) {
                        System.out.println("nome: " + entry_cs.getKey() + "\t\tIP: " + entry_cs.getValue());
                    }
                    System.out.println("#########################################################");
                    break;
                case "1":
                    // Mapa y
                    System.out.println("Introduzir nome: ");
                    Scanner reader3 = new Scanner(System.in);
                    String nomey = reader3.nextLine();
                    if (nomey.isEmpty()) {
                        System.out.println("Hasta la vista... baby!");
                        break;
                    }
                    System.out.println("Introduzir IP: ");
                    Scanner reader4 = new Scanner(System.in);
                    String dadosy = reader4.nextLine();
                    if (dadosy.isEmpty()) {
                        System.out.println("Hasta la vista... baby!");
                        break;
                    }
                    if(nomey!=null & dadosy!=null){
                        // Verifica se nome existe na PIT
                        if (y.containsKey(nomey)){
                            // Se existe nome adiciona novo IP
                            System.out.println("Nome existe, adiciona IP");
                            y.get(nomey).add(dadosy);
                            // FALTA TTL
                        }else {
                            // Caso seja um nome que não existe na PIT cria novo array e associa ao nome
                            y.put(nomey, new ArrayList<>());
                            y.get(nomey).add(dadosy);
                            System.out.println("Adicionados");
                        }
                    }
                    System.out.println("------Y------");
                    for (Map.Entry<String, List<String>> entry_cs : y.entrySet()) {
                        System.out.println("nome: " + entry_cs.getKey() + "\t\tIP: " + entry_cs.getValue());
                    }
                    System.out.println("#########################################################");
                    break;
                case "2":
                    // Comparar pit
                    System.out.println("Comparar PIT");
                    for (Map.Entry<String, List<String>> entry : x.entrySet())
                    {
                        if(y.containsKey(entry.getKey())){
                            // Caso nome exista na PIT destino vai buscar array e adiciona IP's em falta
                            List<String> arrayx = entry.getValue();
                            List<String> arrayy = y.get(entry.getKey());
                            System.out.println("Array x " + arrayx + "\nArray y " + arrayy);
                            System.out.println("Chave existe, verificar lista de IP's.");
                            for (int i = 0; i<arrayx.size(); i++){
                                if (arrayy.contains(arrayx.get(i))){
                                    System.out.println("Array y contém IP " + arrayx.get(i));
                                }else{
                                    arrayy.add(arrayx.get(i));
                                    System.out.println("IP " + arrayx.get(i) + "foi adicionado");
                                }
                            }
                        }else{
                            y.put(entry.getKey(),entry.getValue());
                            System.out.println("A chave " + entry.getKey() + " e os IP " + entry.getValue() + " foram adicionados");
                        }
                    }
                    System.out.println("#########################################################");
                    break;
                case "9":
                    // Limpar mapas
                    System.out.println("Limpar CS");
                    x.clear();
                    y.clear();
                    System.out.println("#########################################################");
                    break;
            }
            //---------Print CS x---------
            System.out.println("------X------");
            for (Map.Entry<String, List<String>> entry_cs : x.entrySet()) {
                System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
            }
            //---------Print CS y---------
            System.out.println("------Y------");
            for (Map.Entry<String, List<String>> entry_cs : y.entrySet()) {
                System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
            }
            System.out.println("********************************************************");
        }
    }
}
