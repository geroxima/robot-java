package uni.robot;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.*;

import uni.math.Vector2D;

/**
 * Esta es la representacion de una bolsa con cierta cantidad de conos
 * Estos conos podran ser llevados y/o podran ser depositados por el robot.
 * <p>
 * Como sabemos, nuestros robots tendran una capacidad limitada para
 * transportar conos.
 * <p>
 * De manera inteligente obtendremos gran utilidad de la bolsa de conos.
 *
 * @see uni.robot.Mundo
 * @see uni.robot.Cono
 */
public class RecipienteConos implements IFigura {

    /**
     * Constructor para el recipientes de conos.
     *
     * @param m El mundo donde dibujar el recipiente.
     * @param fila La fila donde ubicar el recipiente.
     * @param columna La fila donde ubicar el recipiente.
     * @param cantMax La cantidad maxima de conos que puede contener el recipiente.
     * @param cantInicial La cantidad inicial de conos en el recipiente.
     */
    public RecipienteConos(Mundo m, int fila, int columna, int cantMax,
                           int cantInicial) {

        this.validarMundo(m);
        this.cantInicialRecipiente = cantInicial;
        this.cantMaxRecipiente = cantMax;
        this.posicion = new Vector2D(columna, fila);
        this.mundo = m;
        this.cargarImagenPrimeraVez();
        this.mundo.verificarPosicionInicial(this); //verifico si puede ser creado en ese lugar del mundo.
        this.verificarCantInicialConos(cantMax, cantInicial); //Un simples checkeo
        this.mundo.agregar(this);

    }

    /**
     * Metodo encargado de resta la cantidad de conos en la bolsita del robot.
     */
    public void sacarCono() {
        if (cantInicialRecipiente > 0) {
            this.cantInicialRecipiente--;
        } else {
            error("No hay conos disponibles en el recipiente.");
        }
    }

    /**
     * Metodo encargado de sumar la cantidad de conos en la bolsita del robot.
     */
    public void ponerCono() {
        if (cantInicialRecipiente < cantMaxRecipiente) {
            this.cantInicialRecipiente++;
        } else {
            error("El recipiente esta lleno con " +
                  cantInicialRecipiente +
                  " conos."
                  );
        }
    }

    /**
     * Metodo necesario para implementar la interfaz
     * Este metodo se encarga de dibujar el objeto en el mundo.
     *
     * @param g El contexto grafico donde dibujar.
     * @param x La posicion en X donde dibujar.
     * @param y La posicion en Y donde dibujar.
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
     * Este metodo se encarga de devolver la fila del objeto.
     *
     * @return La fila donde esta ubicado el objeto.
     */
    public int getFila() {
        return this.posicion.getY();
    }

    /**
     * Metodo para implementar la interfaz.
     * Este metodo se encarga de devolver la columna del objeto.
     *
     * @return La columna donde esta ubicado el objeto.
     */
    public int getColumna() {

        return this.posicion.getX();
    }

    /**
     * Este metodo indica si el recipiente esta vacio.
     *
     * @return Devuelve true si el recipiente esta vacio, y false
     *         en caso contrario.
     */
    public boolean estaVacio() {
        if (cantInicialRecipiente == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo encargado de devolver la cantidad de conos en el recipiente.
     *
     * @return Cantidad de conos en el recipiente.
     */
    public int cantidadConos() {
        return cantInicialRecipiente;
    }

    /*
     * Metodo de ayuda encargado de cargar la imagen del recipiente a memoria
     *  por primera vez.
     */
    private void cargarImagenPrimeraVez() {

        try {
            //Obtengo la imagen
            imagen = ImageIO.read(this.getClass().getClassLoader().getResource(
                "imagenes" + File.separator + "bolsaConos.gif"));
        } catch (IOException ex) {
            error(
                "Error al cargar imagen. RecipienteConos no está bien instalado.");
        }
    }

    /*
     * Método encargado de verificar la cantidad inicial de conos en el recipiente.
     * @param cantMax cantidad máxima de conos.
     * @param cantInicial cantidad inicial de conos.
     */
    private void verificarCantInicialConos(int cantMax, int cantInicial) {

        if (cantInicial > cantMax) {
            error(
                "No se puede colocar mas conos que lo permitido por el recipiente!.");
        }
        if (cantInicial < 0) {
            error(
                "No se permite una cantidad negativa de conos en el recipiente!. ");
        }
    }

    /**
     * Lanza una excepcion.
     *
     * @param mensaje El mensaje de error.
     */
    private void error(String mensaje) {
        throw new RuntimeException(mensaje);
    }

    /*
     * Método encargado de validar que el mundo no sea null.
     * @param mundo Mundo
     */
    private void validarMundo(Mundo mundo) {
        if (null == mundo) {
            error("Hay un problema con el mundo. Es null!");
        }
    }

    //Atributos privados
    private Vector2D posicion;
    private Image imagen;
    private Mundo mundo;
    private int cantInicialRecipiente, cantMaxRecipiente;

}
