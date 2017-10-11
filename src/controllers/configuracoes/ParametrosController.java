package controllers.configuracoes;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import application.DadosGlobais;
import controllers.compras.DepartamentoController;
import controllers.utils.ConfigColumnController;
import controllers.utils.ProgressBarForm;
import controllers.utils.SearchController;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.configuracoes.Config;
import models.configuracoes.ConfigDAO;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import models.configuracoes.Sequencial;
import models.configuracoes.SequencialDAO;
import tools.utils.Util;

public class ParametrosController implements Initializable {

	@FXML
	private AnchorPane anchorPanePrincipal;

	@FXML
	private ToolBar toolBarDetalhes;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;

	@FXML
	private TextField txtCodigo;

	@FXML
	private TextField txtNome;

	@FXML
	private Label lblCodigo;

	@FXML
	private Label lblNome;

	@FXML
	private TabPane tabPane;

	// Tab Compras
	@FXML
	private Tab tabCompras;

	@FXML
	private TextField txtCpUnidade;

	@FXML
	private Label lblCpUnidade;

	@FXML
	private TextField txtCpGrupo;

	@FXML
	private Label lblCpGrupo;

	@FXML
	private TextField txtCpSubGrupo;

	@FXML
	private Label lblCpSubGrupo;

	@FXML
	private TextField txtCpFabricante;

	@FXML
	private Label lblCpFabricante;

	@FXML
	private TextField txtCpDepartamento;

	@FXML
	private Label lblCpDepartamento;

	@FXML
	private TextField txtCpGrade;

	@FXML
	private Label lblCpGrade;

	@FXML
	private TextField txtCpCorEstilo;

	@FXML
	private Label lblCpCorEstilo;

	@FXML
	private TextField txtCpFornecedor;

	@FXML
	private Label lblCpFornecedor;

	@FXML
	private TextField txtCpTributacao;

	@FXML
	private Label lblCpTributacao;

	@FXML
	private TextField txtCpAplicacao;

	@FXML
	private Label lblCpAplicacao;

	@FXML
	private TextField txtCpFamiliaPreco;

	@FXML
	private Label lblCpFamiliaPreco;

	@FXML
	private TextField txtCpBoxLocalEstoque;

	@FXML
	private Label lblCpBoxLocalEstoque;

	@FXML
	private TextField txtCpTabelaLcServico;

	@FXML
	private Label lblCpTabelaLcServico;

	@FXML
	private TextField txtCpTabelaNCM;

	@FXML
	private Label lblCpTabelaNCM;

	@FXML
	private TextField txtCpItem;

	@FXML
	private Label lblCpItem;

	// Tab Vendas
	@FXML
	private Tab tabVendas;

	@FXML
	private TextField txtVdPais;

	@FXML
	private Label lblVdPais;

	@FXML
	private TextField txtVdUF;

	@FXML
	private Label lblVdUF;

	@FXML
	private TextField txtVdCidade;

	@FXML
	private Label lblVdCidade;

	@FXML
	private TextField txtVdRota;

	@FXML
	private Label lblVdRota;

	@FXML
	private TextField txtVdRamoAtividade;

	@FXML
	private Label lblVdRamoAtividade;

	@FXML
	private TextField txtVdRegiao;

	@FXML
	private Label lblVdRegiao;

	@FXML
	private TextField txtVdConvenio;

	@FXML
	private Label lblVdConvenio;

	@FXML
	private TextField txtVdCliente;

	@FXML
	private Label lblVdCliente;

	@FXML
	private TextField txtVdPlanoPagamento;

	@FXML
	private Label lblVdPlanoPagamento;

	@FXML
	private TextField txtVdMensagemNf;

	@FXML
	private Label lblVdMensagemNf;

	// Tab Finaceiro
	@FXML
	private Tab tabFinanceiro;

	@FXML
	private TextField txtFcSecao;

	@FXML
	private Label lblFcSecao;

	@FXML
	private TextField txtFcMoeda;

	@FXML
	private Label lblFcMoeda;

	@FXML
	private TextField txtFcPortador;

	@FXML
	private Label lblFcPortador;

	@FXML
	private TextField txtFcCentroCusto;

	@FXML
	private Label lblFcCentroCusto;

	@FXML
	private TextField txtFcPlanoConta;

	@FXML
	private Label lblFcPlanoConta;

