package tools.xml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import application.DadosGlobais;
import tools.utils.Util;

/**
 * Class with the methods to read/save the file with the properties of the
 * columns of the table defined by the user.
 * 
 * @author User
 *
 */
public class XMLTableColumns { 	
	/**
	 * Save to an xml file the settings of the columns of a tableView.
	 * 
	 * @param cols Columns to save to file
	 * @param filename File name
	 */
	public void WriteXMLColumns(ArrayList<UserDefinedColumn> cols, String filename) {

		UserDefinedColumns userDefinedColumns = new UserDefinedColumns();
		userDefinedColumns.setColumn(cols);

		try {

				File folder = new File(DadosGlobais.folderGridLog + "Forms[U" + DadosGlobais.usuarioLogado.getCodigo() + "E"+DadosGlobais.usuarioLogado.getCodemp()+"]" );
				folder.mkdir();

				File xml = new File(folder.getAbsolutePath() + "\\" + filename + ".xml");
				JAXBContext context = JAXBContext.newInstance(UserDefinedColumns.class);
				Marshaller marshaller = context.createMarshaller();

				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");

				marshaller.marshal(userDefinedColumns, xml);


		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Read the xml file with the information of the columns.
	 * 
	 * @param filename File name
	 * @return List of columns
	 */
	public ArrayList<UserDefinedColumn> ReadXMLColumns(String filename) {

		File file = null;
		UserDefinedColumns userDefinedColumns = null;
		try {

			file = new File(DadosGlobais.folderGridLog + "Forms[U" + DadosGlobais.usuarioLogado.getCodigo() + "E"+DadosGlobais.usuarioLogado.getCodemp()+"]\\" + filename + ".xml");
			if (file.exists()) {
				JAXBContext context = JAXBContext.newInstance(UserDefinedColumns.class);

				Unmarshaller unmarshaller = context.createUnmarshaller();
				
				userDefinedColumns = (UserDefinedColumns) unmarshaller.unmarshal(file);

				return userDefinedColumns.getColumn();
			}

		} catch (JAXBException e) {
			
			e.printStackTrace();
		}

		return null;
	}

}
