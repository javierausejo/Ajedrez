package Cliente.Interfaz;

import Clases.Jugador;
import Clases.Mensaje.Mensaje;
import Clases.Mensaje.MensajeChat;
import Clases.Mensaje.MensajeMovimiento;
import Cliente.HiloCliente;
import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Figuras.Peon;
import Cliente.Interfaz.Tablero.Posicion;
import Cliente.Interfaz.Tablero.Tablero;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static Cliente.Interfaz.Tablero.Tablero.DIM_BOTON_TABLERO;
import static Cliente.Interfaz.Tablero.Tablero.DIM_TABLERO;

public class Interfaz extends JFrame implements ActionListener, KeyListener {

    // CONSTANTES DE CLASE
    private final static String TITULO = "AJEDREZ EN LÍNEA";
    private final static int ESPACIO = 10;
    private final static int TAM_TXT = 30;
    private final static String USUARIO = "Nombre de usuario:";
    private final static String ESTADO = "Estado:";
    private final static String ESTADO_DESC = "DESCONECTADO";
    private final static String ESTADO_CON = "CONECTADO";
    private final static String ESTADO_CONECTANDO = "CONECTANDO...";
    private final static String ESTADO_BUSQUEDA = "BUSCANDO PARTIDA...";
    private final static Font FUENTE = new Font("CC Wild Words", Font.BOLD, 13);
    private final static String CONEX = "Conectarse";
    private final static String DESCONEX = "Desconectarse";
    private final static Dimension DIM_BOTONES_CONEX = new Dimension(130, 25);
    private final static String AREA_CONEX = "Parámetros de conexión";
    private final static String AREA_JUEGO = "Área de juego";
    private final static String JUG_1 = "JUGADOR 1";
    private final static String JUG_2 = "JUGADOR 2";
    private final static String TIEMPO = "HH : MM : SS";
    private final static int ANCHO_CHAT = 25;

    // VARIABLES DE INSTANCIA
    private JPanel pnlContenedor;
    private JTextField txtUsuario, txtEstado, txtMensaje;
    private JButton btnConectar, btnDesconectar;
    private JLabel lblUsuario1, lblUsuario2, lblTiempo1, lblTiempo2;
    private Tablero pnlTablero;
    private Casilla[][] arrayTablero;
    private DefaultListModel modelo;
    private JTextArea txaChat;
    private JScrollPane scpChat;
    private HiloCliente hiloCliente;
    private Jugador jugador;

