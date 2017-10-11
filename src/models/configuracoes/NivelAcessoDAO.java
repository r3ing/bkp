package models.configuracoes;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import com.mysql.jdbc.Util;

import application.DadosGlobais;
import connect.ConnectionHib;
import javafx.scene.control.MenuBar;
import tools.application.ListMenu;

public class NivelAcessoDAO {

	private EntityManager em = null;
	private int utctag;
	private LocalDateTime ts_now;
	private MenuBar motro;
	

	public boolean gerarDados(int idUsuario, int codemp, List<String> menu, BigInteger checkDeleteUser, boolean flagAllAcesso)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		ts_now = LocalDateTime.now();
		utctag = (int) (new Date().getTime() / 1000);
		em = ConnectionHib.emf.createEntityManager();
		boolean resultado = false;
		NivelAcesso nivAcesso=null;
		
		
		try {
			

		for (String s : menu) {
				em.getTransaction().begin();
				
				UsuarioPK usuarioPK = new UsuarioPK();
				usuarioPK.setCheckDelete(checkDeleteUser);
				usuarioPK.setCodemp(codemp);
				usuarioPK.setCodigo(idUsuario);
				
				Usuario usuario = em.find(Usuario.class, usuarioPK);
				
//				NivelAcessoPK nivAcessoPk = new NivelAcessoPK();			
				nivAcesso = new NivelAcesso();
				nivAcesso.setUsuario(usuario);
				nivAcesso.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
				nivAcesso.setLastMovto(ts_now);
				nivAcesso.setUtctag((utctag));
				nivAcesso.setFlagAtivo(1);
				nivAcesso.setMenu(s);				
				nivAcesso.setFlagView((flagAllAcesso) ? 1 : 0);
				nivAcesso.setFlagInsert((s.subSequence(2, 5).equals("Cad"))  ?((flagAllAcesso) ? 1 : 0)  : 0);
				nivAcesso.setFlagUpdate((s.subSequence(2, 5).equals("Cad")) ? ((flagAllAcesso) ? 1 : 0) : 0);
				nivAcesso.setFlagDisable((s.subSequence(2, 5).equals("Cad")) ? ((flagAllAcesso) ? 1 : 0) : 0);
				nivAcesso.setFlagEnable((s.subSequence(2, 5).equals("Cad")) ? ((flagAllAcesso) ? 1 : 0) : 0);
				nivAcesso.setFlagPrint((s.subSequence(2, 5).equals("Cad")) ? ((flagAllAcesso) ? 1 : 0) : 0);
				
				em.persist(nivAcesso);
				em.getTransaction().commit();
				
				resultado = true;
			
			
		}
		} catch (Exception e) {
			
			em.getTransaction().rollback();
			resultado = false;
			
		}finally {
			
			em.close();
		}
		
	
		
