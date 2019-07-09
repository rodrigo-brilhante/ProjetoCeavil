
package ned.ceavil.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mauricio.junior
 */
public class Eixo {

    private Integer id;
    private String titulo;
    private String nomeImagem;
    private String caminhoImagem;
    private List<Indicador> indicadores;
    private boolean existe = false;

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Eixo() {
        this.indicadores = new ArrayList<>();
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

    public String getNomeImagem() {
        return nomeImagem;
    }

    public void setNomeImagem(String nomeImagem) {
        this.nomeImagem = nomeImagem;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }
    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
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
    
    public Eixo cloneEixo(){
        Eixo aux = new Eixo();
        aux.id = this.id;
        aux.titulo = this.titulo;
        aux.caminhoImagem = this.caminhoImagem;
        aux.nomeImagem = this.nomeImagem;
        this.indicadores.forEach((indicador) -> {
            aux.indicadores.add(indicador.cloneIndicador());
        });
        return aux;
    }
}
