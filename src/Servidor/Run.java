package Servidor;

import Clases.Jugador;
import Clases.Partida;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Run {
    // CONSTANTES DE CLASE
    private final static int PUERTO = 3000;

    // VARIABLES DE INSTANCIA
    public static ArrayList<Partida> arrayPartidas;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            arrayPartidas = new ArrayList<>();
            Socket socket;
            Thread t;
            // flujos de conexión del cliente hacia el servidor
            ObjectInputStream flujoLectura;
            ObjectOutputStream flujoEscritura;
            // creamos variables de instancia necesarias para gestionar jugadores
            Jugador jugador = null;
            int idPartidaAux = 0;
            String idJugador, nickName, idPartida;
            // variables booleanas
            boolean control = true; // controla vida del hilo
            boolean encuentraPartida; // controla si hay partida disponible o hay que crear una nueva
            while (control) {
                // damos formato a idJugador, e inicializamos encuentraPartida
                encuentraPartida = false;
                // esperamos conexiones e inicializamos su flujo de lectura para recibir nickname
                socket = serverSocket.accept();
                flujoEscritura = new ObjectOutputStream(socket.getOutputStream());
                flujoLectura = new ObjectInputStream(socket.getInputStream());
                nickName = flujoLectura.readUTF();
                // buscamos para el cliente una partida en la que encajarlo
                // le mandaremos un objeto jugador a su medida en el momento que la encontremos
                for (Partida p : arrayPartidas) {
                    if (p.getArrayJugadores().size() != 2) { // si encontramos partida con hueco
                        idJugador = "J" + (p.getArrayJugadores().size() + 1);
                        jugador = new Jugador(idJugador, nickName, socket, flujoEscritura, flujoLectura);
                        jugador.setLocal(false);
                        p.añadirJugador(jugador);
                        // si la partida cuenta con 2 jugadores QUE NO HAN CERRADO SUS CONEXIONES, debe empezar
                        if (p.getArrayJugadores().size() == 2) {
                            Jugador jug = null;
                            for (int i = 0; i < p.getArrayJugadores().size(); i++) {
                                try {
                                    jug = p.getArrayJugadores().get(i);
                                    // envíamos a cada conexión su objeto Jugador, aprovechando así para comprobar
                                    // si el cliente/jugador local todavía no ha cerrado la conexión y sigue disponible
                                    // para la partida
                                    jug.getFlujoEscritura().writeObject(jug);
                                    jug.getFlujoEscritura().flush();
                                    encuentraPartida = true;
                                } catch (SocketException e) {
                                    // excepción que salta cuando al pasarle el objeto Jugador al cliente
                                    // este ya ha cerrado la conexión
                                    encuentraPartida = false;
                                    p.eliminarJugador(jug); // eliminamos el jugador correspondiente de la partida
                                    break; // salimos del bucle que recorre jugadores
                                }
                            }
                            // se manda un booleano a las conexiones para saber si comienza la partida
                            // comenzará si no ha saltado el socketException de arriba
                            if (encuentraPartida) {
                                for (Jugador j : p.getArrayJugadores()) {
                                    j.getFlujoEscritura().writeBoolean(true);
                                    // ARRANCAMOS HILOSERVIDOR PARA CADA JUGADOR
                                    t = new Thread(new HiloServidor(j, p));
                                    t.start();
                                }
                                break; // salimos del bucle en la primera coincidencia de partidas
                            } else {
                                // en este punto se deja el jugador en espera de rival y se actualizan sus valores,
                                // pasando de ser jugador visitante a jugador local
                                // se presupone que la conexión ha fallado con el que en principio iba a ser el jugador local
                                // porque el emparejamiento, una vez se establece conexión con el servidor, es instantáneo
                                // en definitiva, tomamos el primer jugador y actualizamos sus valores
                                jug = p.getArrayJugadores().get(0);
                                // actualizamos idNickname del jugador y lo seteamos como local
                                idJugador = "J" + (p.getArrayJugadores().size());
                                jug.setIdJugador(idJugador);
                                jugador.setLocal(true);
                                // enviamos objeto jugador porque el bucle de HiloCliente así lo requiere.
                                jug.getFlujoEscritura().writeObject(jug);
                                jug.getFlujoEscritura().flush();
                                jug.getFlujoEscritura().writeBoolean(false);
                            }
                        }
                    }
                }
                if (arrayPartidas.isEmpty() || encuentraPartida == false) { // si no encuentra, creamos una nueva
                    // aumentamos secuencialmente idPartidaAux y damos formato a idPartida
                    idPartidaAux += 1;
                    idPartida = "P" + idPartidaAux;
                    // creamos la nueva partida y le añadimos el jugador
                    Partida partida = new Partida(idPartida);
                    idJugador = "J" + (partida.getArrayJugadores().size() + 1);
                    jugador = new Jugador(idJugador, nickName, socket, flujoEscritura, flujoLectura);
                    jugador.setLocal(true);
                    partida.añadirJugador(jugador);
                    // añadimos partida a arrayPartidas
                    arrayPartidas.add(partida);
                }
            }
            // si salimos del bucle significa que hay que cerrar servidor y conexiones
            serverSocket.close();
            for (Partida p : arrayPartidas) {
                for (Jugador jug : p.getArrayJugadores()) {
                    jug.cerrarConexion();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void eliminarPartida(Partida partida) {
        // desconectamos a los clientes de la partida en cuestión
        for (Jugador jug : partida.getArrayJugadores()) {
            jug.cerrarConexion();
        }
        // eliminamos la partida del array
        arrayPartidas.remove(partida);
    }
}
