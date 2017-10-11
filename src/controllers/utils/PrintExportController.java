package controllers.utils;

import static net.sf.dynamicreports.report.builder.DynamicReports.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.DadosGlobais;
import controllers.configuracoes.UsuarioController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import tools.reports.BuildDynamicReport;
import tools.reports.ColumnsPrint;
import tools.reports.JasperFX;
import tools.reports.Page;
import tools.reports.TableActionsPrint;
import tools.reports.TableConfPrint;
import tools.utils.Util;

/**
 * Controlling class of the form printGridPane.
 * 
 * @author User
 */
public class PrintExportController implements Initializable {

	@FXML
	private AnchorPane anpPrintGridPane;
	@FXML
	private Button btnCancelar, btnPrintReport, btnExportDoc, btnExportExcel, btnExportPdf, btnExportHtml;
	@FXML
	private GridPane gridPane;
	@FXML
	private Label lblTitleColums, lblPrint, lblSum, lblCount, lblAverage;
	@FXML
	public TableView<?> tableView;

	@FXML
	private CheckBox chkPageOrientation;
	
	@FXML
	private Pane titleBar;

	public List<TableConfPrint> tableShowPrintList;

	public List<TableActionsPrint> tableActionsPrint = new ArrayList<TableActionsPrint>();

	public String titleReport;

	static Stage stg;

	ProgressBar pBar;

	Util util = new Util();

	BuildDynamicReport bdr = new BuildDynamicReport();

	@SuppressWarnings({ "rawtypes", "static-access" })
	/**
	 * Constructor method.
	 * 
	 * @param table
	 *            Data table.
	 * @param tableShowPrintList
	 *            List of parameters from any table view.
	 * @param titleReport
	 *            Title of report.
	 * @param actionPrintExport
	 *            Action to print or export.
	 */
	public PrintExportController(TableView table, List<TableConfPrint> tableShowPrintList, String titleReport,
			ProgressBar bar, Stage cena) {
		// TODO Auto-generated constructor stub
		this.tableView = table;
		this.tableShowPrintList = tableShowPrintList;
		this.titleReport = titleReport;

		this.pBar = bar;
		this.stg = cena;
	}

	@FXML
	void actionBtnCancelar(ActionEvent event) {
		Util.closeWindow(anpPrintGridPane);
		tableShowPrintList.clear();
	}

	@FXML
	void actionBtnExportDoc(ActionEvent event) {

		sendToBuildReport("doc");

	}

	@FXML
	void actionBtnExportExcel(ActionEvent event) {

		sendToBuildReport("excel");

	}

	@FXML
	void actionBtnExportHtml(ActionEvent event) {

		sendToBuildReport("html");
	}

	@FXML
	void actionBtnExportPdf(ActionEvent event) {

		sendToBuildReport("pdf");

	}

	@FXML
	void actionBtnPrintReport(ActionEvent event) {

		sendToBuildReport("print");

	}

