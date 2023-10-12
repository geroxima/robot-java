package uni.robot;

import java.util.ArrayList;
import java.util.List;

import java.awt.*;

import uni.math.Vector2D;

/**
 * Esta clase representa el area de trabajo en el mundo.
 * Es utilizado en la clase Mundo para dibujar las imagenes,
 * en vez de dibujar directamente sobre la ventana.
 * 
 *
 * @see uni.robot.Mundo
 **/

class MundoCanvas extends Canvas {

    public MundoCanvas(int cantFilas, int cantColumnas, int baseMundo,
                       int alturaMundo, boolean tablero) {
        this.cantFilas = cantFilas;
        this.cantColumnas = cantColumnas;
        this.baseMundo = baseMundo;
        this.alturaMundo = alturaMundo;
        this.setBackground(Color.WHITE);
        //doy tamaño al canvas
        this.setSize(baseMundo, alturaMundo);
        this.listaFiguras = new ArrayList();
        this.fondoTablero = tablero; //indica si dibuja tablero o cuadricula en el mundo
    }

    /**
     * El método paint es utilizado para dibujar las figuras en el mundo
     * Recibimos un contexto gráfico (g) y en el podemos dibujar cualquier cosa.
     * <p>
     * Para el movimiento se utiliza la técnica de doble buffer.
     *
     * @param g El contexto grafico donde dibujar.
     */
    public void paint(Graphics g) {
        paint(g, null, null);
    }

    /**
     * Agrega una figura al canvas.
     *
     * @param f IFigura a agregar.
     */
    /**@todo utilizar generics en lugar de evitar el warning*/
    @SuppressWarnings("unchecked")
    public void agregar(IFigura f) {
        listaFiguras.add(f);
        this.repaint();
    }

    /**
     * Remueve una figura del canvas.
     *
     * @param figura IFigura a remover.
     */
    public void remover(IFigura figura) {
        if (!this.listaFiguras.remove(figura)) {
            error("No se puede remover la figura " + figura);
        }
    }

    /**
     * Este metodo es necesario para eliminar el flicker de la ventana
     * (para que funcione correctamente el metodo paint).
     * <p>
     * OBS: El metodo paint llama a repaint, repaint llama a update, y update
     * lanza un hilo nuevo con paint. El hilo es manejado por el Sistema Operativo.
     *
     * @param g El gontexto grafico donde se debe dibujar.
     */
    public void update(Graphics g) {
        paint(g);
    }

    /*
     * Devuelve una lista de IFiguras que hay en dicha posicion.
     * <p>
     * Necesario para el caso donde hay mas de un cono, robot, o figura
     * en la misma posicion.
     *
     * @param fila La fila en que se desea obtener los objetos.
     * @param columna La columna en que se desea obtener los objetos.
     * @return Devuelve una lista con todos los objetos ubicados en dicha posicion.
     */
    /**@todo utilizar generics en lugar de evitar el warning*/
    @SuppressWarnings("unchecked")
    List figurasEnPosicion(int fila, int columna) {

        ArrayList lista = new ArrayList();

        for (int z = 0; z < listaFiguras.size(); z++) {

            IFigura f = (IFigura) listaFiguras.get(z);

            if (fila == f.getFila() && columna == f.getColumna()) {
                lista.add(f);
            }
        }

        return lista;
    }

    /*
     * Metodo encargado del movimientos del robot.
     *
     * @param f El robot.
     * @param posicionVieja Posicion actual del robot.
     * @param direccion Direccion a la que apunta el robot.
     */
    void mover(IFigura f, Vector2D posicionVieja, Vector2D direccion) {

        //Creo un Vector2D con los datos en X e Y pero en pixeles para la pantalla
        Vector2D coordenadas = new Vector2D(columnaAPixels(posicionVieja.getX()),
                                            filaAPixels(posicionVieja.getY()));

        /* Esta delta representa el incremento en las coordenadas que tendra
         * el robot, usando un escalar como factor de multiplicacion.
         */
        Vector2D delta = direccion.multiplicar(VELOCIDAD);

        //Muevo en las coordenadas y direccion dadas
        for (int i = 0; i < this.DISTANCIA; i += VELOCIDAD) {
            coordenadas = coordenadas.sumar(delta);

            paint(this.getGraphics(), f, coordenadas);
        }
    }

    /**
     * Método encargado de verificar que la dirección del robot sea válida.
     *
     * @param r Robot cuya dirección se va a verificar.
     */
    void verificarDireccionInicial(Robot r) {
        if (r.getDireccion() != Robot.NORTE &&
            r.getDireccion() != Robot.OESTE &&
            r.getDireccion() != Robot.SUR &&
            r.getDireccion() != Robot.ESTE) {
            error("La direccion: " + r.getDireccion() + " no es valida!");
        }
    }

