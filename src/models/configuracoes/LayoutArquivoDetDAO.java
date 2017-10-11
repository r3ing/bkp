package models.configuracoes;  

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.axis2.databinding.types.soapencoding.DateTime;

import application.DadosGlobais;
import connect.ConnectionHib;
import interfaces.InterfaceDAO;
import javafx.scene.control.TableView;
import models.configuracoes.Auditoria;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.TabelasModel;
import tools.utils.Util;


public class LayoutArquivoDetDAO implements InterfaceDAO
{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto)
	{

		LayoutArquivoDet larquivoDet = (LayoutArquivoDet) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		
		try 
		{
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			
			em.createQuery("UPDATE Sequencial s SET s.cnfLayoutArquivos = s.cnfLayoutArquivos+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cnfLayoutArquivos") + 1;
			
			larquivoDet.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			//larquivoDet.setCodLayout(codigo);
			larquivoDet.setUtctag(utctag);
			//larquivoDet.setCheckDelete(Util.checkDeleteCreate());
			larquivoDet.setFlagAtivo(1);
			larquivoDet.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			larquivoDet.setLastMovto(ts_now);
			
			//em.createNativeQuery("INSERT INTO layout_arquivo_cab (`LAST_MOVTO`, `LAST_CODUSER`, `CHECK_DELETE`, `UTCTAG`, `FLAG_ATIVO`, `CODEMP`, `CODIGO`, `DESCRICAO`, `TABELA`, `TIPO_ARQUIVO_DADOS`, `FLAG_TIPO_OPERACAO`, `SEPARADOR`, `COMANDO_SQL`)"
			//+ "VALUES(12/02/2017, 1, 1, 1, 1, 1, 1, '1', 'TABELA', 1, 1, ';', 'SELECT * FROM tb');").executeUpdate();	

			
			/*em.createNativeQuery("INSERT INTO layout_arquivo_cab (CODEMP, COMANDO_SQL, descricao, FLAG_ATIVO, FLAG_TIPO_OPERACAO, LAST_CODUSER, LAST_MOVTO, SEPARADOR, TABELA, TIPO_ARQUIVO_DADOS, utctag, CHECK_DELETE, CODIGO)"
					+ " VALUES(:CODEMP, :COMANDO_SQL, :descricao, :FLAG_ATIVO, :FLAG_TIPO_OPERACAO, :LAST_CODUSER, :LAST_MOVTO, :SEPARADOR, :TABELA, :TIPO_ARQUIVO_DADOS, :utctag, :CHECK_DELETE, :CODIGO)")
			.setParameter("CODEMP", larquivoCab.getCodemp())
			.setParameter("COMANDO_SQL", larquivoCab.getComandoSql())
			.setParameter("descricao", larquivoCab.getDescricao())
			.setParameter("FLAG_ATIVO", larquivoCab.getFlagAtivo())
			.setParameter("FLAG_TIPO_OPERACAO", larquivoCab.getFlagTipoOperacao())
			.setParameter("LAST_CODUSER", larquivoCab.getLastCoduser())
			.setParameter("LAST_MOVTO", larquivoCab.getLastMovto())
			.setParameter("SEPARADOR", larquivoCab.getSeparador())
			.setParameter("TABELA", larquivoCab.getTabela())
			.setParameter("TIPO_ARQUIVO_DADOS", larquivoCab.getTipoArquivoDados())
			.setParameter("utctag", larquivoCab.getUtctag())
			.setParameter("CHECK_DELETE", larquivoCab.getCheckDelete())
			.setParameter("CODIGO", larquivoCab.getCodigo())			
			.executeUpdate();*/	
			
			em.persist(larquivoDet);
			
			em.getTransaction().commit();
			
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			
			logRet.setMsg(EnumLogRetornoStatus.SUCESSO.name());
			
			logRet.setLastRecord(codigo);
			
			logRet.setObjeto(larquivoDet);
			
		}
		catch (Exception e) 
		{
			em.getTransaction().rollback();			
			throw e;
		}
		finally 
		{			
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
	public List<LayoutArquivoDet> getList(List<Integer> flagAtivo) 
	{
		List<LayoutArquivoDet> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("LayoutArquivoDetAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
					.getResultList();

		}
		catch (Exception e) {
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

			List<LayoutArquivoCab> list = em.createNamedQuery("larquivoDetLast")
					.setMaxResults(1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
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
	public LogRetorno getById(Integer codigo) 
	{
		
		LogRetorno logRet = new LogRetorno();

		try
		{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<LayoutArquivoCab> list = em.createNamedQuery("larquivoDetById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
					.getResultList();

			if (!list.isEmpty())
			{
				if(list.size() > 1)
				{
					logRet.setStatus(EnumLogRetornoStatus.REGDUPLICADO);
					logRet.setObjeto(list.get(0));
					logRet.setMsg(DadosGlobais.resourceBundle.getString("errorDuplicateRecord"));
				}
				else if(list.size() == 1)
				{
					logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
					logRet.setObjeto(list.get(0));
				}

			}else
			{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg(DadosGlobais.resourceBundle.getString("errorSelect"));
			}
		}
		catch (Exception e)
		{
			throw e;
		
		}finally
		{
			em.close();
		}
		return logRet;
	}

	public LogRetorno update(Object objeto){
		
		LayoutArquivoDet larquivoDet = (LayoutArquivoDet) objeto;
	
		LogRetorno logRet = new LogRetorno();
		
		try {
			em = ConnectionHib.emf.createEntityManager();
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			em.getTransaction().begin();
			
			LayoutArquivoDetPK larquivoDetPK = new LayoutArquivoDetPK();
			//larquivoDetPK.setCodLayout(larquivoDet.getCodLayout());
			//larquivoDetPK.setCheckDelete(larquivoDet.getCheckDelete());

			LayoutArquivoDet newLarquivoDet = em.find(LayoutArquivoDet.class, larquivoDet);
			
			if(newLarquivoDet != null && newLarquivoDet.getFlagAtivo() == 1){
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			larquivoDet.setLastMovto(ts_now);
			larquivoDet.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			larquivoDet.setUtctag(utctag);
			
			em.merge(larquivoDet);
			
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
		
		LayoutArquivoDet larquivoDet = (LayoutArquivoDet) objeto;
		
		try 
		{
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE LayoutArquivoCab u "
					+ "SET u.flagAtivo = "+larquivoDet.getFlagAtivo()+","
					+ "u.lastMovto = :dtAlteracao,"
				    + "u.lastCoduser = "+DadosGlobais.usuarioLogado.getCodigo()+","
				    + "u.utctag = "+utctag+" "
					+ "WHERE u.codigo= :codigo "
					+ "AND u.checkDelete = :checkDelete "
					+ "AND u.codemp = :codemp ")
			.setParameter("dtAlteracao", ts_now)
			//.setParameter("codigo", larquivoDet.getCodLayout())
			//.setParameter("checkDelete", larquivoDet.getCheckDelete())
			.setParameter("codemp", larquivoDet.getCodemp()).executeUpdate();
			
			Integer tipoOperacao = null;
			String valorAnterior = null;
			String valorNovo = null;
			if (larquivoDet.getFlagAtivo() == 1) 
			{
				tipoOperacao = EnumTipoOpeAuditoria.REATIVACAO.idTipoOpe;//Reativação
				valorAnterior = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logExclusao").toUpperCase();//Inativo
				valorNovo = DadosGlobais.resourceBundle.getString("controllers.configuracoes.AuditoriaController.logAtivacao").toUpperCase();//Ativo
			} 
			else 
			{
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
			//auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadGrupo").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(ts_now));
			auditoria.setValorAnterior(valorAnterior);
			auditoria.setValorNovo(valorNovo);
			//auditoria.setHistorico(larquivoDet.getCodLayout() + "-" + larquivoDet.getDescColuna());
			//auditoria.setDocCodigo(String.valueOf(larquivoDet.getCodLayout()));
	
			em.persist(auditoria);
			em.getTransaction().commit();
			
		} 
		catch (Exception e) 
		{
			em.getTransaction().rollback();
			throw e;
		} 
		finally 
		{
			em.close();
		}

	}

	@SuppressWarnings("unchecked")
	public List<LayoutArquivoDet> filterByColumn (String column, String filter, int action, List<Integer> ativo) {
		 					
		List<LayoutArquivoDet> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM LayoutArquivoDet d WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM LayoutArquivoDet d WHERE d." + column
									+ "= :filter AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM LayoutArquivoDet d WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)
						.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.LAYOUT_ARQUIVO_CAB))
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
