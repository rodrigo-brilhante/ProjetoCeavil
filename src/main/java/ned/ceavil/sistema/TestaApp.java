/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ned.ceavil.sistema;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import ned.ceavil.modelos.EbookFile;
import ned.ceavil.modelos.EbookJson;
import ned.ceavil.modelos.Ebook;
import ned.ceavil.modelos.Eixo;
import ned.ceavil.modelos.Indicador;
import ned.ceavil.modelos.Utils;

/**
 *
 * @author mauricio.junior
 */
public class TestaApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic 

        UUID idEbook = UUID.randomUUID();
        
        
        Ebook ebook1 = new Ebook();
        ebook1.setTitulo("EaD na UNIFENAS");
        ebook1.setCapa("capa-ebook.jpg");
        
        EbookJson dbJson = new EbookJson();
        try {

            //Gerar ebook
            dbJson.generateEbookJson(ebook1);

            //Ler ebook
            Ebook ebookLido = dbJson.readEbookJson("EaDnaUNIFENAS.json");

            Eixo eixo1 = new Eixo();
            eixo1.setTitulo("EIXO 1 – PLANEJAMENTO E AVALIAÇÃO INSTITUCIONAL");
            eixo1.setNomeImagem("imagem-eixo1.jpg");
            
            
            
            //dbJson.generateEbookJson(ebookLido);

            
            Indicador indicador11 = new Indicador();
            indicador11.setTitulo("1.1 Projeto de autoavaliação institucional");
            indicador11.setDescricao("Na UNIFENAS, há um projeto de Autoavaliação Institucional (AI), desde 2004, que atende às necessidades institucionais, como instrumento de gestão e de ação acadêmico-administrativa de melhoria institucional, que leva em consideração suas características de universidade multicâmpus.");
            indicador11.setNomeImagem("info-indicador11.jpg");
            indicador11.setLocalizacao(0, "321");
            indicador11.setLocalizacao(1, "1.1");
            indicador11.setLocalizacao(2, "Eixo 1");
            
            eixo1.setIndicador(indicador11);
            
            Indicador indicador12 = new Indicador();
            indicador12.setTitulo("1.2 Auto avaliação institucional: participação da comunidade acadêmica");
            indicador12.setDescricao("O projeto de autoavaliação descreve como ocorre a participação de todos os segmentos da comunidade acadêmica e da sociedade civil organizada (vedada a composição que privilegie a maioria absoluta de um deles), abrangendo instrumentos de coleta diversificados (voltados à particularidades de cada segmento e objeto de análise) e estratégias para fomentar o engajamento crescente.");
            indicador12.setNomeImagem("info-indicador12.jpg");
            indicador12.setLocalizacao(0, "327");
            indicador12.setLocalizacao(1, "1.2");
            indicador12.setLocalizacao(2, "Eixo 1");
            
            eixo1.setIndicador(indicador12);
            
            ebookLido.setEixo(eixo1);
            
            dbJson.generateEbookJson(ebookLido);
            
            // Criar estrutura de pastas para o Ebook.
            EbookFile ebookFile = new EbookFile();
            ebookFile.criarEstuturaPastas( Utils.removerAcentosEspacos(ebook1.getTitulo()));
            ebookFile.criarEstruturaAquivos();

        } catch (IOException ex) {
            Logger.getLogger(TestaApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
