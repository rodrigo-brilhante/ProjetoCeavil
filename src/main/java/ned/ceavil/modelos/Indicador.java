
package ned.ceavil.modelos;

import java.util.UUID;

/**
 *
 * @author mauricio.junior
 */
public class Indicador {

    private String id;
    private String titulo;
    private String descricao;
    private String nomeInfografico;
    private String[] localizacao;

    public Indicador() {
        this.id = UUID.randomUUID().toString();
        localizacao = new String[3];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getNomeInfografico() {
        return nomeInfografico;
    }

    public void setNomeInfografico(String nomeInfografico) {
        this.nomeInfografico = nomeInfografico;
    }

    public String[] getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(int indice, String info) {
        this.localizacao[indice] = info;
    }
    
}
