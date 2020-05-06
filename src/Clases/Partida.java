package Clases;

import java.io.IOException;
import java.util.ArrayList;

public class Partida {

    // VARIABLES DE INSTANCIA
    private ArrayList<Jugador> arrayJugadores;

    public Partida(String idPartida) {
        arrayJugadores = new ArrayList<>();
    }

    public void añadirJugador(Jugador jugador) {
        arrayJugadores.add(jugador);
    }

    public void eliminarJugador(Jugador jugador) throws IOException {
        arrayJugadores.remove(jugador);
        jugador.getSocket().close();
    }

    public ArrayList<Jugador> getArrayJugadores() {
        return arrayJugadores;
    }

}