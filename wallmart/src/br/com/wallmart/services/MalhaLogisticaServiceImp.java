package br.com.wallmart.services;

import javax.ejb.EJB;
import javax.jws.WebService;

import br.com.wallmart.exception.BusinessException;
import br.com.wallmart.facade.MalhaLogisticaBean;

@WebService(endpointInterface = "br.com.wallmart.services.MalhaLogisticaService")
@SuppressWarnings("rawtypes")
public class MalhaLogisticaServiceImp {

	@EJB
	MalhaLogisticaBean malhaLogisticaBean;

	public double searchShortestPath(String codMapa, String codOrigem, String codDestino, double autonomia, double valorLitro) throws BusinessException {
		return malhaLogisticaBean.searchShortestPath(codMapa, codOrigem, codDestino, autonomia, valorLitro);
	}

	public Boolean cadastrarNovoCaminho(String codMapa, String codOrigem, String codDestino, Double distancia) throws BusinessException {
		malhaLogisticaBean.cadastrarNovoCaminho(codMapa, codOrigem, codDestino, distancia);
		return Boolean.TRUE;
	}

}