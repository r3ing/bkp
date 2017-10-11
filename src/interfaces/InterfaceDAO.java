package interfaces;

import java.util.List;
import tools.utils.LogRetorno;

public interface InterfaceDAO {	
	
	LogRetorno insert(Object bean);
	LogRetorno update(Object bean);
	void delete(Object bean);
	List<?> getList(List<Integer> flagAtivo);
	LogRetorno getLast();
	LogRetorno getById(Integer codigoa);
	List<?> filterByColumn (String column, String filter, int action, List<Integer> ativo);
	
}
