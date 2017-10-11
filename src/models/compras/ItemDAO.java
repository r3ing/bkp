package models.compras; 

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.StyledEditorKit.ItalicAction;
import application.DadosGlobais;
import connect.ConnectionHib;
import controllers.utils.ComboBoxData.CboxData;
import interfaces.InterfaceDAO;
import models.configuracoes.Auditoria;
import models.configuracoes.DepositoEstoque;
import models.configuracoes.SequencialDAO;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.enums.EnumTipoOpeAuditoria;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class ItemDAO{

	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;

	public LogRetorno insert(Object objeto, List<ItemCodBar> lis, List<ItemValor> listValores, List<ItemFornecedor> listItemFornecedor){

		Item item = (Item) objeto;
		LogRetorno logRet = new LogRetorno();
		SequencialDAO seqDAO = new SequencialDAO();
		Util ut = new Util();
		try {
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			
			em.createQuery("UPDATE Sequencial s SET s.cpItem = s.cpItem+1 WHERE s.codemp= :codemp AND s.checkDelete = :checkDelete")
			.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
			.setParameter("checkDelete", DadosGlobais.empresaLogada.getCheckDelete())
			.executeUpdate();
			
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			Integer codigo = seqDAO.getLastInsertCodigo(DadosGlobais.empresaLogada.getCodigo(), "cpItem") + 1;
			
			item.setCodemp(DadosGlobais.empresaLogada.getCodigo());
			item.setCodigo(codigo);
			item.setUtctag(utctag);
			item.setCheckDelete(Util.checkDeleteCreate());
			item.setFlagAtivo(1);
			item.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
	        item.setLastMovto(ts_now);
			
	        for (int i = 0; i < listValores.size(); i++) {
	        	
	        	listValores.get(i).setItem(item);
	        	listValores.get(i).setCheckDelete(Util.checkDeleteCreate());
	        	listValores.get(i).setFlagAtivo(1);
	        	listValores.get(i).setLastMovto(ts_now);
	        	listValores.get(i).setUtctag(utctag);
	        	listValores.get(i).setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());

			}
	        
			
			Set<ItemCodBar> itemCodbars = new HashSet<ItemCodBar>();

			if(lis.size() > 0 ){
				
				for(int i=0; i < lis.size(); i++){
					lis.get(i).setCodemp(item.getCodemp());
					lis.get(i).setItem(item);
					lis.get(i).setCheckDelete(Util.checkDeleteCreate());
					lis.get(i).setFlagAtivo(1);
					lis.get(i).setLastCoduser(5);
					lis.get(i).setLastMovto(ts_now);
					lis.get(i).setUtctag(utctag);
					itemCodbars.add(lis.get(i));
				}
				
			}
			
			Set<ItemFornecedor> itemFornecedores = new HashSet<ItemFornecedor>();
						
			for (int j = 0; j < listItemFornecedor.size(); j++) {

				if(listItemFornecedor.get(j).getCheckDelete() == null && listItemFornecedor.get(j).getFlagAtivo().equals(1)){

					listItemFornecedor.get(j).setCodemp(item.getCodemp());
					listItemFornecedor.get(j).setItem(item);
					listItemFornecedor.get(j).setCheckDelete(Util.checkDeleteCreate());
					listItemFornecedor.get(j).setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					listItemFornecedor.get(j).setLastMovto(ts_now);
					listItemFornecedor.get(j).setUtctag(utctag);
					itemFornecedores.add(listItemFornecedor.get(j));
				
				}
			}
			
			Set<ItemEstoqueDeposito> itemEstoqueDepositos = new HashSet<ItemEstoqueDeposito>();
			
			List<DepositoEstoque> listDepositos ;
			
			listDepositos = em.createNamedQuery("DepositoEstoqueAll")
					.setParameter("flag", 1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.DEPOSITO_ESTOQUES))
					.getResultList();
			
			for (int j = 0; j < listDepositos.size(); j++) {
				
				for (int i = 0; i < listValores.size(); i++) {
					ItemEstoqueDeposito itemEstoqueDeposito = new ItemEstoqueDeposito();
					ut.initializeAttribClass(itemEstoqueDeposito);
					itemEstoqueDeposito.setCodemp(listValores.get(i).getCodemp());
					itemEstoqueDeposito.setItem(item);
					itemEstoqueDeposito.setCheckDelete(Util.checkDeleteCreate());
					itemEstoqueDeposito.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
					itemEstoqueDeposito.setLastMovto(ts_now);
					itemEstoqueDeposito.setUtctag(utctag);
					itemEstoqueDeposito.setDepositoEstoque(listDepositos.get(j));
					itemEstoqueDepositos.add(itemEstoqueDeposito);
				}
					
			}


				ItemCodBar cdb = new ItemCodBar();
				cdb.setCodemp(item.getCodemp());
				cdb.setCheckDelete(Util.checkDeleteCreate());
				cdb.setFlagAtivo(1);
				cdb.setCodBarras(item.getCodigo().toString());
				
				cdb.setQtdEmbalagem(BigDecimal.valueOf(1.00));
				cdb.setLastMovto(ts_now);
				cdb.setUtctag(utctag);
				cdb.setFlagCodbarPrincipal(lis.size() > 0 ? 0 : 1);
				cdb.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				cdb.setItem(item);
				itemCodbars.add(cdb);


				item.setItemCodBars(itemCodbars);
				item.setItemValors(listValores);
				item.setItemFornecedor(itemFornecedores);
				item.setItemEstoqueDeposito(itemEstoqueDepositos);

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
	public List<Item> getList(List<Integer> flagAtivo) {
		List<Item> list = null;
		try {	
		em = ConnectionHib.emf.createEntityManager();		
		
		list = em.createNamedQuery("ItemJoinEmpAll")
					.setParameter("flag", flagAtivo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
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

			List<Item> list = em.createNamedQuery("ItemLast")
					.setMaxResults(1)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();

			if (!list.isEmpty()){
				List<ItemCodBar> listCb = em.createNamedQuery("ItemCodBars")
						.setParameter("item", list.get(0))
						.getResultList();
				
				Set<ItemCodBar> itemCodbars = new HashSet<ItemCodBar>();
				itemCodbars.addAll(listCb);
				
				List<ItemValor> listVlr = em.createNamedQuery("ItemValores")
						.setParameter("item", list.get(0))
						.getResultList();
				
				List<ItemFornecedor> listFornecedor = em.createNamedQuery("ItemFornecedores")
						.setParameter("item", list.get(0))
						.getResultList();
				
				Set<ItemFornecedor> itemFornecedores = new HashSet<ItemFornecedor>();
				
				itemFornecedores.addAll(listFornecedor);
				
				List<ItemEstoqueDeposito> listEstoqueDeposito = em.createNamedQuery("ItemEstoqueDepositos")
						.setParameter("item", list.get(0))
						.getResultList();
				
				Set<ItemEstoqueDeposito> itemEstoqueDeposito = new HashSet<ItemEstoqueDeposito>();
				
				itemEstoqueDeposito.addAll(listEstoqueDeposito);
				
				list.get(0).setItemValors(listVlr);
				
				list.get(0).setItemCodBars(itemCodbars);
				
				list.get(0).setItemFornecedor(itemFornecedores);
				
				list.get(0).setItemEstoqueDeposito(itemEstoqueDeposito);
				
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

			List<Item> list = em.createNamedQuery("ItemById")
					.setParameter("codigo", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();
			
			if (!list.isEmpty()){
			
				//dispara as consultas dos codigos de barras e dos valores do item
				List<ItemCodBar> listCb = em.createNamedQuery("ItemCodBars")
						.setParameter("item", list.get(0))
						.getResultList();
				
				Set<ItemCodBar> itemCodbars = new HashSet<ItemCodBar>();
				itemCodbars.addAll(listCb);
				
				List<ItemValor> listVlr = em.createNamedQuery("ItemValores")
						.setParameter("item", list.get(0))
						.getResultList();
				
				List<ItemFornecedor> listFornecedor = em.createNamedQuery("ItemFornecedores")
						.setParameter("item", list.get(0))
						.getResultList();
				
				Set<ItemFornecedor> itemFornecedores = new HashSet<ItemFornecedor>();
				
				itemFornecedores.addAll(listFornecedor);
				
				List<ItemEstoqueDeposito> listEstoqueDeposito = em.createNamedQuery("ItemEstoqueDepositos")
						.setParameter("item", list.get(0))
						.getResultList();
				
				Set<ItemEstoqueDeposito> itemEstoqueDeposito = new HashSet<ItemEstoqueDeposito>();
				
				itemEstoqueDeposito.addAll(listEstoqueDeposito);
				
				
				list.get(0).setItemValors(listVlr);
				
				list.get(0).setItemCodBars(itemCodbars);
				
				list.get(0).setItemFornecedor(itemFornecedores);
				
				list.get(0).setItemEstoqueDeposito(itemEstoqueDeposito);
				
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
			e.printStackTrace();
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
	public LogRetorno getCodBarById(String codigo) {
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<ItemCodBar> list = em.createNamedQuery("CodBarById")
					.setParameter("codBarras", codigo)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
					.getResultList();
			
			if(list.size() == 0){
				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			}else{
				logRet.setStatus(EnumLogRetornoStatus.ERRO);
				logRet.setMsg("Código de barras ja Cadastrado ITEM: "+ list.get(0).getItem().getCodigo());
				logRet.setObjeto(list.get(0));
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
			throw e;
		
		}finally{
			em.close();
		}
		return logRet;
	}
	
	public LogRetorno update(Object objeto, List<ItemCodBar> listCodBar, List<ItemCodBar> listCodBarOrigem, List<ItemFornecedor> listItemFornecedor){
		
		Item item = (Item) objeto;
	
		LogRetorno logRet = new LogRetorno();
		
		try {
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);
			
			em.getTransaction().begin();
			
//			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
//			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
//			ts_now = Util.dateToLocalDateTime(dataServer);
//			
//			
//			
//			em.createQuery("UPDATE Item i SET "
//					+ " i.lastMovto = :lastMovto,"
//					+ " i.lastCoduser = :lastCoduser,"
//					+ " i.utctag = :utctag,"
//					+ " i.descricao = :descricao"
//					+ " WHERE i.codigo = :codItem AND i.checkDelete = :itemCheckDelete and i.codemp = :codEmp"
//					)
//			.setParameter("lastMovto", ts_now)
//			.setParameter("lastCoduser", DadosGlobais.usuarioLogado.getCodigo())
//			.setParameter("utctag", utctag)
//			.setParameter("descricao", item.getDescricao())
//			.setParameter("codItem", item.getCodigo())
//			.setParameter("itemCheckDelete", item.getCheckDelete())
//			.setParameter("codEmp", item.getCodemp())
//			.executeUpdate();
//			
		
//			em.createQuery("UPDATE ItemValor iv SET "
//					+ " iv.lastMovto = :lastMovto,"
//					+ " iv.lastCoduser = :lastCoduser,"
//					+ " iv.utctag = :utctag,"
//					+ " iv.qtdMax = :qtdMax"
//					+ " WHERE iv.codemp = :codEmp AND iv.item = :item"
//					)
//			.setParameter("lastMovto", ts_now)
//			.setParameter("lastCoduser", DadosGlobais.usuarioLogado.getCodigo())
//			.setParameter("utctag", utctag)
//			.setParameter("qtdMax", item.getItemValors().get(0).getQtdMax())
//			.setParameter("item", item)
//			.setParameter("codEmp", item.getItemValors().get(0).getCodemp())
//			.executeUpdate();
//			
			
			ItemPK itemPK = new ItemPK();
			itemPK.setCodigo(item.getCodigo());
			itemPK.setCheckDelete(item.getCheckDelete());

			
		    Item newItem = em.find(Item.class, itemPK);
			
			if(newItem != null && newItem.getFlagAtivo() == 1){
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			newItem.setLastMovto(ts_now);
			newItem.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			newItem.setUtctag(utctag);
			
			newItem.setDescricao(item.getDescricao());
			newItem.setDescReduzida(item.getDescReduzida());
			newItem.setTipoItem(item.getTipoItem());
			newItem.setCodCest(item.getCodCest());
			newItem.setQtdEmbCompra(item.getQtdEmbCompra());
			newItem.setQtdEmbVenda(item.getQtdEmbVenda());
			newItem.setQtdEmbAtacado(item.getQtdEmbAtacado());
			newItem.setQtdVolumes(item.getQtdVolumes());			        
			newItem.setFatorConversao(item.getFatorConversao());
			newItem.setMoeda(item.getMoeda());
			newItem.setGrupo(item.getGrupo());
			newItem.setFabricante(item.getFabricante());
			newItem.setSubGrupo(item.getSubGrupo());
			newItem.setUnidade(item.getUnidade());
			newItem.setUnidadeEmb(item.getUnidadeEmb());
			newItem.setCodDepartamento(item.getCodDepartamento());
			newItem.setCodDepartamentoFk(item.getCodDepartamentoFk());
			newItem.setCodFamiliaPreco(item.getCodFamiliaPreco());
			newItem.setCodFamiliaPrecoFk(item.getCodFamiliaPrecoFk());
			newItem.setCodNcm(item.getCodNcm());
			newItem.setCodNcmFk(item.getCodNcmFk());
			newItem.setNcmCodigo(item.getNcmCodigo());
			newItem.setCodLcServico(item.getCodLcServico());
			newItem.setCodLcServicoFk(item.getCodLcServicoFk());
			newItem.setLcServicoCodigo(item.getLcServicoCodigo());
			
		
			newItem.setDescTecnica(item.getDescTecnica());
			newItem.setNumFrabricate(item.getNumFrabricate());
			newItem.setNumOriginal(item.getNumOriginal());
			newItem.setQtdPallet(item.getQtdPallet());
			newItem.setMedidaM3(item.getMedidaM3());
			newItem.setQtdLitros(item.getQtdLitros());
			newItem.setPesoBruto(item.getPesoBruto());
			newItem.setPesoLiquido(item.getPesoLiquido());
			newItem.setObservacao(item.getObservacao());	

						
			newItem.setFlagBalanca(item.getFlagBalanca());							
			newItem.setBalancaTipoPreco(item.getBalancaTipoPreco());				    
			newItem.setBalancaInstrucoes(item.getBalancaInstrucoes());										
			newItem.setBalancaDiasValidade(item.getBalancaDiasValidade());	

			newItem.setFlagDisponivelNet(item.getFlagDisponivelNet());
			newItem.setFlagMobile(item.getFlagMobile());
			newItem.setFlagItemComposto(item.getFlagItemComposto());		


			newItem.setProcedimentoEstoqueNegativo(item.getProcedimentoEstoqueNegativo());

			newItem.setDadosAdicionais(item.getDadosAdicionais());
			newItem.setCodigoAnp(item.getCodigoAnp());
			newItem.setPercentualGasAnp(item.getPercentualGasAnp());
			
			newItem.setCodItemSimilar1(item.getCodItemSimilar1());
			newItem.setCodItemSimilar1Fk(item.getCodItemSimilar1Fk());

			newItem.setCodItemSimilar2(item.getCodItemSimilar2());
			newItem.setCodItemSimilar2Fk(item.getCodItemSimilar2Fk());

			newItem.setCodItemSimilar3(item.getCodItemSimilar3());
			newItem.setCodItemSimilar3Fk(item.getCodItemSimilar3Fk());

			newItem.setCodItemSimilar4(item.getCodItemSimilar4());
			newItem.setCodItemSimilar4Fk(item.getCodItemSimilar4Fk());

			newItem.setCodItemSimilar5(item.getCodItemSimilar5());
			newItem.setCodItemSimilar5Fk(item.getCodItemSimilar5Fk());

			newItem.setCodItemSimilar6(item.getCodItemSimilar6());
			newItem.setCodItemSimilar6Fk(item.getCodItemSimilar6Fk());

			//--------------------CORRESPONDING VALIDATION AND SAVE OF TAB DADOS D VEICULO---------------------------------- 


			newItem.setVeiculoFlag(item.getVeiculoFlag());	

			newItem.setVeiculoChassi(item.getVeiculoChassi());					
			newItem.setVeiculoCor(item.getVeiculoCor());					
			newItem.setVeiculoPotencia(item.getVeiculoPotencia());					
			newItem.setVeiculoCm3(item.getVeiculoCm3());					
			newItem.setVeiculoPesoBruto(item.getVeiculoPesoBruto());					
			newItem.setVeiculoPesoLiquido(item.getVeiculoPesoLiquido());					
			newItem.setVeiculoNoSerie(item.getVeiculoNoSerie());					
			newItem.setVeiculoTipoCombustivel(item.getVeiculoTipoCombustivel());
			newItem.setVeiculoNoMotor(item.getVeiculoNoMotor());					
			newItem.setVeiculoDistanciaEixos(item.getVeiculoDistanciaEixos());
			newItem.setVeiculoRenavam(item.getVeiculoRenavam());					
			newItem.setVeiculoAnoFabricacao(item.getVeiculoAnoFabricacao());
			newItem.setVeiculoAnoModelo(item.getVeiculoAnoModelo());
			newItem.setVeiculoTipoPintura(item.getVeiculoTipoPintura());
			newItem.setVeiculoCmkg(item.getVeiculoCmkg());					
			newItem.setVeiculoTipo(item.getVeiculoTipo());					
			newItem.setVeiculoEspecie(item.getVeiculoEspecie());
			newItem.setVeiculoCondicaoVin(item.getVeiculoCondicaoVin());
			newItem.setVeiculoCondicao(item.getVeiculoCondicao());					
			newItem.setVeiculoCodMarcaModelo(item.getVeiculoCodMarcaModelo());					
			newItem.setVeiculoLotacao(item.getVeiculoLotacao());
			newItem.setVeiculoRestricao(item.getVeiculoRestricao());					
			newItem.setVeiculoFlagEnviaVenda(item.getVeiculoFlagEnviaVenda());			
			
			Query sqlUpdateValor;
			String qy;
			
			
				for(int i=0; i < item.getItemValors().size(); i++){
			
					if(item.getItemValors().get(i).getFlagChangeItemValor() || item.getItemValors().get(i).getFlagChangeTributacaoItem() || item.getItemValors().get(i).getFlagChangePrecoEmp()){
					
					qy= "UPDATE ItemValor iv SET ";
					
					qy  += " iv.lastMovto = :lastMovto, iv.lastCoduser = :lastCoduser, iv.utctag = :utctag ";
					
					if(item.getItemValors().get(i).getFlagChangeItemValor())
					qy += " ,iv.qtdMax = :qtdMax, iv.qtdMin = :qtdMin, iv.locacao = :locacao";
					
					if(item.getItemValors().get(i).getFlagChangeTributacaoItem())
							qy += " ,COD_TRIBUTACAO = :codTributacao, COD_TRIBUTACAO_FK = :codTributacaoFk";
					
					if(item.getItemValors().get(i).getFlagChangePrecoEmp()){
						qy += ", VLR_CUSTO = :valorCusto"
							+ ", FP_VLR_EMBALAGEM = :valorEmbalagem"
							+ ", FP_DESCONTO_COMPRA = :descontoCompra"
							+ ", FP_IPI_COMPRA = :ipiCompra"
							+ ", FP_FRETE_COMPRA = :freteCompra"
							+ ", FP_CREDITO_ICMS = :creditoIcms"
							+ ", FP_ICMS_DESONERADO = :icmsDesonerado"
							+ ", FP_SUBSTITUICAO_TRIB = :substituicaoTrib"
							+ ", FP_CREDITO_PIS = :creditoPis"
							+ ", FP_CREDITO_COFINS = :creditoCofins"
							+ ", FP_DESP_ACESSORIA = :despesaAcessoria"
							+ ", FP_SEGURO = :seguroCompra"
							+ ", FP_OUTROS_CREDITOS = :outrosCreditos"
							+ ", FP_OUTROS_CUSTOS = :outrosCustos"
							+ ", FP_MARGEM_LIQUIDA_VAREJO = :margemLiquidaVarejo"
							+ ", FP_MARGEM_LIQUIDA_ATACADO = :margemLiquidaAtacado"
							+ ", FP_CUSTO_OPERACIONAL_VAREJO = :custoOperacionalVarejo"
							+ ", FP_CUSTO_OPERACIONAL_ATACADO = :custoOperacionalAtacado"
							+ ", FP_PIS_VENDA_VAREJO = :pisVendaVarejo"
							+ ", FP_PIS_VENDA_ATACADO = :pisVendaAtacado"
							+ ", FP_COFINS_VENDA_VAREJO = :cofinsVendaVarejo"
							+ ", FP_COFINS_VENDA_ATACADO = :cofinsVendaAtacado"
							+ ", FP_ICMS_ISS_VENDA_VAREJO = :icmsIssVendaVarejo"
							+ ", FP_ICMS_ISS_VENDA_ATACADO = :icmsIssVendaAtacado"
							+ ", FP_COMISSAO_VENDA_VAREJO = :comissaoVendaVarejo"
							+ ", FP_COMISSAO_VENDA_ATACADO = :comissaoVendaAtacado"
							+ ", FP_IMPOSTO_RENDA_VAREJO = :impostoRendaVarejo"
							+ ", FP_IMPOSTO_RENDA_ATACADO = :impostoRendaAtacado"
							+ ", FP_CONTRIBUICAO_SOCIAL_VAREJO = :contribSocialVarejo"
							+ ", FP_CONTRIBUICAO_SOCIAL_ATACADO = :contribSocialAtacado"
							+ ", FP_FRETE_VENDA_VAREJO = :freteVendaVarejo"
							+ ", FP_FRETE_VENDA_ATACADO = :freteVendaAtacado"
							+ ", FP_SIMPLES_NACIONAL_VAREJO = :simplesNacVarejo"
							+ ", FP_SIMPLES_NACIONAL_ATACADO = :simplesNacAtacado"
							+ ", FP_OUTRAS_DESPESAS_VAREJO = :outrasDespesasVarejo"
							+ ", FP_OUTRAS_DESPESAS_ATACADO = :outrasDespesasAtacado"
							+ ", FP_CUSTO_REAL_COMPRA = :custoRealCompra"
							//+ ", MARGEM_LUCRO_BRUTO_ATACADO = :margemBrutoAtacado"
							//+ ", MARGEM_LUCRO_BRUTO_VAREJO = :margemBrutoVarejo"
							+ ", VLR_VENDA_VAREJO = :vlrVendaVarejo"
							+ ", VLR_VENDA_ATACADO = :vlrVendaAtacado";						
					}
						
					qy += " WHERE iv.codemp = :codEmp AND iv.item = :item";
					
					sqlUpdateValor = em.createQuery(qy)
									.setParameter("lastMovto", ts_now)
									.setParameter("lastCoduser", DadosGlobais.usuarioLogado.getCodigo())
									.setParameter("utctag", utctag)
									.setParameter("item", item)
									.setParameter("codEmp", item.getItemValors().get(i).getCodemp());
									
					
					if(item.getItemValors().get(i).getFlagChangeItemValor()){
						sqlUpdateValor.setParameter("qtdMax", item.getItemValors().get(i).getQtdMax());
						sqlUpdateValor.setParameter("qtdMin", item.getItemValors().get(i).getQtdMin());
						sqlUpdateValor.setParameter("locacao", item.getItemValors().get(i).getLocacao());
					}
					
					if(item.getItemValors().get(i).getFlagChangeTributacaoItem()){
						sqlUpdateValor.setParameter("codTributacao", item.getItemValors().get(i).getTributacao().getCodigo());
						sqlUpdateValor.setParameter("codTributacaoFk", item.getItemValors().get(i).getTributacao().getCheckDelete());
					}
					
					if(item.getItemValors().get(i).getFlagChangePrecoEmp()){
						
						sqlUpdateValor.setParameter("valorCusto", item.getItemValors().get(i).getVlrCusto());
						sqlUpdateValor.setParameter("valorEmbalagem", item.getItemValors().get(i).getFpVlrEmbalagem());
						sqlUpdateValor.setParameter("descontoCompra", item.getItemValors().get(i).getFpDescontoCompra());
						sqlUpdateValor.setParameter("ipiCompra",item.getItemValors().get(i).getFpIpiCompra());
						sqlUpdateValor.setParameter("freteCompra",item.getItemValors().get(i).getFpFreteCompra());
						sqlUpdateValor.setParameter("creditoIcms",item.getItemValors().get(i).getFpCreditoIcms());
						sqlUpdateValor.setParameter("icmsDesonerado",item.getItemValors().get(i).getFpIcmsDesonerado());
						sqlUpdateValor.setParameter("substituicaoTrib",item.getItemValors().get(i).getFpSubstituicaoTrib());
						sqlUpdateValor.setParameter("creditoPis",item.getItemValors().get(i).getFpCreditoPis());
						sqlUpdateValor.setParameter("creditoCofins",item.getItemValors().get(i).getFpCreditoCofins());
						sqlUpdateValor.setParameter("despesaAcessoria",item.getItemValors().get(i).getFpDespAcessoria());
						sqlUpdateValor.setParameter("seguroCompra",item.getItemValors().get(i).getFpSeguro());
						sqlUpdateValor.setParameter("outrosCreditos",item.getItemValors().get(i).getFpOutrosCreditos());
						sqlUpdateValor.setParameter("outrosCustos",item.getItemValors().get(i).getFpOutrosCustos());
						sqlUpdateValor.setParameter("margemLiquidaVarejo",item.getItemValors().get(i).getFpMargemLiquidaVarejo());
						sqlUpdateValor.setParameter("margemLiquidaAtacado",item.getItemValors().get(i).getFpMargemLiquidaAtacado());
						sqlUpdateValor.setParameter("custoOperacionalVarejo",item.getItemValors().get(i).getFpCustoOperacionalVarejo());
						sqlUpdateValor.setParameter("custoOperacionalAtacado",item.getItemValors().get(i).getFpCustoOperacionalAtacado());
						sqlUpdateValor.setParameter("pisVendaVarejo",item.getItemValors().get(i).getFpPisVendaVarejo());
						sqlUpdateValor.setParameter("pisVendaAtacado",item.getItemValors().get(i).getFpPisVendaAtacado());
						sqlUpdateValor.setParameter("cofinsVendaVarejo",item.getItemValors().get(i).getFpCofinsVendaVarejo());
						sqlUpdateValor.setParameter("cofinsVendaAtacado",item.getItemValors().get(i).getFpCofinsVendaAtacado());
						sqlUpdateValor.setParameter("icmsIssVendaVarejo",item.getItemValors().get(i).getFpIcmsIssVendaVarejo());
						sqlUpdateValor.setParameter("icmsIssVendaAtacado",item.getItemValors().get(i).getFpIcmsIssVendaAtacado());
						sqlUpdateValor.setParameter("comissaoVendaVarejo",item.getItemValors().get(i).getFpComissaoVendaVarejo());
						sqlUpdateValor.setParameter("comissaoVendaAtacado",item.getItemValors().get(i).getFpComissaoVendaAtacado());
						sqlUpdateValor.setParameter("impostoRendaVarejo",item.getItemValors().get(i).getFpImpostoRendaVarejo());
						sqlUpdateValor.setParameter("impostoRendaAtacado",item.getItemValors().get(i).getFpImpostoRendaAtacado());
						sqlUpdateValor.setParameter("contribSocialVarejo",item.getItemValors().get(i).getFpContribuicaoSocialVarejo());
						sqlUpdateValor.setParameter("contribSocialAtacado",item.getItemValors().get(i).getFpContribuicaoSocialAtacado());
						sqlUpdateValor.setParameter("freteVendaVarejo",item.getItemValors().get(i).getFpFreteVendaVarejo());
						sqlUpdateValor.setParameter("freteVendaAtacado",item.getItemValors().get(i).getFpFreteVendaAtacado());
						sqlUpdateValor.setParameter("simplesNacVarejo",item.getItemValors().get(i).getFpSimplesNacionalVarejo());
						sqlUpdateValor.setParameter("simplesNacAtacado",item.getItemValors().get(i).getFpSimplesNacionalAtacado());
						sqlUpdateValor.setParameter("outrasDespesasVarejo",item.getItemValors().get(i).getFpOutrasDespesasVarejo());
						sqlUpdateValor.setParameter("outrasDespesasAtacado",item.getItemValors().get(i).getFpOutrasDespesasAtacado());
						sqlUpdateValor.setParameter("custoRealCompra",item.getItemValors().get(i).getFpCustoRealCompra());
						//sqlUpdateValor.setParameter("margemBrutoAtacado",item.getItemValors().get(i).getFpma);
						//sqlUpdateValor.setParameter("margemBrutoVarejo",item.getItemValors().get(i).getFpVlrEmbalagem());
						sqlUpdateValor.setParameter("vlrVendaVarejo",item.getItemValors().get(i).getVlrVendaVarejo());
						sqlUpdateValor.setParameter("vlrVendaAtacado",item.getItemValors().get(i).getVlrVendaAtacado());						
						
					}
					try {
					
						sqlUpdateValor.executeUpdate();	
					
					} catch (Exception e1) {
						em.getTransaction().rollback();
						throw e1;
					}
					
					
				
				}
			}
		
			
				
				for (int i = 0; i < listCodBar.size(); i++) {
					
					if(listCodBar.get(i).getCheckDelete() == null){
						
						listCodBar.get(i).setCodemp(newItem.getCodemp());
						listCodBar.get(i).setItem(newItem);
						listCodBar.get(i).setCheckDelete(Util.checkDeleteCreate());
						listCodBar.get(i).setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
						listCodBar.get(i).setLastMovto(ts_now);
						listCodBar.get(i).setUtctag(utctag);
						em.persist(listCodBar.get(i));
					
					}else{
					
					for (int j = 0; j < listCodBarOrigem.size(); j++) {
							
						if(listCodBar.get(i).getCodBarras().equals(listCodBarOrigem.get(j).getCodBarras())){
							
							if(!listCodBar.get(i).equals(listCodBarOrigem.get(j))){
								
								if(listCodBar.get(i).getFlagAtivo().equals(1)){
								
								em.createQuery("UPDATE ItemCodBar iv SET "
								+ " iv.lastMovto = :lastMovto,"
								+ " iv.lastCoduser = :lastCoduser,"
								+ " iv.utctag = :utctag,"
								+ " iv.qtdEmbalagem = :qtdEmbalagem,"
								+ " iv.flagCodbarPrincipal = :flagCodbarPrincipal"
						    	+ " WHERE iv.codBarras = :codBarras AND iv.checkDelete = :checkDelete AND iv.item  = :item")
						.setParameter("lastMovto", ts_now)
						.setParameter("lastCoduser", DadosGlobais.usuarioLogado.getCodigo())
						.setParameter("utctag", utctag)
						.setParameter("qtdEmbalagem", listCodBar.get(i).getQtdEmbalagem())
						.setParameter("flagCodbarPrincipal", listCodBar.get(i).getFlagCodbarPrincipal())
						.setParameter("codBarras",listCodBar.get(i).getCodBarras())
						.setParameter("checkDelete",listCodBar.get(i).getCheckDelete())
						.setParameter("item", listCodBar.get(i).getItem())
						.executeUpdate();
								
								}else{
									try {
										em.createQuery("DELETE FROM ItemCodBar iv WHERE iv.codBarras = :codBarras AND iv.checkDelete = :checkDelete AND iv.item  = :item")
										.setParameter("codBarras",listCodBar.get(i).getCodBarras())
										.setParameter("checkDelete",listCodBar.get(i).getCheckDelete())
										.setParameter("item", listCodBar.get(i).getItem())
										.executeUpdate();
									} catch (Exception e2) {
										em.getTransaction().rollback();
										throw e2;
									}
									
								}
							}
						}
					}
					}	
				}
				
				for (int j = 0; j < listItemFornecedor.size(); j++) {
				
					if(listItemFornecedor.get(j).getCheckDelete() == null && listItemFornecedor.get(j).getFlagAtivo().equals(1)){
					
						
						listItemFornecedor.get(j).setCodemp(newItem.getCodemp());
						listItemFornecedor.get(j).setItem(newItem);
						listItemFornecedor.get(j).setCheckDelete(Util.checkDeleteCreate());
						listItemFornecedor.get(j).setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
						listItemFornecedor.get(j).setLastMovto(ts_now);
						listItemFornecedor.get(j).setUtctag(utctag);
						em.persist(listItemFornecedor.get(j));
					
					
					}else if(listItemFornecedor.get(j).getCheckDelete() != null && listItemFornecedor.get(j).getFlagAtivo().equals(0)){
						
						try {
							em.createQuery("DELETE FROM ItemFornecedor iv WHERE iv.codItemFornecedor = :codItemFornecedor AND iv.checkDelete = :checkDelete AND iv.item  = :item AND iv.fornecedor = :fornecedor")
							.setParameter("codItemFornecedor",listItemFornecedor.get(j).getCodItemFornecedor())
							.setParameter("checkDelete",listItemFornecedor.get(j).getCheckDelete())
							.setParameter("item", listItemFornecedor.get(j).getItem())
							.setParameter("fornecedor", listItemFornecedor.get(j).getFornecedor())
							.executeUpdate();
						} catch (Exception e2) {
							em.getTransaction().rollback();
							throw e2;
						}

					}

				}
			
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
		
		Item item = (Item) objeto;
		
		try {
			
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			
			Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
			utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));			
			ts_now = Util.dateToLocalDateTime(dataServer);
			
			em.createQuery("UPDATE Item u "
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
			auditoria.setTipoRegistro(DadosGlobais.resourceBundle.getString("miCadItem").toUpperCase());
			auditoria.setDtMovto(Util.geraDataMovto(DadosGlobais.dataMovtoSistema));
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
	public List<Item> filterByColumn (String column, String filter, int action, List<Integer> ativo) {
		 					
		List<Item> listFilter = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em
							.createQuery("SELECT d FROM Item d "
									+ "LEFT JOIN FETCH d.fabricante f "
									+ "LEFT JOIN FETCH d.moeda m "
									+ "LEFT JOIN FETCH d.grupo g "
									+ "LEFT JOIN FETCH d.subGrupo sg "
									+ "LEFT JOIN FETCH d.departamento dep "
									+ "LEFT JOIN FETCH d.familiaPreco fam "
									+ "LEFT JOIN FETCH d.unidade uni "
									+ "LEFT JOIN FETCH d.unidadeEmb uniEmb "
									+ "LEFT JOIN FETCH d.ncm nc "
									+ "LEFT JOIN FETCH d.lcServico lc "
									+ "LEFT JOIN FETCH d.itemCodBars cb "
									+ "LEFT JOIN FETCH d.itemValors vl "
									+ " WHERE d." + column
									+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", (filter.equals("")) ? "%" : filter)
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
							.setParameter("flag", ativo)
							.getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT d FROM Item d "
									+ "LEFT JOIN FETCH d.fabricante f "
									+ "LEFT JOIN FETCH d.moeda m "
									+ "LEFT JOIN FETCH d.grupo g "
									+ "LEFT JOIN FETCH d.subGrupo sg "
									+ "LEFT JOIN FETCH d.departamento dep "
									+ "LEFT JOIN FETCH d.familiaPreco fam "
									+ "LEFT JOIN FETCH d.unidade uni "
									+ "LEFT JOIN FETCH d.unidadeEmb uniEmb "
									+ "LEFT JOIN FETCH d.ncm nc "
									+ "LEFT JOIN FETCH d.lcServico lc "
									+ "LEFT JOIN FETCH d.itemCodBars cb "
									+ "LEFT JOIN FETCH d.itemValors vl "
									+ " WHERE d." + column
									+ "= :filter AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))
							.setParameter("codemp", Util.getCompartilhamentoEntidade(EnumCompartilhamento.ITENS))
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT d FROM Item d "
								+ "LEFT JOIN FETCH d.fabricante f "
								+ "LEFT JOIN FETCH d.moeda m "
								+ "LEFT JOIN FETCH d.grupo g "
								+ "LEFT JOIN FETCH d.subGrupo sg "
								+ "LEFT JOIN FETCH d.departamento dep "
								+ "LEFT JOIN FETCH d.familiaPreco fam "
								+ "LEFT JOIN FETCH d.unidade uni "
								+ "LEFT JOIN FETCH d.unidadeEmb uniEmb "
								+ "LEFT JOIN FETCH d.ncm nc "
								+ "LEFT JOIN FETCH d.lcServico lc "
								+ "LEFT JOIN FETCH d.itemCodBars cb "
								+ "LEFT JOIN FETCH d.itemValors vl "
								+ " WHERE d." + column
								+ " LIKE CONCAT('%', :filter, '%') AND d.codemp IN (:codemp) AND d.flagAtivo IN (:flag)")
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
