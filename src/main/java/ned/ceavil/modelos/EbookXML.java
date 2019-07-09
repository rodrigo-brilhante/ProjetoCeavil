package ned.ceavil.modelos;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;

/**
 * @author rodrigo.brito
 * @author mauricio.junior
 */
public class EbookXML {
    //Criação de uma fábrica de documentos
    private DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

    //Criação dos documentos
    private DocumentBuilder docBuilder;

    public void gerarXHTMLCapa(String ebookId, String nomeImagem) throws ParserConfigurationException, TransformerConfigurationException, TransformerException{

        String pathArquivo = PastaEpub.DB + File.separator 
                + ebookId
                + File.separator
                + PastaEpub.EPUB.OEBPS.getDescricao()
                + File.separator;
        
        docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        
        //Elemento root
        Element htmlElement = doc.createElement("html");
        htmlElement.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
        doc.appendChild(htmlElement);
        
        //Head
        Element head = doc.createElement("head");
        //Head Title
        Element titleHead = doc.createElement("title");
        titleHead.setTextContent("Cover");
        head.appendChild(titleHead);
        htmlElement.appendChild(head);
        
        //Body
        Element body = doc.createElement("body");
        //div
        Element div = doc.createElement("div");
        div.setAttribute("style", "text-align: center; padding: 0pt; margin: 0pt;");
        //sgv
        Element sgv = doc.createElement("svg");
            sgv.setAttribute("xmlns", "http://www.w3.org/2000/svg");
            sgv.setAttribute("height", "100%");
            sgv.setAttribute("preserveAspectRatio", "xMidYMid meet");
            sgv.setAttribute("version", "1.1");
            sgv.setAttribute("viewBox", "0 0 1410 2250");
            sgv.setAttribute("width", "100%");
            sgv.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
        div.appendChild(sgv);
        
        //img Cover
        Element image = doc.createElement("image");
        image.setAttribute("width", "1410");
        image.setAttribute("height", "2250");
        image.setAttribute("xlink:href", nomeImagem);
        
        sgv.appendChild(image);
        
        body.appendChild(div);
        
        htmlElement.appendChild(body);
        //Document para Texto:
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        transformer.transform(new DOMSource(doc), new StreamResult(new File(pathArquivo+ "cover.xhtml")));
        
    }
        
