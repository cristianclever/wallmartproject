package br.com.wallmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "findByCodMapa", query = "SELECT u FROM Mapa u WHERE u.codMapa =:codMapa")
		})

public class Mapa extends BaseEntity{

	public String getCodMapa() {
		return codMapa;
	}

	public void setCodMapa(String codMapa) {
		this.codMapa = codMapa;
	}

	@Column(length=20,unique=true,nullable = false)
	private String codMapa;	
}
