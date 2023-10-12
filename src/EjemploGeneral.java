import uni.robot.*;

/**
 * Ejemplo básico del funcionamiento del robot, conos, y recipientes de conos.
 * <p>
 *
 * Los robots trabajan en conjunto para dibujar las sigla "UNI" en la pantalla.
 * */
public class EjemploGeneral {

    /**
     * Método principal de la clase.
     *
     * @param arg Argumentos desde la línea de comandos (no necesarios).
     * @throws InterruptedException Excepcion que puede ser lanzada por el Thread.sleep
     */
    public static void main(String[] arg) throws InterruptedException {

        int maximaCapacidad = 100;
        int cantidadInicial = 0;
        int filaRobot = 2;
        int columnaRobot = 1;

        Mundo miMundo = new Mundo("Encarnacion City", 8, 15);
        Robot carlitos1 = new Robot(miMundo, filaRobot, columnaRobot,
                                    Robot.ESTE, maximaCapacidad,
                                    cantidadInicial);

        Robot carlitos2 = new Robot(miMundo, filaRobot, columnaRobot + 5,
                                    Robot.ESTE, maximaCapacidad,
                                    cantidadInicial);

        Robot carlitos3 = new Robot(miMundo, filaRobot, columnaRobot + 10,
                                    Robot.ESTE, maximaCapacidad,
                                    cantidadInicial);

        int cantMaxRecipiente = 100;
        int cantInicialRecipiente = 100;
        int filaRecipiente = 1;
        int columnaRecipiente = 1;

        RecipienteConos rc1 = new RecipienteConos(miMundo, filaRecipiente,
                                                  columnaRecipiente,
                                                  cantMaxRecipiente,
                                                  cantInicialRecipiente);
        RecipienteConos rc2 = new RecipienteConos(miMundo, filaRecipiente,
                                                  columnaRecipiente + 5,
                                                  cantMaxRecipiente,
                                                  cantInicialRecipiente);

        RecipienteConos rc3 = new RecipienteConos(miMundo, filaRecipiente,
                                                  columnaRecipiente + 10,
                                                  cantMaxRecipiente,
                                                  cantInicialRecipiente);

        dibujarU(carlitos1);
        dibujarN(carlitos2);
        dibujarI(carlitos3);

        //Hace girar a los robots
        baileFinal(carlitos1, carlitos2, carlitos3);
    }

    /**
     * Dibujamos la letra U.
     * @param carlitos Robot encargado de dibujar la letra U.
     */
    private static void dibujarU(Robot carlitos) {

        prepararRobot(carlitos);
        pintar(carlitos, 3);
        irIzquierda(carlitos);
        pintar(carlitos, 2);
        irIzquierda(carlitos);
        pintar(carlitos, 3);
    }

    /**
     * Dibujamos la letra N.
     * @param carlitos Robot encargado de dibujar la letra N.
     */
    private static void dibujarN(Robot carlitos) {

        prepararRobot(carlitos);
        avanzar(carlitos, 3);
        mediaVuelta(carlitos);
        pintar(carlitos, 3);
        carlitos.ponerCono();
        irDerecha(carlitos);
        irDerecha(carlitos);
        carlitos.ponerCono();
        irIzquierda(carlitos);
        irDerecha(carlitos);
        carlitos.ponerCono();
        irIzquierda(carlitos);
        irDerecha(carlitos);
        carlitos.ponerCono();
        mediaVuelta(carlitos);
        carlitos.avanzar();
        pintar(carlitos, 3);

    }

    /**
     * Dibujamos la letra I.
     * @param carlitos Robot encargado de dibujar la letra I.
     */
    private static void dibujarI(Robot carlitos) {

        prepararRobot(carlitos);
        pintar(carlitos, 4);
        irIzquierda(carlitos);
        irIzquierda(carlitos);
        pintar(carlitos, 4);

    }

    /*
     * Metodo encargado de hacer girar media vuelta al robot.
     * @param carlitos Robot que dará la media vuelta.
     */
    private static void mediaVuelta(Robot carlitos) {

        carlitos.girarDerecha();
        carlitos.girarDerecha();

    }

    /*
     * Hace avanzar al robot una cierta cantidad de pasos.
     * @param carlitos Robot que dará cierta cantidad de pasos.
     * @param pasos Cantidad de pasos a avanzar.
     */
    private static void avanzar(Robot carlitos, int pasos) {

        for (int i = 0; i < pasos; i++) {
            carlitos.avanzar();
        }

    }

    /*
     * Metodo encargado de preparar al robot para dibujar la letra.
     * Lleva al robot hasta el recipiente, carga conos, y lo deja
     * en posicion de comenzar a dibujar.
     * @param carlitos Robot
     */
    private static void prepararRobot(Robot carlitos) {

        carlitos.girarIzquierda();
        carlitos.avanzar();

        for (int i = 0; i < 10; i++) {
            carlitos.guardarCono();
        }

        mediaVuelta(carlitos);
        carlitos.avanzar();

    }

    /*
     * Deposita un cono y avanza
     * @param carlitos Robot que dará cierta cantidad de pasos.
     * @param pasos Cantidad de pasos a avanzar.
     **/
    private static void pintar(Robot carlitos, int pasos) {

        for (int i = 0; i < pasos; i++) {
            carlitos.ponerCono();
            carlitos.avanzar();
        }

    }

    /*
     * Gira izquierda y avanza
     * @param carlitos Es el robot que avanzará
     */
    private static void irIzquierda(Robot carlitos) {

        carlitos.girarIzquierda();
        carlitos.avanzar();

    }

    /*
     * Gira derecha y avanza
     * @param carlitos Es el robot que avanzará
     */
    private static void irDerecha(Robot carlitos) {

        carlitos.girarDerecha();
        carlitos.avanzar();

    }

    /**
     * Método encargado de ejecutar el baile de despedida.
     * @param carlitos1 Es el robot que bailará.
     * @param carlitos2 Es el robot que bailará.
     * @param carlitos3 Es el robot que bailará.
     * @throws InterruptedException Excepcion que puede ser lanzada por el Thread.sleep
     */
    private static void baileFinal(Robot carlitos1, Robot carlitos2,
                                   Robot carlitos3) throws InterruptedException {

        for (int i = 0; i < 12; i++) {
            carlitos1.girarDerecha();
            Thread.sleep(150);
            carlitos2.girarDerecha();
            Thread.sleep(150);
            carlitos3.girarDerecha();
            Thread.sleep(150);
        }

    }

}
