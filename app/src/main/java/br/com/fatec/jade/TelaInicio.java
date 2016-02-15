package br.com.fatec.jade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import br.com.fatec.jade.barcode.BarcodeCaptureActivity;
import br.com.fatec.jade.modelos.Produto;
import br.com.fatec.jade.opcoes.SettingsActivity;

public class TelaInicio extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private ControleBD banco_dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicio);
        banco_dados = new ControleBD(this);
        findViewById(R.id.fab).setOnClickListener(this);
        findViewById(R.id.botaoBusca).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sobre) {
            Intent it = new Intent(this, TelaSobre.class);
            startActivity(it);
        } else if (id == R.id.login) {
            Intent it = new Intent(this, MainActivity.class);
            it.putExtra("abrindo", false);
            startActivity(it);
        } else if (id == R.id.opcoes) {
            Intent it = new Intent(this, SettingsActivity.class);
            startActivity(it);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            // launch barcode activity.
            Intent intent = new Intent(this, BarcodeCaptureActivity.class);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            Boolean focus = preferences.getBoolean("foco", false);
            Boolean flash = preferences.getBoolean("flash", false);
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, focus);
            intent.putExtra(BarcodeCaptureActivity.UseFlash, flash);

            startActivityForResult(intent, RC_BARCODE_CAPTURE);
        }
        if (v.getId() == R.id.botaoBusca) {
            this.buscar(v);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                    Produto produto = banco_dados.buscaProdutoByBarcode(barcode.displayValue);
                    if (produto.getStatus().equals("Negativo")) {
                        String nome = produto.getNome();
                        String marca = produto.getMarca();
                        String imagem = produto.getImagem();
                        float nota = produto.getNota();
                        Intent it = new Intent(TelaInicio.this, TelaResultadoFalseProduto.class);
                        it.putExtra("nome", nome);
                        it.putExtra("marca", marca);
                        it.putExtra("imagem", imagem);
                        it.putExtra("nota", nota);
                        it.putExtra("chave", barcode.displayValue);
                        startActivity(it);
                    } else {
                        String nome = produto.getNome();
                        String marca = produto.getMarca();
                        String imagem = produto.getImagem();
                        Intent it = new Intent(TelaInicio.this, TelaResultadoTrueProduto.class);
                        it.putExtra("nome", nome);
                        it.putExtra("marca", marca);
                        it.putExtra("imagem", imagem);
                        startActivity(it);
                    }
                } else {
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void buscar(View v) {
        EditText textoBusca = (EditText) findViewById(R.id.campoBusca);
        String busca = textoBusca.getText().toString();
        Intent it = new Intent(this, ListaResultados.class);
        it.putExtra("busca", busca);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
