package ned.ceavil.modelos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.nio.file.Paths;
import java.util.zip.ZipOutputStream;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;

/**
 * @author rodrigo.brito
 * @author mauricio.junior
 */
public class EbookFile {

    final String DIR_SOURCES = "sources";
    String nomePastaEbook;
    private File caminhoFinal;

    public File getCaminhoFinal() {
        return caminhoFinal;
    }
    
    public String getNomePastaEbook() {
        return nomePastaEbook;
    }

    public void setNomePastaEbook(String nomePastaEbook) {
        this.nomePastaEbook = nomePastaEbook;
    }

    //pasta onde ira armazenar todo arquivos.json existentes
    public void criarPastaPrincipal(){
        File pastaPrincipal = new File(PastaEpub.DB.toString());
        pastaPrincipal.mkdir();
    }
    
    public void criarEstuturaPastas(String nomePastaEbook) {
        criarPastaEbook(nomePastaEbook);
        criarPastaMetaInf();
        criarPastaOEBPS();
    }

    public void criarEstruturaAquivos() throws IOException {
//        File Container = new File(PastaEpub.DB.toString() + File.separator+ this.nomePastaEbook + File.separator + PastaEpub.METAINF.getDescricao()+ File.separator+"container.xml");
//        File Content = new File(PastaEpub.DB.toString() + File.separator+ this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao()+ File.separator + "content.opf");
//        File Toc = new File(PastaEpub.DB.toString() + File.separator+ this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao()+ File.separator+"toc.ncx");
////        File Html = new File(PastaEpub.DB.toString() + File.separator+ this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao()+ File.separator + "eixo" + 5 + ".html");
//        
//        Utils.deleteDirectoryStream(Container.toPath());
//        Utils.deleteDirectoryStream(Content.toPath());
//        Utils.deleteDirectoryStream(Toc.toPath());
        //Não está encontando o eixo
//        Utils.deleteDirectoryStream(Html.toPath());
        
        criarArquivoContainer(this.nomePastaEbook + File.separator + PastaEpub.METAINF.getDescricao());
        criarArquivoContent(this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao());
        criarArquivoToc(this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao());
        criarArquivoCSS(this.nomePastaEbook + File.separator + PastaEpub.OEBPS.getDescricao());
    }

    private void criarPastaEbook(String nomePastaEbook) {
        File pastaEbook = new File(PastaEpub.DB +File.separator + nomePastaEbook);
        pastaEbook.mkdir();
        this.nomePastaEbook = pastaEbook.getName();
    }

    private void criarPastaMetaInf() {
        File pastaMetainf = new File(Utils.PathFile(this.nomePastaEbook, PastaEpub.METAINF.getDescricao()));
        pastaMetainf.mkdir();
    }

    private void criarPastaOEBPS() {
        File pastaOEBPS = new File(Utils.PathFile(this.nomePastaEbook, PastaEpub.OEBPS.getDescricao()));
        pastaOEBPS.mkdir();
    }

    private void criarArquivoContainer(String pathPastaMetainf) throws IOException {
        File arquivoContainer = new File(Utils.PathFile(pathPastaMetainf, ArquivoEpub.CONTAINER.getDescricao()));
        arquivoContainer.createNewFile();
    }

    private void criarArquivoContent(String pathPastaOEBPS) throws IOException {
        File arquivoContent = new File(Utils.PathFile(pathPastaOEBPS, ArquivoEpub.CONTENT.getDescricao()));
        arquivoContent.createNewFile();
    }

    private void criarArquivoToc(String pathPastaOEBPS) throws IOException {
        File arquivoToc = new File(Utils.PathFile(pathPastaOEBPS, ArquivoEpub.TOC.getDescricao()));
        arquivoToc.createNewFile();
    }
    
    private void criarArquivoCSS(String pathPastaOEBPS) throws IOException{
        File arquivoCSS = new File (Utils.PathFile(pathPastaOEBPS, ArquivoEpub.CSS.getDescricao()));
        arquivoCSS.createNewFile();
        
        try (FileWriter arquivo = new FileWriter(arquivoCSS)) {
            PrintWriter gravarArquivo = new PrintWriter(arquivo);
            gravarArquivo.print("td {\n  text-aling: center;\n}\n");
        }
    }

    public void empacotarEbook(String ebookid, String ebookNome) throws IOException, FileNotFoundException {
        Path diretorioEbook = Paths.get(PastaEpub.DB + File.separator + ebookid);
        File zipFile = Paths.get(ebookNome + ".epub").toFile();
        caminhoFinal = new File(zipFile.getPath());
        //Zip Mimetype
        try (ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            //Zip Mimetype
            zipMimetype(zipStream);
            //Zip Container
            zipContainer(zipStream);
            //Recursos - OEBPS//
            zipRecursos(diretorioEbook, zipStream);
            //zipStream.finish();
        }
    }

    private void zipMimetype(ZipOutputStream zipStream) throws UnsupportedEncodingException, IOException {
        //Mimetype//
        byte[] content = "application/epub+zip".getBytes("ASCII");
        ZipEntry entry = new ZipEntry("mimetype");
        entry.setMethod(ZipOutputStream.STORED);
        entry.setSize(content.length);
        entry.setCompressedSize(content.length);
        CRC32 crc = new CRC32();
        crc.update(content);
        entry.setCrc(crc.getValue());
        zipStream.putNextEntry(entry);
        zipStream.write(content);
        zipStream.closeEntry();
    }

    private void zipContainer(ZipOutputStream zipStream) throws IOException {

        //Container//
        zipStream.putNextEntry(new ZipEntry("META-INF/container.xml"));
        Writer out = new OutputStreamWriter(zipStream);
        out.write("<?xml version=\"1.0\"?>\n");
        out.write("<container version=\"1.0\" xmlns=\"urn:oasis:names:tc:opendocument:xmlns:container\">\n");
        out.write("\t<rootfiles>\n");
        out.write("\t\t<rootfile full-path=\"OEBPS/content.opf\" media-type=\"application/oebps-package+xml\"/>\n");
        out.write("\t</rootfiles>\n");
        out.write("</container>");
        out.flush();

    }

    private void zipRecursos(Path diretorioEbook, ZipOutputStream zipStream) throws IOException {
        File pastaRecursos = new File(diretorioEbook.toString() + File.separator + PastaEpub.OEBPS.getDescricao());
        File[] listOfFiles = pastaRecursos.listFiles();

        for (File fileToZip : listOfFiles) {
            addToZipFile("OEBPS/" + fileToZip.getName(), fileToZip, zipStream);
        }
    }

    private void addToZipFile(String nomeEntrada, File file, ZipOutputStream zipStream) throws IOException {
        String inputFileName = file.getPath();

        try (FileInputStream inputStream = new FileInputStream(inputFileName)) {

            ZipEntry entrada = new ZipEntry(nomeEntrada);
            entrada.setCreationTime(FileTime.fromMillis(file.lastModified()));
            zipStream.putNextEntry(entrada);

            byte[] readBuffer = new byte[2048];
            int amountRead;

            while ((amountRead = inputStream.read(readBuffer)) > 0) {
                zipStream.write(readBuffer, 0, amountRead);
            }
            zipStream.closeEntry();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> listarJson(){
        File pasta = new File(PastaEpub.DB.toString());
        List<String> lista = new ArrayList<>();
        for (Object object : pasta.listFiles()) {
            if(object.toString().endsWith(".json"))
                lista.add(object.toString());
        }
        return lista;
    }
}
