

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import br.com.wallmart.facade.MalhaLogisticaBean;
import br.com.wallmart.services.MalhaLogisticaService;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test {

	private static EJBContainer container;


	public static final String CLASSES = "C:/DEV/workspace/wallmart/build/classes";
	
	
	
	@BeforeClass
	public static void setUpClass() throws Exception {

		System.out.println("Inicializando Ambiente...");

		Map<String, Object> properties = new HashMap<String, Object>();

		File[] modules = { 
				new File(CLASSES)

		};


		properties.put(EJBContainer.MODULES, modules);
		properties.put("org.glassfish.ejb.embedded.glassfish.configuration.file", "teste-resources/domain.xml");
		container = EJBContainer.createEJBContainer(properties);
		//System.setProperty("java.security.auth.login.config","teste-resources/login.conf");
		int x = 0;
	}

	@AfterClass
	public static void finalizer() {

		if (container != null) {
			System.out.println("Encerrando container");
			container.close();
			System.out.println("Container Finalizado.");
		}
	}

	
	private MalhaLogisticaBean getMalhaLogisticaBean(){
		EJBContainer ejbcont = container;
		MalhaLogisticaBean mbean = null;
		try {
			mbean = (MalhaLogisticaBean) container.getContext().lookup("java:global/classes/MalhaLogisticaBean");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mbean;
	}
	@org.junit.Test
	public void testA_clean() throws Exception {
		MalhaLogisticaBean mbean = getMalhaLogisticaBean();
		mbean.clearDatabase();
	}
	 
	
	
	@org.junit.Test
	public void testB_CreateMapaCaminho() throws Exception {


		MalhaLogisticaBean mbean = getMalhaLogisticaBean();
		/*
		A B 10
		B D 15
		A C 20
		C D 30
		B E 50
		D E 30
		*/
		
		mbean.cadastrarNovoCaminho("SP", "A", "B", 1000D);
		mbean.cadastrarNovoCaminho("SP", "A", "B", 10D);
		mbean.cadastrarNovoCaminho("SP2", "A", "B", 10D);
		
		
		mbean.cadastrarNovoCaminho("SP", "B", "D", 15D);
		mbean.cadastrarNovoCaminho("SP", "A", "C", 20D);
		mbean.cadastrarNovoCaminho("SP", "C", "D", 30D);
		mbean.cadastrarNovoCaminho("SP", "B", "E", 50D);
		mbean.cadastrarNovoCaminho("SP", "D", "E", 30D);
		
		mbean.cadastrarNovoCaminho("SP", "A", "B1", 1D); //7
		mbean.cadastrarNovoCaminho("SP", "B1", "C1", 1D);
		mbean.cadastrarNovoCaminho("SP", "C1", "D1", 1D);
		mbean.cadastrarNovoCaminho("SP", "D1", "D2", 1D);
		mbean.cadastrarNovoCaminho("SP", "D2", "D3", 1D);
		mbean.cadastrarNovoCaminho("SP", "D3", "D4", 1D);
		mbean.cadastrarNovoCaminho("SP", "D4", "E", 1D);
		
		
		
	}
	

	@org.junit.Test
	public void testC_BuscaSolucao() throws Exception {
		
		MalhaLogisticaBean mbean = getMalhaLogisticaBean();
		
		
		double result = mbean.searchShortestPath("SP", "A", "D", 10D, 2.5);
		
		
		assertTrue(result==6.25);
		System.out.println("Test.testBuscaSolucao()");


	}
	
	@org.junit.Test(expected=EJBException.class)
	public void testC_BuscaSolucaoInexistente() throws Exception {
		
		MalhaLogisticaBean mbean = getMalhaLogisticaBean();
		double result = 0;
		try {
			result = mbean.searchShortestPath("SP", "A", "D3", 10D, 2.5);
		} catch (Throwable e) {
			throw e;
		}
		 
		
		System.out.println("Test.testBuscaSolucao()");


	}	
		
}
