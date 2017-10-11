package models.compras; 

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import application.DadosGlobais;
import connect.ConnectionHib;
import interfaces.InterfaceDAO;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class ItemCodBarDAO{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto){

		ItemCodBar item = (ItemCodBar) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			
			em.createQuery("UPDATE Sequencial s SET s.cpItemCodBar = s.cpItemCodBar+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cpItemCodBar") + 1;
			
			item.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			item.setCodigo(codigo);
			item.setUtctag(utctag);
			item.setCheckDelete(Util.checkDeleteCreate());
			item.setFlagAtivo(1);
			item.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
	        item.setLastMovto(ts_now);
			
	        //item.setCodCest(111);
	        //item.setCodDepartamento(4);
	        //item.setCodFabricante(5445);
	        //item.setCodFamiliaPreco(888);
	        //item.setCodLcservico("44");
	        //item.setCodMoeda(4);
	        //item.setCodNcm("789");
	        //item.setCodSubgrupo(5);
	        //item.setCodUnidade(5665);
	        //item.setCodUnidadeEmb(66);
	        //item.setDescontoMax(BigDecimal.valueOf(20.0));
	        //item.setFatorConversao(BigDecimal.valueOf(5.0));
	        //item.setNumFrabricate("");
	        //item.setNumOriginal("");
	        //item.setQtdEmbAtacado(BigDecimal.valueOf(5.0));
	        //item.setQtdEmbCompra(BigDecimal.valueOf(5.0));
	        //item.setQtdEmbVenda(BigDecimal.valueOf(5.0));
	        //item.setQtdLitros(BigDecimal.valueOf(5.0));
	        //item.setQtdMedidaM3(BigDecimal.valueOf(5.0));
	        //item.setQtdPallet(BigDecimal.valueOf(5.0));
	        //item.setQtdPesoBruto(BigDecimal.valueOf(5.0));
	        //item.setQtdPesoLiquido(BigDecimal.valueOf(5.0));
			
	        
	        ItemCodBarValor a = new ItemCodBarValor();
			a.setCodemp(item.getCodemp());
			a.setFlagAtivo(1);
			a.setLastMovto(ts_now);
			a.setUtctag(utctag);
			a.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			a.setCodTributacao(item.getCodigo()+item.getCodemp());
			a.setItemCodBar(item);
			
			ItemCodBarCodBar cdb = new ItemCodBarCodBar();
			cdb.setCodemp(item.getCodemp());
			cdb.setFlagAtivo(1);
			cdb.setCodBarras(item.getCodigo().toString());
			cdb.setLastMovto(ts_now);
			cdb.setUtctag(utctag);
			cdb.setFlagCodbarPrincipal(0);
			cdb.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			cdb.setItemCodBar(item);
			Set<ItemCodBarCodBar> itemCodbars = new HashSet<ItemCodBarCodBar>();
			itemCodbars.add(cdb);

			
			item.setItemCodBarCodbars(itemCodbars);
			
			item.setItemCodBarValors(Arrays.asList(a));		

			em.persist(item);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(codigo);
			
			logRet.setObjeto(item);
			
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
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
	public List<ItemCodBar> getList(Integer codItem) {
		List<ItemCodBar> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("CodBarrasItem")
					.setParameter("codItem", codItem)
					.getResultList();
		
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;	
		
		} finally {
			em.close();
		}

		return list;
	}

	
	@SuppressWarnings("unchecked")
	public LogRetorno getLast(){
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<ItemCodBar> list = em.createNamedQuery("ItemCodBarLast")
					.setMaxResults(1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();

			if (!list.isEmpty()){
				if(list.size() > 1){
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				}
				else if(list.size() == 1){
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			}else{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		}
		catch (Exception e){
			throw e;
		
		}finally{
			em.close();
		}
		return logRet;
	}

	/**
	 * Metodo para buscar departamento pelo CODIGO
	 * 
	 * @param codigo
	 * @return departamento buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Integer codigo) {
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<ItemCodBar> list = em.createNamedQuery("ItemCodBarById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();
			
			
			if (!list.isEmpty()){
				if(list.size() > 1){
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				}
				else if(list.size() == 1){
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			}else{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		}
		catch (Exception e){
			throw e;
		
		}finally{
			em.close();
		}
		return logRet;
	}

	public LogRetorno update(Object objeto){
		
		ItemCodBar item = (ItemCodBar) objeto;
	
		LogRetorno logRet = new LogRetorno();
		
		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			ItemCodBarPK itemPK = new ItemCodBarPK();
			itemPK.setCodigo(item.getCodigo());
			//itemPK.setCodemp(item.getCodemp());
			itemPK.setCheckDelete(item.getCheckDelete());

		    ItemCodBar newItemCodBar = em.find(ItemCodBar.class, itemPK);
			
			if(newItemCodBar != null && newItemCodBar.getFlagAtivo() == 1){
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			item.setLastMovto(ts_now);
			item.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			item.setUtctag(utctag);
			
			em.merge(item);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			logRet.setMsg("Sucesso");
			}else{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorUpdate"));
			}
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
		
		return logRet;
	}

	public void delete(Object objeto) {
		
		ItemCodBar item = (ItemCodBar) objeto;
		
		try {
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE ItemCodBar u "
					+ "SET u.flagAtivo = "+item.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.codigo= :codigo "
					+ "AND u.recordNo = :recordNo "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			.setParameter("codigo", item.getCodigo())
			.setParameter("recordNo", item.getRecordNo())
			.setParameter("codemp", item.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (item.getFlagAtivo() == 1) {
				tipoOperacao = EnumTipoOpeAuditoria.REATIVACAO.idTipoOpe;//Reativação
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
			} else {
				tipoOperacao = EnumTipoOpeAuditoria.EXCLUSAO.idTipoOpe;//Exclusão
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
			}
			
			//Cria auditoria
			Auditoria auditoria = new Auditoria();
			auditoria.setCheckDelete(Util.checkDeleteCreate());
			auditoria.setLastMovto(ts_now);
			auditoria.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setUtctag(utctag);
			auditoria.setFlagAtivo(1);
			auditoria.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			auditoria.setCodUsuario(DadosGlobais.usuarioLogado.getCodigo());
			auditoria.setNomeUsuario(DadosGlobais.usuarioLogado.getNome());
			auditoria.setSistema("EPTUS");
			auditoria.setTipoOperacao(tipoOperacao);
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadItemCodBar").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			auditoria.setHistorico(item.getCodigo() + "-" + item.getDescricao());
			auditoria.setDocCodigo(String.valueOf(item.getCodigo()));
	
			em.persist(auditoria);
			em.getTransaction().commit();
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<ItemCodBar> filterByColumn (String column, String filter, int action, List<Integer> ativo) {
		 					
		List<ItemCodBar> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM ItemCodBar d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM ItemCodBar d WHERE d." + column
									+ "= :filter AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM ItemCodBar d WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.id.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
						.setParameter("flag", ativo)
						.getResultList();
			}
		} catch (Exception e) {
			throw e;	
		} finally {
			em.close();
		}

		return listFilter;
	}
	
}
