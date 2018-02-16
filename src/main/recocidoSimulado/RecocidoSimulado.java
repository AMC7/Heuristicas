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
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;
import static java.lang.Math.abs;

/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimulado{
	private double valor;
	private Grafica grafica;
	private ArrayList<Solucion> soluciones;
	private int lenLote;
	private double fEnfriamiento;
	private Solucion mejorSolucion;
	private double porAceptados;	
	private String direccionEntrada;
	private double e;
	private double et;
	private double ep;
	private int n;

	public RecocidoSimulado(double por,String entr,double [][] pesos,int lenLote,double fEnfriamiento,double e,double et,double ep,int n){
		grafica = new Grafica(pesos);
		soluciones = new ArrayList<Solucion>();
		porAceptados=por;
		this.lenLote=lenLote;
	 	this.fEnfriamiento=fEnfriamiento;
		this.e=e;
		this.et=et;
		this.ep=ep;
		direccionEntrada = entr;
		this.n=n;
	}
	
	public SimpleEntry<Double,Solucion> calculaLotes(double temperatura,Solucion s){
		int c =0;
		double r = 0.0;
		int contador =0;
		int limite = lenLote*lenLote;
		while(c<lenLote){
			Solucion s1 = s.getVecino(1);
			contador++;			
			if(s1.esMenorOIgual(temperatura,s)){
				s = s1;
				c++;
				r+=s1.getF();
			}
			if(contador==limite){
				break;			
			}
		}

		return new SimpleEntry<Double,Solucion> (r/lenLote,s);
	}	
	

	public void aceptacionPorUmbrales(double temperatura,Solucion s){
		double p=0;
		mejorSolucion = s;		
		while(temperatura>e){			
			double q = Double.POSITIVE_INFINITY;			
			while(p<=q){			
				q = p;
				SimpleEntry<Double,Solucion> dupla = calculaLotes(temperatura,s);
				p=dupla.getKey();
				s=dupla.getValue();
				mejorSolucion = s;
			}
			temperatura = fEnfriamiento*temperatura;		
		}	
	}

	public double getPorcentajeAceptados(Solucion s,double temperatura){
		int c = 0;
		n = 56;
		for(int i=0;i<n;i++){
			Solucion s1 = s.getVecino(2);
			if(s1.esMenorOIgual(temperatura,s)){
				c++;
				s = s1;
			}
		}
		return (double)c/(double)n	;
	}

	public double busquedaBinaria(Solucion s,double t1,double t2){
		double tm = (t1+t2)/2;
		if(t2-t1<et)
			return tm;			
		double p = getPorcentajeAceptados(s,tm);
		if(abs(porAceptados-p)<ep)
			return tm;
		if(p>porAceptados)
			return busquedaBinaria(s,t1,tm);
		else
			return busquedaBinaria(s,tm,t2);
	}
	
	public double temperaturaInicial(Solucion s,double temperatura){
		double p= getPorcentajeAceptados(s,temperatura);
		int tmp = (int) abs(porAceptados-p);	
		double t1 = 0.0;
		double t2 = 0.0;		
		if(tmp<=ep)
			return temperatura;
		if(p<porAceptados){
			while(p<porAceptados){
				temperatura = 2*temperatura;
				p=getPorcentajeAceptados(s,temperatura);
			}
			t1 = temperatura/2;
			t2 = temperatura;
		}else{
			while(p>porAceptados){
				temperatura = 2*temperatura;
				p=getPorcentajeAceptados(s,temperatura);
			}
			t1 = temperatura;
			t2 = temperatura/2;
		}
		return busquedaBinaria(s,t1,t2);
	}	
	public double promedioDePeso(int [] ciudades){
		return 0.0;
	}
}
