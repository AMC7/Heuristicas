package principal;
import static util.Print.*;
import static util.ManejadorDeArchivos.*;
import static util.MyString.*;
import util.Dupla;
import util.Punto;
import java.util.LinkedList;
import java.lang.Math;
import java.util.Hashtable;
import util.GeneradorDeClases;
import conector.ConectorBaseDatos;
import grafica.Grafica;
import recocidoSimulado.*;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Principal{
	
	
	
	public static void main(String [] args){
		ConectorBaseDatos conector = new ConectorBaseDatos("tsp");
		conector.conecta();
		double[][] hola = conector.getPesos(); 
		//double [][] pesos,int tamanoLote,int maximoBusquedasVecinas,int factorEnfriamiento,double ceroTemperatura	
		//RecocidoSimulado recocido= new RecocidoSimulado(conector.getPesos(),100,100,0.6,0.1);
		//Solucion solucion = new Solucion();		
		//recocido.calculaLotes(2.22,solucion);
		conector.close();
	}
}
