/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ned.ceavil.sistema;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author mauricio.junior
 */
public class TestaZip {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Collection<Path> entradasEbook = new ArrayList<>();

        try {

            Path diretorioEbook = Paths.get("epubEaDnaUNIFENAS");
            Files.walk(diretorioEbook).filter(Files::isRegularFile).forEach(e -> entradasEbook.add(e));

            File zipFileName = Paths.get("epubEaDnaUNIFENAS.zip").toFile();
            ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFileName));

            entradasEbook.forEach((path) -> {

                //addToZipFile(path.sub .substring( diretorioEbook.getFileName().toString().length()+1 ), arquivoMimetype, zipStream);
                //System.out.println(path);
                System.out.println(path.toString().substring(diretorioEbook.getFileName().toString().length() + 1));

                String nomeEntrada = path.toString().substring(diretorioEbook.getFileName().toString().length() + 1);

                addToZipFile(nomeEntrada, path.toFile(), zipStream);

            });

            zipStream.close();
            zipStream.finish();

        } catch (IOException ex) {
            Logger.getLogger(TestaZip.class.getName()).log(Level.SEVERE, null, ex);
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
