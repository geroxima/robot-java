package uni.robot;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

import uni.math.Vector2D;

/**
 * Esta clase representa la abstraccion de un mundo real, y caracteriza el
 * ambiente de convivencia y trabajo de los robot.
 * <p>
 * El mundo es plano, y esta conformado por calles y avenidas(filas y columnas),
 * rodeado de paredes de NEUTRONIUN impenetrables.
 *
 * @see uni.math.Vector2D
 **/
public class Mundo extends Frame {

    /**
     * Constructores de objetos Mundo.
     */
    public Mundo() {
        this("<<Comunidad de Unibots>>", 12, 12);
    }

    /**
     * Creamos un nuevo objeto mundo.
     *
     * @param tituloMundo El texto ubicado en la barra de titulos del mundo.
     * @param cantFilas La cantidad de filas del mundo.
     * @param cantColumnas La cantidad de columnas del mundo.
     */
    public Mundo(String tituloMundo, int cantFilas, int cantColumnas) {

        validarFilasColumnas(cantFilas, cantColumnas);

        this.cantFilas = cantFilas;
        this.cantColumnas = cantColumnas;

        /* Asigno el espacio necesario para la ventana. Cuadricula(calles y avenidas)
         * y ademas las series de numeros y el espacio de la barra de tareas(Superior)
         * Se resta 1 porque la cuadricula comienza en cero.
         * El tamanho de la ventana se obtiene en base a la cantidad de filas y
         * columnas.
         */
        this.alturaMundo = ESPACIO_SUPERIOR + (cantFilas - 1) * this.DISTANCIA +
            2 * ESPACIO_NUMEROS;
        this.baseMundo = (cantColumnas - 1) * this.DISTANCIA +
            3 * ESPACIO_NUMEROS;
        boolean usarTablero = false;
        this.canvas = new MundoCanvas(this.cantFilas, this.cantColumnas,
                                      baseMundo, alturaMundo, usarTablero);
        this.tituloMundo = tituloMundo;
        this.configurarVentana();
        this.alinearEnPantalla();

        //Coloco el ícono en el mundo.
        try {
            this.setIconImage(ImageIO.read(this.getClass().getClassLoader().
                                           getResource("imagenes" +
                File.separator + "imagenEste.gif")));
        } catch (IOException e) {
            new RuntimeException("error al cargar el icono de la ventana");
        }

        this.setVisible(true); //Pongo en pantalla la ventana

    }

    /**
     * Creamos un nuevo objeto mundo. Constructor protegido
     *
     * @param tituloMundo El texto ubicado en la barra de titulos del mundo.
     * @param cantFilas La cantidad de filas del mundo.
     * @param cantColumnas La cantidad de columnas del mundo.
     * @param imagen
     */
    // Esto va a cambiar en un futuro
    Mundo(String tituloMundo, int cantFilas, int cantColumnas,
          String imagen) {

        validarFilasColumnas(cantFilas, cantColumnas);

        this.cantFilas = cantFilas;
        this.cantColumnas = cantColumnas;

        //Modifico la distancia
        this.DISTANCIA = 70;

        // Asigno el espacio necesario para la ventana. Cuadricula(calles y avenidas)
        // y ademas las series de numeros y el espacio de la barra de tareas(Superior)
        // Se resta 1 porque la cuadricula comienza en cero.
        // El tamanho de la ventana se obtiene en base a la cantidad de filas y
        // columnas.
        //
        this.alturaMundo = ESPACIO_SUPERIOR + cantFilas * this.DISTANCIA +
            2 * ESPACIO_NUMEROS;
        this.baseMundo = cantColumnas * this.DISTANCIA +
            3 * ESPACIO_NUMEROS;
        this.canvas = new MundoCanvas(this.cantFilas, this.cantColumnas,
                                      baseMundo, alturaMundo, true);

        this.canvas.setBackground(new Color(253, 253, 240));

        this.tituloMundo = tituloMundo;
        this.configurarVentana();
        this.alinearEnPantalla();

        //Coloco el ícono en el mundo.
        try {
            this.setIconImage(ImageIO.read(this.getClass().getClassLoader().
                                           getResource(imagen)));
        } catch (IOException e) {
            new RuntimeException("error al cargar el icono de la ventana");
        }

        this.setVisible(true); //Pongo en pantalla la ventana

    }

