package controllers.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.DadosGlobais;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.StringConverter;
import tools.controls.ComboBoxFilter;

/**
 * Class ComboBox Data
 * 
 * @author User
 *
 */
public class ComboBoxData {

	private final LocalDate today = LocalDate.now();
	private String pattern = "dd/MM/yyyy";

	ObservableList<CboxData> listPeriodo = FXCollections.observableArrayList(
			new CboxData(DadosGlobais.resourceBundle.getString("hoje"), "hoje"),
			new CboxData(DadosGlobais.resourceBundle.getString("estaSemana"), "estaSemana"),
			new CboxData(DadosGlobais.resourceBundle.getString("semanaAnterior"), "semanaAnterior"),
			new CboxData(DadosGlobais.resourceBundle.getString("esteMes"), "esteMes"),
			new CboxData(DadosGlobais.resourceBundle.getString("mesAnterior"), "mesAnterior"),
			new CboxData(DadosGlobais.resourceBundle.getString("esteAno"), "esteAno"),
			new CboxData(DadosGlobais.resourceBundle.getString("todos"), "todos"));

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ComboBoxData(ComboBox cbox, DatePicker dateInitial, DatePicker dateFinal) {
		super();
		// TODO Auto-generated constructor stub
		cbox.getItems().addAll(listPeriodo);
		cbox.getSelectionModel().selectFirst();

		cbox.setConverter(new StringConverter<CboxData>() {
			@Override
			public CboxData fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String toString(CboxData object) {
				// TODO Auto-generated method stub
				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}
		});

		cbox.valueProperty().addListener(new ChangeListener<CboxData>() {

			@Override
			public void changed(ObservableValue ov, CboxData oldValue, CboxData newValue) {

				switch (newValue.getField()) {
				case "hoje":
					dateInitial.setValue(today);
					dateFinal.setValue(today);

					break;

				case "estaSemana":
					dateFinal.setValue(today);
					dateInitial.setValue(today);
					while (!dateInitial.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY))
						dateInitial.setValue(dateInitial.getValue().minusDays(1));

					break;

				case "semanaAnterior":
					dateFinal.setValue(today);
					while (!dateFinal.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY))
						dateFinal.setValue(dateFinal.getValue().minusDays(1));

					dateFinal.setValue(dateFinal.getValue().minusDays(1));
					dateInitial.setValue(dateFinal.getValue());

					while (!dateInitial.getValue().getDayOfWeek().equals(DayOfWeek.SUNDAY))
						dateInitial.setValue(dateInitial.getValue().minusDays(1));
					break;

				case "esteMes":
					dateInitial.setValue(LocalDate.of(today.getYear(), today.getMonth(), 1));
					dateFinal.setValue(today);
					break;

				case "mesAnterior":
					dateInitial.setValue(today);
					dateInitial.setValue(dateInitial.getValue().minusMonths(1));
					dateInitial.setValue(
							LocalDate.of(dateInitial.getValue().getYear(), dateInitial.getValue().getMonth(), 1));

					dateFinal.setValue(today);
					dateFinal.setValue(dateFinal.getValue().minusMonths(1));
					dateFinal.setValue(LocalDate.of(dateFinal.getValue().getYear(), dateFinal.getValue().getMonth(),
							dateFinal.getValue().lengthOfMonth()));
					break;

				case "esteAno":
					dateInitial.setValue(LocalDate.of(today.getYear(), 1, 1));
					dateFinal.setValue(LocalDate.of(today.getYear(), 12, 31));
					break;

				case "todos":
					dateInitial.setValue(LocalDate.of(0001, 01, 01));
					dateFinal.setValue(LocalDate.of(9999, 12, 31));
					break;

				default:
					break;
				}
			}

		});

		// Date format for DatePicker
		@SuppressWarnings("rawtypes")
		StringConverter converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return today;
				}
			}
		};

		dateInitial.setConverter(converter);
		dateFinal.setConverter(converter);

		// Color of the selected date range in DatePicker
		Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isAfter(dateInitial.getValue()) && item.isBefore(dateFinal.getValue())
								|| item.isEqual(dateInitial.getValue()) || item.isEqual(dateFinal.getValue()))
							setStyle("-fx-background-color: #29B6F6;");
					}
				};
			}
		};

		dateInitial.setDayCellFactory(dayCellFactory);
		dateFinal.setDayCellFactory(dayCellFactory);

	}

	public class CboxData {
		String value;
		String field;

		public CboxData(String value, String field) {
			super();
			this.value = value;
			this.field = field;
		}

		public String getValue() {
			return value;
		}

		public String getField() {
			return field;
		}
	}

}
