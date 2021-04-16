package br.com.zup.Treinopropostas.Proposta;

import br.com.zup.Treinopropostas.Proposta.Enum.StatusCliente;

import javax.swing.text.html.parser.Entity;

public class Solicitacao {

    private String documento;
    private String nome;
    private StatusCliente resultadoSolicitacao;
    private String idProposta;

    public Solicitacao(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public Solicitacao(StatusCliente resultadoSolicitacao) {
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public Solicitacao(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public StatusCliente getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }

}
