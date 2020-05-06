package Clases.Mensaje;

import Clases.Jugador;

import java.io.Serializable;

public class MensajeChat implements Mensaje, Serializable {

    // VARIABLES DE INSTANCIA
    private Jugador jugador; // jugador que lo envía
    private String txt; // cadena de texto a enviar

    public MensajeChat(Jugador jugador, String txt) {
        this.jugador = jugador;
        this.txt = txt;
    }

    @Override
    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
