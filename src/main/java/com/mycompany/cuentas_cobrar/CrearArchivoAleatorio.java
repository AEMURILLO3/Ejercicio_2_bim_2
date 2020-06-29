package com.mycompany.cuentas_cobrar;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author andres.murillo
 */
public class CrearArchivoAleatorio {
    private static final int NUMERO_REGISTROS = 100;
    
    private void crearArchivo() throws FileNotFoundException, IOException{
        JFileChooser selectorArchivo = new JFileChooser();
        selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        int resultado = selectorArchivo.showSaveDialog (null);
        
        if (resultado == JFileChooser.CANCEL_OPTION)
            return;
        
        File nombreArchivo = selectorArchivo.getSelectedFile();
        
        if (nombreArchivo == null || nombreArchivo.getName().equals(""))
            JOptionPane.showMessageDialog(null, "Nombre de archivo Incorrecto",
                    "Nombre de archivo Incorrecto", JOptionPane.ERROR_MESSAGE);
        
        else {
            try {
                RandomAccessFile archivo = 
                        new RandomAccessFile (nombreArchivo, "rw");
                
                RegistroCuentasAccesoAleatorio registroEnBlanco = 
                   new RegistroCuentasAccesoAleatorio();
                
                for (int cuenta = 0; cuenta < NUMERO_REGISTROS; cuenta ++)
                    registroEnBlanco.escribir(archivo);
                
                archivo.close();
                
                JOptionPane.showMessageDialog(null, "se Creó el archivo" +
                        nombreArchivo, "Estado", JOptionPane.INFORMATION_MESSAGE);
                
                System.exit (0);
            }
            
            catch (IOException excepcionES) {
                JOptionPane.showMessageDialog(null, "Error al procesar el archivo",
                        "Error al procesar el archivo", JOptionPane.ERROR_MESSAGE);
                
                System.exit (1);
            }
            
        }
    }
    
    public static void main (String args []) throws IOException{
        CrearArchivoAleatorio aplicacion = new CrearArchivoAleatorio();
        aplicacion.crearArchivo();
    }
    
}
