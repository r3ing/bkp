package models.configuracoes;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.EntityManager;

import application.DadosGlobais;
import connect.ConnectionHib;
import tools.utils.Util;


public class SequencialDAO {

	LocalDateTime ts_now;
	BigInteger utctag;
	private EntityManager em;

	public Sequencial getSequencial(int codemp)  throws Exception{
		
		em = ConnectionHib.emf.createEntityManager();
	
		return (Sequencial) em.createQuery("SELECT s FROM Sequencial s WHERE s.codemp =:code").setParameter("code", codemp)
				.getSingleResult();		
	}
	
	public Integer getLastInsertCodigo(Integer codemp, String columnSearch){
		Integer lastcod = null;
		em = ConnectionHib.emf.createEntityManager();
		try{
			lastcod = (Integer) em.createQuery("SELECT s."+columnSearch+" FROM Sequencial s WHERE s.id.codemp = :code")
			.setParameter("code", codemp)
			.getSingleResult();	
		}catch(Exception e){
			throw e;
		}finally{
			em.close();
		}
		return lastcod;
	}
	
	public void incrementaSessao(Sequencial seq){
		em = ConnectionHib.emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(seq);
		em.getTransaction().commit();
		em.close();
	}
	
	public void update(Sequencial seq) {
		
		ts_now = LocalDateTime.now();
		utctag = BigInteger.valueOf((int) (new Date().getTime() / 1000));

		em = ConnectionHib.emf.createEntityManager();
		try {

			em.getTransaction().begin();

			SequencialPK seqPK = new SequencialPK();
			seqPK.setCodemp(seq.getCodemp());
			seqPK.setCheckDelete(seq.getCheckDelete());		
			
			
			Sequencial newSeq = em.find(Sequencial.class, seqPK);
			
			
			if(newSeq != null){
				
				Date dataServer  = (Date) em.createNativeQuery(ConnectionHib.SGBD.toLowerCase().equals("mysql") ? "SELECT NOW()" : "SELECT  SYSDATE FROM DUAL").getSingleResult();
				utctag = BigInteger.valueOf((int) (dataServer.getTime() / 1000));		
				ts_now = Util.dateToLocalDateTime(dataServer);
				
				seq.setLastMovto(ts_now);
				seq.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				seq.setUtctag(utctag);
				
				em.merge(seq);
				
				em.getTransaction().commit();
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();

		} finally {
			em.close();
		}
	}

}
