package ned.ceavil.modelos;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import ned.ceavil.sistema.forms.FmrWorkspace;
import ned.ceavil.sistema.forms.JPanelIndicador;
import ned.ceavil.sistema.forms.TelaNovo;

/**
 *@author mauricio.junior
 *@author rodrigo.brito
 */
public class Utils {

    public static String removerAcentosEspacos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll(" +", "");
    }

    public static void copiarArquivos(File source, File destination) throws IOException {
        if (destination.exists()) {
            destination.delete();
        }
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen()) {
                sourceChannel.close();
            }
            if (destinationChannel != null && destinationChannel.isOpen()) {
                destinationChannel.close();
            }
        }
    }

    public static String PathDestino(String ebookId, String nomeImagem){
        return Paths.get(PastaEpub.DB 
                + File.separator
                + ebookId + File.separator
                + "OEBPS"
                + File.separator
                + nomeImagem
            ).toString();
    }
    public static String PathFile(String nomePasta,String pastaEpub){
        return Paths.get(PastaEpub.DB 
            + File.separator
            + nomePasta 
            + File.separator 
            + pastaEpub
        ).toString();
    }  
    
    public static void deleteDirectoryStream(Path path) throws IOException {
        Files.walk(path)
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .forEach(File::delete);
    }
    
    public static void OpenWorkSpace(Ebook ebook, TelaNovo tela){
        FmrWorkspace Frame = new FmrWorkspace();
        Frame.setEbook(ebook);
        Frame.RebuildJtree();
        Frame.setEbookCapa(ebook.getCaminhoCapa());
        Frame.setEbookId(ebook.getId());
        Frame.setVisible(true);
        tela.dispose();        
    }
    
}
     
    