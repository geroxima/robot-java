import uni.robot.Mundo;
import uni.robot.Robot;

/**
 * Clase de ejemplo.
 * <p>
 *
 * Realiza un recorrido por los limites del mundo.
 * */
public class EjemploRecorrido {

    /**
     * Metodo principal de la clase.
     *
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     */
    public static void main(String[] arg) {

        int maximaCapacidad = 10;
        int cantidadInicial = 3;

        Mundo m = new Mundo("Eldorado City", 10, 10);
        Robot carlos1 = new Robot(m, 0, 0, Robot.ESTE, maximaCapacidad,
                                  cantidadInicial);
        for (int i = 0; i < 4; i++) {
            for (int b = 0; b < 9; b++) {
                carlos1.avanzar();
            }
            carlos1.girarDerecha();
        }
    }
}