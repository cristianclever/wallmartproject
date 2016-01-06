package br.com.wallmart.facade;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.wallmart.exception.BusinessException;
import br.com.wallmart.model.Caminho;
import br.com.wallmart.model.Mapa;
import es.usc.citius.hipster.algorithm.Algorithm.SearchResult;
import es.usc.citius.hipster.algorithm.Hipster;
import es.usc.citius.hipster.graph.GraphBuilder;
import es.usc.citius.hipster.graph.GraphSearchProblem;
import es.usc.citius.hipster.graph.HipsterDirectedGraph;
import es.usc.citius.hipster.model.impl.WeightedNode;
import es.usc.citius.hipster.model.problem.SearchProblem;

@Stateless
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MalhaLogisticaBean {

	
	public static final Logger log = Logger.getLogger(MalhaLogisticaBean.class.getSimpleName());
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("MainBean.postConstruct");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("MainBean.preDestroy");
	}

	
	public void clearDatabase(){
		//LIMPA TODA A BASE
		em.createNativeQuery("DELETE FROM CAMINHO").executeUpdate();
		em.createNativeQuery("DELETE FROM MAPA").executeUpdate();
		
		log.info("Limpou a base de dados");
	
	}
	
	
	
	@Resource
	private SessionContext sessionContext;

	@PersistenceContext(unitName = "wallMartPU")
	private EntityManager em;

	public double searchShortestPath(String codMapa, String codOrigem, String codDestino, double autonomia,	double valorLitro) throws BusinessException{
		
		double retorno = 0;
		
		
		if (codMapa == null || codOrigem == null || codDestino == null) {
			throw new BusinessException("Parametros invalidos");
		}

		//Antes de iniciar todo o processamento do grafo, deve verificar se existe Inicio e Fim do processo (apara o mapa)
		/*
		,@NamedQuery(name = "findExistsInicio", query = "FROM Caminho u WHERE u.mapa.codMapa =:codMapa and u.codInicio=:codInicio")
		,@NamedQuery(name = "findExistsFim", query = "FROM Caminho u WHERE u.mapa.codMapa =:codMapa and u.codFim=:codFim")		
		*/

		Query queryOrigem = em.createNamedQuery("findExistsInicio", Caminho.class).setParameter("codMapa",codMapa).setParameter("codInicio",codOrigem);
		Query queryDestino = em.createNamedQuery("findExistsFim", Caminho.class).setParameter("codMapa",codMapa).setParameter("codFim",codDestino);

		
		Long countOrigem = (Long) queryOrigem.getSingleResult();
		Long countDestino = (Long) queryDestino.getSingleResult();
		
		if(countOrigem<1||countDestino<1){
			log.warning("Origem ou destino inexistentes: [codMapa:"+codMapa + ", codInicio:"+codOrigem + ", codFim:"+codDestino+"]" );
			throw new BusinessException("Origem ou destino inexistentes");
		}
		
		
		
		List<Caminho> listCaminhos = null;

		try {
			TypedQuery<Caminho> query = em.createNamedQuery("findAllCaminhosByCodMapa", Caminho.class).setParameter("codMapa",codMapa);
			listCaminhos = query.getResultList();
		} catch (NoResultException e) {
			// TODO:JA SABEMOS QUE EXISTE UMA SOLUCAO
		}

		GraphBuilder<String, Double> graphBuilder = GraphBuilder.<String, Double> create();

		for (Caminho caminho : listCaminhos) {
			graphBuilder = graphBuilder.connect(caminho.getCodInicio()).to(caminho.getCodFim()).withEdge(caminho.getDistancia());
		}

		HipsterDirectedGraph<String, Double> graph = graphBuilder.createDirectedGraph();
		SearchProblem p = GraphSearchProblem.startingFrom(codOrigem).in(graph).takeCostsFromEdges().build();

		SearchResult sr = Hipster.createDijkstra(p).search(codDestino);
		WeightedNode n = (WeightedNode) sr.getGoalNode();
		Double custo = (Double) n.getCost();
		
		//Agora que tem o custo, realiza o restante
		
		
		
		retorno = custo * valorLitro /autonomia;

		return retorno;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Boolean cadastrarNovoCaminho(String codMapa, String codOrigem, String codDestino, Double distancia) throws BusinessException, PersistenceException {

		
		if (codMapa == null || codOrigem == null || codDestino == null||distancia==null) {
			throw new BusinessException("Parametros invalidos");
		}		
		
		
		//se mapa nao existir, cria
		Mapa mapa = null;
		try {
			TypedQuery<Mapa> query = em.createNamedQuery("findByCodMapa", Mapa.class).setParameter("codMapa", codMapa);
			mapa =  query.getSingleResult();
		} catch (NoResultException e) {
			// TODO:nada
		}		
		
		if(mapa==null){
			mapa = new Mapa();
			mapa.setCodMapa(codMapa);			
			//cria
			em.persist(mapa);
			em.flush();
		}
		

		
		
		
		
		//cria o caminho
		// se o caminho ja existe, apenas atualiza a distancia
//		@NamedQuery(name = "findByKeys", query = "SELECT u FROM Caminho u WHERE u.mapa.codMapa =:codMapa and u.codInicio=:codInicio and u.codFim=:codFim")
		Caminho c = null;
		try {
			TypedQuery<Caminho> query = em.createNamedQuery("findByKeys", Caminho.class)
					.setParameter("codMapa", codMapa)
					.setParameter("codInicio", codOrigem)
					.setParameter("codFim", codDestino);
			c =  query.getSingleResult();//so pode haver um
		} catch (NoResultException e) {
			// TODO:nada
		}		
		
		if(c!=null){
			//managed
			c.setDistancia(distancia);
		}
		else{
			//Novo
			c = new Caminho();
			c.setMapa(mapa);
			c.setCodInicio(codOrigem);
			c.setCodFim(codDestino);
			c.setDistancia(distancia);

			em.persist(c);			
		}
		
		
		return Boolean.TRUE;
	}

}
