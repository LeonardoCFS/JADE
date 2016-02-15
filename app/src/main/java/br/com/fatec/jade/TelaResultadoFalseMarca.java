package br.com.fatec.jade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.fatec.jade.modelos.Marca;

public class TelaResultadoFalseMarca extends AppCompatActivity {

    String chave;
    private ControleBD banco_dados;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private String consumidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_resultado_false_marca);
        banco_dados = new ControleBD(this);

        String nome = getIntent().getStringExtra("nome");
        String imagem = getIntent().getStringExtra("imagem");
        chave = getIntent().getStringExtra("chave");
        float nota = getIntent().getFloatExtra("nota", 0);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        consumidor = preferences.getString("usuario", "FALHOU DE NOVO");

        TextView nomeText = (TextView) findViewById(R.id.marcaNomeMFalse);
        RatingBar notaBar = (RatingBar) findViewById(R.id.barraAvaliacaoMarca);
        ImageView imagemView = (ImageView) findViewById(R.id.imgMarca);
        nomeText.setText(nome);
        notaBar.setRating(nota);
        Resources res = getResources();
        int resourceId = res.getIdentifier(imagem, "drawable", getPackageName());
        imagemView.setImageResource(resourceId);
        //impede que o foco não vá para o campo de texto e inicie o teclado
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public void listarComentarioMarca(View view) {
        Intent it = new Intent(this, ActivityComentario.class);
        it.putExtra("chave", chave);
        it.putExtra("categoria", "marca");
        startActivity(it);
    }

    public void novoComentarioMarca(View view) {
        EditText texto = (EditText) findViewById(R.id.campoComentario);
        RatingBar notaBar = (RatingBar) findViewById(R.id.barraAvaliacaoMarca);
        String texto_avaliacao = texto.getText().toString();
        banco_dados.insereComentarioMarca(chave, consumidor, texto_avaliacao, notaBar.getRating());
        texto.setText("");
        Marca marca = banco_dados.buscaMarca(chave);
        float nota = marca.getNota();
        notaBar.setRating(nota);
        Toast.makeText(getApplicationContext(), "Enviado", Toast.LENGTH_SHORT).show();
    }
}
