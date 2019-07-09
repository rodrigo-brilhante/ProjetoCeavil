package ned.ceavil.modelos;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import ned.ceavil.sistema.forms.TelaNovo;

/**
 * @author rodrigo.brito
 */
public class Button extends JButton implements ActionListener{
    String nome;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
       nome = e.toString().substring(48,92);
       JOptionPane.showMessageDialog(null, nome);
       TelaNovo t = new TelaNovo();
    }
    
    public Button(String string){
        addActionListener(this);
        setPreferredSize(new Dimension(60,70));
        ImageIcon icone = new ImageIcon("C:\\Users\\rodrigo.brito.UNIFENAS\\Documents\\NetBeansProjects\\Projeto-ceavil\\src\\main\\resources\\epub.png");
        setIcon(icone);
        setText(string);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
    }
}
