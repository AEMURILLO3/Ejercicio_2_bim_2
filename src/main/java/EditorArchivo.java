
import com.mycompany.cuentas_cobrar.RegistroCuentasAccesoAleatorio;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 *
 * @author andres.murillo
 */
public class EditorArchivo {
    RandomAccessFile archivo;
    
    public EditorArchivo (File nombreArchivo) throws IOException{
        archivo = new RandomAccessFile (nombreArchivo, "rw");
    }
    
    public void cerrarArchivo() throws IOException{
        if (archivo !=null)
            archivo.close();
    }
    
    public RegistroCuentasAccesoAleatorio obtenerRegistro (int numeroCuenta)
            throws IllegalArgumentException, NumberFormatException, IOException{
        RegistroCuentasAccesoAleatorio registro = new
            RegistroCuentasAccesoAleatorio ();
        
        if (numeroCuenta < 1 || numeroCuenta > 100)
            throw  new IllegalArgumentException ("Fuera de rango");
          
            archivo.seek((numeroCuenta -1)*RegistroCuentasAccesoAleatorio.TAMANIO);
            
             registro.leer(archivo);
             
             return registro;
    }
    
    
    public void actualizarRegistro (int numeroCuenta, String primerNombre, 
            String apellidoPaterno, double saldo)
            throws IllegalArgumentException, IOException{
        RegistroCuentasAccesoAleatorio registro = obtenerRegistro (numeroCuenta);
        if (numeroCuenta == 0)
            throw new IllegalArgumentException ("La cuenta no existe");
        
        archivo.seek((numeroCuenta -1)*RegistroCuentasAccesoAleatorio.TAMANIO);
        
        registro = new RegistroCuentasAccesoAleatorio(numeroCuenta, 
        primerNombre, apellidoPaterno, saldo);
        
        registro.escribir(archivo);
    } 
    
    public void nuevoRegistro(int numeroCuenta, String primerNombre, String apellidoPaterno,
            double saldo)
            throws IllegalArgumentException, IOException{
        RegistroCuentasAccesoAleatorio registro = obtenerRegistro(numeroCuenta);
        
        if (registro.obtenerCuenta()!=0)
            throw new IllegalArgumentException ("La cuenta ya existe");
        
        archivo.seek((numeroCuenta -1)* RegistroCuentasAccesoAleatorio.TAMANIO);
        
        registro= new RegistroCuentasAccesoAleatorio(numeroCuenta,
        primerNombre, apellidoPaterno, saldo);
        
        registro.escribir(archivo);
    }
    
    public void eliminarRegistro(int numeroCuenta)
            throws IllegalArgumentException, IOException{
        RegistroCuentasAccesoAleatorio registro = obtenerRegistro(numeroCuenta);
        
        if (registro.obtenerCuenta() == 0)
            throw new IllegalArgumentException ("La cuenta no existe");
        
        archivo.seek((numeroCuenta - 1)*RegistroCuentasAccesoAleatorio.TAMANIO);
        
        registro = new RegistroCuentasAccesoAleatorio ();
        
        registro.escribir(archivo);
    }
    
}
