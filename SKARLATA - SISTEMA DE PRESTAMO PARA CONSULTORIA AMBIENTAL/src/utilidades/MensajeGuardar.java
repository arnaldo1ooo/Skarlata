package utilidades;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class MensajeGuardar{
        
         
         public static void main(String[] args) {
        	 final JOptionPane pane = new JOptionPane ("Guardado con éxito");
             final JDialog dialog = pane.createDialog(null, "Guardado");
                   Timer timer = new Timer(1000, new ActionListener() {
                                   public void actionPerformed(ActionEvent evt) {
                                     dialog.dispose();
                                   }
                            });
                   timer.setRepeats(false);//Para que repita el proceso 
                   
                   timer.start();// Inicio del time
                   pane.show();
                   dialog.show();
                   
                   timer.stop(); //Fin del time
                   dialog.dispose();
         }
}