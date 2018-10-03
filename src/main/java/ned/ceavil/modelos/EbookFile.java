package ned.ceavil.modelos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Paths;
import java.util.zip.ZipOutputStream;

import java.nio.file.Path;

/**
 *
 * @author mauricio.junior
 */
public class EbookFile {

    final String DIR_SOURCES = "sources";
    String nomePastaEbook;

    public String getNomePastaEbook() {
        return nomePastaEbook;
    }

    public void setNomePastaEbook(String nomePastaEbook) {
        this.nomePastaEbook = nomePastaEbook;
    }

    public void criarEstuturaPastas(String nomePastaEbook) {
        criarPastaEbook(nomePastaEbook);
        criarPastaMetaInf();
        criarPastaOEBPS();
    }

    public void criarEstruturaAquivos() throws IOException {
        criarArquivoMime(this.nomePastaEbook);
        criarArquivoContainer(this.nomePastaEbook + File.separator + PastaEpub.METAINF.getDescricao());
        criarArquivoContent(this.nomePastaEbook + File.separator + PastaEpub.METAINF.getDescricao());
        criarArquivoToc(this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao());
    }

    private void criarPastaEbook(String nomePastaEbook) {
        File pastaEbook = new File(PastaEpub.EPUB.getDescricao() + nomePastaEbook);
        pastaEbook.mkdir();
        this.nomePastaEbook = pastaEbook.getName();
    }

    private void criarPastaMetaInf() {
        File pastaMetainf = new File(this.nomePastaEbook + File.separator + PastaEpub.METAINF.getDescricao());
        pastaMetainf.mkdir();
    }

    private void criarPastaOEBPS() {
        File pastaOEBPS = new File(this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao());
        pastaOEBPS.mkdir();
    }

    private void criarArquivoMime(String nomePastaEbook) throws IOException {

        File arquivoMimetype = new File(nomePastaEbook + File.separator + ArquivoEpub.MIMETYPE.getDescricao());
        arquivoMimetype.createNewFile();

    }

    private void criarArquivoContainer(String pathPastaMetainf) throws IOException {
        File arquivoContainer = new File(pathPastaMetainf + File.separator + ArquivoEpub.CONTAINER.getDescricao());
        arquivoContainer.createNewFile();
    }

    private void criarArquivoContent(String pathPastaOEBPS) throws IOException {
        File arquivoContent = new File(pathPastaOEBPS + File.separator + ArquivoEpub.CONTENT.getDescricao());
        arquivoContent.createNewFile();
    }

    private void criarArquivoToc(String pathPastaOEBPS) throws IOException {
        File arquivoToc = new File(pathPastaOEBPS + File.separator + ArquivoEpub.TOC.getDescricao());
        arquivoToc.createNewFile();
    }

    /*
    public void empacotarEbook() {

        Path diretorioEbook = Paths.get(this.nomePastaEbook);

        File zipFileName = Paths.get(this.nomePastaEbook + ".epub").toFile();

        ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFileName));

        addToZipFile(arquivoMimetype.getPath().substring(pastaEbook.getName().length() + 1), arquivoMimetype, zipStream);

        addToZipFile(arquivoContainer.getPath().substring(pastaEbook.getName().length() + 1), arquivoContainer, zipStream);

        addToZipFile(arquivoContent.getPath().substring(pastaEbook.getName().length() + 1), arquivoContent, zipStream);

        zipStream.close();
        zipStream.finish();
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
     */
}
