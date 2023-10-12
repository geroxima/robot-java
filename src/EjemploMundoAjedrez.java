/**
 * <p>Title: Proyecto Robot.</p>
 * <p>Description: Utilizado para la catedra de Introduccion a la Programacion II.</p>
 * <p>Copyright: Copyright (c) 2004-2006.</p>
 * <p>Company: Universidad Nacional de Itapua.</p>
 * @author Amin Mansuri. Rivas, Endrigo.
 * @version 1.8
 */

import uni.robot.MundoAjedrez;
import uni.robot.Robot;

public class EjemploMundoAjedrez {

    public static void main(String[] args) {

        //Creo un mundo que representa un tablero de Ajedrez
        MundoAjedrez m = new MundoAjedrez();
        Robot r = new Robot(m);
    }

}
