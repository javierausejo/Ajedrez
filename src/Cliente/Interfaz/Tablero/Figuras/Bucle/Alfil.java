package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
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
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] arrayTablero, boolean detectarJaqueMate) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        Figura figura;
        // variables para calcular movimientos
        boolean NO = true;
        boolean NE = true;
        boolean SO = true;
        boolean SE = true;
        int fAux, cAux;
        for (int i = 1; i < 8; i++) {
            // dirección NOROESTE
            if (NO) {
                fAux = fila - i;
                cAux = columna - i;
                p = new Posicion(fAux, cAux);
                NO = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, NO, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!NO && detectarJaqueMate && fAux > 0 && cAux > 0) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux -= 1;
                    cAux -= 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
            // dirección NORESTE
            if (NE) {
                fAux = fila - i;
                cAux = columna + i;
                p = new Posicion(fAux, cAux);
                NE = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, NE, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!NE && detectarJaqueMate && fAux > 0 && cAux < 7) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux -= 1;
                    cAux += 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
            // dirección SUROESTE
            if (SO) {
                fAux = fila + i;
                cAux = columna - i;
                p = new Posicion(fAux, cAux);
                SO = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, SO, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!SO && detectarJaqueMate && fAux < 7 && cAux > 0) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux += 1;
                    cAux -= 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
            // dirección SURESTE
            if (SE) {
                fAux = fila + i;
                cAux = columna + i;
                p = new Posicion(fAux, cAux);
                SE = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, SE, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!SE && detectarJaqueMate && fAux < 7 && cAux < 7) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux += 1;
                    cAux += 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
        }
        return hsPosiblesMovimientos;
    }

    @Override
    public HashSet<Posicion> detectarRutaJaque(Posicion pos, Posicion posRey, Casilla[][] arrayTablero) {
        HashSet<Posicion> hsRutaJaque = new HashSet<>();
        int fila = pos.getFila(), col = pos.getColumna();
        int filaAux, colAux;
        // establecemos parámetros para sacar la ruta
        if (posRey.getFila() > pos.getFila()) {
            filaAux = 1;
        } else {
            filaAux = -1;
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
