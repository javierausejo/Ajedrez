package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Peon extends Figura {

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
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] arrayTablero) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        int nFila, nColumna;
        Posicion p;

        // una fila NORTE
        if (esMia()) {
            nFila = fila + 1;
        } else {
            nFila = fila - 1;
        }
        nColumna = columna;
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, arrayTablero, p);

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
            comprobar(hsPosiblesMovimientos, arrayTablero, p);
        }

        // comprobar si puede COMER
        if (esMia()) {
            nFila = fila + 1;
        } else {
            nFila = fila - 1;
        }
        nColumna = columna - 1;
        p = new Posicion(nFila, nColumna);
        comprobarComer(p, arrayTablero);
        nColumna = columna + 1;
        p = new Posicion(nFila, nColumna);
        comprobarComer(p, arrayTablero);

        return hsPosiblesMovimientos;
    }

    private void comprobarComer(Posicion posicion, Casilla[][] array) {
        // detectamos si la fila y la columna enviadas entran dentro del tablero
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
            // detectamos si en dicha posición hay una figura y si es nuestra o no
            if (array[fila][columna].getFigura() != null && (array[fila][columna].getFigura().esMia() != esMia())) {
                hsPosiblesMovimientos.add(posicion);
            }
        }
    }
}
