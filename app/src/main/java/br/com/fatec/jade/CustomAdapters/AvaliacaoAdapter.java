package br.com.fatec.jade.CustomAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fatec.jade.R;
import br.com.fatec.jade.modelos.Avaliacao;

public class AvaliacaoAdapter extends ArrayAdapter<Avaliacao> {

    public AvaliacaoAdapter(Context context, ArrayList<Avaliacao> avaliacacoes) {
        super(context, 0, avaliacacoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Avaliacao avaliacao = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_avaliacao, parent, false);
        }
        // Lookup view for data population
        TextView avaliacaoConsumidor = (TextView) convertView.findViewById(R.id.avalicaoConsumidor);
        RatingBar avaliacaoNota = (RatingBar) convertView.findViewById(R.id.avaliacaoNota);
        TextView avaliacaoAvaliacao = (TextView) convertView.findViewById(R.id.avaliacaoAvalicao);
        // Populate the data into the template view using the data object
        avaliacaoConsumidor.setText(avaliacao.getConsumidor());
        avaliacaoNota.setRating(avaliacao.getNota());
        avaliacaoNota.setIsIndicator(true);
        avaliacaoAvaliacao.setText(avaliacao.getTexto_avalicacao());
        // Return the completed view to render on screen
        return convertView;
    }
}
