package Cliente.Interfaz.Tablero;

import Cliente.Interfaz.Tablero.Figuras.Figura;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.Serializable;

public class Casilla implements Serializable {

    // VARIABLES DE INSTANCIA
    private transient JButton btnCasilla;
    private Posicion posicion;
    private Figura figura;

    public Casilla(JButton btnCasilla, Posicion posicion) {
        this.btnCasilla = btnCasilla;
        this.posicion = posicion;
        figura = null;
    }

    public JButton getBtnCasilla() {
        return btnCasilla;
    }

    public Figura getFigura() {
        return figura;
    }

    public void setFigura(Figura figura) {
        this.figura = figura;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    /**
     * Método que colorea el borde de la casilla.
     */
    public void colorearBorde() {
        btnCasilla.setBorder(new LineBorder(Color.RED, 4));
    }

    /**
     * Método que descolorea el borde de la casilla, devolviéndolo a su estado natural.
     */
    public void descolorearBorde() {
        btnCasilla.setBorder(new LineBorder(Color.GRAY, 1));
    }

    public void colorearSeleccion() {
        btnCasilla.setBorder(new LineBorder(Color.GREEN, 4));
    }
}
