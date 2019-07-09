package ned.ceavil.modelos;
/**
 * @author mauricio.junior
 */
public class Indicador {

    private Integer id, idEixo;
    private String titulo;
    private String descricao;
    private String nomeImagem;
    private final String[] localizacao;
    private String caminhoImagem;
    private boolean existe = false;
    private String nomeTimeLine;
    private String caminhoTimeLine;

    
    public String getNomeTimeLine() {
        return nomeTimeLine;
    }

    public void setNomeTimeLine(String nomeTimeLine) {
        this.nomeTimeLine = nomeTimeLine;
    }

    public String getCaminhoTimeLine() {
        return caminhoTimeLine;
    }

    public void setCaminhoTimeLine(String caminhoTimeLine) {
        this.caminhoTimeLine = caminhoTimeLine;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public Indicador() {
        localizacao = new String[3];
    }

    public Integer getIdEixo() {
        return idEixo;
    }

    public void setIdEixo(Integer idEixo) {
        this.idEixo = idEixo;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeInfografico) {
        this.nomeImagem = nomeInfografico;
    }

    public String[] getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(int indice, String info) {
        this.localizacao[indice] = info;
    }
    
    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public Indicador cloneIndicador(){
        Indicador aux = new Indicador();
        aux.id = this.id;
        aux.titulo = this.titulo;
        aux.descricao = this.descricao;
        aux.caminhoImagem = this.caminhoImagem;
        aux.nomeImagem = this.nomeImagem;
        aux.idEixo = this.idEixo;
        aux.existe = this.existe;
        aux.nomeTimeLine = this.nomeTimeLine;
        aux.caminhoTimeLine = this.caminhoTimeLine;
        System.arraycopy(this.localizacao, 0, aux.localizacao, 0, 3);
        return aux;
    }
}
