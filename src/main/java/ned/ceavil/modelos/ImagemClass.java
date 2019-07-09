package ned.ceavil.modelos;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author rodrigo.brito
 */
public class ImagemClass {

    public ImagemClass() {
        this.fileImagem = null;
    }

    private boolean ok;

    public boolean isOk() {
        return ok;
    }
    private File fileImagem;
    private String caminho;

    public void setCaminho(String caminho){
        this.caminho = caminho;
    }
    public String getCaminho() {
        return caminho;
    }
    
    public File getFileImagem() {
        return fileImagem;
    }
    public void setFileImagem(File fileImagem) {
        this.fileImagem = fileImagem;
    }
    
 
    private Image BuildImg(Integer tipo,String caminho){
        ImageIcon imagem = new ImageIcon(caminho);
        switch (tipo){
            case 1://Fluxograma
                return imagem.getImage().getScaledInstance(309, 211, Image.SCALE_DEFAULT);
            case 2://Capa
                return imagem.getImage().getScaledInstance(126, 201 , Image.SCALE_DEFAULT);
        }
        return null;
    }
    
    public ImageIcon FindImg() {
        ImageIcon imagem = null;
        try {
            JFileChooser FileImagem = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
            FileImagem.setFileFilter(filter);
            int returnVal = FileImagem.showOpenDialog(FileImagem);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                imagem = new ImageIcon(BuildImg(1,FileImagem.getCurrentDirectory().getPath() + File.separator + FileImagem.getSelectedFile().getName()));
                caminho = FileImagem.getCurrentDirectory().getPath() + File.separator + FileImagem.getSelectedFile().getName();
                fileImagem = FileImagem.getSelectedFile();
                ok = true;
            }else ok = false;
        } catch (Exception e) {
            e.printStackTrace();
            ok = false;
        }
        return imagem;
    }
    
    public ImageIcon IconeInfografico(String Caminho){
        return new ImageIcon(BuildImg(1, Caminho));
    }
    
    public ImageIcon IconeCapa(String Caminho){
        return new ImageIcon(BuildImg(2, Caminho));
    }
}
