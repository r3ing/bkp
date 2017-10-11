package models.compras;

import java.util.List;
import javax.persistence.EntityManager;
import connect.ConnectionHib;

public class ItemFornecedorDAO {
	
	private EntityManager em = null;
	
	@SuppressWarnings("unchecked")
	public List<ItemFornecedor> getAllItemFornecedor(Fornecedor forn){
		
		List<ItemFornecedor> list = null;
		try {	
			em = ConnectionHib.emf.createEntityManager();		
			
			list = em.createNamedQuery("AllByFornecedor")
					.setParameter("codForn", forn.getCodigo())
//					.setParameter("checkDeleteForn", forn.getCheckDelete())
					.getResultList();			

			}
			catch (Exception e) {
				throw e;	
			
			} finally {
				em.close();
			}		
		
		return list;
	}

}
