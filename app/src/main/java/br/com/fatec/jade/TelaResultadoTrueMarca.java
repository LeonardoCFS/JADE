package br.com.fatec.jade;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaResultadoTrueMarca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado_true_marca);

        String nome = getIntent().getStringExtra("nome");
        String imagem = getIntent().getStringExtra("imagem");

        TextView nomeText = (TextView) findViewById(R.id.marcaNomeMTrue);
        ImageView imagemView = (ImageView) findViewById(R.id.imgMarca);

        nomeText.setText(nome);
        Resources res = getResources();
        int resourceId = res.getIdentifier(imagem, "drawable", getPackageName());
        imagemView.setImageResource(resourceId);

        ImageView imgAlternativo1 = (ImageView) findViewById(R.id.imgMarcaAlternativa1);
        ImageView imgAlternativo2 = (ImageView) findViewById(R.id.imgMarcaAlternativa2);
        TextView textAlternativo1 = (TextView) findViewById(R.id.nomeMarcaAlternativa1);
        TextView textAlternativo2 = (TextView) findViewById(R.id.nomeMarcaAlternativa2);
        if (nome.equals("Lux")) {
            resourceId = res.getIdentifier("granado_logo", "drawable", getPackageName());
            imgAlternativo1.setImageResource(resourceId);
            textAlternativo1.setText("Granado");
            resourceId = res.getIdentifier("phebo_logo", "drawable", getPackageName());
            imgAlternativo2.setImageResource(resourceId);
            textAlternativo2.setText("Phebo");
        } else if (nome.equals("Avon")) {
            resourceId = res.getIdentifier("natura_logo", "drawable", getPackageName());
            imgAlternativo1.setImageResource(resourceId);
            textAlternativo1.setText("Natura");
            resourceId = res.getIdentifier("boticario_logo", "drawable", getPackageName());
            imgAlternativo2.setImageResource(resourceId);
            textAlternativo2.setText("O Boticário");
        } else if (nome.equals("Rexona")) {
            resourceId = res.getIdentifier("phebo_logo", "drawable", getPackageName());
            imgAlternativo1.setImageResource(resourceId);
            textAlternativo1.setText("Phebo");
            resourceId = res.getIdentifier("cativa_logo", "drawable", getPackageName());
            imgAlternativo2.setImageResource(resourceId);
            textAlternativo2.setText("Cativa");
        }
    }

}
