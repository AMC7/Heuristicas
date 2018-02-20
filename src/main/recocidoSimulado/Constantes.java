package recocidoSimulado;
import static util.Print.*;
import grafica.Grafica;	
import java.util.ArrayList;
import conector.ConectorBaseDatos;
import static util.ManejadorDeArchivos.*;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Constantes{
	
	public static double pesoPromedio;
	public static Grafica grafica;
	public static double castigo;
	private static Integer [] arreglo;

	public static void setConstantes(String entrada,ConectorBaseDatos conector,Double f){
		grafica = new Grafica(conector.getPesos());				
		textToArray(entrada);		
		setCastigo(f);	
		setPesoPromedio();
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
			for(int i =0;i<grafica.getArreglo()[ciudad].length;i++){
				Double peso = grafica.getPeso(ciudad,i);				
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
		for(int ciudad:arreglo)
			for(int i =0;i<grafica.getArreglo()[ciudad].length;i++){
				Double peso = grafica.getPeso(ciudad,i);				
				if(!peso.equals(Double.POSITIVE_INFINITY)){
							pesos+=peso;
							numeroAristas+=1.0;
				}		
		}
		pesoPromedio = pesos/numeroAristas;
	}	

}
