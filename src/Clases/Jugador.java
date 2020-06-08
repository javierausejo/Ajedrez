package Clases;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Jugador implements Serializable {

    // VARIABLES DE INSTANCIA
    private String idJugador, nickName;
    private boolean local; // si true, fichas blancas; si false, fichas negras
    private transient Socket socket;
    private transient ObjectOutputStream flujoEscritura;
    private transient ObjectInputStream flujoLectura;

    public Jugador(String idJugador, String nickName, Socket socket, ObjectOutputStream flujoEscritura, ObjectInputStream flujoLectura) {
        this.idJugador = idJugador;
        this.nickName = nickName;
        this.socket = socket;
        this.flujoEscritura = flujoEscritura;
        this.flujoLectura = flujoLectura;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getFlujoEscritura() {
        return flujoEscritura;
    }

    public void setFlujoEscritura(ObjectOutputStream flujoEscritura) {
        this.flujoEscritura = flujoEscritura;
    }

    public ObjectInputStream getFlujoLectura() {
        return flujoLectura;
    }

    public void setFlujoLectura(ObjectInputStream flujoLectura) {
        this.flujoLectura = flujoLectura;
    }

    public void cerrarConexion() {
        try {
            flujoEscritura.close();
            flujoLectura.close();
            socket.close();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}
