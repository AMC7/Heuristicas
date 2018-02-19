package grafica;
import static util.Print.*;
import static util.ManejadorDeArchivos.*;
import static util.MyString.*;
import util.Dupla;
import util.Punto;
import java.util.LinkedList;
import java.lang.Math;
import java.util.Hashtable;
import java.util.HashSet;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Grafica{
	
	Double [][] grafica;	
	
	public Grafica(Double [][] a){			
		grafica=a;		
	}	

	public Double[][] getArreglo(){
		return grafica;	
	}

	public void setArreglo(Double [][] a){
		grafica=a;		
	}

	public Double getPeso(int i,int j){
		return grafica[i][j];
	}

	
}
