/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ned.ceavil.modelos;

/**
 *
 * @author mauricio.junior
 */
public enum PastaEpub {
    
    EPUB("epub"),
    METAINF("META-INF"),
    OEBPS("OEBPS");
    
    private String descricao;

    private PastaEpub(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }  
    
}
