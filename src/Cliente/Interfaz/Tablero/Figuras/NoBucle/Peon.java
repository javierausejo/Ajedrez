package Cliente.Interfaz.Tablero.Figuras.NoBucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.FiguraNoBucle;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Peon extends FiguraNoBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;
    private boolean primerMovimiento;
    private boolean comerAlPaso;

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

    public boolean sePuedeComerAlPaso() {
        return comerAlPaso;
    }

    public void setComerAlPaso(boolean comerAlPaso) {
        this.comerAlPaso = comerAlPaso;
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

        // comprobar si puede COMER de modo tradicional
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

        // comprobar si puede COMER AL PASO
        comprobarComerAlPaso(posicion, arrayTablero);

        return hsPosiblesMovimientos;
    }

    private void comprobarComer(Posicion posicion, Casilla[][] array, boolean detectarJaqueMate) {
        int fila = posicion.getFila(), columna = posicion.getColumna();
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

    /**
     * Método que comprueba cuándo los peones pueden comer al paso.
     * Si es así, se añade la posición correspondiente al HashSet de posibles movimientos.
     */
    private void comprobarComerAlPaso(Posicion posicion, Casilla[][] arrayTablero) {
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Peon pAux;
        // comprobamos si las casillas correspondientes se pueden comer al paso
        if (columna - 1 >= 0) {
            if (arrayTablero[fila][columna - 1].getFigura() instanceof Peon) {
                pAux = (Peon) arrayTablero[fila][columna - 1].getFigura();
                if (pAux.sePuedeComerAlPaso()) {
                    hsPosiblesMovimientos.add(new Posicion(fila + 1, columna - 1));
                }
            }
        }
        if (columna + 1 <= 7) {
            if (arrayTablero[fila][columna + 1].getFigura() instanceof Peon) {
                pAux = (Peon) arrayTablero[fila][columna + 1].getFigura();
                if (pAux.sePuedeComerAlPaso()) {
                    hsPosiblesMovimientos.add(new Posicion(fila + 1, columna + 1));
                }
            }
        }
    }
}
