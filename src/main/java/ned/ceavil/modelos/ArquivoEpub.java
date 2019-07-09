
package ned.ceavil.modelos;

/**
 *
 * @author mauricio.junior
 */
public enum ArquivoEpub {
    
    MIMETYPE("mimetype"),
    CONTAINER("container.xml"),
    CONTENT("content.opf"),
    TOC("toc.ncx"),
    CSS("style.css");
    
    private String descricao;

    private ArquivoEpub(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }  
    
}
