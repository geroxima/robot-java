package uni.math;

/**
 * Esta clase es la representación abstracta del vector matemático.
 * <p>
 * Esta en otro paquete porque no tiene relacion directa con el robot.
 */
public class Vector2D {

    /**
     * Constructor para vectores.
     *
     * @param x Dimension <b>x</b> del vector.
     * @param y Dimension <b>y</b> del vector.
     */
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Suma de vectores.
     *
     * @param otro Es el vector que se va a sumar.
     * @return Vector2D Retorna el nuevo vector resultado de la suma de vectores.
     */
    public Vector2D sumar(Vector2D otro) {
        return new Vector2D(this.x + otro.x, this.y + otro.y);
    }

    /**
     * Multiplicacion de x e y por un escalar.
     *
     * @param escalar Valor escalar a multiplicar (Multiplicacion de vectores).
     * @return Vector2D Retorna el nuevo vector resultado del producto de vectores.
     */
    public Vector2D multiplicar(int escalar) {
        return new Vector2D(this.x * escalar, this.y * escalar);
    }

    /**
     * Metodo encargado de devolver la columna correspondiente al vector.
     *
     * @return La columna del vector.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Metodo encargado de devolver la fila correspondiente al vector.
     *
     * @return La fila del vector.
     */
    public int getY() {
        return this.y;
    }

    //atributos privados.
    final private int x;
    final private int y;
}
