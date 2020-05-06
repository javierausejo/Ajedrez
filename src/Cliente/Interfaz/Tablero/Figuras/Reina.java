package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Reina extends Figura {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;

    public Reina(boolean local) {
        super("REINA", local);
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        boolean N = true;
        boolean S = true;
        boolean O = true;
        boolean E = true;
        boolean NO = true;
        boolean NE = true;
        boolean SO = true;
        boolean SE = true;

        for (int i = 1; i < 8; i++) {
            // dirección norte
            if (N) {
                p = new Posicion(fila - i, columna);
                N = comprobarBucle(hsPosiblesMovimientos, array, p, N);
            }
            // dirección noroeste
            if (NO) {
                p = new Posicion(fila - i, columna - i);
                NO = comprobarBucle(hsPosiblesMovimientos, array, p, NO);
            }
            // dirección noreste
            if (NE) {
                p = new Posicion(fila - i, columna + i);
                NE = comprobarBucle(hsPosiblesMovimientos, array, p, NE);
            }

            if (S) {
                // dirección sur
                p = new Posicion(fila + i, columna);
                S = comprobarBucle(hsPosiblesMovimientos, array, p, S);
            }
            // dirección suroeste
            if (SO) {
                p = new Posicion(fila + i, columna - i);
                SO = comprobarBucle(hsPosiblesMovimientos, array, p, SO);
            }
            // dirección sureste
            if (SE) {
                p = new Posicion(fila + i, columna + i);
                SE = comprobarBucle(hsPosiblesMovimientos, array, p, SE);
            }

            if (O) {
                // dirección oeste
                p = new Posicion(fila, columna - i);
                O = comprobarBucle(hsPosiblesMovimientos, array, p, O);
            }

            // dirección este
            if (E) {
                p = new Posicion(fila, columna + i);
                E = comprobarBucle(hsPosiblesMovimientos, array, p, E);
            }
        }
        return hsPosiblesMovimientos;
    }
}
