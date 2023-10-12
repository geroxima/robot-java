package uni.test;

import uni.robot.Mundo;

/**
 * Esta clase sirve para testear el constructor de la clase Cono
 * Esta en otro paquete porque no tiene relacion directa con el robot.
 */
public class ConstructorConoTest {

    /**
     * Estos son TESTS de las validaciones. Todos ellos deben fallar, por eso estan
     * comentados.
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     */
    public static void main(String[] arg) {

        int fila = 10;
        int columna = 10;

        Mundo tierra = new Mundo("Que mundo cheamigo!! :)...", fila, columna);

        //Testeo mundo null
//                Cono c = new Cono(null, fila, columna);

        //Testeo columna negativa
//                Cono c1 = new Cono(tierra, 5, -columna);

        //Testeo fila negativa
//                Cono c2 = new Cono(tierra, -fila, 5);

        //Testeo columna > cantColumnasMundo
//                Cono c3 = new Cono(tierra,  5, 11);

        //Testeo fila > cantFilasMundo
//                Cono c4 = new Cono(tierra, 11, 5);

        //Testeo columna cero
//                Cono c5 = new Cono(tierra, 5, 0);

        //Testeo fila cero
//                Cono c6 = new Cono(tierra, 0, 5);

        //Testeo columna sobre limite superior mundo
//                Cono c7 = new Cono(tierra, 5, columna);

        //Testeo fila sobre limite superior del mundo
//                Cono c8 = new Cono(tierra, fila, 5);

    }
}
