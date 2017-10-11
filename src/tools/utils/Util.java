package tools.utils;

import java.awt.AWTException;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotFactory;
import com.sun.javafx.scene.KeyboardShortcutsHandler;
import com.sun.javafx.scene.control.behavior.TextFieldBehavior;
import com.sun.javafx.scene.control.skin.BehaviorSkinBase;
import com.sun.javafx.scene.control.skin.TextFieldSkin;
import com.sun.javafx.scene.traversal.Direction;
import com.sun.prism.impl.TextureResourcePool;
import com.sun.tools.jxc.gen.config.Classes;

import application.DadosGlobais;
import br.com.samuelweb.nfe.Certificado;
import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.CertificadoUtil;
import controllers.application.LoginController;
import controllers.compras.ComposicaoPrecosController;
import controllers.compras.DepartamentoController;
import controllers.configuracoes.AjustesSistemaController;
import controllers.configuracoes.LayoutsArquivoController;
import controllers.configuracoes.ImportacaoDadosController;
import controllers.configuracoes.SearchLabelController;
import controllers.utils.AlertExceptionController;
import controllers.utils.ConfigColumnController;
import controllers.utils.SearchController;
import controllers.utils.SearchControllerList;
import controllers.utils.SearchControllerMultiselection;
import controllers.utils.ShowAlertController;
import controllers.utils.ComboBoxData.CboxData;
import controllers.utils.TrocaDataSistemaController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import models.compras.Departamento;
import models.compras.Fornecedor;
import models.configuracoes.Compartilhamento;
import models.configuracoes.NivelAcesso;
import models.configuracoes.TabelaAuxiliarCstIcm;
import models.vendas.CidadeDAO;
import tools.controls.ComboBoxFilter;
import tools.criptografia.EncryptMD5;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumNivelAcesso;
import tools.xml.UserDefinedColumn;
import tools.xml.XMLTableColumns;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class with method used in the application.
 * 
 * @author User
 *
 */

public class Util {

	private static List<String> listMenu;
	public static boolean flagTrasnportadora = false;
	public static boolean flagVendedor = false;
	public static boolean flagContasReceber = false;

	public static Integer LOW_SIZE = 1, MEDIUM_SIZE = 2, FULL_SIZE = 3, LOWUP_SIZE = 4;

	public String path = "", firstLevel = "", secondLevel = "", finalLevel = "";

	public String readFileConf(String url) {
		System.out.println(Util.class.getProtectionDomain().getCodeSource().getLocation().toString());
		// String urlFile =
		// Util.class.getProtectionDomain().getCodeSource().getLocation().toString();
		// File f = new File("src\\eptus.conf");
		// File f = new File(urlFile+"eptus.conf");
		// try {
		// System.out.println(f.getCanonicalPath().toString());
		String dir = Util.class.getProtectionDomain().getCodeSource().getLocation().toString();
		System.out.println(dir);
		// } catch (IOException e1) {
		// TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// String idiomaConf[] = null;
		// if (f.exists()) {
		// try {
		//
		// Scanner sc = new Scanner(new FileReader(f));
		//
		// idiomaConf = sc.next().split("=");
		//
		// sc.close();
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// if(idiomaConf!=null)
		// return idiomaConf[1];
		// else
		return "vacio";
	}

	public Parent openWindow(String rota, Object arg) throws IOException {
		FXMLLoader firstLoader = new FXMLLoader(getClass().getResource(rota));
		firstLoader.setController(arg);
		Parent root = firstLoader.load();
		return root;
	}

	public static void closeWindow(AnchorPane pane) {
		Stage stage = (Stage) pane.getScene().getWindow();
		stage.close();
	}

