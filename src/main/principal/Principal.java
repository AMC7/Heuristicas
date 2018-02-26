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
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Principal{

		public static String base;
		public static String entrada;
		public static double porc;
		public static int tamanoLote;
		public static double factorFrio;
		public static double e;
		public static double et;
		public static double ep;
		public static double f;
		public static int n;
		public static double temp;
		public static String salida;
		public static int numeroSemillas;
		public static String grafica;
		public static ConectorBaseDatos conector;

		public void leeArchivoConfiguracion(String archivo){
			String texto = lee(archivo);
			texto= texto.replace(" ","");
			texto= texto.replace("\t","");
			String [] parametros = texto.split("\n");
			for(int i=0;i<parametros.length;i++){
				parametros[i] = parametros[i].substring(parametros[i].indexOf("=")+1);
			}
			base = parametros[0];
			entrada = parametros[1];
			porc = Double.parseDouble(parametros[2]);
			tamanoLote=Integer.parseInt(parametros[3]);
			factorFrio=Double.parseDouble(parametros[4]);
			e=Double.parseDouble(parametros[5]);
			et=Double.parseDouble(parametros[6]);
			ep =Double.parseDouble(parametros[7]);
			f  = Double.parseDouble(parametros[8]);
			n = Integer.parseInt(parametros[9]) ;
			temp = Double.parseDouble(parametros[10]);
			salida= parametros[11];
			numeroSemillas =Integer.parseInt(parametros[12]);
			grafica = parametros[13];
		}

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
			RecocidoSimulado recocido= new RecocidoSimulado(porc,tamanoLote,factorFrio,e,et,ep,n,temp,grafica);
			for(int j=0;j<numeroSemillas;j++){
				long inicio = System.nanoTime();
				solucion.shuffle();
				Solucion semilla = new Solucion(solucion);				
				Solucion actual = recocido.lanzaSemilla(solucion);
				if(actual.compareTo(solucion)==-1&&actual.esFactible())
					solucion = actual;
				long termino = System.nanoTime();

				p(imprimeTiempo(inicio,termino));
				p(imprimePorcentaje(j,numeroSemillas)+"%");
				p("Actual:\n\n"+actual);
				p("Semilla:\n\n"+semilla);

			}
			escribe(salida,Arrays.toString(solucion.getArreglo()).replace("[","").replace("]",""));
			recocido.guardaGrafica();
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

		public static void main(String [] args) throws IOException{
			Principal principal = new Principal();
			principal.leeArchivoConfiguracion("config/configuraciones.cnf");
			conector = new ConectorBaseDatos(base);
			conector.conecta();
			Scanner sc = new Scanner(System.in);
			p("Selecciona:\n0: lanzaSemillas\n1: evaluaSoluciones");
			int i = sc.nextInt();
			if(i ==0){
				lanzaSemillas();
			}else{
				evaluaSoluciones();
			}
			conector.close();
		}
}