    public void gerarXHTMLEixo(String ebookid, List<Eixo> eixos) throws ParserConfigurationException, TransformerException {
        int index = 1;
        Integer tocId = 1;
        String pathArquivo = PastaEpub.DB + File.separator 
                + ebookid
                + File.separator
                + PastaEpub.EPUB.OEBPS.getDescricao()
                + File.separator;

        for (Eixo eixo : eixos) {

            docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //Elemento root
            Element htmlElement = doc.createElement("html");
            htmlElement.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
            doc.appendChild(htmlElement);

            //Head
            Element head = doc.createElement("head");

            //Head Title
            Element titleHead = doc.createElement("title");
            head.appendChild(titleHead);

            //Style CSS
            Element link = doc.createElement("link");
            link.setAttribute("href", "style.css");
            link.setAttribute("type", "text/css");
            link.setAttribute("rel", "stylesheet");
            head.appendChild(link);
            
            htmlElement.appendChild(head);
            
            //Elemento Body
            Element bodyElement = doc.createElement("body");
            htmlElement.appendChild(bodyElement);

            //Eixo
            Element tituloEixo = doc.createElement("h2");
            tituloEixo.setTextContent(eixo.getTitulo());
            bodyElement.appendChild(tituloEixo);

//            Element divEixo = doc.createElement("div");
            Element imgEixoElement = doc.createElement("img");
            imgEixoElement.setAttribute("alt", eixo.getNomeImagem());
            imgEixoElement.setAttribute("src", eixo.getNomeImagem());
//            divEixo.appendChild(imgEixoElement);
//            bodyElement.appendChild(divEixo);
            bodyElement.appendChild(imgEixoElement);

            for (Indicador indicador : eixo.getIndicadores()) {
                //Bloco I -> titulo + descrição
                Element divBlocoI = doc.createElement("div");
                    divBlocoI.setAttribute("style", "page-break-before: always;");
                //Bloco II -> <p> + imagem infografico
                Element divBlocoII = doc.createElement("div");
                    divBlocoII.setAttribute("style", "page-break-before: always;");
                //Bloco III -> <p> + imagem timeline
                Element divBlocoIII = doc.createElement("div");
                    divBlocoIII.setAttribute("style", "page-break-before: always;");
                //Bloco IV -> <p> + tabela da localização 
                Element divBlocoIV = doc.createElement("div");
                    divBlocoIV.setAttribute("style", "page-break-before: always;");                    
                    
            //Inicio Bloco I    
                Element tituloIndicador = doc.createElement("h3");
                    tituloIndicador.setAttribute("id",tocId.toString());
                    tituloIndicador.setTextContent(indicador.getTitulo());
                    divBlocoI.appendChild(tituloIndicador);
                Element descricaoIndicador = doc.createElement("p");
                    descricaoIndicador.setTextContent(indicador.getDescricao());
                    divBlocoI.appendChild(descricaoIndicador);
            //Fim do Bloco I
            //Inicio Bloco II
                    Element infografico = doc.createElement("p");
                        infografico.setAttribute("style", "text-align: center; font-weight: bold;");
                        infografico.setTextContent("Infográfico");
                        divBlocoII.appendChild(infografico);
                    Element imgIndicadorElement = doc.createElement("img");
                        imgIndicadorElement.setAttribute("alt", indicador.getNomeImagem());
                        imgIndicadorElement.setAttribute("src", indicador.getNomeImagem());
                        divBlocoII.appendChild(imgIndicadorElement);
            //Fim do Bloco II
            //Inicio Bloco III
                    Element textoTimeLine = doc.createElement("p");
                        textoTimeLine.setAttribute("style", "text-align: center; font-weight: bold;");
                        textoTimeLine.setTextContent("Linha do Tempo");
                        divBlocoIII.appendChild(textoTimeLine);
                    Element imgTimeLine = doc.createElement("img");
                        imgTimeLine.setAttribute("alt", indicador.getNomeTimeLine());
                        imgTimeLine.setAttribute("src", indicador.getNomeTimeLine()); 
                        divBlocoIII.appendChild(imgTimeLine);
            //Fim do Bloco III
            //Inicio do Bloco IV
                    Element localizacao = doc.createElement("p");
                        localizacao.setAttribute("style", "text-align: center; font-weight: bold;");
                        localizacao.setTextContent("Localização");
                        divBlocoIV.appendChild(localizacao);
                    Element table = doc.createElement("table");
                        table.setAttribute("border", "1px solid black;");
                        table.setAttribute("width", "100%");
                        Element firstLine = doc.createElement("tr");
                            Element firstField = doc.createElement("td");
                                firstField.setAttribute("style", "text-align: center;");
                                firstField.setTextContent("N° Página PDI");
                                firstLine.appendChild(firstField);
                            Element secondField = doc.createElement("td");
                                secondField.setAttribute("style", "text-align: center;");
                                secondField.setTextContent("n° Indicador");
                                firstLine.appendChild(secondField);
                            Element thirdField = doc.createElement("td");
                                thirdField.setAttribute("style", "text-align: center;");
                                thirdField.setTextContent("Estação");
                                firstLine.appendChild(thirdField);
                        table.appendChild(firstLine);
                        Element secondLine = doc.createElement("tr");
                            Element NumPagPDI = doc.createElement("td");
                                NumPagPDI.setAttribute("style", "text-align: center;");
                                NumPagPDI.setTextContent(indicador.getLocalizacao()[0]);
                                secondLine.appendChild(NumPagPDI);
                            Element NumIndicador = doc.createElement("td");
                                NumIndicador.setAttribute("style", "text-align: center;");
                                NumIndicador.setTextContent(indicador.getLocalizacao()[1]);
                                secondLine.appendChild(NumIndicador);
                            Element NomePasta = doc.createElement("td");
                                NomePasta.setAttribute("style", "text-align: center;");
                                NomePasta.setTextContent(indicador.getLocalizacao()[2]);
                                secondLine.appendChild(NomePasta);
                        table.appendChild(secondLine);
                divBlocoIV.appendChild(table);
            //Fim do bloco IV
            bodyElement.appendChild(divBlocoI);
            bodyElement.appendChild(divBlocoII);
            bodyElement.appendChild(divBlocoIII);
            bodyElement.appendChild(divBlocoIV);
            tocId++;
            }

            //Document para Texto:
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();

            transformer.transform(new DOMSource(doc), new StreamResult(new File(pathArquivo + "eixo" + index + ".xhtml")));

            index++;

        }
    }

