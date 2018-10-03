/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ned.ceavil.modelos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import ned.ceavil.sistema.TestaJSON;

/**
 *
 * @author mauricio.junior
 */
public class ControllerJson {

    ObjectMapper objectMapperJson;
    String ebookJsonString;

    public ControllerJson() {
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
        String nomeArquivoJson = Utils.removerAcentosEspacos(ebook.getTitulo());
        try (OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream(nomeArquivoJson + ".json"), "UTF-8")) {
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

        try (InputStream input = new FileInputStream(fileNameJson)) {
           return objectMapperJson.readValue(input, Ebook.class);
        }
           
    }
    

}
