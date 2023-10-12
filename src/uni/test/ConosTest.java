package uni.test;

import uni.robot.Mundo;
import uni.robot.Robot;

/**
 * Clase de testeo de conos.
 * Realiza un pequeño test de los conos.
 * Obs: Dos conos en la misma posicion NO se considera un bug.
 * Esta en otro paquete porque no tiene relacion directa con el robot.
 * <p>
 *
 * Objetivo:<p>
 * Verificar posicion inicial.<p>
 * Colocar/sacar conos.<p>
 * Verificar mas de un cono en la misma posicion.
 *
 * */
public class ConosTest {

    /**
     * Metodo principal de la clase.
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     */
    public static void main(String[] arg) {

        int maximaCapacidad = 100;
        int cantidadInicial = 100;

        //creo los objetos
        Mundo m = new Mundo("Eldorado City", 10, 10);
        Robot carlos1 = new Robot(m, 0, 0, Robot.NORTE, maximaCapacidad,
                                  cantidadInicial);

        m.setTitle("Depositando conos...");

        //Deposito conos en el mundo
        for (int i = 0; i < 4; i++) {
            carlos1.girarDerecha();

            for (int j = 1; j < 10; j++) {
                carlos1.ponerCono();
                carlos1.avanzar();
            }
        }

        m.setTitle("Retirando conos...");

        //Retiro conos del mundo
        for (int i = 0; i < 4; i++) {
            carlos1.girarDerecha();

            for (int j = 1; j < 10; j++) {
                carlos1.guardarCono();
                carlos1.avanzar();
            }
        }

        carlos1.girarDerecha();

        /**
         * Deposito y retiro todos los conos
         */
        //Deposito 5 conos en la misma posicion
        for (int i = 0; i < 5; i++) {
            carlos1.ponerCono();
        }

        //Retiro 5 conos
        for (int i = 0; i < 5; i++) {
            carlos1.guardarCono();
        }

        carlos1.avanzar();

        /**
         * Deposito y retiro todos los conos - 1
         */
        //Deposito 5 conos en la misma posicion
        for (int i = 0; i < 5; i++) {
            carlos1.ponerCono();
        }

        //Retiro 4 conos
        for (int i = 0; i < 4; i++) {
            carlos1.guardarCono();
        }

        carlos1.avanzar();

        m.setTitle("Trabajo finalizado...");

    }
}
