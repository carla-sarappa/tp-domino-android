package ar.edu.unq.uis.domino.utils;

import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;

import kotlin.jvm.functions.Function1;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

public class TextUtils {

    // Estos serian como los transformers de Arena o los filters de Angular
    // pero aca los tengo que llamar a mano, no puedo agregarlos en el binding
    // o en el layout. Asi que los dejo como metodos estaticos para poder usarlos
    // desde varios lugares

    public static void setTextAsCurrency(TextView textView, Double precio){
        textView.setText(NumberFormat.getCurrencyInstance().format(precio));
    }

    public static <T> String joinList(List<T> lista, Function1<T,String> extractor, String separador, String ultimoSeparador) {
        StringBuilder s = new StringBuilder();
        for(int i=0; i < lista.size();i++) {
            s.append(extractor.invoke(lista.get(i)));
            if (i < lista.size() - 2) s.append(separador);
            if (i == lista.size() - 2) s.append(ultimoSeparador);
        }
        return s.toString();
    }
}
