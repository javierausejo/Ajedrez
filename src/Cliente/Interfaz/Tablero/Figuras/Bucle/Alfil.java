package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Rey;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Alfil extends FiguraBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;

    public Alfil(boolean local) {
        super("ALFIL", local);
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array, boolean detectarJaqueMate) {
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
                NO = comprobarBucle(hsPosiblesMovimientos, array, p, NO, detectarJaqueMate);
            }
            // dirección NORESTE
            if (NE) {
                nFila = fila - i;
                nColumna = columna + i;
                p = new Posicion(nFila, nColumna);
                NE = comprobarBucle(hsPosiblesMovimientos, array, p, NE, detectarJaqueMate);
            }
            // dirección SUROESTE
            if (SO) {
                nFila = fila + i;
                nColumna = columna - i;
                p = new Posicion(nFila, nColumna);
                SO = comprobarBucle(hsPosiblesMovimientos, array, p, SO, detectarJaqueMate);
            }
            // dirección SURESTE
            if (SE) {
                nFila = fila + i;
                nColumna = columna + i;
                p = new Posicion(nFila, nColumna);
                SE = comprobarBucle(hsPosiblesMovimientos, array, p, SE, detectarJaqueMate);
            }
        }
        return hsPosiblesMovimientos;
    }

    @Override
    public HashSet<Posicion> detectarRutaJaque(Posicion pos, Casilla[][] arrayTablero) {
        HashSet<Posicion> hsRutaJaque = new HashSet<>();
        // detectamos posición del rey rival
        Posicion posRey = detectarPosicionReyRival(pos, arrayTablero);
        int fila = pos.getFila(), col = pos.getColumna();
        int filaAux, colAux;
        // establecemos parámetros para sacar la ruta
        if (posRey.getFila() > pos.getFila()) {
            filaAux = 1;
        } else {
            filaAux =-1;
        }
        if (posRey.getColumna() > pos.getColumna()) {
            colAux = 1;
        } else {
            colAux = -1;
        }
        // añadimos la posición actual
        hsRutaJaque.add(new Posicion(fila, col));
        Posicion posicion;
        boolean cumple = true;
        while (cumple) {
            fila += filaAux;
            col += colAux;
            posicion = new Posicion(fila, col);
            hsRutaJaque.add(posicion);
            if (fila == posRey.getFila() && col == posRey.getColumna()) {
                cumple = false;
            }
        }

        return hsRutaJaque;
    }
}