		return resultado;
		
	}
	
	public void insertNewMenu(Usuario user, List<String> listNewMenu ) throws Exception{		
		ts_now = LocalDateTime.now();
		utctag = (int) (new Date().getTime() / 1000);
		em = ConnectionHib.emf.createEntityManager();	
		
		for (String menus : listNewMenu) {			
			em.getTransaction().begin();
			UsuarioPK usuarioPk = new UsuarioPK();
			usuarioPk.setCheckDelete(user.getCheckDelete());
			usuarioPk.setCodemp(user.getCodemp());
			usuarioPk.setCodigo(user.getCodigo());			
			Usuario usuario = em.find(Usuario.class, usuarioPk);
			
			NivelAcesso nivAcesso = new NivelAcesso();				
			nivAcesso.setUsuario(usuario);			
			nivAcesso.setLastCoduser(DadosGlobais.usuarioLogado.getCodigo());
			nivAcesso.setLastMovto(ts_now);
			nivAcesso.setUtctag((utctag));
			nivAcesso.setFlagAtivo(1);
			nivAcesso.setMenu(menus);
			nivAcesso.setFlagView(0);
			nivAcesso.setFlagInsert(0);
			nivAcesso.setFlagUpdate(0);
			nivAcesso.setFlagDisable(0);
			nivAcesso.setFlagEnable(0);
			nivAcesso.setFlagPrint(0);				
			em.persist(nivAcesso);
//			em.flush();
			em.getTransaction().commit();
			em.clear();
		}

		em.close();
		
	}

	public boolean userMenuExist(EntityManager em, int checkDelete, int codUsuario, int codEmp, String idMenu) {
		boolean exist = false;
		try {
			List<NivelAcesso> result = em
					.createQuery(
							"SELECT n FROM NivelAcesso n WHERE n.id.codUsuario =:codUsuario AND n.id.codemp =:codEmp AND checkDelete =:checkDelete AND menu =:idMenu")
					.setParameter("codUsuario", codUsuario).setParameter("codEmp", codEmp)
					.setParameter("checkDelete", checkDelete).setParameter("idMenu", idMenu).getResultList();
			System.out.println(result);
			if (result.isEmpty()) {
				exist = true;
				System.out.println(result);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return exist;
	}

	public BigInteger getCheckDeleteUser(EntityManager em, int codeUser, int codEmp) {
		BigInteger checkDeleteUser = null;
		try {
			List<Usuario> usuario = em
					.createQuery("SELECT u FROM Usuario u WHERE u.id.codigo =:codeUser AND u.id.codemp =:codEmp")
					.setParameter("codeUser", codeUser).setParameter("codEmp", codEmp).getResultList();
			if(usuario !=null){
				for(Usuario user : usuario)
					checkDeleteUser = user.getCheckDelete();
			}
		} catch (HibernateException e) {
			System.err.println(e.getMessage());
		} finally {

		}

		return checkDeleteUser;
	}

	public void alterarPermisos(Usuario user, String menu, boolean flagView, boolean flagInsert,boolean flagUpdate, boolean flagDisable, boolean flagEnabled, boolean flagPrint) {
		try{
		em = ConnectionHib.emf.createEntityManager();
		em.getTransaction().begin();
		NivelAcessoPK nAcessoPK = new NivelAcessoPK();
		nAcessoPK.setUsuario(user);
		nAcessoPK.setMenu(menu);
		NivelAcesso nAcesso = em.find(NivelAcesso.class, nAcessoPK);		
		nAcesso.setFlagView((flagView) ? 1 : 0);
		nAcesso.setFlagInsert((flagInsert) ? 1 : 0);
		nAcesso.setFlagUpdate((flagUpdate) ? 1 : 0);
		nAcesso.setFlagDisable((flagDisable) ? 1 : 0);
		nAcesso.setFlagEnable((flagEnabled) ? 1 : 0);
		nAcesso.setFlagPrint((flagPrint) ? 1 : 0);
		// em.persist(nAcesso);
		em.getTransaction().commit();
		}catch(HibernateException e){
			em.getTransaction().rollback();
			throw e;
		}finally {
			em.close();
		}
		

	}
	
	public void removeMenu(Usuario user, List<NivelAcesso> listMenuRemove){
		try{
			em = ConnectionHib.emf.createEntityManager();
			
			em.getTransaction().begin();
			for(NivelAcesso nivelAcessoRemove : listMenuRemove){
				NivelAcessoPK nAcessoPK = new NivelAcessoPK();
				nAcessoPK.setUsuario(user);
				nAcessoPK.setMenu(nivelAcessoRemove.getMenu());
				
				NivelAcesso remove = em.find(NivelAcesso.class, nAcessoPK);
				em.remove(remove);
			}
			em.getTransaction().commit();
			
		}catch(HibernateException e){
			throw e;
		}finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<NivelAcesso> getListPermisao(Usuario user) {
		
		List<NivelAcesso> l = null;
		try {
			
			em = ConnectionHib.emf.createEntityManager();
			l = em
					.createNamedQuery("NivelAcessoAll")
					.setParameter("user", user)
					.getResultList();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		finally {
			em.close();
		}
		
		return l;
	}

	public void duplicaNivelAcesso(List<NivelAcesso> listaOrigem, List<NivelAcesso> listaDestino){

		em = ConnectionHib.emf.createEntityManager();
		em.getTransaction().begin();
		for(int i=0; i < listaOrigem.size(); i++){
			
			for(int j=0; j < listaDestino.size(); j++){
				
				if(listaOrigem.get(i).getMenu().equals(listaDestino.get(j).getMenu())){
					
					
					NivelAcessoPK nAcessoPK = new NivelAcessoPK();
////					nAcessoPK.setCodemp(listaDestino.get(j).getId().getCodemp());
//					nAcessoPK.setUsuario(listaDestino.get(j).getUsuario().getCodigo());
					//nAcessoPK.setRecordNo(listaDestino.get(j).getId().getRecordNo());

					NivelAcesso nAcesso = em.find(NivelAcesso.class, nAcessoPK);
					
					
					nAcesso.setFlagView(listaOrigem.get(i).getFlagView());
					nAcesso.setFlagInsert(listaOrigem.get(i).getFlagInsert());
					nAcesso.setFlagUpdate(listaOrigem.get(i).getFlagUpdate());
					nAcesso.setFlagDisable(listaOrigem.get(i).getFlagDisable());
					nAcesso.setFlagEnable(listaOrigem.get(i).getFlagEnable());
					nAcesso.setFlagPrint(listaOrigem.get(i).getFlagPrint());
					// em.persist(nAcesso);
					
				}
			}
			
		}
		
		em.getTransaction().commit();
		
		em.close();
		
	}
	
}
