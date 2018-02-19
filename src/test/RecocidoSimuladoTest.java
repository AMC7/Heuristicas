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
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimuladoTest{
	RecocidoSimulado recocido;	
	ConectorBaseDatos conector;
	int [] arreglo;	
	String texto;
		
	@Test
	public void pruebaConeccion(){
		try{
			conector = new ConectorBaseDatos("/sqlite/tsp");
		}catch(Exception e){
			e.printStackTrace();			
		}		
		assertTrue("No esta bien la ruta sql.",conector.conecta());	
	} 	
	@Test
	public void pruebaVecino(){		
		File file = new File("entrada/entrada.txt");
		assertTrue("No existe el archivo de entrada",file.exists());		
		texto =lee("entrada/entrada.txt");		
		//Solucion solucion = new Solucion(texto) ;	
	}	
	
	@Test	
	public void pruebaFuncionObjetivo(){
	}
	
}
