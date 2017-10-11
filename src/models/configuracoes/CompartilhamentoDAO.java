package models.configuracoes;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.HibernateException;
import application.DadosGlobais;
import connect.ConnectionHib;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.Util;

public class CompartilhamentoDAO {

	private LocalDateTime ts_now;
	private int utctag;
	private EntityManager em;
	private LogRetorno logRet = new LogRetorno();

	@SuppressWarnings("unchecked")
	public List<Compartilhamento> getCompartilhamentosById(int codEmp, BigInteger checkDeleteEmp) {
		List<Compartilhamento> listCompart = null;
		System.out.println("codigo empresa= "+codEmp+", checkdelete= "+checkDeleteEmp);
		try {
			em = ConnectionHib.emf.createEntityManager();
			listCompart = em
					.createQuery(
							"SELECT c FROM Compartilhamento c WHERE c.id.codemp =:codemp AND c.id.checkDelete =:checkDeleteEmp")
					.setParameter("codemp", codEmp).setParameter("checkDeleteEmp", checkDeleteEmp).getResultList();
		} catch (HibernateException e) {

		} finally {
			em.close();
		}
		return listCompart;
	}

	public LogRetorno inserirDadosCompartilhados(int codEmp, BigInteger checkDeleteEmp, List<String> listMenu) throws Exception {
		
		int recordNumber = 0;
		int recordSession = 0;
		int recordDelete = 0;
//		int checkDelete = 0;
		int recordNo = 0;
		int recordNoNew = 0;
//		int lastCodDepart = 0;

		try {
			em = ConnectionHib.emf.createEntityManager();
			  
			for (String idMenu : listMenu) {

//				SequencialDAO seqDAO = new SequencialDAO();
//				/**
//				 * aqui se le pasa la empresa q este logueada en el moemento del
//				 * uso del software, por defecto estoy usando uno
//				 */
//				Sequencial seq = seqDAO.getSequencial(codEmp);
//				recordNumber = seq.getRecordNumber();
//				recordSession = seq.getRecordSession();
//				recordDelete = seq.getRecordDelete();
//				recordNo = seq.getRecordNo();
//
//				recordNoNew = Integer.parseInt(String.valueOf(seq.getCodemp())
//						+ String.valueOf((seq.getRecordNumber() + 1)) + String.valueOf((seq.getRecordSession() + 1)));
//
//				SequencialPK seqPK = new SequencialPK();
//				// neste caso, vai ser alterado o Sequencial da empresa
//				// pesquizada
//				seqPK.setCodemp(codEmp);
//				seqPK.setCheckDelete(DadosGlobais.empresaLogada.getCheckDelete());
//
//				Sequencial sequencial = em.find(Sequencial.class, seqPK);
////				lastCodDepart = sequencial.getCpDepartamento();
//				sequencial.setRecordNumber(recordNumber + 1);
//				sequencial.setRecordSession(recordSession + 1);
//				sequencial.setRecordDelete(recordDelete + 1);
//				// sequencial.setCpDepartamento(lastCodDepart);
//				sequencial.setLastMovto(ts_now);
//				sequencial.setUtctag(utctag);

				em.getTransaction().begin();
				
				Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();

				CompartilhamentoPK compartPK = new CompartilhamentoPK();
				compartPK.setCodemp(codEmp);
				compartPK.setCheckDelete(checkDeleteEmp);
//				compartPK.setRecordNo(recordNoNew);
				compartPK.setModFuncao(idMenu);
				
				utctag = (int) (dataServer.getTime() / 1000);			
				ts_now = Util.dateToLocalDateTime(dataServer);
				
				Compartilhamento compart = new Compartilhamento();
				compart.setLastMovto(ts_now);
				compart.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				compart.setId(compartPK);
				compart.setUtctag(String.valueOf(utctag));
				compart.setCodEmpcompartilhada(String.valueOf(codEmp));
				em.persist(compart);
				em.getTransaction().commit();
				
				logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
				logRet.setMsg("O registro foi gravado com sucesso.");
			
			}

		} catch (HibernateException e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		} finally {
			em.close();
			
		}
		
		return logRet;

	}
	
	
	public LogRetorno updateCompartilhados(List<Compartilhamento> dadosCompartilhar){
		ts_now = LocalDateTime.now();
		utctag = (int) (new Date().getTime() / 1000);
		try{
			em = ConnectionHib.emf.createEntityManager();
			em.getTransaction().begin();
			for(Compartilhamento compartilhar : dadosCompartilhar){
			Compartilhamento compart = em.find(Compartilhamento.class, compartilhar.getId());
			compart.setLastMovto(ts_now);
			compart.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			compart.setUtctag(String.valueOf(utctag));
			compart.setCodEmpcompartilhada(compartilhar.getCodEmpcompartilhada());
			}
			em.getTransaction().commit();
			logRet.setStatus(EnumLogRetornoStatus.SUCESSO);
			logRet.setMsg("O compartilhamento foi gravado com sucesso.");
		}catch(HibernateException e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
		}finally {
			em.close();
		}
		
		return logRet;
	}
	
	public void deleteMenu(List<Compartilhamento> listMenuDelete){
		CompartilhamentoPK compPK = null;
		try {
			em = ConnectionHib.emf.createEntityManager();	

			em.getTransaction().begin();
			for(int i=0; i<listMenuDelete.size(); i++){				
				compPK = new CompartilhamentoPK();
				compPK.setCodemp(listMenuDelete.get(i).getId().getCodemp());
				compPK.setCheckDelete(listMenuDelete.get(i).getId().getCheckDelete());
				compPK.setModFuncao(listMenuDelete.get(i).getId().getModFuncao());
				Compartilhamento cpm = em.find(Compartilhamento.class, compPK);				
				em.remove(cpm);
			}			
			em.getTransaction().commit();
			
		} catch (HibernateException e) {
			// TODO: handle exception
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
		
	}

}
