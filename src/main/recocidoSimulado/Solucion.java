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
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class Solucion{

	private Double f;
	private int [] arreglo;
	
	public Solucion(){
		
	}
	
	public Solucion(int [] arreglo2){
		arreglo = arreglo2;
	}
	
	public double getF(){
		return f;
	}	

	public void swap(int [] a,int i,int j){
		int uno = a[i];
		a[i]=a[j];
		a[j]=uno;
	} 

	public Solucion getVecino(int n){
		int [] arreglo2 = arreglo.clone();			
		for(int i=0;i<n;i++){		
			Random ran = new Random();
			int o = ran.nextInt(arreglo2.length);
			int p = ran.nextInt(arreglo2.length);
			swap(arreglo2,o,p);				
		}		
		return new Solucion(arreglo2);
	}
	
	public double funcionObjetivo(){
		return 0.0;	
	}

	public boolean esMenorOIgual(double temperatura,Solucion solucion2){
		return f<=temperatura+solucion2.f;
	}	
}
