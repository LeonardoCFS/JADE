package br.com.fatec.jade.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fatec.jade.R;
import br.com.fatec.jade.modelos.Resultado;

public class ResultadoAdapter extends ArrayAdapter<Resultado> {

    public ResultadoAdapter(Context context, ArrayList<Resultado> resultados) {
        super(context, 0, resultados);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Resultado resultado = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_resultado, parent, false);
        }
        // Lookup view for data population
        TextView resultadoCategoria = (TextView) convertView.findViewById(R.id.resultadoCategoria);
        TextView resultadoNome = (TextView) convertView.findViewById(R.id.resultadoNome);
        TextView resultadoChave = (TextView) convertView.findViewById(R.id.resultadoChave);
        // Populate the data into the template view using the data object
        resultadoCategoria.setText(resultado.getCategoria());
        resultadoNome.setText(resultado.getNome());
        resultadoChave.setText(resultado.getChave());
        // Return the completed view to render on screen
        return convertView;
    }


}
