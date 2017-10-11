package controllers.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import tools.utils.Util;

public class SpinnerController {

	public SpinnerController(int min, int max, int select, Spinner<Integer>... spinners) {
		super();

		// TODO Auto-generated constructor stub
		for (Spinner<Integer> spinner : spinners) {
			spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, select));

			spinner.addEventFilter(KeyEvent.KEY_TYPED, kt -> {
				char c = kt.getCharacter().charAt(0);
				if (!Character.isDigit(c))
					kt.consume();
			});

			spinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {
				try {
					if (newValue == null) {
						System.out.println(123456789);
						spinner.getValueFactory().setValue((int) select);
					}
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			});

//			spinner.getEditor().textProperty()
//					.addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
//						if (Integer.valueOf(novo) > max) {
//							spinner.getEditor().textProperty().set(String.valueOf(max));
//						}
//					});

			// spinner.focusedProperty().addListener((s, ov, nv) -> {
			// if (!nv)
			// if(spinner.getEditor().getText().isEmpty())
			// spinner.getEditor().textProperty().set(String.valueOf(select));
			// return;
			//
			// });

		}

	}

}
