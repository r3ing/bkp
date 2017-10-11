package controllers.utils;

import application.DadosGlobais;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import tools.controls.ComboBoxFilter;

public class ComboBoxIndicadorIE {
	
	
	public static void ComboBoxIndicadorIE(ComboBox<ComboBoxFilter> cbox){
		//super();
		
		//REGRAS DE NFE E SPED
		//"1": Contribuinte de ICMS. Neste caso, dever� ser informada a Inscri��o Estadual do destinat�rio.
		//"2": Contribuinte isento. 
		//"9": N�o Contribuinte, que pode ou n�o possuir Inscri��o Estadual no Cadastro de Contribuintes do ICMS.
		
		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.cboxIndicadorIE0"), 0, "1"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.cboxIndicadorIE1"), 1, "2"));
		list.add(new ComboBoxFilter(DadosGlobais.resourceBundle.getString("clienteController.cboxIndicadorIE2"), 2,"9"));
		
		cbox.getItems().addAll(list);
		
		cbox.getSelectionModel().selectFirst();
		
		cbox.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public ComboBoxFilter fromString(String string) {
				
				return null;
			}

			@Override
			public String toString(ComboBoxFilter object) {
				
				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}
		});
		
	}
	
}
