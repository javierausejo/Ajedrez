package Cliente.Interfaz.Tablero.Figuras.NoBucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.FiguraNoBucle;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Caballo extends FiguraNoBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;

    public Caballo(boolean local) {
        super("CABALLO", local);
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array, boolean detectarJaqueMate) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        int nFila, nColumna;
        // una fila NORTE
        nFila = fila - 1;
        nColumna = columna - 2; // dos columnas OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        nColumna = columna + 2; //dos columnas ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // una fila SUR
        nFila = fila + 1;
        nColumna = columna - 2; // dos columnas OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        nColumna = columna + 2; // dos columnas ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dos filas NORTE
        nFila = fila - 2;
        nColumna = columna - 1; // una columna OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        nColumna = columna + 1; // una columnas ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dos filas SUR
        nFila = fila + 2;
        nColumna = columna - 1; // una columna OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        nColumna = columna + 1; // una columna ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        return hsPosiblesMovimientos;
    }
}
