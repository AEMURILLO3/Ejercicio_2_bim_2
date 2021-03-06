// clase para procesar transacciones 
import com.mycompany.cuentas_cobrar.IUBanco;
import com.mycompany.cuentas_cobrar.RegistroCuentasAccesoAleatorio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 *
 * @author andres.murillo
 */
public class ProcesadorTransacciones extends JFrame{
    private IUBanco interfazUsuario;
    private JMenuItem elementoNuevo, elementoActualizar, elementoEliminar,
      elementoAbrir, elementoSalir;
    private JTextField campos[];
    private JTextField campoCuenta, campoTransaccion;
    private JButton botonAccion, botonCancelar;
    private EditorArchivo archivoDatos;
    private RegistroCuentasAccesoAleatorio registro;
    
    public ProcesadorTransacciones (){
        super ("Procesador de Transacciones");
        //barras de menus y menu archivo
        interfazUsuario = new IUBanco (5);
        getContentPane().add(interfazUsuario);
        interfazUsuario.setVisible(false);
        //boton de accion
        botonAccion = interfazUsuario.obtenerBotonHacerTarea1();
        botonAccion.setText("Guardar Cambios");
        botonAccion.setEnabled (false);
        
        botonAccion.addActionListener(
        new ActionListener(){ //clase interna 
             public void actionPerformed(ActionEvent evento){
             String accion = evento.getActionCommand();
             realizarAccion ( accion);
            } //fin metodo actionPerformed
        }//fin clase interna
        ); //fin addActionListener
        
        botonCancelar = interfazUsuario.obtenerBotonHacerTarea2();
        botonCancelar.setText("Cancelar");
        botonCancelar.setEnabled(false);
        
        botonCancelar.addActionListener(
        new ActionListener(){
            public void actionPerformed ( ActionEvent evento){
                interfazUsuario.borrarCampos();
            }
        });
        
        campos = interfazUsuario.obtenerCampos();
        campoCuenta = campos [IUBanco.CUENTA];
        campoCuenta.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                mostrarRegistro("0");
            }
            
        });
        
        //crear refeencia campo transaccion
        campoTransaccion = campos [IUBanco.TRANSACCION ];
        campoTransaccion.addActionListener(
        new ActionListener (){
            public void actionPerformed (ActionEvent evento){
                mostrarRegistro(campoTransaccion.getText());
            }
        });
        
        JMenuBar barraMenus = new JMenuBar(); //establecer menu
        setJMenuBar(barraMenus);
        
        JMenu menuArchivo = new JMenu("Archivo");
        barraMenus.add (menuArchivo);
        
        //establecer elemento de menú para agregar un registro
        elementoNuevo = new JMenuItem ("Nuevo Registro");
        elementoNuevo.setEnabled(false);
        
        elementoNuevo.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent evento){
                // configurar los campos de la GUI para su edición

                campos [IUBanco.CUENTA].setEnabled(true);
                campos [IUBanco.PRIMERNOMBRE].setEnabled(true);
                campos [IUBanco.APELLIDO].setEnabled(true);
                campos [IUBanco.SALDO].setEnabled(true);
                campos [IUBanco.TRANSACCION].setEnabled(false);
                
                botonAccion.setEnabled(true);
                botonAccion.setText("Crear");
                botonCancelar.setEnabled(true);
                
                interfazUsuario.borrarCampos();
                
            }
        });
        //establecer elemento de menú para actualizar un registro
        elementoActualizar = new JMenuItem("Actualizar registro");
        elementoActualizar.setEnabled(false);
        
        elementoActualizar.addActionListener(
        new ActionListener (){
            public void actionPerformed (ActionEvent evento){
                campos[IUBanco.CUENTA].setEnabled(true);
                campos [IUBanco.PRIMERNOMBRE].setEnabled(false);
                campos [IUBanco.APELLIDO].setEnabled(false);
                campos [IUBanco.SALDO].setEnabled(false);
                campos [IUBanco.TRANSACCION].setEnabled(true);
                
                botonAccion.setEnabled(true);
                botonAccion.setText("Actualizar");
                botonCancelar.setEnabled(true);
                
                interfazUsuario.borrarCampos();//restablecer campos de texto    
            }
        });
        
        elementoEliminar = new JMenuItem ("Eliminar registro");
        elementoEliminar.setEnabled (false);
        
        elementoEliminar.addActionListener(
        new ActionListener (){
            public void actionPerformed (ActionEvent evento){
              campos[IUBanco.CUENTA].setEnabled(true);
                campos [IUBanco.PRIMERNOMBRE].setEnabled(false);
                campos [IUBanco.APELLIDO].setEnabled(false);
                campos [IUBanco.SALDO].setEnabled(false);
                campos [IUBanco.TRANSACCION].setEnabled(false);  
                
                botonAccion.setEnabled(true);
                botonAccion.setText("Eliminar");
                botonCancelar.setEnabled(true);
                
                interfazUsuario.borrarCampos();
            }
        });
        
        elementoAbrir = new JMenuItem("Nuevo / abrir archivo");
        elementoAbrir.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent evento){
               // tratar de abrir el archivo
                if (!abrirArchivo())
                    return;
                
                elementoNuevo.setEnabled (true);
                elementoActualizar.setEnabled (true);
                elementoEliminar.setEnabled (true);
                elementoAbrir.setEnabled (false);
                
                interfazUsuario.setVisible(true);
                campos[IUBanco.CUENTA].setEnabled (false);
                campos[IUBanco.PRIMERNOMBRE].setEnabled (false);
                campos[IUBanco.APELLIDO].setEnabled (false);
                campos[IUBanco.SALDO].setEnabled (false);
                campos[IUBanco.TRANSACCION].setEnabled (false);
                
            }
        });
        // establecer elemento de menú para salir del programa
        elementoSalir = new JMenuItem ("Salir");
        elementoSalir.addActionListener(
                new ActionListener () {
                    public void actionPerformed (ActionEvent evento){
                        try {
                            archivoDatos.cerrarArchivo ();
                            }
                        catch (IOException excepcionES) {
                            JOptionPane.showMessageDialog(
                            ProcesadorTransacciones.this, "Error al cerrar el archivo",
                                    "Error de ES", JOptionPane.ERROR_MESSAGE);                            
                        }
                        
                        finally {
                            System.exit(0);
                        }
                    }
             });
        // adjuntar elementos de menú a l menú Archivo
        menuArchivo.add (elementoAbrir);
        menuArchivo.add (elementoNuevo);
        menuArchivo.add (elementoActualizar);
        menuArchivo.add (elementoEliminar);
        menuArchivo.addSeparator ();
        menuArchivo.add (elementoSalir);
        
        setSize (400, 250);
        setVisible (true);
    }  // fin del constructor

    
    public static void main (String args []){
        new ProcesadorTransacciones ();
        
    }
    
    private boolean abrirArchivo(){
        JFileChooser selectorArchivo = new JFileChooser ();
        selectorArchivo.setFileSelectionMode ( JFileChooser.FILES_ONLY);
        
        int resultado = selectorArchivo.showOpenDialog (this);
        
        if (resultado == JFileChooser.CANCEL_OPTION)
            return false;
        
        File nombreArchivo = selectorArchivo.getSelectedFile();
        
        if (nombreArchivo == null || nombreArchivo.getName().equals ("")){
           JOptionPane.showMessageDialog (this, "Nombre de archivo Incorrecto",
                   "Nombre de archivo Incorrecto", JOptionPane.ERROR_MESSAGE);
           return false;
           }
        try {
            archivoDatos = new EditorArchivo (nombreArchivo);
            }
        catch (IOException excepcionES){
            JOptionPane.showMessageDialog (this, "Error al abrir el archivo",
                    "Error de ES", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }// fin del método abrirArchivo
        
    private void realizarAccion (String accion){
        try {
            // obtener los valores de los campos de texto

            String [] valores = interfazUsuario.obtenerValoresCampos();
            
            int numeroCuenta = Integer.parseInt (valores[IUBanco.CUENTA]);
            
            String primerNombre = valores [IUBanco.PRIMERNOMBRE];
            String apellidoPaterno = valores [IUBanco.APELLIDO];
            double saldo = Double.parseDouble (valores [IUBanco.SALDO]);
            
            if (accion.equals ("Crear"))
                archivoDatos.nuevoRegistro (numeroCuenta, primerNombre, apellidoPaterno, saldo);
            
            else if (accion.equals ("Actualizar"))
                archivoDatos.actualizarRegistro (numeroCuenta, primerNombre, apellidoPaterno, saldo);
            
            else if (accion.equals ("Eliminar"))
                archivoDatos.eliminarRegistro (numeroCuenta);
            
            else JOptionPane.showMessageDialog (this, "Acción Incorrecta",
                    "Error al ejecutar la acción", JOptionPane.ERROR_MESSAGE); 
        }// fin del bloque try

        
        catch (NumberFormatException formato) {
            JOptionPane.showMessageDialog (this, "Entrada Incorrecta",
                    "Error en formato de número", JOptionPane.ERROR_MESSAGE);   
        }
        
        catch (IllegalArgumentException cuentaIncorrecta){
            JOptionPane.showMessageDialog(this, cuentaIncorrecta.getMessage(),
                    "Número de cuenta Incorrecto", JOptionPane.ERROR_MESSAGE);
        }
        
        catch (IOException excepcionES){
            JOptionPane.showMessageDialog(this, "Error al escribir en el archivo",
                    "Error de ES", JOptionPane.ERROR_MESSAGE);
        }
    }// fin del método realizarAccion

    
    private void mostrarRegistro(String transaccion){
        try {
            int numeroCuenta = Integer.parseInt(
            interfazUsuario.obtenerValoresCampos()[IUBanco.CUENTA]);
            
            RegistroCuentasAccesoAleatorio registro = 
                    archivoDatos.obtenerRegistro (numeroCuenta);
            
            if (registro.obtenerCuenta () == 0)
                JOptionPane.showMessageDialog(this, "El registro no existe",
                        "Número de cuenta incorrecto", JOptionPane.ERROR_MESSAGE);
            //obtener la transaccion
            double cambiar = Double.parseDouble (transaccion);
            // crear un arreglo de cadena para enviarlo a los campos de texto

            String[] valores = {String.valueOf (registro.obtenerCuenta ()),
                registro.obtenerPrimerNombre(), registro.obtenerApellidoPaterno(),
                String.valueOf(registro.obtenerSaldo() + cambiar),
                "Compras (+) o pago (-)"
            };
            
            interfazUsuario.establecerValoresCampos (valores);
            
        }// fin del bloque try
        
        catch (NumberFormatException formato){
            JOptionPane.showMessageDialog(this, "Entrada Incorrecta",
                    "Error en formato de número", JOptionPane.ERROR_MESSAGE);
            }
        
        catch (IllegalArgumentException cuentaIncorrecta){
            JOptionPane.showMessageDialog (this, cuentaIncorrecta.getMessage(),
                    "Número de cuenta Incorrecto", JOptionPane.ERROR_MESSAGE);
        }
        
        catch (IOException excepcionES){
            JOptionPane.showMessageDialog (this, "Error al leer el archivo",
                    "Error de ES", JOptionPane.ERROR_MESSAGE);
        }
        
    } // fin del método mostrarRegistro

    
}// fin de la clase ProcesadorTransacciones

