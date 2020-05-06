package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Alfil extends Figura {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;

    public Alfil(boolean local) {
        super("ALFIL", local);
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        // variables para calcular movimientos
        boolean NO = true;
        boolean NE = true;
        boolean SO = true;
        boolean SE = true;
        int nFila, nColumna;
        for (int i = 1; i < 8; i++) {
            // dirección NOROESTE
            if (NO) {
                nFila = fila - i;
                nColumna = columna - i;
                p = new Posicion(nFila, nColumna);
                NO = comprobarBucle(hsPosiblesMovimientos, array, p, NO);
            }
            // dirección NORESTE
            if (NE) {
                nFila = fila - i;
                nColumna = columna + i;
                p = new Posicion(nFila, nColumna);
                NE = comprobarBucle(hsPosiblesMovimientos, array, p, NE);
            }
            // dirección SUROESTE
            if (SO) {
                nFila = fila + i;
                nColumna = columna - i;
                p = new Posicion(nFila, nColumna);
                SO = comprobarBucle(hsPosiblesMovimientos, array, p, SO);
            }
            // dirección SURESTE
            if (SE) {
                nFila = fila + i;
                nColumna = columna + i;
                p = new Posicion(nFila, nColumna);
                SE = comprobarBucle(hsPosiblesMovimientos, array, p, SE);
            }
        }
        return hsPosiblesMovimientos;
    }
}
