package controllers.configuracoes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import application.DadosGlobais;
import controllers.utils.ProgressBarForm;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.configuracoes.NivelAcesso;
import models.configuracoes.NivelAcessoDAO;
import models.configuracoes.Usuario;
import models.configuracoes.UsuarioDAO;
import tools.application.ListMenu;
import tools.criptografia.EncryptMD5;
import tools.utils.Util;

public class NivelesAcessoController implements Initializable {

	@FXML
	private AnchorPane anchorPaneNivel;
	@FXML
	private Button btnSearch, btnRefresh;

	@FXML
	private TabPane tabPane;
	
	@FXML
	private Tab tab;

	@FXML
	private TextField txtCodigo, txtNome;

	@FXML
	private Label lblCodigo, lblNome;	

	@FXML
	private MenuBar mnBarPrincipal;

	//	TREETABLEVIEW
	private TreeTableView<ListMenu> treeTableNivelAcesso;
	private TreeTableColumn<ListMenu, String> treeColumnMenu;
	private TreeTableColumn<ListMenu, Boolean> checkColumnVisible;
	private TreeTableColumn<ListMenu, Boolean> checkColumnInsert;
	private TreeTableColumn<ListMenu, Boolean> checkColumnUpdate;
	private TreeTableColumn<ListMenu, Boolean> checkColumnDelete;
	private TreeTableColumn<ListMenu, Boolean> checkColumnReactivete;
	private TreeTableColumn<ListMenu, Boolean> checkColumnPrint;
	
	//	ITENS TREETABLEVIEW
	TreeItem<ListMenu> itemsRoot;
	TreeItem<ListMenu> itemsPaes;
	TreeItem<ListMenu> itemsFilhos;

	// --------- PROGRESS BAR----------------
	static Stage stg;

	// --------- VARIABLES ---------------
	boolean ckActionSelectAll = false;//FLAG PARA CONTROLAR OS CHECKBOX SELECT ALL 
	private int tabPaneSize = 0;//TOTAL DAS ABAS ABERTAS
	private int countTabRemove = 0;//CONTADOR DAS ABAS A FECHAR
	int moduloPae = 0; //PROFUNDIDADE DO MENU NO MOMENTO DE SER PERCORRIDO, SE É 0 (ZERO), É O MODULO
	List<NivelAcesso> listPermisoUsuario = null;//LISTA DAS PERMISSOES DO USUARIONO BANCO DE DADOS
	ListMenu menuPermissao = null;// INSTANCIA DA CLASE LISTMENU
	List<ListMenu> listSavePermissos = null;//LISTA DAS PERMISSOES ALTERADAS
	private Usuario usuarioRastreio;//USUARIO A PESQUIZAR PERMISSÕES 
	Usuario result = null;//USUARIO PESQUIZADO COM A LISTA DAS PERMISSÕES
	Util util = new Util();//INSTANCIA DA CLASE UTIL
	
	/**
	 * Class Constructor
	 * @param mnBar
	 */
	public NivelesAcessoController(MenuBar mnBar) {
		// TODO Auto-generated constructor stub
		this.mnBarPrincipal = mnBar;
	}

	/**
	 * Class Constructor
	 * @param mnBar
	 * @param user
	 */
	public NivelesAcessoController(MenuBar mnBar, Usuario user) {
		// TODO Auto-generated constructor stub
		this.mnBarPrincipal = mnBar;
		this.usuarioRastreio = user;
	}

