package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Rey;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public HashMap<Posicion, ArrayList<Casilla>> depurarRutaJaque(Casilla casillaAlfil, Posicion posRey, Casilla[][] arrayTablero, HashMap<Posicion, ArrayList<Casilla>> hmJaqueMate) {
        Posicion posicion = casillaAlfil.getPosicion();
        int filaAlfil = posicion.getFila(), colAlfil = posicion.getColumna();
        int filaRey = posRey.getFila(), colRey = posRey.getColumna();
        int colAux = 0, filaAux = 0;

        if (filaRey > filaAlfil) {
            filaAux = 1;
        } else {
            filaAux =-1;
        }
        if (colRey > colAlfil) {
            colAux = 1;
        } else {
            colAux = -1;
        }

        // determinamos si es posible actualizar el hmJaqueMate, si entra dentro de las condiciones
        if (filaRey + filaAux <= 7 && filaRey + filaAux >= 0 && colRey + colAux <= 7 && colRey + colAux >= 0) {
            if (arrayTablero[filaRey + filaAux][colRey + colAux].getFigura() == null) { // está vacía
                ArrayList<Casilla> arrayCasillas = new ArrayList<>();
                arrayCasillas.add(casillaAlfil);
                // añadimos un nuevo registro a hmJaqueMate
                hmJaqueMate.put(new Posicion(filaRey + filaAux, colRey + colAux), arrayCasillas);
            }
        }
        return hmJaqueMate;
    }
}
