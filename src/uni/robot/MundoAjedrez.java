package uni.robot;

import java.io.File;

/**
 * Esta clase es la representacion abstracta de un tablero de Ajedrez.
 * Un tablero contiene 8 filas y 8 columnas. Puedes hacer 7 movimientos en una
 * fila y 7 movimientos en una columna.
 *
 * <p>Title: Proyecto Robot.</p>
 * <p>Description: Utilizado para la catedra de Introduccion a la Programacion II.</p>
 * <p>Copyright: Copyright (c) 2004-2006.</p>
 * <p>Company: Universidad Nacional de Itapua.</p>
 * @author Amin Mansuri. Rivas, Endrigo.
 * @version 1.6
 */
public class MundoAjedrez extends Mundo {

    /**
     * Constructores de objetos Mundo.
     */
    public MundoAjedrez() {
        super("<<Tablero de Ajedrez>>", 8, 8, "imagenes" +
              File.separator + "tableroAjedrez.gif");

    }
}
