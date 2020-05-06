package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.io.Serializable;
import java.util.HashSet;

public abstract class Figura implements Serializable {

    // VARIABLES DE INSTANCIA
    private String nom;
    private boolean mia;

    public Figura(String nom, boolean mia) {
        this.nom = nom;
        this.mia = mia;
    }

    public String getNom() {
        return nom;
    }

    public boolean esMia() {
        return mia;
    }

    /**
     * Método abstracto que devuelve un hashset de instancias Posicion.java con los posibles movimientos que puede
     * hacer una figura en una posición determinada del tablero.
     *
     * @param posicion     la posición actual de la figura
     * @param arrayTablero el estado del tablero
     * @return hashset de instancias Posicion.java
     */
    public abstract HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] arrayTablero);

    /**
     * Método para comprobar si es posible un movimiento en piezas cuya lógica
     * de movimiento NO responde a bucles: caballo y rey.
     * Peón tiene un funcionamiento interno propio y diferente a este definido
     * en su clase.
     *
     * @param hsPosiblesMovimientos indica los posibles movimientos de la figura.
     * @param posicion              posición a comprobar si se puede añadir
     */
    public void comprobar(HashSet<Posicion> hsPosiblesMovimientos, Casilla[][] array, Posicion posicion) {
        // detectamos si la fila y la columna enviadas entran dentro del tablero
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
            if (this instanceof Peon) { // si se trata de un peón, el comportamiento varía
                if (array[fila][columna].getFigura() == null) {
                    hsPosiblesMovimientos.add(posicion);
                }
            } else {
                if (array[fila][columna].getFigura() == null || (array[fila][columna].getFigura().esMia() != esMia())) {
                    hsPosiblesMovimientos.add(posicion);
                }
            }
        }
    }

    /**
     * Método para comprobar si es posible un movimiento en piezas cuya lógica
     * de movimiento responda a bucles: torre, alfil y reina.
     *
     * @param hsPosiblesMovimientos indica los posibles movimientos de la figura.
     * @param array                 representa las casillas del tablero en el momento actual de juego
     * @param posicion              posición a comprobar si se puede añadir
     * @param direccion             representa las coordenadas de la figura: norte, sur, este, oeste...
     * @return
     */
    public boolean comprobarBucle(HashSet<Posicion> hsPosiblesMovimientos, Casilla[][] array, Posicion posicion, boolean direccion) {
        // detectamos si la fila y la columna enviadas entran dentro del tablero
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
            // casilla sin ocupar
            if (array[fila][columna].getFigura() == null) {
                hsPosiblesMovimientos.add(posicion);
            } else if (!array[fila][columna].getFigura().esMia() == esMia()) { // casilla ocupada por figura del rival
                hsPosiblesMovimientos.add(posicion);
                direccion = false;
            } else if (array[fila][columna].getFigura().esMia() == esMia()) { // casilla ocupada por nosotros
                direccion = false;
            }
        } else {
            direccion = false;
        }
        return direccion;
    }
}
