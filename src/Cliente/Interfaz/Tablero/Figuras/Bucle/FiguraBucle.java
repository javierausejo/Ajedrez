package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Rey;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public abstract class FiguraBucle extends Figura {

    public FiguraBucle(String nom, boolean mia) {
        super(nom, mia);
    }

    public abstract HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] arrayTablero, boolean detectarJaqueMate);

    public abstract HashSet<Posicion> detectarRutaJaque(Posicion pos, Casilla[][] arrayTablero);

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
    public boolean comprobarBucle(HashSet<Posicion> hsPosiblesMovimientos, Casilla[][] array, Posicion posicion, boolean direccion, boolean detectarJaqueMate) {
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

    public Posicion detectarPosicionReyRival(Posicion pos, Casilla[][] arrayTablero) {
        HashSet<Posicion> hsPosiblesMovimientos = getPosiblesMovimientos(pos, arrayTablero, false);
        Posicion posRey = null;
        // detectamos posición del rey rival
        for (Posicion p : hsPosiblesMovimientos) {
            if (arrayTablero[p.getFila()][p.getColumna()].getFigura() instanceof Rey
                    && arrayTablero[p.getFila()][p.getColumna()].getFigura().esMia() != esMia()) {
                posRey = p;
            }
        }
        return posRey;
    }
}
