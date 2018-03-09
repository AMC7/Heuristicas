package recocidoSimulado;
import static util.Print.*;
import grafica.Grafica;
import java.util.ArrayList;
import conector.ConectorBaseDatos;
import static util.ManejadorDeArchivos.*;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Random;
import java.io.File;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Constantes{

	public static double pesoPromedio;
	public static Grafica grafica;
	public static String graficaPath;
	public static double castigo;
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
	public static ConectorBaseDatos conector;
	public static Integer [] arreglo;
	public static Random random;
	public static String length;
	public static String direccionSemilla;	
	public static boolean barrido;

	public static String[] ponParametros(String archivo){
		String texto = lee(archivo).replace(" ","").replace("\t","");
		String [] parametros = texto.split("\n");
		for(int i=0;i<parametros.length;i++)
			parametros[i] = parametros[i].substring(parametros[i].indexOf("=")+1);
		return parametros;
	}

	public static int getNumeroArchivos(String directorio){
		File file = new File(directorio);
		return file.listFiles().length;
	}

	public static void setConstantes(String archivo){
		//Inicio las constantes que no necesitan calculos extra
		String[] parametros = ponParametros(archivo);
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
		numeroSemillas = Integer.parseInt(parametros[11]);
		graficaPath = parametros[12];	
		barrido = Boolean.parseBoolean(parametros[13]);
		textToArray(entrada);
		//Inicio las constantes que necesitan calculos extra		
		try{
			conector = new ConectorBaseDatos(base);
		}catch(Exception e){
			e.printStackTrace();
		}		
		conector.conecta();
		grafica = new Grafica(conector.getPesos());					
		setCastigo(f);
		setPesoPromedio();
		length = String.valueOf(arreglo.length);
		int valor = getNumeroArchivos("entrada/semillas/")/2;
		salida= "salidas/res/tonio-"+length+"-"+valor+".tsp";		
		direccionSemilla = "entrada/semillas/tonio-"+length+"-semilla-"+valor+".tsp";
	}

	public static void setConstantes(Integer[] arreglo,Double[][] pesos,Double f){
		Constantes.grafica = new Grafica(pesos);
		Constantes.arreglo = arreglo;
		setCastigo(f);
		setPesoPromedio();
	}

	public static void textToArray(String entrada){
		String [] texto = lee(entrada).replace(" ","").replace("\n","").split(",");
		arreglo = new Integer[texto.length];
		Integer i =0;
		for(String cadena: texto)
			arreglo[i++]=Integer.parseInt(cadena);
	}

	public static void setCastigo(Double f){
		Double maximaDistancia = 0.0;
		for(int ciudad:arreglo){
			for(int ciudad2:arreglo){
				Double peso = grafica.getPeso(ciudad,ciudad2);
				if(!peso.equals(Double.POSITIVE_INFINITY))
					if(maximaDistancia.compareTo(peso)==-1)
						maximaDistancia = peso;
			}
		}
		castigo = maximaDistancia*f;
	}

	public static void setPesoPromedio(){
		Double pesos =0.0;
		Double numeroAristas = 0.0;
		for(int ciudad:arreglo){
			for(int ciudad2:arreglo){
				Double peso = grafica.getPeso(ciudad,ciudad2);
				if(!peso.equals(Double.POSITIVE_INFINITY)&& !peso.equals(0.)){
							pesos+=peso;
							numeroAristas+=1.0;
				}
			}
		}
		pesoPromedio = (pesos/numeroAristas);
	}

}
