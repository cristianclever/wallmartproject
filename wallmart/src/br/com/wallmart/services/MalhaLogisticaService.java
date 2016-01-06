package br.com.wallmart.services;

import javax.ejb.EJB;
import javax.jws.WebService;

import br.com.wallmart.exception.BusinessException;
import br.com.wallmart.facade.MalhaLogisticaBean;

@WebService
public interface MalhaLogisticaService {
	public double searchShortestPath(String codMapa, String codOrigem, String codDestino, double autonomia, double valorLitro) throws BusinessException;
	public Boolean cadastrarNovoCaminho(String codMapa, String codOrigem, String codDestino, Double distancia) throws BusinessException;

}