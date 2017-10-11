package tools.enums;

import java.util.ArrayList;

public enum EnumBancos {

	
	BANCO_DO_BASIL(1, "BANCO DO BRASIL", "CARTEIRA 16", "16", 0),
	BANCO_DO_BASIL1(1, "BANCO DO BRASIL", "CARTEIRA 17", "17", 1),
	BANCO_DO_BASIL2(1, "BANCO DO BRASIL", "CARTEIRA 18", "18", 2),
	
	BRADESCO(2, "BRADESCO", "CARTEIRA 9", "9", 0),
	
	CAIXA(3, "CAIXA ECONÓMICA FEDERAL", "CARTEIRA PADRÃO", "0", 0),
	
	HSBC(4, "HSBC", "CARTEIRA PADRÃO", "0", 0),
	
	ITAU(5, "ITAÚ", "CARTEIRA PADRÃO", "0", 0),
	
	SAFRA(6, "SAFRA", "CARTEIRA PADRÃO", "0", 0),
	
	SANTANDER(7, "SANTANDER", "CARTEIRA PADRÃO", "0", 0),
	
	SICOOB(8, "SICOOB", "CARTEIRA 1", "1", 0),	
	SICOOB1(8, "SICOOB", "CARTEIRA 3", "3", 1);
	




	public Integer id;
	public String banco;
	public String carteira;
	public String idcarteira;
	public Integer index;

	EnumBancos(Integer id, String banco, String carteira, String idcarteira, Integer index) {
		this.id = id;
		this.banco = banco;
		this.carteira = carteira;
		this.idcarteira = idcarteira;
		this.index = index;
	}
}
