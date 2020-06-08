package Clases.Mensaje;

import Clases.Jugador;

import java.io.Serializable;

public class MensajeEmpate implements Mensaje, Serializable {

    // VARIABLES DE INSTANCIA
    private Jugador jugador; // jugador que envía
    private boolean tablas, iniciaSolicitud;

    public MensajeEmpate(Jugador jugador, boolean tablas, boolean iniciaSolicitud) {
        this.jugador = jugador;
        this.tablas = tablas;
        this.iniciaSolicitud = iniciaSolicitud;
    }

    @Override
    public Jugador getJugador() {
        return jugador;
    }

    public boolean aceptaTablas() {
        return tablas;
    }

    public boolean iniciaSolicitud() {
        return iniciaSolicitud;
    }
}
