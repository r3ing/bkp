package controllers.utils;


import java.util.Arrays;
import java.util.List;

import application.DadosGlobais;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import models.vendas.Uf;
import models.vendas.UfDAO;

public class ComboBoxUF {
	
	UfDAO ufDAO = new UfDAO();
	List<Integer> paramFlagAtivo = Arrays.asList(1);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ComboBoxUF(ComboBox cbox){
		super();
		// TODO Auto-generated constructor stub
		ObservableList<Uf> list = FXCollections.observableArrayList(ufDAO.getList(paramFlagAtivo));
		ObservableList<String> listUf = FXCollections.observableArrayList();
		for (int i = 0; i < list.size(); i++) {			
			listUf.add(list.get(i).getSigla().toString());
		}
		cbox.getItems().addAll(listUf);

		cbox.getSelectionModel().select(DadosGlobais.empresaLogada.getUf());
		
	}
	
	

}
