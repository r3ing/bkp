package tools.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import tools.utils.Util;

/**
 * Clase XMLConfig, aquim fazemos a gestão do arquivo de configuração XML, para
 * a gestão dos arquivos XML usamos a libreria JAXB, ela da soporte aos arquivos
 * XML
 * 
 * @author Julio Cesar
 * @version 1.0
 */

public class XMLConfig { 

	private final static String ENDERECO_XML_CONFIG = "c:/EptusAM/";
	private final static String NOME_XML_CONFIG = "conf.xml";

	/**
	 * Metodo para Cadastrar nova configurção de server
	 * 
	 * @param idServer
	 *            Vai ser gerador incremental
	 * @param nomeConn
	 *            Nome da conexão
	 * @param sgbd 
	 * 			  Tipo de Sistema Gestor de Bancos de Dados           
	 * @param host
	 *            IP do Server ejemplo 190.0.0.1 ou Localhost
	 * @param port
	 *            Porta da conexão
	 * @param nomeBD
	 *            Nome da Base de Dados
	 * @param sig
	 * @param usuario
	 *            Nome do usuario do Banco de Dados
	 * @param senha
	 *            Senha do Banco de Dados
	 * @param connDefault
	 * @param codEmp
	 *            Codigo da Empresa da instalação
	 * @throws Exception 
	 * @throws JAXBException 
	 */
	public static void createNewServers(int idServer, String nomeConn, String sgbd, String host, int port, String nomeBD, String sig,
			String usuario, String senha, boolean connDefault, int codEmp) throws JAXBException, Exception {

		ArrayList<Server> dadosXML = getListServers();
		Server server = null;
		Servers servers = null;
		ArrayList<Server> dados = new ArrayList<Server>();

		if (dadosXML != null) {
			if (idServer > highID().getHighID()) {
				if (!connDefault) {
					for (int i = 0; i < dadosXML.toArray().length; i++) {
						server = new Server(dadosXML.get(i).getIdServer(), dadosXML.get(i).isServerDefault(),
								dadosXML.get(i).getNome(),dadosXML.get(i).getSgbd(), dadosXML.get(i).getHost(), dadosXML.get(i).getPort(),
								dadosXML.get(i).getBd(), dadosXML.get(i).getUser(), dadosXML.get(i).getPsw(),
								dadosXML.get(i).getEmpresaDefault());

						dados.add(server);
					}
				} else {
					for (int i = 0; i < dadosXML.toArray().length; i++) {
						server = new Server(dadosXML.get(i).getIdServer(), false, dadosXML.get(i).getNome(), dadosXML.get(i).getSgbd(),
								dadosXML.get(i).getHost(), dadosXML.get(i).getPort(), dadosXML.get(i).getBd(),
								dadosXML.get(i).getUser(), dadosXML.get(i).getPsw(),
								dadosXML.get(i).getEmpresaDefault());

						dados.add(server);
					}
				}

				Server server1 = new Server(idServer, connDefault, nomeConn, sgbd, host, port, nomeBD, usuario, senha,
						codEmp);

				dados.add(server1);
				//
				servers = new Servers();
				if (highID() != null)
					servers.setHighID(highID().getHighID() + 1);
				else
					servers.setHighID(1);
				servers.setServer(dados);

			} else {// se o idServer é menor o que o highID do arquivo então
					// Atualição
				int highID = highID().getHighID();
				for (int i = 0; i < dadosXML.toArray().length; i++) {
					// server = new Server();
					if (dadosXML.get(i).getIdServer() == idServer) {
						server = new Server(idServer, connDefault, nomeConn, sgbd, host, port, nomeBD, usuario, senha,
								codEmp);

					} else {
						if (!connDefault) {
							server = new Server(dadosXML.get(i).getIdServer(), dadosXML.get(i).isServerDefault(),
									dadosXML.get(i).getNome(), dadosXML.get(i).getSgbd(), dadosXML.get(i).getHost(), dadosXML.get(i).getPort(),
									dadosXML.get(i).getBd(), dadosXML.get(i).getUser(), dadosXML.get(i).getPsw(),
									dadosXML.get(i).getEmpresaDefault());

						} else {
							server = new Server(dadosXML.get(i).getIdServer(), false, dadosXML.get(i).getNome(), dadosXML.get(i).getSgbd(),
									dadosXML.get(i).getHost(), dadosXML.get(i).getPort(), dadosXML.get(i).getBd(),
									dadosXML.get(i).getUser(), dadosXML.get(i).getPsw(),
									dadosXML.get(i).getEmpresaDefault());

						}

					}
					dados.add(server);
				}
				servers = new Servers();
				servers.setHighID(highID);
				servers.setServer(dados);
			}

		} else {// o arquivo fica vacio

			Server server1 = new Server(idServer, connDefault, nomeConn, sgbd, host, port, nomeBD, usuario, senha, codEmp);
			dados.add(server1);
			//
			servers = new Servers();
			if (highID() != null)
				servers.setHighID(highID().getHighID() + 1);
			else
				servers.setHighID(1);
			servers.setServer(dados);
		}

		try {

			JAXBContext context = JAXBContext.newInstance(Servers.class);
			Marshaller m = context.createMarshaller();
			
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			// m.marshal(servers, System.out); //Saída em consola

			Writer w = null;
			try {
				w = new FileWriter(ENDERECO_XML_CONFIG+NOME_XML_CONFIG);
				m.marshal(servers, w);
			} finally {
				try {
					w.close();
				} catch (Exception e) {
				}
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo para excluir server gravado no arquivo de configuração XML
	 * 
	 * @param idServer
	 *            ID da configuração
	 * @throws Exception 
	 * @throws JAXBException 
	 */
	public static void excluirServer(int idServer) {
		boolean alterado = false;
		Servers servers = null;
		Util ut = new Util();
		ArrayList<Server> dadosXML;
		try {
			dadosXML = getListServers();
			if (dadosXML != null) {
				for (int i = 0; i < dadosXML.toArray().length; i++) {
					if (dadosXML.get(i).getIdServer() == idServer) {
						if (!dadosXML.get(i).isServerDefault()) {
							dadosXML.remove(i);
							alterado = true;
						} else {

							Util.alertWarn(
									"Sorry, this connection is SERVERDEFAULT, you should changed to follow this action!");
							break;
						}

					}
				}

				if (alterado) {
					servers = new Servers();
					servers.setHighID(highID().getHighID());
					servers.setServer(dadosXML);

					try {
						JAXBContext context = JAXBContext.newInstance(Servers.class);
						Marshaller marshaller = context.createMarshaller();
						marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
						// marshaller.marshal(servers, System.out);
						Writer w = null;
						try {
							try {
								w = new FileWriter(ENDERECO_XML_CONFIG+NOME_XML_CONFIG);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							marshaller.marshal(servers, w);
						} finally {
							try {
								w.close();
							} catch (Exception e) {
//								System.out.println("asdsadasderrorrr!");
							}
						}

					} catch (JAXBException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

//					Util.alertInf("Se ha eliminado datos de una conexion");

				}

			}

		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		
	}

	/**
	 * Metodo para saber o ultimo ID
	 * 
	 * @return Servers com o ultimo ID
	 */
	public static Servers highID() {
		Servers servers = null;
		boolean control = false;
		try {
			JAXBContext context2 = JAXBContext.newInstance(Servers.class);
			Unmarshaller um = context2.createUnmarshaller();
			servers = (Servers) um.unmarshal(new File(ENDERECO_XML_CONFIG+NOME_XML_CONFIG));

		} catch (JAXBException e) {

			control = true;
		}

		if (control)
			return null;
		else
			return servers;
	}
	
	public static File arquivoConfExist(){

		File file = new File(ENDERECO_XML_CONFIG+NOME_XML_CONFIG);
		 if(!file.exists()){
			try {
				if(!file.createNewFile()){
					file = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 return file;
	}

	/**
	 * Metodo retorna lista de configurações de servers no arquivo de
	 * Configuração
	 * 
	 * @return lista de configurações de servers
	 * @throws IOException 
	 */
	public static ArrayList<Server> getListServers() throws JAXBException {
		Servers servers = null;
		boolean control = false;
		try {			
			
			if(arquivoConfExist() != null){
				JAXBContext context2 = JAXBContext.newInstance(Servers.class);
				Unmarshaller um = context2.createUnmarshaller();
				servers = (Servers) um.unmarshal(arquivoConfExist());
			}

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();			
			control = true;
		}

		if (control)
			return null;
		else
//			return servers != null ? servers.getServer() : null;
			return servers.getServer() ;
	}

	/**
	 * Metodo retorna nome da conexão por default
	 * 
	 * @return Nome da conexão default
	 */
	public static String nomeConexaoDefault() {
		Servers servers = null;
		String nomeConexaoDefault = null;
		try {
			JAXBContext context2 = JAXBContext.newInstance(Servers.class);
			Unmarshaller um = context2.createUnmarshaller();
			servers = (Servers) um.unmarshal(new File(ENDERECO_XML_CONFIG+NOME_XML_CONFIG));

		} catch (JAXBException e) {
			System.out.println(e.getErrorCode());

		}

		if (servers != null) {
			for (int i = 0; i < servers.getServer().size(); i++) {
				if (servers.getServer().get(i).isServerDefault()) {
					nomeConexaoDefault = servers.getServer().get(i).getNome();
					break;
				}
			}
		}

		return nomeConexaoDefault;
	}

}
