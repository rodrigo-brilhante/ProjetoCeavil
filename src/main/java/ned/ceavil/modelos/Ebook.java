/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ned.ceavil.modelos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author mauricio.junior
 */
public class Ebook {
    
    private String id;
    private String titulo;
    private String capa;
    private List<Eixo> eixos;

    public Ebook() {
        this.id = UUID.randomUUID().toString();
        this.eixos = new ArrayList<>();
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

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public List<Eixo> getEixos() {
        return eixos;
    }

    public void setEixos(List<Eixo> eixos) {
        this.eixos = eixos;
    }
    
    public void setEixo(Eixo eixo) {
        this.eixos.add(eixo);
    }
    
}
