package ned.ceavil.sistema.forms;

import ned.ceavil.modelos.ImagemClass;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import ned.ceavil.modelos.Ebook;
import ned.ceavil.modelos.EbookFile;
import ned.ceavil.modelos.EbookJson;
import ned.ceavil.modelos.Utils;

/**
 * @author mauricio.junior
 * @author rodrigo.brito
 */
public class TelaNovo extends javax.swing.JFrame {
    private final EbookFile ebookFile = new EbookFile();
    EbookJson ebookjson = new EbookJson();
    private Ebook newEbook;
    private final ImagemClass imagem = new ImagemClass();
    private final List<String> enderecos = new ArrayList<>();

    public TelaNovo() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/IconeEpub.png")).getImage());
        desativa();
        ebookFile.criarPastaPrincipal();
        carregarArquivos(ebookFile);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        btApagar = new javax.swing.JButton();
        btEditar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        btSair = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btNovoEbook = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerador Ebook");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        btApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeDelete.png"))); // NOI18N
        btApagar.setText("Apagar");
        btApagar.setToolTipText("Excluir Ebook selecionado");
        btApagar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btApagarActionPerformed(evt);
            }
        });

        btEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeEditar.png"))); // NOI18N
        btEditar.setText("Editar");
        btEditar.setToolTipText("Abrir Ebook selecionado");
        btEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditarActionPerformed(evt);
            }
        });

        jLabel4.setText("Ebooks");

        jList1.setBackground(new java.awt.Color(240, 240, 240));
        jList1.setToolTipText("Click para selecionar o Ebook");
        jList1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jList1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeCancelar.png"))); // NOI18N
        btSair.setText("Sair     ");
        btSair.setToolTipText("Excluir Ebook selecionado");
        btSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btNovoEbook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeNew.png"))); // NOI18N
        btNovoEbook.setText("Novo");
        btNovoEbook.setToolTipText("Criar um novo Ebook");
        btNovoEbook.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btNovoEbook.setPreferredSize(new java.awt.Dimension(87, 39));
        btNovoEbook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoEbookActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btNovoEbook, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(btNovoEbook, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void carregarArquivos(EbookFile ebook){
        enderecos.clear();
        DefaultListModel modelo = new DefaultListModel();
        for (String string : ebook.listarJson()) {
            enderecos.add(string);
            try {
                Ebook EBook = new Ebook();
                EBook = ebookjson.readEbookJson(string.substring(3));
                modelo.addElement(EBook.getTitulo());
            } catch (IOException ex) {
                Logger.getLogger(TelaNovo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jList1.setModel(modelo);
    }
    
    //Habilitar botões de editar e apagar
    private void ativa(){
        btApagar.setEnabled(true);
        btEditar.setEnabled(true);
    }
    //Desabilitar botões de editar e apagar
    private void desativa(){
        btApagar.setEnabled(false);
        btEditar.setEnabled(false);
    }
    
    public void CreateEbook(){
        EbookJson jsonEbook = new EbookJson();
        try {
            //Gerar EbookJson
            jsonEbook.generateEbookJson(newEbook);
            //Gerar Estrutura de pastas
            EbookFile arquivoEbook = new EbookFile();
            arquivoEbook.criarEstuturaPastas(newEbook.getId());
            arquivoEbook.criarEstruturaAquivos();
        } catch (IOException ex) {
            Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        Utils.OpenWorkSpace(newEbook, this);
    }
    
    private Ebook GetSelectedEbook(){
        try {
            int index = jList1.getSelectedIndex();
            return ebookjson.readEbookJson(enderecos.get(index).substring(3));
        } catch (IOException ex) {
            Logger.getLogger(TelaNovo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void btNovoEbookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoEbookActionPerformed
        FmrNewCover frame = new FmrNewCover(this);
        frame.setVisible(true);
    }//GEN-LAST:event_btNovoEbookActionPerformed

    private void btEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditarActionPerformed
        FmrNewCover frame = new FmrNewCover(this, GetSelectedEbook());
        frame.setVisible(true);
    }//GEN-LAST:event_btEditarActionPerformed

    private void btApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btApagarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Excluir", JOptionPane.YES_NO_OPTION, ERROR_MESSAGE);

        if (resposta == JOptionPane.YES_OPTION) {
            int index = jList1.getSelectedIndex();
            File path1 = new File(enderecos.get(index));
            File path2 = new File(enderecos.get(index).substring(0,39));
            try {
                Utils.deleteDirectoryStream(path1.toPath());
                Utils.deleteDirectoryStream(path2.toPath());
                carregarArquivos(ebookFile);
                if (jList1.getModel().getSize() == 0) desativa();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(rootPane, "Ebook não foi exluido");
                Logger.getLogger(TelaNovo.class.getName()).log(Level.SEVERE, null, ex);
            }
            carregarArquivos(ebookFile);
        }
    }//GEN-LAST:event_btApagarActionPerformed

    private void jList1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseReleased
        if(!jList1.isSelectionEmpty()) 
            ativa();
    }//GEN-LAST:event_jList1MouseReleased

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        if (evt.getClickCount()== 2 && !jList1.isSelectionEmpty() && evt.getButton() == 1)
            Utils.OpenWorkSpace(GetSelectedEbook(), this);
    }//GEN-LAST:event_jList1MouseClicked

    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        this.dispose();
    }//GEN-LAST:event_btSairActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaNovo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaNovo().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btApagar;
    private javax.swing.JButton btEditar;
    private javax.swing.JButton btNovoEbook;
    private javax.swing.JButton btSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
