package ned.ceavil.modelos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;


/**
 * @author mauricio.junior
 */
public class EbookJson {

    ObjectMapper objectMapperJson;
    String ebookJsonString;

    public EbookJson() {
        objectMapperJson = new ObjectMapper();
    }

    /**
     * Gerar arquivo Json a partir de um objeto Ebook
     * @param ebook
     * @throws JsonProcessingException
     * @throws IOException 
     */
    public void generateEbookJson(Ebook ebook) throws JsonProcessingException, IOException {
        ebookJsonString = objectMapperJson.writeValueAsString(ebook);
        String nomeArquivoJson = ebook.getId();
        try (OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream(PastaEpub.DB + File.separator + nomeArquivoJson + ".json"), "UTF-8")) {
            bufferOut.write(ebookJsonString);
        }
    }
    
    /**
     * Ler arquivo Json
     * @param fileNameJson
     * @return Objeto Ebook
     * @throws IOException 
     */
    public Ebook readEbookJson(String fileNameJson) throws IOException{
        try (InputStream input = new FileInputStream(PastaEpub.DB + File.separator + fileNameJson)) {
           return objectMapperJson.readValue(input, Ebook.class);
        }
    }
}
