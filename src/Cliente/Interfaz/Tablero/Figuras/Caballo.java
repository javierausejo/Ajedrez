package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Caballo extends Figura {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;

    public Caballo(boolean local) {
        super("CABALLO", local);
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        int nFila, nColumna;
        // una fila NORTE
        nFila = fila - 1;
        nColumna = columna - 2; // dos columnas OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);
        nColumna = columna + 2; //dos columnas ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);

        // una fila SUR
        nFila = fila + 1;
        nColumna = columna - 2; // dos columnas OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);
        nColumna = columna + 2; // dos columnas ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);

        // dos filas NORTE
        nFila = fila - 2;
        nColumna = columna - 1; // una columna OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);
        nColumna = columna + 1; // una columnas ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);

        // dos filas SUR
        nFila = fila + 2;
        nColumna = columna - 1; // una columna OESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);
        nColumna = columna + 1; // una columna ESTE
        p = new Posicion(nFila, nColumna);
        comprobar(hsPosiblesMovimientos, array, p);

        return hsPosiblesMovimientos;
    }
}
