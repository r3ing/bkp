package models.configuracoes;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.HibernateException;
import application.DadosGlobais;
import connect.ConnectionHib;
import tools.criptografia.CriptografiaAes;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class EmpresaDAO {

	private LocalDateTime ts_now;
	private BigInteger utctag;
	private EntityManager em;
	private LogRetorno logRet = new LogRetorno();
	public ConfigDAO confDAO = new ConfigDAO();
	public SequencialDAO seqDAO = new SequencialDAO();

	public List<Empresa> getListEmpresa() {
		List<Empresa> listEmpresas = null;
		
		try {
			em = ConnectionHib.emf.createEntityManager();		
			
			listEmpresas = em.createQuery("SELECT e FROM Empresa e INNER JOIN FETCH e.config").getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return listEmpresas;
	}

	public void update(Empresa empresa) {
		ts_now = LocalDateTime.now();
		utctag = BigInteger.valueOf((int) (new Date().getTime() / 1000));

		em = ConnectionHib.emf.createEntityManager();
		try {

			em.getTransaction().begin();

			EmpresaPK emPk = new EmpresaPK();
			emPk.setCodigo(empresa.getCodigo());
			//emPk.setCodemp(empresa.getCodemp());
			//emPk.setRecordNo(empresa.getRecordNo());
			emPk.setCheckDelete(Util.checkDeleteCreate());

			Empresa newEmpresa = em.find(Empresa.class, emPk);

			if (newEmpresa != null && newEmpresa.getFlagAtivo() == 1) {

				empresa.setLastMovto(ts_now);
				empresa.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				empresa.setUtctag(String.valueOf(utctag));

				em.merge(empresa);
				em.getTransaction().commit();

			}

		} catch (Exception e) {

			em.getTransaction().rollback();

		} finally {
			em.close();
		}
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

			List<Empresa> list = em.createNamedQuery("EmpresaById")
					.setParameter("codigo", codigo)
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

	// Metodo para ser usado pelo formulario Compartilhar Empresas
	public List<Empresa> getEmpresas(int codemp) {
		List<Empresa> listEmp = null;
		try {
			em = ConnectionHib.emf.createEntityManager();
			// vai retornar tudas as empresas, mas nao a empresa logada
			listEmp = em.createQuery("SELECT e FROM Empresa e WHERE e.id.codigo <>:codemp")
					.setParameter("codemp", codemp).getResultList();
			// System.out.println("tamanho "+listEmp.get(0).getNomeFantasia());
		} catch (HibernateException hibexc) {
			hibexc.printStackTrace();
		} finally {
			em.close();
		}
		return listEmp;
	}

//	public Empresa getEmpresaCnfig(int codemp) {
//		Empresa emp = null;
//		try {
//
//			em = ConnectionHib.emf.createEntityManager();
//			List<Empresa> list = em.createQuery("SELECT e FROM Empresa e WHERE e.codigo =:codemp")
//					.setParameter("codemp", codemp).getResultList();
//
//			if (!list.isEmpty()) {
//				emp = list.get(0);
//				Config config = confDAO.getConfigById(codemp, emp.getCheckDelete());
//
//				if (config != null)
//					emp.setConfig(config);
//				else
//					emp.setConfig(null);
//
//			}
//
//		} catch (HibernateException hibexc) {
//			hibexc.printStackTrace();
//			System.out.println(hibexc);
//		} finally {
//			em.close();
//		}
//
//		return emp;
//	}

	@SuppressWarnings("unchecked")
	public Empresa getEmpresaSeq(int codemp) throws Exception {
		Empresa emp = null;
		try {

			em = ConnectionHib.emf.createEntityManager();
			
			List<Empresa> list = em.createQuery("SELECT e FROM Empresa e WHERE e.codigo =:codemp")
					.setParameter("codemp", codemp)
					.getResultList();

			if (!list.isEmpty()) {
				emp = list.get(0);
				
				Sequencial seq = seqDAO.getSequencial(codemp);
				if (seq != null)
					emp.setSequencial(seq);
				else
					emp.setSequencial(null);

			}

		} catch (HibernateException hibexc) {
			hibexc.printStackTrace();
			System.out.println(hibexc);
		} finally {
			em.close();
		}

		return emp;
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> filterByColumn(String column, String filter, int action, List<Integer> ativo) {

		List<Empresa> listFilter = null;

		try {
			em = ConnectionHib.emf.createEntityManager();
			
			if (action == 1) {
				if (filter.equals("")) {
					listFilter = em.createNamedQuery("Empresa.findAll").getResultList();
				} else {

					listFilter = em
							.createQuery("SELECT e FROM Empresa e WHERE e." + column
									+ "= :filter AND e.flagAtivo IN (:flag)")
							.setParameter("filter", Integer.parseInt(filter))							
							.setParameter("flag", ativo)
							.getResultList();

				}

			} else {
				listFilter = em
						.createQuery("SELECT e FROM Empresa e WHERE e." + column
								+ " LIKE CONCAT('%', :filter, '%') AND e.flagAtivo IN (:flag)")
						.setParameter("filter", (filter.equals("")) ? "%" : filter)						
						.setParameter("flag", ativo)
						.getResultList();
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			em.close();
		}

		return listFilter;

	}

	/// METODOS UTILIZADOS PELO INSTALADOR
	public Empresa checaEmpresa(int codemp, EntityManagerFactory emf) {

		Empresa emp = new Empresa();

		List<Empresa> listEmp = emf.createEntityManager()
				.createQuery("SELECT e FROM Empresa e WHERE e.id.codigo = :codigo").setMaxResults(1)
				.setParameter("codigo", codemp).getResultList();

		if (listEmp.size() > 0) {
			emp = listEmp.get(0);
		}

		return emp;
	}

	public LogRetorno criaEmpresa(Empresa emp, EntityManagerFactory emf) {

		ts_now = LocalDateTime.now();
		utctag = BigInteger.valueOf((int) (new Date().getTime() / 1000));

		EntityManager eml = null;

		try {

			eml = emf.createEntityManager();

			eml.getTransaction().begin();

			emp.setCheckDelete(Util.checkDeleteCreate());
			emp.setRecordNo(emp.getCodemp());
			emp.setUtctag(String.valueOf(utctag));
			emp.setFlagAtivo(1);
			emp.setLastCoduser(1);
			emp.setDtImplantacao(ts_now);
			emp.setLastMovto(ts_now);

			Sequencial seq = new Sequencial();

			seq.setCheckDelete(Util.checkDeleteCreate());
			seq.setRecordDelete(1);
			seq.setRecordNumber(1);
			seq.setRecordSession(1);
			seq.setCpDepartamento(0);
			seq.setCpUnidade(0);
			seq.setUtUsuario(1);
			seq.setUtctag(utctag);
			seq.setLastCoduser(1);
			seq.setLastMovto(ts_now);
			SequencialPK seqPk = new SequencialPK();
			seqPk.setCodemp(emp.getCodemp());
			seqPk.setCheckDelete(Util.checkDeleteCreate());
			//seq.setId(seqPk);

			eml.persist(emp);
			eml.persist(seq);
			eml.getTransaction().commit();

		} catch (Exception e) {
			System.out.println(e);
			eml.getTransaction().rollback();
		} finally {
			eml.close();
		}
		return logRet;
	}

	public LogRetorno atualizaEmpresa(Empresa emp, EntityManagerFactory emf) {

		ts_now = LocalDateTime.now();
		utctag = BigInteger.valueOf((int) (new Date().getTime() / 1000));

		EntityManager eml = null;

		try {

			eml = emf.createEntityManager();
			eml.getTransaction().begin();

			EmpresaPK empPK = new EmpresaPK();
			//empPK.setCodemp(emp.getCodemp());
			empPK.setCodigo(emp.getCodigo());
			empPK.setCheckDelete(Util.checkDeleteCreate());
			//empPK.setRecordNo(emp.getRecordNo());

			Empresa newEmpresa = eml.find(Empresa.class, empPK);
	
			newEmpresa.setLastMovto(ts_now);
			newEmpresa.setLastCoduser(1);
			newEmpresa.setUtctag(String.valueOf(utctag));
			newEmpresa.setRazaoSocial(emp.getRazaoSocial());
			newEmpresa.setNomeFantasia(emp.getNomeFantasia());
			newEmpresa.setCnpj(emp.getCnpj());
			newEmpresa.setCidade(emp.getCidade());
			newEmpresa.setUf(emp.getUf());
			newEmpresa.setVersao(emp.getVersao());
			newEmpresa.setCrcChecksys(emp.getCrcChecksys());

			eml.getTransaction().commit();

		} catch (Exception e) {

			eml.getTransaction().rollback();

		} finally {
			eml.close();
		}

		return logRet;
	}

}
