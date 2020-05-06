package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Rey extends Figura {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;
    private boolean primerMovimiento;

    public Rey(boolean local) {
        super("REY", local);
        primerMovimiento = true;
    }

    public boolean esPrimerMovimiento() {
        return primerMovimiento;
    }

    public void setPrimerMovimiento(boolean primerMovimiento) {
        this.primerMovimiento = primerMovimiento;
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        // dirección norte
        p = new Posicion(fila - 1, columna);
        comprobar(hsPosiblesMovimientos, array, p);
        // dirección noroeste
        p = new Posicion(fila - 1, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p);
        // dirección noreste
        p = new Posicion(fila - 1, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p);

        // dirección sur
        p = new Posicion(fila + 1, columna);
        comprobar(hsPosiblesMovimientos, array, p);
        // dirección suroeste
        p = new Posicion(fila + 1, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p);
        // dirección sureste
        p = new Posicion(fila + 1, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p);

        // dirección oeste
        p = new Posicion(fila, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p);

        // dirección este
        p = new Posicion(fila, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p);

        return hsPosiblesMovimientos;
    }
}
