package uni.robot;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Image;

import uni.math.Vector2D;

/**
 * Esta clase representa la idea abstracta de un robot. Los UNIBOTS coexisten
 * en un mundo poco emocionante, no existen cafeterías, tampoco cines, (es un
 * mundo finito rodeado de paredes de sólido NEUTRONIUN impenetrable), pero
 * cada robot tiene una tarea interesante asignada.
 *
 * <p>
 * El mundo esta formado por una cuadricula de calles y avenidas que los
 * robots pueden atravesar (nunca en diagonal), y conos que pueden recoger
 * con su poderoso brazo y colocarlos en una bolsa que tienen adheridos a su
 * cintura. Carlos es un robot más dentro de la gran comunidad. Como todos
 * los que allí residen, al comenzar solo sabe hacer unas pocas acciones. Con
 * el tiempo lo ayudaremos a integrarse de manera más eficiente.
 *
 * <p>
 * Nuestro robot generalmente trabaja colocando y recogiendo diversos conos
 * entre las calles y avenidas de la ciudad, donde existen paredes y
 * obstáculos (no muy complejos, pero al fin molestos) que debe superar.
 *
 * <p>
 * La finalidad de los UNIBOTS es realizar las tareas para su dueño. Cuando
 * necesitan determinar la cantidad de conos que poseen, solo tienen que
 * ingresar su brazo en la bolsa y contar.
 *
 * <p>
 * Los conos son pequeños señalizadores plásticos y están colocados únicamente
 * en las intersecciones de algunas calles y avenidas. Puede haber varios
 * robots en la misma calle, esquina, o trabajando en la misma tarea, aunque
 * generalmente ocupamos uno a la vez. Al contrario de los obstáculos, los
 * conos no interfieren con el recorrido de los UNIBOTS, pero solo puede
 * haber un cono por intersección. Los contenedores, son recipientes con uno,
 * varios, o ningún cono en su interior.
 *
 * <p>
 * Carlos ejecuta las instrucciones dadas por su dueño en forma secuencial.
 *
 * @see uni.math.Vector2D
 */
public class Robot implements IFigura {

    /**
     * Crea un nuevo objeto Robot.
     *
     * @param m El mundo donde se ubica el robot
     */
    public Robot(Mundo m) {
        this(m, 0, 0, SUR, 10, 10);
    }

    /**
     * Crea un nuevo objeto Robot.
     *
     * @param mundo El mundo en el cual existira el robot.
     * @param fila Numero de fila de donde parte el robot.
     * @param columna Numero de columna de donde parte el robot.
     * @param dirInicial Direccion Inicial: NORTE, SUR, ESTE, OESTE.
     * @param cantidadMaxima Cantidad maxima de conos en la bolsita del robot.
     * @param cantConos Cantidad inicial de conos en la bolsita del robot.
     */
    public Robot(Mundo mundo, int fila, int columna, int dirInicial,
                 int cantidadMaxima, int cantConos) {

        this.validarMundo(mundo);
        this.mundo = mundo;
        this.posicion = new Vector2D(columna, fila);
        this.cantConosBolsita = cantConos;
        this.cantidadMaxima = cantidadMaxima;
        this.dir = dirInicial;
        this.mundo.verificarDireccionInicial(this);
        this.cargarImagenPrimeraVez(); //Cargo las imagenes por primera vez
        this.imagen = imagenes[this.dir]; //asigna la imagen inicial
        this.verificarCantInicialConos(cantidadMaxima, cantConos); //Un simples checkeo
        this.mundo.verificarPosicionInicial(this); //verifico si puede ser creado en ese lugar del mundo.
        this.mundo.agregar(this); //agrego el robot al mundo
    }

    /**
     * Metodo para implementar la interfaz.
     * Se encarga de dibujar el objeto a pantalla.
     *
     * @param g Graphics El contexto grafico donde dibujar.
     * @param x int La posicion en X donde dibujar.
     * @param y int La posicion en Y donde dibujar.
     */
    public void pintar(Graphics g, int x, int y) {

        //Resto algunos pixeles para alinear la imagen sobre el cruce de calles
        final int alineacionY = 35;
        final int alineacionX = 12;

        g.drawImage(this.imagenes[this.dir], x - alineacionX, y -
                    alineacionY, null);
    }

    /**
     * Este metodo devuelve el numero de fila donde esta ubicado el robot en
     * el mundo.
     *
     * @return posicion Y  de la fila.
     */
    public int getFila() {
        return this.posicion.getY();
    }

    /**
     * Este metodo devuelve el numero de columna donde esta ubicado el robot
     * en el mundo
     *
     * @return posicion X  de la columna
     */
    public int getColumna() {
        return this.posicion.getX();
    }