    /**
     * Devuelve el numero de filas que contiene el mundo
     *
     * @return int el numero de filas que contiene el mundo
     */
    public int getCantidadFilas() {
        return this.cantFilas;
    }

    /**
     * Devuelve el numero de columnas que contiene el mundo
     *
     * @return int el numero de columnas que contiene el mundo
     */
    public int getCantidadColumnas() {
        return this.cantColumnas;
    }

    /**
     * Este método infelizmente debe ser publico, debido a que  sobrescribo
     * el repaint del mundo, esto porque necesito repintar ademas del mundo
     * al canvas.
     */
    public void repaint() {
        super.repaint();
        canvas.repaint();
    }

    /**
     * Metodo que permite emitir dialogos.
     *
     * @param mensaje String El mensaje que se emitira en el dialogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showConfirmDialog(this, mensaje,
                                      "<<>>", JOptionPane.DEFAULT_OPTION);
    }

    /**
     * Agrega cualquier IFigura al mundo.
     *
     * @param figura IFigura El objeto que se agregara al mundo.
     */
    void agregar(IFigura figura) {
        canvas.agregar(figura);
    }

    /**
     * Metodo encargado de eliminar los conos del mundo.
     *
     * @param figura La figura que debe removerse.
     */
    void remover(IFigura figura) {
        canvas.remover(figura);
    }

    /**
     * Metodo encargado de verificar si el objeto esta creado dentro del mundo.
     *
     * @param figura El objeto que se esta creando(Cono, Robot, ...).
     */
    void verificarPosicionInicial(IFigura figura) {
        canvas.verificarPosicionInicial(figura);
    }

    /**
     * Metodo encargado de verificar si la direccion inicial del robot es correcta
     *
     * @param r Robot Robot que se desea verificar la direccion.
     */
    void verificarDireccionInicial(Robot r) {
        canvas.verificarDireccionInicial(r);
    }

    /**
     * Metodo encargado del movimientos del robot.
     *
     * @param f El robot.
     * @param posicionVieja Posicion actual del robot.
     * @param direccion Direccion a la que apunta el robot.
     */
    void mover(IFigura f, Vector2D posicionVieja, Vector2D direccion) {
        canvas.mover(f, posicionVieja, direccion);
    }

    /**
     * Devuelve una lista de IFiguras que hay en dicha posicion.
     * <p>
     * Necesario para el caso donde hay mas de un cono, robot, o figura
     * en la misma posicion.
     *
     * @param fila La fila en que se desea obtener los objetos.
     * @param columna La columna en que se desea obtener los objetos.
     * @return Devuelve una lista con todos los objetos ubicados en dicha posicion.
     */
    List figurasEnPosicion(int fila, int columna) {
        return canvas.figurasEnPosicion(fila, columna);
    }

    /**
     * Este metodo se encarga de todo el trabajo sucio de configuracion de la ventana.
     */
    protected void configurarVentana() {
        this.setTitle(tituloMundo); //Pongo titulo a la ventana

        this.setResizable(false); //Indico que la ventana no puede cambiar de tamaño
        this.setSize(baseMundo, alturaMundo); //Indico el tamaño de la ventana

        //Maneja el evento de cierre de la ventana
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.add(canvas);

    }

    /**
     * Metodo que alinea al mundo en el centro de la pantalla.
     */
    protected void alinearEnPantalla() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //Obtengo las dimensiones del mundo
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                         (screenSize.height - frameSize.height) / 2);
    }

    protected void validarFilasColumnas(int cantFilas, int cantColumnas) {
        if (cantFilas <= 0 | cantColumnas <= 0) {
            throw new RuntimeException(
                "Cantidad de filas o columnas incorrectas en el mundo!");
        }

        if (cantFilas > 100 | cantColumnas > 100) {
            throw new RuntimeException(
                "Cantidad de filas o columnas excesivo en el mundo!");
        }
    }

    //Atributos privados
    private String tituloMundo;
    private int cantFilas;
    private int cantColumnas;

    //Atributos protegidos
    protected int baseMundo;
    protected int alturaMundo;
    protected MundoCanvas canvas;
    protected static int DISTANCIA = 30;
    protected static final int VELOCIDAD = 5;
    protected static final int ESPACIO_SUPERIOR = 20;
    protected static final int ESPACIO_NUMEROS = 20;

}
