package xml;

import java.io.*;

public class Class1 {
    public static void main(String[] args) throws Exception {
        // Cria ficheiro data com todas as notícias
        Writer output = new BufferedWriter(new FileWriter("C:/Users/Asus/Desktop/datadn.xml"));
        // Localização das notícias
        String desporto = "C:/Users/Asus/Desktop/dndesporto.xml";
        String pais = "C:/Users/Asus/Desktop/dnpais.xml";
        String mundo = "C:/Users/Asus/Desktop/dnmundo.xml";
        copia(desporto, output);
        copia(pais, output);
        copia(mundo, output);
        output.close();
        System.out.println("Copiado!");
    }

    public static void copia(String file, Writer output) throws IOException {
        String troca = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
        FileInputStream in = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            // Retira cabeçalho
            if (strLine.contains("?>")){
                strLine = strLine.replace(troca, "");
            }
            output.write(strLine);
        }
    }
}
