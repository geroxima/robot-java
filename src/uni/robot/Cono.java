package uni.robot;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.*;

import uni.math.Vector2D;

/**
 * Esta clase representa la abstraccion de lo que es un cono real, de esos
 * que se usan en la calle para señalizacion.
 * */
public class Cono implements IFigura {

    /**
     * Constructor. Crea un nuevo objeto Cono.
     *
     * @param m Es el mundo donde agregar el cono.
     * @param columna Posicion de la columna donde poner el cono.
     * @param fila Posicion de la fila donde poner el cono.
     */
    public Cono(Mundo m, int fila, int columna) {
        this.validarMundo(m);
        this.posicion = new Vector2D(columna, fila);
        this.mundo = m;
        this.cargarImagenPrimeraVez();
        this.mundo.verificarPosicionInicial(this); //verifico si puede ser creado en ese lugar del mundo.
        this.mundo.agregar(this);

    }

    /**
     * Pinta el objeto en el mundo en las coordenadas de pantalla dadas.
     *
     * @param g el contexto grafico al que se tiene que pintar.
     * @param x la posicion x de la pantalla en pixeles.
     * @param y la posicion y de la pantalla en pixeles.
     */
    public void pintar(Graphics g, int x, int y) {

        //Resto algunos pixeles para alinear la imagen sobre el cruce de calles
        final int alineacionY = 30;
        final int alineacionX = 5;
        g.drawImage(this.imagen, x - alineacionX, y - alineacionY,
                    new Container());
    }

    /**
     * Metodo para implementar la interfaz.
     * Este metodo devuelve la fila en base a las coordenadas Y.
     *
     * @return La fila en que se encuentra el objeto.
     */
    public int getFila() {

        return this.posicion.getY();
    }

    /**
     * Metodo para implementar la interfaz.
     * Este metodo devuelve la fila en basea las coordenadas X.
     *
     * @return La columna en que se encuentra el objeto.
     */
    public int getColumna() {

        return this.posicion.getX();
    }

    /**
     * Lanza una excepcion.
     *
     * @param mensaje El mensaje de error.
     */
    protected void error(String mensaje) {
        throw new RuntimeException(mensaje);
    }

    /**
     * Método encargado de validar que el mundo no sea null.
     * @param mundo Mundo
     */
    private void validarMundo(Mundo mundo) {
        if (null == mundo) {
            error("Hay un problema con el mundo. Es null!");
        }
    }

    /*
     * Metodo encargado de levantar la imagen del cono por primera vez.
     */
    private void cargarImagenPrimeraVez() {

        //Obtengo la imagen
        try {
            imagen = ImageIO.read(this.getClass().getClassLoader().getResource(
                "imagenes" + File.separator + "cono.gif"));

        } catch (IOException ex) {
            error("Error al cargar la imagen del cono!");
        }
    }

    //Atributos privados
    private Vector2D posicion;
    private Image imagen;
    private Mundo mundo;
}
