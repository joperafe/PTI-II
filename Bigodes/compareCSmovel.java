package movel;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class compareCSmovel {
    public static void main(String[] args) {
        // Tabelas CS x e y
        Map<String, String> x = new HashMap<>();
        Map<String, String> y = new HashMap<>();
        //---------Print CS x---------
        System.out.println("------X------");
        for (Map.Entry<String, String> entry_cs : x.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
        }
        //---------Print CS y---------
        System.out.println("------Y------");
        for (Map.Entry<String, String> entry_cs : y.entrySet()) {
            System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
        }
        System.out.println("********************************************************");
        Scanner reader = new Scanner(System.in);
        System.out.println("Possibilidades:\n CS x \t- 0 -\n CS y \t- 1 -\n Comparar CS's \t- 2 -\n Limpar CS's \t- 9 -\n");
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
                    System.out.println("Introduzir dados: ");
                    Scanner reader2 = new Scanner(System.in);
                    String dadosx = reader2.nextLine();
                    if (dadosx.isEmpty()) {
                        System.out.println("Hasta la vista... baby!");
                        break;
                    }
                    if(nomex!=null & dadosx!=null){
                        x.put(nomex,dadosx);
                    }
                    System.out.println("------X------");
                    for (Map.Entry<String, String> entry_cs : x.entrySet()) {
                        System.out.println("nome: " + entry_cs.getKey() + "\t\tdados: " + entry_cs.getValue());
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
                    System.out.println("Introduzir dados: ");
                    Scanner reader4 = new Scanner(System.in);
                    String dadosy = reader4.nextLine();
                    if (dadosy.isEmpty()) {
                        System.out.println("Hasta la vista... baby!");
                        break;
                    }
                    if(nomey!=null & dadosy!=null){
                        y.put(nomey,dadosy);
                    }
                    System.out.println("------Y------");
                    for (Map.Entry<String, String> entry_cs : y.entrySet()) {
                        System.out.println("nome: " + entry_cs.getKey() + "\t\tdados: " + entry_cs.getValue());
                    }
                    System.out.println("#########################################################");
                    break;
                case "2":
                    // Comparar cs
                    System.out.println("Comparar CS");
                    for (Map.Entry<String, String> entry : x.entrySet())
                    {
                        if(y.containsKey(entry.getKey())){
                            System.out.println("Chave existe, verificar TTL");
                        }else{
                            y.put(entry.getKey(),entry.getValue());
                            System.out.println("A chave " + entry.getKey() + " e os dados " + entry.getValue() + " foram adicionados");
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
            for (Map.Entry<String, String> entry_cs : x.entrySet()) {
                System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
            }
            //---------Print CS y---------
            System.out.println("------Y------");
            for (Map.Entry<String, String> entry_cs : y.entrySet()) {
                System.out.println("Tema: " + entry_cs.getKey() + "\t\tIPs: " + entry_cs.getValue());
            }
            System.out.println("********************************************************");
        }
    }
}
