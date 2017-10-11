package models.configuracoes;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the nivel_acesso database table.
 * 
 */
@Embeddable
public class NivelAcessoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	private Usuario usuario;
		
	private String menu;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NivelAcessoPK other = (NivelAcessoPK) obj;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	
	
}