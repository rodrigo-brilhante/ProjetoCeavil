package ned.ceavil.modelos;
/**
 * @author mauricio.junior
 */
public enum LocalizacaoIndicador {
    
    NUMEROPAGINAPDI(0), NUMEROINDICADOR(1), NOMEDAPASTA(2);
    
    public int indiceLocalizacao;

    private LocalizacaoIndicador(int indiceLocalizacao) {
        this.indiceLocalizacao = indiceLocalizacao;
    }

    public int getIndiceLocalizacao() {
        return indiceLocalizacao;
    }
       
}
