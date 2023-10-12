package uni.test;

/**
 * Esta clase sirve para testear el constructor de la clase Mundo
 * Esta en otro paquete porque no tiene relacion directa con el robot.
 */
public class ConstructorMundoTest {

    /*
     * Estos son TESTS de las validaciones. Todos ellos deben fallar, por eso estan
     * comentados.
     * @param arg Argumentos desde la linea de comandos (no necesarios).
     */
    public static void main(String[] arg) {

        int fila = 10;
        int columna = 10;

        //valido fila negativa
//         Mundo marte = new Mundo("Que mundo cheamigo!! :)...", -fila, columna);

        //valido columna negativa
//         Mundo pluton = new Mundo("Que mundo cheamigo!! :)...", fila, -columna);

        //valido overflow fila
//         Mundo venus = new Mundo("Que mundo cheamigo!! :)...", 500, columna);

        //valido overflow columna
//         Mundo jupiter = new Mundo("Que mundo cheamigo!! :)...", fila, 500);

    }

}
