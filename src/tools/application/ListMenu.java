package tools.application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ListMenu{
	
	private String id;
	private String nome;
//	private int recordNo;
	private BooleanProperty visible;
	private BooleanProperty inserir;
	private BooleanProperty update;
	private BooleanProperty excluir;
	private BooleanProperty reativar;	
	private BooleanProperty print;
	
	
	public ListMenu(){	
		
	}
	
	public ListMenu(String id, String nome, boolean visible, boolean inserir, boolean update,
			boolean excluir, boolean reativar, boolean print) {
//		super();
		this.id = id;
		this.nome = nome;
//		this.recordNo = recordNo;
		this.visible = new SimpleBooleanProperty(visible);
		this.inserir = new SimpleBooleanProperty(inserir);
		this.update = new SimpleBooleanProperty(update);
		this.excluir = new SimpleBooleanProperty(excluir);
		this.reativar = new SimpleBooleanProperty(reativar);
		this.print = new SimpleBooleanProperty(print);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the visible
	 */
	public BooleanProperty visibleProperty() {
		return visible;
	}
	
	public boolean getVisible(){
		return visible.get();
	}
	/**
	 * @param visible the visible to set
	 */
	public void setVisible(BooleanProperty visible) {
		this.visible = visible;
	}
	/**
	 * @return the inserir
	 */
	public BooleanProperty inserirProperty() {
		return inserir;
	}
	
	public boolean getInserir(){
		return inserir.get();
	}
	/**
	 * @param inserir the inserir to set
	 */
	public void setInserir(BooleanProperty inserir) {
		this.inserir = inserir;
	}
	/**
	 * @return the update
	 */
	public BooleanProperty updateProperty() {
		return update;
	}

	public boolean getUpdate(){
		return update.get();
	}
	/**
	 * @param update the update to set
	 */
	public void setUpdate(BooleanProperty update) {
		this.update = update;
	}
	/**
	 * @return the excluir
	 */
	public BooleanProperty excluirProperty() {
		return excluir;
	}

	public boolean getExcluir(){
		return excluir.get();
	}
	/**
	 * @param excluir the excluir to set
	 */
	public void setExcluir(BooleanProperty excluir) {
		this.excluir = excluir;
	}

	/**
	 * @return the reativar
	 */
	public BooleanProperty reativarProperty() {
		return reativar;
	}

	public boolean getReativar(){
		return reativar.get();
	}
	/**
	 * @param reativar the reativar to set
	 */
	public void setReativar(BooleanProperty reativar) {
		this.reativar = reativar;
	}

//	public int getRecordNo() {
//		return recordNo;
//	}
//
//	public void setRecordNo(int recordNo) {
//		this.recordNo = recordNo;
//	}	
	
	/**
	 * @return the print
	 */
	public boolean getPrint(){
		return print.get();
	}
	public BooleanProperty printProperty() {
		return print;
	}

	/**
	 * @param print the print to set
	 */
	public void setPrint(BooleanProperty print) {
		this.print = print;
	}

	@Override
	public String toString() {
		return "ListMenu [id=" + id + ", nome=" + nome + ", visible=" + visible + ", inserir=" + inserir + ", update="
				+ update + ", excluir=" + excluir + ", reativar=" + reativar + ", print=" + print + "]";
	}

		
	
//	public ListMenu(String id, String nome, boolean permiso) {
////		super();
//		this.id = id;
//		this.nome = nome;
//		this.visible = new SimpleBooleanProperty(permiso);
//	}
//	
//	
//	public BooleanProperty permisoProperty(){
//		return visible;
//	}
//	/**
//	 * @return the permiso
//	 */
//	public boolean getPermiso() {
//		return visible.get();
//	}
//	/**
//	 * @param permiso the permiso to set
//	 */
//	public void setPermiso(BooleanProperty permiso) {
//		this.visible = permiso;
//	}
//	/**
//	 * @return the id
//	 */
//	public String getId() {
//		return id;
//	}
//	/**
//	 * @param id the id to set
//	 */
//	public void setId(String id) {
//		this.id = id;
//	}
//	/**
//	 * @return the nome
//	 */
//	public String getNome() {
//		return nome;
//	}
//	/**
//	 * @param nome the nome to set
//	 */
//	public void setNome(String nome) {
//		this.nome = nome;
//	}

	
}