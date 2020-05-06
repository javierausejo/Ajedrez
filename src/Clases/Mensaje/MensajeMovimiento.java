package Clases.Mensaje;

import Clases.Jugador;
import Cliente.Interfaz.Tablero.Casilla;

import java.io.Serializable;

public class MensajeMovimiento implements Mensaje, Serializable {

    // VARIABLES DE INSTANCIA
    private Jugador jugador; // jugador que lo envía
    private Casilla casillaOrigen;
    private Casilla casillaDestino;
    private boolean seCome;

    public MensajeMovimiento(Jugador jugador, Casilla casillaOrigen, Casilla casillaDestino, boolean seCome) {
        this.jugador = jugador;
        this.casillaOrigen = casillaOrigen;
        this.casillaDestino = casillaDestino;
        this.seCome = seCome;
    }

    @Override
    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Casilla getCasillaOrigen() {
        return casillaOrigen;
    }

    public void setCasillaOrigen(Casilla casillaOrigen) {
        this.casillaOrigen = casillaOrigen;
    }

    public Casilla getCasillaDestino() {
        return casillaDestino;
    }

    public void setCasillaDestino(Casilla casillaDestino) {
        this.casillaDestino = casillaDestino;
    }

    public boolean getSeCome() {
        return seCome;
    }

    public void setSeCome(boolean seCome) {
        this.seCome = seCome;
    }
}
