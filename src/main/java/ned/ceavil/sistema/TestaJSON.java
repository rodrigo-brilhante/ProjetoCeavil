package ned.ceavil.sistema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import ned.ceavil.modelos.ArquivoEpub;
import ned.ceavil.modelos.Ebook;
import ned.ceavil.modelos.Eixo;
import ned.ceavil.modelos.Indicador;
import ned.ceavil.modelos.PastaEpub;

/**
 *
 * @author mauricio.junior
 */
public class TestaJSON {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Indicador indicador11 = new Indicador();
        indicador11.setTitulo("1.1 Projeto de autoavaliaçao");
        indicador11.setDescricao("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin at gravida leo. Morbi nunc ligula, pulvinar laoreet risus nec, aliquet vestibulum ipsum. Pellentesque in pharetra arcu, sit amet euismod dui. Ut tristique cursus massa. Quisque tincidunt varius risus non rutrum. Nam id ipsum ut libero convallis ultricies. In in leo ac sem varius auctor eget eget lacus. Mauris porta posuere enim, in consequat nulla convallis ut. Mauris accumsan justo in lorem placerat, quis tristique nunc molestie. In viverra aliquam augue ut laoreet. In sed lorem erat. Aliquam eget augue tempus, tincidunt orci ac, mollis ante. Fusce facilisis quam ut ante pharetra, et cursus odio vulputate.");
        indicador11.setNomeImagem("info-indicador11.jpg");
        String[] localizacao = new String[3];
        localizacao[0] = "10";
        localizacao[1] = "1.1";
        localizacao[2] = "Pasta XYZ";
        //indicador11.setLocalizacao(localizacao);

        Indicador indicador12 = new Indicador();
        indicador12.setTitulo("1.2 Projeto de muita coisa");
        indicador12.setDescricao("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin at gravida leo. Morbi nunc ligula, pulvinar laoreet risus nec, aliquet vestibulum ipsum. Pellentesque in pharetra arcu, sit amet euismod dui. Ut tristique cursus massa. Quisque tincidunt varius risus non rutrum. Nam id ipsum ut libero convallis ultricies. In in leo ac sem varius auctor eget eget lacus. Mauris porta posuere enim, in consequat nulla convallis ut. Mauris accumsan justo in lorem placerat, quis tristique nunc molestie. In viverra aliquam augue ut laoreet. In sed lorem erat. Aliquam eget augue tempus, tincidunt orci ac, mollis ante. Fusce facilisis quam ut ante pharetra, et cursus odio vulputate.");
        indicador12.setNomeImagem("info-indicador12.jpg");
        localizacao = new String[3];
        localizacao[0] = "15";
        localizacao[1] = "1.2";
        localizacao[2] = "Pasta XYZ4";
        //indicador12.setLocalizacao(localizacao);

        Eixo eixo1 = new Eixo();
        eixo1.setTitulo("Eixo 1 - Planejamento e Avaliação institucional");
        eixo1.setNomeImagem("imagem-eixo.jpg");
        eixo1.setIndicador(indicador11);
        eixo1.setIndicador(indicador12);

        Eixo eixo2 = new Eixo();
        eixo2.setTitulo("Eixo 2 - Desenvolvimento Institucional");
        eixo2.setNomeImagem("imagem-eixo2.jpg");
        eixo2.setIndicador(indicador11);
        eixo2.setIndicador(indicador12);

        Ebook ebook1 = new Ebook();
        ebook1.setTitulo("EaD na UNIFENAS");
        ebook1.setCapa("capa-ebook.jpg");
        ebook1.setEixo(eixo1);
        ebook1.setEixo(eixo2);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonStr = new String();
        try {
            jsonStr = objectMapper.writeValueAsString(ebook1);

        } catch (JsonProcessingException ex) {
            Logger.getLogger(TestaJSON.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            OutputStreamWriter bufferOut = new OutputStreamWriter(new FileOutputStream("epub1.json"), "UTF-8");
            bufferOut.write(jsonStr);
            bufferOut.flush();
            bufferOut.close();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        ObjectMapper jsonMap = new ObjectMapper();

        InputStream input;
        try {
            input = new FileInputStream("epub1.json");
            Ebook ebookFile = jsonMap.readValue(input, Ebook.class);
            input.close();
            System.out.println(ebook1.getTitulo());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //Criar a estrutura de pastas para o epub:
        //Pasta para o Epub
        File pastaEbook = new File(PastaEpub.EPUB.getDescricao() + "x");
        pastaEbook.mkdir();

        //Pasta META-INF
        File pastaMetainf = new File(pastaEbook.getName() + File.separator + PastaEpub.METAINF.getDescricao());
        pastaMetainf.mkdir();

        //Pasta OEBPS
        File pastaOEBPS = new File(pastaEbook.getName() + File.separator + PastaEpub.OEBPS.getDescricao());
        pastaOEBPS.mkdir();

        //Criacao de arquivos
        try {
            File arquivoMimetype = new File(pastaEbook.getName() + File.separator + ArquivoEpub.MIMETYPE.getDescricao());
            arquivoMimetype.createNewFile();

            File arquivoContainer = new File(pastaMetainf.getPath() + File.separator + ArquivoEpub.CONTAINER.getDescricao());
            arquivoContainer.createNewFile();

            File arquivoContent = new File(pastaOEBPS.getPath() + File.separator + ArquivoEpub.CONTENT.getDescricao());
            File arquivoToc = new File(pastaOEBPS.getPath() + File.separator + ArquivoEpub.TOC.getDescricao());
            arquivoContent.createNewFile();
            arquivoToc.createNewFile();

            // Empacotamento para zip
            Path diretorioEbook = Paths.get(pastaEbook.getName());

            File zipFileName = Paths.get("oepubx.epub").toFile();

            ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFileName));

            String can = arquivoContent.getPath();
            String str = arquivoContainer.getParent();
            

                        
            addToZipFile(arquivoMimetype.getPath().substring( pastaEbook.getName().length()+1 ), arquivoMimetype, zipStream);

            addToZipFile(arquivoContainer.getPath().substring( pastaEbook.getName().length()+1 ), arquivoContainer, zipStream);

            addToZipFile(arquivoContent.getPath().substring( pastaEbook.getName().length()+1 ), arquivoContent, zipStream);

            zipStream.close();
            zipStream.finish();


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static void addToZipFile(String nomeEntrada, File file, ZipOutputStream zipStream) {
        String inputFileName = file.getPath();

        try (FileInputStream inputStream = new FileInputStream(inputFileName)) {

            ZipEntry entrada = new ZipEntry(nomeEntrada);
            entrada.setCreationTime(FileTime.fromMillis(file.lastModified()));
            zipStream.putNextEntry(entrada);

            byte[] readBuffer = new byte[2048];
            int amountRead;
            int written = 0;

            while ((amountRead = inputStream.read(readBuffer)) > 0) {
                zipStream.write(readBuffer, 0, amountRead);
                written += amountRead;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
