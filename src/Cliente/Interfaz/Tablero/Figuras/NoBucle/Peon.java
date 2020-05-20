package Cliente.Interfaz.Tablero.Figuras.NoBucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.FiguraNoBucle;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Peon extends FiguraNoBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;
    private boolean primerMovimiento;

    public Peon(boolean local) {
        super("PEÓN", local);
        primerMovimiento = true;
    }

    public boolean esPrimerMovimiento() {
        return primerMovimiento;
    }

    public void setPrimerMovimiento(boolean primerMovimiento) {
        this.primerMovimiento = primerMovimiento;
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] arrayTablero, boolean detectarJaqueMate) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        int nFila, nColumna;
        Posicion p;

        if (!detectarJaqueMate) {
            // una fila NORTE
            if (esMia()) {
                nFila = fila + 1;
            } else {
                nFila = fila - 1;
            }
            nColumna = columna;
            p = new Posicion(nFila, nColumna);
            comprobar(hsPosiblesMovimientos, arrayTablero, p, false);

            // si se trata de primer movimiento
            if (esPrimerMovimiento() && !hsPosiblesMovimientos.isEmpty()) {
                // dos filas NORTE
                if (esMia()) {
                    nFila = fila + 2;
                } else {
                    nFila = fila - 2;
                }
                nColumna = columna;
                p = new Posicion(nFila, nColumna);
                comprobar(hsPosiblesMovimientos, arrayTablero, p, false);
            }
        }

        // comprobar si puede COMER
        if (esMia()) {
            nFila = fila + 1;
        } else {
            nFila = fila - 1;
        }
        nColumna = columna - 1;
        p = new Posicion(nFila, nColumna);
        comprobarComer(p, arrayTablero, detectarJaqueMate);
        nColumna = columna + 1;
        p = new Posicion(nFila, nColumna);
        comprobarComer(p, arrayTablero, detectarJaqueMate);

        return hsPosiblesMovimientos;
    }

    private void comprobarComer(Posicion posicion, Casilla[][] array, boolean detectarJaqueMate) {
        // detectamos si la fila y la columna enviadas entran dentro del tablero
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
            // detectamos si en dicha posición hay una figura y si es nuestra o no
            if (array[fila][columna].getFigura() != null) {
                if (array[fila][columna].getFigura().esMia() != esMia()) {
                    hsPosiblesMovimientos.add(posicion);
                } else {
                    if (detectarJaqueMate) {
                        hsPosiblesMovimientos.add(posicion);
                    }
                }
            } else {
                if (detectarJaqueMate) {
                    hsPosiblesMovimientos.add(posicion);
                }
            }
        }
    }
}
