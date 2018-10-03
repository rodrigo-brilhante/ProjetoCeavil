
package ned.ceavil.modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author mauricio.junior
 */
public class Eixo {

    private String id;
    private String titulo;
    private String nomeImagem;
    private List<Indicador> indicadores;
    
    //private String nomePaginaEpub;

    public Eixo() {
        this.id = UUID.randomUUID().toString();
        this.indicadores = new ArrayList<>();
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

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public List<Indicador> getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(List<Indicador> indicadores) {
        this.indicadores = indicadores;
    }
    
    public void setIndicador(Indicador indicador) {
        this.indicadores.add(indicador);
    }
    
    
}
