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
	
	double [][] grafica;	
	
	public Grafica(double [][] a){			
		grafica=a;		
	}	

	public double[][] getArreglo(){
		return grafica;	
	}

	public void setArreglo(double [][] a){
		grafica=a;		
	}
	
}
