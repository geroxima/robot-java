package uni.robot;

import java.awt.Graphics;

/**
 * Los objetos implementan esto para que puedan dibujarse en el mundo.
 *
 * @see uni.robot.Mundo
 */
interface IFigura {

    /**
     * Pinta el objeto en el mundo en las coordenadas de pantalla dadas.
     *
     * @param g el contexto grafico al que se tiene que pintar.
     * @param x la posicion x de la pantalla en pixeles.
     * @param y la posicion y de la pantalla en pixeles.
     */
    public void pintar(Graphics g, int x, int y);

    /**
     * Obtiene el numero de fila.
     *
     * @return el numero de fila.
     */
    public int getFila();

    /**
     * Obtiene el numero de columna.
     *
     * @return el numero de columna.
     */
    public int getColumna();
}
