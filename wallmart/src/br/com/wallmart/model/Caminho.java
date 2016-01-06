package br.com.wallmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
//para evitar duplicacoes
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"codInicio","codFim","mapa_id"})})
@NamedQueries(value = {
		@NamedQuery(name = "findByKeys", query = "SELECT u FROM Caminho u WHERE u.mapa.codMapa =:codMapa and u.codInicio=:codInicio and u.codFim=:codFim")
		,@NamedQuery(name = "findAllCaminhosByCodMapa", query = "FROM Caminho u WHERE u.mapa.codMapa =:codMapa")
		,@NamedQuery(name = "findExistsInicio", query = "SELECT COUNT(u) FROM Caminho u WHERE u.mapa.codMapa =:codMapa and u.codInicio=:codInicio")
		,@NamedQuery(name = "findExistsFim", query = "SELECT COUNT(u) FROM Caminho u WHERE u.mapa.codMapa =:codMapa and u.codFim=:codFim")
		

})

public class Caminho extends BaseEntity {

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public String getCodInicio() {
		return codInicio;
	}

	public void setCodInicio(String codInicio) {
		this.codInicio = codInicio;
	}

	public String getCodFim() {
		return codFim;
	}

	public void setCodFim(String codFim) {
		this.codFim = codFim;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	@ManyToOne
	@JoinColumn(name="MAPA_ID")
	private Mapa mapa;

	@Column(length=20,nullable = false)
	private String codInicio;

	@Column(length=20,nullable = false)
	private String codFim;
	
	@Column(nullable = false)
	private Double distancia; 

}
