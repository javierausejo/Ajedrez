package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Bucle.FiguraBucle;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Reina extends FiguraBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;

    public Reina(boolean local) {
        super("REINA", local);
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array, boolean detectarJaqueMate) {
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
                N = comprobarBucle(hsPosiblesMovimientos, array, p, N, detectarJaqueMate);
            }
            // dirección noroeste
            if (NO) {
                p = new Posicion(fila - i, columna - i);
                NO = comprobarBucle(hsPosiblesMovimientos, array, p, NO, detectarJaqueMate);
            }
            // dirección noreste
            if (NE) {
                p = new Posicion(fila - i, columna + i);
                NE = comprobarBucle(hsPosiblesMovimientos, array, p, NE, detectarJaqueMate);
            }

            if (S) {
                // dirección sur
                p = new Posicion(fila + i, columna);
                S = comprobarBucle(hsPosiblesMovimientos, array, p, S, detectarJaqueMate);
            }
            // dirección suroeste
            if (SO) {
                p = new Posicion(fila + i, columna - i);
                SO = comprobarBucle(hsPosiblesMovimientos, array, p, SO, detectarJaqueMate);
            }
            // dirección sureste
            if (SE) {
                p = new Posicion(fila + i, columna + i);
                SE = comprobarBucle(hsPosiblesMovimientos, array, p, SE, detectarJaqueMate);
            }

            if (O) {
                // dirección oeste
                p = new Posicion(fila, columna - i);
                O = comprobarBucle(hsPosiblesMovimientos, array, p, O, detectarJaqueMate);
            }

            // dirección este
            if (E) {
                p = new Posicion(fila, columna + i);
                E = comprobarBucle(hsPosiblesMovimientos, array, p, E, detectarJaqueMate);
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
        } else if (posRey.getFila() == pos.getFila()) {
            filaAux = 0;
        } else {
            filaAux = -1;
        }
        if (posRey.getColumna() > pos.getColumna()) {
            colAux = 1;
        } else if (posRey.getColumna() == pos.getColumna()) {
            colAux = 0;
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

    public HashMap<Posicion, ArrayList<Casilla>> depurarRutaJaque(Casilla casillaReina, Posicion posRey, Casilla[][] arrayTablero, HashMap<Posicion, ArrayList<Casilla>> hmJaqueMate) {
        Posicion posicion = casillaReina.getPosicion();
        int filaReina = posicion.getFila(), colReina = posicion.getColumna();
        int filaRey = posRey.getFila(), colRey = posRey.getColumna();
        int colAux = 0, filaAux = 0;

        if (filaRey > filaReina) {
            filaAux = 1;
        } else if (filaRey < filaReina) {
            filaAux =-1;
        } else {
            filaAux = 0;
        }
        if (colRey > colReina) {
            colAux = 1;
        } else if (colRey < colReina) {
            colAux = -1;
        } else {
            colAux = 0;
        }

        // determinamos si es posible actualizar el hmJAqueMate, si entra dentro de las condiciones
        if (filaRey + filaAux <= 7 && filaRey + filaAux >= 0 && colRey + colAux <= 7 && colRey + colAux >= 0) {
            if (arrayTablero[filaRey + filaAux][colRey + colAux].getFigura() == null) { // está vacía
                ArrayList<Casilla> arrayCasillas = new ArrayList<>();
                arrayCasillas.add(casillaReina);
                // añadimos un nuevo registro a hmJaqueMate
                hmJaqueMate.put(new Posicion(filaRey + filaAux, colRey + colAux), arrayCasillas);
            }
        }
        return hmJaqueMate;
    }
}