	@FXML
	void actionSavePermissions(ActionEvent event) throws InstantiationException, IllegalAccessException {
		if (listSavePermissos == null) {
			util.showAlert("Não houver alteração das permissões","information");
		} else {
			boolean confirmar = false;
			confirmar = util.showAlert(DadosGlobais.resourceBundle.getString("NivelesAcessoController.alertConfirm"),"confirmation");
			if (confirmar) {
				Task<String> tarefaCargaPg = new Task<String>() {
					@Override
					protected String call() throws Exception {

						if (listSavePermissos != null) {
							for (int i = 0; i < listSavePermissos.size(); i++) {								
								try {
									NivelAcessoDAO.class.newInstance().alterarPermisos(usuarioRastreio, listSavePermissos.get(i).getId(), listSavePermissos.get(i).getVisible(),
											listSavePermissos.get(i).getInserir(), listSavePermissos.get(i).getUpdate(),
											listSavePermissos.get(i).getExcluir(), listSavePermissos.get(i).getReativar(),
											listSavePermissos.get(i).getPrint());
								} catch (NumberFormatException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InstantiationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}							
						} 
						return "-";
					}

					@Override
					protected void succeeded() {
						stg.close();
						listSavePermissos =null;
						Util.showNotification(DadosGlobais.resourceBundle.getString("NivelesAcessoController.NotificationSucesso"));
					}

					@Override
					protected void failed() {
						stg.close();					
						util.showAlert("Error de conexion","error");						
					}

					@Override
					protected void cancelled() {
						super.cancelled();
					}

				};
				Thread t = new Thread(tarefaCargaPg);
				t.setDaemon(true);
				t.start();
				stg = ProgressBarForm.showProgressBar(NivelesAcessoController.class, tarefaCargaPg,
						"Gravando Informação",false);

			}else{				
				cancelActionSave();
			}
		}
	}

	/**
	 * METODO CANCELAR OPERAÇÃO
	 */
	public void cancelActionSave(){
		//PERCORRER O TREETABLEVIEW E ATUALIZAR OS DADOS CON LISTPERMISOS					
		tabPaneSize = tabPane.getTabs().size();
		changeDataTreeView(0, itemsPaes, mnBarPrincipal.getMenus(), "CANCEL");
		countTabRemove =0;	
		listSavePermissos = null;
	}

	/**
	 * METODO QUE FAZ O SELECTED ALL OU UNSELECTED ALL DOS CHECKBOX, PERCORRE O TREETABLEVIEW E ALTERA O VALOR DO CAMPO DEPENDENDO DA COLUNA,
	 * CADA ALTERAÇÃO VAI SER ADICIONADA NA LISTA "LISTSAVEPERMISSOS".
	 * 
	 * @param itemRoot ROOT DO TREE
	 * @param action COLUNA NA QUAL VAI SER FEITO A SELECTED ALL OU UNSELECTED ALL
	 * @param newValue NOVO VALOR DO CHECKBOX
	 */
	public void recursiveCheckTreeSelectedAll(TreeItem<ListMenu> itemRoot, String action, boolean newValue){
		for(int i=0; i < itemRoot.getChildren().size(); i++){
			if(((TreeItem)itemRoot.getChildren().get(i)).getChildren().size() > 0 ){

				recursiveCheckTreeSelectedAll((TreeItem)itemRoot.getChildren().get(i), action, newValue);

				switch (action) {
				case "Visible":
					itemRoot.getChildren().get(i).getValue().setVisible(new SimpleBooleanProperty(newValue));
					break;
				default:
					break;
				}

				listSave(itemRoot.getChildren().get(i).getValue());
				if(!itemRoot.getChildren().get(i).isExpanded()){
					itemRoot.getChildren().get(i).setExpanded(true);
				}

			}else{
				switch (action) {
				case "Visible":
					itemRoot.getChildren().get(i).getValue().setVisible(new SimpleBooleanProperty(newValue));
					break;

				case "Insert":
					itemRoot.getChildren().get(i).getValue().setInserir(new SimpleBooleanProperty(newValue));
					break;

				case "Update":
					itemRoot.getChildren().get(i).getValue().setUpdate(new SimpleBooleanProperty(newValue));
					break;

				case "Delete":
					itemRoot.getChildren().get(i).getValue().setExcluir(new SimpleBooleanProperty(newValue));				
					break;	

				case "Reativar":
					itemRoot.getChildren().get(i).getValue().setReativar(new SimpleBooleanProperty(newValue));				
					break;

				case "Print":
					itemRoot.getChildren().get(i).getValue().setPrint(new SimpleBooleanProperty(newValue));				
					break;

				default:
					break;
				}
				listSave(itemRoot.getChildren().get(i).getValue());
			}		
		}	
	}


	/**
	 * METODO PARA CREAR O TREETABLEVIEW POR CADA MODULO, CADA MODULO GERA UN TAB E UN TREETABLE
	 * 
	 * @param nomeObjeto MENU PARA GERAR O TABLEVIEW NESSE MODULO
	 * @param itemRaiz NOVA INSTANCIA DO ITEMROOT
	 */
	public void createTreeView(Menu nomeObjeto, TreeItem itemRaiz) {
		tab = new Tab(nomeObjeto.getText());		
		tab.setClosable(false);	
		
		treeTableNivelAcesso = new TreeTableView<ListMenu>();		
		treeTableNivelAcesso.setEditable(true);
		treeColumnMenu = new TreeTableColumn<ListMenu, String>(DadosGlobais.resourceBundle.getString("NivelesAcessoController.treeColumnMenu"));
		treeColumnMenu.setPrefWidth(250);

		checkColumnVisible = new TreeTableColumn<ListMenu, Boolean>(DadosGlobais.resourceBundle.getString("NivelesAcessoController.checkColumnVisible")); 
		CheckBox ckSelectedAllVisible = new CheckBox();
		ckSelectedAllVisible.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

				recursiveCheckTreeSelectedAll(itemRaiz, "Visible", newValue);
				((TreeTableView)ckSelectedAllVisible.getParent().getParent().getParent().getParent().getParent()).refresh();
				ckActionSelectAll = true;

			}
		});
		checkColumnVisible.setGraphic(ckSelectedAllVisible);
		checkColumnVisible.setPrefWidth(100);

		checkColumnInsert = new TreeTableColumn<ListMenu, Boolean>(DadosGlobais.resourceBundle.getString("NivelesAcessoController.checkColumnInsert"));
		CheckBox ckSelectedAllInsert= new CheckBox();
		ckSelectedAllInsert.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				//				if(newValue){
				recursiveCheckTreeSelectedAll(itemRaiz, "Insert", newValue);
				((TreeTableView)ckSelectedAllVisible.getParent().getParent().getParent().getParent().getParent()).refresh();
				ckActionSelectAll = true;
				//				}
			}
		});
		checkColumnInsert.setGraphic(ckSelectedAllInsert);
		checkColumnInsert.setPrefWidth(100);

		checkColumnUpdate = new TreeTableColumn<ListMenu, Boolean>(DadosGlobais.resourceBundle.getString("NivelesAcessoController.checkColumnUpdate"));
		CheckBox ckSelectedAllUpdate = new CheckBox();
		ckSelectedAllUpdate.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				recursiveCheckTreeSelectedAll(itemRaiz, "Update", newValue);
				((TreeTableView)ckSelectedAllVisible.getParent().getParent().getParent().getParent().getParent()).refresh();
				ckActionSelectAll = true;

			}
		});
		checkColumnUpdate.setGraphic(ckSelectedAllUpdate);
		checkColumnUpdate.setPrefWidth(100);

		checkColumnDelete = new TreeTableColumn<ListMenu, Boolean>(DadosGlobais.resourceBundle.getString("NivelesAcessoController.checkColumnDelete"));
		CheckBox ckSelectedAllDelete = new CheckBox();
		ckSelectedAllDelete.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

				recursiveCheckTreeSelectedAll(itemRaiz, "Delete", newValue);
				((TreeTableView)ckSelectedAllVisible.getParent().getParent().getParent().getParent().getParent()).refresh();
				ckActionSelectAll = true;

			}
		});
		checkColumnDelete.setGraphic(ckSelectedAllDelete);
		checkColumnDelete.setPrefWidth(100);

		checkColumnReactivete = new TreeTableColumn<ListMenu, Boolean>(DadosGlobais.resourceBundle.getString("NivelesAcessoController.checkColumnReactivete"));
		CheckBox ckSelectedAllReativar = new CheckBox();
		ckSelectedAllReativar.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

				recursiveCheckTreeSelectedAll(itemRaiz, "Reativar", newValue);
				((TreeTableView)ckSelectedAllVisible.getParent().getParent().getParent().getParent().getParent()).refresh();

			}
		});
		checkColumnReactivete.setGraphic(ckSelectedAllReativar);
		checkColumnReactivete.setPrefWidth(100);

		checkColumnPrint = new TreeTableColumn<ListMenu, Boolean>(DadosGlobais.resourceBundle.getString("NivelesAcessoController.checkColumnPrint"));
		CheckBox ckSelectedAllPrint = new CheckBox();
		ckSelectedAllPrint.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub

				recursiveCheckTreeSelectedAll(itemRaiz, "Print", newValue);
				((TreeTableView)ckSelectedAllVisible.getParent().getParent().getParent().getParent().getParent()).refresh();
				ckActionSelectAll = true;

			}
		});
		checkColumnPrint.setGraphic(ckSelectedAllPrint);
		checkColumnPrint.setPrefWidth(100);

		treeColumnMenu.setCellValueFactory((CellDataFeatures<ListMenu, String> p) -> new ReadOnlyStringWrapper(p.getValue().getValue().getNome()));
		checkColumnVisible.setCellValueFactory((CellDataFeatures<ListMenu, Boolean> p) -> new ReadOnlyBooleanWrapper(p.getValue().getValue().getVisible()));
		checkColumnVisible.setCellFactory(
				new Callback<TreeTableColumn<ListMenu, Boolean>, TreeTableCell<ListMenu, Boolean>>() {
					@Override
					public TreeTableCell<ListMenu, Boolean> call(
							TreeTableColumn<ListMenu, Boolean> p) {
						CheckBoxTreeColumnVisible check = new CheckBoxTreeColumnVisible();
						check.setAlignment(Pos.CENTER);
						return check;
					}
				});

		checkColumnInsert.setCellValueFactory((CellDataFeatures<ListMenu, Boolean> p) -> new ReadOnlyBooleanWrapper(p.getValue().getValue().getInserir()));
		checkColumnInsert.setCellFactory(new Callback<TreeTableColumn<ListMenu, Boolean>, TreeTableCell<ListMenu, Boolean>>() {
			@Override
			public TreeTableCell<ListMenu, Boolean> call(
					TreeTableColumn<ListMenu, Boolean> p) {
				CheckBoxTreeColumnInsert check = new CheckBoxTreeColumnInsert();
				check.setAlignment(Pos.CENTER);
				return check;
			}
		});

		checkColumnUpdate.setCellValueFactory((CellDataFeatures<ListMenu, Boolean> p) -> new ReadOnlyBooleanWrapper(p.getValue().getValue().getUpdate()));
		checkColumnUpdate.setCellFactory(
				new Callback<TreeTableColumn<ListMenu, Boolean>, TreeTableCell<ListMenu, Boolean>>() {
					@Override
					public TreeTableCell<ListMenu, Boolean> call(
							TreeTableColumn<ListMenu, Boolean> p) {
						CheckBoxTreeColumnUpdate check = new CheckBoxTreeColumnUpdate();
						check.setAlignment(Pos.CENTER);
						return check;
					}
				});

		checkColumnDelete.setCellValueFactory((CellDataFeatures<ListMenu, Boolean> p) -> new ReadOnlyBooleanWrapper(p.getValue().getValue().getExcluir()));
		checkColumnDelete.setCellFactory(
				new Callback<TreeTableColumn<ListMenu, Boolean>, TreeTableCell<ListMenu, Boolean>>() {
					@Override
					public TreeTableCell<ListMenu, Boolean> call(
							TreeTableColumn<ListMenu, Boolean> p) {
						CheckBoxTreeColumnDelete check = new CheckBoxTreeColumnDelete();
						check.setAlignment(Pos.CENTER);
						return check;
					}
				});

		checkColumnReactivete.setCellValueFactory((CellDataFeatures<ListMenu, Boolean> p) -> new ReadOnlyBooleanWrapper(p.getValue().getValue().getReativar()));
		checkColumnReactivete.setCellFactory(
				new Callback<TreeTableColumn<ListMenu, Boolean>, TreeTableCell<ListMenu, Boolean>>() {
					@Override
					public TreeTableCell<ListMenu, Boolean> call(
							TreeTableColumn<ListMenu, Boolean> p) {
						CheckBoxTreeColumnReactivete check = new CheckBoxTreeColumnReactivete();
						check.setAlignment(Pos.CENTER);
						return check;
					}
				});

		checkColumnPrint.setCellValueFactory((CellDataFeatures<ListMenu, Boolean> p) -> new ReadOnlyBooleanWrapper(p.getValue().getValue().getPrint()));
		checkColumnPrint.setCellFactory(
				new Callback<TreeTableColumn<ListMenu, Boolean>, TreeTableCell<ListMenu, Boolean>>() {
					@Override
					public TreeTableCell<ListMenu, Boolean> call(
							TreeTableColumn<ListMenu, Boolean> p) {
						CheckBoxTreeColumnPrint check = new CheckBoxTreeColumnPrint();
						check.setAlignment(Pos.CENTER);
						return check;
					}
				});


		treeTableNivelAcesso.setRoot(itemRaiz);		
		treeTableNivelAcesso.getColumns().addAll(treeColumnMenu, checkColumnVisible, checkColumnInsert, checkColumnUpdate, checkColumnDelete, checkColumnReactivete, checkColumnPrint);
		treeTableNivelAcesso.setShowRoot(false);
		tab.setContent(treeTableNivelAcesso);
		tabPane.setPadding(new Insets(5));
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(0);
	}

	/**
	 * CADA ALTERAÇÃO DAS PERMISSÕES VAI SER SALVADAS NESTA LISTA, ATÉ O MOMENTO EM QUE FORA GRAVADA ALTERAÇÃO NO BANCO DE DADOS
	 * 
	 * @param listSaveMenu PARAMETROS QUE VAN SER SALVADOS NA LISTA
	 * @return
	 */
	public List<ListMenu> listSave(ListMenu listSaveMenu) {		
		if (listSavePermissos == null) {
			listSavePermissos = new ArrayList<ListMenu>();						
			listSavePermissos.add(listSaveMenu);
		} else {			
			boolean flag = false;			
			for (int i = 0; i < listSavePermissos.size(); i++) {
				if (listSavePermissos.get(i).getId().equals(listSaveMenu.getId())) {
					listSavePermissos.get(i).setVisible(new SimpleBooleanProperty(listSaveMenu.getVisible()));
					listSavePermissos.get(i).setInserir(new SimpleBooleanProperty(listSaveMenu.getInserir()));
					listSavePermissos.get(i).setUpdate(new SimpleBooleanProperty(listSaveMenu.getUpdate()));
					listSavePermissos.get(i).setExcluir(new SimpleBooleanProperty(listSaveMenu.getExcluir()));
					listSavePermissos.get(i).setReativar(new SimpleBooleanProperty(listSaveMenu.getReativar()));
					listSavePermissos.get(i).setPrint(new SimpleBooleanProperty(listSaveMenu.getPrint()));
					flag = true;
					break;
				}
			}

			if (!flag) {				
				listSavePermissos.add(listSaveMenu);
			}
		}

		return listSavePermissos;
	}


	/**
	 *	METODO RECURSIVO PARA GERAR a INFORMAÇÃO DO TREEITEM COM AS PERMISSÕES DE NIVEIS DO ACESSOS
	 *
	 * @param idPae  PROFUNDIDADE DO MENU, SE É ZERO(0) É O MENU PÃE
	 * @param padre	 TREEITEM PAE 	
	 * @param observableListMenu LISTA DOS MENUS
	 * @param action SE ACTION IGUAL "CANCEL", VAI SER EXCLUIDA AS ABAS ABERTAS E CREADAS AS NOVAS ABAS; SE FORA CREATE, SÓ VAI CREAR AS ABAS
	 */
	public void changeDataTreeView(int idPae, TreeItem<ListMenu> padre, ObservableList<?> observableListMenu, String action) {
		for (int i = 0; i < observableListMenu.size(); i++) {
			if (observableListMenu.get(i) instanceof Menu) {
				if (idPae == 0) {
					itemsRoot = new TreeItem<>();
					moduloPae = 1;

					for (NivelAcesso nvAcess : listPermisoUsuario) {
						String menu = ((Menu) observableListMenu.get(i)).getId().subSequence(0, 5).toString() + ((Menu) observableListMenu.get(i)).getId().subSequence(9, ((Menu) observableListMenu.get(i)).getId().toCharArray().length);
						if (nvAcess.getMenu().equals(EncryptMD5.getMD5(menu))) {							
							menuPermissao = new ListMenu(nvAcess.getMenu(), ((Menu)observableListMenu.get(i)).getText(),  ((nvAcess.getFlagView()==1) ? true : false), (nvAcess.getFlagInsert()==1) ? true : false, (nvAcess.getFlagUpdate()==1) ? true : false, (nvAcess.getFlagDisable()==1) ? true : false, (nvAcess.getFlagEnable()==1) ? true : false, (nvAcess.getFlagPrint()==1) ? true : false);
							break;
						}
					}
					itemsPaes = new TreeItem<>(menuPermissao);

					itemsRoot.getChildren().add(itemsPaes);

					changeDataTreeView(moduloPae, itemsPaes, ((Menu) observableListMenu.get(i)).getItems(), action);
					if(action.equals("CREATE")){
						createTreeView(((Menu)observableListMenu.get(i)), itemsRoot);
					}else if(action.equals("CANCEL")){
						if(tabPaneSize > countTabRemove){
							tabPane.getTabs().remove(0);
							countTabRemove++;
						}
						createTreeView(((Menu)observableListMenu.get(i)), itemsRoot);
					}
				}
				else{
					moduloPae++;	
					for (NivelAcesso nvAcess : listPermisoUsuario) {
						String menu = ((Menu) observableListMenu.get(i)).getId().subSequence(0, 5).toString() + ((Menu) observableListMenu.get(i)).getId().subSequence(9, ((Menu) observableListMenu.get(i)).getId().toCharArray().length);
						if (nvAcess.getMenu().equals(EncryptMD5.getMD5(menu))) {
							menuPermissao = new ListMenu(nvAcess.getMenu(), ((Menu)observableListMenu.get(i)).getText(), (nvAcess.getFlagView()==1) ? true : false, (nvAcess.getFlagInsert()==1) ? true : false, (nvAcess.getFlagUpdate()==1) ? true : false, (nvAcess.getFlagDisable()==1) ? true : false, (nvAcess.getFlagEnable()==1) ? true : false, (nvAcess.getFlagPrint()==1) ? true : false);

							break;
						}
					}
					itemsFilhos = new TreeItem<>(menuPermissao);
					padre.getChildren().add(itemsFilhos);
					changeDataTreeView(moduloPae, itemsFilhos, ((Menu) observableListMenu.get(i)).getItems(), action);
				}

			} else if (observableListMenu.get(i) instanceof MenuItem) {
				for (NivelAcesso nvAcess : listPermisoUsuario) {
					String menuItem = ((MenuItem) observableListMenu.get(i)).getId().subSequence(0, 5).toString() + ((MenuItem) observableListMenu.get(i)).getId().subSequence(9, ((MenuItem) observableListMenu.get(i)).getId().toCharArray().length);
					if (nvAcess.getMenu().equals(EncryptMD5.getMD5(menuItem))) {
						menuPermissao = new ListMenu(nvAcess.getMenu(), ((MenuItem)observableListMenu.get(i)).getText(), (nvAcess.getFlagView()==1) ? true : false, (nvAcess.getFlagInsert()==1) ? true : false, (nvAcess.getFlagUpdate()==1) ? true : false, (nvAcess.getFlagDisable()==1) ? true : false, (nvAcess.getFlagEnable()==1) ? true : false, (nvAcess.getFlagPrint()==1) ? true : false);
						break;
					}
				}

				itemsFilhos = new TreeItem<>(menuPermissao);
				padre.getChildren().add(itemsFilhos);
			}
		}
	}
	
	
	/**
	 *  METODO PARA LIMPAR OU ADICIONAR DADOS NO BANCO DE DADOS, NO CASO EXISTAN DADOS LIXOS NO BANCO DE DADOS VAI SER EXCLUIDOS,
	 *  SE NÃO EXISTIR O MENU BUSCADO, VAI SER GRAVADO NO BANCO DE DADOS
	 * 
	 * @param usuario USUARIO A PROCURAR
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public boolean validarInformacion(Usuario usuario) throws InstantiationException, IllegalAccessException, Exception{

		boolean mnExistBD = false;
		boolean alterarBD = false;
		List<String> listMenuInserir = null;
		List<NivelAcesso> listMenuExcluir = null;

		List<String> listIdMenus = Util.getIdMenu(mnBarPrincipal.getMenus());
		for(int i=0; i < listIdMenus.size(); i++){
			for(NivelAcesso permissoes : listPermisoUsuario){
				String cleanIdMenu = String.valueOf(listIdMenus.get(i).subSequence(0, 5).toString() + listIdMenus.get(i).subSequence(9, listIdMenus.get(i).toCharArray().length));
				if(EncryptMD5.getMD5(cleanIdMenu).equals(permissoes.getMenu())){
					mnExistBD = true;
					break;
				}				
			}if (!mnExistBD) {
				if (listMenuInserir == null) {
					listMenuInserir = new ArrayList<String>();
				}
				listMenuInserir.add(EncryptMD5.getMD5(listIdMenus.get(i).subSequence(0, 5).toString() + listIdMenus.get(i).subSequence(9, listIdMenus.get(i).toCharArray().length)));
			}
			mnExistBD = false;
		}

		for(int y =0; y < listPermisoUsuario.size(); y++){
			for(int i =0; i < listIdMenus.size(); i++){
				String cleanIdMenu = String.valueOf(listIdMenus.get(i).subSequence(0, 5).toString() + listIdMenus.get(i).subSequence(9, listIdMenus.get(i).toCharArray().length));
				if(EncryptMD5.getMD5(cleanIdMenu).equals(listPermisoUsuario.get(y).getMenu())){
					mnExistBD = true;
					break;
				}
			}

			if(!mnExistBD){
				if (listMenuExcluir == null) {
					listMenuExcluir = new ArrayList<NivelAcesso>();
				}
				listMenuExcluir.add(listPermisoUsuario.get(y));
			}
			mnExistBD = false;
		}

		if(listMenuInserir !=null){
			alterarBD = true;			
			NivelAcessoDAO.class.newInstance().insertNewMenu(usuario, listMenuInserir);
			listMenuInserir = null;
		}

		if(listMenuExcluir !=null){
			alterarBD =true;
			NivelAcessoDAO.class.newInstance().removeMenu(usuarioRastreio, listMenuExcluir);
			listMenuExcluir = null;
		}

		return alterarBD;
	}
	
	/**
	 *  METODO PARA PESQUISAR AS PERMISSÕES DO USUARIO
	 */
	public void searchUserData() {	
		try {
			Task<String> tarefaCargaPg = new Task<String>() {
				@Override
				protected String call() throws Exception {					
					result = UsuarioDAO.class.newInstance().getUserAccessById(usuarioRastreio);
					return "-";
				}

				@Override
				protected void succeeded() {
					stg.close();					
					txtNome.clear();
					if (result != null) {						
						listPermisoUsuario = result.getNiveisAcesso();						
						txtNome.setText(result.getNome());
						try {
							try {
								if(validarInformacion(result)){									
									result = UsuarioDAO.class.newInstance().getUserAccessById(usuarioRastreio);
									listPermisoUsuario = result.getNiveisAcesso();
								}
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}							

							changeDataTreeView(0, itemsPaes, mnBarPrincipal.getMenus(), "CREATE");

						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

						}
					} else {
						util.showAlert("Usuario não encontrado","information");
						txtCodigo.requestFocus();
					}

				}

				@Override
				protected void failed() {
					stg.close();
					util.showAlert("Error de conexion","error");
				}

				@Override
				protected void cancelled() {
					super.cancelled();
				}

			};
			Thread t = new Thread(tarefaCargaPg);
			t.setDaemon(true);
			t.start();
			stg = ProgressBarForm.showProgressBar(NivelesAcessoController.class, tarefaCargaPg,
					"Gerando informação do Usuario",false);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		

		lblCodigo.setText(DadosGlobais.resourceBundle.getString("NivelesAcessoController.lblCodigo"));
		lblNome.setText(DadosGlobais.resourceBundle.getString("NivelesAcessoController.lblNome"));
		
		txtCodigo.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue && txtNome.isFocused()) {
					if (!txtCodigo.getText().isEmpty()) {
						searchUserData();
					} else {
						util.showAlert("Insira o codigo do usuario.","error");
						tabPane.getTabs().remove(0, tabPane.getTabs().size());
						txtNome.clear();
						txtCodigo.requestFocus();
					}
				}
			}
		});

		tabPane.requestFocus();

		if (usuarioRastreio!=null) {
			txtCodigo.setText(usuarioRastreio.getCodigo().toString());
			searchUserData();
		}

	}

	

	// ------------------------------------------------------------------------------
	// DESTA FORMA O SCROLL NAO ALTERA A INFORMAÇÃO QUE TINHA O CHECKBOXTREE
	// ------------------------------------------------------------------------------
	
	//CHECKBOX DA COLUNA VISIBLE
	class CheckBoxTreeColumnVisible extends TreeTableCell<ListMenu, Boolean> {

		private final CheckBox check = new CheckBox();

		public CheckBoxTreeColumnVisible() {
			check.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					getTreeTableView().getSelectionModel().select(getIndex());						
					getTreeTableView().getTreeItem(getIndex()).getValue().setVisible(new SimpleBooleanProperty(check.isSelected()));
					listSave(getTreeTableView().getTreeItem(getIndex()).getValue());
				}
			});
		}

		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);				
			if (!empty) {						
				check.setSelected(item);
				setGraphic(check);
			} else {
				setText(null);
				setGraphic(null);
			}
		}

	}
	// -----------------------------------------------	
	//CHECKBOX DA COLUNA INSERIR
	class CheckBoxTreeColumnInsert extends TreeTableCell<ListMenu, Boolean> {

		private final CheckBox check = new CheckBox();

		public CheckBoxTreeColumnInsert() {
			check.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub						

					getTreeTableView().getSelectionModel().select(getIndex());
					getTreeTableView().getTreeItem(getIndex()).getValue().setInserir(new SimpleBooleanProperty(check.isSelected()));
					listSave(getTreeTableView().getTreeItem(getIndex()).getValue());
				}
			});
		}
		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && getTreeTableView().getTreeItem(getIndex()).getChildren().size()==0) {							
				check.setSelected(item);
				setGraphic(check);
			} else {				
				setText(null);
				setGraphic(null);
			}
		}

	}
	// -----------------------------------------------	
	//CHECKBOX DA COLUNA UPDATE
	class CheckBoxTreeColumnUpdate extends TreeTableCell<ListMenu, Boolean> {

		private final CheckBox check = new CheckBox();

		public CheckBoxTreeColumnUpdate() {
			check.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub

					getTreeTableView().getSelectionModel().select(getIndex());
					getTreeTableView().getTreeItem(getIndex()).getValue().setUpdate(new SimpleBooleanProperty(check.isSelected()));	
					listSave(getTreeTableView().getTreeItem(getIndex()).getValue());}

			});
		}
		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && getTreeTableView().getTreeItem(getIndex()).getChildren().size()==0) {							
				check.setSelected(item);
				setGraphic(check);
			} else {				
				setText(null);
				setGraphic(null);
			}
		}

	}
	// -----------------------------------------------
	//CHECKBOX DA COLUNA EXCLUIR
	class CheckBoxTreeColumnDelete extends TreeTableCell<ListMenu, Boolean> {

		private final CheckBox check = new CheckBox();

		public CheckBoxTreeColumnDelete() {
			check.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub

					getTreeTableView().getSelectionModel().select(getIndex());							
					getTreeTableView().getTreeItem(getIndex()).getValue().setExcluir(new SimpleBooleanProperty(check.isSelected()));
					listSave(getTreeTableView().getTreeItem(getIndex()).getValue());

				}
			});
		}
		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && getTreeTableView().getTreeItem(getIndex()).getChildren().size()==0) {							
				check.setSelected(item);
				setGraphic(check);
			} else {				
				setText(null);
				setGraphic(null);
			}
		}

	}
	// -----------------------------------------------	
	//CHECKBOX DA COLUNA REATIVAR
	class CheckBoxTreeColumnReactivete extends TreeTableCell<ListMenu, Boolean> {

		private final CheckBox check = new CheckBox();

		public CheckBoxTreeColumnReactivete() {
			check.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub

					getTreeTableView().getSelectionModel().select(getIndex());
					getTreeTableView().getTreeItem(getIndex()).getValue().setReativar(new SimpleBooleanProperty(check.isSelected()));
					listSave(getTreeTableView().getTreeItem(getIndex()).getValue());

				}
			});
		}
		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && getTreeTableView().getTreeItem(getIndex()).getChildren().size()==0) {							
				check.setSelected(item);
				setGraphic(check);
			} else {				
				setText(null);
				setGraphic(null);
			}
		}

	}
	// -----------------------------------------------
	//CHECKBOX DA COLUNA IMPRIMIR
	class CheckBoxTreeColumnPrint extends TreeTableCell<ListMenu, Boolean> {

		private final CheckBox check = new CheckBox();

		public CheckBoxTreeColumnPrint() {
			check.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					getTreeTableView().getSelectionModel().select(getIndex());
					getTreeTableView().getTreeItem(getIndex()).getValue().setPrint(new SimpleBooleanProperty(check.isSelected()));
					listSave(getTreeTableView().getTreeItem(getIndex()).getValue());
				}
			});
		}
		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty && getTreeTableView().getTreeItem(getIndex()).getChildren().size()==0) {							
				check.setSelected(item);
				setGraphic(check);
			} else {				
				setText(null);
				setGraphic(null);
			}
		}

	}
}
