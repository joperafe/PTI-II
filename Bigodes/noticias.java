package leituraxml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class xml {

    static final class vars {

        private final int fifo;
        private final int ttl;

        public vars(int fifo, int ttl) {
            this.fifo = fifo;
            this.ttl = ttl;
        }

        public int getFifo() {
            return fifo;
        }

        public int getTTL() {
            return ttl;

        }
    }

    public static vars configfiles(){
        int fifo = 0;
        int ttl = 0;
        try {

            File fXmlFile = new File("C:/Users/Asus/Desktop/XML/configfile.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("lista");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                System.out.println("#########################");
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    fifo = Integer.parseInt(eElement.getElementsByTagName("fifo").item(0).getTextContent());
                    ttl = Integer.parseInt(eElement.getElementsByTagName("ttl").item(0).getTextContent());
                    System.out.println("Conteudo: " + fifo);
                    System.out.println("Conteudo: " + eElement.getElementsByTagName("ttl").item(0).getTextContent());
                    System.out.println("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new vars(fifo, ttl);
    }

    public static void main(String argv[]) {
        //vars variables = configfiles();
        //System.out.println("Aqui está o tamanho do fifo :" + variables.getFifo());
        //System.out.println("Aqui está o tamanho do ttl :" + variables.getTTL());

        try {

            File fXmlFile = new File("C:/Users/Asus/Desktop/XML/lista.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("tema");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    mapa.put(eElement.getAttribute("id"),eElement.getElementsByTagName("conteudo").item(0).getTextContent());
                    System.out.println("Tema: " + eElement.getAttribute("id"));
                    System.out.println("Conteudo: " + eElement.getElementsByTagName("conteudo").item(0).getTextContent());
                    System.out.println("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
