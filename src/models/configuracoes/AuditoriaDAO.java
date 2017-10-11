package models.configuracoes;  

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.EntityManager;
import application.DadosGlobais;
import connect.ConnectionHib;
import tools.utils.Util;

/**
 * @author User
 *
 */
public class AuditoriaDAO { 

	//private EntityManager em;	
	List<Auditoria> listAuditoria ;
	Util util = new Util();
	private EntityManager em = null;
	private BigInteger utctag;
	
	
	/**
	 * Create new Auditoria with attributes
	 * 
	 * @param lastMovto
	 * @param checkDelete
	 * @param recordNo
	 * @param utctag
	 * @param tipoOperacao
	 * @param dtMovto
	 * @param valorAnterior
	 * @param valorNovo
	 * @param historico
	 * @param docCodigo
	 * @return auditoria
	 */
	public Auditoria generateAuditoria(LocalDateTime lastMovto, int checkDelete, int recordNo, Integer utctag,
			String tipoOperacao, String tipoRegistro, LocalDateTime dtMovto, String valorAnterior, String valorNovo,
			String historico, String docCodigo) {

		Auditoria auditoria = new Auditoria();

		auditoria.setLastMovto(lastMovto);
		auditoria.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
		auditoria.setCheckDelete(Util.checkDeleteCreate());
		auditoria.setRecordNo(recordNo);
//		auditoria.setUtctag(utctag);
		auditoria.setFlagAtivo(1);
		auditoria.setCodemp(DadosGlobais.empresaLogada.getCodigo());
		auditoria.setCodUsuario(DadosGlobais.usuarioLogado.getCodigo());
		auditoria.setNomeUsuario(DadosGlobais.usuarioLogado.getNome());
		auditoria.setSistema("EPTUS");
//		auditoria.setTipoOperacao(tipoOperacao);
		auditoria.setTipoRegistro(tipoRegistro);
		auditoria.setDtMovto(util.geraDataMovto());
		auditoria.setValorAnterior(valorAnterior);
		auditoria.setValorNovo(valorNovo);
		auditoria.setHistorico(historico);
		auditoria.setDocCodigo(docCodigo);

		return auditoria;
	}

	/**
	 * Get all Auditoria.
	 * 
	 * @return listAuditoria List with all Auditoria.
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unchecked")
	public List<Auditoria> getListAuditoria() {

		em = ConnectionHib.emf.createEntityManager();
		
		listAuditoria = em.createNamedQuery("Auditoria.findAll").setParameter("codemp", DadosGlobais.empresaLogada.getCodigo()).getResultList();
		
		em.close();

		return listAuditoria;

	}

	@SuppressWarnings("unchecked")
	public List<Auditoria> filterAuditoria(String column, String filter, List<String> tipoOperacion,
			List<LocalDate> data) throws Exception{
		
		try {
		  em = ConnectionHib.emf.createEntityManager();

			if (tipoOperacion.isEmpty()) {
				listAuditoria = em
						.createQuery(
								"SELECT a FROM Auditoria a WHERE a.dtMovto BETWEEN :dateInicial AND :dateFinal AND a.codemp =:codemp AND UPPER(a."
										+ column + ") LIKE CONCAT('%', :filter, '%')")
						.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
						.setParameter("dateInicial", (LocalDateTime.of(data.get(0), LocalTime.MIN)))
						.setParameter("dateFinal", (LocalDateTime.of(data.get(1), LocalTime.MAX)))
						.setParameter("filter", (filter.equals("")) ? "%" : filter).getResultList();

			}

			else {
				listAuditoria = em
						.createQuery(
								"SELECT a FROM Auditoria a WHERE a.dtMovto BETWEEN :dateInicial AND :dateFinal AND a.codemp =:codemp AND a.tipoOperacao IN (:tipoOperacion) "
								+ "AND UPPER(a."+ column + ") LIKE CONCAT('%', :filter, '%')")
						.setParameter("codemp", DadosGlobais.empresaLogada.getCodigo())
						.setParameter("dateInicial", (LocalDateTime.of(data.get(0), LocalTime.MIN)))
						.setParameter("dateFinal", (LocalDateTime.of(data.get(1), LocalTime.MAX)))
						.setParameter("tipoOperacion", tipoOperacion)
						.setParameter("filter", (filter.equals("")) ? "%" : filter).getResultList();

			}

		
		} finally {
			em.close();
		}

		return listAuditoria;

	}


	
}
