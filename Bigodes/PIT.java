package hashmap;
import java.util.*;

public class PIT {
    public static void main(String[] args){

        //-----------Construção da Content Store-------------
        //De momento PIT (?)
        //List<Map<String,List<String>>> list = new ArrayList<Map<String,List<String>>>();
        //list.add(map1);
        Map<String, List<String>> map1 = new HashMap<String, List<String>>();
        List<String> arraylist1 = new ArrayList<String>();
        List<String> arraylist2 = new ArrayList<String>();

        //------Adicionar Notícias,(lista dos values)------
        //De momento a guardar ips de onde vêm pedidos
        arraylist1.add("2001::22:1::1/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist1.add("2001:0690:2280:820::2A/126");
        arraylist2.add("121:90:20:820::2A/128");

        //------Adicionar notícias aos Temas(key)------
        //De momento a guardar temas pesquisados
        map1.put("politica", arraylist1);
        map1.put("tecnologia", arraylist2);

        //---------Print mapa notícias--------
        for(Map.Entry<String,List<String>> entry : map1.entrySet()){
            System.out.println("Tema: " + entry.getKey() + " IPs: " + entry.getValue());
        }
/*
        System.out.println("********************************************************");

        for(String key : map1.keySet()){
            System.out.println("Key: " + key);
        }
        */
    /*
    //Imptimir cenas dos hashmaps

        System.out.println("********************************************************");
        Scanner reader = new Scanner(System.in);
        System.out.println("Tema a procurar: ");
        String tema = reader.nextLine();

        if (map1.containsKey(tema))
        {
            List listar = map1.get(tema);
            System.out.println("Existem " + listar.size() + " notícias do tema " + tema);
            System.out.println("Lista: " + map1.get(tema));
        }*/
    }
}
