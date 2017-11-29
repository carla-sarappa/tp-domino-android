package ar.edu.unq.uis.domino;

import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

public class TextUtils {

    public static void setTextAsCurrency(TextView textView, Double precio){
        textView.setText(NumberFormat.getCurrencyInstance().format(precio));
    }
}
