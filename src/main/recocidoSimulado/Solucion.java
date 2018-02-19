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
import java.util.Arrays;
import grafica.Grafica;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Solucion implements Comparable<Object>{

	private Double f;
	private Integer [] arreglo;
	
	public Integer[] getArreglo(){
		return arreglo;	
	}

	@Override
	public int compareTo(Object objeto){
		if(objeto instanceof Solucion){
			Solucion other = (Solucion)objeto;
			return f.compareTo(other.f);
		}
		return -1;	
	}
	
	public Solucion(Solucion solucion,Grafica grafica,double castigo,double pesoPromedio){
		arreglo=solucion.getArreglo().clone();
		shuffle();
		setF(grafica,castigo,pesoPromedio);			
	}
	
	public Solucion(Integer [] arreglo2,Grafica grafica,double castigo,double pesoPromedio){
		arreglo = arreglo2;
		setF(grafica,castigo,pesoPromedio);	
	}
	
	public Solucion(Grafica grafica,double castigo,String entrada,Double pesoPromedio){		
		textToArray(entrada);		
		setF(grafica,castigo,pesoPromedio);
	}

	public Solucion(String entrada){		
		textToArray(entrada);
	}
	public void textToArray(String entrada){
		String [] texto = lee(entrada).replace(" ","").replace("\n","").split(",");
		arreglo = new Integer[texto.length];
		Integer i =0;		
		for(String cadena: texto)
			arreglo[i++]=Integer.parseInt(cadena);			
	}	
	
	public double getF(){
		return f;
	}

	/**Esta es la funcion de costo*/
	public void setF(Grafica grafica,double castigo,double pesoPromedio){
		double res = 0.0;
		for(int i=0;i<arreglo.length-1;i++){
			if(grafica.getPeso(arreglo[i],arreglo[i+1]).equals(Double.POSITIVE_INFINITY))
				res+=castigo;			
			else{
				res+=grafica.getPeso(arreglo[i],arreglo[i+1]);
			}
		}
		f=res/(pesoPromedio*(arreglo.length-1));
	}	

	public void swap(Integer [] a,Integer i,Integer j){
		Integer uno = a[i];
		a[i]=a[j];
		a[j]=uno;
	} 

	public Solucion getVecino(Grafica grafica,double castigo,double pesoPromedio){
		Integer [] arreglo2 = arreglo.clone();			
		Random ran = new Random();
		Integer o = ran.nextInt(arreglo2.length);
		Integer p = ran.nextInt(arreglo2.length);	
		swap(arreglo2,o,p);
		return new Solucion(arreglo2,grafica,castigo,pesoPromedio);
	}

	public void shuffle(){
		List<Integer> listaTemporal = Arrays.asList(arreglo);
		Collections.shuffle(listaTemporal);
	}

	public boolean esMenorOIgual(double temperatura,Solucion solucion2){
		return f<=temperatura+solucion2.f;
	}

	public boolean esFactible(Grafica grafica){
		for(int i=0;i<arreglo.length-1;i++){
			if(grafica.getPeso(arreglo[i],arreglo[i+1]).equals(Double.POSITIVE_INFINITY)){
				return false;			
			}
		}	
		return true;	
	}

	@Override
	public String toString(){
		return Arrays.toString(arreglo);
	}	
}
