package uni.test;

import uni.robot.*;

/**
 * Clase de testeo del robot.
 * Realiza un completo test del funcionamiento del robot.
 * Esta en otro paquete porque no tiene relacion directa con el robot.
 * <p>
 *
 * <b>Objetivo:</b><p>
 * Verificar posicion inicial.<p>
 * Colocar/sacar conos.<p>
 * Vaciar totalmente, Cargar totalmente la bolsita de conos.<p>
 * Cargar mas conos de lo permitido de la bolsita.<p>
 * Extraer mas conos que lo permitido de la bolsita.<p>
 * Choque pared : ESTE, OESTE, NORTE, SUR.<p>
 * Verificar metodo hayCono().<p>
 * Verificar metodo hayRecipienteCono().<p>
 * Verificar metodo getCantidadMaximaConos().<p>
 * Verificar giros a la derecha e izquierda.<p>
 * Verificar metodo getDireccion().
 *
 **/

public class RobotTest {

    /**
     * Metodo principal de la clase.
     *
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     * @throws InterruptedException Se controla la excepcion que puede lanzar
     *          el Thread.sleep
     */
    public static void main(String[] arg) throws InterruptedException {

        int maximaCapacidad = 100;
        int cantidadInicial = 0;

        Mundo miMundo = new Mundo("Encarnacion City", 20, 20);
        Robot carlitos = new Robot(miMundo, 0, 0, Robot.ESTE, maximaCapacidad,
                                   cantidadInicial);

        RecipienteConos rc1 = new RecipienteConos(miMundo, 0, 1, 100, 100);

        /**
         * Colocar/Sacar conos.
         */
        carlitos.avanzar();
        miMundo.setTitle("Robot Carlitos tiene: " + carlitos.cantidadConos() +
                         " conos");

        for (int i = 0; i < 100; i++) {
            carlitos.guardarCono();
        }

        miMundo.setTitle("Robot Carlitos tiene: " + carlitos.cantidadConos() +
                         " conos");
        Thread.sleep(500);

        for (int i = 0; i < 100; i++) {
            carlitos.ponerCono();
        }

        miMundo.setTitle("Robot Carlitos tiene: " + carlitos.cantidadConos() +
                         " conos");

        carlitos.avanzar();

        /*
         * Extraer mas conos que lo permitido de la bolsita.<p>
         */
        //       carlitos.ponerCono();


        /**
         * Cargar mas conos de lo permitido de la bolsita.<p>
         */
//       RecipienteConos rc2 = new RecipienteConos(miMundo, 0, 3, 200, 200);
//        carlitos.avanzar();

//        for (int i = 0; i < 101; i++) {
//            carlitos.guardarCono();
//        }

        /**
         * Choque pared : ESTE, OESTE, NORTE, SUR.<p>
         */
        //NORTE
        Robot robin = new Robot(miMundo, 0, 19, Robot.NORTE, maximaCapacidad,
                                cantidadInicial);
//        robin.avanzar();

        //ESTE
        Robot pitufina = new Robot(miMundo, 19, 19, Robot.ESTE, maximaCapacidad,
                                   cantidadInicial);
//        pitufina.avanzar();

        //SUR
        Robot superman = new Robot(miMundo, 19, 0, Robot.SUR, maximaCapacidad,
                                   cantidadInicial);
//        superman.avanzar();

        //OESTE
        Robot batman = new Robot(miMundo, 0, 0, Robot.OESTE, maximaCapacidad,
                                 cantidadInicial);
//        batman.avanzar();


        /**
         *Verificar metodo hayCono().<p>
         */
        Robot donatello = new Robot(miMundo, 2, 0, Robot.ESTE, maximaCapacidad,
                                    cantidadInicial);
        //       donatello.guardarCono();
        Cono c = new Cono(miMundo, 2, 1);
        donatello.avanzar();
        donatello.guardarCono();
        donatello.avanzar();

        /**
         * Verificar metodo hayRecipienteCono().<p>
         */
        RecipienteConos rc3 = new RecipienteConos(miMundo, 2, 3, 1, 1);
        donatello.avanzar();
        donatello.guardarCono();
        donatello.ponerCono();
        donatello.guardarCono();
//        donatello.guardarCono();

        /**
         * Verificar metodo getCantidadMaximaConos().<p>
         */
        miMundo.setTitle("Cant. Max Conos donatello: " +
                         donatello.getCantidadMaximaConos() + "");

        /**
         * Verificar giros a la derecha e izquierda.<p>
         */
        donatello.girarDerecha();
        donatello.girarIzquierda();

        /**
         * Verificar metodo getDireccion().<p>
         */
        if (donatello.getDireccion() != Robot.ESTE) {
            System.out.println("Direccion Erronea!");
        } else {
            System.out.println("Direccion ESTE");
        }

    }
}
