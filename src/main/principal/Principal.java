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
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.util.Random;
import static recocidoSimulado.Constantes.*;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Principal{

		public static String imprimeTiempo(long inicio,long termino){
			Double diferencia = new Double(termino-inicio);
			diferencia/=1000000000.;
			return diferencia.intValue() +" segundos";
		}

		public static Double imprimePorcentaje(int iteracion, int total){
			return new Double(iteracion)/new Double(total)*100;
		}

		public static void lanzaSemillas(){
			Constantes.setConstantes(entrada,conector,f);
			Solucion solucion = new Solucion(entrada);
			Solucion mejor = new Solucion(solucion);
			String length = String.valueOf(solucion.getArreglo().length);			
			RecocidoSimulado recocido= new RecocidoSimulado();
			for(int j=0;j<numeroSemillas;j++){
				long inicio = System.nanoTime();
				solucion.shuffle();
				Solucion semilla = new Solucion(solucion);	
				p("num"+semilla.hashCode());
				Constantes.random = new Random(semilla.hashCode());				
				p("Semilla:"+semilla);
				p(imprimePorcentaje(j,numeroSemillas)+"%");			
				Solucion actual = recocido.lanzaSemilla(semilla);
				if(actual.compareTo(mejor)==-1){
					mejor = actual;
					escribe(salida,Arrays.toString(mejor.getArreglo()).replace("[","").replace("]",""));
					escribe("entrada/semilla"+length+".tsp",Arrays.toString(semilla.getArreglo()).replace("[","").replace("]",""));		
					recocido.guardaGrafica();
				}
				long termino = System.nanoTime();
				p("Resultado:"+actual);
				p(imprimeTiempo(inicio,termino)+"\n\n");
			}
		}

		public static void evaluaSoluciones(){
			File file= new File("salidas");
			File[] archivos = file.listFiles();
			if(archivos!=null){
				for(File archivo:archivos){
					if(!archivo.isDirectory()&&archivo.getAbsolutePath().endsWith("tsp")){
						Constantes.setConstantes(archivo.getAbsolutePath(),conector,f);									
						Solucion solucion = new Solucion(archivo.getAbsolutePath());
						p(archivo.getAbsolutePath());
						p(solucion);
						p("\n\n");
					}
				}
			}

		}

		public static void evaluaSemilla(String valor){
			if(!valor.equals("40")&&!valor.equals("150")){
				p("El valor:"+valor+" no es valido");				
				return;
			}	
			leeArchivoConfiguracion("config/configuraciones"+valor+".cnf");			
			Constantes.setConstantes(entrada,conector,f);
			RecocidoSimulado recocido= new RecocidoSimulado();			
			Solucion solucion = new Solucion(entrada);
			Solucion semilla = new Solucion(solucion);		
			String length = String.valueOf(solucion.getArreglo().length);								
			p("Semilla:"+semilla);
			p("num"+semilla.hashCode());
			Constantes.random = new Random(semilla.hashCode());	
			Solucion actual = recocido.lanzaSemilla(semilla);
			p("Actual:"+actual);
			recocido.guardaGrafica();
			escribe("entrada/semilla"+length+".tsp",Arrays.toString(semilla.getArreglo()).replace("[","").replace("]",""));		
			escribe(salida,Arrays.toString(actual.getArreglo()).replace("[","").replace("]",""));
		}

		public static void main(String [] args) throws IOException{
			Scanner sc = new Scanner(System.in);
			p("Selecciona:\n0: lanzaSemillas\n1: evaluaSoluciones\n2: pruebaSemilla");
			int i = sc.nextInt();
			switch(i){
				case 0:
					leeArchivoConfiguracion("config/configuraciones.cnf");
					lanzaSemillas();
				break;
				case 1:		
					evaluaSoluciones();
				break;
				case 2:
					p("Quieres evaluar la semilla de 40 o la de 150");
					String valor = sc.next();
					evaluaSemilla(valor);
				break;		
			}			
			conector.close();
		}
}
