package ned.ceavil.sistema.forms;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ned.ceavil.modelos.Ebook;
import ned.ceavil.modelos.EbookJson;
import ned.ceavil.modelos.Eixo;
import ned.ceavil.modelos.ImagemClass;
import ned.ceavil.modelos.Indicador;
import ned.ceavil.modelos.Utils;

/**
 *
 * @author rodrigo.brito
 */
public class FmrView extends javax.swing.JFrame {
    private final ImagemClass image = new ImagemClass();
    private static Ebook ebook = new Ebook();
    private final EbookJson ebookJson = new EbookJson();
    private static String ebookCapa;
    private FmrWorkspace form;
    private JPanel painel;
    private static final Eixo eixo = new Eixo();
    private static final Indicador indicador = new Indicador();
    private static JLabel labelRef;
    private boolean capa, infografico;
    /**
     * Creates new form FmrView
     */
    private FmrView() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/IconeEpub.png")).getImage());
    }
    
    //Contrutor View Capa
    public FmrView(FmrWorkspace form, String id, JLabel label) throws IOException{
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/IconeEpub.png")).getImage());
        this.setLocationRelativeTo(null);
        this.form = form;
        labelRef = label;
        ebook.setId(id);
        ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
        jLblImagem.setIcon(new ImageIcon(ebook.getCaminhoCapa()));
        capa = true;
    }
    //Construtor View Info
    public FmrView(JPanel painel,Integer EixoId, Integer IndicadorId, String ebookId, JLabel label, int tipo) throws IOException {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/IconeEpub.png")).getImage());
        this.setLocationRelativeTo(null);
        ebook = ebookJson.readEbookJson(ebookId + ".json");
        eixo.setId(EixoId);
        labelRef = label;
        this.painel = painel;
        //Verificar se é indicador
        if(IndicadorId != null){
            if(tipo == 1){
                indicador.setId(IndicadorId);
                jLblImagem.setIcon(new ImageIcon(ebook.getEixos().get(EixoId).getIndicadores().get(IndicadorId).getCaminhoImagem()));
                infografico = true;
            }else {
                indicador.setId(IndicadorId);
                jLblImagem.setIcon(new ImageIcon(ebook.getEixos().get(EixoId).getIndicadores().get(IndicadorId).getCaminhoTimeLine()));
            }
        }else   jLblImagem.setIcon(new ImageIcon(ebook.getEixos().get(EixoId).getCaminhoImagem()));
        capa = false;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void Alterar(){
        try {
            ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
            if(image.FindImg() != null){    
                String nomeInfo = "x" + Utils.removerAcentosEspacos(image.getFileImagem().getName());
                if (JOptionPane.showConfirmDialog(null, "Deseja realmente alterar?", "Alterar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    if(capa){
                        File file = new File(Utils.PathDestino(ebook.getId(), ebook.getCapa()));
                        Utils.deleteDirectoryStream(file.toPath());
                        jLblImagem.setIcon(new ImageIcon(image.getCaminho()));
                        labelRef.setIcon(image.IconeCapa(image.getCaminho()));
                        form.setEbookCapa(image.getCaminho());
                        ebook.setCapa(nomeInfo);
                        ebookCapa = image.getCaminho();
                        ebook.setCaminhoCapa(ebookCapa);
                        Utils.copiarArquivos(image.getFileImagem(), new File(Utils.PathDestino(ebook.getId(), nomeInfo)));
                        ebookJson.generateEbookJson(ebook);
                    }else{
                        File file;
                        //Verrificar se é o indicador
                        if(indicador.getId() != null){
                            if(infografico){
                                file = new File(Utils.PathDestino(ebook.getId(), ebook.getEixos().get(eixo.getId()).getIndicadores().get(indicador.getId()).getNomeImagem()));
                                Utils.deleteDirectoryStream(file.toPath());                        
                                ebook.getEixos().get(eixo.getId()).getIndicadores().get(indicador.getId()).setCaminhoImagem(image.getCaminho());
                                ebook.getEixos().get(eixo.getId()).getIndicadores().get(indicador.getId()).setNomeImagem(nomeInfo);
                            }else{
                                file = new File(Utils.PathDestino(ebook.getId(), ebook.getEixos().get(eixo.getId()).getIndicadores().get(indicador.getId()).getNomeTimeLine()));
                                Utils.deleteDirectoryStream(file.toPath());                        
                                ebook.getEixos().get(eixo.getId()).getIndicadores().get(indicador.getId()).setCaminhoTimeLine(image.getCaminho());
                                ebook.getEixos().get(eixo.getId()).getIndicadores().get(indicador.getId()).setNomeTimeLine(nomeInfo);
                            }
                        }else if(eixo.getId() != null){
                            file = new File(Utils.PathDestino(ebook.getId(), ebook.getEixos().get(eixo.getId()).getNomeImagem()));
                            Utils.deleteDirectoryStream(file.toPath());
                            ebook.getEixos().get(eixo.getId()).setCaminhoImagem(image.getCaminho());
                            ebook.getEixos().get(eixo.getId()).setNomeImagem(nomeInfo);
                        }
                        labelRef.setIcon(image.IconeInfografico(image.getCaminho()));
                        jLblImagem.setIcon(new ImageIcon(image.getCaminho()));
                        ebookJson.generateEbookJson(ebook);
                        Utils.copiarArquivos(image.getFileImagem(), new File(Utils.PathDestino(ebook.getId(), nomeInfo)));
                    }
                }    
            }
        } catch (IOException ex) {
            Logger.getLogger(FmrNewInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void Sair(){
        if(form != null)
            form.ActivateButtonView();
        else if(painel != null)
            if(painel instanceof JPanelEixo)
                ((JPanelEixo)painel).ActivateButtonView();
            else ((JPanelIndicador)painel).ActivateButtonView();
        this.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jLblImagem = new javax.swing.JLabel();
        btnSair = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLblImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScrollPane1.setViewportView(jLblImagem);

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeCancelar.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeImagem.png"))); // NOI18N
        btnAlterar.setText("Alterar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair)
                    .addComponent(btnAlterar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        Sair();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed
        Alterar();
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Sair();
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FmrView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FmrView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FmrView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FmrView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FmrView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLblImagem;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}