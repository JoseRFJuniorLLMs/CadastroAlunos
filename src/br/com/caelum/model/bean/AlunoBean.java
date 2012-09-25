package br.com.caelum.model.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONStringer;

public class AlunoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private String nome;
	private String fone;
	private String end;
	private String site;
	private double nota;
	private String foto;

	public AlunoBean() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public String toString() {
		return this.id + " - " + this.nome;
	}

	public String toJSON() throws JSONException {
		JSONStringer j = new JSONStringer();
		j.object().key("id").value(id).key("nome").value(nome).key("fone")
				.value(fone).key("end").value(end).key("foto").value(foto)
				.key("nota").value(nota).endObject();

		return j.toString();
	}

}