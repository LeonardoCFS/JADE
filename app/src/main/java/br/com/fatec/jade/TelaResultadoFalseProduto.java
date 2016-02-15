package br.com.fatec.jade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.fatec.jade.modelos.Produto;

public class TelaResultadoFalseProduto extends AppCompatActivity {

    String chave;
    private ControleBD banco_dados;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String consumidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado_false_produto);
        banco_dados = new ControleBD(this);

        String nome = getIntent().getStringExtra("nome");
        String marca = getIntent().getStringExtra("marca");
        String imagem = getIntent().getStringExtra("imagem");
        chave = getIntent().getStringExtra("chave");
        float nota = getIntent().getFloatExtra("nota", 5);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        consumidor = preferences.getString("usuario", "FALHOU DE NOVO");

        TextView nomeText = (TextView) findViewById(R.id.prodNomeFalse);
        TextView marcaText = (TextView) findViewById(R.id.marcaNomeFalse);
        ImageView imagemView = (ImageView) findViewById(R.id.imagemProdutoFalse);
        RatingBar notaBar = (RatingBar) findViewById(R.id.barraAvaliacaoProduto);
        nomeText.setText(nome);
        marcaText.setText(marca);
        Resources res = getResources();
        int resourceId = res.getIdentifier(imagem, "drawable", getPackageName());
        imagemView.setImageResource(resourceId);
        notaBar.setRating(nota);

        //impede que o foco não vá para o campo de texto e inicie o teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void listarComentarioProduto(View view) {
        Intent it = new Intent(this, ActivityComentario.class);
        it.putExtra("chave", chave);
        it.putExtra("categoria", "produto");
        startActivity(it);
    }

    public void novoComentarioProduto(View view) {
        EditText texto = (EditText) findViewById(R.id.campoComentario);
        RatingBar notaBar = (RatingBar) findViewById(R.id.barraAvaliacaoProduto);
        String texto_avaliacao = texto.getText().toString();
        banco_dados.insereComentarioProduto(chave, consumidor, texto_avaliacao, notaBar.getRating());
        texto.setText("");
        Produto produto = banco_dados.buscaProdutoByBarcode(chave);
        float nota = produto.getNota();
        notaBar.setRating(nota);
        Toast.makeText(getApplicationContext(), "Enviado", Toast.LENGTH_SHORT).show();
    }
}
