package uni.robot;

import java.util.ArrayList;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JEditorPane;

/**
 * Esta clase representa la abstraccion de un mundo real, y caracteriza el
 * ambiente de convivencia y trabajo de los robot.
 * <p>
 * El mundo es plano, y esta conformado por calles y avenidas(filas y columnas),
 * rodeado de paredes de NEUTRONIUN impenetrables.
 * <p>
 * Ademas tiene un area de texto donde se pueden escribir frases, y cambiar los
 * colores de las letras.
 *
 **/
public class MundoConTexto extends Mundo {

    /**
     * Constructor
     * @param texto El texto a usarse en el area de texto.
     * @param tituloMundo String Frase utilizada en la barra de titulo de la ventana
     * @param cantFilas int La cantidad de filas del mundo.
     * @param cantColumnas int La cantidad de columnas del mundo.
     */
    public MundoConTexto(String texto, String tituloMundo, int cantFilas,
                         int cantColumnas) {
        super(tituloMundo, cantFilas, cantColumnas);
        this.texto = texto;
        setTexto(this.texto);

    }

    /**
     * Cambia el color de la letra en la posicion especificada de la frase.
     *
     * @param c Color El nuevo color de la letra.
     * @param indice int La posición de la letra.
     */
    /**
     * @param c * @todo utilizar generics en lugar de evitar el warning
     * @param indice*/
    @SuppressWarnings("unchecked")
    public void setColor(Color c, int indice) {

        //Valido el indice
        if (indice < 0 | indice > texto.length()) {
            throw new RuntimeException(
                "El número está fuera de rango!\nMayor que el tamaño de la palabra, o negativo");
        }

        //Actualizo el color
        this.arrayColores.set(indice, c);

        //reescribo la frase
        textArea.setText(armarTextoHtml());

    }

    /**
     * Retorna el texto contenido en el area de texto
     *
     * @return String texto contenido en el area de texto
     */
    public String getText() {
        return this.texto;
    }

    /**
     * Sobrescribo el metodo de la clase superior para poder agregar el canvas
     * nuevo.
     */
    protected void configurarVentana() {
        super.configurarVentana();

        this.textArea = new JEditorPane("text/html", "") {
            //Pequenho hacking para paliar el flicker del area de texto.
            public void repaint() {}

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                super.repaint();
            }

        };

        //Por un problema de superposicion de contructores inicializo estos
        //valores fuera del constructor.
        this.textArea = new JEditorPane("text/html", "");
        this.textArea.setBackground(Color.WHITE);
        this.canvas.setBackground(Color.WHITE);
        this.textArea.setEditable(false);
        this.texto = "";

        this.arrayColores = new ArrayList();

        //Cambio el orden de los paneles, primero StyleTextArea y despues canvas
        this.remove(this.canvas);

        //Se añade un borde.
        this.textArea.setBorder(BorderFactory.createEmptyBorder(1, 10, 1, 10));
        textArea.setDoubleBuffered(true);

        //Manejo de layout (En este caso usamos GridBagLayout)
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gb);
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        this.textArea.setMinimumSize(new Dimension(baseMundo, 10));
        gb.setConstraints(this.textArea, c);
        add(this.textArea);

        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.SOUTHWEST;
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        gb.setConstraints(this.canvas, c);
        add(this.canvas);

    }

    /*
     * Cambia el texto en el area de texto.
     *
     * @param texto String El nuevo texto.
     */
    private void setTexto(String texto) {

        //cargo el arreglo de colores
        this.arrayColores = coloresPorDefecto(this.arrayColores);

        textArea.setText(armarTextoHtml());
        pack();
        setSize(Math.max(textArea.getWidth(), baseMundo),
                alturaMundo + textArea.getHeight());
        alinearEnPantalla();

    }

    /*
     * Convierte colores a formato hexadecimal necesario para HTML.
     *
     * @param color Color Un objeto color de la clase Color (java.awt.color)
     * @return String el color en formato hexadecimal (listo para usar en HTML)
     */
    private String colorAHexadecimal(Color color) {
        return "#" + toHexString(color.getRed()) +
            toHexString(color.getGreen()) +
            toHexString(color.getBlue());
    }

    /*
     * Sobrescribe el metodo toHexString.
     * Completa los colores. Cuando un color es 0 se concatena a 00.
     *
     * @param color int
     * @return String
     */
    private String toHexString(int color) {
        String s = Integer.toHexString(color);
        if (s.length() < 2) {
            s = '0' + s;
        }
        return s;
    }

    /*
     * Inicializo todas las posiciones del arreglo con el color por defecto: Negro.
     *
     * @param arrayColores ArrayList El arreglo donde cargar todos los colores.
     */
    /**@todo utilizar generics en lugar de evitar el warning*/
    @SuppressWarnings("unchecked")
    private ArrayList coloresPorDefecto(ArrayList arrayColores) {
        for (int i = 0; i < texto.length(); i++) {
            arrayColores.add(new Color(0, 0, 0));
        }
        return arrayColores;
    }

    /*
     * Metodo encargado de armar el html encargado de los colores en el area de texto.
     * Vease que el area de texto en realidad es un JLabel, y este tipo de componente
     * soporta HTML.
     *
     * @return String El string completo con el codigo html y los colores incluidos.
     */
    private String armarTextoHtml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<html><p>");
        Color viejo = null;
        String cerrarFont = "";
        for (int i = 0; i < this.texto.length(); i++) {
            Color nuevo = (Color)this.arrayColores.get(i);
            if (nuevo.equals(viejo)) {
                sb.append(this.texto.charAt(i));
            } else {
                sb.append(cerrarFont + "<font color='" +
                          colorAHexadecimal(nuevo) +
                          "'>" + this.texto.charAt(i));
                cerrarFont = "</font>";
            }
            viejo = nuevo;
        }
        sb.append(cerrarFont + "<p></html>");
        return sb.toString();

    }

    //Atributos privados
    private JEditorPane textArea;
    private String texto;
    private ArrayList arrayColores;
}
