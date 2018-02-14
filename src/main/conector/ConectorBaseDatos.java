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
	
	/**Unico constructor recibe un nombre, un usuario y una contrasena */
	public ConectorBaseDatos(String nombreBase){
		this.nombreBase=nombreBase;
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

	public double[][] getPesos(){
		ResultSet resultado = creaStatement("SELECT * FROM connections;");
		double [][] pesos = new double[1093][1093];		
		for(int i=0;i<pesos.length;i++)
			for(int j=0;j<pesos.length;j++)
				pesos[i][j]=Double.POSITIVE_INFINITY;
		
		try{	
			while (resultado.next()) {
				int idCity1=resultado.getInt(1); 			
				int idCity2=resultado.getInt(2); 			
				double distance=resultado.getDouble(3);	 
				pesos[idCity1][idCity2]=distance;
				pesos[idCity2][idCity1]=distance;		 
			}
		for(double[] a:pesos){
			p(Arrays.toString(a));		
		}
		}catch(SQLException e){
			e.printStackTrace();		
		}
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