package Cliente.Interfaz.Tablero.Figuras.Bucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Bucle.FiguraBucle;
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
        Posicion p;
        // variables para calcular movimientos
        boolean N = true;
        boolean S = true;
        boolean O = true;
        boolean E = true;
        for (int i = 1; i < 8; i++) {
            // dirección norte
            if (N) {
                p = new Posicion(fila + i, columna);
                N = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, N, detectarJaqueMate);
            }
            if (S) {
                // dirección sur
                p = new Posicion(fila - i, columna);
                S = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, S, detectarJaqueMate);
            }
            // dirección oeste
            if (O) {
                p = new Posicion(fila, columna - i);
                O = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, O, detectarJaqueMate);
            }
            if (E) {
                // dirección este
                p = new Posicion(fila, columna + i);
                E = comprobarBucle(hsPosiblesMovimientos, arrayTablero, p, E, detectarJaqueMate);
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
