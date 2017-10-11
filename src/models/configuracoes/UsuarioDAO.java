package models.configuracoes;


import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import application.DadosGlobais;
import connect.ConnectionHib;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class UsuarioDAO {

	private LocalDateTime ts_now;
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno inserir(Usuario usuario, List menu) throws Exception {

		int recordNumber = 0;
		int recordSession = 0;
		int recordDelete = 0;
		int checkDelete = 0;
		int recordNo = 0;
		int recordNoNew = 0;
		int lastCod = 0;
		int newCod = 0;
		LogRetorno logRet = new LogRetorno();
		logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
		logRet.setMsg("Inicio");
		ts_now = LocalDateTime.now();

		Date dataServer = (Date) em
				.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
				.getSingleResult();

		utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));

		em = ConnectionHib.emf.createEntityManager();

		List<Usuario> userSuporte = em.createQuery("SELECT d FROM Usuario d WHERE d.nome =:nomeUsuario")
				.setParameter("nomeUsuario", usuario.getNome()).getResultList();

		if (userSuporte.size() == 0) {

			userSuporte = em.createQuery("SELECT d FROM Usuario d WHERE d.pwd =:pwdUsuario")
					.setParameter("pwdUsuario", usuario.getPwd()).getResultList();
			if (userSuporte.size() == 0) {
				SequencialDAO seqDAO = new SequencialDAO();
				/**
				 * aqui se le pasa la empresa q este logueada en el moemento del
				 * uso del software, por defecto estoy usando uno
				 */
				Sequencial seq = seqDAO.getSequencial(DadosGlobais.codEmpDefault);
				recordNumber = seq.getRecordNumber();
				recordSession = seq.getRecordSession();
				recordDelete = seq.getRecordDelete();
				checkDelete = Integer.parseInt(String.valueOf(seq.getCodemp())
						+ String.valueOf((seq.getRecordDelete() + 1)) + String.valueOf((seq.getRecordSession() + 1)));
				recordNoNew = Integer.parseInt(String.valueOf(seq.getCodemp())
						+ String.valueOf((seq.getRecordNumber() + 1)) + String.valueOf((seq.getRecordSession() + 1)));
				recordNo = seq.getRecordNo();

				try {
					em.getTransaction().begin();

					SequencialPK seqPK = new SequencialPK();
					seqPK.setCodemp(DadosGlobais.codEmpDefault);
					seqPK.setCheckDelete(DadosGlobais.empresaLogada.getCheckDelete());

					Sequencial sequencial = em.find(Sequencial.class, seqPK);
					lastCod = sequencial.getCnfUsuario();

					sequencial.setRecordNumber(recordNumber + 1);
					sequencial.setRecordSession(recordSession + 1);
					sequencial.setRecordDelete(recordDelete + 1);
					sequencial.setCnfUsuario(lastCod + 1);
					sequencial.setLastMovto(ts_now);
					sequencial.setUtctag(utctag);

					usuario.setCheckDelete(Util.checkDeleteCreate());

					usuario.setCodemp(DadosGlobais.codEmpDefault);
					usuario.setRecordNo(recordNoNew);
					usuario.setCodigo(lastCod + 1);
					usuario.setUtctag(utctag);
					usuario.setFlagAtivo(1);
					usuario.setLastCoduser(DadosGlobais.idUsuario);
					usuario.setLastMovto(ts_now);

					// boolean nivAcessosucesso =
					// NivelAcessoDAO.class.newInstance().gerarDados(lastCod +
					// 1,
					// DadosGlobais.codEmpDefault, menu, checkDelete,
					// false );

					// if (nivAcessosucesso) {
					//
					// em.persist(usuario);
					//
					// em.getTransaction().commit();
					//
					// logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					//
					// logRet.setMsg("Sucesso");
					//
					// logRet.setLastRecord(lastCod + 1);
					//
					// }

				} catch (Exception e) {
					em.getTransaction().rollback();
					logRet.setStatus(EnumLogRetornoStatus.ERRO);
					logRet.setMsg("Erro ao Gravar Usuário");
				} finally {
					em.close();
				}
			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg("Senha Inválida!");
			}
		} else {
			logRet.setStatus(EnumLogRetornoStatus.ERRO);
			logRet.setMsg("Nome de Usuário já está em uso!");
		}

		return logRet;
	}

	@SuppressWarnings("unchecked")
	public LogRetorno loginUser(Empresa empresa, String pwdMD5) {

		em = ConnectionHib.emf.createEntityManager();
		List<Usuario> usuarioLogins = null;
		LogRetorno logRetorno = new LogRetorno();
		logRetorno.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
		logRetorno.setMsg("Inicio Login");

		try {
			try {

				Date dataServer = (Date) em.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
						.getSingleResult();

				ts_now = Util.dateToLocalDateTime(dataServer);

				usuarioLogins = em.createNamedQuery("UsuarioLogin").setParameter("pwd", pwdMD5).getResultList();

			} catch (HibernateException e) {

				logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
				logRetorno.setMsg("Exception Login - Erro ao Consultar Usuário");

			}

			if (usuarioLogins.size() > 0) {

				Usuario usuarioLogin = usuarioLogins.get(0);

				if (usuarioLogin.getCodEmpDispLogin().contains(empresa.getCodigo().toString())) {

					// try{
					// //DadosGlobais.listNivelAcesso =
					// usuarioLogin.getNiveisAcesso();
					// //DadosGlobais.listNivelAcesso =
					// NivelAcessoDAO.class.newInstance().getListPermisao(usuarioLogin);
					// //System.out.println(DadosGlobais.listNivelAcesso);
					//
					// }catch(Exception e){
					// e.printStackTrace();
					// logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
					// logRetorno.setMsg("Exception Login - Erro ao Consultar os
					// Níveis de Acesso do Usuário \n"+e);
					//
					// }
					// System.out.println(222222);
					if (usuarioLogin.getNiveisAcesso().size() > 0) {

						try {

							DadosGlobais.listCodempCompartilha = CompartilhamentoDAO.class.newInstance()
									.getCompartilhamentosById(empresa.getCodigo(), empresa.getCheckDelete());

						} catch (Exception e) {
							e.printStackTrace();
							logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
							logRetorno
									.setMsg("Exception Login - Erro ao buscar os compartilhamentos da empresa \n" + e);

						}

						if (DadosGlobais.listCodempCompartilha.size() > 0) {
							try {
								DadosGlobais.configEmpLogada = null;
								// System.out.println(empresa.get);
								empresa.getConfig();

							} catch (Exception e) {
								e.printStackTrace();
								logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
								logRetorno.setMsg("Exception Login - Erro ao Buscar a Configuração " + e);
							}
							if (empresa.getConfig() != null) {

								DadosGlobais.usuarioLogado = usuarioLogin;
								DadosGlobais.empresa = empresa.getNomeFantasia();
								DadosGlobais.codEmpDefault = empresa.getCodigo();
								DadosGlobais.idUsuario = usuarioLogin.getCodigo();
								DadosGlobais.usuario = usuarioLogin.getNome();
								DadosGlobais.idioma = usuarioLogin.getIdioma();
								DadosGlobais.pais = (usuarioLogin.getIdioma().trim().equals("PT") ? "BR"
										: usuarioLogin.getIdioma().equals("EN") ? "US" : "ES");
								DadosGlobais.sistema = empresa.getSistema();
								DadosGlobais.empresaLogada = empresa;

								DadosGlobais.dataMovtoSistema = ts_now;

								DadosGlobais.traducao();

								logRetorno.setStatus(EnumLogRetornoStatus.SUCESSO);
								logRetorno.setMsg("Login Efetuado Com Sucesso!");

							} else {

								logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
								logRetorno.setMsg("Erro Login \nConfiguração da Empresa não encontrada!");
							}

						} else {
							logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
							logRetorno.setMsg("Erro Login \nCompartilhamentos da Empresa não encontrados!");
						}

					} else {
						logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
						logRetorno.setMsg("Erro Login \nNivel Acesso Não Encontrado");
					}

				} else {

					logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
					logRetorno.setMsg("Usuário não tem permissão para acessar a empresa:\n" + "[" + empresa.getCodigo()
							+ "] " + empresa.getNomeFantasia());

				}
			} else {

				logRetorno.setStatus(EnumLogRetornoStatus.ERRO);
				logRetorno.setMsg("Usuário não encontrado. \nVerifique a Senha Digitada!");
			}

		} finally {
			em.close();
		}
		return logRetorno;

	}

	public Usuario getUserById(int idUsuario, int codemp) {
		Usuario usuario = null;
		em = ConnectionHib.emf.createEntityManager();

		List<Usuario> listUser = em
				.createQuery("SELECT us FROM Usuario us WHERE us.id.codigo = :codigo AND us.id.codemp = :codEmp")
				.setParameter("codigo", idUsuario).setParameter("codEmp", codemp).getResultList();
		if (!listUser.isEmpty()) {
			// System.out.println("usuario encontrado");
			usuario = listUser.get(0);
		}

		em.close();
		return usuario;
	}

	public Usuario getUserAccessById(Usuario user) {
		Usuario usuario = null;
		em = ConnectionHib.emf.createEntityManager();

		List<Usuario> listUser = em
				.createQuery(
						"SELECT us FROM Usuario us LEFT JOIN FETCH us.niveisAcesso WHERE us = :user AND us.flagAtivo = 1")
				.setParameter("user", user).getResultList();
		if (!listUser.isEmpty()) {
			// System.out.println("usuario encontrado");
			usuario = listUser.get(0);
		}

		em.close();
		return usuario;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Metodo para listar todos os usuários, dependendo do valor do flag_Ativo
	 * 
	 * @param flagAtivo
	 * @return
	 */
	public List<Usuario> getListUsuario(List<Integer> flagAtivo) {
		try {

			em = ConnectionHib.emf.createEntityManager();

			List<Usuario> listUsuario = em.createNamedQuery("UsuarioAll").setParameter("flag", flagAtivo)
					.setParameter("codemp", DadosGlobais.codEmpDefault).getResultList();

			return listUsuario;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			em.close();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filterByColumn(String column, String filter, int action, List<Integer> ativo) {
		List<Usuario> listFilter = null;
		try {

			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM Usuario d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp = :codemp AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", DadosGlobais.codEmpDefault).setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM Usuario d WHERE d." + column
									+ "= :filter AND d.id.codemp = :codemp AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", DadosGlobais.codEmpDefault).setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM Usuario d WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp = :codemp AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", DadosGlobais.codEmpDefault).setParameter("flag", ativo).getResultList();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return listFilter;
	}

	public void excluir(int codigo, int codemp, int recordNo, int lastCoduser, int flagAtivo) throws Exception {

		int recordNumber = 0;
		int recordSession = 0;
		int recordDelete = 0;
		int checkDelete = 0;
		int recordNum = 0;
		int recordNoNew = 0;
		Util util = new Util();
		ts_now = LocalDateTime.now();

		em = ConnectionHib.emf.createEntityManager();

		Date dataServer = (Date) em
				.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
				.getSingleResult();

		utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));

		SequencialDAO seqDAO = new SequencialDAO();
		Sequencial seq = seqDAO.getSequencial(DadosGlobais.codEmpDefault);

		recordNumber = seq.getRecordNumber();
		recordSession = seq.getRecordSession();
		recordDelete = seq.getRecordDelete();
		checkDelete = Integer.parseInt(String.valueOf(seq.getCodemp()) + String.valueOf((seq.getRecordDelete() + 1))
				+ String.valueOf((seq.getRecordSession() + 1)));
		recordNoNew = Integer.parseInt(String.valueOf(seq.getCodemp()) + String.valueOf((seq.getRecordNumber() + 1))
				+ String.valueOf((seq.getRecordSession() + 1)));
		recordNum = seq.getRecordNo();

		try {

			em.getTransaction().begin();

			UsuarioPK userPK = new UsuarioPK();
			userPK.setCodigo(codigo);
			userPK.setCodemp(DadosGlobais.codEmpDefault);
			// userPK.setRecordNo(recordNo);

			Usuario user = em.find(Usuario.class, userPK);
			user.setLastMovto(ts_now);
			user.setLastCoduser(lastCoduser);
			user.setUtctag(utctag);
			user.setFlagAtivo(flagAtivo);

			// String tipoOperacao = null;
			// String valorAnterior = null;
			// String valorNovo = null;
			// if (flagAtivo == 1) {
			// tipoOperacao = "Reativação";
			// valorAnterior = "Inativo";
			// valorNovo = "Ativo";
			// } else {
			// tipoOperacao = "Exclusão";
			// valorAnterior = "Ativo";
			// valorNovo = "Inativo";
			// }

			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (flagAtivo == 1) {
				tipoOperacao = EnumTipoOpeAuditoria.REATIVACAO.idTipoOpe;// Reativação
				valorAnterior = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();// Inativo
				valorNovo = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();// Ativo
			} else {
				tipoOperacao = EnumTipoOpeAuditoria.EXCLUSAO.idTipoOpe;// Exclusão
				valorAnterior = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();// Ativo
				valorNovo = DadosGlobais.resourceBundle
						.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();// Inativo
			}

			// Cria auditoria
			Auditoria auditoria = new Auditoria();

			auditoria.setLastMovto(ts_now);
			auditoria.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setCheckDelete(DadosGlobais.empresaLogada.getCheckDelete());
			auditoria.setRecordNo(recordNoNew);
			auditoria.setUtctag(utctag);
			auditoria.setFlagAtivo(1);
			auditoria.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			auditoria.setCodUsuario(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setNomeUsuario(DadosGlobais.usuarioLogado.getNome());
			auditoria.setSistema("EPTUS");
			auditoria.setTipoOperacao(tipoOperacao);
			auditoria.setTipoRegistro("USUARIO");
			auditoria.setDtMovto(util.geraDataMovto());
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(user.getCodigo() + "-" + user.getNome());
			auditoria.setDocCodigo(String.valueOf(user.getCodigo()));

			// Update Sequencial
			SequencialPK seqPK = new SequencialPK();
			seqPK.setCodemp(DadosGlobais.codEmpDefault);
			seqPK.setCheckDelete(DadosGlobais.empresaLogada.getCheckDelete());

			Sequencial sequencial = em.find(Sequencial.class, seqPK);
			sequencial.setLastMovto(ts_now);
			sequencial.setRecordNumber(recordNumber + 1);
			sequencial.setRecordSession(recordSession + 1);
			sequencial.setRecordDelete(recordDelete + 1);
			sequencial.setUtctag(utctag);

			em.persist(auditoria);
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	public Usuario getLastUsuario() {
		em = ConnectionHib.emf.createEntityManager();

		Usuario lastUser = null;

		List<Usuario> listUser = null;

		try {
			listUser = em.createNamedQuery("LastUsuario").setMaxResults(1)
					.setParameter("codemp", DadosGlobais.codEmpDefault).getResultList();

			lastUser = listUser.get(0);

		} catch (Exception e) {
			Util.alertError("Error ao consultar Usuario. USER - 001");
			// e.printStackTrace();
		} finally {
			em.close();
		}

		return lastUser;
	}

	public LogRetorno update(Usuario usuario) {
		LogRetorno logRet = new LogRetorno();

		ts_now = LocalDateTime.now();

		em = ConnectionHib.emf.createEntityManager();

		Date dataServer = (Date) em
				.createNativeQuery(
						ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL")
				.getSingleResult();

		utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));

		try {

			em.getTransaction().begin();

			UsuarioPK userPK = new UsuarioPK();

			userPK.setCodigo(usuario.getCodigo());

			userPK.setCodemp(usuario.getCodemp());

			userPK.setCheckDelete(usuario.getCheckDelete());

			Usuario newUsuario = em.find(Usuario.class, userPK);

			if (newUsuario != null && newUsuario.getFlagAtivo() == 1) {

				usuario.setLastMovto(ts_now);
				usuario.setLastCoduser(DadosGlobais.idUsuario);
				usuario.setUtctag(utctag);

				if (!usuario.getPwd().equals(newUsuario.getPwd())) {
					usuario.setPwd3(newUsuario.getPwd2());
					usuario.setPwd2(newUsuario.getPwd1());
					usuario.setPwd1(newUsuario.getPwd());

				}

				em.merge(usuario);
				em.getTransaction().commit();

				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
				logRet.setMsg("Sucesso");
				
			} else {
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorUpdate"));
			}

		} catch (Exception e) {

			em.getTransaction().rollback();

		} finally {
			em.close();
		}

		return logRet;
	}
}
