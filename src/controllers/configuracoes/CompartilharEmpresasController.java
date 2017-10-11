package controllers.configuracoes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.CustomTextField;
import controllers.application.TelaPrincipalEptusController;
import controllers.utils.ProgressBarForm;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.configuracoes.Compartilhamento;
import models.configuracoes.CompartilhamentoDAO;
import models.configuracoes.CompartilhamentoPK;
import models.configuracoes.Empresa;
import models.configuracoes.EmpresaDAO;
import tools.controls.ComboBoxFilter;
import tools.criptografia.EncryptMD5;
import tools.enums.EnumCompartilhamento;
import tools.enums.EnumLogRetornoStatus;
import tools.utils.LogRetorno;
import tools.utils.Util;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

public class CompartilharEmpresasController implements Initializable {

	@FXML
	private AnchorPane paneFormCadGrupos;

	@FXML
	private ToolBar toolBarCadUnid;

	@FXML
	private Button btnSave, btnCancel;

	@FXML
	private Label lblCodigo, lblEmpresa;

	@FXML
	private TextField txtEmpresa;// txtCodigoEmp, txtEmpresa;

	@FXML
	private CheckComboBox<ComboBoxFilter> cBoxEmpresas;

	@FXML
	private TreeTableView<PermissoesCompartilhados> treeView;
	
	@FXML
	private TreeTableColumn<PermissoesCompartilhados, String> colModulos;

	@FXML
	private TreeTableColumn<PermissoesCompartilhados, Boolean> colCompartilhar;

	@FXML
	private TreeTableColumn<PermissoesCompartilhados, String> colEmpresas;

	@FXML
	private CustomTextField txtCodigoEmp;

	private MenuBar mnBarPrincipal;
	MenuBar menu = null;
	static Stage stg;
	private LogRetorno logRet;
	private Util util = new Util();

	// --------------- TREE TABLEVIEW --------------------
	boolean mnExistBD = false;
	private TreeItem<PermissoesCompartilhados> itemRaiz = new TreeItem<PermissoesCompartilhados>();	
	PermissoesCompartilhados permCompartilhados;
	// -------------- Listas -----------------------------	
	List<Compartilhamento> listSaveShared;
	List<Compartilhamento> listComp;// lista com dados do banco de dados
	List<String> listIdMenu;
	Empresa empresa;
	
//	Object obj;

