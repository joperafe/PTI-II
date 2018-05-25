package xml;

import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadURLXMLFile {
    public static void main(String[] args) throws Exception {
        // URL deve ser passado no ficheiro de configuração
        String url = "http://www.dnoticias.pt/rss/pais.xml";
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(url).openStream());
        // Normaliza texto
        doc.getDocumentElement().normalize();
        // Vai buscar cada separador "item"
        NodeList nList = doc.getElementsByTagName("item");
        // Corre as primeiras 5 noticias existentes
        for (int temp = 0; temp < 5/*nList.getLength()*/; temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                // Se houver conteúdo faz print
                Element eElement = (Element) nNode;
                // Vai buscar cada separador "title"
                System.out.println("Noticia : " + eElement.getElementsByTagName("title").item(0).getTextContent());
            }
        }
    }
}
