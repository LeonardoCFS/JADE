package br.com.fatec.jade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.fatec.jade.CustomAdapters.ResultadoAdapter;
import br.com.fatec.jade.modelos.Marca;
import br.com.fatec.jade.modelos.Produto;
import br.com.fatec.jade.modelos.Resultado;

public class ListaResultados extends AppCompatActivity {

    private ControleBD banco_dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_resultados);
        String busca = getIntent().getStringExtra("busca");
        ListView lv = (ListView) findViewById(R.id.lista);
        banco_dados = new ControleBD(this);

        lv.setOnItemClickListener(new ListClickHandler());

        // Construct the data source
        ArrayList<Resultado> resultados;
        resultados = banco_dados.encheLista(busca);
        // Create the adapter to convert the array to views
        ResultadoAdapter adapter = new ResultadoAdapter(this, resultados);
        // Attach the adapter to a ListView
        lv.setAdapter(adapter);
    }

    private class ListClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            TextView textCategoria = (TextView) view.findViewById(R.id.resultadoCategoria);
            String categoria = textCategoria.getText().toString();
            TextView textChave = (TextView) view.findViewById(R.id.resultadoChave);
            String chave = textChave.getText().toString();
            if (categoria.equals("Produto")) {
                Produto produto = banco_dados.buscaProdutoByBarcode(chave);
                if (produto.getStatus().equals("Negativo")) {
                    String nome = produto.getNome();
                    String marca = produto.getMarca();
                    String imagem = produto.getImagem();
                    float nota = produto.getNota();
                    Intent it = new Intent(ListaResultados.this, TelaResultadoFalseProduto.class);
                    it.putExtra("nome", nome);
                    it.putExtra("marca", marca);
                    it.putExtra("imagem", imagem);
                    it.putExtra("nota", nota);
                    it.putExtra("chave", chave);
                    startActivity(it);
                } else {
                    String nome = produto.getNome();
                    String marca = produto.getMarca();
                    String imagem = produto.getImagem();
                    Intent it = new Intent(ListaResultados.this, TelaResultadoTrueProduto.class);
                    it.putExtra("nome", nome);
                    it.putExtra("marca", marca);
                    it.putExtra("imagem", imagem);
                    startActivity(it);
                }
            } else {
                Marca marca = banco_dados.buscaMarca(chave);
                if (marca.getStatus().equals("Negativo")) {
                    String nome = marca.getNome();
                    float nota = marca.getNota();
                    String imagem = marca.getImagem();
                    Intent it = new Intent(ListaResultados.this, TelaResultadoFalseMarca.class);
                    it.putExtra("nome", nome);
                    it.putExtra("nota",nota);
                    it.putExtra("imagem",imagem);
                    it.putExtra("chave", chave);
                    startActivity(it);
                } else {
                    String nome = marca.getNome();
                    String imagem = marca.getImagem();
                    Intent it = new Intent(ListaResultados.this, TelaResultadoTrueMarca.class);
                    it.putExtra("nome", nome);
                    it.putExtra("imagem",imagem);
                    startActivity(it);
                }
            }
        }
    }
}