    /**
     * Metodo encargado de hacer girar 90° hacia la derecha al robot partiendo
     * de su direccion actual.
     */
    public void girarDerecha() {
        this.dir--;
        if (this.dir < 0) {
            this.dir = 3;
        }
        imagen = imagenes[this.dir]; //actualizo la imagen
        this.mundo.repaint();
    }

    /**
     * Metodo encargado de hacer girar 90° hacia la izquierda al robot partiendo
     * de su direccion actual.
     */
    public void girarIzquierda() {
        dir = (dir + 1) % 4;
        imagen = this.imagenes[this.dir]; //actualizo la imagen
        this.mundo.repaint();

    }

    /**
     * Metodo encargado de hacer que el robot recoja conos.
     * <p>
     *
     * Puedo recoger un cono ponerlo en la bolsita de conos que posee el robot.
     * Puedo recoger un cono de un cruce de calles o de un recipiente de conos.
     * Puedo tener mas de un cono, contenedor y robot en el mismo cruce.
     * Solo se recoje un cono por vez.
     */
    public void guardarCono() {

        boolean seGuardoCono = false;

        if (this.cantConosBolsita == this.cantidadMaxima) {

            error("No hay suficiente espacio en la bolsa del robot.");
        }

        //Programacion defensiva
        if (this.cantConosBolsita > this.cantidadMaxima) {

            error("No puedo tener mas conos de los permitidos en la bolsita.");
        }

        //pido un arreglo con todas las figuras que exiten en la posicion actual de mi robot
        List figuras = mundo.figurasEnPosicion(this.getFila(),
                                               this.getColumna());

        for (int i = 0; i < figuras.size(); i++) {

            IFigura f = (IFigura) figuras.get(i);

            //Si la IFigura es un Cono, borro
            if (f instanceof Cono) {
                this.mundo.remover(f);
                this.mundo.repaint();
                seGuardoCono = true;
                break; // solo saco un cono a la vez
            }

            //Si la IFigura es un RecipienteConos, quito del recipiente un cono a la vez
            if (f instanceof RecipienteConos) {
                RecipienteConos recipiente = (RecipienteConos) f;
                recipiente.sacarCono();
                seGuardoCono = true;
                break;
            }
        }

        if (!seGuardoCono) {
            error("No hay cono/s para guardar.");
        }
        this.cantConosBolsita++;
    }

    /**
     * Metodo encargado de poner conos en los cruces de calles y avenidas.
     * <p>
     *
     * Saco un cono de la bolsita de conos que posee el robot.
     * Agrego un cono a un cruce de calles o a un recipiente de conos.
     * El robot no puede poner contenedores, solo conos
     */
    public void ponerCono() {

        boolean sePusoCono = false;

        if (this.cantConosBolsita == 0) {

            error("No hay suficientes conos en la bolsita");
        }

        //pido un arreglo con todas las figuras que exiten en la posicion actual de mi robot
        List figuras = mundo.figurasEnPosicion(this.getFila(),
                                               this.getColumna());

        for (int i = 0; i < figuras.size(); i++) {

            IFigura f = (IFigura) figuras.get(i);

            //Si la IFigura es un RecipienteConos, agrego al recipiente un cono a la vez
            if (f instanceof RecipienteConos) {
                RecipienteConos recipiente = (RecipienteConos) f;
                recipiente.ponerCono();

                sePusoCono = true;
                break; //solo saco un cono a la vez

            }
        }

        if (!sePusoCono) {
            new Cono(mundo, getFila(), getColumna());
        }
        this.cantConosBolsita--;
        /**/
        this.mundo.repaint();
    }

    /**
     * Este metodo sirve para determinar si hay un cono en la posicion actual del robot
     *
     * @return boolean true si hay un cono
     */
    public boolean hayCono() {
        //pido un arreglo con todas las figuras que exiten en la posicion actual de mi robot
        List figuras = mundo.figurasEnPosicion(this.getFila(),
                                               this.getColumna());

        for (int i = 0; i < figuras.size(); i++) {

            IFigura f = (IFigura) figuras.get(i);

            //Si la IFigura es un Cono retorno verdadero
            if (f instanceof Cono) {
                return true;
            }
        }

        return false;
    }

    /**
     * Este metodo sirve para determinar si hay un RecipienteConos en la posicion actual del robot
     *
     * @return boolean true si hay un RecipienteConos
     */
    public boolean hayRecipienteCono() {
        //pido un arreglo con todas las figuras que exiten en la posicion actual de mi robot
        List figuras = mundo.figurasEnPosicion(this.getFila(),
                                               this.getColumna());

        for (int i = 0; i < figuras.size(); i++) {

            IFigura f = (IFigura) figuras.get(i);

            //Si la IFigura es un Cono retorno verdadero
            if (f instanceof RecipienteConos) {
                return true;
            }
        }

        return false;
    }

