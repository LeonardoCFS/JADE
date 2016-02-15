package br.com.fatec.jade.modelos;

public class Avaliacao {
    private String cod_barra;
    private String consumidor;
    private float nota;
    private String texto_avalicacao;


    public String getCod_barra() {
        return cod_barra;
    }

    public void setCod_barra(String cod_barra) {
        this.cod_barra = cod_barra;
    }

    public String getTexto_avalicacao() {
        return texto_avalicacao;
    }

    public void setTexto_avalicacao(String texto_avalicacao) {
        this.texto_avalicacao = texto_avalicacao;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(String consumidor) {
        this.consumidor = consumidor;
    }
}
