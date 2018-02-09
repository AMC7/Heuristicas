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
/**@version 1.0
   @author Antonio Martinez Cruz*/
public class RecocidoSimuladoTest{
	RecocidoSimulado recocido;	
	
	@Before
	public void inicializa(){
		recocido= new RecocidoSimulado();			
	}		
	@Test	
	public void pruebaFuncionObjetivo(){
		assertTrue(recocido.funcionObjetivo()>=0.0);			
	}
}
