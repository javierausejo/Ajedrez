package Cliente.Interfaz.Tablero.Figuras.NoBucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public abstract class FiguraNoBucle extends Figura {

    public FiguraNoBucle(String nom, boolean mia) {
        super(nom, mia);
    }

    /**
     * Método para comprobar si es posible un movimiento en piezas cuya lógica
     * de movimiento NO responde a bucles: caballo, rey y peón.
     * en su clase.
     *
     * @param hsPosiblesMovimientos indica los posibles movimientos de la figura.
     * @param posicion              posición a comprobar si se puede añadir
     */
    public void comprobar(HashSet<Posicion> hsPosiblesMovimientos,
                          Casilla[][] array, Posicion posicion, boolean detectarJaqueMate) {
        // detectamos si la fila y la columna enviadas entran dentro del tablero
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
            if (this instanceof Peon) { // si se trata de un peón, el comportamiento varía
                if (array[fila][columna].getFigura() == null) {
                    hsPosiblesMovimientos.add(posicion);
                }
            } else {
                if (array[fila][columna].getFigura() != null) {
                    if (array[fila][columna].getFigura().esMia() != esMia()) {
                        hsPosiblesMovimientos.add(posicion);
                    } else {
                        if (detectarJaqueMate) {
                            hsPosiblesMovimientos.add(posicion);
                        }
                    }
                } else {
                    hsPosiblesMovimientos.add(posicion);
                }
            }
        }
    }
}
