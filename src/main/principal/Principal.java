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

		public static String config = "";
		public static String imprimeTiempo(long inicio,long termino){
			Double diferencia = new Double(termino-inicio);
			diferencia/=1000000000.;
			return diferencia.intValue() +" segundos";
		}

		public static void lanzaSemillas(){
			Solucion mejor = new Solucion(entrada);
			for(int j=0;j<numeroSemillas;j++){
				long inicio = System.nanoTime();
				Solucion semilla = new Solucion(mejor);	
				semilla.shuffle();				
				RecocidoSimulado recocido= new RecocidoSimulado(semilla);
				evaluaSemilla(recocido);
				if(recocido.getResultado().compareTo(mejor)==-1){
					mejor = recocido.getResultado();
					save(recocido,j);
				}
				long termino = System.nanoTime();
				p(imprimeTiempo(inicio,termino)+"\n\n");
			}
		}

		public static void evaluaSemilla(RecocidoSimulado recocido){
			p("Semilla:"+recocido.getSemilla());
			Constantes.random = new Random(recocido.getSemilla().hashCode());	
			recocido.lanzaSemilla();
			p("Resultado:"+recocido.getResultado());
		}

		public static void evaluaSoluciones(){
			File file= new File("salidas/res");
			File[] archivos = file.listFiles();
			config = "salidas/res/general.cnf";			
			setConstantes(config);
			if(archivos!=null)
				for(File archivo:archivos)
					if(!archivo.isDirectory()&&archivo.getAbsolutePath().endsWith(".tsp")){
						p(new Solucion(archivo.getAbsolutePath()).getF()+": "+archivo.getName());
					}
		}


		public static void save(RecocidoSimulado recocido,int i){
			String numero = String.valueOf(i);
			String original = direccionSemilla;
			direccionSemilla = direccionSemilla.replace(".tsp","")+numero+".tsp";
			saveSemilla(recocido);	
			direccionSemilla = original;
		}

		public static void saveSemilla(RecocidoSimulado recocido){
			escribe(direccionSemilla,Arrays.toString(recocido.getSemilla().getArreglo()).replace("[","").replace("]",""));
			String direccionSemillaConf = direccionSemilla.substring(0,direccionSemilla.indexOf(".")+1)+"cnf";
			String [] tmp		 = lee(config).split("\n");
			String renglones  = "";
			for(int i =0;i<tmp.length;i++){
				if(i ==1)
					renglones+="entrada ="+direccionSemilla+"\n";
				else				
					renglones+=tmp[i]+"\n";			
			}
			escribe(direccionSemillaConf,renglones);
			escribe(graficaPath,recocido.getGrafica());
		} 
		
		public static void saveResultado(RecocidoSimulado recocido){
			escribe(graficaPath,recocido.getGrafica());	
			escribe(salida,Arrays.toString(recocido.getResultado().getArreglo()).replace("[","").replace("]",""));
		}

		public static File getFile(String path,Scanner sc){
			File file = new File(path);
			File [] files = file.listFiles();
			LinkedList<File> archivos = new LinkedList<File>();	
			for(File archivo :files)
				if(archivo.getName().substring(archivo.getName().indexOf(".")+1).equals("cnf"))
					archivos.add(archivo);
			int indice = 0;
			for(File archivo:archivos)
				p((indice++)+":"+archivo.getName());
			int valor = sc.nextInt();
			if(valor>archivos.size()){
				p("El valor:"+valor+" no es valido");				
				return null;
			}
			return archivos.get(valor);
		}

		public static void main(String [] args) throws IOException{
			Scanner sc = new Scanner(System.in);
			RecocidoSimulado recocido = null;			
			p("Selecciona:\n0: lanzaSemillas\n1: evaluaSoluciones\n2: pruebaSemilla");
			int i = sc.nextInt();
			switch(i){
				case 0:
					p("Que semilla quieres usar para el recocido?");
					File archivo = getFile("semillas",sc);	
					config = archivo.getAbsolutePath();	
					setConstantes(config);
					lanzaSemillas();
					break;
				case 1:
					evaluaSoluciones();
					break;
				case 2:
					p("Que semilla quieres ejecutar?");
					archivo = getFile("semillas",sc);	
					config = archivo.getAbsolutePath();	
					setConstantes(config);
					recocido= new RecocidoSimulado(new Solucion(entrada));			
					evaluaSemilla(recocido);
					break;
			}
			
			if(i!=1){			
				conector.close();
				p("Quieres salvar la semilla?[S/N]");
				String guardar= sc.next();
				switch(guardar){
					case "S":	
						saveSemilla(recocido);	
				}
				p("Quieres salvar el resultado?[S/N]");
				guardar= sc.next();
				switch(guardar){
					case "S":	
						saveResultado(recocido);	
				}
			}	
		}
}
