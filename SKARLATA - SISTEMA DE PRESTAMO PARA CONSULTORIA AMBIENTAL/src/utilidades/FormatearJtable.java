package utilidades;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class FormatearJtable extends DefaultTableCellRenderer {
	 
    private Format formatter;
 
    /*
     * No construtor da classe é passado o formato
     */
    public FormatearJtable(Format formatter) {
        this.formatter = formatter;
    }
 
    public void setValue(Object value) {
        //  setando o formato do cellrenderer
        try {
            if (value != null) {
                value = formatter.format(value);
            }
        } catch (IllegalArgumentException e) {
        }
        super.setValue(value);
    }
 
    /**
     *  método retorna formato para data
     */
    public static FormatearJtable getDateRenderer() {
        return new FormatearJtable(new SimpleDateFormat("dd/MM/yyyy"));
    }
     
    /**
     *  método retorna formato para data e hora
     */
    public static FormatearJtable getDateTimeRenderer() {
        return new FormatearJtable(DateFormat.getDateTimeInstance());
    }
 
    /**
     *  método retorna formato para hora
     */
    public static FormatearJtable getTimeRenderer() {
        //linha comentada é um formato de horario mais completo
        //return new FormatRenderer(DateFormat.getTimeInstance());
        return new FormatearJtable(new SimpleDateFormat("HH:mm"));
    }
}
