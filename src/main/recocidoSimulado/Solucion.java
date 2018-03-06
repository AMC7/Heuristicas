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
import java.lang.Math;
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

	public Solucion(Integer [] arreglo){
		this.arreglo=arreglo.clone();
		setF();
	}

	public Solucion(Integer [] arreglo, Double f){
		this.arreglo=arreglo.clone();
		this.f = f;
	}

	public Solucion(Solucion solucion){
		this(solucion.getArreglo(),solucion.getF());
	}

	public Solucion(Solucion solucion,Integer [] arreglo2,int o,int p){
		arreglo = arreglo2.clone();
		setF(solucion,o,p);
	}

	public Solucion(Grafica grafica,double castigo,String entrada){
		textToArray(entrada);
		setF();
	}

	public Solucion(String entrada){
		textToArray(entrada);
		setF();
	}

	public void textToArray(String entrada){
		String [] texto = lee(entrada).replace(" ","").replace("\n","").replace("[","").replace("]","").split(",");
		arreglo = new Integer[texto.length];
		Integer i =0;
		for(String cadena: texto)
			arreglo[i++]=Integer.parseInt(cadena);
	}

	public Double getF(){
		return f;
	}

	public double funcionCosto(int i, int j){
		if(Constantes.grafica.getPeso(arreglo[i],arreglo[j]).equals(Double.POSITIVE_INFINITY))
				return Constantes.castigo;
		else
				return Constantes.grafica.getPeso(arreglo[i],arreglo[j]);

	}
	/**Esta es la funcion de costo*/
	public void setF(){
		double res = 0.0;
		for(int i=0;i<arreglo.length-1;i++){
			res+=funcionCosto(i,i+1);
		}
		f=res/(Constantes.pesoPromedio*(arreglo.length-1));
	}

	public boolean esValido(int i){
		return i<arreglo.length&& i>=0;
	}
	public void setF(Solucion solucion,int o,int p){
		Double res = solucion.getF()*(Constantes.pesoPromedio*(arreglo.length-1));
		int min = Math.min(o,p);
		int max = Math.max(o,p);
		if(esValido(min)&&esValido(max)){
			if(esValido(min-1)){
				res-=solucion.funcionCosto(min,min-1);
				res+=funcionCosto(min,min-1);
			}
			if(esValido(min+1)){
				res-=solucion.funcionCosto(min,min+1);
				res+=funcionCosto(min,min+1);
			}
			if(esValido(max-1)){
				res-=solucion.funcionCosto(max,max-1);
				res+=funcionCosto(max,max-1);
			}
			if(esValido(max+1)){
				res-=solucion.funcionCosto(max,max+1);
				res+=funcionCosto(max,max+1);
			}

		}
		f=res/(Constantes.pesoPromedio*(arreglo.length-1));
	}

	public void swap(Integer [] a,Integer i,Integer j){
		Integer uno = a[i];
		a[i]=a[j];
		a[j]=uno;
	}

	public Solucion getVecino(){
		Integer [] arreglo2 = arreglo.clone();
		Integer o = Constantes.random.nextInt(arreglo2.length);
		Integer p = Constantes.random.nextInt(arreglo2.length);
		swap(arreglo2,o,p);
		return new Solucion(this,arreglo2,o,p);
	}

	public void shuffle(){
		List<Integer> listaTemporal = Arrays.asList(arreglo);
		Collections.shuffle(listaTemporal);
		setF();
	}

	public boolean esMenorOIgual(double temperatura,Solucion solucion2){
		return f<=temperatura+solucion2.f;
	}

	public boolean esFactible(){
		for(int i=0;i<arreglo.length-1;i++)
			if(Constantes.grafica.getPeso(arreglo[i],arreglo[i+1]).equals(Double.POSITIVE_INFINITY))
				return false;
		return true;
	}

	@Override
	public int hashCode(){
		return Arrays.toString(arreglo).hashCode();
	}

	@Override
	public String toString(){
		return Arrays.toString(arreglo)+"\nf:"+getF()+"\nesFactible:"+esFactible();
	}

	@Override
	public boolean equals(Object object){
		if(object instanceof Solucion){
			Solucion other = (Solucion)object;
			return Arrays.equals(other.getArreglo(),arreglo)&&other.getF().equals(f);
		}
		return false;
	}
}
