package uni.test;

import uni.robot.*;

/**
 * Clase de testeo del recipiente de conos.
 * Realiza un pequeño test del manejo de conos sobre el recipiente.
 * Obs: Dos recipientes en la misma posicion NO se considera un bug.
 *      Se obtienen los conos del primer recipiente agregado en esa posicion.
 * Esta en otro paquete porque no tiene relacion directa con el robot.
 * <p>
 *
 * Objetivo:<p>
 * Verificar posicion inicial.<p>
 * Colocar/sacar conos.<p>
 * Vaciar totalmente, Cargar totalmente.<p>
 * Cargar mas conos de lo permitido.
 * */
public class RecipienteConosTest {

    /**
     * Metodo principal de la clase.
     *
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     */
    public static void main(String[] arg) throws InterruptedException {

        final int cantInicialRecipiente1 = 50;
        final int cantInicialRecipiente2 = 0;
        final int cantMaxRecip = 50;
        final int cantidadInicialRobot = 10;
        final int cantMaxRobot = 100;

        Mundo m = new Mundo("Eldorado City", 10, 10);
        RecipienteConos rc1 = new RecipienteConos(m, 0, 0, cantMaxRecip,
                                                  cantInicialRecipiente1);
        RecipienteConos rc2 = new RecipienteConos(m, 0, 9, cantMaxRecip,
                                                  cantInicialRecipiente2);

        RecipienteConos rc3 = new RecipienteConos(m, 0, 0, cantMaxRecip,
                                                  cantInicialRecipiente2);

        m.setTitle("Rc1 = " + rc1.cantidadConos() + " Conos. Rc2 = " +
                   rc2.cantidadConos() + " Conos.");

        Robot carlos1 = new Robot(m, 1, 0, Robot.NORTE, cantMaxRobot,
                                  cantidadInicialRobot);
        /*
         * LLevo conos de un recipiente a otro.
         */
        Thread.sleep(500);
        carlos1.avanzar();
        carlos1.girarDerecha();

        //Saco todos los conos
        for (int i = 0; i < 50; i++) {
            carlos1.guardarCono();
        }

        m.setTitle("Llevando conos...");

        for (int i = 1; i < 10; i++) {
            carlos1.avanzar();
            Thread.sleep(150);
        }

        //Deposito todos los conos
        for (int i = 0; i < 50; i++) {
            carlos1.ponerCono();
        }

//        carlos1.ponerCono();

        m.setTitle("Rc1 = " + rc1.cantidadConos() + " Conos. Rc2 = " +
                   rc2.cantidadConos() + " Conos.");

    }
}
