package ned.ceavil.sistema.forms;

import ned.ceavil.modelos.ImagemClass;
import ned.ceavil.modelos.ButtonTabComponent;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import ned.ceavil.modelos.Ebook;
import ned.ceavil.modelos.EbookFile;
import ned.ceavil.modelos.EbookJson;
import ned.ceavil.modelos.EbookXML;
import ned.ceavil.modelos.Eixo;
import ned.ceavil.modelos.Indicador;
import ned.ceavil.modelos.PastaEpub;
import ned.ceavil.modelos.Utils;

/**
 * @author rodrigo.brito
 */
public class FmrWorkspace extends javax.swing.JFrame {

    private final ImagemClass image = new ImagemClass();
    private Ebook ebook = new Ebook();
    private final EbookJson ebookJson = new EbookJson();
    private String ebookId, ebookCapa, ebookTitle;
    public Boolean check = false;
    javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
    
    public FmrWorkspace() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/IconeEpub.png")).getImage());
        setExtendedState(MAXIMIZED_BOTH);
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int selRow = jTree1.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = jTree1.getPathForLocation(e.getX(), e.getY());
                    jTree1.setSelectionPath(selPath);
                    if (selRow < -1) {
                        jTree1.setSelectionRow(selRow);
                    }
                }
            }
        };
        jTree1.addMouseListener(ml);
        jTree1.setToggleClickCount(10);
    }

    
    public Ebook getEbook() {
        return ebook;
    }
    public void setEbook(Ebook ebook) {
        this.ebook = ebook;
        image.setCaminho(this.ebook.getCaminhoCapa());
    }
        
    public void setEbookTitle(String ebookTitle) {
        this.ebookTitle = ebookTitle;
    }
    public void setEbookCapa(String ebookCapa) {
        this.ebookCapa = ebookCapa;
        jLblCapaEbook.setIcon(image.IconeCapa(ebookCapa));
    }

    public String getEbookId() {
        return ebookId;
    }
    public void setEbookId(String ebookId) {
        this.ebookId = ebookId;
    }
    
    public void atualizar(String titulo, Object object){
        if(titulo.length() > 0 && titulo.length() < 5){
            jTabbedPane1.setTitleAt(jTabbedPane1.getSelectedIndex(), titulo.substring(0,5) + "..");
        }else  if (object instanceof JPanelEixo)
            jTabbedPane1.setTitleAt(jTabbedPane1.getSelectedIndex(), titulo.substring(0,6) + "..");            
        else jTabbedPane1.setTitleAt(jTabbedPane1.getSelectedIndex(), titulo.substring(0,5) + "..");            
        RebuildJtree();
    }



    private void duploClickJtree(MouseEvent e, DefaultMutableTreeNode no) {
        String titleAba = jTree1.getLastSelectedPathComponent().toString();
        if ((e.getClickCount() == 2) && (no.getLevel() == 1)) {
            String []aux = titleAba.split(" ");
            Integer idEixo = Integer.parseInt(aux[1]) - 1;
            titleAba = titleAba.substring(0,6)+"..";
            if (jTabbedPane1.indexOfTab(titleAba) != -1) {
                jTabbedPane1.setSelectedIndex(jTabbedPane1.indexOfTab(titleAba));
            }else{
                try {
                    //Atualizar objeto ebook com arquivo.json
                    if(ebookId != null)
                        ebook = ebookJson.readEbookJson(ebookId + ".json");
                    else ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
                } catch (IOException ex) {
                    Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Eixo object : ebook.getEixos())
                    if(object.getId().equals(idEixo))
                        OpenEixo(titleAba,idEixo);
            }
        }
        if ((e.getClickCount() == 2) && (no.getLevel() == 2)) {
            String []aux = titleAba.split(" ");
            String []aux2 = aux[0].split("[.]");
            Integer idEixo = Integer.parseInt(aux2[0])-1, idIndicador = Integer.parseInt(aux2[1])-1;
            titleAba = titleAba.substring(0,5)+"..";
            if (jTabbedPane1.indexOfTab(titleAba) != -1) {
                jTabbedPane1.setSelectedIndex(jTabbedPane1.indexOfTab(titleAba));
            }else {
                try {
                    //Atualizar objeto ebook com arquivo.json
                    if(ebookId != null)
                        ebook = ebookJson.readEbookJson(ebookId + ".json");
                    else ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
                } catch (IOException ex) {
                    Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Eixo object : ebook.getEixos())
                    if(object.getId().equals(idEixo))
                        for (Indicador objtectInd : object.getIndicadores())
                            if(objtectInd.getId().equals(idIndicador))
                                OpenIndicador(titleAba,idEixo,idIndicador);
            }
        }
    }
    //Criar Novo Eixo OU Indicador
    private void NewIndicador(){
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent();
        if ((selectedNode != null)&&(selectedNode.getLevel() <= 1)) {
            FmrNewInfo form = new FmrNewInfo(this, true, treeNode1.getIndex(selectedNode),selectedNode.getChildCount(),this, selectedNode);
            form.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(rootPane, "Eixo não selecionado", "Não foi possível criar", ERROR_MESSAGE);
            RebuildJtree();
        }
    }
    private void NewEixo(){
        FmrNewInfo form = new FmrNewInfo(this, true, treeNode1.getChildCount(),-1,this, null);
        form.setVisible(true);
    }
    
    public void CreateEixo(Eixo eixo) throws IOException, InterruptedException{
        ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
        ebook.setEixo(eixo);
        ebookJson.generateEbookJson(ebook);
        treeNode1.add(new DefaultMutableTreeNode(eixo.getTitulo()));
        RebuildJtree();
        criarAbaEixo(eixo.getTitulo(), treeNode1);
    }
    public void CreateIndicador(Indicador indicador, DefaultMutableTreeNode selectedNode) throws IOException{
        ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
        indicador.setId(ebook.getEixos().get(indicador.getIdEixo()).getIndicadores().size());
        ebook.getEixos().get(indicador.getIdEixo()).setIndicador(indicador);
        ebookJson.generateEbookJson(ebook);
        selectedNode.add(new DefaultMutableTreeNode(indicador.getTitulo()));
        //for para manter o ultimo eixo selecionado
        int max = RebuildJtree();
            for(int i = 1; i < max;i++){
                String aux = jTree1.getPathForRow(i).toString().split(",")[1];
                String aux2 = selectedNode.toString()+"]";
                aux = Utils.removerAcentosEspacos(aux);
                aux2 = Utils.removerAcentosEspacos(aux2);
                if(aux.equals(aux2))
                    jTree1.setSelectionRow(i);
            }
        criarAbaIndicador(indicador, selectedNode);
    }
    
    //Criar aba Novo Eixo OU Indicador e Atribui id
    private void criarAbaEixo(String nome, DefaultMutableTreeNode no) throws IOException, InterruptedException{
        JPanelEixo internalFormEixo = new JPanelEixo(this);
        
        //Enviar para o form Eixo o ebookId
        internalFormEixo.setEbookId(ebook.getId());

        //Confere se existe o nome nas abas do jtabbedpanel
        if ((jTabbedPane1.indexOfTab(no.toString())) == -1) {
            if(nome.length() > 0 && nome.length()<6){
                CreateTabbed(nome + "...", internalFormEixo);
            }else CreateTabbed(nome.substring(0, 6)+"..", internalFormEixo);

            internalFormEixo.setTituloEixo(nome);
            String []aux = nome.split(" ");
            internalFormEixo.setIdEixo(Integer.parseInt(aux[1]));
            internalFormEixo.carregar(ebook.getEixos().get(Integer.parseInt(aux[1])-1));
            internalFormEixo.NewSalvar();
        }
    }
    private void criarAbaIndicador(Indicador indicador, DefaultMutableTreeNode no){
        JPanelIndicador internalFormIndicador = new JPanelIndicador(this);
        if ((jTabbedPane1.indexOfTab(no.toString()) == -1)) {
            if(indicador.getTitulo().length() > 0 && indicador.getTitulo().length()<5){
                CreateTabbed(indicador.getTitulo()+"..", internalFormIndicador);
            }else CreateTabbed(indicador.getTitulo().substring(0,5)+"..", internalFormIndicador);

            internalFormIndicador.setEbookId(ebook.getId());
            internalFormIndicador.setCampos(indicador);
        }
    }
    
    //Reabrir Aba Eixo OU Indicador
    private void OpenEixo(String title, Integer id){
        JPanelEixo  internalFormEixo = new JPanelEixo(this);
        internalFormEixo.setEbookId(ebook.getId());
        internalFormEixo.carregar(ebook.getEixos().get(id));
        CreateTabbed(title, internalFormEixo);
    }
    private void OpenIndicador(String title, Integer idEixo, Integer idIndicador){
        JPanelIndicador internalFormIndicador = new JPanelIndicador(this);
        internalFormIndicador.setEbookId(ebook.getId());
        internalFormIndicador.carregar(ebook.getEixos().get(idEixo).getIndicadores().get(idIndicador));//,jTabbedPane1.getHeight(), jTabbedPane1.getWidth());
        internalFormIndicador.setIdEixo(idEixo);
        internalFormIndicador.setIdIndicador(idIndicador);
        CreateTabbed(title, internalFormIndicador);
    }
    
    private void CreateTabbed(String title, Component component ){
        jTabbedPane1.add(title,component);
        jTabbedPane1.setSelectedComponent(component);
        int i = jTabbedPane1.getSelectedIndex();
        jTabbedPane1.setTabComponentAt(i, new ButtonTabComponent(jTabbedPane1));
    }
    
    //Reconstroi jTree completo 
    public int RebuildJtree(){
        Integer rows=0,F=0;
        try {
            ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
            treeNode1.removeAllChildren();
            jLblCapaEbook.setIcon(image.IconeCapa(ebook.getCaminhoCapa()));
            for (Eixo eixo : ebook.getEixos()) {
                DefaultMutableTreeNode pai = new DefaultMutableTreeNode(eixo.getTitulo());
                F=0;
                for (Indicador indicador : eixo.getIndicadores()) {
                    DefaultMutableTreeNode filho = new DefaultMutableTreeNode(indicador.getTitulo());
                    pai.add(filho);
                    rows++;
                    F++;
                }
                rows++;
                treeNode1.add(pai);
            }
            DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
            model.reload();
            for (int i = 0; i < jTree1.getRowCount(); i++) 
                jTree1.expandRow(i);
            jTree1.setSelectionRow((rows-1)-F);
                } catch (IOException ex) {
            Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }
    
    //Escolher onde salvar Ebook
    public File SalvarComo(Ebook ebook) {
        JFileChooser savefile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("epub", "epub");
        savefile.setFileFilter(filter);
        savefile.setSelectedFile(new File(ebook.getTitulo()));
        if (savefile.showSaveDialog(savefile) == JFileChooser.CANCEL_OPTION)
            return null;
        return savefile.getSelectedFile();
    }
    
    //Apagar Eixo ou Indicador
    public void apagarEixo(DefaultMutableTreeNode no){
        try {
            int x = ebook.getEixos().size();
            int IdEixo = -1;
            Ebook ebookAux = copiaEbook();
            for(Eixo eixo : ebook.getEixos())
                if(eixo.getTitulo().equals(no.getUserObject()))
                    IdEixo = eixo.getId();
                if (ebook.getEixos().size()-1 == IdEixo) {//Não avera alterção de indice
                    ebook.getEixos().remove(IdEixo);
                    ebookJson.generateEbookJson(ebook);
                }else if (IdEixo != -1){//avera alteração de indice
                    for (Eixo eixo : ebook.getEixos())
                        if(eixo.getId() != IdEixo)
                            ebookAux.setEixo(eixo);
                    corrigeEbook(ebookAux);
                    ebook = ebookAux;
                    ebookJson.generateEbookJson(ebook);
                }
                no.removeFromParent();
                RebuildJtree();
                jTabbedPane1.removeAll();
        } catch (IOException ex) {
            Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void apagaIndicador(DefaultMutableTreeNode no){
        String []ID = no.toString().split("[.]");
        String []INDEX = ID[1].split(" ");
        int IdEixo = Integer.parseInt(ID[0])-1;
        int IdIndicador = Integer.parseInt(INDEX[0])-1;
        try {
            if ((ebook.getEixos().get(IdEixo).getIndicadores().size()-1) == IdIndicador) {//Não havera alterção de indice
                    ebook.getEixos().get(IdEixo).getIndicadores().remove(IdIndicador);
                    ebookJson.generateEbookJson(ebook);
                }else if (IdIndicador != -1){//havera alteração de indice
                    Eixo eixo = copiaEixo(ebook.getEixos().get(IdEixo));
                    for (Indicador indicador : ebook.getEixos().get(IdEixo).getIndicadores())
                        if(indicador.getId() != IdIndicador)
                            eixo.setIndicador(indicador);
                    corrigeIndicador(eixo);
                    ebook.getEixos().set(IdEixo,eixo);
                    ebookJson.generateEbookJson(ebook);
                }
            no.removeFromParent();      
            RebuildJtree();
            jTabbedPane1.removeAll();
        } catch (IOException ex) {
                Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void corrigeEbookII(Ebook e){
        for (int i = 0; i < e.getEixos().size(); i++) {
            String []Titulo = e.getEixos().get(i).getTitulo().split("-");
            String aux = "Eixo " + (i+1) + " -" + Titulo[1]; 
            e.getEixos().get(i).setTitulo(aux);
            e.getEixos().get(i).setId(i);
            corrigeIndicadorII(e.getEixos().get(i));
        }
    }
    private void corrigeIndicadorII(Eixo e){
        String []Eixo = e.getTitulo().split("-");
        for (int i = 0; i < e.getIndicadores().size(); i++) {
            String []Titulo = e.getIndicadores().get(i).getTitulo().split(" ");
            String titulo = Titulo[1];
            for (int j = 2; j < Titulo.length; j++)
                titulo += " " + Titulo[j];
            String id = (e.getId()+1)+"."+(i+1);
            e.getIndicadores().get(i).setTitulo(id+" "+titulo);
            e.getIndicadores().get(i).setLocalizacao(1,id);
            e.getIndicadores().get(i).setLocalizacao(2, Eixo[0]);
        }
    }    
    private void corrigeEbook(Ebook e){
        for (int i = 0; i < e.getEixos().size(); i++)
            if(e.getEixos().get(i).getId() != i){
                String []Titulo = e.getEixos().get(i).getTitulo().split("-");
                String aux = "Eixo " + (i+1) + " -" + Titulo[1]; 
                e.getEixos().get(i).setTitulo(aux);
                e.getEixos().get(i).setId(i);
                
                for (int j = 0; j < e.getEixos().get(i).getIndicadores().size(); j++) {
                        String []TituloIndicador = e.getEixos().get(i).getIndicadores().get(j).getTitulo().split("[.]");
                        String titulo = TituloIndicador[1];
                        e.getEixos().get(i).getIndicadores().get(j).setTitulo((i+1)+"."+titulo);
                        e.getEixos().get(i).getIndicadores().get(j).setIdEixo(i);
                }
            }
    }
    private void corrigeIndicador(Eixo e){
        String []Eixo = e.getTitulo().split("-");
        for (int i = 0; i < e.getIndicadores().size(); i++) {
            String []Titulo = e.getIndicadores().get(i).getTitulo().split(" ");
            String titulo = Titulo[1];
            for (int j = 2; j < Titulo.length; j++)
                titulo += " " + Titulo[j];
            String id = (e.getId()+1)+"."+(i+1);
            e.getIndicadores().get(i).setTitulo(id+" "+titulo);
            e.getIndicadores().get(i).setId(i);
            e.getIndicadores().get(i).setLocalizacao(1,id);
            e.getIndicadores().get(i).setLocalizacao(2, Eixo[0]); 
        }
    }
    
    //Copia somente os campos do Ebook, retorna new Ebook
    private Ebook copiaEbook(){
        Ebook e = new Ebook();
        e.setId(ebook.getId());
        e.setTitulo(ebook.getTitulo());
        e.setCapa(ebook.getCapa());
        e.setCaminhoCapa(ebook.getCaminhoCapa());
        return e;
    }
    //Copia apenas os campos do Eixo, retorna new Eixo
    private Eixo copiaEixo(Eixo eixo){
        Eixo e = new Eixo();
        e.setId(eixo.getId());
        e.setCaminhoImagem(eixo.getCaminhoImagem());
        e.setNomeImagem(eixo.getNomeImagem());
        e.setExiste(eixo.isExiste());
        e.setTitulo(eixo.getTitulo());
        return e;
    }

    //Mover Eixo para cima ou para baixo 
    private void upEixo(int id){
        if (id > 0) {
            try {
                ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
                jTabbedPane1.removeAll();
                Eixo aux = ebook.getEixos().get(id).cloneEixo();
                ebook.getEixos().set(id,ebook.getEixos().get(id-1));
                ebook.getEixos().set(id-1,aux);
                corrigeEbookII(ebook);
                ebookJson.generateEbookJson(ebook);
                RebuildJtree();
            } catch (IOException ex) {
                Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else JOptionPane.showMessageDialog(rootPane, "Não é possivel subir \nLimite maxímo atingido");
    }
    private void dowmEixo(int id){
        if (id < ebook.getEixos().size()-1) {
            try {
                ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
                jTabbedPane1.removeAll();
                Eixo aux = ebook.getEixos().get(id).cloneEixo();
                ebook.getEixos().set(id,ebook.getEixos().get(id+1));
                ebook.getEixos().set(id+1,aux);
                corrigeEbookII(ebook);
                ebookJson.generateEbookJson(ebook);
                RebuildJtree();
            } catch (IOException ex) {
                Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else JOptionPane.showMessageDialog(rootPane, "Não é possivel descer \nLimite maxímo atingido");
    }

    //Mover Indicador para cima ou para baixo
    private void upIndicador(int idEixo, int idIndicador){
        if (idIndicador > 0) {
           try {
                ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
                jTabbedPane1.removeAll();
                Indicador aux = ebook.getEixos().get(idEixo).getIndicadores().get(idIndicador).cloneIndicador();
                ebook.getEixos().get(idEixo).getIndicadores().set(idIndicador,ebook.getEixos().get(idEixo).getIndicadores().get(idIndicador-1));
                ebook.getEixos().get(idEixo).getIndicadores().set(idIndicador-1,aux);
                corrigeIndicadorII(ebook.getEixos().get(idEixo));
                ebookJson.generateEbookJson(ebook);
                RebuildJtree();
            } catch (IOException ex) {
                Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else JOptionPane.showMessageDialog(rootPane, "Não é possivel subir \nLimite maxímo atingido");
    }
    private void dowmIndicador(int idEixo, int idIndicador){
        if (idIndicador < ebook.getEixos().get(idEixo).getIndicadores().size()-1) {
           try {
                ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
                jTabbedPane1.removeAll();
                Indicador aux = ebook.getEixos().get(idEixo).getIndicadores().get(idIndicador).cloneIndicador();
                ebook.getEixos().get(idEixo).getIndicadores().set(idIndicador,ebook.getEixos().get(idEixo).getIndicadores().get(idIndicador+1));
                ebook.getEixos().get(idEixo).getIndicadores().set(idIndicador+1,aux);
                corrigeIndicadorII(ebook.getEixos().get(idEixo));
                ebookJson.generateEbookJson(ebook);
                RebuildJtree();
            } catch (IOException ex) {
                Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else JOptionPane.showMessageDialog(rootPane, "Não é possivel descer \nLimite maxímo atingido");
    }
    
    public void ActivateButtonView(){
        btView.setEnabled(true);
    }
    
    public void CloseTabbed(){
        jTabbedPane1.remove(jTabbedPane1.getSelectedComponent());
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItemEixo = new javax.swing.JMenuItem();
        jMenuItemIndicador = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItemApagar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItemUp = new javax.swing.JMenuItem();
        jMenuItemDown = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLblCapaEbook = new javax.swing.JLabel();
        btView = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btNovoEixo = new javax.swing.JButton();
        btNovoIndicador = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuVoltar = new javax.swing.JMenu();

        jMenuItemEixo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItemEixo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeAddPopUp.png"))); // NOI18N
        jMenuItemEixo.setText("Adicionar Eixo");
        jMenuItemEixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEixoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemEixo);

        jMenuItemIndicador.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItemIndicador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeAddPopUp.png"))); // NOI18N
        jMenuItemIndicador.setText("Adicionar Indicador");
        jMenuItemIndicador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemIndicadorActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemIndicador);
        jPopupMenu1.add(jSeparator1);

        jMenuItemApagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItemApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeDeletePopUp.png"))); // NOI18N
        jMenuItemApagar.setText("Apagar");
        jMenuItemApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemApagarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemApagar);
        jPopupMenu1.add(jSeparator2);

        jMenuItemUp.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItemUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeUpPopUp.png"))); // NOI18N
        jMenuItemUp.setText("Mover para cima");
        jMenuItemUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUpActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemUp);

        jMenuItemDown.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jMenuItemDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeDownPopUp.png"))); // NOI18N
        jMenuItemDown.setText("Mover para baixo");
        jMenuItemDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDownActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItemDown);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerador Ebook");
        setIconImages(null);
        setLocation(new java.awt.Point(0, 0));
        setLocationByPlatform(true);
        setSize(new java.awt.Dimension(0, 0));
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jTree1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setToolTipText("");
        jTree1.setComponentPopupMenu(jPopupMenu1);
        jTree1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTree1.setRootVisible(false);
        jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTree1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Capa Ebook");

        jLblCapaEbook.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblCapaEbook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IlustracaoCapa.png"))); // NOI18N

        btView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeVisualizar.png"))); // NOI18N
        btView.setText("Visualizar");
        btView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewActionPerformed1(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btView)
                    .addComponent(jLblCapaEbook, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLblCapaEbook)
                .addGap(18, 18, 18)
                .addComponent(btView)
                .addContainerGap())
        );

        btView.getAccessibleContext().setAccessibleName("btVisualizar");

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeGerar.png"))); // NOI18N
        jButton3.setToolTipText("Clique aqui para criar o ebook");
        jButton3.setBorder(null);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Gerar Ebook");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton3)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Adicionar");

        btNovoEixo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btNovoEixo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeAdd.png"))); // NOI18N
        btNovoEixo.setText(" Eixo          ");
        btNovoEixo.setToolTipText("Clique para criar um novo eixo");
        btNovoEixo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btNovoEixo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoEixoActionPerformed(evt);
            }
        });

        btNovoIndicador.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btNovoIndicador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeAdd.png"))); // NOI18N
        btNovoIndicador.setText("Indicador");
        btNovoIndicador.setToolTipText("Clique para adicionar um indicador a um eixo");
        btNovoIndicador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btNovoIndicador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoIndicadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btNovoEixo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btNovoIndicador, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNovoEixo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNovoIndicador, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setName("teste"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenuVoltar.setForeground(new java.awt.Color(0, 51, 153));
        jMenuVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconeHome.png"))); // NOI18N
        jMenuVoltar.setText("Voltar ao menu principal");
        jMenuVoltar.setToolTipText("Clique aqui para voltar a tela principal");
        jMenuVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuVoltar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuVoltar.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jMenuVoltar.setIconTextGap(10);
        jMenuVoltar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuVoltarMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenuVoltar);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTree1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseReleased
        if (evt.isPopupTrigger()) {
            jPopupMenu1.show(this, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTree1MouseReleased

    private void jMenuItemEixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEixoActionPerformed
        NewEixo();
    }//GEN-LAST:event_jMenuItemEixoActionPerformed

    private void jMenuItemIndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemIndicadorActionPerformed
        NewIndicador();
    }//GEN-LAST:event_jMenuItemIndicadorActionPerformed

    private void jMenuItemApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemApagarActionPerformed
        try {
            ebook = ebookJson.readEbookJson(ebook.getId()+".json");
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
            if (selectedNode != null && JOptionPane.showConfirmDialog(null, "Deseja realmente alterar?", "Alterar", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION){
                if (selectedNode.getLevel() == 1 ) {
                    apagarEixo(selectedNode);
                }else if (selectedNode.getLevel() == 2) {
                    apagaIndicador(selectedNode);
                }
            }else if(selectedNode == null)
                JOptionPane.showMessageDialog(rootPane, "Nenhum item selecionado,",null,JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItemApagarActionPerformed

    private void btNovoEixoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoEixoActionPerformed
        NewEixo();
    }//GEN-LAST:event_btNovoEixoActionPerformed

    private void btNovoIndicadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoIndicadorActionPerformed
        NewIndicador();
    }//GEN-LAST:event_btNovoIndicadorActionPerformed

    private void jMenuVoltarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuVoltarMouseClicked
        TelaNovo frame = new TelaNovo();
        frame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuVoltarMouseClicked

    private void jMenuItemUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemUpActionPerformed
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (selectedNode != null && JOptionPane.showConfirmDialog(null, "Deseja realmente alterar?", "Alterar", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION){
            if (selectedNode.getLevel() == 1 ) {
                String []ID = selectedNode.getUserObject().toString().split(" ");
                upEixo(Integer.parseInt(ID[1])-1);
            }else if (selectedNode.getLevel() == 2) {
                String []titulo = selectedNode.getUserObject().toString().split(" ");
                String []ID = titulo[0].split("[.]");
                upIndicador(Integer.parseInt(ID[0])-1,Integer.parseInt(ID[1])-1);
            }
        }else if(selectedNode == null)
            JOptionPane.showMessageDialog(rootPane, "Nenhum item selecionado,",null,JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jMenuItemUpActionPerformed

    private void jMenuItemDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDownActionPerformed
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (selectedNode != null && JOptionPane.showConfirmDialog(null, "Deseja realmente alterar?", "Alterar", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE) == JOptionPane.YES_OPTION){
            if (selectedNode.getLevel() == 1 ) {
                String []ID = selectedNode.getUserObject().toString().split(" ");
                dowmEixo(Integer.parseInt(ID[1])-1);
            }else if (selectedNode.getLevel() == 2) {
                String []titulo = selectedNode.getUserObject().toString().split(" ");
                String []ID = titulo[0].split("[.]");
                dowmIndicador(Integer.parseInt(ID[0])-1,Integer.parseInt(ID[1])-1);
            }
        }else if(selectedNode == null)
            JOptionPane.showMessageDialog(rootPane, "Nenhum item selecionado,",null,JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jMenuItemDownActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Buscar Json do ebook//
        EbookJson jsonEbook = new EbookJson();
        try {

            Ebook ebookLido = jsonEbook.readEbookJson(ebook.getId() + ".json");

            File nome = SalvarComo(ebookLido);
            if(nome != null){
                String caminho = nome.getPath();
                nome = new File(caminho + ".epub");
                

                EbookFile arquivoEbook = new EbookFile();
                arquivoEbook.criarEstuturaPastas(ebookLido.getId());
                arquivoEbook.criarEstruturaAquivos();

                EbookXML ebookxml = new EbookXML();
                ebookxml.gerarXHTMLCapa(ebookLido.getId(), ebookLido.getCapa());
                ebookxml.gerarXHTMLEixo(ebookLido.getId(), ebookLido.getEixos());
                ebookxml.gerarTocNCX(ebookLido.getId(), ebookLido.getEixos());
                ebookxml.gerarContentOPF(ebookLido.getId(), ebookLido.getEixos(), ebookLido.getCapa());

                arquivoEbook.empacotarEbook(ebookLido.getId(), Utils.removerAcentosEspacos(ebookLido.getTitulo()));
                Utils.copiarArquivos(arquivoEbook.getCaminhoFinal(), nome);
                Utils.deleteDirectoryStream(arquivoEbook.getCaminhoFinal().toPath());
                JOptionPane.showMessageDialog(rootPane, "Ebook gerado com sucesso!", ebookTitle, INFORMATION_MESSAGE);
            }
        } catch (IOException | ParserConfigurationException | TransformerException ex) {
            Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btViewActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewActionPerformed1
        FmrView form;
        try {
            btView.setEnabled(false);
            form = new FmrView(this,ebook.getId(),jLblCapaEbook);
            form.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btViewActionPerformed1

    private void jTree1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTree1MouseClicked
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        duploClickJtree(evt, selectedNode);
    }//GEN-LAST:event_jTree1MouseClicked

    @SuppressWarnings("empty-statement")
    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        try {
            ebook = ebookJson.readEbookJson(ebook.getId() + ".json");
            for(int i = 0; i < 100; i++);
            jLblCapaEbook.setIcon(image.IconeCapa(ebook.getCaminhoCapa()));
        } catch (IOException ex) {
            Logger.getLogger(FmrWorkspace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowStateChanged

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
            java.util.logging.Logger.getLogger(FmrWorkspace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FmrWorkspace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FmrWorkspace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FmrWorkspace.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FmrWorkspace().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btNovoEixo;
    private javax.swing.JButton btNovoIndicador;
    private javax.swing.JButton btView;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLblCapaEbook;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemApagar;
    private javax.swing.JMenuItem jMenuItemDown;
    private javax.swing.JMenuItem jMenuItemEixo;
    private javax.swing.JMenuItem jMenuItemIndicador;
    private javax.swing.JMenuItem jMenuItemUp;
    private javax.swing.JMenu jMenuVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

}
