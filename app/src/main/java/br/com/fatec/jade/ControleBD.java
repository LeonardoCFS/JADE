package br.com.fatec.jade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

import br.com.fatec.jade.modelos.Avaliacao;
import br.com.fatec.jade.modelos.Marca;
import br.com.fatec.jade.modelos.Produto;
import br.com.fatec.jade.modelos.Resultado;

public class ControleBD extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "jade.db";
    private static final int DATABASE_VERSION = 1;

    public ControleBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Produto buscaProdutoByBarcode(String cod_barras) {

        String query = "Select * FROM produto WHERE cod_barras= \"" + cod_barras + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Produto produto = new Produto();
        c.moveToFirst();
        produto.setCod_barras(c.getString(0));
        produto.setNome(c.getString(1));
        produto.setMarca(c.getString(2));
        produto.setCategoria(c.getString(3));
        produto.setStatus(c.getString(4));
        produto.setFabricante(c.getString(5));
        produto.setCnpj(c.getString(6));
        produto.setImagem(c.getString(7));
        produto.setNota(c.getFloat(8));
        c.close();
        db.close();

        return produto;
    }

    public Marca buscaMarca(String cnpj) {
        String query = "Select * FROM marca WHERE cnpj=\"" + cnpj + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        Marca marca = new Marca();
        c.moveToFirst();
        marca.setCnpj(c.getString(0));
        marca.setNome(c.getString(1));
        marca.setStatus(c.getString(2));
        marca.setNota(c.getFloat(3));
        marca.setImagem(c.getString(4));
        c.close();
        db.close();
        return marca;
    }

    public void atualizaNotaMarca(String cnpj, ArrayList<Avaliacao> avaliacoes) {
        float nota = 0;
        for (int i = 0; i < avaliacoes.size(); i++) {
            nota = nota + avaliacoes.get(i).getNota();
        }
        nota = nota / avaliacoes.size();
        Log.d("UPDATE", String.valueOf(nota));
        ContentValues valores = new ContentValues();
        valores.put("nota", nota);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update("marca", valores, "cnpj=?", new String[]{cnpj});
    }

    public void atualizaNotaProduto(String cod_barras, ArrayList<Avaliacao> avaliacoes) {
        float nota = 0;
        for (int i = 0; i < avaliacoes.size(); i++) {
            nota = nota + avaliacoes.get(i).getNota();
        }
        nota = nota / avaliacoes.size();
        Log.d("UPDATE", String.valueOf(nota));
        ContentValues valores = new ContentValues();
        valores.put("nota", nota);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update("produto", valores, "cod_barras=?", new String[]{cod_barras});
    }

    public void insereComentarioMarca(String cnpj, String consumidor, String texto_avalicao, float nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cnpj", cnpj);
        valores.put("consumidor", consumidor);
        valores.put("nota", nota);
        valores.put("avaliacao", texto_avalicao);
        db.insert("avaliacao_marca", null, valores);
        String query = "Select * FROM avaliacao_marca WHERE cnpj= \"" + cnpj + "\"";
        ArrayList<Avaliacao> avaliacoes = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setConsumidor(c.getString(2));
            avaliacao.setNota(c.getFloat(3));
            avaliacao.setTexto_avalicacao(c.getString(4));
            avaliacoes.add(avaliacao);
        }
        c.close();
        this.atualizaNotaMarca(cnpj, avaliacoes);
        db.close();
    }

    public void insereComentarioProduto(String cod_barras, String consumidor, String texto_avalicao, float nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cod_barras", cod_barras);
        valores.put("consumidor", consumidor);
        valores.put("nota", nota);
        valores.put("avaliacao", texto_avalicao);
        db.insert("avaliacao_produto", null, valores);
        String query = "Select * FROM avaliacao_produto WHERE cod_barras= \"" + cod_barras + "\"";
        ArrayList<Avaliacao> avaliacoes = new ArrayList<>();
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setConsumidor(c.getString(2));
            avaliacao.setNota(c.getFloat(3));
            avaliacao.setTexto_avalicacao(c.getString(4));
            avaliacoes.add(avaliacao);
        }
        c.close();
        this.atualizaNotaProduto(cod_barras, avaliacoes);
        db.close();
    }

    public ArrayList<Resultado> encheLista(String busca) {

        ArrayList<Resultado> resultados = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM produto WHERE nome LIKE '%" + busca + "%'";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Resultado resultado = new Resultado();
            resultado.setChave(c.getString(0));
            resultado.setNome(c.getString(1));
            resultado.setCategoria("Produto");
            resultados.add(resultado);
        }
        c.close();

        sql = "SELECT * FROM marca WHERE nome LIKE '%" + busca + "%'";
        c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Resultado resultado = new Resultado();
            resultado.setChave(c.getString(0));
            resultado.setNome(c.getString(1));
            resultado.setCategoria("Marca");
            resultados.add(resultado);
        }
        c.close();

        sql = "SELECT * FROM produto WHERE cod_barras= \"" + busca + "\"";
        c = db.rawQuery(sql, null);
        while (c.moveToNext()) {
            Resultado resultado = new Resultado();
            resultado.setChave(c.getString(0));
            resultado.setNome(c.getString(1));
            resultado.setCategoria("Produto");
            resultados.add(resultado);
        }
        c.close();

        db.close();

        return resultados;
    }

    public ArrayList<Avaliacao> encheComentario(String busca, String categoria) {
        ArrayList<Avaliacao> avaliacoes = new ArrayList<>();
        String query;
        if (categoria.equals("produto")) {
            query = "Select * FROM avaliacao_produto WHERE cod_barras= \"" + busca + "\"";
        } else {
            query = "Select * FROM avaliacao_marca WHERE cnpj= \"" + busca + "\"";
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setConsumidor(c.getString(2));
            avaliacao.setNota(c.getFloat(3));
            avaliacao.setTexto_avalicacao(c.getString(4));
            avaliacoes.add(avaliacao);
        }
        c.close();
        db.close();
        return avaliacoes;
    }
}
