package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Rey;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class FiguraBucle extends Figura {

    public FiguraBucle(String nom, boolean mia) {
        super(nom, mia);
    }

    public abstract HashSet<Posicion> detectarRutaJaque(Posicion posInicial, Posicion posRey, Casilla[][] arrayTablero);

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
    public boolean comprobarBucle(HashSet<Posicion> hsPosiblesMovimientos,
                                  Casilla[][] array, Posicion posicion,
                                  boolean direccion, boolean detectarJaqueMate) {
        // detectamos si la fila y la columna enviadas entran dentro del tablero
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
            // casilla sin ocupar
            if (array[fila][columna].getFigura() == null) {
                hsPosiblesMovimientos.add(posicion);
            } else if (array[fila][columna].getFigura().esMia() != esMia()) { // casilla ocupada por figura del rival
                hsPosiblesMovimientos.add(posicion);
                direccion = false;
            } else if (array[fila][columna].getFigura().esMia() == esMia()) { // casilla ocupada por nosotros
                if (detectarJaqueMate) {
                    hsPosiblesMovimientos.add(posicion);
                }
                direccion = false;
            }
        } else {
            direccion = false;
        }
        return direccion;
    }

    /**
     * Método auxiliar para terminar de determinar si se da jaque mate o no, ayudándonos a cubrir
     * casos como el "mate del pasillo".
     * @param figura la figura a comprobar si es rey
     * @param posicion la posición a comprobar si se puede añadir
     * @param hsPosiblesMovimientos el HashSet al que añadir la posición
     * @param arrayTablero el tablero en el momento actual.
     */
    public void comprobarJaqueMateAux(Figura figura, Posicion posicion,
                                      Casilla[][] arrayTablero,
                                      HashSet<Posicion> hsPosiblesMovimientos) {
        // comprobamos que la figura sea el rey que tiene que ser
        if (figura != null && figura instanceof Rey && figura.esMia() != esMia()) {
            int fila = posicion.getFila();
            int columna = posicion.getColumna();
            figura = arrayTablero[fila][columna].getFigura(); // figura de la posición que recibimos por parámetro
            if (figura == null || figura.esMia()) { // la añadimos si es null o nuestra
                hsPosiblesMovimientos.add(posicion);
            }
        }
    }
}
