package connect;

import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.HibernateException;
import application.DadosGlobais;
import tools.utils.LogRetorno;
import tools.xml.Server;
import tools.xml.XMLConfig;

public class ConnectionHib {

	public static EntityManagerFactory emf;
	public static String BD;
	public static String USER;
	public static String PASSWORD;
	public static String HOST;
	public static int PORT;
	public static String URL;
	public static String SERVER;
	public static boolean STATUS = false;
	public static String SGBD;
	public static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static String MYSQL_DIALECT = "org.hibernate.dialect.MySQLDialect";
	public static String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	public static String ORACLE_DIALECT = "org.hibernate.dialect.OracleDialect";
	
//	public static LogRetorno logRet = new LogRetorno();

	public static boolean cargarDadosXML() throws Exception{
		ArrayList<Server> xmlConf = XMLConfig.getListServers();
		boolean succes = false;
		
		if (xmlConf != null) {
			
			for (int i = 0; i < xmlConf.toArray().length; i++) {
				if (xmlConf.get(i).isServerDefault()) {
					System.out.println(xmlConf.get(i).getBd());
					BD = xmlConf.get(i).getBd();
					HOST = xmlConf.get(i).getHost();
					System.out.println(HOST);
					PORT = xmlConf.get(i).getPort();
					URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + BD;
					USER = xmlConf.get(i).getUser();
					PASSWORD = xmlConf.get(i).getPsw();
					SERVER = xmlConf.get(i).getNome();
					SGBD = xmlConf.get(i).getSgbd();
					DadosGlobais.codEmpDefault = xmlConf.get(i).getEmpresaDefault();
					DadosGlobais.Server = HOST;
				}
			}
			
			succes = true;

		}
		return succes;
	}
	
	public EntityManagerFactory getEntityManagerFactory()
	{
		return emf;		
	}

	public static EntityManagerFactory createConnection(){
		try {

//			if (emf == null) {	
//				try {
//					cargarDadosXML();
//				} catch (Exception e) { 
//					// TODO: handle exception
//				}
//				
//			} 
		
				HashMap<String, String> persistenceProperties = new HashMap<String, String>();
//				System.out.println(HOST);
				persistenceProperties.put("hibernate.connection.url",
						(SGBD.equals("MySQL")) ? "jdbc:mysql://" + HOST + ":" + PORT + "/" + BD
								: "jdbc:oracle:thin:@" + HOST + ":" + PORT + ":" + BD);
				persistenceProperties.put("hibernate.connection.driver_class",
						(SGBD.equals("MySQL")) ? MYSQL_DRIVER : ORACLE_DRIVER);
				persistenceProperties.put("hibernate.connection.password", PASSWORD);
				persistenceProperties.put("hibernate.connection.username", USER);
				persistenceProperties.put("hibernate.dialect", (SGBD.equals("MySQL")) ? MYSQL_DIALECT : ORACLE_DIALECT);
				
				emf = Persistence.createEntityManagerFactory("ConnectionData", persistenceProperties);
				
				if (emf != null) {
					STATUS = true;					
				}

			
		} catch (Exception e) {
			// TODO: handle exception	
			e.printStackTrace();
//			logRet.setStatus(0);
//			logRet.setMsg(e.toString());
			//Util.alertError("Erro ao conetar ao servidor - Conn 001");
//			System.out.println("erro98989");
			e.printStackTrace();
		}
		return emf;
	}

	public static boolean testarConnection(String sgbd, String host, int port, String bd, String user, String pswd) {
		boolean teste = false;
		HashMap<String, String> persistenceProperties = new HashMap<String, String>();
		persistenceProperties.put("hibernate.connection.url", (sgbd.equals("MySQL"))
				? "jdbc:mysql://" + host + ":" + port + "/" + bd : "jdbc:oracle:thin:@" + host + ":" + port + ":" + bd);
		persistenceProperties.put("hibernate.connection.driver_class",
				(sgbd.equals("MySQL")) ? MYSQL_DRIVER : ORACLE_DRIVER);
		persistenceProperties.put("hibernate.connection.password", pswd);
		persistenceProperties.put("hibernate.connection.username", user);
		persistenceProperties.put("hibernate.dialect", (sgbd.equals("MySQL")) ? MYSQL_DIALECT : ORACLE_DIALECT);

		EntityManagerFactory managerFactory = null;

		try {
			managerFactory = Persistence.createEntityManagerFactory("ConnectionData", persistenceProperties);

			if (managerFactory != null) {
				teste = true;
			}
		} catch (HibernateException e) {
//			e.printStackTrace();
//			System.out.println("Erro");

		} finally {
			managerFactory.close();
		}

		return teste;
	}

	public static void closeConnection() {
		if (emf != null) {
			emf.close();
		}

	}

	// public static void main(String[] args) {
	// createConnection();
	// DepartamentoHibDAO dep = new DepartamentoHibDAO();
	//// System.out.println(dep.inserir("Nueewgtwtwwtvo", 1));//arreglar codigo
	// usuario, ahora esta por defecto en uno
	//// List<String> parameter = Arrays.asList("1");
	//// for(Departamento depart : dep.getListDepartament(parameter)){
	//// System.out.println(depart.toString());
	//// }
	////// DepartamentoHibDAO depar = new DepartamentoHibDAO();
	////// depar.excluir(35,1,11618,1);
	//// emf.close();
	////// SequencialHibDAO seq = new SequencialHibDAO();
	////// List<Sequencial> listSeq = seq.getSequencial(Dados.empresaDefault);
	////// for(Sequencial sq : listSeq){
	////// System.out.println(sq.toString());
	////// }
	////// emf.close();
	//////
	////// DepartamentoHibDAO dep = new DepartamentoHibDAO();
	////// System.out.println(dep.inserir("probando hibernate2", 1));//arreglar
	// codigo usuario, ahora esta por defecto en uno
	////// SequencialHibDAO seq1 = new SequencialHibDAO();
	////// List<Sequencial> listSeq1 = seq.getSequencial(1);
	////// for(Sequencial sq : listSeq){
	////// System.out.println(sq.toString());
	////// }
	//////// em.close();
	////// emf.close();
	//// // em = emf.createEntityManager();
	//// // imprimir();
	// }

}
