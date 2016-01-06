
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
public class TestWS {

	public MalhaLogisticaService getMalhaLogisticaServiceClient() {

		URL url = null;

		try {
			url = new URL("http://localhost:8080/wallmart/MalhaLogisticaServiceImpService?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 1st argument service URI, refer to wsdl document above
		// 2nd argument is service name, refer to wsdl document above
		QName qname = new QName("http://services.wallmart.com.br/", "MalhaLogisticaServiceImpService");

		Service service = Service.create(url, qname);
		MalhaLogisticaService ws = service.getPort(MalhaLogisticaService.class);

		return ws;

	}

	@org.junit.Test(expected = EJBException.class)
	public void testE_TestWS() throws Exception {

		MalhaLogisticaService service = getMalhaLogisticaServiceClient();

		Double retorno = null;
		retorno = service.searchShortestPath("SP", "A", "E", 10D, 2.5);

		System.out.println("Retorno ws:" + retorno);

	}
}