    /**
     * Método encargado de verificar que la posición del robot sea válida.
     *
     * @param figura Figura cuya posición se va a verificar.
     */
    void verificarPosicionInicial(IFigura figura) {

        //si la columna inicial del robot es mayor que la cantidad de columnas en el mundo
        if (figura.getColumna() >= this.cantColumnas) {
            error(
                "La columna del objeto excede la cant. de columnas del mundo.");
        }

        if (figura.getFila() >= this.cantFilas) {
            error("La fila del objeto excede a la cant. de filas del mundo.");
        }

        //Verifico los border derecho y superior del mundo.
        if (figura.getFila() < 0 | figura.getColumna() < 0) {
            error("No se puede ubicar al objeto en una posicion negativa.");
        }

    }

    /*
     * Lanza una excepcion.
     *
     * @param mensaje El mensaje de error.
     */
    private void error(String mensaje) {
        throw new RuntimeException(mensaje);
    }

    /*
     * Metodo encargado de transformar las filas en coordenadas Y para representar en pantalla
     *
     * @param fila La fila que se debe transformar en coordenadas Y
     * @return Retorna en pixeles la coordenada Y de la fila
     */
    private int filaAPixels(int fila) {
        int offsetY = 0;
        if (this.fondoTablero) {
            offsetY = DISTANCIA / 2;
        }

        return ESPACIO_SUPERIOR + ESPACIO_NUMEROS + (fila * DISTANCIA) +
            offsetY;
    }

    /*
     * Metodo encargado de transformar las columnas en coordenadas X para representar en pantalla
     *
     * @param columna La columna que se debe transformar en coordenadas X
     * @return Retorna en pixeles la coordenada X de la fila
     */
    private int columnaAPixels(int columna) {
        int offsetX = 0;
        if (this.fondoTablero) {
            offsetX = DISTANCIA / 2;
        }

        return ESPACIO_NUMEROS + (columna * DISTANCIA) + offsetX;

    }

    /*
     * Este metodo verifica si el robot toca el limite del mundo.
     * <p>
     * En ese caso muestra el dialogo y termina el programa.
     *
     * @param coordFigura Coordenadas vectoriales de la figura.
     */
    private void estaSobreLimite(Vector2D coordFigura) {

        if (coordFigura.getX() > columnaAPixels(this.cantColumnas - 1)) {

            error("choque pared Este.");
        }

        if (coordFigura.getX() < columnaAPixels(0)) {
            error("choque pared Oeste.");
        }

        if (coordFigura.getY() < filaAPixels(0)) {
            error("choque pared Norte.");
        }

        if (coordFigura.getY() > filaAPixels(this.cantFilas - 1)) {
            error("choque pared Sur.");
        }
    }

