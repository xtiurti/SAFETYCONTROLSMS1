package br.com.twautomacao.safetycontrolsms.pojo;

import java.io.Serializable;
import java.text.Collator;

public class Comando implements Comparable<Comando>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nome;
	String comando;
	Integer imageId;
	String descricao;

	public Comando() {

	}

	public String getDescricao() {
		return descricao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comando other = (Comando) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getImageId() {
		return imageId;
	}
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
	public Comando(String nome, String comando, int imageId) {
		super();
		this.nome = nome;
		this.comando = comando;
		this.imageId = imageId;
	}
	@Override
	public int compareTo(Comando another) {
		return this.nome.compareToIgnoreCase(another.nome);
	}
}
