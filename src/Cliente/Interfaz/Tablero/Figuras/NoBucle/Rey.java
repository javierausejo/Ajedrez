package Cliente.Interfaz.Tablero.Figuras.NoBucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.FiguraNoBucle;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Rey extends FiguraNoBucle {

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
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array, boolean detectarJaqueMate) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        // dirección norte
        p = new Posicion(fila - 1, columna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección noroeste
        p = new Posicion(fila - 1, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección noreste
        p = new Posicion(fila - 1, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dirección sur
        p = new Posicion(fila + 1, columna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección suroeste
        p = new Posicion(fila + 1, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección sureste
        p = new Posicion(fila + 1, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dirección oeste
        p = new Posicion(fila, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dirección este
        p = new Posicion(fila, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        return hsPosiblesMovimientos;
    }
}
