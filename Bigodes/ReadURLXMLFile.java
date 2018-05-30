package xml;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import hashmap.United;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

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
        String DNurlPais = variables.getPais();
        String DNurlMundo = variables.getMundo();
        String DNurlDesporto = variables.getDesporto();

        criaxml("dnpais", DNurlPais);
        criaxml("dndesporto", DNurlDesporto);
        criaxml("dnmundo", DNurlMundo);
    }

    public static void criaxml(String tema, String url) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document data = db.parse(new URL(url).openStream());
        // Normaliza texto
        data.getDocumentElement().normalize();
        // Vai buscar cada separador "item"
        NodeList nList = data.getElementsByTagName("item");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        // root elements
        // TEMAS
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(tema);
        doc.appendChild(rootElement);
        // Corre as primeiras 5 noticias existentes

        for (int temp = 0; temp < 5; temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                // Se houver conteúdo faz print
                Element eElement = (Element) nNode;
                // Vai buscar cada separador "title"
                System.out.println("Noticia : " + eElement.getElementsByTagName("title").item(0).getTextContent());
                // Cria data.xml
                Element staff = doc.createElement(tema);
                rootElement.appendChild(staff);
                // set id da noticia
                staff.setAttribute("id", String.valueOf(temp));
                staff.appendChild(doc.createTextNode(eElement.getElementsByTagName("title").item(0).getTextContent()));
                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("C:/Users/Asus/Desktop/"+ tema +".xml"));
                // Output to console for testing
                // StreamResult result = new StreamResult(System.out);
                transformer.transform(source, result);
                System.out.println("File saved!");
                StreamResult result1 = new StreamResult(System.out);
                transformer.transform(source, result1);
            }
        }
    }
}
