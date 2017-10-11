package tools.enums;

import java.util.ArrayList;

@SuppressWarnings("unused")
public enum EnumDefaultFields 
{
	serialVersionUID(0, "serialVersionUID", 0),
	LastMovto(1, "LastMovto", 1),
	FlagAtivo(2, "FlagAtivo", 2),
	LastCoduser(3, "LastCoduser", 3),
	Utctag(4, "Utctag", 4),
	CheckDelete(5, "CheckDelete", 5),
	RecordNo(6, "RecordNo", 6),
	Codemp(7, "Codemp", 7);

	public Integer id;
	public String value;
	public Integer index;
	
	public static boolean Contains(String value) 
	{
		   boolean result = false;
			
		   EnumDefaultFields [] enm = EnumDefaultFields.values();
			
		   for ( int i = 0; i < enm.length; i++ ) 
		   {
				  if( enm[i].value.equalsIgnoreCase(value) )
				  {	  
					  result = true;
					  break;
				  }	  
				  else{}
		   }
			
		   return result;
	}	

	EnumDefaultFields(Integer id, String value, Integer index) 
	{
		this.id = id;
		this.value = value;
		this.index = index;
	}
}
