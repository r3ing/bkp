package models.vendas;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

public class ClienteEndEntregaDAO implements InterfaceDAO{


	private LocalDateTime ts_now; 
	private BigInteger utctag;
	private EntityManager em = null;
	
	/**
	 * Metodo para buscar departamento pelo CODIGO
	 * 
	 * @param codigo
	 * @return departamento buscado ou null
	 */
	@SuppressWarnings("unchecked")
	public LogRetorno getById(Cliente cliente, BigInteger checkDelete) {
		
		LogRetorno logRet = new LogRetorno();

		try{
			em = ConnectionHib.emf.createEntityManager();
			
			logRet.setStatus(EnumLogRetornoStatus.INICIOTRANSACAO);

			List<ClienteEndEntrega> list = em.createNamedQuery("ClienteEndEntregaById")
					.setParameter("codigo", cliente.getCodigo())
					.setParameter("checkDelete", checkDelete)
					.setParameter("codemp",Util.getCompartilhamentoEntidade(EnumCompartilhamento.CLIENTE_ENDERECO))
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

	@Override
	public LogRetorno insert(Object bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> getList(List<Integer> flagAtivo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogRetorno getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> filterByColumn(String column, String filter, int action, List<Integer> ativo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogRetorno getById(Integer codigoa) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogRetorno update(Object bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Object bean) {
		// TODO Auto-generated method stub
		
	}
	
}
