import uni.robot.Mundo;
import uni.robot.Robot;

/**
 * Clase de ejemplo.
 * <p>
 *
 * Realiza un recorrido por las orillas de un pequeño mundo, girando una
 * vuelta completa en cada esquina que llega.
 */
public class EjemploRecorridoGiratorio {

    /**
     * Metodo principal de la clase.
     *
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     * @throws InterruptedException Se controla la excepcion que puede lanzar
     *          el Thread.sleep
     */
    public static void main(String[] arg) throws InterruptedException {

        Mundo m = new Mundo("Eldorado City", 11, 11);
        Robot carlos = new Robot(m, 0, 0, Robot.ESTE, 10, 3);
        for (int i = 0; i < 4; i++) {
            for (int b = 0; b < 10; b++) {
                carlos.avanzar();
                for (int c = 0; c < 4; c++) {
                    carlos.girarDerecha();
                    //indicamos cuanto tiempo debe pausar
                    Thread.sleep(150);
                }
            }
            carlos.girarDerecha();
        }
    }
}
