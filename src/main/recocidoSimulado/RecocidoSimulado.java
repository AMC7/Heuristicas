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
import java.lang.Thread;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimulado{
	
	private int lenLote;
	private Double fEnfriamiento;
	private Solucion mejorSolucion;
	private Double porAceptados;	
	private Double e;
	private Double et;
	private Double ep;
	private int n;
	private double temp;


	public RecocidoSimulado(Double por,int lenLote,Double fEnfriamiento,Double e,Double et,Double ep,int n,double temperatura){
		porAceptados=por;
		this.lenLote=lenLote;
	 	this.fEnfriamiento=fEnfriamiento;
		this.e=e;
		this.et=et;
		this.ep=ep;
		this.n=n;
		this.temp = temperatura;
	}
	
	public SimpleEntry<Double,Solucion> calculaLotes(Double temperatura,Solucion s){
		int c =0;
		Double r = 0.0;
		int contador =0;
		int limite = lenLote*lenLote;
		while(c<lenLote){
			Solucion s1 = s.getVecino();
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
	
	
	public Solucion aceptacionPorUmbrales(Double temperatura,Solucion s){
		Double p=0.0;
		while(temperatura>e){			
			Double q = Double.POSITIVE_INFINITY;	
			while(p.compareTo(q)<=0){			
				q = p;
				SimpleEntry<Double,Solucion> dupla = calculaLotes(temperatura,s);
				p=dupla.getKey();
				s=dupla.getValue();
			}
			temperatura = fEnfriamiento*temperatura;		
		}
		return s;
	}

	public Double getPorcentajeAceptados(Solucion s,Double temperatura){
		int c = 0;
		for(int i=0;i<n;i++){
			Solucion s1 = s.getVecino();
			if(s1.esMenorOIgual(temperatura,s)){
				c++;
				s = s1;
			}
		}	
		return (double)c/(double)n	;
	}

	public Double busquedaBinaria(Solucion s,Double t1,Double t2){
		Double tm = (t1+t2)/2.;
		if(t2-t1<et)
			return tm;			
		Double p = getPorcentajeAceptados(s,tm);
		if(abs(porAceptados-p)<ep)
			return tm;
		if(p>porAceptados)
			return busquedaBinaria(s,t1,tm);
		else
			return busquedaBinaria(s,tm,t2);
	}

	public Double temperaturaInicial(Solucion s,Double temperatura){
		Double p= getPorcentajeAceptados(s,temperatura);
		Double t1 = 0.0;
		Double t2 = 0.0;		
		if(abs(porAceptados-p)<=ep)
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
				temperatura = temperatura/2;
				p=getPorcentajeAceptados(s,temperatura);
			}
			t1 = temperatura;
			t2 = 2*temperatura;
		}
		return busquedaBinaria(s,t1,t2);
	}	

	public Double 

	public Solucion lanzaSemilla(Solucion solucion){	
		Solucion inicial = new Solucion(solucion);
		Double tInicial =temperaturaInicial(inicial,temp);
		return aceptacionPorUmbrales(tInicial,inicial);
	}
}
