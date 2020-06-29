package com.mycompany.cuentas_cobrar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author andres.murillo
 */
public class IUBanco extends JPanel {
    protected final static String nombres[] = {"Número de cuenta",
        "Primer nombre", "Apellido", "Saldo", "Monto de la transacción"};
    protected JLabel etiquetas [];
    protected JTextField campos [];
    protected JButton hacerTarea1, hacerTarea2;
    protected JPanel panelInternoCentro, panelInternoSur;
    
    protected int tamanio;
    
    public static final int CUENTA = 0, PRIMERNOMBRE = 1, APELLIDO = 2, SALDO = 3, TRANSACCION = 4;
    
    public IUBanco (int miTamanio){
        tamanio=miTamanio;
        etiquetas= new JLabel [ tamanio];
        campos = new JTextField [tamanio];
        
        for (int cuenta = 0; cuenta < etiquetas.length; cuenta++)
            etiquetas [cuenta] = new JLabel (nombres [cuenta]);
        
        for (int cuenta = 0; cuenta < campos.length; cuenta++)
            campos [cuenta] = new JTextField ();
        
        panelInternoCentro = new JPanel ();
        panelInternoCentro.setLayout (new GridLayout(tamanio, 2));
        
        for (int cuenta = 0 ; cuenta < tamanio; cuenta++){
            panelInternoCentro.add(etiquetas[cuenta]);
            panelInternoCentro.add(campos[cuenta]);
        }
        
        hacerTarea1= new JButton();
        hacerTarea2= new JButton();
        
        panelInternoSur = new JPanel();
        panelInternoSur.add(hacerTarea1);
        panelInternoSur.add(hacerTarea2);
        
        setLayout(new BorderLayout());
        add(panelInternoCentro, BorderLayout.CENTER);
        add(panelInternoSur,BorderLayout.SOUTH);
        
        validate();
    }
    
    public JButton obtenerBotonHacerTarea1(){
        return hacerTarea1;
    }
    
    public JButton obtenerBotonHacerTarea2(){
        return hacerTarea2;
    }
    
    public JTextField [] obtenerCampos(){
        return campos;
    }
    
    public void borrarCampos(){
        for (int cuenta = 0;cuenta <tamanio; cuenta++)
            campos[cuenta].setText("");
    }
    
    public void establecerValoresCampos (String cadenas [] )
            throws IllegalArgumentException{
        if (cadenas.length != tamanio)
            throw new IllegalArgumentException ("Debe haber " + tamanio + "objetos String en el arreglo");
        
        for (int cuenta = 0;cuenta < tamanio; cuenta++)
            campos[cuenta].setText(cadenas[cuenta]);
    }
    
    public String [] obtenerValoresCampos(){
        String valores [] = new String [tamanio];
        for (int cuenta = 0 ; cuenta < tamanio; cuenta++)
            valores [cuenta] = campos[cuenta].getText();
        return valores;
        
    }
}
