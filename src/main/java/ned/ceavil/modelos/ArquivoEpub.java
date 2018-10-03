
package ned.ceavil.modelos;

/**
 *
 * @author mauricio.junior
 */
public enum ArquivoEpub {
    
    MIMETYPE("mimetype"),
    CONTAINER("container.xml"),
    CONTENT("conent.opf"),
    TOC("toc.ncx");   
    
    private String descricao;

    private ArquivoEpub(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }  
    
}
