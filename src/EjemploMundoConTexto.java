import java.awt.Color;

import uni.robot.MundoConTexto;
import uni.robot.Robot;

/**
 * En esta clase de ejemplo coexisten el robot y el area de texto.
 * Ambos son componentes que trabajan por separado, y estan agrupados por
 * un mundo.
 * <p>
 *
 * El robot realiza un recorrido, y coloca algunos conos.
 * Una pequeña frase de ejemplo con colores en el area de texto.
 * */
public class EjemploMundoConTexto {

    /**
     * Metodo principal de la clase.
     *
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     */
    public static void main(String[] arg) {

        //Primero elaboro una frase
        String texto = "Habia una vez un gato, no el de Shrek, era otro...";

        //creo nuestro nuevo mundo con texto
        MundoConTexto m = new MundoConTexto(texto, "Eldorado City", 5, 15);

        //Luego, asigno algunos colores
        for (int i = 1; i < texto.length(); i++) {
            m.setColor(Color.BLACK, i - 1);
            m.setColor(Color.GREEN, i);

            try {
                //Gano tiempo en cada cambio de color
                Thread.sleep(200);
            } catch (InterruptedException ex) {}
        }

        //Creo un robot
        Robot carlos1 = new Robot(m, 0, 0, Robot.ESTE, 10, 5);

        //ejecuto acciones
        carlos1.avanzar();
        for (int i = 0; i < 4; i++) {
            carlos1.avanzar();
            carlos1.girarDerecha();
            carlos1.ponerCono();
        }
        carlos1.girarIzquierda();
        carlos1.girarIzquierda();
        carlos1.avanzar();

    }
}
