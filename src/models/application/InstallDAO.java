package models.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.flywaydb.core.Flyway;


import connect.ConnectionHib;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import tools.application.Migration;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class InstallDAO {

	public static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static String MYSQL_DIALECT = "org.hibernate.dialect.MySQLDialect";
	public static String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	public static String ORACLE_DIALECT = "org.hibernate.dialect.OracleDialect";
	String tipoServidor;
	String ipServer; 
	String porta;
	String baseDados;
	String usuarioBD; 
	String pwdBD;
	LogRetorno logRet = new LogRetorno();
	public static EntityManagerFactory emf;

	
	

	public InstallDAO(){
		
	}
HashMap<String, String> persistenceProperties = new HashMap<String, String>();	
	
	EntityManagerFactory managerFactory = null;
public InstallDAO(String tipoServidor, String ipServer, String porta, String baseDados, String usuarioBD,
			String pwdBD) {
		super();
		this.tipoServidor = tipoServidor;
		this.ipServer = ipServer;
		this.porta = porta;
		this.baseDados = baseDados;
		this.usuarioBD = usuarioBD;
		this.pwdBD = pwdBD;
	}


public EntityManagerFactory connectHibernate(boolean flagBase) {
	try {
		
	if(!flagBase){
				persistenceProperties.put("hibernate.connection.url", (tipoServidor.equals("MySQL"))
				? "jdbc:mysql://" + ipServer + ":" + porta : "jdbc:oracle:thin:@" + ipServer + ":" + porta);
		
	}else{
				persistenceProperties.put("hibernate.connection.url", (tipoServidor.equals("MySQL"))
				? "jdbc:mysql://" + ipServer + ":" + porta + "/" + baseDados : "jdbc:oracle:thin:@" + ipServer + ":" + porta + ":" + baseDados);
	}
		
		persistenceProperties.put("hibernate.connection.driver_class",
				(tipoServidor.equals("MySQL")) ? MYSQL_DRIVER : ORACLE_DRIVER);
		persistenceProperties.put("hibernate.connection.password", pwdBD);
		persistenceProperties.put("hibernate.connection.username", usuarioBD);
		persistenceProperties.put("hibernate.dialect", (tipoServidor.equals("MySQL")) ? MYSQL_DIALECT : ORACLE_DIALECT);
	
	emf = Persistence.createEntityManagerFactory("ConnectionData", persistenceProperties);
		
	} catch (Exception e) {
		// TODO: handle exception
		//Util.alertError("Erro ao conetar ao servidor - Conn 001");
		emf=null;
		
	}

	return emf;
}



	public Connection connectJDBC(){

	Connection connect1 = null;
	
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
		
		} catch (ClassNotFoundException e) {
		
			System.err.println("ERROR AL REGISTRAR EL DRIVER");
		
			System.exit(0); //parar la ejecución
		
		}
		//Establecer la conexión con el servidor
		try {
		
		 connect1 = DriverManager.getConnection("jdbc:mysql://"+ipServer+":"+porta, usuarioBD, pwdBD);
		 
		} catch (SQLException e) {
		
		
		}
		
		return connect1;

	}


	//public boolean verificaServidor(String tipoServidor, String ipServer, String porta, String baseDados, String usuarioBD, String pwdBD){
	
	
	public LogRetorno verificaServidor() throws SQLException{	

		
		
		connectHibernate(false);
		if(emf != null){
			logRet.setStatus(1);
			logRet.setMsg("Servidor Localizado");
		}else{
			logRet.setStatus(0);
			logRet.setMsg("Sem Conexao Com Servidor");
		}
		return logRet;
	}
	
	
	public LogRetorno verificaBaseDados(){
	
////	persistenceProperties.put("hibernate.connection.url", (tipoServidor.equals("MySQL"))
////				? "jdbc:mysql://" + ipServer + ":" + porta + "/" + baseDados : "jdbc:oracle:thin:@" + ipServer + ":" + porta + ":" + baseDados);
////	
//		try{
//					
//			
//			managerFactory = null;
//			
//			managerFactory = Persistence.createEntityManagerFactory("ConnectionData", persistenceProperties);
//			
//			
//			
//			if(managerFactory  != null){
//							
//			logRet.setStatus(1);
//			logRet.setMsg("Banco Localizado");
//			
//			
//			}
//		
//		}catch(Exception e){
//			
//			logRet.setStatus(0);
//			logRet.setMsg("Banco não existe, Cria o Banco de Dados");	
//			
//		}
//		finally {
//			managerFactory.close();
//		}
		
		connectHibernate(true);
		if(emf != null){
			logRet.setStatus(1);
			logRet.setMsg("Servidor Localizado");	
		}else{
			logRet.setStatus(0);
			logRet.setMsg("Sem Conexao Com Servidor");
		}
		return logRet;
	}
	
	public LogRetorno criaBanco() throws SQLException{
		
		Statement stmt = null;
		try {
					
			Connection con = connectJDBC();
			
			stmt = con.createStatement();
		    
			String sql = "CREATE DATABASE "+baseDados;
			stmt.execute(sql);
			logRet.setStatus(1);
			logRet.setMsg("Banco Criado Com Sucesso");
			connectHibernate(true);
		} catch (Exception e) {
			
			logRet.setStatus(0);
			logRet.setMsg("Erro ao Criar Banco de Dados");
		}
	    
		
		return logRet;
	
}