    public void gerarTocNCX(String ebookid, List<Eixo> eixos) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {

        EbookJson ebookJson = new EbookJson();
        Ebook ebook = ebookJson.readEbookJson(ebookid + ".json");

        String pathArquivo = PastaEpub.DB + File.separator 
                + ebookid
                + File.separator
                + PastaEpub.EPUB.OEBPS.getDescricao()
                + File.separator;

        docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        //Elemento root
        Element ncx = doc.createElement("ncx");
        ncx.setAttribute("xmlns", "http://www.daisy.org/z3986/2005/ncx/");
        ncx.setAttribute("version", "2005-1");
        doc.appendChild(ncx);

        Element head = doc.createElement("head");
        Element meta1 = doc.createElement("meta");
        meta1.setAttribute("name", "dtb:uid");
        meta1.setAttribute("content", "urn:uuid:0cc33cbd-94e2-49c1-909a-72ae16bc2658");
        Element meta2 = doc.createElement("meta");
        meta2.setAttribute("name", "dtb:depth");
        meta2.setAttribute("content", "1");
        Element meta3 = doc.createElement("meta");
        meta3.setAttribute("name", "dtb:totalPageCount");
        meta3.setAttribute("content", "0");
        Element meta4 = doc.createElement("meta");
        meta4.setAttribute("name", "dtb:maxPageNumber");
        meta4.setAttribute("content", "0");
        head.appendChild(meta1);
        head.appendChild(meta2);
        head.appendChild(meta3);
        head.appendChild(meta4);
        ncx.appendChild(head);

        Element docTitle = doc.createElement("docTitle");
        Element docText = doc.createElement("text");
        docText.setTextContent(ebook.getTitulo());
        docTitle.appendChild(docText);
        ncx.appendChild(docTitle);

        Element navMap = doc.createElement("navMap");

        File pastaRecursos = new File(PastaEpub.DB + File.separator + ebookid + File.separator + PastaEpub.OEBPS.getDescricao());
        File[] listaRecursos = pastaRecursos.listFiles();
        int tocId = 1;
        int idEixo = 0;
        int indexNav = 1;
        for (File recurso : listaRecursos) {

            String nomeRecurso = recurso.getName();
            
            if (ebook.getEixos().size() > idEixo && recurso.getName().contains("xhtml") && !(recurso.getName().equals("cover.xhtml"))) {
                Element navPoint = doc.createElement("navPoint");
                navPoint.setAttribute("id", "navPoint" + "-" + indexNav);
                navPoint.setAttribute("playOrder", String.valueOf(indexNav));
                Element navLabel = doc.createElement("navLabel");
                Element text = doc.createElement("text");

                text.setTextContent(ebook.getEixos().get(idEixo).getTitulo().toUpperCase());

                navLabel.appendChild(text);
                Element content = doc.createElement("content");
                content.setAttribute("src", nomeRecurso);
                navPoint.appendChild(navLabel);
                navPoint.appendChild(content);
                navMap.appendChild(navPoint);
                indexNav++;
               
                
                //teste 
                for (Indicador object : ebook.getEixos().get(idEixo).getIndicadores()) {
                    Element navPointIndicador = doc.createElement("navPoint");
                    navPointIndicador.setAttribute("id", "navPoint" + "-" + indexNav);
                    navPointIndicador.setAttribute("playOrder", String.valueOf(indexNav));
                    Element navLabelIndicador = doc.createElement("navLabel");
                    Element textIndicador = doc.createElement("text");

                    textIndicador.setTextContent(object.getTitulo());

                    navLabelIndicador.appendChild(textIndicador);
                    Element contentIndicador = doc.createElement("content");
                    contentIndicador.setAttribute("src", nomeRecurso+"#"+tocId);
                    navPointIndicador.appendChild(navLabelIndicador);
                    navPointIndicador.appendChild(contentIndicador);
                    navMap.appendChild(navPointIndicador);
                    indexNav++;
                    tocId++;
                }
                idEixo++;
            }
        }

        ncx.appendChild(navMap);

        //Document para Texto:
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        DOMImplementation domImpl = doc.getImplementation();
        DocumentType doctype = domImpl.createDocumentType("doctype",
                "-//NISO//DTD ncx 2005-1//EN",
                "http://www.daisy.org/z3986/2005/ncx-2005-1.dtd");

        transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
                doctype.getPublicId());
        transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
                doctype.getSystemId());

        transformer.transform(new DOMSource(doc), new StreamResult(new File(pathArquivo + "toc.ncx")));

    }

    public void gerarContentOPF(String ebookid, List<Eixo> eixos, String capa) throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {

        EbookJson ebookJson = new EbookJson();
        Ebook ebook = ebookJson.readEbookJson(ebookid + ".json");
        String bookuid = UUID.randomUUID().toString();

        String pathArquivo = PastaEpub.DB + File.separator 
                + ebookid
                + File.separator
                + PastaEpub.EPUB.OEBPS.getDescricao()
                + File.separator;

        docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        //Elemento root
        Element packageElement = doc.createElement("package");
        packageElement.setAttribute("xmlns", "http://www.idpf.org/2007/opf");
        packageElement.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
        packageElement.setAttribute("unique-identifier", "bookid");
        packageElement.setAttribute("version", "2.0");
        doc.appendChild(packageElement);

        /* Metadados */
        Element metadata = doc.createElement("metadata");

        Element title = doc.createElement("dc:title");
        title.setTextContent(ebook.getTitulo());

        Element identifier = doc.createElement("dc:identifier");
        identifier.setAttribute("id", "bookid");
        identifier.setTextContent(bookuid);
        

        Element creator = doc.createElement("dc:creator");
        creator.setTextContent("UNIFENAS");

        Element language = doc.createElement("dc:language");
        language.setTextContent("pt-BR");

        Element meta = doc.createElement("meta");
        meta.setAttribute("content", capa);
        meta.setAttribute("name", "cover");

        metadata.appendChild(title);
        metadata.appendChild(identifier);
        metadata.appendChild(creator);
        metadata.appendChild(language);
        metadata.appendChild(meta);

        packageElement.appendChild(metadata);
        /* Fim Metadados */

        Element manifest = doc.createElement("manifest");
        Element itemncx = doc.createElement("item");
        itemncx.setAttribute("id", "ncx");
        itemncx.setAttribute("href", "toc.ncx");
        itemncx.setAttribute("media-type", "application/x-dtbncx+xml");
        manifest.appendChild(itemncx);

        Element spine = doc.createElement("spine");
        spine.setAttribute("toc", "ncx");

        File pastaRecursos = new File(PastaEpub.DB + File.separator + ebookid + File.separator + PastaEpub.OEBPS.getDescricao());
        File[] listaRecursos = pastaRecursos.listFiles();

        for (File recurso : listaRecursos) {

            if (!(recurso.getName().contains("toc") || recurso.getName().contains("opf"))) {

                String nomeRecurso = recurso.getName();
                Element item = doc.createElement("item");
                item.setAttribute("id", nomeRecurso);
                item.setAttribute("href", nomeRecurso);
                item.setAttribute("media-type", IdentificaMimetype(nomeRecurso));
                manifest.appendChild(item);

                if (recurso.getName().contains("xhtml")) {
                    Element itemref = doc.createElement("itemref");
                    itemref.setAttribute("idref", nomeRecurso);
                    spine.appendChild(itemref);
                }
            }
        }

        packageElement.appendChild(manifest);
        packageElement.appendChild(spine);

        //Document para Texto:
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        transformer.transform(new DOMSource(doc), new StreamResult(new File(pathArquivo + "content.opf")));

    }

    private String IdentificaMimetype(String nomeRecurso) {
        String extensao = nomeRecurso.substring(nomeRecurso.lastIndexOf("."));
        String mime = "";
        switch (extensao) {
            case ".xhtml":
                mime = "application/xhtml+xml";
                break;
            case ".jpg":
                mime = "image/jpeg";
                break;
            case ".png":
                mime = "image/png";
                break;
            default:
                break;

        }
        return mime;
    }

}
