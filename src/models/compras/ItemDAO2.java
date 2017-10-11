package models.compras; 

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import org.hibernate.HibernateException;
import application.DadosGlobais;
import connect.ConnectionHib;
import models.configuracoes.Auditoria;
import models.configuracoes.Sequencial;
import models.configuracoes.SequencialDAO;
import models.configuracoes.SequencialPK;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class ItemDAO2 {

	private LocalDateTime ts_now;
	private int utctag;
	private EntityManager em = null;
	private Util util = new Util();
	
	
	public LogRetorno inserir(Item item) throws Exception, HibernateException, SQLException{
	
		int lastCod = 0;
		LogRetorno logRet = new LogRetorno();
		logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
		ts_now = LocalDateTime.now();
		utctag = (int) (new Date().getTime() / 1000);
		
	//	for (int i = 0; i < 500; i++) {
	
		em = ConnectionHib.emf.createEntityManager();
		SequencialDAO seqDAO = new SequencialDAO();
		/**
		 * aqui se le pasa la empresa q este logueada en 

 moemento del uso del
		 * software, por defecto estoy usando uno
		 */
		

		try {
			em.getTransaction().begin();

		
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			em.createQuery("UPDATE Sequencial s SET s.cpItem = s.cpItem+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			System.out.println(888);
			utctag = (int) (dataServer.getTime() / 1000);		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cpItem");
			
//			ItemPK chavePK = new ItemPK();
			item.setCodemp(DadosGlobais.empresaLogada.getCodigo());// esta por defecto hay q arreglar
			//chavePK.setRecordNo(recordNoNew);
//			chavePK.setCodemp(1);
			System.out.println(codigo);
			item.setCodigo(codigo);
//			chavePK.setCheckDelete(checkDelete);
//			chavePK.setRecordNo(recordNo);
//			item.setId(chavePK);
			item.setLastMovto(ts_now);
//			item.setRecordNo(recordNoNew);
			item.setUtctag(utctag);
			item.setFlagAtivo(1);
			item.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			item.setCheckDelete(Util.checkDeleteCreate());
			
//			ItemValorPK ad = new ItemValorPK();
//			ad.setCodItem(sequencial.getCpItem());
//			ad.setCodemp(1);
			
			ItemValor a = new ItemValor();
			a.setCodemp(item.getCodemp());
//			a.setCodItem(item.getCodigo());
			a.setFlagAtivo(1);
			a.setLastMovto(ts_now);
			a.setUtctag(utctag);
			a.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			a.setCodTributacao(item.getCodigo()+item.getCodemp());
			a.setItem(item);
			ItemValor ab = new ItemValor();
			ab.setCodemp(2);
//			a.setCodItem(item.getCodigo());
			ab.setFlagAtivo(1);
			ab.setLastMovto(ts_now);
			ab.setUtctag(utctag);
			ab.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			ab.setCodTributacao(item.getCodigo()+ab.getCodemp());
			ab.setItem(item);

			
//			ItemCodBar cdb = new ItemCodBar();
//			cdb.setCodemp(item.getCodemp());
//			cdb.setFlagAtivo(1);
//			cdb.setCodBarras(item.getCodigo().toString());
//			cdb.setLastMovto(ts_now);
//			cdb.setUtctag(utctag);
//			cdb.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
//			
		//	cdb.setItem(item);
			
			//item.setItemCodbars();
			
			item.setItemValors(Arrays.asList(a,ab));		

			em.persist(item);			
			em.getTransaction().commit();
			
			//CONFIGURAÇÃO DO LOG
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg("Sucesso");
			
			logRet.setLastRecord(item.getCodigo());
			
			logRet.setObjeto(item);
			
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			
		} finally {
			em.close();
		}
	//	}
		return logRet;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Metodo para listar tudos os GRUPOS, dependendo do valor do
	 * flag_Ativo e compartilhamento
	 * 
	 * @param flagAtivo
	 * @return
	 */
	public List<Item> getList(List<Integer> flagAtivo) throws Exception{

		List<Item> list = null;
		
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("ItemJoinEmpAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();
//		for(int i = 1 ; i < list.size(); i++){
//			for(int j = 1 ; j < list.get(i).getItemValors().size(); j++){
//			list.get(i).getItemValors().get(j);
//		}
//			}
		}catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			em.close();
		}

		return list;

	}

	@SuppressWarnings("unchecked")
	public Item getLast()  throws Exception{
		em = ConnectionHib.emf.createEntityManager();
		Item lastEntity = null;
		List<Item> listLast = null;
		LogRetorno logRet = new LogRetorno();
		try {
			listLast = em.createNamedQuery("ItemLast")
					.setMaxResults(1)
					.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();
			
			if (listLast.size() != 0) {
				
					lastEntity = listLast.get(0);
			}
		
			
		} finally {
			em.close();
		}

		return lastEntity;
	}

	/**
	 * Metodo para buscar departamento pelo CODIGO
	 * 
	 * @param codigo
	 * @return departamento buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(int codigo)  throws Exception {
		
		LogRetorno logRet = new LogRetorno();

		try{
			try{	
				em = ConnectionHib.emf.createEntityManager();
			}catch(Exception e){

			}

			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			logRet.setMsg("Inicio Processo");

			List<Item> list = em.createNamedQuery("ItemById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();

			if (!list.isEmpty()){
				if(list.size() > 1){
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg("O código retornou mais de um resultado, verifique integridade do cadastro!");
				}
				else if(list.size() == 1){
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			}else{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg("Erro ao Cadastrar Item");
			}
		}
		finally{
			em.close();
		}
		return logRet;
	}

	public LogRetorno update(Item item) throws Exception {
		ts_now = LocalDateTime.now();
		utctag = (int) (new Date().getTime() / 1000);
		LogRetorno logRet = new LogRetorno();
		logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
		em = ConnectionHib.emf.createEntityManager();
		try {
			em.getTransaction().begin();
			
			ItemPK chavePK = new ItemPK();
			chavePK.setCodigo(item.getCodigo());
			chavePK.setCheckDelete(item.getCheckDelete());

			Item newEntity = em.find(Item.class, chavePK);
			
			newEntity.setLastMovto(ts_now);
			newEntity.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			newEntity.setUtctag(utctag);
			newEntity.setDescricao(item.getDescricao());
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			logRet.setMsg("Sucesso");
			
		} catch (Exception e) {
			em.getTransaction().rollback();
		
		} finally {
			em.close();
		}
		
		return logRet;
	}

	public void excluir(Item item) throws Exception {
		
		int recordNumber = 0;
		int recordSession = 0;
		int recordDelete = 0;
		int checkDelete = 0;
		int recordNum = 0;
		int recordNoNew = 0;

		ts_now = LocalDateTime.now();
		utctag = (int) (new Date().getTime() / 1000);
		

		em = ConnectionHib.emf.createEntityManager();
		SequencialDAO seqDAO = new SequencialDAO();
		Sequencial seq = seqDAO.getSequencial(DadosGlobais.empresaLogada.getCodigo());

		recordNumber = seq.getRecordNumber();
		recordSession = seq.getRecordSession();
		recordDelete = seq.getRecordDelete();
		checkDelete = Integer.parseInt(String.valueOf(seq.getCodemp())
				+ String.valueOf((seq.getRecordDelete() + 1)) + String.valueOf((seq.getRecordSession() + 1)));
		recordNoNew = Integer.parseInt(String.valueOf(seq.getCodemp())
				+ String.valueOf((seq.getRecordNumber() + 1)) + String.valueOf((seq.getRecordSession() + 1)));
		recordNum = seq.getRecordNo();

		try {

			em.getTransaction().begin();

			ItemPK chavePK = new ItemPK();
			chavePK.setCodigo(item.getCodigo());
			chavePK.setCheckDelete(item.getCheckDelete());

			Item newEntity = em.find(Item.class, chavePK);
			
			newEntity.setLastMovto(ts_now);
			newEntity.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			newEntity.setUtctag(utctag);
			newEntity.setFlagAtivo(item.getFlagAtivo());

			String tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (item.getFlagAtivo() == 1) {
				tipoOperacao = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.reativacao");//Reativação
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao");//Inativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao");//Ativo
			} else {
				tipoOperacao = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.exclusao");//Exclusão
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao");//Ativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao");//Inativo
			}
			
			//Cria auditoria
			Auditoria auditoria = new Auditoria();
			
			auditoria.setLastMovto(ts_now);
			auditoria.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setCheckDelete(checkDelete);
			auditoria.setRecordNo(recordNoNew);
			auditoria.setUtctag(utctag);
			auditoria.setFlagAtivo(1);
			auditoria.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			auditoria.setCodUsuario(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setNomeUsuario(DadosGlobais.usuarioLogado.getNome());
			auditoria.setSistema("EPTUS");
			auditoria.setTipoOperacao(tipoOperacao);
			auditoria.setTipoRegistro("ITEM");
			auditoria.setDtMovto(util.geraDataMovto());
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(newEntity.getCodigo() + "-" + newEntity.getDescricao());
			auditoria.setDocCodigo(String.valueOf(newEntity.getCodigo()));
	
			
			// Update Sequencial
			SequencialPK seqPK = new SequencialPK();
			seqPK.setCodemp(DadosGlobais.empresaLogada.getCodigo());
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
	public List<Item> filterByColumn (String column, String filter, int action, List<Integer> ativo) throws Exception {
		 					
		List<Item> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM Item d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
							.setParameter("flag", ativo).getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM Item d WHERE d." + column
									+ "= :filter AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS)).setParameter("flag", ativo).getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM Item d WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS)).setParameter("flag", ativo).getResultList();
			}
			
		} finally {
			em.close();
		}

		return listFilter;
	}

}
