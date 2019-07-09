package ned.ceavil.modelos;
/**
 * @author mauricio.junior
 */
public enum PastaEpub {
    
    EPUB("epub"),
    METAINF("META-INF"),
    OEBPS("OEBPS"),
    DB("DataBase");
    
    private String descricao;

    private PastaEpub(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }  
    
}
