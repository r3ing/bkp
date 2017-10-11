package tools.application;

import org.flywaydb.core.Flyway;

public class Migration {
	Flyway flyway = new Flyway();

	
	public void flywayCriaDataSource(String ipServer, String baseDados, String porta,String usuarioBD,String pwdBD){
	//flyway.setDataSource("jdbc:mysql://"+ipServer+":"+porta, usuarioBD, pwdBD);
	flyway.setDataSource("jdbc:mysql://"+ipServer+":"+porta+"/"+baseDados, usuarioBD, pwdBD);
	flyway.setLocations("/scripts/");
	
}

	public void flywayMigrate() {		
		flyway.getDataSource();
		flyway.migrate();
		
	}

	public void flywayInfo() {
		flyway.getDataSource();	
		flyway.info();
		
	}
	
	public void flywayBaseline(){
	flyway.getDataSource();
	flyway.baseline();
}
	

	public void flywayValide(){
	flyway.getDataSource();
	flyway.validate();
}


	public void flywayRepara(){
	flyway.getDataSource();
	flyway.repair();
}


public void flywayLimpa(){
	flyway.getDataSource();
	flyway.clean();
}
}