    /**
     * Método que inicializa la interfaz de usuario.
     */
    public Interfaz() {
        super(TITULO);
        pnlContenedor = new JPanel(new BorderLayout());
        pnlContenedor.setBorder(new EmptyBorder(ESPACIO, ESPACIO, ESPACIO, ESPACIO));

        // creamos la interfaz
        crearPnlNorte();
        crearPnlCentro();

        // iniciamos por defecto el modoInicio
        setModoInicio();

        // añadimos listeners
        addListeners();

        setContentPane(pnlContenedor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Método que crea la parte Norte de la interfaz de usuario.
     * Incluye lblTitulo y la zona de ÁREA DE CONEXIÓN: lblUsuario, txtUsuario, btnConectar y btnDesconectar.
     * Se añade al norte de pnlContenedor.
     */
    private void crearPnlNorte() {
        JPanel pnlNorte = new JPanel(new BorderLayout());

        // creamos pnlNorteNorte, que incluye lblTitulo
        JPanel pnlNorteNorte = new JPanel();
        JLabel lblTitulo = new JLabel(TITULO);
        lblTitulo.setFont(new Font(lblTitulo.getFont().getFontName(), Font.BOLD, 30));
        pnlNorteNorte.add(lblTitulo);

        // creamos pnlNorteCentro, que incluye la zona ÁREA DE CONEXIÓN
        JPanel pnlNorteCentro = new JPanel(new BorderLayout());
        pnlNorteCentro.setBorder(new EmptyBorder(ESPACIO, 0, ESPACIO, 0));
        pnlNorteCentro.setBorder(new CompoundBorder(
                new TitledBorder(new EtchedBorder(), AREA_CONEX), // borde externo
                new EmptyBorder(ESPACIO, ESPACIO, ESPACIO, ESPACIO) // borde interno
        ));
        // creamos el pnlCentroOeste, que incluye el lblUsuario y txtUsuario
        // se sitúa al oeste de pnlNorteCentro
        JPanel pnlCentroOeste = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblUsuario = new JLabel(USUARIO);
        txtUsuario = new JTextField(TAM_TXT / 3);
        txtUsuario.setHorizontalAlignment(JTextField.CENTER);
        añadirElementos(pnlCentroOeste, lblUsuario, txtUsuario);
        pnlNorteCentro.add(pnlCentroOeste, BorderLayout.WEST);
        // creamos pnlCentroCentro, que incluye lblEstado
        // se sitúa al centro de pnlNorteCentor
        JPanel pnlCentroCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblEstado = new JLabel(ESTADO);
        txtEstado = new JTextField("Desconectado", TAM_TXT / 2);
        txtEstado.setHorizontalAlignment(JTextField.CENTER);
        txtEstado.setEnabled(false);
        txtEstado.setFont(FUENTE);
        añadirElementos(pnlCentroCentro, lblEstado, txtEstado);
        pnlNorteCentro.add(pnlCentroCentro);
        // creamos pnlCentroEste, que incluye btnBuscarPartida y btnConexion
        // se sitúa al este pnlNorteCentro
        JPanel pnlCentroEste = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnConectar = new JButton(CONEX);
        btnConectar.setPreferredSize(DIM_BOTONES_CONEX);
        btnDesconectar = new JButton(DESCONEX);
        btnDesconectar.setPreferredSize(DIM_BOTONES_CONEX);
        añadirElementos(pnlCentroEste, btnDesconectar, btnConectar);
        pnlNorteCentro.add(pnlCentroEste, BorderLayout.EAST);

        // añadimos ambos paneles a pnlNorte
        pnlNorte.add(pnlNorteNorte, BorderLayout.NORTH);
        pnlNorte.add(pnlNorteCentro, BorderLayout.CENTER);

        // añadimos pnlNorte al norte de pnlContenedor
        pnlContenedor.add(pnlNorte, BorderLayout.NORTH);
    }

    /**
     * Método que crea la parte Centro de la interfaz de usuario.
     * Incluye el tablero de ajedrez, el pnl con datos de los jugadores, el pnlRegistro y el pnlChat.
     * Se añade al centro de pnlContenedor.
     */
    private void crearPnlCentro() {
        JPanel pnlCentro = new JPanel(new BorderLayout());
        pnlCentro.setBorder(new CompoundBorder(
                new TitledBorder(new EtchedBorder(), AREA_JUEGO), // borde externo
                new EmptyBorder(DIM_BOTON_TABLERO.height / 2, 0, 0, DIM_BOTON_TABLERO.height / 2) // borde interno
        ));

        // creamos el pnlTablero y lo añadimos al centro de pnlCentro
        // también vinculamos arrayTablero con las casillas del tablero
        pnlTablero = new Tablero();
        pnlTablero.setBorder(new EmptyBorder(0, 0, 0, DIM_BOTON_TABLERO.height / 2));
        pnlCentro.add(pnlTablero, BorderLayout.WEST);

        // creamos pnlCentroEste, que comprenderá pnlInfo y pnlPartida
        JPanel pnlCentroEste = new JPanel(new BorderLayout());
        // creamos pnlInfo y lo añadimos al NORTE de pnlCentroEste
        JPanel pnlInfo = crearPnlInfo();
        pnlCentroEste.add(pnlInfo, BorderLayout.NORTH);
        // creamos pnlPartida, que comprende pnlRegistro y pnlChat, y lo añadimos al centro de pnlCentroEste
        JPanel pnlPartida = crearPnlPartida();
        pnlCentroEste.add(pnlPartida, BorderLayout.CENTER);
        pnlCentro.add(pnlCentroEste, BorderLayout.CENTER);

        // añadimos pnlCentro al CENTRO de pnlContenedor
        pnlContenedor.add(pnlCentro, BorderLayout.CENTER);
    }

    /**
     * Método auxiliar derivado de crearPnlCentro() que genera un jpanel con los datos de los jugadores.
     *
     * @return pnlInfo
     */
    private JPanel crearPnlInfo() {
        JPanel pnlInfo = new JPanel(new BorderLayout());
        pnlInfo.setBorder(new EmptyBorder(0, 0, ESPACIO, 0));

        // creamos pnlInfoLocal, con los datos del jugador 1 y lo añadimos al OESTE de pnlRegistroNorte
        JPanel pnlInfoLocal = new JPanel(new BorderLayout());
        lblUsuario1 = new JLabel(JUG_1, SwingConstants.CENTER);
        lblTiempo1 = new JLabel(TIEMPO, SwingConstants.CENTER);
        pnlInfoLocal.add(lblUsuario1, BorderLayout.NORTH);
        pnlInfoLocal.add(lblTiempo1, BorderLayout.SOUTH);
        pnlInfo.add(pnlInfoLocal, BorderLayout.WEST);

        // creamos pnlInfoVisitante, con los datos del jugador 2 y lo añadimos al ESTE de pnlRegistroNorte
        JPanel pnlInfoVisitante = new JPanel(new BorderLayout());
        lblUsuario2 = new JLabel(JUG_2, SwingConstants.CENTER);
        lblTiempo2 = new JLabel(TIEMPO, SwingConstants.CENTER);
        pnlInfoVisitante.add(lblUsuario2, BorderLayout.NORTH);
        pnlInfoVisitante.add(lblTiempo2, BorderLayout.SOUTH);
        pnlInfo.add(pnlInfoVisitante, BorderLayout.EAST);

        return pnlInfo;
    }

    /**
     * Método auxiliar derivado de crearPnlCentro() que genera un jpanel con gridlayout que comprende
     * pnlRegistro y pnlChat.
     *
     * @return pnlPartida
     */
    private JPanel crearPnlPartida() {
        JPanel pnlPartida = new JPanel(new GridLayout(2, 1));
        pnlPartida.setBorder(new EmptyBorder(0, 0, DIM_BOTON_TABLERO.height, 0));

        // creamos pnlRegistro y pnlChat
        JPanel pnlRegistro = crearPnlRegistro();
        JPanel pnlChar = crearPnlChat();
        // añadimos
        añadirElementos(pnlPartida, pnlRegistro, pnlChar);

        return pnlPartida;
    }

    /**
     * Método auxiliar derivado de crearPnlPartida() que genera un pnl con los lbl's que indican el nombre de usuario
     * y su marca de tiempo. También comprende un jlist que registra los movimientos de la partida
     *
     * @return pnlRegistro
     */
    private JPanel crearPnlRegistro() {
        JPanel pnlRegistro = new JPanel(new GridLayout(1, 1));

        // creamos un scrollpane con jlist que refleje los movimientos de la partida
        // se añade al centro de pnlRegistro
        JList jlist = new JList();
        modelo = new DefaultListModel();
        jlist.setModel(modelo);
        // añadimos scrollpane al jlist
        JScrollPane scrollPane = new JScrollPane(jlist);

        // añadimos elementos a pnlRegistro
        pnlRegistro.add(scrollPane);

        return pnlRegistro;
    }

    /**
     * Método auxiliar derivado de crearPnlPartida() que devuelve un jPanel que contendrá un textArea
     * que funcionará a modo de chat entre los usuarios de la partida.
     *
     * @return pnlChat
     */
    private JPanel crearPnlChat() {
        JPanel pnlChat = new JPanel(new BorderLayout());
        pnlChat.setBorder(new EmptyBorder(ESPACIO, 0, 0, 0));

        txaChat = new JTextArea();
        txaChat.setEditable(false);
        txaChat.setMargin(new Insets(ESPACIO, ESPACIO, ESPACIO, ESPACIO));
        // si el texto no cabe en el jtextarea, lo divide en líneas
        txaChat.setLineWrap(true);
        txaChat.setWrapStyleWord(true);
        // baja scroll automáticamente con la llegada de mensajes
        DefaultCaret caret = (DefaultCaret) txaChat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        scpChat = new JScrollPane(txaChat);
        scpChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pnlChat.add(scpChat, BorderLayout.CENTER);

        txtMensaje = new JTextField(ANCHO_CHAT);
        txtMensaje.setBorder(BorderFactory.createCompoundBorder(
                txtMensaje.getBorder(),
                BorderFactory.createEmptyBorder(ESPACIO, ESPACIO, ESPACIO, ESPACIO)));
        pnlChat.add(txtMensaje, BorderLayout.SOUTH);

        return pnlChat;
    }

    /**
     * Método auxiliar que ayuda a añadir elementos de manera más eficaz.
     *
     * @param panel     el panel que recibe los elementos
     * @param elementos los elementos a añadir en panel
     */
    private void añadirElementos(JPanel panel, JComponent... elementos) {
        for (JComponent elemento : elementos) {
            panel.add(elemento);
        }
    }

    /**
     * Método que devuelve el nickname autoasignado por el cliente.
     */
    public String getNickname() {
        return txtUsuario.getText().trim();
    }

    /**
     * Método que recibe 2 instancias de jugadores y actualiza la interfaz
     * acorde a sus parámetros.
     */
    public void setNicknames(Jugador jugador1, Jugador jugador2) {
        jugador = jugador1;
        lblUsuario1.setText(jugador1.getIdJugador() + " - " + jugador1.getNickName());
        lblUsuario2.setText(jugador2.getIdJugador() + " - " +jugador2.getNickName());
    }

    /**
     * Método que simula un modo desconexión por el cual el usuario ni está buscando partida,
     * ni tiene acceso al ajedrez o al chat.
     */
    public void setModoInicio() {
        // restauramos a tablero sin fichas
        pnlTablero.limpiarTablero();
        arrayTablero = pnlTablero.getArrayTablero();

        // limpiamos txaChat y el modelo del registro de movimientos
        txaChat.setText("");
        modelo.clear();

        txtUsuario.setEnabled(true);
        btnConectar.setEnabled(true);
        btnDesconectar.setEnabled(false);
        for (int i = 0; i < DIM_TABLERO; i++) {
            for (int j = 0; j < DIM_TABLERO; j++) {
                arrayTablero[i][j].getBtnCasilla().setEnabled(false);
            }
        }
        txtMensaje.setEnabled(false);

        // cambiamos color y mensaje del txtEstado
        txtEstado.setBackground(Color.WHITE);
        txtEstado.setText(ESTADO_DESC);

        // restauramos nombres de lblUsuario a los originales
        lblUsuario1.setText(JUG_1);
        lblUsuario2.setText(JUG_2);
    }

    /**
     * Método que, en virtud de una conexión con el servidor a la espera de que este le
     * asigne una partida, simula un modo de búsqueda en el que la interfaz notifica al usuario
     * de esto.
     */
    public void setModoBusqueda() {
        txtUsuario.setEnabled(false);
        btnConectar.setEnabled(false);
        btnDesconectar.setEnabled(true);
        for (int i = 0; i < DIM_TABLERO; i++) {
            for (int j = 0; j < DIM_TABLERO; j++) {
                arrayTablero[i][j].getBtnCasilla().setEnabled(false);
            }
        }
        txtMensaje.setEnabled(false);

        // cambiamos color y mensaje del txtEstado
        txtEstado.setBackground(Color.YELLOW);
        txtEstado.setText(ESTADO_BUSQUEDA);
    }

    /**
     * Método que se invoca cuando el usuario cliente ya ha encontrado una partida
     * y necesita tener habilitados los elementos de tablero y chat correspondientes.
     *
     * @param local  indica si el cliente juega con las fichas blancas (true) o las negras (false)
     */
    public void setModoPartida(boolean local) {
        pnlTablero.comienzoPartida(hiloCliente, local);
        txtUsuario.setEnabled(false);
        btnConectar.setEnabled(false);
        btnDesconectar.setEnabled(true);
        for (int i = 0; i < DIM_TABLERO; i++) {
            for (int j = 0; j < DIM_TABLERO; j++) {
                arrayTablero[i][j].getBtnCasilla().setEnabled(true);
            }
        }
        txtMensaje.setEnabled(true);

        // cambiamos color y mensaje del txtEstado
        txtEstado.setBackground(Color.GREEN);
        txtEstado.setText(ESTADO_CON);
    }

    /**
     * Método que añade los listeners necesarios a los elementos de la interfaz.
     * Se le llama desde el propio constructor.
     */
    private void addListeners() {
        btnConectar.addActionListener(this);
        btnDesconectar.addActionListener(this);
        txtMensaje.addKeyListener(this);
    }


    /**
     * Método invocado desde HiloCliente.java para que la interfaz interprete los mensajes
     * que le redirige el servidor
     *
     * @param mensaje      el objeto Mensaje a interpretar
     * @param enviadoPorMi boolean que representa si el mensaje lo ha enviado el mismo cliente o el rival.
     */
    public void procesarMensaje(Mensaje mensaje, boolean enviadoPorMi) {
        String jug;
        if (enviadoPorMi) {
            jug = "USTED";
        } else {
            jug = mensaje.getJugador().getNickName().toUpperCase();
        }

        if (mensaje instanceof MensajeChat) {
            MensajeChat m = (MensajeChat) mensaje;
            txaChat.append(">>> " + jug + "\n");
            txaChat.append(m.getTxt() + "\n");
            // desplazamos barra de scroll del chat a medida que escribimos
            scpChat.getVerticalScrollBar().setValue(scpChat.getVerticalScrollBar().getMaximum());
        } else if (mensaje instanceof MensajeMovimiento) {
            MensajeMovimiento m = (MensajeMovimiento) mensaje;
            String valoresColumna = "ABCDEFGH";
            Figura figuraOrigen = m.getCasillaOrigen().getFigura();
            // damos formato al mensaje según quién lo envíe
            Posicion posOrigen = m.getCasillaOrigen().getPosicion();
            Posicion posDestino = m.getCasillaDestino().getPosicion();
            char filaOrigenAux = 0, filaDestinoAux = 0;
            if (enviadoPorMi) { // no cambiamos valores si somos nosotros quienes movemos la figura
                filaOrigenAux = (char) ((posOrigen.getFila() + 1) + '0');
                filaDestinoAux = (char) ((posDestino.getFila() + 1) + '0');
            } else { // cambiamos si recibimos movimiento del rival
                filaOrigenAux = (char) ((8 - posOrigen.getFila()) + '0');
                filaDestinoAux = (char) ((8 - posDestino.getFila()) + '0');
                posOrigen = new Posicion(7 - posOrigen.getFila(), 7 - posOrigen.getColumna());
                posDestino = new Posicion(7 - posDestino.getFila(), 7 - posDestino.getColumna());
            }

            if (!m.getSeCome()) {
                modelo.addElement(jug + " >>> Mueve " + figuraOrigen.getNom() + " de " + valoresColumna.charAt(posOrigen.getColumna()) + " - "
                        + filaOrigenAux + " a " + valoresColumna.charAt(posDestino.getColumna()) + " - "
                        + filaDestinoAux + ".");
            } else {
                Figura figuraAComer = m.getCasillaDestino().getFigura();
                modelo.addElement(jug + " >>> Mueve " + figuraOrigen.getNom() + " de " + valoresColumna.charAt(posOrigen.getColumna()) + " - "
                        + filaOrigenAux + " a " + valoresColumna.charAt(posDestino.getColumna()) + " - "
                        + filaDestinoAux + ".");
                modelo.addElement(jug + " >>> Come " + figuraAComer.getNom() + " rival.");
            }

            // movemos la figura en el tablero
            pnlTablero.moverFigura(posOrigen, posDestino);

            // comprobamos si hay jaque
            boolean jaque = pnlTablero.comprobarJaque(arrayTablero[posDestino.getFila()][posDestino.getColumna()]);
            if (jaque == true) {
                modelo.addElement(jug + " >>> Aviso de JAQUE.");
            }

            // promocionamos peón si fuera preciso
            if (arrayTablero[posDestino.getFila()][posDestino.getColumna()].getFigura() instanceof Peon) {
                if (posDestino.getFila() == 7) { // pr
                    System.out.println("hola1");
                } else if (posDestino.getFila() == 0) {
                    System.out.println("Hola2");
                }
            }

            // cambiamos turno
            pnlTablero.cambiarTurno();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Si pulsamos sobre el btnConectar, iniciamos hilo cliente
        // y cambiamos algunos elementos de la interfaz
        if (e.getSource().equals(btnConectar)) {
            if (getNickname().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Indique un nickname.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                hiloCliente = new HiloCliente(this);

                txtEstado.setText(ESTADO_CONECTANDO);
                btnConectar.setEnabled(false);
            }
        }

        // Si pulsamos sobre btnDesconectar, desconectamos al usuario del servidor
        if (e.getSource().equals(btnDesconectar)) {
            hiloCliente.desconexion();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Si pulsamos enter sobre txtMensaje, enviamos mensaje a servidor
        if (e.getSource().equals(txtMensaje)
                && e.getKeyCode() == KeyEvent.VK_ENTER
                && !txtMensaje.getText().trim().isEmpty()) {
            hiloCliente.enviarMensajeChat(txtMensaje.getText().trim());
            txtMensaje.setText("");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