	@FXML
	private TextField txtFcCaixaBanco;

	@FXML
	private Label lblFcCaixaBanco;

	// Attributes used in the class
	public Empresa empresa = new Empresa();
	public EmpresaDAO empDAO = new EmpresaDAO();
	public SequencialDAO seqDAO = new SequencialDAO();
	static Stage stg;
	public Util util = new Util();

	@FXML
	void actionBtnSave(ActionEvent event) {

		if (!Util.noEmpty(txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante, txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco)) {

			// Compras
			empresa.getSequencial().setCpUnidade(Integer.parseInt(txtCpUnidade.getText()));
			empresa.getSequencial().setCpDepartamento(Integer.parseInt(txtCpDepartamento.getText()));
			empresa.getSequencial().setCpFornecedor(Integer.parseInt(txtCpFornecedor.getText()));
			empresa.getSequencial().setCpFabricante(Integer.parseInt(txtCpFabricante.getText()));
			empresa.getSequencial().setCpGrupo(Integer.parseInt(txtCpGrupo.getText()));
			empresa.getSequencial().setCpSubGrupo(Integer.parseInt(txtCpSubGrupo.getText()));
			empresa.getSequencial().setCpCorEstilo(Integer.parseInt(txtCpCorEstilo.getText()));
			empresa.getSequencial().setCpBoxLocalEstoque(Integer.parseInt(txtCpBoxLocalEstoque.getText()));
			empresa.getSequencial().setCpTabelaLcServico(Integer.parseInt(txtCpTabelaLcServico.getText()));
			empresa.getSequencial().setCpTabelaNCM(Integer.parseInt(txtCpTabelaNCM.getText()));
			empresa.getSequencial().setCpItem(Integer.parseInt(txtCpItem.getText()));
			empresa.getSequencial().setCpFamiliaPreco(Integer.parseInt(txtCpFamiliaPreco.getText()));
			empresa.getSequencial().setCpTributacao(Integer.parseInt(txtCpTributacao.getText()));
			empresa.getSequencial().setCpAplicacao(Integer.parseInt(txtCpAplicacao.getText()));
			empresa.getSequencial().setCpGrade(Integer.parseInt(txtCpGrade.getText()));

			// Vendas
			empresa.getSequencial().setVdPais(Integer.parseInt(txtVdPais.getText()));
			empresa.getSequencial().setVdUF(Integer.parseInt(txtVdUF.getText()));
			empresa.getSequencial().setVdCidade(Integer.parseInt(txtVdCidade.getText()));
			empresa.getSequencial().setVdRota(Integer.parseInt(txtVdRota.getText()));
			empresa.getSequencial().setVdRamoAtividade(Integer.parseInt(txtVdRamoAtividade.getText()));
			empresa.getSequencial().setVdRegiao(Integer.parseInt(txtVdRegiao.getText()));
			empresa.getSequencial().setVdConvenio(Integer.parseInt(txtVdConvenio.getText()));
			empresa.getSequencial().setVdCliente(Integer.parseInt(txtVdCliente.getText()));
			empresa.getSequencial().setVdPlanoPagamento(Integer.parseInt(txtVdPlanoPagamento.getText()));
			empresa.getSequencial().setVdMensagemNf(Integer.parseInt(txtVdMensagemNf.getText()));

			// Finaceiro
			empresa.getSequencial().setFcSecao(Integer.parseInt(txtFcSecao.getText()));
			empresa.getSequencial().setFcMoeda(Integer.parseInt(txtFcMoeda.getText()));
			empresa.getSequencial().setFcPortador(Integer.parseInt(txtFcPortador.getText()));
			empresa.getSequencial().setFcCentroCusto(Integer.parseInt(txtFcCentroCusto.getText()));
			empresa.getSequencial().setFcPlanoConta(Integer.parseInt(txtFcPlanoConta.getText()));
			empresa.getSequencial().setFcCaixaBanco(Integer.parseInt(txtFcCaixaBanco.getText()));

			if (1 == 1) {

				Task<String> tarefaCargaPg = new Task<String>() {
					@Override
					protected String call() throws Exception {
						seqDAO.update(empresa.getSequencial());
						return "-";
					}

					@Override
					protected void succeeded() {
						stg.close();
					}

					@Override
					protected void failed() {
						stg.close();
						util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCnxDb"), "error");
					}

					@Override
					protected void cancelled() {

						super.cancelled();
					}
				};
				Thread t = new Thread(tarefaCargaPg);
				t.setDaemon(true);
				t.start();
				stg = ProgressBarForm.showProgressBar(EmpresaController.class, tarefaCargaPg,
						DadosGlobais.resourceBundle.getString("infConsulRegist"), false);

			}

		}

	}

