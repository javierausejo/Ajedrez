package Cliente.Interfaz;

import Clases.Jugador;
import Clases.Mensaje.*;
import Cliente.HiloCliente;
import Cliente.HiloCronometro;
import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Figuras.Bucle.Alfil;
import Cliente.Interfaz.Tablero.Figuras.Bucle.Reina;
import Cliente.Interfaz.Tablero.Figuras.Bucle.Torre;
import Cliente.Interfaz.Tablero.Figuras.Figura;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Caballo;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Peon;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Rey;
import Cliente.Interfaz.Tablero.Posicion;
import Cliente.Interfaz.Tablero.Tablero;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static Cliente.Interfaz.Tablero.Tablero.DIM_BOTON_TABLERO;
import static Cliente.Interfaz.Tablero.Tablero.DIM_TABLERO;

public class Interfaz extends JFrame implements ActionListener, KeyListener {

    // CONSTANTES DE CLASE
    private final static String ICONO = "Recursos/icono.png";
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
    private final static String TIEMPO = " - \"";
    private final static String EMPATE = " ¿TABLAS?";
    private final static int ANCHO_CHAT = 25;

    // VARIABLES DE INSTANCIA
    private JPanel pnlContenedor;
    private JTextField txtUsuario, txtEstado, txtMensaje;
    private JButton btnConectar, btnDesconectar, btnEmpate;
    private JLabel lblUsuario1, lblUsuario2, lblTiempo1, lblTiempo2;
    private Tablero pnlTablero;
    private Casilla[][] arrayTablero;
    private JScrollPane scpRegistro;
    private DefaultListModel modelo;
    private JTextPane txpChat;
    private StyledDocument doc;
    private HiloCliente hiloCliente;
    private HiloCronometro hiloCronometro;

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

        // añadimos icono
        ImageIcon icon = new ImageIcon(ICONO);
        setIconImage(icon.getImage());

        setContentPane(pnlContenedor);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
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

        // creamos un boton para proponer tablas
        JPanel pnlEmpate = new JPanel();
        btnEmpate = new JButton(EMPATE);
        pnlEmpate.add(btnEmpate);
        pnlInfo.add(pnlEmpate, BorderLayout.CENTER);

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
        JPanel pnlChat = crearPnlChat();
        // añadimos
        añadirElementos(pnlPartida, pnlRegistro, pnlChat);

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
        scpRegistro = new JScrollPane(jlist);

        // añadimos elementos a pnlRegistro
        pnlRegistro.add(scpRegistro);

