package br.com.fatec.jade;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaResultadoTrueProduto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado_true_produto);
        String nome = getIntent().getStringExtra("nome");
        String marca = getIntent().getStringExtra("marca");
        String imagem = getIntent().getStringExtra("imagem");

        TextView nomeText = (TextView) findViewById(R.id.prodNomeTrue);
        TextView marcaText = (TextView) findViewById(R.id.marcaNomeTrue);
        ImageView imagemView = (ImageView) findViewById(R.id.imagemProdutoTrue);
        nomeText.setText(nome);
        marcaText.setText(marca);
        Resources res = getResources();
        int resourceId = res.getIdentifier(imagem, "drawable", getPackageName());
        imagemView.setImageResource(resourceId);
        ImageView imgAlternativo1 = (ImageView) findViewById(R.id.imgProdAlternativo1);
        ImageView imgAlternativo2 = (ImageView) findViewById(R.id.imgProdAlternativo2);
        TextView textAlternativo1 = (TextView) findViewById(R.id.nomeProdAlternativo1);
        TextView textAlternativo2 = (TextView) findViewById(R.id.nomeProdAlternativo2);
        if (nome.equals("Sabonete Lux")) {
            resourceId = res.getIdentifier("granado", "drawable", getPackageName());
            imgAlternativo1.setImageResource(resourceId);
            textAlternativo1.setText("Sabonete Granado");
            resourceId = res.getIdentifier("phebo", "drawable", getPackageName());
            imgAlternativo2.setImageResource(resourceId);
            textAlternativo2.setText("Sabonete Phebo");
        } else if (nome.equals("Creme Noturno de Hidratação")) {
            resourceId = res.getIdentifier("natura", "drawable", getPackageName());
            imgAlternativo1.setImageResource(resourceId);
            textAlternativo1.setText("Natura Erva Doce");
            resourceId = res.getIdentifier("boticario", "drawable", getPackageName());
            imgAlternativo2.setImageResource(resourceId);
            textAlternativo2.setText("O Boticário Cuide-se Bem Hidratante");
        } else if (nome.equals("Antitranspirante Women Powder Roll-on")) {
            resourceId = res.getIdentifier("frescor", "drawable", getPackageName());
            imgAlternativo1.setImageResource(resourceId);
            textAlternativo1.setText("Phebo Frescor da Manhã");
            resourceId = res.getIdentifier("roll_on", "drawable", getPackageName());
            imgAlternativo2.setImageResource(resourceId);
            textAlternativo2.setText("Cativa Natureza");
        }
    }
}
