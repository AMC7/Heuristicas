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
import static recocidoSimulado.Constantes.*;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimulado{

	private Solucion semilla;
	private Solucion mejorSolucion;
	private String print;
	
	public RecocidoSimulado(Solucion solucion){
		semilla = new Solucion(solucion);
		mejorSolucion = new Solucion(solucion);
	}

	public String getGrafica(){
		return print;
	}

	public Solucion getSemilla(){
		return semilla;	
	}

	public Solucion getResultado(){
		return mejorSolucion;	
	}

	public void hasBarrido(Solucion solucion){
		int tam = solucion.getArreglo().length;
		Solucion minimoLocal = new Solucion(solucion);				
		imprimeElValor(minimoLocal);		
		do{
			mejorSolucion = new Solucion(minimoLocal);
			imprimeElValor(minimoLocal);			
			for(int i=0;i<tam;i++){
				for(int j=i+1;j<tam;j++){
					Solucion tmp = mejorSolucion.getVecino(i,j);
					if(tmp.compareTo(mejorSolucion)==-1){
						minimoLocal = tmp;
					}
				}
			}
		}while(!minimoLocal.getF().equals(mejorSolucion.getF()));
			
	}

	public SimpleEntry<Double,Solucion> calculaLotes(Double temperatura,Solucion s){
		int c =0;
		Double r = 0.0;
		int contador =0;
		int limite = tamanoLote*20;
		while(c<tamanoLote){
			Solucion s1 = s.getVecino();
			if(s1.esMenorOIgual(temperatura,s)){
				s = s1;
				c++;
				r+=s1.getF();
			}
			if(contador++==limite){
				break;
			}
		}

		return new SimpleEntry<Double,Solucion> (r/tamanoLote,s);
	}

	public void imprimeElValor(Solucion s){
		print+=String.valueOf(s.getF())+"\n";		
	}

	public Solucion aceptacionPorUmbrales(Double temperatura,Solucion s){
		Double p=0.0;
		while(temperatura>e){
			Double q = Double.POSITIVE_INFINITY;
			int repeticiones = 0;
			while(p.compareTo(q)<=0){
				q = p;
				SimpleEntry<Double,Solucion> dupla = calculaLotes(temperatura,s);
				p=dupla.getKey();
				s=dupla.getValue();
				imprimeElValor(s);
			}
			temperatura = factorFrio*temperatura;
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
		if(abs(porc-p)<ep)
			return tm;
		if(p>porc)
			return busquedaBinaria(s,t1,tm);
		else
			return busquedaBinaria(s,tm,t2);
	}

	public Double temperaturaInicial(Solucion s,Double temperatura){
		Double p= getPorcentajeAceptados(s,temperatura);
		Double t1 = 0.0;
		Double t2 = 0.0;
		if(abs(porc-p)<=ep)
			return temperatura;
		if(p<porc){
			while(p<porc){
				temperatura = 2*temperatura;
				p=getPorcentajeAceptados(s,temperatura);
			}
			t1 = temperatura/2;
			t2 = temperatura;
		}else{
			while(p>porc){
				temperatura = temperatura/2;
				p=getPorcentajeAceptados(s,temperatura);
			}
			t1 = temperatura;
			t2 = 2*temperatura;
		}
		return busquedaBinaria(s,t1,t2);
	}

	public void lanzaSemilla(){
		print="";
		Double tInicial;
		tInicial =temperaturaInicial(semilla,temp);
		if(barrido){
			hasBarrido(semilla);
			tInicial*=1.99;
		}		
		mejorSolucion =  aceptacionPorUmbrales(tInicial,mejorSolucion);
		if(barrido){
			hasBarrido(mejorSolucion);
		}
	}
}
