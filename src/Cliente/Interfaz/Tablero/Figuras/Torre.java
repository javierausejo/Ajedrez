package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Torre extends Figura {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;
    private boolean primerMovimiento;

    public Torre(boolean local) {
        super("TORRE", local);
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
        Posicion p;
        // variables para calcular movimientos
        boolean N = true;
        boolean S = true;
        boolean O = true;
        boolean E = true;
        for (int i = 1; i < 8; i++) {
            // dirección norte
            if (N) {
                p = new Posicion(fila + i, columna);
                N = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, N);
            }
            if (S) {
                // dirección sur
                p = new Posicion(fila - i, columna);
                S = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, S);
            }
            // dirección oeste
            if (O) {
                p = new Posicion(fila, columna - i);
                O = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, O);
            }
            if (E) {
                // dirección este
                p = new Posicion(fila, columna + i);
                E = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, E);
            }
        }
        return hsPosiblesMovimientos;
    }
}