	public void writeFileConf(String idioma) {
		File f = new File("../Eptus/src/eptus.conf");

		if (f.exists()) {
			try {
				FileWriter fw = new FileWriter(f);
				fw.write(idioma);
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Closes the selected tab.
	 * 
	 * @param anPane
	 *            AnchorPane that is inside the tab.
	 */
	public void closeTab(AnchorPane anPane) {

		Scene s = anPane.getScene();
		for (Node node : ((AnchorPane) s.getRoot()).getChildren()) {
			if (node instanceof TabPane) {
				for (int i = 0; i < ((TabPane) node).getTabs().size(); i++) {
					if (((TabPane) node).getTabs().get(i).isSelected())
						((TabPane) node).getTabs().remove(i);
				}
			}
		}
	}

	/**
	 * Method to remove blank spaces to a String
	 * 
	 * @param text
	 *            Text
	 * @return Text Text without spaces
	 */
	@SuppressWarnings("resource")
	public static String removeBlankSpace(String text) {

		// Scanner s = new Scanner(text);
		// String t = null;
		// while (s.hasNext()) {
		// if(t == null)
		// t = s.next();
		// else
		// t = t +" "+s.next();
		// }

		return text.trim().replaceAll("\\s+", " ");

		// return t;
	}

	/**
	 * Method that removes the special characters of a text.
	 * 
	 * @param text
	 *            Text with special characters.
	 * @return Text without special characters.
	 */
	public static String removeSpecialCharacters(String text) {

		String original = "áàäãâéèëêíìïóòöõôúùuñÁÀÄÃÂÉÈËÊÍÌÏÓÒÖÕÔÚÙÜÑçÇ";
		String ascii = "aaaaaeeeeiiiooooouuunAAAAAEEEEIIIOOOOOUUUNcC";

		String newText = text;
		for (int i = 0; i < original.length(); i++) {
			newText = newText.replace(original.charAt(i), ascii.charAt(i));
		}
		return newText;
	}

	/**
	 * Validation to write only numbers.
	 */
	public static void onlyNumbers(TextField... texFiels) {

		for (TextField txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {

				char c = event.getCharacter().charAt(0);
				if (!Character.isDigit(c))
					event.consume();
			});

			txt.textProperty().addListener((ov, oldValue, newValue) -> {

				if (newValue.equals("+1") && txt.isDisable())
					return;

				if (!newValue.matches("\\d*"))
					txt.setText("");
			});
		}
	}

	public static void onlyNumbers(CustomTextField... texFiels) {

		for (CustomTextField txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {

				char c = event.getCharacter().charAt(0);
				if (!Character.isDigit(c))
					event.consume();
			});

			txt.textProperty().addListener((ov, oldValue, newValue) -> {

				if (newValue.equals("+1") && txt.isDisable())
					return;

				if (!newValue.matches("\\d*"))
					txt.setText("");
			});

		}
	}

	/**
	 * Validation different text.
	 */
	public static boolean differentText(int chkTxtVal, CustomTextField... texFiels) {

		boolean result = false;
		String strVal = String.valueOf(chkTxtVal);
		for (TextField txt : texFiels) {
			if (strVal.equals(txt.getText())) {
				result = false;
				break;
			} else
				result = true;
		}

		return result;
	}

	/**
	 * Validation to write only numbers.
	 */
	public static void isLetter(KeyEvent event) {

		char c = event.getCharacter().charAt(0);
		// if (Character.isLetter(c))
		if (!Character.isDigit(c))
			event.consume();

	}

	/**
	 * Validation to write only Letters.
	 */
	public static void onlyLetters(TextField... texFiels) {

		for (TextField txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				char c = event.getCharacter().charAt(0);
				if (Character.isDigit(c))
					event.consume();
			});
		}
	}

	/**
	 * Validation to write only letters.
	 */
	public void isNumber(KeyEvent event) {

		char c = event.getCharacter().charAt(0);
		if (Character.isDigit(c))
			event.consume();

	}

	/**
	 * Validation to write only alphanumeric.
	 */
	public static void onlyAlphanumeric(TextField... texFiels) {

		for (TextField txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {
				char c = event.getCharacter().charAt(0);
				if (!Character.toString(c).matches("[A-Za-z0-9çÇãÃõÕñÑêÊôÔáÁéÉíÍóÓúÚ ]"))
					event.consume();
			});
		}
	}

	/**
	 * Validation to write only alphanumeric.
	 */
	public static void onlyDecimal(TextField... texFiels) {

		for (TextField txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_TYPED, event -> {

				char c = event.getCharacter().charAt(0);
				if (!Character.toString(c).matches("[0-9]?,[0-9]{2}"))
					event.consume();
			});
		}
	}

	/**
	 * Validation to write only alphanumeric.
	 */
	public void onlyAlphanumeric(KeyEvent event) {

		char c = event.getCharacter().charAt(0);
		if (!Character.toString(c).matches("[A-Za-z0-9 ]"))
			event.consume();
	}

	public String stringAlfaNumerica(String texto) {
		String text = texto.replaceAll("[^a-zZ-Z1-9 ]", "");
		return text;
	}

	/**
	 * Limit the number of characters.
	 * 
	 * @param maxLength
	 *            Maximum number of characters.
	 * @param textFields
	 *            TextField to write.
	 */
	public static void maxCharacters(int maxLength, TextField... textFields) {

		for (TextField campo : textFields) {
			campo.textProperty()
			.addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
				if (campo.getText().length() > maxLength) {
					campo.setText(campo.getText().substring(0, maxLength));
				}
			});
		}
	}

	public static void maxCharacters(int maxLength, CustomTextField... textFields) {

		for (CustomTextField campo : textFields) {
			campo.textProperty()
			.addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {
				if (campo.getText().length() > maxLength) {
					campo.setText(campo.getText().substring(0, maxLength));
				}
			});
		}
	}

	/**
	 * Always write in capital letters.
	 * 
	 * @param textFields
	 *            TextField to write.
	 */
	public static void whriteUppercase(TextField... textFields) {

		for (TextField campo : textFields) {
			campo.textProperty().addListener((ov, oldValue, newValue) -> {
				campo.setText(newValue.toUpperCase());
			});
		}
	}

	public static void whriteUppercase(CustomTextField... textFields) {

		for (CustomTextField campo : textFields) {
			campo.textProperty().addListener((ov, oldValue, newValue) -> {
				campo.setText(newValue.toUpperCase());
			});
		}
	}

	/**
	 * Always write in capital letters.
	 * 
	 * @param textFields
	 *            TextField to write.
	 */
	public static void whriteUppercase(TextArea... textFields) {

		for (TextArea campo : textFields) {
			campo.textProperty().addListener((ov, oldValue, newValue) -> {
				campo.setText(newValue.toUpperCase());
			});
		}
	}

	/**
	 * Always write in lowerCase letters
	 * 
	 * @param textFields
	 *            TextField to write.
	 */
	public static void whriteLowercase(TextField... textFields) {
		for (TextField campo : textFields) {
			campo.textProperty().addListener((ov, oldValue, newValue) -> {
				campo.setText(newValue.toLowerCase());
			});
		}
	}

	/**
	 * Set focus to next node
	 */
	@SuppressWarnings({ "restriction", "rawtypes" })
	public static void setNextFocus(TextField... texFiels) {

		for (TextField txt : texFiels) {
			txt.setOnAction(event -> {
				if (txt.getSkin() instanceof BehaviorSkinBase)
					((BehaviorSkinBase) txt.getSkin()).getBehavior().traverseNext();

			});

			// TextFieldSkin skin = (TextFieldSkin) ((TextField) txt).getSkin();
			// skin.getBehavior().traverseNext();
		}
	}

	/**
	 * Set focus to next node
	 */
	@SuppressWarnings({ "restriction", "rawtypes", "unchecked" })
	public static void setNextFocus(ComboBox... texFiels) {

		for (ComboBox txt : texFiels) {
			txt.setOnAction(event -> {
				if (txt.getSkin() instanceof BehaviorSkinBase)
					((BehaviorSkinBase) txt.getSkin()).getBehavior().traverseNext();

			});

			// TextFieldSkin skin = (TextFieldSkin) ((TextField) txt).getSkin();
			// skin.getBehavior().traverseNext();
		}
	}

	/**
	 * Email Validation
	 */
	public static boolean validateEmail(String email) {
		Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
		Matcher m = p.matcher(email);
		// if(m.matches())
		if (m.find() && m.group().equals(email))
			return true;
		else
			return false;
	}

	// ------------------
	public static void mascaraNumeroInteiro(TextField textField) {

		textField.textProperty()
		.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			if (!newValue.matches("\\d*")) {
				textField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

	}

	public static void decimalBR(int qtdCasaDecimal, TextField... campos) {
		for (TextField campo : campos) {
			campo.textProperty()
			.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

				/// ^-?[0-9]{1,3}(?:\.[0-9]{2})?$/
				if (!(newValue.matches("\\d+(\\,?\\d{0," + qtdCasaDecimal + "})?"))
						&& !campo.getText().isEmpty()) {
					// if
					// (!(newValue.matches("\\^-?[0-9]{1,3}(?:\\.[0-9]{2})?"))
					// && !campo.getText().isEmpty()) {
					if (newValue.contains(".")) {
						newValue = newValue.replace(".", ",");
						campo.setText(newValue);
					} else {
						campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
					}
				}
			});
		}
	}

	public static void decimalBRNegativo(int qtdCasaDecimal, TextField... campos) {
		for (TextField campo : campos) {
			campo.textProperty()
			.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
				// ^(-|\d+|-\d+|-\d+(\,\d{0,4})|\d+(\,\d{0,4}))$

				if (!(newValue.matches("^(-|\\d+|-\\d+|-\\d+(\\,\\d{0," + qtdCasaDecimal + "})|\\d+(\\,\\d{0,"
						+ qtdCasaDecimal + "}))$")) && !campo.getText().isEmpty()) {
					if (newValue.contains(".")) {
						newValue = newValue.replace(".", ",");
						campo.setText(newValue);

					} else {

						campo.setText(campo.getText().substring(0, campo.getText().length() - 1));
					}
				}

			});
		}
	}

	public static BigDecimal decimalBRtoBigDecimal(int qtdCasaDecimal, String decimal) {

		BigDecimal valor = new BigDecimal("0.0000");

		if ((decimal.matches("\\d+(\\,?\\d{0," + qtdCasaDecimal + "})?") || (decimal.matches("\\d+"))
				|| (decimal.matches("^(-|\\d+|-\\d+|-\\d+(\\,\\d{0," + qtdCasaDecimal + "})|\\d+(\\,\\d{0,"
						+ qtdCasaDecimal + "}))$")))
				&& !decimal.isEmpty()) {
			if (decimal.contains(",")) {
				decimal = decimal.replace(",", ".");
			}
			try {
				valor = new BigDecimal(decimal).setScale(qtdCasaDecimal, RoundingMode.HALF_EVEN);
			} catch (Exception e) {

				valor = new BigDecimal("0.0000");
			}

		}

		return valor;
	}

	public static void mascaraNumero(TextField... textFields) {

		for (TextField textField : textFields) {
			textField.textProperty()
			.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
				newValue = newValue.replaceAll(",", ".");
				if (newValue.length() > 0) {
					try {
						// Double.parseDouble(newValue);
						textField.setText(newValue.replaceAll(",", "."));
					} catch (Exception e) {
						textField.setText(oldValue);
					}
				}
			});
		}

	}

	public static void mascaraNumeroDecimal(TextField textField) {

		textField.textProperty()
		.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			newValue = newValue.replaceAll(".", ",");
			if (newValue.length() > 0) {
				try {

					textField.setText(newValue.replaceAll(".", ","));
				} catch (Exception e) {
					textField.setText(oldValue);
				}
			}
		});

	}

	public static void mascaraCEP(TextField textField) {

		String val = "";

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 6) {
					textField.setText(textField.getText().substring(0, 5));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 9)
					event.consume();

				if (textField.getText().length() == 5) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraData(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 6) {
					textField.setText(textField.getText().substring(0, 5));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 10)
					event.consume();

				if (textField.getText().length() == 2) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 5) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d/*")) {
				textField.setText(textField.getText().replaceAll("[^\\d/]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraData(DatePicker datePicker) {

		datePicker.getEditor().setOnKeyTyped((KeyEvent event) -> {

			if (event.getCharacter().trim().length() == 0) { // apagando
				if (datePicker.getEditor().getText().length() == 3) {
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 2));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}
				if (datePicker.getEditor().getText().length() == 6) {
					datePicker.getEditor().setText(datePicker.getEditor().getText().substring(0, 5));
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}

			} else { // escrevendo

				if (datePicker.getEditor().getText().length() == 10)
					event.consume();

				if (datePicker.getEditor().getText().length() == 2) {
					datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}
				if (datePicker.getEditor().getText().length() == 5) {
					datePicker.getEditor().setText(datePicker.getEditor().getText() + "/");
					datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
				}

			}
		});

		datePicker.getEditor().setOnKeyReleased((KeyEvent evt) -> {

			if (!datePicker.getEditor().getText().matches("\\d/*")) {
				datePicker.getEditor().setText(datePicker.getEditor().getText().replaceAll("[^\\d/]", ""));
				datePicker.getEditor().positionCaret(datePicker.getEditor().getText().length());
			}
		});

	}

	public static void mascaraEmail(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@"
					.contains(event.getCharacter()) == false) {
				event.consume();
			}

			if ("@".equals(event.getCharacter()) && textField.getText().contains("@")) {
				event.consume();
			}

			if ("@".equals(event.getCharacter()) && textField.getText().length() == 0) {
				event.consume();
			}
		});

	}

	//
	public static void mascaraTelefone(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}
			// (12) 3456-7897
			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 10 && textField.getText().substring(9, 10).equals("-")) {
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 1) {
					textField.setText("");
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 0) {
					textField.setText("(" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ")" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9) != "-") {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 13 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8) + textField.getText().substring(9, 10) + "-"
							+ textField.getText().substring(10, 13) + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}

			}

		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d()-\\s*")) {
				textField.setText(textField.getText().replaceAll("[^\\d()-\\s]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void maskPhone(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {

			if (!"0123456789".contains(event.getCharacter()))
				event.consume();

			if (textField.getText().length() > 14)
				event.consume();

		});
		textField.setOnKeyReleased((KeyEvent event) -> {

			textField.setText(textField.getText().replaceAll("[() -]", ""));

			if (textField.getText().length() >= 10) {
				textField.setText("(" + textField.getText().substring(0, 2) + ") " + textField.getText().substring(2, 6)
						+ "-" + textField.getText().substring(6, textField.getText().length()));
				// textField.positionCaret(textField.getText().length());
				event.consume();
			}

			textField.positionCaret(textField.getText().length());

		});

		textField.textProperty()
		.addListener((final ObservableValue<? extends String> obs, final String old, final String novo) -> {

			if (textField.getText().length() > 14)
				textField.setText("");
		});
	}

	public static void maskTelFixo(TextField textField) {

		textField.setOnKeyTyped((KeyEvent evento) -> {

			if (!"0123456789".contains(evento.getCharacter()))
				evento.consume();

			if (evento.getCharacter().trim().length() == 0) {

				switch (textField.getText().length()) {
				case 9:
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
					break;
				case 3:
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
					break;
				case 1:
					textField.setText("");
					textField.positionCaret(textField.getText().length());
					break;
				default:
					break;
				}

			} else if (textField.getText().length() == 14) {
				evento.consume();
			}

			switch (textField.getText().length()) {
			case 1:
				textField.setText("(" + textField.getText());
				textField.positionCaret(textField.getText().length());
				break;
			case 3:
				textField.setText(textField.getText() + ") ");
				textField.positionCaret(textField.getText().length());
				break;
			case 9:
				textField.setText(textField.getText() + "-");
				textField.positionCaret(textField.getText().length());
				break;
			}

		});

	}

	public static void mascaraCPF(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText().substring(0, 7));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 12) {
					textField.setText(textField.getText().substring(0, 11));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d.-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d.-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	/**
	 * Converter um texto ao modelo CNPJ e limpar caracteres especiais de um
	 * CNPJ
	 * 
	 * @param cadenaTexto
	 * @return
	 */
	public static String getStringConverterCNPJ(String cadenaTexto) {
		char[] caracter = cadenaTexto.toCharArray();
		String textConverted = null;

		if (cadenaTexto.contains(".") && cadenaTexto.contains("/") && cadenaTexto.contains("-")) {

			textConverted = cadenaTexto.replace(".", "").replace("/", "").replace("-", "");

		} else {
			for (int i = 0; i < caracter.length; i++) {
				if (textConverted == null) {
					textConverted = String.valueOf(caracter[i]);
				} else {

					if (i == 2) {
						textConverted = textConverted + ".";
					}
					if (i == 5) {
						textConverted = textConverted + ".";
					}
					if (i == 8) {
						textConverted = textConverted + "/";
					}
					if (i == 12) {
						textConverted = textConverted + "-";
					}
					textConverted = textConverted + String.valueOf(caracter[i]);
				}
			}
		}

		return textConverted;
	}

	public static void mascaraCNPJ(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText().substring(0, 2));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText().substring(0, 6));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText().substring(0, 10));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 16) {
					textField.setText(textField.getText().substring(0, 15));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 18)
					event.consume();

				if (textField.getText().length() == 2) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 6) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 10) {
					textField.setText(textField.getText() + "/");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 15) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d./-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d./-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	/**
	 * Set Formatter to txtField Cep
	 * 
	 * @param event
	 *            Event onKeyRelased
	 * @param txt
	 *            TextField Cep
	 */
	@SuppressWarnings("restriction")
	public static void setFormatCep(KeyEvent event, TextField txt) {
		// 69.005-080
		if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
			txt.setText(txt.getText().replace(".", ""));
			txt.setText(txt.getText().replace("-", ""));
			txt.positionCaret(txt.getText().length());
		}

		if (txt.getText().length() == 8) {
			event.consume();
			txt.setText(txt.getText().substring(0, txt.getText().length() - 6) + "." + txt.getText().substring(2, 5)
					+ "-" + txt.getText().substring(5));

			// txt.positionCaret(txt.getText().length());

			TextFieldSkin skin = (TextFieldSkin) ((TextField) txt).getSkin();
			skin.getBehavior().traverseNext();
		}

	}

	/**
	 * Cep Validation
	 * 
	 * @param textField
	 * @return True is Cep is correct and False if not
	 */
	public static boolean validateCep(TextField textField) {
		// 69.005-080
		Pattern p = Pattern.compile("([0-9]{2}).([0-9]{3})-([0-9]{3})");
		Matcher m = p.matcher(textField.getText());
		if (m.find() && m.group().equals(textField.getText()) || textField.getText().isEmpty())
			return true;
		else {
			textField.setStyle("-fx-border-color: #ff7575;");
			textField.requestFocus();
			return false;

		}

	}

	/**
	 * Set Formatter to txtField Phone
	 * 
	 * @param event
	 *            Event onKeyRelased
	 * @param txt
	 *            TextField Phone
	 */
	@SuppressWarnings("restriction")
	public static void setFormatPhone(KeyEvent event, TextField txt) {
		// (92)3305-3200
		if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
			txt.setText(txt.getText().replace("(", ""));
			txt.setText(txt.getText().replace(")", ""));
			txt.setText(txt.getText().replace("-", ""));
			txt.setText(txt.getText().replace(" ", ""));
			txt.positionCaret(txt.getText().length());

		}

		if (txt.getText().length() == 10) {
			event.consume();
			txt.setText("(" + txt.getText().substring(0, 2) + ") " + txt.getText().substring(2, 6) + "-"
					+ txt.getText().substring(6));
			// txt.positionCaret(txt.getText().length());
			TextFieldSkin skin = (TextFieldSkin) ((TextField) txt).getSkin();
			skin.getBehavior().traverseNext();
		}

	}

	/**
	 * Phone Validation
	 * 
	 * @param textField
	 * @return True is Phone is correct and False if not
	 */
	public static boolean validatePhone(TextField textField) {
		// (92) 3305-3200
		Pattern p = Pattern.compile("^\\([1-9]{2}\\) [1-9][0-9]{3}-[0-9]{4}");
		Matcher m = p.matcher(textField.getText());
		if (m.find() && m.group().equals(textField.getText()) || textField.getText().isEmpty())
			return true;
		else {
			textField.setStyle("-fx-border-color: #ff7575;");
			textField.requestFocus();
			return false;

		}

	}

	/**
	 * Set Formatter to txtField Cel
	 * 
	 * @param event
	 *            Event onKeyRelased
	 * @param txt
	 *            TextField Cel
	 */
	@SuppressWarnings("restriction")
	public static void setFormatCel(KeyEvent event, TextField txt) {
		// (00) 00-0000-000
		if (event.getCode().equals(KeyCode.BACK_SPACE) || event.getCode().equals(KeyCode.DELETE)) {
			txt.setText(txt.getText().replace("(", ""));
			txt.setText(txt.getText().replace(")", ""));
			txt.setText(txt.getText().replace("-", ""));
			txt.setText(txt.getText().replace(" ", ""));
			txt.positionCaret(txt.getText().length());

		}

		if (txt.getText().length() == 11) {
			event.consume();
			txt.setText("(" + txt.getText().substring(0, 2) + ") " + txt.getText().substring(2, 4) + "-"
					+ txt.getText().substring(4, 8) + "-" + txt.getText().substring(txt.getText().length() - 3));
			// txt.positionCaret(txt.getText().length());
			TextFieldSkin skin = (TextFieldSkin) ((TextField) txt).getSkin();
			skin.getBehavior().traverseNext();
		}

	}

	/**
	 * Cel Validation
	 * 
	 * @param textField
	 * @return True is Cel is correct and False if not
	 */
	public static boolean validateCel(TextField textField) {
		// (00) 00-0000-000
		Pattern p = Pattern.compile("^\\([1-9]{2}\\) [1-9][0-9]-[0-9]{4}-[0-9]{3}");
		Matcher m = p.matcher(textField.getText());
		if (m.find() && m.group().equals(textField.getText()) || textField.getText().isEmpty())
			return true;
		else {
			textField.setStyle("-fx-border-color: #ff7575;");
			textField.requestFocus();
			return false;
		}

	}

	/**
	 * Ip Validation
	 * 
	 * @param textField
	 * @return True is Ip is correct and False if not
	 */
	public static boolean validateIp(TextField textField) {
		// 10.2.0.30
		Pattern p = Pattern.compile("[1-9][0-9]{1,2}.[0-9]{1,2,3}.[0-9]{1,2,3}.[0-9]{1,2,3}");
		Matcher m = p.matcher(textField.getText());
		if (m.find() && m.group().equals(textField.getText()) || textField.getText().isEmpty())
			return true;
		else {
			textField.setStyle("-fx-border-color: #ff7575;");
			textField.requestFocus();
			return false;
		}

	}

	/**
	 * Custom TextField with search option.
	 * 
	 * @param txt
	 *            TextField
	 * @param position
	 *            Position left or right.
	 */
	public static Button customSearchTextField(String position, Integer width, CustomTextField... tFields) {
		Button btnSearch = null;
		FontAwesomeIconView icon = null;

		for (CustomTextField txt : tFields) {
			btnSearch = new Button();
			btnSearch.setStyle("-fx-background-color : transparent; -fx-padding : 0;");
			icon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
			icon.setFill(Color.web("#757575"));
			icon.setSize("16px");
			btnSearch.setCursor(Cursor.HAND);
			btnSearch.setGraphic(icon);

			if (position.equals("left"))
				txt.setLeft(btnSearch);
			else
				txt.setRight(btnSearch);

			if (width != null) {
				txt.setPrefWidth(width);
			}

		}
		return btnSearch;
	}

	/**
	 * Custom TextField with load option.
	 * 
	 * @param txt
	 *            TextField
	 * @param position
	 *            Position left or right.
	 */
	public static Button customLoadTextField(String position, Integer width, CustomTextField... tFields) {
		Button btnSearch = null;
		FontAwesomeIconView icon = null;

		for (CustomTextField txt : tFields) {
			btnSearch = new Button();
			btnSearch.setStyle("-fx-background-color : transparent; -fx-padding : 0;");
			icon = new FontAwesomeIconView(FontAwesomeIcon.FOLDER_OPEN);
			icon.setFill(Color.web("#757575"));
			icon.setSize("16px");
			btnSearch.setCursor(Cursor.HAND);
			btnSearch.setGraphic(icon);

			if (position.equals("left"))
				txt.setLeft(btnSearch);
			else
				txt.setRight(btnSearch);

			if (width != null) {
				txt.setPrefWidth(width);
			}

		}
		return btnSearch;
	}

	/**
	 * Custom TextField with search option.
	 * 
	 * @param txt
	 *            TextField
	 * @param position
	 *            Position left or right.
	 */
	public static Button customClearTextField(String position, Integer width, CustomTextField... tFields) {

		for (CustomTextField txt : tFields) {
			Button btnSearch = new Button();
			btnSearch.setStyle("-fx-background-color : transparent; -fx-padding : 0;");
			FontAwesomeIconView icon = new FontAwesomeIconView();
			// icon.setFill(Color.web("#757575"));
			// icon.setSize("16px");
			// btnSearch.setCursor(Cursor.HAND);
			// btnSearch.setGraphic(icon);

			if (position.equals("left"))
				txt.setLeft(btnSearch);
			else
				txt.setRight(btnSearch);
			//
			if (width != null) {
				txt.setPrefWidth(width);
				// icon = new
				// FontAwesomeIconView(FontAwesomeIcon.HAND_ALT_RIGHT);
				// icon.setSize("16px");
				// icon.setFill(Color.BLACK);
				// btnSearch.setGraphic(icon);
			}

			return btnSearch;
		}

		return null;

	}

	/**
	 * Custom TextField with search option.
	 * 
	 * @param txt
	 *            TextField
	 * @param position
	 *            Position left or right.
	 */
	public static Button customEditedTextField(String position, Integer width, CustomTextField... tFields) {

		for (CustomTextField txt : tFields) {
			Button btnSearch = new Button();
			btnSearch.setStyle("-fx-background-color : transparent; -fx-padding : 0;");
			FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BOLT);
			icon.setFill(Color.web("#757575"));
			icon.setSize("16px");
			btnSearch.setGraphic(icon);

			if (position.equals("left"))
				txt.setLeft(icon);
			else
				txt.setRight(icon);
			//
			if (width != null) {
				txt.setPrefWidth(width);

			}
			txt.setEditable(false);
			return btnSearch;
		}

		return null;

	}

	/**
	 * Custom TextField with search option.
	 * 
	 * @param txt
	 *            TextField
	 * @param position
	 *            Position left or right.
	 */
	public static Button customSaveTextField(String position, Integer width, CustomTextField... tFields) {

		for (CustomTextField txt : tFields) {
			Button btnSearch = new Button();
			btnSearch.setStyle("-fx-background-color : transparent; -fx-padding : 0;");
			FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.SAVE);
			icon.setFill(Color.web("#212121"));
			icon.setSize("16px");
			btnSearch.setCursor(Cursor.HAND);
			btnSearch.setGraphic(icon);

			if (position.equals("left"))
				txt.setLeft(btnSearch);
			else
				txt.setRight(btnSearch);

			if (width != null) {
				txt.setPrefWidth(width);
				// icon = new
				// FontAwesomeIconView(FontAwesomeIcon.HAND_ALT_RIGHT);
				// icon.setSize("16px");
				// icon.setFill(Color.BLACK);
				// btnSearch.setGraphic(icon);
			}

			return btnSearch;
		}

		return null;

	}

	public void showSearchView(String title, String selectedItem, Class classDAO,
			ObservableList<ComboBoxFilter> listColumn, TextField codigo, TextField descricao, TextField txtExtra,
			TextField txtFocused) {

		try {
			Stage stg = new Stage();
			Scene scene = new Scene(openWindow("/views/utils/viewSearch.fxml", new SearchController(title, listColumn,
					selectedItem, classDAO, codigo, descricao, txtExtra, txtFocused)));
			scene.getStylesheets()
			.add(getClass().getResource("/styles/css/themes " + DadosGlobais.empresaLogada.getSistema()
			+ "/style_" + DadosGlobais.empresaLogada.getSistema() + ".css").toExternalForm());

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.showAndWait();
		} catch (IOException e) {
			tratamentoExcecao(e.getMessage().toString(), "[ Util.showSearchOption() ]");
		}
	}

	public void showSearchMultiSelectionView(String title, String selectedItem, Class classDAO,
			ObservableList<ComboBoxFilter> listColumn, TextField codigo, ComboBox<ComboBoxFilter> cboxDescriptions) {

		try {
			Stage stg = new Stage();
			Scene scene = new Scene(openWindow("/views/utils/viewSearch.fxml", new SearchControllerMultiselection(title,
					listColumn, selectedItem, classDAO, codigo, cboxDescriptions)));
			scene.getStylesheets()
			.add(getClass().getResource("/styles/css/themes " + DadosGlobais.empresaLogada.getSistema()
			+ "/style_" + DadosGlobais.empresaLogada.getSistema() + ".css").toExternalForm());

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.showAndWait();
		} catch (IOException e) {
			tratamentoExcecao(e.getMessage().toString(), "[ Util.showSearchOption() ]");
		}
	}

	public Object showSearchGetParameters(String title, String selectedItem, Class classDAO,
			ObservableList<ComboBoxFilter> listColumn) {

		try {
			Stage stg = new Stage();
			Scene scene = new Scene(openWindow("/views/utils/viewSearch.fxml",
					new SearchControllerList(title, listColumn, selectedItem, classDAO)));
			scene.getStylesheets()
			.add(getClass().getResource("/styles/css/themes " + DadosGlobais.empresaLogada.getSistema()
			+ "/style_" + DadosGlobais.empresaLogada.getSistema() + ".css").toExternalForm());

			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.showAndWait();
		} catch (IOException e) {
			tratamentoExcecao(e.getMessage().toString(), "[ Util.showSearchOption() ]");
		}
		return SearchControllerList.obj;
	}

	/**
	 * Notificação de sucessos
	 * 
	 * @param mensagem
	 *            Mensagem a mostrars
	 */
	public static void showNotification(String mensagem) {

		FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE_ALT);
		icon.setFill(Color.web("#4CAF50"));
		icon.setSize("40px");

		Notifications notification = Notifications.create().title("Sucesso").graphic(icon).text(mensagem)
				.hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_RIGHT);

		notification.showInformation();

	}

	/**
	 * Method that show alerts
	 * 
	 * @param icons
	 *            Icon of the alert.
	 * @param title
	 *            Alert title.
	 * @param info
	 *            Message alert.
	 */
	public boolean showAlert(String message, String alertType) {
		Stage stg = new Stage();
		try {
			Scene scene = new Scene(
					openWindow("/views/utils/viewShowAlert.fxml", new ShowAlertController(message, alertType)));
			stg.setScene(scene);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
			// tratamentoExcecao(e.getMessage().toString(), "[
			// DepartamentoController.actionBtnConfig() ]");
		}
		return ShowAlertController.action;
	}

	public boolean alertConfirmar(String info) {
		boolean confirm = false;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmacion");
		alert.setHeaderText(null);
		alert.setContentText(info);

		Optional<ButtonType> result = alert.showAndWait();
		if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
			confirm = true;
		}
		return confirm;
	}

	public boolean alertConfirmation(String info) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(info);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/styles/img/favicon.ico").toString()));

		ButtonType btnOk = new ButtonType(DadosGlobais.resourceBundle.getString("btnAlertYes"), ButtonData.OK_DONE);
		ButtonType btnCancel = new ButtonType(DadosGlobais.resourceBundle.getString("btnAlertNo"),
				ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(btnOk, btnCancel);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == btnOk)
			return true;
		else
			return false;

	}

	public boolean alertWarningConfirma(String info) {

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText(info);

		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(this.getClass().getResource("/styles/img/favicon.ico").toString()));

		ButtonType btnOk = new ButtonType(DadosGlobais.resourceBundle.getString("btnAlertYes"), ButtonData.OK_DONE);
		ButtonType btnCancel = new ButtonType(DadosGlobais.resourceBundle.getString("btnAlertNo"),
				ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(btnOk, btnCancel);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == btnOk)
			return true;
		else
			return false;

	}

	public static void alertInf(String info) {

		Alert alert = new javafx.scene.control.Alert(AlertType.INFORMATION);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/styles/img/favicon.ico"));
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(info);
		alert.showAndWait();
	}

	public void showSearchLabelInf(String path, TabPane tabPane, String searchedWord) {
		try {
			Stage stg = new Stage();
			Scene scene = new Scene(openWindow("/views/configuracoes/viewSearchLabel.fxml",
					new SearchLabelController(path, tabPane, searchedWord)));

			stg.setScene(scene);

			stg.setHeight(600);
			stg.setWidth(800);

			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			tratamentoExcecao(e.getMessage().toString(), "[ SearchLabelController.SearchLabelController() ]");
		}
	}

	/*
	 * public void showImportacaoDados(TabPane tabPane) { try { Stage stg = new
	 * Stage(); Scene scene = new
	 * Scene(openWindow("/views/configuracoes/viewImportacaoDados.fxml", new
	 * ImportacaoDadosController(tabPane)));
	 * 
	 * stg.setScene(scene);
	 * 
	 * stg.setHeight(600); stg.setWidth(800);
	 * 
	 * stg.initStyle(StageStyle.TRANSPARENT);
	 * stg.initModality(Modality.APPLICATION_MODAL); stg.show(); } catch
	 * (IOException e) { tratamentoExcecao(e.getMessage().toString(),
	 * "[ SearchLabelController.SearchLabelController() ]"); } }
	 */

	public void showLayoutsArquivo() {

		try {
			Stage stg = new Stage();
			Scene scene = new Scene(openWindow("/views/configuracoes/viewLayoutsArquivo.fxml", null));

			stg.setScene(scene);

			stg.setHeight(600);
			stg.setWidth(800);

			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException e) {
			tratamentoExcecao(e.getMessage().toString(), "[ showDLayoutsArquivo.showDLayoutsArquivoController() ]");
		}
	}

	public static void alertWarn(String info) {

		Alert alert = new javafx.scene.control.Alert(AlertType.WARNING);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/styles/img/favicon.ico"));
		alert.setTitle("Warning Dialog");
		alert.setHeaderText(null);
		alert.setContentText(info);
		alert.showAndWait();
	}

	public static void alertError(String info) {

		Alert alert = new javafx.scene.control.Alert(AlertType.ERROR);
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("/styles/img/favicon.ico"));
		alert.setTitle("Error Dialog");
		alert.setHeaderText(null);
		alert.setContentText(info);
		alert.showAndWait();

	}

	/**
	 * Validate empty fields.
	 * 
	 * @param textField
	 *            TextField to validate.
	 */
	public static boolean noEmpty(TextField... textField) 
	{
		boolean vazio = false;

		for (TextField campo : textField) 
		{
			if (campo.getText().trim().isEmpty()) 
			{
				campo.setStyle("-fx-border-color: #ff7575;");
				campo.requestFocus();
				//campo.getScene().getFocusOwner().requestFocus();
				return true;
			}
		}

		return vazio;
	}


	/**
	 * Validate empty fields.
	 * 
	 * @param textField
	 *            TextField to validate.
	 */
	public static boolean noEmpty(CheckComboBox<ComboBoxFilter>... textField) {
		boolean vazio = false;
		for (CheckComboBox<ComboBoxFilter> campo : textField) {
			if (campo.getCheckModel().getCheckedItems().isEmpty()) {
				campo.setStyle("-fx-border-color: #ff7575;");
				campo.requestFocus();
				return true;
			}
		}

		return vazio;
	}


	/**
	 * Validate empty fields.
	 * 
	 * @param textField
	 *            TextField to validate.
	 */
	public static boolean noEmpty(String lbl, TextField... textField ) 
	{
		boolean vazio = false;

		for (TextField campo : textField) 
		{
			if (campo.getText().trim().isEmpty()) 
			{
				campo.setStyle("-fx-border-color: #ff7575;");
				campo.requestFocus();
				lbl = campo.getPromptText();
				vazio = true;	
				break;
			}
		}

		return vazio;
	}


	public static Tab hazTab( ObservableList<?> nodes, TextField txtToSearch )
	{		
		Tab returnTab = null;

		for ( int itNode = 0; itNode < nodes.size(); itNode++ ) 
		{
			Object objList = nodes.get(itNode);
			//Class<?> clase = objList.getClass(); 
			//String className = clase.getName();					 
			if ( objList instanceof Tab ) 
			{	 
				Tab objTab = (Tab)objList;  		
				AnchorPane ap = (AnchorPane) objTab.getContent();
				ObservableList<?> lst = ap.getChildren(); 		   					   
				if ( isInsideTab( lst, txtToSearch ) )
				{
					returnTab = objTab;
					break;	
				}     
				else{}

			}    
			else if ( objList instanceof TabPane )
			{	 					 
				TabPane parentObj = ((TabPane) objList); 	
				ObservableList<?> lst = parentObj.getTabs();
				returnTab = hazTab(lst, txtToSearch);
				if( returnTab != null )
					break;
				else{}
			} 
			/*else if ( className.contains("javafx.scene.layout") )
				 {	 					 
					       Parent parentObj = ((Parent) objList); 					       
						   ObservableList<?> lst = parentObj.getChildrenUnmodifiable();
						   hazTab(lst, txtToSearch);
				 }*/
			else{}

		}

		return returnTab;		
	}			

	public static boolean isInsideTab( ObservableList<?> nodes, TextField txtToSearch )
	{		
		boolean txtFieldsMatch = false;

		for ( int itNode = 0; itNode < nodes.size(); itNode++ ) 
		{
			Object objList = nodes.get(itNode);
			Class<?> clase = objList.getClass(); 
			String className = clase.getName();

			if ( objList instanceof TextField ) 
			{
				TextField instanceTextField = (TextField)objList;
				if ( instanceTextField == txtToSearch )
				{	  
					txtFieldsMatch = true;
					break;
				}		   
				else{}
			}
			else if ( className.contains("javafx.scene.layout") )
			{	 					 
				Parent parentObj = ((Parent) objList); 					       
				ObservableList<?> lst = parentObj.getChildrenUnmodifiable();							   

				if( lst.size() > 0 ) 
					txtFieldsMatch = isInsideTab(lst, txtToSearch);	
				else{}
			}
			else{}

		}

		return txtFieldsMatch;		
	}		

	/**
	 * Validate empty fields.
	 * 
	 * @param textField
	 *            TextField to validate.
	 */
	public static boolean noEmpty(Label lblError, AnchorPane apContainer, TextField... textField) 
	{
		boolean vazio = false;
		for (TextField campo : textField) 
		{
			if ( campo.getText().trim().isEmpty() ) 
			{	

				ObservableList<?> nodes = apContainer.getChildren(); 
				Tab tabParent = hazTab(nodes, campo);

				if( tabParent != null )
				{	
					TabPane tabPaneParent = tabParent.getTabPane();
					tabPaneParent.getSelectionModel().select(tabParent);
					tabPaneParent.requestFocus();
				}	
				else{}

				lblError.setText(DadosGlobais.resourceBundle.getString("utilController.lblError"));

				campo.setStyle("-fx-border-color: #ff7575;");						
				campo.requestFocus();
				return true;
			}
		}	   		

		return vazio;		
	}

	/**
	 * TESTA SE O CAMPO ESTA VAZIO E ATRIBUI VALORES VAZIOS DE ACORDO COM O TIPO
	 * DE DADO
	 * 
	 * @param tipoValorvazio
	 *            (str - String, int - Integer, dec - Decimal)
	 * @param textField
	 * 
	 */
	public static String textfieldNotNull(String tipoValorVazio, TextField campo) {
		String conteudo = campo.getText();

		if (campo.getText().trim().isEmpty()) {
			if (tipoValorVazio.equals("str"))
				conteudo = "";
			else if (tipoValorVazio.equals("int"))
				conteudo = "0";
			else if (tipoValorVazio.equals("dec"))
				conteudo = "0,0000";
		}

		return conteudo;
	}

	public static String textfieldNotNull(String tipoValorVazio, CustomTextField campo) {
		String conteudo = campo.getText();

		if (campo.getText().trim().isEmpty()) {
			if (tipoValorVazio.equals("str"))
				conteudo = "";
			else if (tipoValorVazio.equals("int"))
				conteudo = "0";
			else if (tipoValorVazio.equals("dec"))
				conteudo = "0,0000";
		}

		return conteudo;
	}

	public static String textfieldNotNull(String tipoValorVazio, TextArea campo) {
		String conteudo = campo.getText();

		if (campo.getText().trim().isEmpty()) {
			if (tipoValorVazio.equals("str"))
				conteudo = "";
			else if (tipoValorVazio.equals("int"))
				conteudo = "0";
			else if (tipoValorVazio.equals("dec"))
				conteudo = "0,0000";
		}

		return conteudo;
	}

	/**
	 * Metoth
	 * 
	 * @param date
	 */
	public static void disableDatePicker(DatePicker... datePickers) {
		for(DatePicker datePicker : datePickers){
			datePicker.setOnMouseClicked(e -> {
				if (!datePicker.isEditable())
					datePicker.hide();
			});	
		}
		

	}

	/**
	 * METODO PARA HABILITAR O BUTTON DENTRO DO CUSTOMTEXTFIELD DEPENDENDO DO STATUS EDITABLE
	 * @param txtFields
	 */
	public static void disableButtonCustomTextField(CustomTextField... txtFields){		
		for(CustomTextField txtField : txtFields){
			if(!txtField.isEditable()){					
				txtField.setDisable(true);
				((Button)txtField.getRight()).setDisable(true);				
			}else{				
				txtField.setDisable(false);
				((Button)txtField.getRight()).setDisable(false);	
			}
		}
	}

	/**
	 * Validate empty fields.
	 * 
	 * @param textField
	 *            TextField to validate.
	 */
	public static void setStyleError(boolean flagRequestFocus, TextField... textField) {

		for (TextField campo : textField) {

			campo.setStyle("-fx-border-color: #ff7575;");
			if (flagRequestFocus)
				campo.requestFocus();

		}

	}
	// /**
	// * Validate empty fields.
	// *
	// * @param textField
	// * TextField to validate.
	// */
	// public boolean noEmpty(PasswordField... pwdTextField) {
	//
	// boolean vazio = false;
	//
	// for (PasswordField campo : pwdTextField) {
	// if (campo.getText().trim().isEmpty()) {
	// campo.setStyle("-fx-border-color: #ff7575;");
	// campo.requestFocus();
	// return true;
	// }
	// }
	//
	// return vazio;
	// }

	/**
	 * Validate empty fields.
	 * 
	 * @param comboBox
	 *            ComboBox to validate.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean noEmpty(ComboBox<?>... comboBox) {

		boolean vazio = false;
		for (ComboBox campo : comboBox) {
			if (campo.getEditor().getText().isEmpty()) {
				campo.setStyle("-fx-border-color: #ff7575;");
				return true;
			}
		}
		return vazio;
	}

	/**
	 * Validate empty fields.
	 * 
	 * @param comboBox
	 *            ComboBox to validate.
	 */
	@SuppressWarnings("rawtypes")
	public static boolean noEmptyCboxFilter(ComboBox<ComboBoxFilter>... comboBox) {

		boolean vazio = false;
		for (ComboBox<ComboBoxFilter> campo : comboBox) {
			if (campo.getValue() == null) {
				campo.setStyle("-fx-border-color: #ff7575;");
				return true;
			}
		}
		return vazio;
	}

	/**
	 * Ao clicar no campo voltar ao estilo padrão do campo onClicked
	 */
	public static void defaultStyle(Node... no) {
		for (Node n : no) {
			n.setOnMouseClicked((MouseEvent me) -> {
				if (!n.getStyle().equals("-fx-border-color: none;")) {
					n.setStyle("-fx-border-color: none;");
				}
			});
		}
	}

	public void defaultStyleOnKeyReleassed(Node... no) {
		for (Node n : no) {
			n.setOnMouseClicked((MouseEvent me) -> {
				if (!n.getStyle().equals("-fx-border-color: none;")) {
					n.setStyle("-fx-border-color: none;");
				}
			});
		}
	}

	/**
	 * Define os valor de cada radioButton de um grupo de radiobuttons, o valor
	 * é sempre o ultimo caracter do nome do elemento JAVAFX ex: rdbDefNivSim1 o
	 * 1 vai ser o valor do radio selecionado
	 */
	public static void defineRadioButton(ToggleGroup... no) {
		for (ToggleGroup n : no) {

			for (int i = 0; i < n.getToggles().size(); i++) {

				((RadioButton) n.getToggles().get(i)).setUserData(((RadioButton) n.getToggles().get(i)).getId()
						.substring(((RadioButton) n.getToggles().get(i)).getId().length() - 1));

			}
		}
	}

	/**
	 * Seta o radio button selecionado de acordo com o valor passado
	 */
	public static void setSelectRadioButton(Integer valorSelect, ToggleGroup... no) {
		for (ToggleGroup n : no) {

			for (int i = 0; i < n.getToggles().size(); i++) {

				if (n.getToggles().get(i).getUserData().toString().equals(valorSelect.toString())) {

					n.getToggles().get(i).setSelected(true);

				}

			}
		}
	}

	/**
	 * Set style TabPane, set title and tab horizontal
	 * 
	 * @param titelSystem
	 *            True if the title of tab is defined by system and False if
	 *            defined by user.
	 * @param title
	 *            Array with tab titles
	 * @param tabPanes
	 *            TabPanes
	 */
	public static void setStyleTab(TabPane... tabPanes) {
		int count = 1;

		Label l = null;
		FontAwesomeIconView icon;

		for (TabPane tabPane : tabPanes) {
			for (Tab tab : tabPane.getTabs()) {

				l = new Label("  " + DadosGlobais.resourceBundle.getString("tabOpcoes") + " " + count + "   ");
				l.setId("lblTab");
				// icon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
				// l.setGraphic(icon);

				StackPane stp = new StackPane(new Group(l));
				stp.setId("stpTab");
				tab.setGraphic(stp);
				count++;
			}
			count = 1;
		}

	}

	/**
	 * Set default style no evento Keypresses
	 */
	public static void setKeyPressDefaultStyles(TextField... texFiels) {
		for (TextField txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				if (!txt.getStyle().equals("-fx-border-color: none;")) {
					txt.setStyle("-fx-border-color: #21a9d7;");
				}
			});
		}
	}

	/**
	 * Set default style no evento Keypresses
	 */
	public static void setKeyPressDefaultStyles(CustomTextField... texFiels) {
		for (TextField txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				if (!txt.getStyle().equals("-fx-border-color: none;")) {
					txt.setStyle("-fx-border-color: #21a9d7;");
				}
			});
		}
	}

	/**
	 * Set default style no evento Keypresses
	 */
	public static void setKeyPressDefaultStyles(TextArea... texFiels) {
		for (TextArea txt : texFiels) {
			txt.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				if (!txt.getStyle().equals("-fx-border-color: none;")) {
					txt.setStyle("-fx-border-color: none;");
				}
			});
		}
	}

	/**
	 * Set default style no evento MouseClick
	 */
	public static void setMouseClickDefaultStyles(ComboBox... combo) {
		for (ComboBox txt : combo) {
			txt.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
				if (!txt.getStyle().equals("-fx-border-color: none;")) {
					txt.setStyle("-fx-border-color: none;");
				}
			});
		}
	}

	/**
	 * Set default style no evento MouseClick
	 */
	public static void setMouseClickDefaultStyles(CustomTextField... fields) {
		for (CustomTextField txt : fields) {
			txt.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
				if (!txt.getStyle().equals("-fx-border-color: none;")) {
					txt.setStyle("-fx-border-color: none;");
				}
			});
		}
	}

	/**
	 * Set default style no evento onFocus
	 */
	public static void setStyleOnFocus(TextField... fields) {
		for (TextField txt : fields) {

			txt.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					if (newValue) {
						if (!txt.getStyle().equals("-fx-border-color: #ff7575;"))
							txt.setStyle("-fx-border-color: #21a9d7;");
						else if (txt.getStyle().equals("-fx-border-color: #ff7575;"))
							txt.setStyle("-fx-border-color: #ff7575;");
					} else
						txt.setStyle("-fx-border-color: none;");

				}
			});

		}
	}

	/**
	 * Set default style no evento onFocus
	 */
	public static void setStyleOnFocus(CustomTextField... fields) {
		for (TextField txt : fields) {

			txt.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					if (newValue) {
						if (!txt.getStyle().equals("-fx-border-color: #ff7575;"))
							txt.setStyle("-fx-border-color: #21a9d7;");
						else if (txt.getStyle().equals("-fx-border-color: #ff7575;"))
							txt.setStyle("-fx-border-color: #ff7575;");
					} else
						txt.setStyle("-fx-border-color: none;");

				}
			});

		}
	}

	public static void actionAll(Class clase) {

		Field[] nodes;
		nodes = clase.getDeclaredFields();
		//

		for (Field n : nodes) {
			if (n.getType().getSimpleName().equals("TextField")) {
				// Object object = this;
				// TextField textField = (TextField) n.get(object);
				// ((TextField) n).setText("Text");
				System.out.println(n.getType().getSimpleName());
				// ((TextField) n).setText("virus");
				//
				// ((TextField)((Node) ((Object) n))).setText("virus");

				// System.out.println(n.getName());

			}

		}

		// Field[] nodes;
		// nodes = ClienteController.class.getDeclaredFields();
		// Object object = this;
		//
		// for (Field n : nodes) {
		// if (n.getType().getSimpleName().equals("TextField")) {
		//
		// TextField textField = (TextField) n.get(object);
		//
		// if (textField != null) {
		// textField.setText("Hello World");
		// System.out.println(textField.getId());
		// }
		//
		// }
		//
		// }

	}

	/**
	 * Campo voltar ao estilo padrão do campo
	 */
	public static void setDefaultStyle(Node... no) {
		for (Node n : no) {
			if (!n.getStyle().equals("-fx-border-color: none;")) {
				n.setStyle("-fx-border-color: none;");
			}
		}
	}

	/**
	 * Clear field values.
	 * 
	 * @param no
	 *            TextField to clean.
	 */
	public static void limpar(TextField... no) {
		for (TextField campo : no) {
			campo.clear();
		}
	}

	/**
	 * Clear field values.
	 * 
	 * @param no
	 *            CustomTextField to clean.
	 */
	public static void limpar(CustomTextField... no) {
		for (CustomTextField campo : no) {
			campo.clear();
		}
	}

	/**
	 * Clear field values.
	 * 
	 * @param no
	 *            Label to clean.
	 */
	public static void limpar(Label... no) {
		for (Label campo : no) {
			campo.setText("");
		}
	}

	/**
	 * Clear field values.
	 * 
	 * @param no
	 *            TextArea to clean.
	 */
	public static void limpar(TextArea... no) {
		for (TextArea campo : no) {
			campo.clear();
			;
		}
	}

	/**
	 * Funcao para fomatar a data , passar uma data e o formato ex: "dd/MM/yyyy"
	 * 
	 * @param Data
	 *            para ser formatada.
	 * @param formato
	 *            da data.
	 */
	public static String formataData(Date data, String formato) {

		SimpleDateFormat formatador = new SimpleDateFormat(formato);

		return formatador.format(data);
	}

	/**
	 * Funcao para fomatar a data , passar uma data e o formato ex: "dd/MM/yyyy"
	 * 
	 * @param Data
	 *            para ser formatada.
	 * @param formato
	 *            da data.
	 */
	public static String formataDataString(LocalDateTime data) {

		DateTimeFormatter formatador = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
				.withLocale(new Locale(DadosGlobais.idioma, DadosGlobais.pais));

		return data.format(formatador);
	}

	public static String formataDataHoraString(LocalDateTime data) {

		DateTimeFormatter formatador = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
				.withLocale(new Locale(DadosGlobais.idioma, DadosGlobais.pais));

		return data.format(formatador);
	}

	/**
	 * Função para formatar a data, passar uma data e o formato dela e o formato
	 * de saída da mesma
	 * 
	 * @param data
	 *            Data que vai ser formatada
	 * @param formatIn
	 *            formato da data entrada
	 * @param formatOut
	 *            formato saída da data entrada
	 * @return
	 */
	public static String dateFormatInToOut(String date, String formatIn, String formatOut) {
		Date dataFormatIn;
		String dataFormatOut = null;
		try {
			dataFormatIn = new SimpleDateFormat(formatIn).parse(date);
			dataFormatOut = new SimpleDateFormat(formatOut).format(dataFormatIn);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataFormatOut;
	}

	/**
	 * FORMATO DE DATA exemplo: 2011-12-03T10:15:30+01:00
	 * 
	 * @param localDate
	 * @return
	 */
	public static String formatterDateHoraZone(LocalDateTime localDate) {
		return LocalDateTime
				.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth(), localDate.getHour(),
						localDate.getMinute(), localDate.getSecond())
				.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	public static LocalDateTime geraDataMovto() {

		LocalDateTime dt = DadosGlobais.dataMovtoSistema;

		LocalDateTime dataMovto = LocalDateTime.of(dt.getYear(), dt.getMonth(), dt.getDayOfMonth(),
				LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());

		return dataMovto;
	}

	public static LocalDateTime geraDataMovto(LocalDateTime data) {

		LocalDateTime dt = DadosGlobais.dataMovtoSistema;

		LocalDateTime dataMovto = LocalDateTime.of(dt.getYear(), dt.getMonth(), dt.getDayOfMonth(), data.getHour(),
				data.getMinute(), data.getSecond());

		return dataMovto;
	}

	public static LocalDateTime dateToLocalDateTime(Date data) {

		Instant instants = Instant.ofEpochMilli(data.getTime());

		return LocalDateTime.ofInstant(instants, DadosGlobais.fusoHorarioDefault);

	}

	/**
	 * Funcao para gerar data atual
	 */
	public static Date geraDataAtual() {
		Date data = new Date();
		return data;
	}

	public static List<String> getIdMenu(ObservableList<?> menuItems) {
		try {
			Util.class.newInstance().menu(menuItems);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listMenu;
	}

	/**
	 * Method to resize an Image
	 * 
	 * @param file
	 *            Image to modify
	 * @param url
	 *            Path of the file to save
	 * @return New Image
	 * @throws IOException
	 */
	public static Image resizeImages(File file, String url) throws IOException {

		BufferedImage imagem = null;
		Graphics2D g = null;
		File f = new File(url);

		try {
			imagem = ImageIO.read(file);

			int new_w = 140, new_h = 105;
			BufferedImage new_img = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			g = new_img.createGraphics();
			g.drawImage(imagem, 0, 0, new_w, new_h, null);

			ImageIO.write(new_img, "png", f);

		} catch (IOException ex) {
			System.out.println(ex);

		}

		return new Image(f.toURI().toString());

	}

	/**
	 * Method to save an image from ImageView
	 * 
	 * @param img
	 *            Image to be save
	 */
	public static void saveImageToFile(Image img, String path) {

		File outputFile = new File(path);
		BufferedImage bImage = SwingFXUtils.fromFXImage(img, null);

		try {
			ImageIO.write(bImage, "png", outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to delete file
	 * 
	 * @param path
	 *            Path of the file to be delete
	 */
	public static void deleteFile(String path) {

		File f = new File(path);
		if (f.exists())
			f.delete();

	}

	/**
	 * Set focus to Node
	 * 
	 * @param node
	 *            Control to set focus.
	 */
	public static void setFocus(Node node) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				node.requestFocus();
			}
		});

	}

	public void menu(ObservableList<?> menuItems) {
		if (listMenu == null) {
			listMenu = new ArrayList<String>();
		}

		for (int i = 0; i < menuItems.size(); i++) {
			if (menuItems.get(i) instanceof Menu) {
				// System.out.println("------------**********-"+((Menu)m.get(i)).getText()+"-*********--------------");
				// menuItem(((Menu)m.get(i)).getItems());
				menu(((Menu) menuItems.get(i)).getItems());
				listMenu.add(((Menu) menuItems.get(i)).getId());
				// }else if(((MenuItem)m.get(i)) instanceof MenuItem){
			} else {

				listMenu.add(((MenuItem) menuItems.get(i)).getId());
				// System.out.println(((MenuItem)m.get(i)).getText());
			}
			// lista.add(lm);

		}
		// return listMenu;
	}

	/**
	 * Método para evitar a repetição de aba.
	 * 
	 * @param nomeObjeto
	 *            nome aba
	 * @throws IOException
	 */
	public void crearTab(TabPane tabPanePrincipal, String nomeObjeto, String dir, Object arg, boolean flagSelecionaAba)
			throws IOException {

		boolean existe = false;

		Tab tab;

		int position = 0;

		for (int i = 0; i < tabPanePrincipal.getTabs().size(); i++) {
			if (nomeObjeto.equals(tabPanePrincipal.getTabs().get(i).getText())) {
				existe = true;
				position = i;
				break;
			}
		}
		if (existe) {
			if (!flagSelecionaAba) {

				alertInf("Janela " + nomeObjeto + " já está Aberta!");

			} else {
				tabPanePrincipal.getSelectionModel().select(position);
			}

		} else {
			tab = new Tab(nomeObjeto);
			tab.setClosable(true);
			FXMLLoader vista = new FXMLLoader(getClass().getResource(dir));
			vista.setController(arg);
			tab.setContent(vista.load());
			tabPanePrincipal.getTabs().add(tab);
			tabPanePrincipal.getSelectionModel().select(tab);

		}
	}

	public void fecharTabsAbertas(TabPane tabPane) {

		for (int i = tabPane.getTabs().size() - 1; i > 0; i--) {
			tabPane.getTabs().remove(i);
		}

	}

	public boolean verificaTabsAbertas(TabPane tabPane) {

		if (tabPane.getTabs().size() > 1) {
			return false;
		} else {
			return true;
		}
	}

	public void permissoesMenuPrincipal(ObservableList<?> observableList, List<NivelAcesso> permissao)
			throws InstantiationException, IllegalAccessException {

		boolean existe = false;

		for (int i = 0; i < observableList.size(); i++) {
			// INICIO DE MENU
			if (observableList.get(i) instanceof Menu) {
				String cleanIdMenu = ((Menu) observableList.get(i)).getId().subSequence(0, 5).toString()
						+ ((Menu) observableList.get(i)).getId().subSequence(9,
								((Menu) observableList.get(i)).getId().toCharArray().length);
				// ULTIMATE
				if (DadosGlobais.sistema.equals("Ultimate")
						&& ((Menu) observableList.get(i)).getId().charAt(7) == '1') {
					// lblTitle.setText("Eptus@M - Ultimate");
					for (NivelAcesso nivAc : permissao) {//
						if (EncryptMD5.getMD5(cleanIdMenu).equals(nivAc.getMenu())) {
							if (((Menu) observableList.get(i)).getId().subSequence(2, 5).equals("Hea")) {
								((Menu) observableList.get(i)).setDisable((nivAc.getFlagView() == 1) ? false : true);
							} else {
								((Menu) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ? true : false);
							}
							existe = true;
							break;
						} else
							existe = false;
					}

					// No caso do menu seja novo e nao fique no banco de dados
					// vai ficar desativado
					if (!existe) {
						((Menu) observableList.get(i)).setDisable(true);
						existe = false;

					}
				}

				// PORFESSIONAL
				else if (DadosGlobais.sistema.equals("Professional")
						&& ((Menu) observableList.get(i)).getId().charAt(6) == '1') {
					// lblTitle.setText("Eptus@M - Professional");
					for (NivelAcesso nivAc : permissao) {
						if (EncryptMD5.getMD5(cleanIdMenu).equals(nivAc.getMenu())) {
							if (((Menu) observableList.get(i)).getId().subSequence(2, 5).equals("Hea")) {

								((Menu) observableList.get(i)).setDisable((nivAc.getFlagView() == 1) ? false : true);
							} else {
								((Menu) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ? true : false);
							}
							existe = true;
							break;
						} else
							existe = false;
					}
					if (!existe) {
						((Menu) observableList.get(i)).setDisable(true);
						existe = false;
					}
				}

				// ENTERPRISE
				else if (DadosGlobais.sistema.equals("Enterprise")
						&& ((Menu) observableList.get(i)).getId().charAt(8) == '1') {
					// lblTitle.setText("Eptus@M - Enterprise");
					for (NivelAcesso nivAc : permissao) {
						if (EncryptMD5.getMD5(cleanIdMenu).equals(nivAc.getMenu())) {
							if (((Menu) observableList.get(i)).getId().subSequence(2, 5).equals("Hea")) {

								((Menu) observableList.get(i)).setDisable((nivAc.getFlagView() == 1) ? false : true);
							} else {
								((Menu) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ? true : false);
							}
							existe = true;
							break;
						} else
							existe = false;
					}
					if (!existe) {
						((Menu) observableList.get(i)).setDisable(true);
						existe = false;

					}
				} else
					((Menu) observableList.get(i)).setVisible(false);

				permissoesMenuPrincipal(((Menu) observableList.get(i)).getItems(), permissao);

			} else {

				// ----------------------- INICIO DE MENUITEM
				// ----------------------------------
				String cleanIdMenuItem = ((MenuItem) observableList.get(i)).getId().subSequence(0, 5).toString()
						+ ((MenuItem) observableList.get(i)).getId().subSequence(9,
								((MenuItem) observableList.get(i)).getId().toCharArray().length);
				for (NivelAcesso nivAc : permissao) {
					if (EncryptMD5.getMD5(cleanIdMenuItem).equals(nivAc.getMenu())) {
						((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagAtivo() == 1) ? true : false);
						break;
					}

				}

				if (DadosGlobais.sistema.equals("Professional")
						&& ((MenuItem) observableList.get(i)).getId().charAt(6) == '1') {
					for (NivelAcesso nivAc : permissao) {
						if (EncryptMD5.getMD5(cleanIdMenuItem).equals(nivAc.getMenu())) {
							((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ? true : false);
							existe = true;
							((MenuItem) observableList.get(i)).setDisable(false);
							break;
						} else
							existe = false;
					}

					if (!existe) {
						((MenuItem) observableList.get(i)).setDisable(true);
						existe = false;

					}
				} else if (DadosGlobais.sistema.equals("Ultimate")
						&& ((MenuItem) observableList.get(i)).getId().charAt(7) == '1') {
					for (NivelAcesso nivAc : permissao) {
						if (EncryptMD5.getMD5(cleanIdMenuItem).equals(nivAc.getMenu())) {
							((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ? true : false);
							existe = true;
							((MenuItem) observableList.get(i)).setDisable(false);
							break;
						} else
							existe = false;

					}

					if (!existe) {
						((MenuItem) observableList.get(i)).setDisable(true);
						existe = false;

					}
				} else if (DadosGlobais.sistema.equals("Enterprise")
						&& ((MenuItem) observableList.get(i)).getId().charAt(8) == '1') {
					for (NivelAcesso nivAc : permissao) {
						if (EncryptMD5.getMD5(cleanIdMenuItem).equals(nivAc.getMenu())) {
							((MenuItem) observableList.get(i)).setVisible((nivAc.getFlagView() == 1) ? true : false);
							existe = true;
							((MenuItem) observableList.get(i)).setDisable(false);
							break;
						} else
							existe = false;
					}

					if (!existe) {
						((MenuItem) observableList.get(i)).setDisable(true);
						existe = false;

					}
				} else
					((MenuItem) observableList.get(i)).setVisible(false);

			}
		}
	}

	public static NivelAcesso getNivelAcessoEntidade(EnumNivelAcesso menuItem) {
		NivelAcesso result = null;
		for (NivelAcesso niv : DadosGlobais.usuarioLogado.getNiveisAcesso()) {
			if (niv.getMenu().equals(EncryptMD5.getMD5(menuItem.idMenu))) {
				result = niv;
				break;
			}
		}
		return result;

	}

	// FUNCAO PARA RECUPERAR O COMPARTILHAMENTO
	public static List<Integer> getCompartilhamentoEntidade(EnumCompartilhamento entidade) {

		Compartilhamento compartilha = null;

		try {

			for (Compartilhamento comp : DadosGlobais.listCodempCompartilha) {
				if (comp.getId().getModFuncao().equals(EncryptMD5.getMD5(entidade.toString()))) {
					compartilha = comp;
					break;
				}
			}

		} catch (Exception e) {
			throw e;
		}

		List<Integer> paramCompartilhaEmp = new ArrayList<Integer>();

		try {

			if (compartilha != null) {
				for (int i = 0; i < compartilha.getCodEmpcompartilhada().toString().split(",").length; i++) {
					paramCompartilhaEmp
					.add(Integer.parseInt(compartilha.getCodEmpcompartilhada().toString().split(",")[i]));
				}
			} else {
				paramCompartilhaEmp.add(Integer.parseInt(DadosGlobais.empresaLogada.getCodigo().toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

		return paramCompartilhaEmp;

	}

	public void alertException(String msgUsuario, String exception, boolean flagBtnReportar) {
		Stage stg;

		stg = new Stage();

		FXMLLoader fxmlS = new FXMLLoader(getClass().getResource("/views/utils/viewAlertException.fxml"));

		fxmlS.setController(new AlertExceptionController(msgUsuario, exception, flagBtnReportar));

		Scene scenes;

		try {
			scenes = new Scene(fxmlS.load(), 470, 280);
			stg.setScene(scenes);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException ea) {

			// ea.printStackTrace();
		}

		try {
			scenes = new Scene(fxmlS.load(), 470, 280);
			stg.setScene(scenes);
			stg.initStyle(StageStyle.TRANSPARENT);
			stg.initModality(Modality.APPLICATION_MODAL);
			stg.show();
		} catch (IOException ea) {
			// TODO Auto-generated catch block
			// ea.printStackTrace();
		}

	}

	public File printScreen(String folderSave, String nomeImg, String extensaoImg) throws AWTException, IOException {

		Robot robot = new Robot();

		Integer a = (int) Screen.getPrimary().getVisualBounds().getHeight();

		BufferedImage bi = robot.createScreenCapture(new // Captura a tela na
				// àrea definida
				// pelo retângulo

				Rectangle(0, 0, (int) Screen.getPrimary().getVisualBounds().getWidth(),
						(int) Screen.getPrimary().getVisualBounds().getHeight())); // aqui
		// vc
		// configura
		// as
		// posições
		// xy
		// e
		// o
		// tam
		// da
		// área
		// que
		// quer
		// capturar

		String nomeImagem = nomeImg;

		ImageIO.write(bi, extensaoImg, new File(folderSave + nomeImagem));
		File file = new File(folderSave + nomeImagem);
		return file;
	}

	public File prinScreen(String folderSave, String nomeImg, String extensaoImg) throws AWTException, IOException {

		Robot robot = new Robot();

		Integer a = (int) Screen.getPrimary().getVisualBounds().getHeight();

		BufferedImage bi = robot.createScreenCapture(new // Captura a tela na
				// àrea definida
				// pelo retângulo

				Rectangle(0, 0, (int) Screen.getPrimary().getVisualBounds().getWidth(),
						(int) Screen.getPrimary().getVisualBounds().getHeight())); // aqui
		// vc
		// configura
		// as
		// posições
		// xy
		// e
		// o
		// tam
		// da
		// área
		// que
		// quer
		// capturar

		String nomeImagem = nomeImg;

		ImageIO.write(bi, extensaoImg, new File(folderSave + nomeImagem));
		File file = new File(folderSave + nomeImagem);
		return file;

	}

	public static void alternaBotaoDelete(Button btnDelete, int flagAtivo) {
		FontAwesomeIconView icon = new FontAwesomeIconView(
				flagAtivo == 1 ? FontAwesomeIcon.TRASH : FontAwesomeIcon.CHECK_SQUARE_ALT);
		icon.setSize("16");
		icon.setId("icons");
		btnDelete.setTooltip(new Tooltip(
				DadosGlobais.resourceBundle.getString(flagAtivo == 1 ? "toolTipExcluir" : "tooltipAtivar")));
		btnDelete.setGraphic(icon);
	}

	public void tratamentoExcecao(String msgExcecao, String idFuncaoError) {
		// TESTA ERRO DE CONEXAO COM O SERVIDOR
		if (msgExcecao.contains("Connec") || msgExcecao.contains("Conn") || msgExcecao.contains("Con")) {
			alertException(
					"O sistema não está encontrando o SERVIDOR!\nVerifique sua conexão de rede e tente novamente!",
					"Detalhes do Erro Retornado:\n" + idFuncaoError + "\nSolicite Suporte!\n" + msgExcecao, false);
		} else {
			alertException(DadosGlobais.msgErroException,
					"Detalhes do Erro Retornado:\n" + idFuncaoError + "\nSolicite Suporte!\n" + msgExcecao, true);
		}

	}

	public static void openFormCadastro(AnchorPane anchorPaneListagem, AnchorPane anchorPaneDetalhes, int FORM_SIZE) {

		FadeTransition fadeTransit = new FadeTransition(Duration.seconds(0), anchorPaneDetalhes);
		fadeTransit.setFromValue(0);
		fadeTransit.setToValue(1);
		fadeTransit.play();

		anchorPaneDetalhes.setVisible(true);

		//initTextFieldsAccessibleText( anchorPaneDetalhes );

		// ----FOR ADJUSTING WINDOWS SIZE ACORDING TO ITS CONTENT OR THE SYSTEM
		// RESOLUTION--------

		double apLWidth = anchorPaneListagem.getWidth(), apLHeigth = anchorPaneListagem.getHeight(),
				apDWidth = apLWidth, apDHeigth = apLHeigth, apdTop = 0.0, apdBottom = 0.0, apdLeft = 0.0,
				apdRight = 0.0;

		double div = 1.5, border = 0;

		switch (FORM_SIZE) {
		case 1:// LOW_SIZE

			div = 1.5;

			apdTop = (apLHeigth - (apDHeigth / div));
			apdBottom = (apLHeigth - (apDHeigth / div));
			apdLeft = (apLWidth - (apDWidth / div));
			apdRight = (apLWidth - (apDWidth / div));

			break;
		case 2:// MEDIUM_SIZE

			div = 1.15;
			border = 10;

			apdTop = 0.0;
			apdBottom = (apLHeigth - (apDHeigth - border));
			apdLeft = (apLWidth - (apDWidth / div));
			apdRight = (apLWidth - (apDWidth / div));

			break;
		case 3:// SEMI-FULL_SIZE

			div = 1.01;

			apdTop = 0.0;
			apdBottom = 0.0;
			apdLeft = (apLWidth - (apDWidth / div));
			apdRight = (apLWidth - (apDWidth / div));

			break;

		case 4:// LOWUP_SIZE

			div = 1.27;
			border = 100;

			apdTop = (apLHeigth - (apDHeigth / div));
			apdBottom = (apLHeigth - (apDHeigth / div));
			apdLeft = ((apLWidth - (apDWidth / div)) + border);
			apdRight = ((apLWidth - (apDWidth / div)) + border);

			break;
		}

		if (FORM_SIZE > 0) {
			AnchorPane.setTopAnchor(anchorPaneDetalhes, apdTop);
			AnchorPane.setBottomAnchor(anchorPaneDetalhes, apdBottom);
			AnchorPane.setLeftAnchor(anchorPaneDetalhes, apdLeft);
			AnchorPane.setRightAnchor(anchorPaneDetalhes, apdRight / div);
		}
		double largo = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		double alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

		double alturaCenter = alto / 2.0 - anchorPaneDetalhes.getHeight() / 2.0;
		double largoCenter = largo / 2.0 - anchorPaneDetalhes.getWidth() / 2.0;

		anchorPaneDetalhes.setLayoutX(largoCenter);
		anchorPaneDetalhes.setLayoutY(alturaCenter - 100);

		// ---- END FOR ADJUSTING WINDOWS SIZE ACORDING TO ITS CONTENT OR THE
		// SYSTEM RESOLUTION---------

		anchorPaneListagem.setDisable(true);

	}

	public static void fechaTelaCadastro(AnchorPane anchorPaneListagem, AnchorPane anchorPaneDetalhes) {
		anchorPaneDetalhes.setVisible(false);
		anchorPaneListagem.setDisable(false);
	}

	public static void setCboxFilterSelecionado(ComboBox<ComboBoxFilter> cbox, String valor) {
		for (int i = 0; i < cbox.getItems().size(); i++) {
			if (cbox.getItems().get(i).getField().equals(valor)) {
				cbox.getSelectionModel().select(i);
				break;
			}
		}

	}

	/**
	 * Monta a mascara para Moeda.
	 *
	 * @param textField
	 *            TextField
	 */
	public static void monetaryField(final TextField textField) {
		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.lengthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String value = textField.getText();
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
				value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
				textField.setText(value);
				positionCaret(textField);

				textField.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observableValue, String oldValue,
							String newValue) {
						if (newValue.length() > 17)
							textField.setText(oldValue);
					}
				});
			}
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean,
					Boolean fieldChange) {
				if (!fieldChange) {
					final int length = textField.getText().length();
					if (length > 0 && length < 3) {
						textField.setText(textField.getText() + "00");
					}
				}
			}
		});
	}

	/**
	 * Devido ao incremento dos caracteres das mascaras eh necessario que o
	 * cursor sempre se posicione no final da string.
	 *
	 * @param textField
	 *            TextField
	 */
	private static void positionCaret(final TextField textField) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// Posiciona o cursor sempre a direita.
				textField.positionCaret(textField.getText().length());
			}
		});
	}

	public static BigInteger checkDeleteCreate() {

		Random gerador = new Random();

		Integer utctag = (int) (new Date().getTime() / 1000);

		String soma = (DadosGlobais.empresaLogada.getCodigo().toString() + "00000000");

		long random = (long) ((Math.random() * utctag) + Long.valueOf(soma));

		String checkConcat = String.valueOf(DadosGlobais.empresaLogada.getCodigo())
				+ String.valueOf(gerador.nextInt(99)) + (String.valueOf(random));

		return BigInteger.valueOf(Long.valueOf(checkConcat));
	}

	/**
	 * METODO PARA VERIFICAR, SE AS FONTS DO SISTEMA ESTÁ INSTALADO NO
	 * COMPUTADOR, NO CASO NÃO ESTIVER INSTALADO, VAI SER INSTALADO
	 * 
	 * @param fonts
	 *            ARREGLO CON FONTS DO SISTEMA
	 */
	public static Font FontSystem(String... fontArray) {

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fontFamilies = ge.getAvailableFontFamilyNames();
		Font font = null;
		boolean exist = false;

		for (String fonts : fontArray) {
			for (String fontLocalSystem : fontFamilies) {
				if (fonts.equals(fontLocalSystem)) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				// REGISTRAR FONTS NO COMPUTADOR
				try {
					font = Font.createFont(Font.TRUETYPE_FONT,
							new File("D:/JAVA/worksapce1/Eptus/src/styles/fonts/Segoe UI/segoeui.ttf"));
					ge.registerFont(font);
				} catch (FontFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			exist = false;
		}

		return font;
	}

	// LISTAR CERTIFICADOS DIGITAIS INSTALADOS NO WINDOWS
	public static List<Certificado> getAllCertificate() {
		List<Certificado> certificado = null;
		try {
			certificado = CertificadoUtil.listaCertificadosWindows();
		} catch (NfeException e) {

			e.printStackTrace();
		}
		return certificado;
	}

	/**
	 * METODO PARA INICIALIZAR OS ATRIBUTOS UMA CLASSE
	 * 
	 * @param objetClass
	 *            Classe
	 * @return
	 */
	public Object initializeAttribClass(Object objetClass) {

		Class<?> classInitialize = objetClass.getClass();

		for (int i = 0; i < classInitialize.getDeclaredFields().length; i++) {

			java.lang.reflect.Field atrib = classInitialize.getDeclaredFields()[i];
			try {

				switch (atrib.getType().getSimpleName()) {
				case "String":
					atrib.setAccessible(true);
					atrib.set(objetClass, "");
					break;

				case "StringProperty":
					atrib.setAccessible(true);
					atrib.set(objetClass, "");
					break;

				case "Integer":
					atrib.setAccessible(true);
					atrib.set(objetClass, 0);
					break;

				case "BigInteger":
					atrib.setAccessible(true);
					atrib.set(objetClass, BigInteger.valueOf(0));
					break;

				case "BigDecimal":
					atrib.setAccessible(true);
					atrib.set(objetClass, BigDecimal.valueOf(0.0000).setScale(4));
					break;

				case "Boolean":
					atrib.setAccessible(true);
					atrib.set(objetClass, false);
					break;

				case "LocalDateTime":
					atrib.setAccessible(true);
					// ofPattern("yyyy-MM-dd H:m:s")
					// LocalDateTime data =
					// LocalDateTime.parse(LocalDateTime.of(1999,
					// Month.DECEMBER, 31, 12, 59,
					// 59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd
					// HH:mm:ss")), DateTimeFormatter.ISO_DATE_TIME);
					atrib.set(objetClass, LocalDateTime.of(2001, 01, 01, 00, 00, 00));
					break;
				default:
					break;
				}

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return objetClass;
	}

	/*
	 * 
	 * Auxiliar method to help to the main method called 'searchPath' to finds
	 * Label texts into the TabPanes Objects
	 * 
	 */
	void aux(ObservableList<?> nodes, String searchText) {
		for (int itNode = 0; itNode < nodes.size(); itNode++) {
			Object objList = nodes.get(itNode);

			if (objList instanceof Pane)// All intern panes
			{
				Pane tp = (Pane) objList;
				ObservableList<?> nodesTab = tp.getChildren();
				int obsListSize = nodesTab.size();

				if (obsListSize > 0)
					aux(nodesTab, searchText);
				else {
				}

			} else if (objList instanceof Tab) // All intern tabs
			{

				Tab tab = ((Tab) objList);
				AnchorPane ap = (AnchorPane) tab.getContent();
				ObservableList<?> nodesTab = ap.getChildren();
				int obsListSize = nodesTab.size();

				String lblText = "";

				if (obsListSize > 0) {
					StackPane stackPn = (StackPane) tab.getGraphic();

					if (stackPn != null) {
						ObservableList<?> tmp = stackPn.getChildren();

						if (tmp.size() > 0) {
							Group groupStack = (Group) tmp.get(0);
							ObservableList<?> tmp1 = groupStack.getChildren();

							if (tmp1.size() > 0) {
								Label lblIntTab = (Label) tmp1.get(0);
								lblText = lblIntTab.getText();
							} else {
							}
						} else {
						}
					} else {
					}

					if (lblText == "" || lblText.isEmpty())
						lblText = tab.getText();
					else {
					}

					secondLevel = lblText + " )>";

					aux(nodesTab, searchText);
				} else {
				}

			} else if (objList instanceof Label) {
				Label lblObj = (Label) objList;
				String lblText = lblObj.getText();
				if (removeSpecialCharacters(lblText).toLowerCase().contains(removeSpecialCharacters(searchText.toLowerCase()))) {
					finalLevel = lblText;
					path += firstLevel + secondLevel + finalLevel + "\n";

				} else {
				}

			} else {
			}

		}
	}

	/*
	 * 
	 * Main method called 'searchPath' to finds Label texts into the TabPanes
	 * Objects
	 * 
	 */
	public void searchPath(ObservableList<?> nodes, String searchText) 
	{
		for (int itNode = 0; itNode < nodes.size(); itNode++) {
			Object objList = nodes.get(itNode);

			if (objList instanceof TabPane) // the Tabs that contains some
				// object
			{
				TabPane tp = (TabPane) objList;
				ObservableList<?> nodesTab = tp.getTabs();
				int obsListSize = nodesTab.size();

				if (obsListSize > 0)
					aux(nodesTab, searchText);
				else {
				}

			} else if (objList instanceof Tab) // All the tabs
			{
				Tab tab = ((Tab) objList);
				AnchorPane ap = (AnchorPane) tab.getContent();
				ObservableList<?> nodesTab = ap.getChildren();

				int obsListSize = nodesTab.size();

				if (obsListSize > 0) {
					firstLevel = tab.getText() + " ( ";
					searchPath(nodesTab, searchText);
				} else {
				}
			} else {
			}
		}

	}

	/*
	 * 
	 * Method to perform Enter and click actions in the searchBox
	 * 
	 * 
	 */
	public void actionSearchBox(CustomTextField txtSearch, TabPane tpContainer) 
	{
		String searchedWord = txtSearch.getText();
		searchPath(tpContainer.getTabs(), searchedWord);
		showSearchLabelInf(path, tpContainer, searchedWord);
		path = "";
		firstLevel = "";
		secondLevel = "";
		finalLevel = "";
	}
}