	/**
	 * Build grid with data from any table.
	 * 
	 * @param tableShowPrintList
	 *            List of parameters from any table view.
	 * @param gridPane
	 *            GridPane.
	 */
	public void buildShowTable(List<TableConfPrint> tableShowPrintList, GridPane gridPane) {

		int row = 1;
		// Creating objects labels and check box into gridPane
		for (TableConfPrint tsp : tableShowPrintList) {

			Label label = new Label(tsp.titleColumn);
			label.setId(tsp.id);

			gridPane.add(label, 0, row);

			for (int col = 1; col < gridPane.getColumnConstraints().size(); col++) {
				CheckBox checkBox = new CheckBox();
				checkBox.setId(tsp.id + "checkBox" + col);
				// ActionListener Check box
				checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
						int rowIndex = GridPane.getRowIndex(checkBox);
						int colIndex = GridPane.getColumnIndex(checkBox);

						if (checkBox.isSelected()) {

							for (int i = 2; i < 5; i++) {
								if (colIndex > 1 && checkBox.isSelected() && colIndex != i) {
									((CheckBox) getNodeFromGridPane(gridPane, i, rowIndex)).setSelected(false);

								} else if (colIndex > 1 && !checkBox.isSelected() && colIndex == i) {
									((CheckBox) getNodeFromGridPane(gridPane, i, rowIndex)).setSelected(true);
								}
							}

							if (!((CheckBox) getNodeFromGridPane(gridPane, 1, rowIndex)).isSelected())
								((CheckBox) getNodeFromGridPane(gridPane, 1, rowIndex)).setSelected(true);

						} else {

							if (!((CheckBox) getNodeFromGridPane(gridPane, 1, rowIndex)).isSelected()) {
								for (int i = 2; i < 5; i++) {
									((CheckBox) getNodeFromGridPane(gridPane, i, rowIndex)).setSelected(false);
								}
							}
						}
					}
				});
				gridPane.add(checkBox, col, row);
			}
			if (!tsp.type.equals("Integer") && !tsp.type.equals("Double") && !tsp.type.equals("Float")) {
				((CheckBox) getNodeFromGridPane(gridPane, 2, row)).setDisable(true);
				((CheckBox) getNodeFromGridPane(gridPane, 4, row)).setDisable(true);
			}
			row++;
		}
	}

	/**
	 * Get node position in gridPane by column and row.
	 * 
	 * @param gridPane
	 *            GridPane.
	 * @param col
	 *            Column.
	 * @param row
	 *            Row.
	 */
	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
		for (Node node : gridPane.getChildren()) {
			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Get row count GridPane
	 * 
	 * @param gridPane
	 */
	public int getRowCount(GridPane gridPane) {
		int numRows = gridPane.getRowConstraints().size();
		for (int i = 0; i < gridPane.getChildren().size(); i++) {
			Node child = gridPane.getChildren().get(i);
			if (child.isManaged()) {
				Integer rowIndex = GridPane.getRowIndex(child);
				if (rowIndex != null) {
					numRows = Math.max(numRows, rowIndex + 1);
				}
			}
		}
		return numRows;
	}

	String field = null;
	String[] fields;

	/**
	 * Method to prepare the report with their actions.
	 * 
	 * @param actionPrintExport
	 *            Action of the report, print, export to pdf,doc,excel and html.
	 */
	@SuppressWarnings({ "rawtypes" })
	public void sendToBuildReport(String actionPrintExport) {

		// Complete list of selected columns and operations
		for (int i = 1; i < getRowCount(gridPane); i++) {
			boolean print = false;
			String action = null;

			if (((CheckBox) getNodeFromGridPane(gridPane, 1, i)).isSelected())
				print = true;
			if (((CheckBox) getNodeFromGridPane(gridPane, 2, i)).isSelected())
				action = "sumar";
			else if (((CheckBox) getNodeFromGridPane(gridPane, 3, i)).isSelected())
				action = "contar";
			else if (((CheckBox) getNodeFromGridPane(gridPane, 4, i)).isSelected())
				action = "media";

			if (print || action != null)
				tableActionsPrint.add(new TableActionsPrint(((Label) getNodeFromGridPane(gridPane, 0, i)).getText(),
						((Label) getNodeFromGridPane(gridPane, 0, i)).getId(), tableShowPrintList.get(i - 1).field,
						tableShowPrintList.get(i - 1).type, print, action, tableShowPrintList.get(i - 1).width));
		}

		List<ColumnsPrint> columnsPrints = new ArrayList<ColumnsPrint>();
		String field = null;
		// Complete list of communes and fields
		for (int i = 0; i < tableActionsPrint.size(); i++) {
			if (tableActionsPrint.get(i).print || tableActionsPrint.get(i).action != null) {
				columnsPrints.add(new ColumnsPrint(tableActionsPrint.get(i).column, tableActionsPrint.get(i).field,
						tableActionsPrint.get(i).type, tableActionsPrint.get(i).action,
						tableActionsPrint.get(i).width));
				field += "," + tableActionsPrint.get(i).field;
			}
		}

		if (field != null) {

			fields = field.substring(5).split(",");
			DRDataSource dataSource = new DRDataSource(fields);

			Task<String> TarefaRefresh = new Task<String>() {
				
				JasperReportBuilder report = DynamicReports.report();
				JasperFX viewer;

				@Override
				protected String call() throws Exception {

					for (int x = 0; x < tableView.getItems().size(); x++) {

						Object row[] = new Object[fields.length];
						int y = 0;
						for (TableColumn column : tableView.getVisibleLeafColumns()) {
						
							if (fields[y].equals(((PropertyValueFactory) column.getCellValueFactory()).getProperty())) {
								row[y] = column.getCellData(x);
								

								if (y < fields.length - 1)
									y++;
							}

						}
						dataSource.add(row);
					}
					
					File f = new File("C:/EptusAM/System.tmp/emp"+ DadosGlobais.empresaLogada.getCodemp() +"/logo.png");
					if (!f.exists())
						f = new File("styles/img/logoReport.png");
					
					

					report = bdr.buildReport(dataSource, columnsPrints, chkPageOrientation.isSelected(), titleReport,
							actionPrintExport, f.getPath());
					viewer = new JasperFX(report.toJasperPrint());

					return "-";
				}

				@SuppressWarnings("unused")
				@Override
				protected void succeeded() {
					
					FileChooser fileChooser = new FileChooser();
					File file = new File("");

					// Action of the report
					switch (actionPrintExport) {

					case "print": {

						viewer.show(titleReport);
					}
						break;

					case "pdf": {

						fileChooser.setTitle("Save as Pdf");
						fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
						file = fileChooser.showSaveDialog(null);
						FileOutputStream fileOut;
						if (file != null) {
							try {
								fileOut = new FileOutputStream(file);
								report.toPdf(fileOut);
								fileOut.close();
							} catch (IOException | DRException ex) {
								System.out.println(ex.getMessage());
							}
						}
					}
						break;

					case "excel": {

						fileChooser.setTitle("Save as Excel");
						fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("EXCEL", "*.xlsx"));
						file = fileChooser.showSaveDialog(null);
						FileOutputStream fileOut;
						if (file != null) {
							try {
								fileOut = new FileOutputStream(file);
								report.toXlsx(fileOut);
								fileOut.close();
							} catch (IOException | DRException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
						break;

					case "doc": {
						fileChooser.setTitle("Save as Docx");
						fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DOCX", "*.docx"));
						file = fileChooser.showSaveDialog(null);
						FileOutputStream fileOut;
						if (file != null) {
							try {
								fileOut = new FileOutputStream(file);
								report.toDocx(fileOut);
								fileOut.close();
							} catch (IOException | DRException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
						break;

					case "html": {
						fileChooser.setTitle("Save as Html");
						fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HTML", "*.html"));
						file = fileChooser.showSaveDialog(null);
						if (file != null) {
							JasperHtmlExporterBuilder htmlExporter = export.htmlExporter(file)
									.setOutputImagesToDir(true);
							try {
								//report.toHtml(htmlExporter);	
								JasperExportManager.exportReportToHtmlFile(report.toJasperPrint(), file.getAbsolutePath());
								
							} catch (DRException | JRException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
						break;

					default:
						break;
					}

					tableActionsPrint.clear();
					tableShowPrintList.clear();
					stg.close();
					pBar.setProgress(1);
					Util.closeWindow(anpPrintGridPane);
				}

				@Override
				protected void failed() {
					stg.close();
					util.showAlert("Erro ao Gerar Relatório \n" + exceptionProperty().get(),"error");
					pBar.setProgress(0);
				}

				@Override
				protected void cancelled() {
					super.cancelled();
					pBar.setProgress(0);
					tableActionsPrint.clear();
					tableShowPrintList.clear();
					Util.closeWindow(anpPrintGridPane);
				}
			};
			Thread t = new Thread(TarefaRefresh);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(UsuarioController.class, TarefaRefresh, "Gerando Relatório", true);
			pBar.setProgress(-1);

		}

		else {
			
			util.showAlert("Selecione uma coluna ou operação.","warning");
		}

	}

	public void translations() {

		lblTitleColums.setText(DadosGlobais.resourceBundle.getString("lblTitleColums"));
		lblPrint.setText(DadosGlobais.resourceBundle.getString("lblPrint"));
		lblSum.setText(DadosGlobais.resourceBundle.getString("lblSum"));
		lblCount.setText(DadosGlobais.resourceBundle.getString("lblCount"));
		lblAverage.setText(DadosGlobais.resourceBundle.getString("lblAverage"));
		chkPageOrientation.setText(DadosGlobais.resourceBundle.getString("chkPageOrientation"));
		btnCancelar.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("toolTipBtnCancelar")));
		btnExportPdf.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("btnExportPdf")));
		btnExportExcel.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("btnExportExcel")));
		btnExportDoc.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("btnExportDoc")));
		btnExportHtml.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("btnExportHtml")));
		btnPrintReport.setTooltip(new Tooltip(DadosGlobais.resourceBundle.getString("btnPrintReport")));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		
		
		anpPrintGridPane.getStylesheets()
		.add(getClass().getResource("/styles/css/themes "+DadosGlobais.empresaLogada.getSistema()+ "/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());

		buildShowTable(tableShowPrintList, gridPane);

		translations();

		anpPrintGridPane.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {
			case ESCAPE:
				actionBtnCancelar(null);
				break;

			}
		});

	}

}
