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
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimulado{
	private double valor;
	private Grafica grafica;
	private ArrayList<Solucion> soluciones;
	private int tamanoLote;
	private int maximoBusquedasVecinas;
	private double factorEnfriamiento;
	private double ceroTemperatura;	

	public RecocidoSimulado(double [][] pesos,int tamanoLote,int maximoBusquedasVecinas,double factorEnfriamiento,double ceroTemperatura){
		grafica = new Grafica(pesos);
		soluciones = new ArrayList<Solucion>();
		this.tamanoLote=tamanoLote;
	 	this.maximoBusquedasVecinas= maximoBusquedasVecinas;
	 	this.factorEnfriamiento=factorEnfriamiento;
		this.ceroTemperatura=ceroTemperatura;
	}
	
	public void calculaLotes(double temperatura,Solucion s){
		int c =0;
		double r = 0.0;
		int contador =0;
		int limite = tamanoLote*tamanoLote;
		while(c<tamanoLote){
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
		//return r/tamanoLote,s
	}	
	
	public double promedioDePeso(int [] ciudades){
		return 0.0;
	}
}