	public CompartilharEmpresasController(MenuBar mnBarPrincipal) {
		// TODO Auto-generated constructor stub
		this.mnBarPrincipal = mnBarPrincipal;
	}

	
	public void listSaveChange(ObservableList<?> item, String empCompartilhar){

		for(int i=0; i < item.size(); i++){
			if(item.get(i) instanceof TreeItem){				
				if(((TreeItem)item.get(i)).getChildren().size() > 0){
					listSaveChange(((TreeItem)item.get(i)).getChildren(), empCompartilhar);
				}else{					
					if( ((PermissoesCompartilhados)((TreeItem)item.get(i)).getValue()).getPermissoes() == true){

						if(listSaveShared == null){
							listSaveShared = new ArrayList<Compartilhamento>();
						}

						PermissoesCompartilhados permisChange = ((PermissoesCompartilhados)((TreeItem)item.get(i)).getValue());
						CompartilhamentoPK compartPK = new CompartilhamentoPK();
						compartPK.setCodemp(Integer.parseInt(txtCodigoEmp.getText()));
						compartPK.setModFuncao(permisChange.getIdMenu());
						compartPK.setCheckDelete(permisChange.getCheckDelete());

						Compartilhamento compartilhar = new Compartilhamento();
						compartilhar.setId(compartPK);
						compartilhar.setCodEmpcompartilhada(empCompartilhar);
						listSaveShared.add(compartilhar);
					}
				}				
			}
		}

	}
	@FXML
	void actionBtnSave(ActionEvent event) throws Exception {
		boolean confirmar = false;
		boolean alterou = false;
		String empCompartilhar = txtCodigoEmp.getText();
		try {

			for (ComboBoxFilter c : cBoxEmpresas.getCheckModel().getCheckedItems()) {
				empCompartilhar = empCompartilhar + "," + c.getField();
			}


			listSaveChange(treeView.getRoot().getChildren(), empCompartilhar);

			if (listSaveShared !=null) {
				confirmar = util.showAlert("Vai alterar o compartilhamento da empresa?", "confirmation");

				if (confirmar) {
					Task<String> tarefaCargaPg = new Task<String>() {
						@Override
						protected String call() throws Exception {

							try {
								
								logRet = CompartilhamentoDAO.class.newInstance().updateCompartilhados(listSaveShared);
								
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return "-";
						}

						@Override
						protected void succeeded() {
							stg.close();
							if (logRet.getStatus().equals(EnumLogRetornoStatus.SUCESSO)) {
								Util.showNotification(logRet.getMsg());
//									util.alertException(logRet.getMsg(), null, false);
							}

							listSaveShared=null;
							try {
								carregarDados();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							cBoxEmpresas.getCheckModel().clearChecks();
							cBoxEmpresas.setDisable(false);
							treeView.refresh();
						}

						@Override
						protected void failed() {
							stg.close();
							util.showAlert("Error de conexion", "error");
						}

						@Override
						protected void cancelled() {
							super.cancelled();
						}

					};
					Thread t = new Thread(tarefaCargaPg);
					t.setDaemon(true);
					t.start();
					stg = ProgressBarForm.showProgressBar(CompartilharEmpresasController.class, tarefaCargaPg,
							"Gravando informação do Compartilhamento", false);

				}else{
//					actionBtnCancel(null);
				}
					
			} else {
				util.showAlert("Não houver alterações no compartilhamento.", "information");
				carregarDados();
			}
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}


	@FXML
	void actionBtnCancel(ActionEvent event) {
		Task<String> tarefaCargaPg = new Task<String>() {
			@Override
			protected String call() throws Exception {
				carregarDados();

				return "-";
			}

			@Override
			protected void succeeded() {				
				stg.close();
				cBoxEmpresas.getCheckModel().clearChecks();
				cBoxEmpresas.setDisable(false);
				treeView.refresh();
			}

			@Override
			protected void failed() {
				stg.close();
				util.showAlert("Error de conexion", "error");
			}

			@Override
			protected void cancelled() {
				super.cancelled();
			}

		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
		stg = ProgressBarForm.showProgressBar(CompartilharEmpresasController.class, tarefaCargaPg,
				"Cancelando informação do Compartilhamento", false);

	}
	
	/**
	 * COMBOBOX TIPO OPERAÇÃO
	 */
	public void populaCboxEmp(int codigoEmp) {

		// ArrayList to fill comboBox cbkTipoOperacion
		ObservableList<ComboBoxFilter> empresas = FXCollections.observableArrayList();

		EmpresaDAO o_empDao = new EmpresaDAO();

		List<Empresa> listEmpresa = o_empDao.getEmpresas(codigoEmp);
		cBoxEmpresas.getItems().clear();

		for (int i = 0; i < listEmpresa.size(); i++) {
			empresas.add(new ComboBoxFilter(
					"[" + listEmpresa.get(i).getCodigo().toString() + "] - " + (listEmpresa.get(i).getNomeFantasia()),
					1, (listEmpresa.get(i).getCodigo().toString())));
		}

		cBoxEmpresas.getItems().addAll(empresas);

		cBoxEmpresas.setConverter(new StringConverter<ComboBoxFilter>() {
			@Override
			public String toString(ComboBoxFilter object) {
				// TODO Auto-generated method stub
				if (object != null) {
					return object.getValue();
				} else {
					return "";
				}
			}

			@Override
			public ComboBoxFilter fromString(String string) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	/**
	 * METODO PRA TRANSFORMAR O VALOR DO COMBOBOX NO SEU ID
	 * 
	 * @param empCompartilhadas
	 *            VALOR DO ITEM
	 * @return
	 */
	public String codigoConverteString(String empCompartilhadas) {
		List<Empresa> empresas = null;
		String codigoEmp[] = empCompartilhadas.split(",");
		String result = null;

		try {
			empresas = EmpresaDAO.class.newInstance().getListEmpresa();
			for (int i = 0; i < codigoEmp.length; i++) {
				for (Empresa empresa : empresas) {
					if (Integer.parseInt(codigoEmp[i]) == empresa.getCodemp()) {
						if (result == null) {
							result = empresa.getCodemp().toString();
						} else
							result = result + ", " + empresa.getCodemp();
						break;
					}
				}
			}

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "[ " + result + " ]";
	}

	/**
	 * METODO RETORNA EMPRESA PESQUIZADA
	 * 
	 * @return
	 */
	public Empresa searchEmpresa() {	

		try {
			logRet = EmpresaDAO.class.newInstance().getById(Integer.parseInt(txtCodigoEmp.getText()));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Empresa) logRet.getObjeto();
	}

	/**
	 * METODO INSERI OS MENUS NOVOS ADICIONADOS E LIMPA OS QUE NÃO EXISTAM NO
	 * BANCO DE DADOS
	 * 
	 * @param empresa
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public boolean validarInformacion(Empresa empresa)
			throws InstantiationException, IllegalAccessException, Exception {
		List<String> listMenuInserir = null;
		List<Compartilhamento> listMenuEliminar = null;
		boolean alterado = false;
		
		for (int i = 0; i < listIdMenu.size(); i++) {
			for (Compartilhamento compartilhamento : listComp) {
				if (listIdMenu.get(i).equals(compartilhamento.getId().getModFuncao())) {
					mnExistBD = true;
					break;
				}
			}
			if (!mnExistBD) {
				if (listMenuInserir == null) {
					listMenuInserir = new ArrayList<String>();
				}
				listMenuInserir.add(listIdMenu.get(i));
			}

			mnExistBD = false;
		}

		//PROCURAR LIXOS NA TABELA PARA UMA EMPRESA
		for (int y = 0; y < listComp.size(); y++) {
			for (int i = 0; i < listIdMenu.size(); i++) {
				if (listIdMenu.get(i).equals(listComp.get(y).getId().getModFuncao())) {
					mnExistBD = true;
					break;
				}
			}

			if (!mnExistBD) {
				if (listMenuEliminar == null) {
					listMenuEliminar = new ArrayList<Compartilhamento>();
				}
				listMenuEliminar.add(listComp.get(y));
			}
			mnExistBD = false;

		}

		//UPDATE TABELA DE COMPARTILHAMENTO PARA UMA EMPRESA
		if (listMenuInserir != null) {
			alterado = true;
			CompartilhamentoDAO.class.newInstance().inserirDadosCompartilhados(empresa.getCodemp(),
					empresa.getCheckDelete(), listMenuInserir);
			listMenuInserir.clear();
		}
		//EXCLUIR LIXO DA TABELA PARA UMA EMPRESA
		if (listMenuEliminar != null) {
			alterado = true;
			CompartilhamentoDAO.class.newInstance().deleteMenu(listMenuEliminar);
			listMenuEliminar.clear();
		}

		return alterado;
	}

	/**
	 * METODO PARA CARREGAR A INFORMAÇÃO DO TREETABLEVIEW
	 * 
	 * @throws Exception
	 */
	public void carregarDados() throws Exception {
		boolean resultValidate = false;
		try {
			listComp = CompartilhamentoDAO.class.newInstance().getCompartilhamentosById(empresa.getCodemp(),
					empresa.getCheckDelete());

			resultValidate = validarInformacion(empresa);
			if (resultValidate) {
				listComp = CompartilhamentoDAO.class.newInstance().getCompartilhamentosById(empresa.getCodemp(),
						empresa.getCheckDelete());
			}

			recursiveChangeTree(treeView.getRoot().getChildren());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createTreeView() {

		TreeItem<PermissoesCompartilhados> moduloCompras = new TreeItem<>(new PermissoesCompartilhados(null, "", "Compras", false, ""));		
		TreeItem<PermissoesCompartilhados> moduloVendas = new TreeItem<>(new PermissoesCompartilhados(null, "", "Vendas", false, ""));
		//		TreeItem<PermissoesCompartilhados> moduloFinanceiro = new TreeItem<>(new PermissoesCompartilhados(null, "", "Financeiro", false, ""));
		//		TreeItem<PermissoesCompartilhados> moduloProduc = new TreeItem<>(new PermissoesCompartilhados(null, "", "Produção", false, ""));
		//		TreeItem<PermissoesCompartilhados> moduloServic = new TreeItem<>(new PermissoesCompartilhados(null, "", "Serviços", false, ""));
		//		TreeItem<PermissoesCompartilhados> moduloLivFiscais = new TreeItem<>(new PermissoesCompartilhados(null, "", "Livros Fiscais", false, ""));
		//		TreeItem<PermissoesCompartilhados> moduloContab = new TreeItem<>(new PermissoesCompartilhados(null, "", "Contabilidade", false, ""));
		//		TreeItem<PermissoesCompartilhados> moduloRH = new TreeItem<>(new PermissoesCompartilhados(null, "", "Recursos Humanos", false, ""));
		TreeItem<PermissoesCompartilhados> moduloConfig = new TreeItem<>(new PermissoesCompartilhados(null, "", "Configurações", false, ""));


		//MODULOS COMPRAS	
		TreeItem<PermissoesCompartilhados> itemItem = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.ITENS.toString()), "Itens", false, ""));	
		TreeItem<PermissoesCompartilhados> itemUnidade = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.UNIDADES.toString()), "Unidades", false, ""));
		TreeItem<PermissoesCompartilhados> itemGrupo = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.GRUPOS.toString()), "Grupos", false, ""));
		TreeItem<PermissoesCompartilhados> itemSubGrup = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.SUBGRUPOS.toString()), "Sub - Grupos", false, ""));	 
		TreeItem<PermissoesCompartilhados> itemFabricant = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.FABRICANTES.toString()), "Fabricantes", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemDepart = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.DEPARTAMENTOS.toString()), "Departamentos", false, ""));	 
		TreeItem<PermissoesCompartilhados> itemGradProd = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.GRADE_DE_PRODUTOS.toString()), "Grades de Produtos", false, ""));
		TreeItem<PermissoesCompartilhados> itemCoresEst = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.COR_ESTILOS.toString()), "Cores / Estilos", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemFornec = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.FORNECEDORES.toString()), "Fornecedores", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemTributa = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.TRIBUTACAO.toString()), "Tributação", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemAplicacoes = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.APLICACOES.toString()), "Aplicações", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemFamPred = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.FAMILIAS_DE_PRECOS.toString()), "Familias de Precificação", false, ""));		 
