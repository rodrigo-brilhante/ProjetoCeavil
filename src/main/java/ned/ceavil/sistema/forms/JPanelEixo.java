package ned.ceavil.sistema.forms;

import ned.ceavil.modelos.ImagemClass;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import ned.ceavil.modelos.Ebook;
import ned.ceavil.modelos.EbookJson;
import ned.ceavil.modelos.Eixo;
import ned.ceavil.modelos.Utils;

/**
 * @author rodrigo.brito
 */
public class JPanelEixo extends javax.swing.JPanel {
    //Metodo construtor
    public JPanelEixo(FmrWorkspace Frame) {
        initComponents();
        frame = Frame;
        btSalvarEixo.setEnabled(false);
    }
    private final FmrWorkspace frame;
    private final EbookJson ebookJson = new EbookJson();
    private final ImagemClass imagem = new ImagemClass();
    private Ebook ebook;
    private Eixo eixo = new Eixo();
    private String ebookId;
    private Integer idEixo;

    public Integer getIdEixo() {
        return idEixo;
    }
    public void setIdEixo(Integer idEixo) {
        this.idEixo = idEixo - 1;
    }
    
    public String getEbookId() {
        return ebookId;
    }
    public void setEbookId(String ebookId) {
        this.ebookId = ebookId;
    }

    public void setTituloEixo(String nome){
        txtTituloEixo.setText(nome);
    }
    
    public void initLabel(){
        String []caminhoJar = System.getProperty("java.class.path").split(";");
        try {
            ZipFile zipFile;
            zipFile = new ZipFile(caminhoJar[0]);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                String name = zipEntry.getName();
                if (!zipEntry.isDirectory() && name.contains("photo-1103595_1280.png")){ 
                    InputStream is = ClassLoader.getSystemResourceAsStream(name);   
                    Image icone = new ImageIcon(ImageIO.read(is)).getImage();
                    jLabelIlustacaoEixo.setIcon(new ImageIcon(icone));  
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(JPanelIndicador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void carregar(Eixo eixo){
        this.eixo = eixo;
        txtTituloEixo.setText(eixo.getTitulo());
        jLabelIlustacaoEixo.setIcon(imagem.IconeInfografico(eixo.getCaminhoImagem()));
        btSalvarEixo.setEnabled(false);
    }
    
    public void comparaTitulo(){
        char[] fixo = ("Eixo " + (eixo.getId()+1)).toCharArray();
        char[] titulo = txtTituloEixo.getText().toCharArray();
        String nome = txtTituloEixo.getText();
        boolean diferente = false;
        int contador = 0;
        if(titulo.length > fixo.length)
            for (int i = 0; i < fixo.length; i++)
                    if (fixo[i] == titulo[i])
                        contador++;
        
        if(fixo.length == contador){
                String []aux = nome.split("-");
                nome = aux[1];
                diferente = true;
        }else diferente = true;
        
        if (diferente)
            txtTituloEixo.setText("Eixo " + (eixo.getId()+1) +" - " + nome);
    }
    
    public void NewSalvar() throws IOException{
        ebook = ebookJson.readEbookJson(ebookId + ".json");
        comparaTitulo();
        ebook.getEixos().get(eixo.getId()).setTitulo(txtTituloEixo.getText());
        Utils.copiarArquivos(new File(eixo.getCaminhoImagem()), new File(Utils.PathDestino(ebookId, eixo.getNomeImagem())));
        ebookJson.generateEbookJson(ebook);
        frame.atualizar(txtTituloEixo.getText(),this);
        JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        btSalvarEixo.setEnabled(false);
        frame.CloseTabbed();
    }
    
    public void ActivateButtonView(){
        btView.setEnabled(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        btSalvarEixo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTituloEixo = new javax.swing.JTextField();
        painelEixo = new javax.swing.JPanel();
        jLabelIlustacaoEixo = new javax.swing.JLabel();
        btView = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(730, 600));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btSalvarEixo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeSave.png"))); // NOI18N
        btSalvarEixo.setText("Salvar");
        btSalvarEixo.setToolTipText("");
        btSalvarEixo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSalvarEixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarEixoActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Título");

        txtTituloEixo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTituloEixo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTituloEixoKeyPressed(evt);
            }
        });

        painelEixo.setBackground(new java.awt.Color(204, 204, 204));
        painelEixo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelIlustacaoEixo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelIlustacaoEixo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IlustracaoInfo.png"))); // NOI18N
        jLabelIlustacaoEixo.setText("  ");
        jLabelIlustacaoEixo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeVisualizar.png"))); // NOI18N
        btView.setText("Visualizar");
        btView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelEixoLayout = new javax.swing.GroupLayout(painelEixo);
        painelEixo.setLayout(painelEixoLayout);
        painelEixoLayout.setHorizontalGroup(
            painelEixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEixoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelIlustacaoEixo, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btView)
                .addContainerGap())
        );
        painelEixoLayout.setVerticalGroup(
            painelEixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEixoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEixoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEixoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btView))
                    .addComponent(jLabelIlustacaoEixo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btView.getAccessibleContext().setAccessibleName("btVisualizar");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ilustração do Eixo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTituloEixo)
                    .addComponent(painelEixo, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btSalvarEixo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTituloEixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelEixo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addComponent(btSalvarEixo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel4, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btSalvarEixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarEixoActionPerformed
        try {
            NewSalvar();
        } catch (IOException ex) {
            Logger.getLogger(JPanelEixo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSalvarEixoActionPerformed

    private void txtTituloEixoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloEixoKeyPressed
        btSalvarEixo.setEnabled(true);
    }//GEN-LAST:event_txtTituloEixoKeyPressed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if(imagem.getCaminho() != null)
            jLabelIlustacaoEixo.setIcon(imagem.IconeInfografico(imagem.getCaminho()));
    }//GEN-LAST:event_formComponentResized

    private void btViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewActionPerformed
        try {
            btView.setEnabled(false);
            FmrView fmr = new FmrView(this, eixo.getId(),null,ebookId,jLabelIlustacaoEixo, 1);
            fmr.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(JPanelEixo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btViewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSalvarEixo;
    private javax.swing.JButton btView;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelIlustacaoEixo;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel painelEixo;
    private javax.swing.JTextField txtTituloEixo;
    // End of variables declaration//GEN-END:variables
}
