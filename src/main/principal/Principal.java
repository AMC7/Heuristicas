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
import java.io.IOException;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Principal{
	
	public static void main(String [] args) throws IOException{	
		String base = "/sqlite/tsp";		
		String entrada = "entrada/entrada.txt";
		double porc = 0.9; //porc de vecinas aceptadas				
		int tamanoLote=50;
		double factorFrio=0.8;
		double e=0.12;
		double et=0.12;
		double ep =0.12;
		int n = 56; 
		
		ConectorBaseDatos conector = new ConectorBaseDatos(base);
		conector.conecta();
		double [][] arreglo = conector.getPesos();
		RecocidoSimulado recocido = new RecocidoSimulado(porc,entrada,arreglo,tamanoLote,factorFrio,e,et,ep,n);
	}
}
