package Servidor;

import Clases.Jugador;
import Clases.Mensaje.Mensaje;
import Clases.Partida;

import java.io.*;

public class HiloServidor implements Runnable {

    // VARIABLES DE INSTANCIA
    private Jugador jugador;
    private Partida partida;
    private ObjectInputStream flujoLectura;
    private ObjectOutputStream flujoEscritura;
    private boolean control;

    public HiloServidor(Jugador jugador, Partida partida) {
        this.jugador = jugador;
        this.partida = partida;
        // enlazamos al flujoLectura y flujoEscritura del jugador que se conecta
        flujoLectura = jugador.getFlujoLectura();
        flujoEscritura = jugador.getFlujoEscritura();
        control = true;
    }

    @Override
    public void run() {
        Mensaje mensaje;
        // enviamos al cliente info sobre su rival para que actualice la interfaz
        for (Jugador j : partida.getArrayJugadores()) {
            if (!j.equals(jugador)) {
                try {
                    flujoEscritura.writeObject(j);
                } catch (IOException e) {
                    // excepción que se controla en Run.java
                }
            }
        }
        // comenzamos con bucle para escuchar los mensajes del Jugador en cuestión
        while (control) {
            try {
                mensaje = (Mensaje) flujoLectura.readObject();
                enviarMensaje(mensaje);
            } catch (IOException | ClassNotFoundException e) {
                control = false;
            }
        }

        // si salimos del bucle debemos cerrar conexión
        eliminarPartida();
    }

    /**
     * Método por el cual el servidor recibe por parámetro un mensaje y lo redirige
     * a los jugadores de la partida, enviándolos a sus correspondientes flujos de escritura.
     */
    private void enviarMensaje(Mensaje mensaje) {
        for (Jugador jugador : partida.getArrayJugadores()) {
            try {
                jugador.getFlujoEscritura().writeObject(mensaje);
                jugador.getFlujoEscritura().flush();
            } catch (IOException e) {
                eliminarPartida();
            }
        }
    }

    private void eliminarPartida() {
        for (Jugador jugador : partida.getArrayJugadores()) {
            jugador.cerrarConexion();
        }
        Run.eliminarPartida(partida);
    }
}
