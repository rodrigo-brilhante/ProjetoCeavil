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
import ned.ceavil.modelos.Indicador;
import ned.ceavil.modelos.LocalizacaoIndicador;
import ned.ceavil.modelos.Utils;
/**
 * @author rodrigo.brito
 */
public class JPanelIndicador extends javax.swing.JPanel {
    //Metodo Construtor
    public JPanelIndicador(FmrWorkspace Frame) {
        this.indicador = new Indicador();
        initComponents();
        frame = Frame;
        btSalvarIndicador.setEnabled(false);
    }
    
    private final FmrWorkspace frame;
    private final EbookJson ebookJson = new EbookJson();
    private final ImagemClass imagem = new ImagemClass();
    //TIMELINE
    private final ImagemClass imagemTimeLine = new ImagemClass();
    private Indicador indicador = new Indicador();
    private String ebookId;
    private Ebook ebook;
    private Integer idEixo, idIndicador;

    //Controles para abilitar Salvar
    private boolean descricao,pag;

    public Integer getIdIndicador() {
        return idIndicador;
    }
    public void setIdIndicador(Integer idIndicador) {
        this.idIndicador = idIndicador;
    }

    public String getEbookId() {
        return ebookId;
    }
    public void setEbookId(String ebookId) {
        this.ebookId = ebookId;
    }

    public Integer getIdEixo() {
        return idEixo;
    }
    public void setIdEixo(Integer idEixo) {
        this.idEixo = idEixo;
    }

    public Ebook getEbook() {
        return ebook;
    }
    public void setEbook(Ebook ebook) {
        this.ebook = ebook;
    }

