package application;

import java.awt.Font;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import models.configuracoes.Compartilhamento;
import models.configuracoes.Config;
import models.configuracoes.Empresa;
import models.configuracoes.NivelAcesso;
import models.configuracoes.Usuario;

public class DadosGlobais {
	
	public static int idUsuario;
	public static String usuario;
	public static String empresa;
	public static String Server;
	public static int codEmpDefault;
	public static String idioma;
	public static String pais;
	public static String sistema;
	public static ResourceBundle resourceBundle;
	public static Usuario usuarioLogado;
	public static Empresa empresaLogada;
	public static Config configEmpLogada;
	//public static List<NivelAcesso> listNivelAcesso;
	public static LocalDateTime dataMovtoSistema;
	public static List<Compartilhamento> listCodempCompartilha;
	public static String folderDocumentos = "C://EptusAM//System.doc//";
	public static String folderAnexoEmail = "C://EptusAM//System.anx//";
	public static String folderPrintScreen = "C://EptusAM//System.scr//";
	public static String folderImagens = "C://EptusAM//System.img//";
	public static String folderGridLog = "C://EptusAM//System.grd//";
	public static String folderTemp = "C://EptusAM//System.tmp//";
	public static Font fontSistema; //FONTS USADAS PELO SISTEMAS
	public static ZoneId fusoHorarioDefault = ZoneId.of("GMT-04:00");
	public static String URL_CONSULT_CNPJ = "http://www.receita.fazenda.gov.br/PessoaJuridica/CNPJ/cnpjreva/Cnpjreva_Solicitacao2.asp";
	
	public static String msgErroException = "Ocorreu um erro de execução \n"
											+ "Caso o erro persista solicite suporte técnico!\n"
											+ "Para enviar o log do erro pressione -> Rerportar Erro";	
	@PersistenceContext
	public static EntityManager em;

	public static void traducao(){
		Locale locale = new Locale(DadosGlobais.idioma.toLowerCase());
		DadosGlobais.resourceBundle = ResourceBundle.getBundle("translate/eptus", locale);	
	}
	
}


