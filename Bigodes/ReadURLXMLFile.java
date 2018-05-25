package xml;

import java.io.File;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import hashmap.United;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadURLXMLFile {
    //-------FIFO e TTL--------
    static final class vars {
        private final int fifo;
        private final int ttl;
        private final String pais;
        private final String mundo;
        private final String desporto;
        private vars(int fifo, int ttl, String pais, String mundo, String desporto) {
            this.fifo = fifo;
            this.ttl = ttl;
            this.pais = pais;
            this.mundo = mundo;
            this.desporto = desporto;
        }
        private int getFifo() {
            return fifo;
        }
        public int getTTL() {
            return ttl;
        }
        public String getPais() {
            return pais;
        }
        public String getMundo() {
            return mundo;
        }
        public String getDesporto() {
            return desporto;
        }
    }
    //------Valores do fifo e do ttl do ficheiro de configuração-------
    private static ReadURLXMLFile.vars configfiles(){
        int fifo = 0;
        int ttl = 0;
        String pais = null;
        String mundo = null;
        String desporto = null;
        try {
            File fXmlFile = new File("C:/Users/Asus/Desktop/XML/configfile.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("lista");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    fifo = Integer.parseInt(eElement.getElementsByTagName("fifo").item(0).getTextContent());
                    ttl = Integer.parseInt(eElement.getElementsByTagName("ttl").item(0).getTextContent());
                    pais = String.valueOf(eElement.getElementsByTagName("pais").item(0).getTextContent());
                    mundo = String.valueOf(eElement.getElementsByTagName("mundo").item(0).getTextContent());
                    desporto = String.valueOf((eElement.getElementsByTagName("desporto").item(0).getTextContent()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ReadURLXMLFile.vars(fifo, ttl, pais, mundo, desporto);
    }

    public static void main(String[] args) throws Exception {
        ReadURLXMLFile.vars variables = configfiles();
        // URL deve ser passado no ficheiro de configuração
        //String url = "http://www.dnoticias.pt/rss/pais.xml";
        String urlPais = variables.getPais();
        String urlMundo = variables.getMundo();
        String urlDesporto = variables.getDesporto();
        // Só para as noticias de desporto de momento
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document docDesporto = db.parse(new URL(urlDesporto).openStream());
        // Normaliza texto
        docDesporto.getDocumentElement().normalize();
        // Vai buscar cada separador "item"
        NodeList nList = docDesporto.getElementsByTagName("item");
        // Corre as primeiras 5 noticias existentes
        for (int temp = 0; temp < 5; temp++) {
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
