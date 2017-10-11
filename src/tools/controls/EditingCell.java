package tools.controls;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Class with methods to modify data in a cell.
 * @author User
 *
 */
public class EditingCell extends TableCell<ColumsTableView, String> {

	private TextField textField;

	public EditingCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			createTextField();
			textField.selectAll();
			setGraphic(textField);
			textField.selectAll();
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();

		setText(getItem());
		setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getString());
				}
				setText(null);
				setGraphic(textField);
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}

	private void createTextField() {
		textField = new TextField(getString());
		textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

		textField.addEventFilter(KeyEvent.KEY_PRESSED, kp -> {
			if (kp.getCode().equals(KeyCode.ENTER)) {
				if (!textField.getText().isEmpty()) {
					if (Integer.parseInt(textField.getText()) < 10) {
						textField.setText("10");
						commitEdit(textField.getText());
					} else
						commitEdit(textField.getText());
				} else
					cancelEdit();
			}

			textField.addEventFilter(KeyEvent.KEY_TYPED, kt -> {
				char c = kt.getCharacter().charAt(0);
				if (!Character.isDigit(c))
					kt.consume();
			});
		});

		textField.textProperty()
				.addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
					if (textField.getText().length() > 3) {
						textField.setText(textField.getText().substring(0, 3));
					}
				});

	}

	private String getString() {
		return getItem() == null ? "" : getItem().toString();
	}
}
