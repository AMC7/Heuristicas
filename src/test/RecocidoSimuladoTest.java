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
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimuladoTest{
	RecocidoSimulado recocido;	
	ConectorBaseDatos conector;
		

	@Test
	public void pruebaConeccion(){
		conector = new ConectorBaseDatos("tsp");
		assertTrue(conector.conecta());	
	} 		
	@Test	
	public void pruebaFuncionObjetivo(){
	}
	
}