        return pnlRegistro;
    }

    /**
     * Método auxiliar derivado de crearPnlPartida() que devuelve un jPanel que contendrá un textArea
     * que funcionará a modo de chat entre los usuarios de la partida.
     *
     * @return pnlChat
     */
    private JPanel crearPnlChat() {
        // creamos panel y añadimos imagen de fondo
        JPanel pnlChat = new JPanel(new BorderLayout());
        pnlChat.setBorder(new EmptyBorder(ESPACIO, 0, 0, 0));

        // creamos jtextpane con su hoja de estilos doc
        txpChat = new JTextPane();
        txpChat.setEditable(false);
        doc = txpChat.getStyledDocument();

        JScrollPane scpChat = new JScrollPane(txpChat);
        scpChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scpChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
        lblUsuario1.setText(jugador1.getNickName());
        lblUsuario2.setText(jugador2.getNickName());
    }

    public void setTiempoTurno(boolean esMiTurno, String tiempoRestante) {
        if (esMiTurno) {
            lblTiempo1.setText(tiempoRestante + " \"");
        } else {
            lblTiempo2.setText(tiempoRestante + " \"");
        }
    }

    public void reiniciarTiempos() {
        lblTiempo1.setText(TIEMPO);
        lblTiempo2.setText(TIEMPO);
    }

    /**
     * Método que simula un modo desconexión por el cual el usuario ni está buscando partida,
     * ni tiene acceso al ajedrez o al chat.
     */
    public void setModoInicio() {
        // restablecemos tiempos y apagamos cronometro si fuera necesario
        if (hiloCronometro != null)
            hiloCronometro.apagarCronometro();
        reiniciarTiempos();

        // restauramos a tablero sin fichas
        pnlTablero.limpiarTablero();
        arrayTablero = pnlTablero.getArrayTablero();

        // limpiamos txaChat y el modelo del registro de movimientos
        txpChat.setText("");
        modelo.clear();

        txpChat.setEnabled(false);
        txtUsuario.setEnabled(true);
        btnConectar.setEnabled(true);
        btnDesconectar.setEnabled(false);
        btnEmpate.setEnabled(false);
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
        txtMensaje.setText("");

        // cambiamos color y mensaje del txtEstado
        txtEstado.setBackground(Color.YELLOW);
        txtEstado.setText(ESTADO_BUSQUEDA);
    }

    /**
     * Método que se invoca cuando el usuario cliente ya ha encontrado una partida
     * y necesita tener habilitados los elementos de tablero y chat correspondientes.
     *
     * @param local indica si el cliente juega con las fichas blancas (true) o las negras (false)
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
        txpChat.setEnabled(true);
        txtMensaje.setEnabled(true);

        // cambiamos color y mensaje del txtEstado
        txtEstado.setBackground(Color.GREEN);
        txtEstado.setText(ESTADO_CON);

        // btnEmpate
        if (pnlTablero.esMiTurno())
            btnEmpate.setEnabled(true);
        else
            btnEmpate.setEnabled(false);

        // iniciamos cronómetro
        hiloCronometro = new HiloCronometro(this, pnlTablero.esMiTurno());
        Thread threadCronometro = new Thread(hiloCronometro);
        threadCronometro.start();
    }

    /**
     * Método que sirve para desconectar al usuario del servidor.
     * Se invoca cuando se pulsa btnDesconectar, o cuando el timeOut de movimiento llega a 0.
     */
    public void desconectar() {
        if (hiloCronometro != null)
            hiloCronometro.apagarCronometro();
        hiloCliente.desconexion();
    }

    /**
     * Método que añade los listeners necesarios a los elementos de la interfaz.
     * Se le llama desde el propio constructor.
     */
    private void addListeners() {
        btnConectar.addActionListener(this);
        btnDesconectar.addActionListener(this);
        btnEmpate.addActionListener(this);
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
            SimpleAttributeSet izquierda = new SimpleAttributeSet();
            StyleConstants.setAlignment(izquierda, StyleConstants.ALIGN_LEFT);
            StyleConstants.setForeground(izquierda, Color.BLACK);

            SimpleAttributeSet dcha = new SimpleAttributeSet();
            StyleConstants.setAlignment(dcha, StyleConstants.ALIGN_RIGHT);
            StyleConstants.setForeground(dcha, Color.BLUE);

            try {
                String txt = m.getTxt();
                if (!txpChat.getText().isEmpty()) {
                    txt = "\n\n" + txt;
                }
                if (enviadoPorMi) {
                    doc.insertString(doc.getLength(), txt, izquierda);
                    doc.setParagraphAttributes(doc.getLength(), 1, izquierda, false);
                } else {
                    doc.insertString(doc.getLength(), txt, dcha);
                    doc.setParagraphAttributes(doc.getLength(), 1, dcha, false);
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            // para que el scroll baje automáticamente
            txpChat.setCaretPosition(doc.getLength());
        } else if (mensaje instanceof MensajeMovimiento) {
            try {
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
                    // distinguimos caso enroque rey-torre
                    if (figuraOrigen instanceof Rey && (Math.abs(posOrigen.getColumna() - posDestino.getColumna()) == 2)) {
                        boolean enroque = false; // false es corto, true es largo
                        int f, cOrigenTorre = 0, cDestinoTorre = 0;
                        Posicion pOrigenTorre, pDestinoTorre;
                        // la fila cambia según si es movimiento nuestro o no
                        if (enviadoPorMi) {
                            f = 0;
                        } else {
                            f = 7;
                        }
                        // definimos la posición de origen y destino de la torre
                        if (posDestino.getColumna() > posOrigen.getColumna()) {
                            cOrigenTorre = 7;
                            if (posOrigen.getColumna() == 4) { // significa movimiento de rey blanco
                                cDestinoTorre = 5;
                                enroque = false;
                            } else if (posOrigen.getColumna() == 3) { // significa movimiento de rey negro
                                cDestinoTorre = 4;
                                enroque = true;
                            }
                        } else if (posDestino.getColumna() < posOrigen.getColumna()) {
                            cOrigenTorre = 0;
                            if (posOrigen.getColumna() == 4) { // significa movimiento de rey blanco
                                cDestinoTorre = 3;
                                enroque = true;
                            } else if (posOrigen.getColumna() == 3) { // significa movimiento de rey negro
                                cDestinoTorre = 2;
                                enroque = false;
                            }
                        }
                        pOrigenTorre = new Posicion(f, cOrigenTorre);
                        pDestinoTorre = new Posicion(f, cDestinoTorre);

                        // movemos figuras y actualizamos jlist
                        if (enroque) {
                            modelo.addElement(jug + " >>> Ejecuta ENROQUE LARGO.");
                        } else {
                            modelo.addElement(jug + " >>> Ejecuta ENROQUE CORTO.");
                        }
                        pnlTablero.moverFigura(pOrigenTorre, pDestinoTorre);
                    } else {
                        modelo.addElement(jug + " >>> Mueve " + figuraOrigen.getNom() + " de " + valoresColumna.charAt(posOrigen.getColumna()) + " - "
                                + filaOrigenAux + " a " + valoresColumna.charAt(posDestino.getColumna()) + " - "
                                + filaDestinoAux + ".");
                    }
                } else {
                    Figura figuraAComer = m.getCasillaDestino().getFigura();
                    if (figuraAComer == null) { // salta cuando peón come el paso
                        Casilla casillaAux;
                        if (enviadoPorMi) {
                            casillaAux = arrayTablero[4][posDestino.getColumna()];
                        } else {
                            casillaAux = arrayTablero[3][posDestino.getColumna()];
                        }
                        figuraAComer = casillaAux.getFigura();
                        // limpiamos dicha casilla
                        casillaAux.getBtnCasilla().setIcon(null);
                        casillaAux.setFigura(null);
                    }
                    modelo.addElement(jug + " >>> Mueve " + figuraOrigen.getNom() + " de " + valoresColumna.charAt(posOrigen.getColumna()) + " - "
                            + filaOrigenAux + " a " + valoresColumna.charAt(posDestino.getColumna()) + " - "
                            + filaDestinoAux + ".");
                    modelo.addElement(jug + " >>> Come " + figuraAComer.getNom() + " rival.");

                    // caso de comer rey
                    if (figuraAComer instanceof Rey) {
                        if (enviadoPorMi) {
                            JOptionPane.showMessageDialog(this,
                                    "¡Enhorabuena! Has ganado.",
                                    "Victoria",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "¡Casi! Otra vez será.",
                                    "Derrota",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        desconectar();
                    }
                }

                // movemos la figura en el tablero
                pnlTablero.moverFigura(posOrigen, posDestino);

                // en caso de promoción de peón
                if (figuraOrigen instanceof Peon && (posDestino.getFila() == 0 || posDestino.getFila() == 7)) {
                    hiloCronometro.pausar();
                    if (enviadoPorMi) {
                        seleccionarFiguraPromocion(posDestino);
                    }
                } else {
                    comprobarMovimiento(jug, posDestino);
                }

            } catch (NullPointerException e) {
            }
        } else if (mensaje instanceof MensajePromocion) {
            MensajePromocion m = (MensajePromocion) mensaje;
            Figura fPromocion = m.getFiguraPromocion();
            Posicion posPromocion = m.getPosDestino();
            pnlTablero.ejecutarPromocion(enviadoPorMi, fPromocion, posPromocion);
            if (!enviadoPorMi) { // reinterpretamos las coordenadas en caso de que promocione el rival
                posPromocion = new Posicion(7 - posPromocion.getFila(), 7 - posPromocion.getColumna());
            }
            modelo.addElement(jug + " >>> Promociona PEÓN a " + fPromocion.getNom() + ".");
            comprobarMovimiento(jug, posPromocion);
        } else if (mensaje instanceof MensajeEmpate) {
            MensajeEmpate m = (MensajeEmpate) mensaje;
            // si se recibe mensaje de solicitud de empate
            if (m.iniciaSolicitud()) {
                hiloCronometro.pausar(); // paramos crono
                if (!enviadoPorMi) {// si no está enviada por mí, nace ese menú
                    modelo.addElement(jug + " >>> Recibe solicitud de EMPATE.");
                    aceptarEmpate();
                } else {
                    modelo.addElement(jug + " >>> Emite solicitud de EMPATE.");
                }
            } else {
                // si se recibe un mensaje en el que se acepta la rendición
                if (m.aceptaTablas()) {
                    hiloCronometro.apagarCronometro();
                    JOptionPane.showMessageDialog(this,
                            "Empate por mutuo acuerdo.",
                            "Empate",
                            JOptionPane.INFORMATION_MESSAGE);
                    desconectar();
                } else {
                    hiloCronometro.resume();
                    modelo.addElement(jug + " >>> Solicitud de empate RECHAZADA.");
                }
            }
        }

    }

    /**
     * Método auxiliar que nos ayuda a interpretar los movimientos, tratando
     * de descubrir si se da jaque, jaque mate, o empate por ahogado.
     *
     * @param jug
     * @param posicion
     */
    private void comprobarMovimiento(String jug, Posicion posicion) {
        // comprobamos si hay jaque
        Posicion jaque = pnlTablero.comprobarJaque(arrayTablero[posicion.getFila()][posicion.getColumna()]);
        if (jaque != null) { // significa que hay jaque y puede haber jaque mate
            boolean jaqueMate = pnlTablero.comprobarFinPartida(jaque, true);
            if (jaqueMate) {
                hiloCronometro.apagarCronometro();
                String mensaje = null;
                String titulo = null;
                if (pnlTablero.esMiTurno()) {
                    mensaje = "¡Jaque Mate! Has ganado.";
                    titulo = "Victoria";
                } else {
                    mensaje = "¡Jaque Mate! Otra vez será.";
                    titulo = "Derrota";
                }
                JOptionPane.showMessageDialog(this,
                        mensaje,
                        titulo,
                        JOptionPane.INFORMATION_MESSAGE);
                desconectar();
                return;
            } else {
                modelo.addElement(jug + " >>> Aviso de JAQUE.");
            }
        } else { // significa que puede haber AHOGADO
            boolean ahogado = pnlTablero.comprobarAhogado();
            if (ahogado) {
                hiloCronometro.apagarCronometro();
                String mensaje = "¡Ahogado! Habéis quedado empate.";
                String titulo = "Empate";
                JOptionPane.showMessageDialog(this,
                        mensaje,
                        titulo,
                        JOptionPane.INFORMATION_MESSAGE);
                desconectar();
                return;
            }
        }

        // cambiamos turno, actualizamos btnEmpate
        pnlTablero.cambiarTurno();
        if (pnlTablero.esMiTurno())
            btnEmpate.setEnabled(true);
        else
            btnEmpate.setEnabled(false);

        // desplacamos jlist hasta abajo
        SwingUtilities.invokeLater(() -> scpRegistro.getVerticalScrollBar().setValue(scpRegistro.getVerticalScrollBar().getMaximum()));


        // acabamos con el actual hiloCronometro e iniciamos uno nuevoo para el rival
        hiloCronometro.apagarCronometro();
        hiloCronometro = new HiloCronometro(this, pnlTablero.esMiTurno());
        Thread threadCronometro = new Thread(hiloCronometro);
        threadCronometro.start();
    }


    /**
     * Método que envía al servidor cuál es la figura a la que quiere el jugador en cuestión promocionar el peón.
     */
    public void seleccionarFiguraPromocion(Posicion posDestino) {
        String[] figuras = {"Alfil", "Caballo", "Reina", "Torre"};
        Figura figuraPromocion = null;
        while (figuraPromocion == null) {
            int opcion = JOptionPane.showOptionDialog(this,
                    "Seleccione la figura a la que desea promocionar:", "Promoción",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, figuras, figuras[0]);
            switch (opcion) {
                case 0:
                    figuraPromocion = new Alfil(true);
                    break;
                case 1:
                    figuraPromocion = new Caballo(true);
                    break;
                case 2:
                    figuraPromocion = new Reina(true);
                    break;
                case 3:
                    figuraPromocion = new Torre(true);
                    break;
                default:
                    figuraPromocion = null;
                    break;
            }
        }

        hiloCliente.enviarMensajePromocion(figuraPromocion, posDestino);
    }

    /**
     * Método que surge cuando se recibe una petición de empate.
     */
    private void aceptarEmpate() {
        String[] posibilidades = {"No", "Sí"};
        Boolean empate = null;
        while (empate == null) {
            int opcion = JOptionPane.showOptionDialog(this, "¿Acepta el empate?", "Tablas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, posibilidades, posibilidades[0]);
            switch (opcion) {
                case 0:
                    empate = false;
                    break;
                case 1:
                    empate = true;
                    break;
                default:
                    empate = null;
                    break;
            }
        }

        enviarMensajeEmpate(empate, false);
    }

    public void enviarMensajeEmpate(boolean empate, boolean inicioSolicitud) {
        hiloCliente.enviarMensajeEmpate(empate, inicioSolicitud);
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
            } else if (getNickname().length() > 15) {
                JOptionPane.showMessageDialog(this,
                        "El nickname puede contener hasta un máximo de 15 carácteres.",
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
            desconectar();
        }

        // Si pulsamos sobre btnEmpate, proponemos tablas
        if (e.getSource().equals(btnEmpate)) {
            enviarMensajeEmpate(true, true);
            btnEmpate.setEnabled(false);
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
