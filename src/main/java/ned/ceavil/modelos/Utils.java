/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ned.ceavil.modelos;

import java.text.Normalizer;

/**
 *
 * @author mauricio.junior
 */
public class Utils {

    public static String removerAcentosEspacos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll(" +", "");
    }

}
