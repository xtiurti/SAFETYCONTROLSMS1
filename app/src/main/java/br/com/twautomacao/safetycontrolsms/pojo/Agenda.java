package br.com.twautomacao.safetycontrolsms.pojo;

import java.io.Serializable;

/**
 * Created by Ricardo on 18/08/2014.
 */
public class Agenda implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String numero;
    String nome;
    String senha;

    public Agenda(String numero, String nome, String senha) {
        this.numero = numero;
        this.nome = nome;
        this.senha = senha;
    }

    public void setNumero(String numero) {

        this.numero = numero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumero() {

        return numero;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }
}
