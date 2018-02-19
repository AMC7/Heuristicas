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
import java.lang.Thread;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Principal{
	
	static volatile Solucion solucion = null;
	
	static void Valua(){
		ConectorBaseDatos conector = new ConectorBaseDatos(base);
		conector.conecta();
		Double [][] arreglo = conector.getPesos();
		Solucion hola = new Solucion()
	}	
	public static void main(String [] args) throws IOException{	
		String base = "/sqlite/tsp";		
		String entrada = "entrada/entrada.txt";
		double porc = 0.55;			
		int tamanoLote=2500;
		double factorFrio=0.95;
		double e=0.001;
		double et=0.001;
		double ep =0.001;
		double f  = 3.14159265;
		int n = 9128; 
		double temp = 8.;
		
		ConectorBaseDatos conector = new ConectorBaseDatos(base);
		conector.conecta();
		Double [][] arreglo = conector.getPesos();
		RecocidoSimulado recocido = new RecocidoSimulado(porc,entrada,arreglo,tamanoLote,factorFrio,e,et,ep,n,f,temp);
		recocido.setCiudades();		
		recocido.setCastigo();	
		recocido.setPesoPromedio();
		recocido.setCiudades();
		solucion = recocido.getMejorSolucion();

		double total =100;		
		for(int j=0;j<20;j++){
			long inicio = System.nanoTime();			
			Solucion actual = recocido.lanzaSemilla();
			p(((double)j/total*100)+"%\nf:"+solucion.getF()+"\n"+solucion+"\nesFactible:"+solucion.esFactible(recocido.getGrafica()));
						if(actual.compareTo(solucion)==-1&&actual.esFactible(recocido.getGrafica())){
							solucion = actual;						
						}		
			p((int)((double)(System.nanoTime()-inicio)/1000000000.)+" segundos");
		}	
		p(solucion+"\nesFactible:"+solucion.esFactible(recocido.getGrafica()));	
		escribe("salida.txt",solucion.toString());	
	}
}
