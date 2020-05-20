package Cliente;

import Clases.Jugador;
import Clases.Mensaje.Mensaje;
import Clases.Mensaje.MensajeChat;
import Clases.Mensaje.MensajeMovimiento;
import Cliente.Interfaz.Interfaz;
import Cliente.Interfaz.Tablero.Casilla;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class HiloCliente implements Runnable {

    // CONSTANTES DE CLASE
    private final static int PUERTO = 3000;
    private final static String DIRECCION = "localhost";

    // VARIABLES DE INSTANCIA
    private Interfaz interfaz;
    private Socket socket;
    private ObjectOutputStream flujoEscritura;
    private ObjectInputStream flujoLectura;
    private Jugador jugador;

    public HiloCliente(Interfaz interfaz) {
        this.interfaz = interfaz;
        // arrancamos hilo
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            // generamos conexión al servidor e implantamos modoBusqueda en interfaz
            socket = new Socket(DIRECCION, PUERTO);
            interfaz.setModoBusqueda();
            // creamos flujos de lectura y de escritura
            flujoEscritura = new ObjectOutputStream(socket.getOutputStream());
            flujoLectura = new ObjectInputStream(socket.getInputStream());
            // creamos flujo escritura y mandamos nickname al servidor
            flujoEscritura.writeUTF(interfaz.getNickname()); // enviamos nickname
            flujoEscritura.flush();
            // creamos flujo lectura y recibimos nuestros parámetros como jugador, así
            // como un booleano que indica si comienza la partida
            boolean comienzaPartida = false; // hasta que no sea true, la partida no empieza
            while (!comienzaPartida) {
                jugador = (Jugador) flujoLectura.readObject();
                comienzaPartida = flujoLectura.readBoolean();
            }
            interfaz.setModoPartida(jugador.isLocal());
            interfaz.setNicknames(jugador, (Jugador) flujoLectura.readObject());
            Object o;
            Mensaje mensaje;
            while (!socket.isClosed()) {
                try {
                    mensaje = (Mensaje) flujoLectura.readObject();
                    // determinamos si el mensaje lo ha enviado el mismo cliente o el rival
                    if (mensaje.getJugador().getIdJugador().equals(jugador.getIdJugador())) {
                        interfaz.procesarMensaje(mensaje, true);
                    } else {
                        interfaz.procesarMensaje(mensaje, false);
                    }
                }  catch (SocketException e) {
                    // caso desconexión del cliente hacie el servidor
                    desconexion();
                    JOptionPane.showMessageDialog(interfaz,
                            "Se ha desconectado con éxito.",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (EOFException ex) {
                    // caso pérdida de conexión con servidor
                    desconexion(); // salimos del bucle
                    JOptionPane.showMessageDialog(interfaz,
                            "Se ha perdido la conexión con la partida.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            // si salimos del bucle cerramos conexiones y habilitamos modoInicio en interfaz
            desconexion();
        } catch (ConnectException e) {
            // caso de NO llegar a entablar conexión con el servidor
            interfaz.setModoInicio();
            JOptionPane.showMessageDialog(interfaz,
                    "No pudo establecerse conexión con el servidor.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (SocketException e) {
            // caso desconexión del cliente hacie el servidor
            desconexion();
            JOptionPane.showMessageDialog(interfaz,
                    "Se ha perdido la conexión con el servidor.",
                    "Información",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método usado para desconectar el cliente del servidor.
     * Cierra la conexión y el flujo de escritura.
     * Restaura el estado de la interfaz del cliente al de por defecto.
     */
    public void desconexion() {
        try {
            socket.close();
            flujoLectura.close();
            flujoEscritura.close();
            interfaz.setModoInicio();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que recibe una cadena mensaje del chat de la interfaz y la envía.
     * @param txt cadena enviada durante el chateo
     */
    public void enviarMensajeChat(String txt) {
        try {
            Mensaje mensaje = new MensajeChat(jugador, txt);
            flujoEscritura.writeObject(mensaje);
            flujoEscritura.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  enviarMovimiento(Casilla casillaOrigen, Casilla casillaDestino, boolean seCome) {
        try {
            Mensaje mensaje = new MensajeMovimiento(jugador, casillaOrigen, casillaDestino, seCome);
            flujoEscritura.writeObject(mensaje);
            flujoEscritura.flush();
            flujoEscritura.reset(); // reseteamos flujo de escritura a estado inicial, de lo contrario no transmite bien la figura de las casillas (SALTA NULLPOINTEREXCEPTION)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
