package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Reina extends FiguraBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;

    public Reina(boolean local) {
        super("REINA", local);
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] arrayTablero, boolean detectarJaqueMate) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        int fAux, cAux;
        Figura figura;
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
                fAux = fila - i;
                cAux = columna;
                p = new Posicion(fAux, cAux);
                N = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, N, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!N && detectarJaqueMate && fAux > 0) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux -= 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
            // dirección noroeste
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
            // dirección noreste
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

            if (S) {
                // dirección sur
                fAux = fila + i;
                cAux = columna;
                p = new Posicion(fAux, cAux);
                S = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, S, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!S && detectarJaqueMate && fAux < 7) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux += 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
            // dirección suroeste
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
            // dirección sureste
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

            if (O) {
                // dirección oeste
                fAux = fila;
                cAux = columna - i;
                p = new Posicion(fAux, cAux);
                O = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, O, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!O && detectarJaqueMate && cAux > 0) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    cAux -= 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }

            // dirección este
            if (E) {
                fAux = fila;
                cAux = columna + i;
                p = new Posicion(fila, columna + i);
                E = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, E, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!E && detectarJaqueMate && cAux < 7) {
                    figura = arrayTablero[fAux][cAux].getFigura();
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
}
