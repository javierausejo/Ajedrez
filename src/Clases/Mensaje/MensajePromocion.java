package Clases.Mensaje;

import Clases.Jugador;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Posicion;

import java.io.Serializable;

public class MensajePromocion implements Mensaje, Serializable {

    // VARIABLES DE INSTANCIA
    private Jugador jugador; // jugador que lo envía
    private Figura figuraPromocion;
    private Posicion posDestino;

    public MensajePromocion(Jugador jugador, Figura figuraPromocion, Posicion posDestino) {
        this.jugador = jugador;
        this.figuraPromocion = figuraPromocion;
        this.posDestino = posDestino;
    }

    @Override
    public Jugador getJugador() {
        return jugador;
    }

    public Figura getFiguraPromocion() {
        return figuraPromocion;
    }

    public Posicion getPosDestino() {
        return posDestino;
    }
}
