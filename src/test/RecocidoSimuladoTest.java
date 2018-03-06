import static util.Print.*;
import static util.ManejadorDeArchivos.*;
import static util.MyString.*;
import util.Dupla;
import util.Punto;
import java.util.LinkedList;
import java.lang.Math;
import java.util.Hashtable;
import recocidoSimulado.RecocidoSimulado;
import org.junit.*;
import static org.junit.Assert.*;
import conector.ConectorBaseDatos;
import java.sql.SQLException;
import recocidoSimulado.*;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.lang.Math;
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimuladoTest{
	RecocidoSimulado recocido;
	ConectorBaseDatos conector;
	int [] arreglo;
	String texto;

	@Test
	public void pruebaVecino(){
		Integer [] arreglo = {0,1,2,3,4};
		Double [][] matriz = {{0.,5.,6.,7.,8.},
							  {5.,0.,10.,Double.POSITIVE_INFINITY,12.},
							  {6.,10.,0.,15.,21.},
							  {7.,Double.POSITIVE_INFINITY,15.,0.,20.},
							  {8.,12.,21.,20.,0.}
							 };
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[i].length;j++){
				assertTrue(matriz[i][j].equals(matriz[j][i]));
			}
		}
		Constantes.setConstantes(arreglo,matriz,1.);
		Solucion  solucion = new Solucion(arreglo);
		solucion.shuffle();
		Solucion  temp= solucion.getVecino();
		Solucion  temp1= new Solucion(temp.getArreglo());
		boolean booleano  = Math.abs(temp.getF()-temp1.getF())<=0.00001;
		assertTrue(temp.toString()+"!="+temp1.toString(),booleano);
	}

	@Test
	public void pruebaPromedio(){ 
		int tamano = 150;
		Double [][] matriz = new Double [tamano][tamano]; 	
		Integer [] arreglo = new Integer [matriz.length];
		for(int i =0;i<arreglo.length;i++)
			arreglo[i]=i;				
		int valor = 1;		
		for(int i =0;i<tamano;i++)
			for(int j =0;j<tamano;j++){
				matriz[i][j] = (double)valor++;
			}
		Constantes.setConstantes(arreglo,matriz,1.);
		double cuadrado = (double)tamano*tamano;
		double promEsp = ((cuadrado*(cuadrado+1.))/2.)/(cuadrado);
		assertTrue(Constantes.pesoPromedio==promEsp);	
				
	}


}
