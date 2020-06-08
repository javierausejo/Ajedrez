package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Rey;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

public class Torre extends FiguraBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;
    private boolean primerMovimiento;

    public Torre(boolean local) {
        super("TORRE", local);
        primerMovimiento = true;
    }

    public boolean esPrimerMovimiento() {
        return primerMovimiento;
    }

    public void setPrimerMovimiento(boolean primerMovimiento) {
        this.primerMovimiento = primerMovimiento;
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] arrayTablero, boolean detectarJaqueMate) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        int fAux, cAux;
        Figura figura;
        Posicion p;
        // variables para calcular movimientos
        boolean N = true;
        boolean S = true;
        boolean O = true;
        boolean E = true;
        for (int i = 1; i < 8; i++) {
            // dirección norte
            if (N) {
                fAux = fila + i;
                cAux = columna;
                p = new Posicion(fAux, cAux);
                N = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, N, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!N && detectarJaqueMate && fAux < 7) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux += 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
            if (S) {
                // dirección sur
                fAux = fila - i;
                cAux = columna;
                p = new Posicion(fAux, cAux);
                S = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, S, detectarJaqueMate);
                // determinamos que se respetan las dimensiones del tablero
                if (!S && detectarJaqueMate && fAux > 0) {
                    figura = arrayTablero[fAux][cAux].getFigura();
                    fAux -= 1;
                    p = new Posicion(fAux, cAux);
                    comprobarJaqueMateAux(figura, p, arrayTablero, hsPosiblesMovimientos);
                }
            }
            // dirección oeste
            if (O) {
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
            if (E) {
                // dirección este
                fAux = fila;
                cAux = columna + i;
                p = new Posicion(fAux, cAux);
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


    /**
     * Método que en virtud de la posición de la torre, nos ayuda a detectar cuál de sus rutas pone en jaque al rey.
     *
     * @param pos
     * @param arrayTablero
     * @return un hashset con la ruta que sigue la torre
     */
    @Override
    public HashSet<Posicion> detectarRutaJaque(Posicion pos, Posicion posRey, Casilla[][] arrayTablero) {
        HashSet<Posicion> hsRutaJaque = new HashSet<>();
        int fila = pos.getFila(), col = pos.getColumna();
        int filaAux = 0, colAux = 0;
        // establecemos parámetros para sacar la ruta
        if (posRey.getFila() == pos.getFila()) {
            if (posRey.getColumna() > pos.getColumna()) {
                colAux = 1;
            } else {
                colAux = -1;
            }
        } else if (posRey.getColumna() == pos.getColumna()) {
            if (posRey.getFila() > pos.getFila()) {
                filaAux = 1;
            } else {
                filaAux = -1;
            }
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