    /*
     * Esta indireccion fue necesaria como paliativo para un problema de null
     * en la carga de las imagenes. Esta complejidad no es totalmente necesaria
     * pero lo que se trato de hacer fue una optimizacion de rendimiento.
     *
     * @param g El contexto grafico donde dibujar.
     * @param figuraCambiada La figura que desea dibujar.
     * @param coordFigura un objeto Vector2D con las coordenadas del robot.
     */
    private void paint(Graphics g, IFigura figuraCambiada,
                       Vector2D coordFigura) {

        /* Creo un espacion en memoria (buffer) para una imagen y
         * obtengo su contexto grafico (gContext).
         * En este ultimo dibujo todas las calles y avenidas.
         * Creo un segundo espacion en memoria (buffer2) y
         * obtengo su contexto grafico (gContext2).
         * En gContext2 dibujo la imagen completa (buffer).
         * En gContext2 agrego todos los objetos (Robots, conos, etc).
         * Y finalmente dibujo en el mundo el buffer2.
         *
         */
        /**@todo seperar el doble buffer en un metodo que diseñe el fondo*/
        if (null == buffer || null == buffer2 || null == gContext ||
            null == gContext2) {

            buffer = createImage(this.baseMundo, this.alturaMundo); //crea una imagen Off-Screen

            gContext = buffer.getGraphics();
            gContext.setColor(Color.black);

            /*
             * El fondo del mundo puede ser un tablero o calles y avenidas
             */
            if (fondoTablero) {

                int num = 0;

                //Dibujo el tablero
                for (int fila = 0; fila < this.cantFilas; fila++) {

                    //Inicializo el valor de y
                    int y = ESPACIO_NUMEROS +
                        (fila * this.DISTANCIA);

                    //Dibuja columna partiendo de blanco
                    for (int col = 0; col < this.cantColumnas; col++) {

                        int x = ESPACIO_NUMEROS + col * this.DISTANCIA + 5;

                        //Determino el color de cada columna
                        if ((col % 2) == 0) {
                            gContext.setColor(Color.WHITE);

                        } else {
                            gContext.setColor(Color.LIGHT_GRAY);

                        }
                        gContext.fillRect(x, y, this.DISTANCIA,
                                          this.DISTANCIA);

                    }

                    fila++;
                    //incremento y
                    y = ESPACIO_NUMEROS +
                        (fila * this.DISTANCIA);

                    //dibuja columna partiendo de negro
                    for (int col = 0; col < this.cantColumnas; col++) {

                        int x = ESPACIO_NUMEROS + col * this.DISTANCIA + 5;

                        //Determino el color de cada columna
                        if ((col % 2) != 0) {
                            gContext.setColor(Color.WHITE);

                        } else {
                            gContext.setColor(Color.LIGHT_GRAY);
                        }

                        gContext.fillRect(x, y, this.DISTANCIA,
                                          this.DISTANCIA);

                    }

                }

                gContext.setColor(Color.BLACK);
                int alturaFont = gContext.getFontMetrics().getHeight();

                // la serie de letras horizontales
                String[] abecedario = {
                    "", "A", "B", "C", "D", "E", "F", "G", "H"};

                for (int i = 1; i <= this.cantColumnas; i++) {
                    String str = abecedario[i];
                    gContext.drawString(str,
                                        ESPACIO_NUMEROS + i * this.DISTANCIA -
                                        (this.DISTANCIA / 2),
                                        alturaFont);

                }

                int contador = 8;
                //Dibujamos la serie de numeros verticales
                for (int i = 1; i <= this.cantFilas; i++) {
                    String str = contador + "";
                    gContext.drawString(str,
                                        ESPACIO_NUMEROS -
                                        gContext.
                                        getFontMetrics().stringWidth(str),
                                        ESPACIO_NUMEROS + (alturaFont / 2) +
                                        i * this.DISTANCIA -
                                        (this.DISTANCIA / 2));

                    contador--; //Se utiliza para el nro en la pantalla.
                }

            } else {

                // lineas verticales(avenidas)
                for (int col = 0; col < this.cantColumnas; col++) {
                    int x = ESPACIO_NUMEROS + col * this.DISTANCIA + 5;
                    gContext.drawLine(x, ESPACIO_NUMEROS, x,
                                      (ESPACIO_SUPERIOR +
                                       ((this.cantFilas - 1) * this.DISTANCIA)));
                }

                //dibujamos las lineas horizontales(calles)
                for (int fila = 0; fila < this.cantFilas; fila++) {
                    int y = ESPACIO_NUMEROS +
                        (fila * this.DISTANCIA);
                    gContext.drawLine(ESPACIO_NUMEROS + 5, y,
                                      ESPACIO_NUMEROS + 5 +
                                      this.DISTANCIA * (this.cantColumnas - 1),
                                      y);
                }

                int alturaFont = gContext.getFontMetrics().getHeight();

                // la serie de numeros horizontales
                for (int i = 0; i < this.cantColumnas; i++) {
                    String str = i + "";
                    gContext.drawString(str,
                                        ESPACIO_NUMEROS -
                                        gContext.getFontMetrics().stringWidth(
                        str) /
                                        2 + i * this.DISTANCIA,
                                        alturaFont);
                }

                //Dibujamos la serie de numeros verticales
                for (int i = 0; i < this.cantFilas; i++) {
                    String str = i + "";
                    gContext.drawString(str,
                                        ESPACIO_NUMEROS -
                                        gContext.getFontMetrics().stringWidth(
                        str),
                                        ESPACIO_NUMEROS +
                                        (alturaFont / 2) + i * this.DISTANCIA);
                }
            }
            //el doble buffer
            buffer2 = createImage(this.baseMundo, this.alturaMundo);
            gContext2 = buffer2.getGraphics();
        }

        gContext2.setPaintMode();
        gContext2.drawImage(buffer, 0, 0, this);

        //Pinto todas las Ifiguras que esten en el arrayList.
        for (int z = 0; z < listaFiguras.size(); z++) {

            IFigura f = (IFigura) listaFiguras.get(z);
            if (null != coordFigura) {
                estaSobreLimite(coordFigura);
            }
            //Aqui decide si pintar la imagen con coordenadas cartesianas o en fila y columnas.
            if (f == figuraCambiada) {
                f.pintar(gContext2, coordFigura.getX(), coordFigura.getY());
            } else {
                f.pintar(gContext2, columnaAPixels(f.getColumna()),
                         filaAPixels(f.getFila()));
            }
        }

        //Finalmente dibujamos en el mundo el contenido del buffer2 terminado.
        g.drawImage(this.buffer2, 0, 0, this);
    }

    //atributos privados
    private Image buffer;
    private Image buffer2;
    private Graphics gContext;
    private Graphics gContext2;
    private int cantFilas;
    private int cantColumnas;
    private ArrayList listaFiguras;
    private int baseMundo;
    private int alturaMundo;
    private static final int DISTANCIA = Mundo.DISTANCIA;
    private static final int VELOCIDAD = Mundo.VELOCIDAD;
    private static final int ESPACIO_SUPERIOR = Mundo.ESPACIO_SUPERIOR;
    private static final int ESPACIO_NUMEROS = Mundo.ESPACIO_NUMEROS;
    private boolean fondoTablero;

}