public 	Empresa checaEmp(int codemp){
	
	
	
	Empresa emp = new Empresa();
	
	EmpresaDAO empDado = new EmpresaDAO();
	
	emp = empDado.checaEmpresa(codemp, emf);
	
	return emp;
}


public 	LogRetorno criaEmp(Empresa empresa){
	

	EmpresaDAO empDado = new EmpresaDAO();
	
	empDado.criaEmpresa(empresa, emf);
	
	return logRet;
	
}


public 	LogRetorno atualizarEmp(Empresa empresa){

	EmpresaDAO empDado = new EmpresaDAO();
	
	empDado.atualizaEmpresa(empresa, emf);
	
	return logRet;
	
}
	
public LogRetorno criaTabelas() throws SQLException{
		
		try {
			
			Migration m = new Migration();
			m.flywayCriaDataSource(ipServer, baseDados, porta, usuarioBD, pwdBD);
			m.flywayMigrate();
			
		} catch (Exception e) {
			
			logRet.setStatus(0);
			logRet.setMsg("Erro ao Criar Tabelas");
		}
	    
		
		return logRet;
	
}


public void copyDirectory(File srcDir, File dstDir)
{
    try{
        if (srcDir.isDirectory()) {
            if (!dstDir.exists()) {
                dstDir.mkdir();
            }

            String[] children = srcDir.list();
            for (int i=0; i<=children.length;i++)
            {
                copyDirectory(new File(srcDir, children[i]),
                    new File(dstDir, children[i]));
            }
        } else {
            copyFile(srcDir, dstDir);
        }
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}
public void copyFile(File s, File t)
{
    try{
          FileChannel in = (new FileInputStream(s)).getChannel();
          FileChannel out = (new FileOutputStream(t)).getChannel();
          in.transferTo(0, s.length(), out);
         
         // System.out.println( in.transferTo(0, s.length(), out));
          in.close();
          out.close();
    }
    catch(Exception e)
    {
        System.out.println(e);
    }
}

public LogRetorno criaEstruturaPastas(){
	
	File f = new File("src\\estruturaPastas\\");
    File fileOrg=null;
    File fileDest=null;
    File[] ficheros = f.listFiles();
    File[] ficheros1 =null;
    String sSubDir="";
    String sFile="";
    String sOrigen;
    String sDestino;
    
		try {
			
			
			File folder = new File("C:\\Eptus22");
			
			if (!folder.exists()){
				folder.mkdir();
			}

			try {
				for (int x = 0; x < ficheros.length; x++) {
					
					sSubDir = ficheros[x].getName();
					sOrigen = "src\\estruturaPastas\\" + sSubDir;
					sDestino = "C:\\Eptus22\\" + sSubDir;
					fileOrg = new File(sOrigen);
					fileDest = new File(sDestino);
					copyDirectory(fileOrg,fileDest);

				}

			    logRet.setStatus(1);
				logRet.setMsg("Pastas Criadas");
			} catch (Exception e) {
				// TODO: handle exception
				  logRet.setStatus(0);
					logRet.setMsg("Erro1");
			}

		} catch (Exception e) {
			// TODO: handle exception
			logRet.setStatus(0);
			logRet.setMsg("Erro1");
		}
		
	return logRet;
}
public LogRetorno atualizaTabelas() throws SQLException{
	
	
	try {
		
		//Thread.sleep(3000);
		Migration m = new Migration();
		m.flywayCriaDataSource(ipServer, baseDados, porta, usuarioBD, pwdBD);
		m.flywayMigrate();
		
	} catch (Exception e) {
		
		logRet.setStatus(0);
		logRet.setMsg("Erro ao Criar Tabelas");
	}
    
	
	return logRet;

}

}
