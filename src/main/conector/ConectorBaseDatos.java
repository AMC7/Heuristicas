package conector;
import static util.Print.*;
import static util.ManejadorDeArchivos.*;
import static util.MyString.*;
import util.Dupla;
import util.Punto;
import java.util.LinkedList;
import java.lang.Math;
import java.util.Hashtable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class ConectorBaseDatos{

	private String nombreBase;
	private Connection coneccion;

	public void close(){
		try{
			coneccion.close();
		}catch(SQLException e){
			e.printStackTrace();
		}	
	}
	
	public ConectorBaseDatos(String nombreBase) throws IOException{
		this.nombreBase=nombreBase;
		File file = new File(nombreBase);
		if(!file.exists()){
			throw new IOException("No existe esa base de datos");		
		}
	}

	public ResultSet creaStatement(String query){
		try{		
			Statement m_Statement = coneccion.createStatement();
			return  m_Statement.executeQuery(query);
		}catch(SQLException e){
			e.printStackTrace();		
		}
		return null;
	}

	public Double[][] getPesos(){
		ResultSet resultado = creaStatement("SELECT * FROM connections;");
		Double [][] pesos = new Double[1093][1093];		
		for(int i=0;i<pesos.length;i++)
			for(int j=0;j<pesos.length;j++)
				if(i!=j)
					pesos[i][j]=Double.POSITIVE_INFINITY;
				else
					pesos[i][j]=0.;
		try{	
			while (resultado.next()) {
				int idCity1=resultado.getInt(1); 			
				int idCity2=resultado.getInt(2); 			
				Double distance=resultado.getDouble(3);	 
				pesos[idCity1][idCity2]=distance;
				pesos[idCity2][idCity1]=distance;
			}
		}catch(SQLException e){
			e.printStackTrace();		
		}
		close();
		return pesos;	
	}

	public boolean conecta(){
		try{
			coneccion = DriverManager.getConnection("jdbc:sqlite:"+nombreBase);		
			return true;		
		}catch(SQLException e){
			e.printStackTrace();
			return false;			
		}
	}

}
