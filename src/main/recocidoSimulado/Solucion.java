package recocidoSimulado;
import static util.Print.*;
import static util.ManejadorDeArchivos.*;
import static util.MyString.*;
import util.Dupla;
import util.Punto;
import java.util.LinkedList;
import java.lang.Math;
import java.util.Hashtable;
import grafica.Grafica;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Solucion{

	private Double f;
	private Integer [] arreglo;
	
	public Solucion(){
		
	}
	
	public Solucion(Integer [] arreglo2){
		arreglo = arreglo2;
		setF();
	}

	public Solucion(String entrada){		
		String [] texto = entrada.split(",");
		arreglo = new Integer[texto.length];
		Integer i =0;		
		for(String cadena: texto)
			arreglo[i++]=Integer.parseInt(cadena.replace("\n",""));			
		setF();
	}
	
	public double getF(){
		return f;
	}

	public void setF(){
			f =0.0;
	}	

	public void swap(Integer [] a,Integer i,Integer j){
		Integer uno = a[i];
		a[i]=a[j];
		a[j]=uno;
	} 

	public Solucion getVecino(Integer n){
		Integer [] arreglo2 = arreglo.clone();			
		Random ran = new Random();
		Integer o = ran.nextInt(arreglo2.length);
		Integer p = ran.nextInt(arreglo2.length);
		swap(arreglo2,o,p);				
		return new Solucion(arreglo2);
	}
	
	public double funcionObjetivo(){
		return 0.0;	
	}

	public void shuffle(){
		List<Integer> listaTemporal = Arrays.asList(arreglo);
		Collections.shuffle(listaTemporal);
	}

	public boolean esMenorOIgual(double temperatura,Solucion solucion2){
		return f<=temperatura+solucion2.f;
	}
	
	@Override
	public String toString(){
		return Arrays.toString(arreglo);
	}	
}
