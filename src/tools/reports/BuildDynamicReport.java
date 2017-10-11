package tools.reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.HyperLinkBuilder;

import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;

import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;

import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.exception.DRException;
import tools.reports.ColumnsPrint;

/**
 * Class with the methods to generate reports and export to different formats.
 * 
 * @author User
 *
 */
public class BuildDynamicReport {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	/**
	 * Method to generate the report, show for the printing and export.
	 * 
	 * @param dataSource
	 *            The report data.
	 * @param columns
	 *            The columns of the report, with the title, fields, data type
	 *            and actions.
	 * @param pageOrientation
	 *            Orientation of the page, default vertical orientation.
	 * @param titleReport
	 *            Title of report.
	 * @param actionPrintExport
	 *            Action of the report, print, export to pdf,excel,docx. *
	 */
	public JasperReportBuilder buildReport(DRDataSource dataSource, List<ColumnsPrint> columnsPrints, Boolean pageOrientation,
			String titleReport, String actionPrintExport, String urlImage) {
		
		JasperReportBuilder report = DynamicReports.report();
		
		try {			

			// Create columns with actions
			for (ColumnsPrint columnsPrint : columnsPrints) {

				ValueColumnBuilder valueColumnBuilder;

				if (columnsPrint.dataType.equalsIgnoreCase("integer"))
					valueColumnBuilder = col
							.column(columnsPrint.title, columnsPrint.field,
									(DRIDataType) type.detectType(columnsPrint.dataType))
							.setValueFormatter(new ValueFormatter());
				else if (columnsPrint.dataType.equalsIgnoreCase("date"))
					valueColumnBuilder = col.column(columnsPrint.title, columnsPrint.field,
							(DRIDataType) type.detectType(columnsPrint.dataType)).setPattern("dd/MM/yyyy");
				else
					valueColumnBuilder = col.column(columnsPrint.title, columnsPrint.field,
							(DRIDataType) type.detectType(columnsPrint.dataType));

				valueColumnBuilder.setWidth(columnsPrint.width.intValue());

				report.addColumn(valueColumnBuilder);

				if (columnsPrint.action != null && columnsPrint.action.equals("sumar"))
					report.subtotalsAtSummary(sbt.sum(valueColumnBuilder));
				else if (columnsPrint.action != null && columnsPrint.action.equals("contar"))
					report.subtotalsAtSummary(sbt.count(valueColumnBuilder));

				else if (columnsPrint.action != null && columnsPrint.action.equals("media"))
					report.subtotalsAtSummary(sbt.avg(valueColumnBuilder));
			}

			// Styles of report
			StyleBuilder boldStyle = stl.style().bold();
			StyleBuilder italicStyle = stl.style().italic();

			StyleBuilder boldCenteredStyle = stl.style(boldStyle).setTextAlignment(HorizontalTextAlignment.CENTER,
					VerticalTextAlignment.MIDDLE);

			StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle).setBackgroundColor(java.awt.Color.LIGHT_GRAY)
					.setBorder(stl.penThin());

			StyleBuilder columnAlignment = stl.style().setTextAlignment(HorizontalTextAlignment.LEFT,
					VerticalTextAlignment.MIDDLE);

			StyleBuilder subtotalStyle = stl.style(boldStyle).setTextAlignment(HorizontalTextAlignment.LEFT,
					VerticalTextAlignment.MIDDLE);

			StyleBuilder titleStyle = stl.style(boldCenteredStyle)
					.setTextAlignment(HorizontalTextAlignment.RIGHT, VerticalTextAlignment.MIDDLE).setFontSize(15);

			StyleBuilder bold18CenteredStyle = stl.style(boldCenteredStyle).setFontSize(18);

			HyperLinkBuilder link = hyperLink("http://www.eptusdaamazonia.com.br");

			// Assign components report
			report.setColumnTitleStyle(columnTitleStyle).highlightDetailEvenRows().title(cmp
					.horizontalList(
							//cmp.image(getClass().getResource("/styles/img/logoReport.png")).setFixedDimension(90, 60),
							
							cmp.image(urlImage).setFixedDimension(90, 60),
							cmp.verticalList(
									cmp.text("Eptus da Amazonia").setStyle(bold18CenteredStyle)
											.setHorizontalTextAlignment(HorizontalTextAlignment.LEFT),
									cmp.text("http://www.eptusdamazonia.com").setStyle(italicStyle).setHyperLink(link)),
							cmp.text(titleReport).setStyle(titleStyle))
					.newRow().add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen1Point())).setFixedHeight(8)))
					.pageFooter(cmp.horizontalList().add(cmp.pageXofY().setStyle(stl.style(boldCenteredStyle))),
							cmp.text(new java.util.Date()).setStyle(boldStyle)
									.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT))
					.setColumnStyle(columnAlignment).setSubtotalStyle(subtotalStyle).setDataSource(dataSource);

			if (pageOrientation)
				report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE).setPageMargin(margin(20));
			else
				report.setPageFormat(PageType.A4).setPageMargin(margin(20));

			FileChooser fileChooser = new FileChooser();
			// fileChooser.setInitialDirectory(new
			// File(System.getProperty("user.dir")));//user.home
			File file = new File("");

