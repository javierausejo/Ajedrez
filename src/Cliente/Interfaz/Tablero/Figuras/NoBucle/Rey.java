package Cliente.Interfaz.Tablero.Figuras.NoBucle;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Bucle.Torre;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.FiguraNoBucle;
import Cliente.Interfaz.Tablero.Posicion;

import java.util.HashSet;

import static Cliente.Interfaz.Tablero.Tablero.DIM_TABLERO;

public class Rey extends FiguraNoBucle {

    // VARIABLES DE INSTANCIA
    private HashSet<Posicion> hsPosiblesMovimientos;
    private boolean primerMovimiento;

    public Rey(boolean local) {
        super("REY", local);
        primerMovimiento = true;
    }

    public boolean esPrimerMovimiento() {
        return primerMovimiento;
    }

    public void setPrimerMovimiento(boolean primerMovimiento) {
        this.primerMovimiento = primerMovimiento;
    }

    @Override
    public HashSet<Posicion> getPosiblesMovimientos(Posicion posicion, Casilla[][] array, boolean detectarJaqueMate) {
        hsPosiblesMovimientos = new HashSet<Posicion>();
        int fila = posicion.getFila();
        int columna = posicion.getColumna();
        Posicion p;
        // dirección norte
        p = new Posicion(fila - 1, columna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección noroeste
        p = new Posicion(fila - 1, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección noreste
        p = new Posicion(fila - 1, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dirección sur
        p = new Posicion(fila + 1, columna);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección suroeste
        p = new Posicion(fila + 1, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);
        // dirección sureste
        p = new Posicion(fila + 1, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dirección oeste
        p = new Posicion(fila, columna - 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        // dirección este
        p = new Posicion(fila, columna + 1);
        comprobar(hsPosiblesMovimientos, array, p, detectarJaqueMate);

        return hsPosiblesMovimientos;
    }

    /**
     * Método que comprueba si es posible ejecutar un movimiento de enroque corto, añadiéndolo así al HashSet de
     * posibles movimientos.
     */
    public void comprobarEnroqueCorto(Casilla casillaRey, Casilla[][] arrayTablero, boolean local) {
        boolean enroqueCorto = false;
        int colAux1, colAux2;
        Figura fAux, fAux1, fAux2;

        if (esPrimerMovimiento()) {
            if (local) {
                fAux = arrayTablero[0][7].getFigura();
                colAux1 = 6;
                fAux1 = arrayTablero[0][colAux1].getFigura();
                colAux2 = 5;
                fAux2 = arrayTablero[0][colAux2].getFigura();
            } else {
                fAux = arrayTablero[0][0].getFigura();
                colAux1 = 1;
                fAux1 = arrayTablero[0][colAux1].getFigura();
                colAux2 = 2;
                fAux2 = arrayTablero[0][colAux2].getFigura();
            }

            if (fAux != null && fAux instanceof Torre) {
                HashSet<Posicion> hsAux;
                Torre torre = (Torre) fAux;
                if (torre.esPrimerMovimiento()
                        && fAux1 == null
                        && fAux2 == null) {
                    enroqueCorto = true;
                    // recorremos tablero para determinar si se puede hacer enroque o no dependiendo
                    // de si el rey termina el movimiento en una posición que le deja en jaque, o atravesando
                    // una casilla a la que puede llegar el rival
                    Casilla c;
                    for (int i = 0; i < DIM_TABLERO; i++) {
                        for (int j = 0; j < DIM_TABLERO; j++) {
                            c = arrayTablero[i][j];
                            // si la figura no es nuestra, detectamos si puede bloquear el enroque dado que nuestro propio
                            // rey está en jaque o la ruta que atraviesa es un posible movimiento de una ficha rival
                            if (c.getFigura() != null && c.getFigura().esMia() != esMia()) {
                                hsAux = c.getFigura().getPosiblesMovimientos(new Posicion(i, j), arrayTablero, false);
                                for (Posicion p : hsAux) {
                                    if ((p.getFila() == 0 && p.getColumna() == colAux1)
                                            || (p.getFila() == 0 && p.getColumna() == colAux2)
                                            || (p.getFila() == casillaRey.getPosicion().getFila()
                                            && p.getColumna() == casillaRey.getPosicion().getColumna())) {
                                        enroqueCorto = false;
                                        break;
                                    }
                                }
                            }
                            if (!enroqueCorto)
                                break;
                        }
                        if (!enroqueCorto)
                            break;
                    }
                }
            }
            // añadimos las posiciones correspondientes al HashSet
            if (enroqueCorto) {
                hsPosiblesMovimientos.add(arrayTablero[0][colAux1].getPosicion());
            }
        }
    }


    /**
     * Método que comprueba si es posible ejecutar un movimiento de enroque largo, añadiéndolo así al HashSet de
     * posibles movimientos.
     */
    public void comprobarEnroqueLargo(Casilla casillaRey, Casilla[][] arrayTablero, boolean local) {
        HashSet<Posicion> hsAux;
        boolean enroqueLargo = false;
        int colAux1, colAux2;
        Figura fAux, fAux1, fAux2, fAux3;

        if (esPrimerMovimiento()) {
            if (local) {
                fAux = arrayTablero[0][0].getFigura();
                colAux1 = 2;
                fAux1 = arrayTablero[0][colAux1].getFigura();
                colAux2 = 3;
                fAux2 = arrayTablero[0][colAux2].getFigura();
                fAux3 = arrayTablero[0][1].getFigura();
            } else {
                fAux = arrayTablero[0][7].getFigura();
                colAux1 = 5;
                fAux1 = arrayTablero[0][colAux1].getFigura();
                colAux2 = 4;
                fAux2 = arrayTablero[0][colAux2].getFigura();
                fAux3 = arrayTablero[0][6].getFigura();
            }
            if (fAux != null && fAux instanceof Torre) {
                Torre torre = (Torre) fAux;
                if (torre.esPrimerMovimiento()
                        && fAux1 == null
                        && fAux2 == null
                        && fAux3 == null) {
                    enroqueLargo = true;
                    // recorremos tablero para determinar si se puede hacer enroque o no dependiendo
                    // de si el rey termina el movimiento en una posición que le deja en jaque, o atravesando
                    // una casilla a la que puede llegar el rival
                    Casilla c;
                    for (int i = 0; i < DIM_TABLERO; i++) {
                        for (int j = 0; j < DIM_TABLERO; j++) {
                            c = arrayTablero[i][j];
                            // si la figura no es nuestra, detectamos si puede bloquear el enroque dado que nuestro propio
                            // rey está en jaque o la ruta que atraviesa es un posible movimiento de una ficha rival
                            if (c.getFigura() != null && c.getFigura().esMia() != esMia()) {
                                hsAux = c.getFigura().getPosiblesMovimientos(new Posicion(i, j), arrayTablero, false);
                                for (Posicion p : hsAux) {
                                    if ((p.getFila() == 0 && p.getColumna() == colAux1)
                                            || (p.getFila() == 0 && p.getColumna() == colAux2)
                                            || (p.getFila() == casillaRey.getPosicion().getFila()
                                            && p.getColumna() == casillaRey.getPosicion().getColumna())) {
                                        enroqueLargo = false;
                                        break;
                                    }
                                }
                            }
                            if (!enroqueLargo)
                                break;
                        }
                        if (!enroqueLargo)
                            break;
                    }
                }
            }
            // añadimos las posiciones correspondientes al HashSet
            if (enroqueLargo) {
                hsPosiblesMovimientos.add(arrayTablero[0][colAux1].getPosicion());
            }
        }
    }
}