	@FXML
	void actionBtnCancel(ActionEvent event) {

		Util.setDefaultStyle(txtCodigo,txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante, txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco);
		tarefaConsulta("current");

		btnSave.setDisable(true);
		txtCodigo.requestFocus();

	}

	@FXML
	void keyPressedTxtCodigo(KeyEvent event) {

		// Util.setDefaultStyle(txtCodigo);

		if ((event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.TAB)) && !Util.noEmpty(txtCodigo))
			tabPane.requestFocus();

		if (event.getCode().equals(KeyCode.F2)) {

			// try {
			// stg = new Stage();
			// Scene scene = new
			// Scene(util.openWindow("/views/utils/viewSearch.fxml",new
			// SearchController()));
			// scene.getStylesheets().add(getClass().getResource("/styles/css/themes
			// "+DadosGlobais.empresaLogada.getSistema()+"/style_"+DadosGlobais.empresaLogada.getSistema()+".css").toExternalForm());
			// stg.setScene(scene);
			// stg.initStyle(StageStyle.TRANSPARENT);
			// stg.initModality(Modality.APPLICATION_MODAL);
			// stg.show();
			// } catch (IOException e) {
			// util.tratamentoExcecao(e.getMessage().toString(), "[
			// ParametrosController.keyPressedTxtCodigo() ]");
			// }
		}
	}

	@FXML
	void keyTypedTxtCodigo(KeyEvent event) {
		// Util.isLetter(event);
	}

	public void saveDataOnEnter(TextField... textFields) {
		for (TextField txt : textFields) {

			txt.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				if (event.getCode().equals(KeyCode.ENTER)) {
					actionBtnSave(null);
				}
			});
		}
	}

	public void setManutencaoParametros() {

		txtCodigo.setText(String.valueOf(empresa.getCodemp()));
		txtNome.setText(empresa.getNomeFantasia());

		// Compras
		txtCpUnidade.setText(String.valueOf(empresa.getSequencial().getCpUnidade()));
		txtCpDepartamento.setText(String.valueOf(empresa.getSequencial().getCpDepartamento()));
		txtCpFornecedor.setText(String.valueOf(empresa.getSequencial().getCpFornecedor()));
		txtCpFabricante.setText(String.valueOf(empresa.getSequencial().getCpFabricante()));
		txtCpGrupo.setText(String.valueOf(empresa.getSequencial().getCpGrupo()));
		txtCpSubGrupo.setText(String.valueOf(empresa.getSequencial().getCpSubGrupo()));
		txtCpCorEstilo.setText(String.valueOf(empresa.getSequencial().getCpCorEstilo()));
		txtCpBoxLocalEstoque.setText(String.valueOf(empresa.getSequencial().getCpBoxLocalEstoque()));
		txtCpTabelaLcServico.setText(String.valueOf(empresa.getSequencial().getCpTabelaLcServico()));
		txtCpTabelaNCM.setText(String.valueOf(empresa.getSequencial().getCpTabelaNCM()));
		txtCpItem.setText(String.valueOf(empresa.getSequencial().getCpItem()));
		txtCpFamiliaPreco.setText(String.valueOf(empresa.getSequencial().getCpFamiliaPreco()));
		txtCpTributacao.setText(String.valueOf(empresa.getSequencial().getCpTributacao()));
		txtCpAplicacao.setText(String.valueOf(empresa.getSequencial().getCpAplicacao()));
		txtCpGrade.setText(String.valueOf(empresa.getSequencial().getCpGrade()));

		// Vendas
		txtVdPais.setText(String.valueOf(empresa.getSequencial().getVdPais()));
		txtVdUF.setText(String.valueOf(empresa.getSequencial().getVdUF()));
		txtVdCidade.setText(String.valueOf(empresa.getSequencial().getVdCidade()));
		txtVdRota.setText(String.valueOf(empresa.getSequencial().getVdRota()));
		txtVdRamoAtividade.setText(String.valueOf(empresa.getSequencial().getVdRamoAtividade()));
		txtVdRegiao.setText(String.valueOf(empresa.getSequencial().getVdRegiao()));
		txtVdConvenio.setText(String.valueOf(empresa.getSequencial().getVdConvenio()));
		txtVdCliente.setText(String.valueOf(empresa.getSequencial().getVdCliente()));
		txtVdPlanoPagamento.setText(String.valueOf(empresa.getSequencial().getVdPlanoPagamento()));
		txtVdMensagemNf.setText(String.valueOf(empresa.getSequencial().getVdMensagemNf()));

		// Finaceiro
		txtFcSecao.setText(String.valueOf(empresa.getSequencial().getFcSecao()));
		txtFcMoeda.setText(String.valueOf(empresa.getSequencial().getFcMoeda()));
		txtFcPortador.setText(String.valueOf(empresa.getSequencial().getFcPortador()));
		txtFcCentroCusto.setText(String.valueOf(empresa.getSequencial().getFcCentroCusto()));
		txtFcPlanoConta.setText(String.valueOf(empresa.getSequencial().getFcPlanoConta()));
		txtFcCaixaBanco.setText(String.valueOf(empresa.getSequencial().getFcCaixaBanco()));

	}

	public void tarefaConsulta(String tipoConsulta) {

		Task<String> TarefaRefresh = new Task<String>() {
			@Override
			protected String call() throws Exception {

				if (tipoConsulta.equals("filter"))
					empresa = empDAO.getEmpresaSeq(Integer.valueOf(txtCodigo.getText()));
				else
					empresa = empDAO.getEmpresaSeq(DadosGlobais.empresaLogada.getCodemp());

				return "-";
			}

			@Override
			protected void succeeded() {
				stg.close();

				if (empresa != null && empresa.getSequencial() != null) {
					btnSave.setDisable(false);
					setManutencaoParametros();
					if (tipoConsulta.equals("current"))
						btnSave.setDisable(true);
				} else {
					util.showAlert(DadosGlobais.resourceBundle.getString("alertErrorCodigoNull"), "error");

				}

			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Erro ao Conectar ao Banco de Dados, verifique a conexão de rede!", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}
		};
		Thread t = new Thread(TarefaRefresh);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(ParametrosController.class, TarefaRefresh, "Consultando Registros",
				false);
	}

	public ParametrosController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		// Set focus to txtCodigo
		Util.setFocus(txtCodigo);

		// Set default style of Node on in event onClick
		Util.defaultStyle(txtCodigo, txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante, txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco);

		txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue && !Util.noEmpty(txtCodigo))
					tarefaConsulta("filter");
			}
		});

		// Limit number of characters
		Util.maxCharacters(8, txtCodigo,txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante,  txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco);

		// Validate write only numbers
		Util.onlyNumbers(txtCodigo, txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante, txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco);

		// Set default style of Node on in event keyPressed
		Util.setKeyPressDefaultStyles(txtCodigo, txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante, txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco);
		
		Util.setStyleOnFocus(txtCodigo, txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante, txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco);

		saveDataOnEnter(txtCpUnidade, txtCpDepartamento, txtCpFornecedor, txtCpFabricante, txtCpGrupo, txtCpSubGrupo,
				txtCpCorEstilo, txtCpBoxLocalEstoque, txtCpTabelaLcServico, txtCpTabelaNCM, txtCpItem, txtCpFamiliaPreco, txtCpTributacao,
				txtCpAplicacao, txtCpGrade, txtVdPais, txtVdUF, txtVdCidade, txtVdRota, txtVdRamoAtividade, txtVdRegiao, txtVdConvenio, txtVdCliente,
				txtVdPlanoPagamento, txtVdMensagemNf, txtFcSecao, txtFcMoeda, txtFcPortador, txtFcCentroCusto, txtFcPlanoConta, txtFcCaixaBanco);

		// Set default data
		actionBtnCancel(null);

		// ShortCuts Keys
		anchorPanePrincipal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			switch (event.getCode()) {

			case F6:
				if (!btnSave.isDisable())
					actionBtnSave(null);
				break;

			case F8:
				if (!btnCancel.isDisable())
					actionBtnCancel(null);
				break;

			default:
				break;
			}
		});

	}

}