    /**
     * Metodo que revisa la cantidad de conos que posee el robot en su bolsita.
     *
     * @return int Cantidad de conos que posee el robot en la bolsita.
     */
    public int cantidadConos() {
        return cantConosBolsita;
    }

    /**
     * Metodo que revisa la cantidad Maxima de conos que puede poseer el robot en su bolsita.
     *
     * @return int Cantidad maxima de conos que puede poseer el robot en la bolsita.
     */
    public int getCantidadMaximaConos() {
        return cantidadMaxima;
    }

    /**
     * Metodo que indica hacia donde apunta nuestro robot : Norte, Este, Sur,
     * Oeste.
     * <p>
     *
     * Estan representadas por 4 constantes estaticas : Robot.NORTE,
     * Robot.SUR, Robot.ESTE, Robot.OESTE.
     *
     * @return direccion partiendo de cero al NORTE y girando en sentido
     *         antihorario terminando en 3 al ESTE
     */
    public int getDireccion() {
        return dir;
    }

    /**
     * Metodo encargado de hacer avanzar al robot. Este realiza el calculo
     * del movimiento por medio de suma de vectores.
     */
    public void avanzar() {
        mundo.mover(this, this.posicion, this.direcciones[this.dir]);
        this.posicion = this.posicion.sumar(this.direcciones[this.dir]);
    }

    /*
     * Metodo encargado de verificar la cantidad maxima y la cantidad
     * inicial de conos que se asignaron al robot al momentos de crearlo
     *
     * @param cantidadMaxima La cantidad maxima de conos que puede llevar el robot
     * (el tamaño de la bolsita).
     * @param cantConos La cantidad inicial de conos que tiene el robot.
     */
    private void verificarCantInicialConos(int cantidadMaxima, int cantConos) {
        if (cantidadMaxima < 0) {

            error("La cantidad MAXIMA inicial de conos no puede ser negativa!.");
        }
        if (cantConos < 0) {
            error("La cantidad inicial de conos no puede ser negativa!.");
        }
        if (cantConos > cantidadMaxima) {
            error("Mas conos que el maximo permitido!.");
        }
    }

    /*
     *
     * Metodo encargado de cargar las imagenes por primera vez
     *
     * @see ver mas informacion sobre uso de la clase ImageIO en la
     *      documentacion de java
     */
    private void cargarImagenPrimeraVez() {

        //creo los objetos image
        try {

            this.imagenes = new Image[] {
                ImageIO.read(this.getClass().getClassLoader().getResource(
                dirImagenes + "imagenNorte.gif")),
                ImageIO.read(this.getClass().getClassLoader().getResource(
                dirImagenes + "imagenOeste.gif")),
                ImageIO.read(this.getClass().getClassLoader().getResource(
                dirImagenes + "imagenSur.gif")),
                ImageIO.read(this.getClass().getClassLoader().getResource(
                dirImagenes + "imagenEste.gif"))
            };
        } catch (IOException ex) {
            error("Error al instanciar Robot.. faltan imagenes");
        }

    }

    /*
     * Método encargado de validar que el mundo no sea null.
     *
     * @param mundo Mundo
     */
    private void validarMundo(Mundo mundo) {
        if (null == mundo) {
            error("Hay un problema con el mundo. Es null!");
        }
    }

    /*
     * Lanza una excepcion.
     *
     * @param mensaje El mensaje de error.
     */
    protected void error(String mensaje) {
        throw new RuntimeException(mensaje);
    }

    //Atributos públicos

    /**
     * Orientacion Norte dentro del mundo.
     */
    public final static int NORTE = 0;

    /**
     * Orientacion Oeste dentro del mundo.
     */
    public final static int OESTE = 1;

    /**
     * Orientacion Sur dentro del mundo.
     */
    public final static int SUR = 2;

    /**
     * Orientacion Este dentro del mundo.
     */
    public final static int ESTE = 3;

    //atributos privados
    private Mundo mundo;
    private Vector2D posicion;
    private int cantidadMaxima;
    private int cantConosBolsita; //cantidad actual de conos que tiene nuestro robot
    protected int dir; //direccion del robot en todo momento
    private final static Vector2D[] direcciones = {
        new Vector2D(0, -1), new Vector2D( -1, 0), new Vector2D(0, 1),
        new Vector2D(1, 0)
    };
    private final String dirImagenes = "imagenes" + File.separator;

    //atributos protegidos
    protected Image imagen; //representa la imagen del robot
    protected Image[] imagenes; //el arreglo de imagenes
}