    public void initLabel(){
        String caminhoJar = System.getProperty("java.class.path");
        try {
            ZipFile zipFile;
            zipFile = new ZipFile(caminhoJar);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                String name = zipEntry.getName();
                if (!zipEntry.isDirectory() && name.contains("photo-1103595_1280.png")){ //Aqui pergunta se achou esse arquivo  
                    InputStream is = ClassLoader.getSystemResourceAsStream(name);  
                    Image icone = new ImageIcon(ImageIO.read(is)).getImage();
                    lblImagem.setIcon(new ImageIcon(icone));
                    //TIMELINE
                    lblImagemTimeLine.setIcon(new ImageIcon(icone));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(JPanelIndicador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cadastrarCampos(Indicador indicador){
        indicador.setTitulo(txtTituloInd.getText());
        indicador.setDescricao(txtDescricao.getText());
        indicador.setLocalizacao(LocalizacaoIndicador.NUMEROPAGINAPDI.getIndiceLocalizacao(), txtPagPDI.getText());
        indicador.setLocalizacao(LocalizacaoIndicador.NUMEROINDICADOR.getIndiceLocalizacao(), txtNumIndicador.getText());
        indicador.setLocalizacao(LocalizacaoIndicador.NOMEDAPASTA.getIndiceLocalizacao(), txtNomePasta.getText());
    }
    public void carregar(Indicador indicador){
        this.indicador = indicador;
        txtTituloInd.setText(indicador.getTitulo());
        txtDescricao.setText(indicador.getDescricao());
        lblImagem.setIcon(imagem.IconeInfografico(indicador.getCaminhoImagem()));
        //TIMELINE
        lblImagemTimeLine.setIcon(imagemTimeLine.IconeInfografico(indicador.getCaminhoTimeLine()));
        txtPagPDI.setText(indicador.getLocalizacao()[0]);
        txtNumIndicador.setText(indicador.getLocalizacao()[1]);
        txtNomePasta.setText(indicador.getLocalizacao()[2]);
        btSalvarIndicador.setEnabled(false);
    }
   
    public void comparaTitulo(){
        char[] fixo = ((idEixo+1) +"."+(idIndicador+1)).toCharArray();
        char[] titulo = txtTituloInd.getText().toCharArray();
        String nome = txtTituloInd.getText();
        boolean diferente = false;
        if(titulo.length > fixo.length){
            for (int i = 0; i < fixo.length; i++)
                if (fixo[i] != titulo[i])
                    diferente = true;
        }else diferente = true;
        if (diferente) {
            txtTituloInd.setText((idEixo+1)+"."+(idIndicador+1) +" " + nome);
            char[] id = idEixo.toString().toCharArray();
            for (int i = 0; i < id.length; i++)
                if(id[i] != titulo[i]){
                }
        }
    }
    
    public void NewSalvar() throws IOException{
        ebook = ebookJson.readEbookJson(ebookId + ".json");
        comparaTitulo();
        cadastrarCampos(indicador);
        ebook.getEixos().get(idEixo).getIndicadores().set(idIndicador, indicador);
        Utils.copiarArquivos(new File(indicador.getCaminhoImagem()), new File(Utils.PathDestino(ebookId, indicador.getNomeImagem())));
        //TIMELINE
        Utils.copiarArquivos(new File(indicador.getCaminhoTimeLine()), new File(Utils.PathDestino(ebookId, indicador.getNomeTimeLine())));
        ebookJson.generateEbookJson(ebook);
        frame.atualizar(txtTituloInd.getText(),this);
        JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
        btSalvarIndicador.setEnabled(false);
        frame.CloseTabbed();
    }
    
    public void Salvar(){
        try{
            ebook = ebookJson.readEbookJson(ebookId + ".json");
            if(ebook.getEixos().size() != idEixo){
                if(indicador.isExiste()){
                    idEixo = indicador.getIdEixo();
                    idIndicador = indicador.getId();
                    comparaTitulo();
                    indicador.setTitulo(txtTituloInd.getText());
                    File fileImagem = new File(indicador.getCaminhoImagem());
                    imagem.setFileImagem(fileImagem);
                    //TIMELINE
                    File fileTimeLine = new File(indicador.getCaminhoTimeLine());
                    imagemTimeLine.setFileImagem(fileTimeLine);
                    cadastrarCampos(indicador);
                }else {
                    indicador.setIdEixo(idEixo);
                    indicador.setId(idIndicador);
                    cadastrarCampos(indicador);
                }
                if(ebook.getEixos().get(idEixo).getIndicadores().isEmpty()){
                    ebook.getEixos().get(idEixo).getIndicadores().add(idIndicador, indicador);
                    indicador.setExiste(true);
                }else if (indicador.isExiste()) 
                    ebook.getEixos().get(idEixo).getIndicadores().set(idIndicador, indicador);
                else{
                    ebook.getEixos().get(idEixo).getIndicadores().add(idIndicador, indicador);
                    indicador.setExiste(true);
                }
                Utils.copiarArquivos(imagem.getFileImagem(), new File(Utils.PathDestino(ebookId, indicador.getNomeImagem())));
                //TIMELINE
                Utils.copiarArquivos(imagemTimeLine.getFileImagem(), new File(Utils.PathDestino(ebookId, indicador.getNomeTimeLine())));
                ebookJson.generateEbookJson(ebook);
                frame.atualizar(txtTituloInd.getText(),this);
                btSalvarIndicador.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
            }else JOptionPane.showMessageDialog(frame, "Verifique se o eixo foi salvo", "Eixo não salvo", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(JPanelIndicador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Creates new form NewJPanelIndicador
     * @param indicador
     */
    public void setCampos(Indicador indicador) {
        this.indicador = indicador;
        idEixo = indicador.getIdEixo();
        idIndicador = indicador.getId();
        txtTituloInd.setText(indicador.getTitulo());
        txtNumIndicador.setText((idEixo+1)+"."+(idIndicador+1));
        txtNomePasta.setText("Eixo " + (idEixo+1));
        lblImagem.setIcon(imagem.IconeInfografico(indicador.getCaminhoImagem()));
        //TIMELINE
        lblImagemTimeLine.setIcon(imagemTimeLine.IconeInfografico(indicador.getCaminhoTimeLine()));
    }
    
    private void ativa(){
        if(indicador.isExiste())
            btSalvarIndicador.setEnabled(true);
        else if(pag && descricao)
            btSalvarIndicador.setEnabled(true);
    }
    
    public void ActivateButtonView(){
        btView.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane1 = new java.awt.ScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtTituloInd = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        painelIndicador = new javax.swing.JPanel();
        lblImagem = new javax.swing.JLabel();
        btView = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtNomePasta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtPagPDI = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtNumIndicador = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btSalvarIndicador = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        painelIndicador1 = new javax.swing.JPanel();
        lblImagemTimeLine = new javax.swing.JLabel();
        btViewTimeLine = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(730, 900));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        scrollPane1.setPreferredSize(new java.awt.Dimension(730, 900));

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Título");
        jLabel3.setAlignmentY(0.0F);

        txtTituloInd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTituloInd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTituloIndKeyPressed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Descrição");
        jLabel4.setAlignmentY(0.0F);

        txtDescricao.setColumns(20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setRows(5);
        txtDescricao.setToolTipText("");
        txtDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDescricaoKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(txtDescricao);

        painelIndicador.setBackground(new java.awt.Color(204, 204, 204));
        painelIndicador.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblImagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IlustracaoInfo.png"))); // NOI18N
        lblImagem.setText("  ");

        btView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeVisualizar.png"))); // NOI18N
        btView.setText("Visualizar");
        btView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelIndicadorLayout = new javax.swing.GroupLayout(painelIndicador);
        painelIndicador.setLayout(painelIndicadorLayout);
        painelIndicadorLayout.setHorizontalGroup(
            painelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelIndicadorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btView)
                .addContainerGap())
        );
        painelIndicadorLayout.setVerticalGroup(
            painelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelIndicadorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelIndicadorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelIndicadorLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btView))
                    .addComponent(lblImagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Localização");
        jLabel1.setAlignmentY(0.0F);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        txtNomePasta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNomePasta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomePastaKeyPressed(evt);
            }
        });

        jLabel9.setText("Nome da Pasta");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNomePasta)
                    .addComponent(jLabel9))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomePasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(204, 204, 204));

        txtPagPDI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPagPDI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPagPDIKeyPressed(evt);
            }
        });

        jLabel7.setText("N° página PDI");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtPagPDI)
                    .addComponent(jLabel7))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPagPDI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(204, 204, 204));

        txtNumIndicador.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumIndicador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumIndicadorKeyPressed(evt);
            }
        });

        jLabel8.setText("N° Indicador");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtNumIndicador)
                    .addComponent(jLabel8))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtNumIndicador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btSalvarIndicador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeSave.png"))); // NOI18N
        btSalvarIndicador.setText("Salvar");
        btSalvarIndicador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btSalvarIndicador.setMaximumSize(new java.awt.Dimension(109, 39));
        btSalvarIndicador.setMinimumSize(new java.awt.Dimension(109, 39));
        btSalvarIndicador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarIndicadorActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Infográfico");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Linha do Tempo");

        painelIndicador1.setBackground(new java.awt.Color(204, 204, 204));
        painelIndicador1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblImagemTimeLine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagemTimeLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IlustracaoInfo.png"))); // NOI18N
        lblImagemTimeLine.setText("  ");

        btViewTimeLine.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeVisualizar.png"))); // NOI18N
        btViewTimeLine.setText("Visualizar");
        btViewTimeLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewTimeLineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelIndicador1Layout = new javax.swing.GroupLayout(painelIndicador1);
        painelIndicador1.setLayout(painelIndicador1Layout);
        painelIndicador1Layout.setHorizontalGroup(
            painelIndicador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelIndicador1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagemTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addGap(2, 2, 2)
                .addComponent(btViewTimeLine)
                .addContainerGap())
        );
        painelIndicador1Layout.setVerticalGroup(
            painelIndicador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelIndicador1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelIndicador1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelIndicador1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btViewTimeLine))
                    .addComponent(lblImagemTimeLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelIndicador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTituloInd, javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btSalvarIndicador, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelIndicador1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTituloInd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelIndicador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelIndicador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btSalvarIndicador, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        scrollPane1.add(jPanel8);

        add(scrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btSalvarIndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarIndicadorActionPerformed
        try {
            NewSalvar();
        } catch (IOException ex) {
            Logger.getLogger(JPanelIndicador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSalvarIndicadorActionPerformed

    private void txtTituloIndKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTituloIndKeyPressed
        ativa();
    }//GEN-LAST:event_txtTituloIndKeyPressed
    private void txtDescricaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescricaoKeyPressed
        descricao = true;
        ativa();
    }//GEN-LAST:event_txtDescricaoKeyPressed
    private void txtPagPDIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPagPDIKeyPressed
        pag = true;
        ativa();
    }//GEN-LAST:event_txtPagPDIKeyPressed
    private void txtNumIndicadorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumIndicadorKeyPressed
        ativa();
    }//GEN-LAST:event_txtNumIndicadorKeyPressed
    private void txtNomePastaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomePastaKeyPressed
        ativa();
    }//GEN-LAST:event_txtNomePastaKeyPressed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    if(imagem.getCaminho() != null)
        lblImagem.setIcon(imagem.IconeInfografico(imagem.getCaminho()));
    //TIMELIME
    if(imagemTimeLine.getCaminho() != null)
        lblImagemTimeLine.setIcon(imagemTimeLine.IconeInfografico(imagemTimeLine.getCaminho()));
    }//GEN-LAST:event_formComponentResized

    private void btViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewActionPerformed
        try {
            btView.setEnabled(false);
            FmrView fmr = new FmrView(this,idEixo,idIndicador,ebookId,lblImagem, 1);
            fmr.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(JPanelEixo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btViewActionPerformed

    private void btViewTimeLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewTimeLineActionPerformed
        try {
            btView.setEnabled(false);
            FmrView fmr = new FmrView(this,idEixo,idIndicador,ebookId,lblImagemTimeLine, 0);
            fmr.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(JPanelEixo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btViewTimeLineActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSalvarIndicador;
    private javax.swing.JButton btView;
    private javax.swing.JButton btViewTimeLine;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImagem;
    private javax.swing.JLabel lblImagemTimeLine;
    private javax.swing.JPanel painelIndicador;
    private javax.swing.JPanel painelIndicador1;
    private java.awt.ScrollPane scrollPane1;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtNomePasta;
    private javax.swing.JTextField txtNumIndicador;
    private javax.swing.JTextField txtPagPDI;
    private javax.swing.JTextField txtTituloInd;
    // End of variables declaration//GEN-END:variables
}
