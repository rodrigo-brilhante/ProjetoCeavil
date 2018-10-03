
package ned.ceavil.sistema;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import ned.ceavil.modelos.Eixo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author mauricio.junior
 */
public class TestaXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        Eixo eixo1 = new Eixo();
        eixo1.setTitulo("EIXO 1 – PLANEJAMENTO E AVALIAÇÃO INSTITUCIONAL");
        eixo1.setNomeImagem("imagem-eixo1.jpg");
              
        File paginaEixo = new File("eixo1.xhtml");
        
        //Criação de uma fábrica de documentos
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance(); 
        
        //Criação dos documentos
        DocumentBuilder docBuilder;
        try {
            
            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
        
            //Elemento root
            Element htmlElement = doc.createElement("html");
            htmlElement.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
            doc.appendChild(htmlElement);
        
            //Elementos filhos
            Element bodyElement = doc.createElement("body");
            htmlElement.appendChild(bodyElement);
            
            //Elemento EIXO:
            Element eixoElement = doc.createElement("h2");
            eixoElement.setTextContent(eixo1.getTitulo());
            Element divEixoElement = doc.createElement("div");
            Element imgEixoElement = doc.createElement("img");
            imgEixoElement.setAttribute("src", eixo1.getNomeImagem());
            divEixoElement.appendChild(imgEixoElement);
            eixoElement.appendChild(divEixoElement);
            
            bodyElement.appendChild(eixoElement);
            
            //Document para Texto:
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            
            transformer.transform( new DOMSource( doc ), new StreamResult( new File("eixo1.xhtml") ) );
            
            String personXMLStringValue = writer.getBuffer().toString();
            
            System.out.println(personXMLStringValue);
       
        } catch (ParserConfigurationException ex) {
            System.out.println(ex.getMessage());
        } catch (TransformerConfigurationException ex) {
             System.out.println(ex.getMessage());
        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }
      
        
        
        
        
        try {
            
            paginaEixo.createNewFile();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
}