//			// Action of the report
//			switch (actionPrintExport) {
//
//			case "print":
//
//				viewer = new JasperFX(report.toJasperPrint());
//				Page pages = new Page(report.toJasperPrint().getPages().size() - 1, 0, 0);
//				//viewer.show(800, 600, null, Modality.WINDOW_MODAL, StageStyle.DECORATED);
//				// report.show(false);
//
//				break;
//
//			case "pdf": {
//				viewer = new JasperFX(report.toJasperPrint());
//				fileChooser.setTitle("Save as Pdf");
//				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
//				file = fileChooser.showSaveDialog(null);
//				FileOutputStream fileOut;
//				if (file != null) {
//					try {
//						fileOut = new FileOutputStream(file);
//						report.toPdf(fileOut);
//						fileOut.close();
//					} catch (IOException ex) {
//						System.out.println(ex.getMessage());
//					}
//				}
//			}
//				break;
//
//			case "excel": {
//				//viewer = new JasperFX(report.toJasperPrint());
//				fileChooser.setTitle("Save as Excel");
//				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("EXCEL", "*.xlsx"));
//				file = fileChooser.showSaveDialog(null);
//				FileOutputStream fileOut;
//				if (file != null) {
//					try {
//						fileOut = new FileOutputStream(file);
//						report.toXlsx(fileOut);
//						fileOut.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			}
//				break;
//
//			case "doc": {
//				fileChooser.setTitle("Save as Docx");
//				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DOCX", "*.docx"));
//				file = fileChooser.showSaveDialog(null);
//				FileOutputStream fileOut;
//				if (file != null) {
//					try {
//						fileOut = new FileOutputStream(file);
//						report.toDocx(fileOut);
//						fileOut.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//
//			}
//				break;
//
//			case "html": {
//				fileChooser.setTitle("Save as Html");
//				fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HTML", "*.html"));
//				file = fileChooser.showSaveDialog(null);
//				if (file != null) {
//					JasperHtmlExporterBuilder htmlExporter = export.htmlExporter(file).setOutputImagesToDir(true);
//					report.toHtml(htmlExporter);
//				}
//
//			}
//				break;
//
//			default:
//				break;
//			}

		} catch (DRException e) {

			e.printStackTrace();

		}
return report;
	}

	private static class ValueFormatter extends AbstractValueFormatter<String, Number> {

		private static final long serialVersionUID = 1L;

		@Override
		public String format(Number value, ReportParameters reportParameters) {
			// return type.bigIntegerType().valueToString(value,
			// reportParameters.getLocale());
			return String.valueOf(value);
		}

	}

	private static class DateFormatter {

		private void DateFormatter() {
			// TODO Auto-generated method stub

		}

	}

}