//		TreeItem<PermissoesCompartilhados> itemDeposito = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.DEPOSITOS_LOCAL_ESTOQUE.toString()), "Depósitos - Local Estoque", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemTabServic = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.TABELA_LC_SERVICOS.toString()), "Tabela Serviços LC 116/03", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemProdut = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.TABELA_NCM.toString()), "Tabela Produtos NCM", false, ""));		 
		TreeItem<PermissoesCompartilhados> itemOpeEntrada = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.OPERACOES_DE_ENTRADA.toString()), "Operações de Entrada", false, ""));			
		moduloCompras.getChildren().setAll(itemItem, itemUnidade, itemGrupo, itemSubGrup, itemFabricant, itemDepart, itemGradProd, itemCoresEst, itemFornec, itemAplicacoes, itemFamPred, itemTabServic, itemProdut, itemOpeEntrada);

		//MODULO VENDAS
		TreeItem<PermissoesCompartilhados> itemCliente = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.CLIENTES.toString()), "Clientes", false, ""));	 
		TreeItem<PermissoesCompartilhados> itemCidade = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.CIDADES.toString()), "Cidades", false, ""));
		TreeItem<PermissoesCompartilhados> itemUF = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.UF.toString()), "UF", false, ""));
		TreeItem<PermissoesCompartilhados> itemPais = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.PAIS.toString()), "País", false, ""));
		TreeItem<PermissoesCompartilhados> itemOpeSaida= new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.OPERACOES_DE_SAIDA.toString()), "Operações de Saida", false, ""));
		moduloVendas.getChildren().setAll(itemCliente, itemCidade, itemUF, itemPais, itemOpeSaida);

		//MODULO CONFIGURAÇÕES
		TreeItem<PermissoesCompartilhados> itemUsuario = new TreeItem<>(new PermissoesCompartilhados(null, EncryptMD5.getMD5(EnumCompartilhamento.USUARIOS.toString()), "Usuários", false, ""));
		moduloConfig.getChildren().addAll(itemUsuario);


		//		itemCompra.getChildren().add(itemCadastro);
		//		itemRaiz.getChildren().addAll(moduloCompras, moduloVendas, moduloFinanceiro, moduloProduc, moduloServic, moduloLivFiscais, moduloContab, moduloRH, moduloConfig);
		itemRaiz.getChildren().addAll(moduloCompras, moduloVendas, moduloConfig);

		colModulos = new TreeTableColumn<PermissoesCompartilhados, String>("Modulos");
		colModulos.setCellValueFactory((CellDataFeatures<PermissoesCompartilhados, String> p) -> new ReadOnlyStringWrapper(p.getValue().getValue().getMenu()));

		colCompartilhar = new TreeTableColumn<PermissoesCompartilhados, Boolean>("Compartilhar");	
		colCompartilhar.setCellValueFactory((CellDataFeatures<PermissoesCompartilhados, Boolean> p) -> new ReadOnlyBooleanWrapper(p.getValue().getValue().getPermissoes()));
		colCompartilhar.setCellFactory(
				new Callback<TreeTableColumn<PermissoesCompartilhados, Boolean>, TreeTableCell<PermissoesCompartilhados, Boolean>>() {
					@Override
					public TreeTableCell<PermissoesCompartilhados, Boolean> call(
							TreeTableColumn<PermissoesCompartilhados, Boolean> p) {
						CheckBoxTree check = new CheckBoxTree();
						check.setAlignment(Pos.CENTER);
						return check;
					}
				});

		colEmpresas = new TreeTableColumn<PermissoesCompartilhados, String>("Empresas Compartilhadas");
		colEmpresas.setCellValueFactory(
				(CellDataFeatures<PermissoesCompartilhados, String> p) -> new ReadOnlyStringWrapper(
						p.getValue().getValue().getEmpCompartilhadas()));

		treeView.setRoot(itemRaiz);
		treeView.getColumns().remove(0, treeView.getColumns().size());
		treeView.getColumns().addAll(colModulos, colCompartilhar, colEmpresas);
		treeView.setShowRoot(false);

	}



	public void recursive(ObservableList<?> childrens){		
		for(int i=0; i < childrens.size(); i++){
			if(childrens.get(i) instanceof TreeItem){
				if(listIdMenu == null){
					listIdMenu = new ArrayList<String>();
				}
				if(((TreeItem)childrens.get(i)).getChildren().size() > 0){					
					recursive(((TreeItem)childrens.get(i)).getChildren());
				}else{					
					listIdMenu.add(((PermissoesCompartilhados)((TreeItem)childrens.get(i)).getValue()).getIdMenu());
				}					
			}
		}
	}

	public void updateInformationTree(TreeItem itemMenu){		
		for(Compartilhamento compartilhado : listComp){
			if(compartilhado.getId().getModFuncao().equals(((PermissoesCompartilhados)itemMenu.getValue()).getIdMenu())){				
				PermissoesCompartilhados perm = ((PermissoesCompartilhados)itemMenu.getValue());
				perm.setCheckDelete(compartilhado.getId().getCheckDelete());
				perm.setEmpCompartilhadas(fixListSharedShow(compartilhado.getCodEmpcompartilhada()));
				perm.setPermissoes(new SimpleBooleanProperty(false));
				itemMenu.setValue(perm);
				break;
			}
			else if(itemMenu.getChildren().size() > 0 ){				
				PermissoesCompartilhados perm = ((PermissoesCompartilhados)itemMenu.getValue());				
				perm.setPermissoes(new SimpleBooleanProperty(false));
				itemMenu.setValue(perm);
				break;
			}
		}

	}

	public void recursiveChangeTree(ObservableList<?> childrens){			
		for(int i=0; i < childrens.size(); i++){
			if(childrens.get(i) instanceof TreeItem){
				if(((TreeItem)childrens.get(i)).getChildren().size() > 0){	
					recursiveChangeTree(((TreeItem)childrens.get(i)).getChildren());
					updateInformationTree(((TreeItem)childrens.get(i)));
				}else{
					updateInformationTree(((TreeItem)childrens.get(i)));
				}					
			}
		}
	}

	public String fixListSharedShow(String empCompartilhadas){
		String codigos[] = empCompartilhadas.split(",");
		String result = null;
		
		for(int i=0; i <codigos.length; i++){
			if(result == null){
				result = codigos[i];
			}else{
				result = result+", "+codigos[i];
			}				
		}
		return "[ "+result+" ]";
	}
	
	/**
	 * EXIBE O FORMULARIO DE BUSCA DE departamento
	 */
	public void showSearchEmpresas() {
		
		ObservableList<ComboBoxFilter> list = FXCollections.observableArrayList();

		list.add(new ComboBoxFilter("Código", 1, "codigo"));
		list.add(new ComboBoxFilter("Nome de Fantasia", 2, "nomeFantasia"));
		list.add(new ComboBoxFilter("CNPJ", 3, "cnpj"));
		
		Object obj = util.showSearchGetParameters("Empresa", "nomeFantasia", EmpresaDAO.class, list);
		
		if(obj != null){
			empresa = (Empresa)obj;			
		}
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtCodigoEmp.requestFocus();
			}
		});

		treeView.setDisable(true);

		createTreeView();

		// TESTE DE ELEMENTO CUSTOMTEXTFIELD COM UM BOTÃO DENTRO
		Button btn1 = new Button();
		btn1.setStyle("-fx-background-color : transparent; -fx-padding : 0;");
		FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		icon.setFill(Color.web("#757575"));
		icon.setSize("16px");
		btn1.setGraphic(icon);
		txtCodigoEmp.setRight(btn1);		

		Method mt;

		try {
			mt = TelaPrincipalEptusController.class.getMethod("mnBar", MenuBar.class);
			menu = (MenuBar) mt.invoke(TelaPrincipalEptusController.class.newInstance(), mnBarPrincipal);
			// createTree(pae, itemsPaes, m.getMenus());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		txtCodigoEmp.setOnKeyPressed(key -> {
			if (key.getCode().equals(KeyCode.ENTER) || key.getCode().equals(KeyCode.TAB)) {

				if (txtCodigoEmp.getText().equals("")) {
					txtCodigoEmp.requestFocus();
					util.showAlert("Insira o codigo da empresa a pesquizar.", "warning");

				} else {					

					recursive(treeView.getRoot().getChildren());

					Task<String> tarefaCargaPg = new Task<String>() {
						@Override
						protected String call() throws Exception {
							empresa = searchEmpresa();
							return "-";
						}

						@Override
						protected void succeeded() {
							stg.close();
							if (empresa != null) {
								txtEmpresa.setText(empresa.getNomeFantasia());

								Task<String> tarefaCargaPg1 = new Task<String>() {
									@Override
									protected String call() throws Exception {
										populaCboxEmp(Integer.parseInt(txtCodigoEmp.getText()));
										carregarDados();
										return "-";
									}

									@Override
									protected void succeeded() {
										stg.close();
										treeView.setDisable(false);
										treeView.refresh();
									}

									@Override
									protected void failed() {
										stg.close();
										util.showAlert("Error de conexion", "error");
									}

									@Override
									protected void cancelled() {
										super.cancelled();
									}

								};
								Thread t1 = new Thread(tarefaCargaPg1);
								t1.setDaemon(true);
								t1.start();
								stg = ProgressBarForm.showProgressBar(CompartilharEmpresasController.class,
										tarefaCargaPg1, "Carregando informação do Compartilhamento", false);

							} else {
								util.showAlert("Empresa não encontrada.", "information");
								txtCodigoEmp.requestFocus();
							}
						}

						@Override
						protected void failed() {
							stg.close();
							util.showAlert("Error de conexion", "error");
						}

						@Override
						protected void cancelled() {
							super.cancelled();
						}

					};
					Thread t = new Thread(tarefaCargaPg);
					t.setDaemon(true);
					t.start();
					stg = ProgressBarForm.showProgressBar(CompartilharEmpresasController.class, tarefaCargaPg,
							"Carregando informação do Compartilhamento", false);

				}
			}
			else if(key.getCode().equals(KeyCode.F2)){
				showSearchEmpresas();
				if(empresa !=null){
					txtCodigoEmp.setText(String.valueOf(empresa.getCodigo()));
					txtEmpresa.setText(empresa.getNomeFantasia());
					recursive(treeView.getRoot().getChildren());
					populaCboxEmp(empresa.getCodigo());
					try {
						carregarDados();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					treeView.setDisable(false);
					treeView.refresh();
				}
			}
		});
	}

	// ----------------------------------------------------------------------------------------------------------------------
	class PermissoesCompartilhados {
		private BigInteger checkDelete;
		private String idMenu;
		private String menu;
		private SimpleBooleanProperty permissoes;
		private String empCompartilhadas;

		public PermissoesCompartilhados(BigInteger checkDelete, String idMenu, String menu, boolean permissoes,
				String empCompartilhadas) {
			super();
			this.checkDelete = checkDelete;
			this.idMenu = idMenu;
			this.menu = menu;
			this.permissoes = new SimpleBooleanProperty(permissoes);
			this.empCompartilhadas = empCompartilhadas;
		}



		public BigInteger getCheckDelete() {
			return checkDelete;
		}



		public void setCheckDelete(BigInteger checkDelete) {
			this.checkDelete = checkDelete;
		}



		/**
		 * @return the idMenu
		 */
		public String getIdMenu() {
			return idMenu;
		}

		/**
		 * @param idMenu
		 *            the idMenu to set
		 */
		public void setIdMenu(String idMenu) {
			this.idMenu = idMenu;
		}

		/**
		 * @return the menu
		 */
		public String getMenu() {
			return menu;
		}

		/**
		 * @param menu
		 *            the menu to set
		 */
		public void setMenu(String menu) {
			this.menu = menu;
		}

		/**
		 * @return the permissoes
		 */
		public SimpleBooleanProperty getPermissoesProperty() {
			return permissoes;
		}

		public Boolean getPermissoes() {
			return permissoes.get();
		}

		/**
		 * @param permissoes
		 *            the permissoes to set
		 */
		public void setPermissoes(SimpleBooleanProperty permissoes) {
			this.permissoes = permissoes;
		}

		/**
		 * @return the empCompartilhadas
		 */
		public String getEmpCompartilhadas() {
			return empCompartilhadas;
		}

		/**
		 * @param empCompartilhadas
		 *            the empCompartilhadas to set
		 */
		public void setEmpCompartilhadas(String empCompartilhadas) {
			this.empCompartilhadas = empCompartilhadas;
		}

		@Override
		public String toString() {
			return "PermissoesCompartilhados [checkDelete=" + checkDelete + ", idMenu=" + idMenu + ", menu=" + menu
					+ ", permissoes=" + permissoes + ", empCompartilhadas=" + empCompartilhadas + "]";
		}

	}

	// ------------------------------------------------------------------------------
	// DESTA FORMA O SCROLL NAO ALTERA A INFORMAÇÃO QUE TINHA O CHECKBOXTREE
	// --------------------------------------------------------------------------
	class CheckBoxTree extends TreeTableCell<PermissoesCompartilhados, Boolean> {

		private final CheckBox check = new CheckBox();

		public CheckBoxTree() {
			check.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub

					cBoxEmpresas.setDisable(true);
					btnSave.setDisable(false);
					getTreeTableView().getTreeItem(getIndex()).getValue()
					.setPermissoes(new SimpleBooleanProperty(check.isSelected()));

					String empCompartilhar = txtCodigoEmp.getText();

					for (ComboBoxFilter c : cBoxEmpresas.getCheckModel().getCheckedItems()) {
						empCompartilhar = empCompartilhar + "," + c.getField();
					}
					if(check.isSelected() && getTreeTableView().getTreeItem(getIndex()).getChildren().size() >0){
						for(int i=0; i < getTreeTableView().getTreeItem(getIndex()).getChildren().size(); i++){	
							//							System.out.println(getTreeTableView().getTreeItem(getIndex()).getChildren().ge);
							getTreeTableView().getTreeItem(getIndex()).getChildren().get(i).getValue().setPermissoes(new SimpleBooleanProperty(true));							
							getTreeTableView().getTreeItem(getIndex()).getChildren().get(i).getValue().setEmpCompartilhadas("[ " + empCompartilhar + " ]");												
						}
					}

					if (check.isSelected() && getTreeTableView().getTreeItem(getIndex()).getChildren().size() == 0) {							
						getTreeTableView().getTreeItem(getIndex()).getValue().setEmpCompartilhadas("[ " + empCompartilhar + " ]");							
					}

					treeView.refresh();	

				}
			});
		}

		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty){
				check.setSelected(item);
				setGraphic(check);
			} else {
				setText(null);
				setGraphic(null);
			}
		}

	}
	// -----------------------------------------------

}
