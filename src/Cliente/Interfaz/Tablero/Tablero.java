package Cliente.Interfaz.Tablero;

import Cliente.HiloCliente;
import Cliente.Interfaz.Tablero.Figuras.*;
import Cliente.Interfaz.Tablero.Figuras.Bucle.Alfil;
import Cliente.Interfaz.Tablero.Figuras.Bucle.FiguraBucle;
import Cliente.Interfaz.Tablero.Figuras.Bucle.Reina;
import Cliente.Interfaz.Tablero.Figuras.Bucle.Torre;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Caballo;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Peon;
import Cliente.Interfaz.Tablero.Figuras.NoBucle.Rey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Tablero extends JPanel implements ActionListener {

    // CONSTANTES DE CLASE
    public final static int DIM_TABLERO = 8;
    public final static Dimension DIM_BOTON_TABLERO = new Dimension(65, 65);
    private final static String TORREBLANCA = "Recursos/Figuras/Blancas/torre.png";
    private final static String TORRENEGRA = "Recursos/Figuras/Negras/torre.png";
    private final static String CABALLOBLANCO = "Recursos/Figuras/Blancas/caballo.png";
    private final static String CABALLONEGRO = "Recursos/Figuras/Negras/caballo.png";
    private final static String ALFILBLANCO = "Recursos/Figuras/Blancas/alfil.png";
    private final static String ALFILNEGRO = "Recursos/Figuras/Negras/alfil.png";
    private final static String REYBLANCO = "Recursos/Figuras/Blancas/rey.png";
    private final static String REYNEGRO = "Recursos/Figuras/Negras/rey.png";
    private final static String REINABLANCA = "Recursos/Figuras/Blancas/reina.png";
    private final static String REINANEGRA = "Recursos/Figuras/Negras/reina.png";
    private final static String PEONBLANCO = "Recursos/Figuras/Blancas/peon.png";
    private final static String PEONNEGRO = "Recursos/Figuras/Negras/peon.png";

    // VARIABLES DE INSTANCIA
    private HiloCliente hiloCliente;
    private Casilla[][] arrayTablero;
    private boolean local, miTurno;
    private HashSet<Posicion> hsPosiblesMovimientos;
    private Casilla ultCasillaAux;

    public Tablero() {
        setLayout(new GridLayout(DIM_TABLERO + 1, DIM_TABLERO + 1));
        crearTablero();
    }

    /**
     * Método que crea el tablero y cambia de color las casillas.
     */
    private void crearTablero() {
        arrayTablero = new Casilla[DIM_TABLERO][DIM_TABLERO]; // inicializamos el array de casillas del tablero
        String valoresColumna = " ABCDEFGH";
        Color color = Color.WHITE;
        JButton btn;
        Posicion pos;
        for (int fila = DIM_TABLERO; fila > 0; fila--) { // rellenamos tablero de botones y coordenadas numéricas de las filas
            add(new JLabel(Integer.toString(fila), SwingConstants.CENTER));
            for (int col = 0; col < DIM_TABLERO; col++) {
                pos = new Posicion(fila - 1, col);
                // creamos el botón y le damos unos parámetros
                btn = new JButton();
                btn.setPreferredSize(DIM_BOTON_TABLERO);
                btn.setBackground(color);
                // implementamos el listener y le establecemos como actionComand sus propias coordenadas
                // para que luego desde actionPerformed, se pueda detectar la casilla pulsada, y por tanto,
                // determinar si hay figura o no en dicho botón, actuando en consecuencia
                btn.addActionListener(this);
                btn.setActionCommand(pos.getFila() + "_" + pos.getColumna());
                // añadimos el btn al tablero y al array
                add(btn);
                arrayTablero[fila - 1][col] = new Casilla(btn, pos); // empezamos a dar forma a las casillas del tablero
                // cambiamos color
                if (color.equals(Color.GRAY) && col != 7) {
                    color = Color.WHITE;
                } else if (color.equals(Color.WHITE) && col != 7) {
                    color = Color.GRAY;
                }
            }
        }
        for (int i = 0; i < DIM_TABLERO + 1; i++) { // última fila que indica las coordenadas de las columnas del tablero
            add(new JLabel(Character.toString(valoresColumna.charAt(i)), SwingConstants.CENTER));
        }
    }

    /**
     * Método que da inicio a la partida, colocando las Fichas de inicio en sus sitios correspondientes
     * y seteando el hiloCliente de Tablero.java
     *
     * @param local indica si el cliente juega con fichas blancas(true) o negras (false).
     */
    public void comienzoPartida(HiloCliente hiloCliente, boolean local) {
        this.hiloCliente = hiloCliente;
        this.local = local;
        // preestablecemos por defecto que la partida la empieza el jugador de fichas blancas
        if (local)
            miTurno = true;
        else
            miTurno = false;
        // inicializamos el hsPosiblesMovimientos
        hsPosiblesMovimientos = new HashSet<Posicion>();
        boolean mia = local;
        Image image = null;
        Image imagePeon = null;
        ImageIcon imageIcon = null;
        Figura figura = null, figuraPeon = null;
        int f = 0;
        // rellenamos tablero con todas las fichas
        for (int i = 0; i < 2; i++) {
            for (int col = 0; col < DIM_TABLERO - 1; col++) {
                if (i == 0) { // figuras negras
                    if (local) {
                        f = 7;
                        mia = false;
                    } else {
                        f = 0;
                        mia = true;
                    }
                    imagePeon = new ImageIcon(PEONNEGRO).getImage();
                    switch (col) {
                        case 0:
                            image = new ImageIcon(TORRENEGRA).getImage();
                            break;
                        case 1:
                            image = new ImageIcon(CABALLONEGRO).getImage();
                            break;
                        case 2:
                            image = new ImageIcon(ALFILNEGRO).getImage();
                            break;
                        case 3:
                            if (local) {
                                image = new ImageIcon(REINANEGRA).getImage();
                            } else {
                                image = new ImageIcon(REYNEGRO).getImage();
                            }
                            break;
                        case 4:
                            if (local) {
                                image = new ImageIcon(REYNEGRO).getImage();
                            } else {
                                image = new ImageIcon(REINANEGRA).getImage();
                            }
                            break;
                        default:
                            image = null;
                            break;
                    }
                } else if (i == 1) { // figuras blancas
                    if (local) {
                        f = 0;
                        mia = true;
                    } else {
                        f = 7;
                        mia = false;
                    }
                    imagePeon = new ImageIcon(PEONBLANCO).getImage();
                    switch (col) {
                        case 0:
                            image = new ImageIcon(TORREBLANCA).getImage();
                            break;
                        case 1:
                            image = new ImageIcon(CABALLOBLANCO).getImage();
                            break;
                        case 2:
                            image = new ImageIcon(ALFILBLANCO).getImage();
                            break;
                        case 3:
                            if (local) {
                                image = new ImageIcon(REINABLANCA).getImage();
                            } else {
                                image = new ImageIcon(REYBLANCO).getImage();
                            }
                            break;
                        case 4:
                            if (local) {
                                image = new ImageIcon(REYBLANCO).getImage();
                            } else {
                                image = new ImageIcon(REINABLANCA).getImage();
                            }
                            break;
                        default:
                            image = null;
                            break;
                    }
                }

                // creamos las instancias de figura correspondientes en función de la columna
                switch (col) {
                    case 0:
                        figura = new Torre(mia);
                        break;
                    case 1:
                        figura = new Caballo(mia);
                        break;
                    case 2:
                        figura = new Alfil(mia);
                        break;
                    case 3:
                        if (local) {
                            figura = new Reina(mia);
                        } else {
                            figura = new Rey(mia);
                        }
                        break;
                    case 4:
                        if (local) {
                            figura = new Rey(mia);
                        } else {
                            figura = new Reina(mia);
                        }
                        break;
                    default:
                        figura = null;
                        break;
                }

                if (image != null) {
                    // añadimos torres, caballos, alfiles, reinas y reyes
                    imageIcon = new ImageIcon(image.getScaledInstance(
                            DIM_BOTON_TABLERO.width, DIM_BOTON_TABLERO.height, Image.SCALE_SMOOTH));
                    // definimos el icono de las casillas, así como la figura que las ocupa
                    if (col <= 2) { // comportamiento para torres, caballos y alfiles
                        arrayTablero[f][col].getBtnCasilla().setIcon(imageIcon);
                        arrayTablero[f][col].setFigura(figura);
                        arrayTablero[f][DIM_TABLERO - 1 - col].getBtnCasilla().setIcon(imageIcon);
                        arrayTablero[f][DIM_TABLERO - 1 - col].setFigura(figura);
                    } else { // comportamiento para reyes y reinas
                        arrayTablero[f][col].getBtnCasilla().setIcon(imageIcon);
                        arrayTablero[f][col].setFigura(figura);
                    }

                    // añadimos peones
                    if (col <= 3) { // para que la imagen de un peón no pise a otro
                        imageIcon = new ImageIcon(imagePeon.getScaledInstance(
                                DIM_BOTON_TABLERO.width, DIM_BOTON_TABLERO.height, Image.SCALE_SMOOTH));
                        if (i == 0) {
                            if (local)
                                f = 6; // fila peones negros
                            else
                                f = 1; // fila peones blancos
                        } else {
                            if (local)
                                f = 1; // fila peones blancos
                            else
                                f = 6; // fila peones negros
                        }
                        // definimos el icono de las casillas, así como la figura que las ocupa
                        arrayTablero[f][col].getBtnCasilla().setIcon(imageIcon);
                        arrayTablero[f][DIM_TABLERO - 1 - col].getBtnCasilla().setIcon(imageIcon);
                        figuraPeon = new Peon(mia);
                        arrayTablero[f][col].setFigura(figuraPeon);
                        figuraPeon = new Peon(mia);
                        arrayTablero[f][DIM_TABLERO - 1 - col].setFigura(figuraPeon);
                    }
                }
            }
        }
    }

    public void limpiarTablero() {
        ImageIcon imageIcon = null;
        for (int fila = 0; fila < DIM_TABLERO; fila++) {
            for (int col = 0; col < DIM_TABLERO; col++) {
                arrayTablero[fila][col].getBtnCasilla().setIcon(null);
                arrayTablero[fila][col].setFigura(null);
                arrayTablero[fila][col].descolorearBorde();
            }
        }
    }

    /**
     * Método privado que devuelve el arrayTablero, que comprende
     * únicamente las casillas del tablero.
     *
     * @return arrayTablero
     */
    public Casilla[][] getArrayTablero() {
        return arrayTablero;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (miTurno) {
            int fila = Integer.parseInt(e.getActionCommand().split("_")[0]);
            int col = Integer.parseInt(e.getActionCommand().split("_")[1]);
            Casilla casilla = arrayTablero[fila][col];
            Figura figura = casilla.getFigura();
            // Si la casilla pulsada está ocupada por una figura NUESTRA
            if (figura != null && figura.esMia()) {
                // actualizamos ultCasillaAux
                ultCasillaAux = casilla;
                // si hubiéramos pinchado antes sobre otra opción, deberíamos borrar
                // los viejos posibles movimientos y añadir los nuevos
                if (!hsPosiblesMovimientos.isEmpty()) {
                    for (Posicion p : hsPosiblesMovimientos) {
                        arrayTablero[p.getFila()][p.getColumna()].descolorearBorde();
                    }
                    hsPosiblesMovimientos.clear(); // limpiamos hashset
                }
                // añadimos los posibles movimientos sobre la última figura pinchada
                hsPosiblesMovimientos = figura.getPosiblesMovimientos(new Posicion(fila, col), arrayTablero, false);
                for (Posicion p : hsPosiblesMovimientos) {
                    arrayTablero[p.getFila()][p.getColumna()].colorearBorde();
                }
            } else if (figura == null || !figura.esMia()) {
                // si la casilla pulsada no está ocupada por una figura, o está ocupada por una figura rival,
                // deberemos comprobar si dicha casilla se incluye dentro de hsPosiblesMovimientos,
                // correspondiendo entonces o no con un movimiento
                if (!hsPosiblesMovimientos.isEmpty()) {
                    // en caso de que no haya una figura en la casilla pulsada,
                    // comprobar si es una casilla cuya posición se incluye en hsPosiblesMovimientos
                    // lo que quiere decir que se trata del movimiento de una ficha en el que NO se come
                    for (Posicion p : hsPosiblesMovimientos) {
                        if (p.getFila() == fila && p.getColumna() == col) {
                            // enviamos al servidor un mensaje con el movimiento ANTES de actualizar el tablero
                            if (figura == null) { // se trata de un movimiento en el que no se come
                                hiloCliente.enviarMovimiento(ultCasillaAux, casilla, false);
                            } else { // se trata de un movimiento en el que se come
                                hiloCliente.enviarMovimiento(ultCasillaAux, casilla, true);
                            }
                        }
                        // aprovechamos, con el recorrido del bucle, a descolorear los bordes
                        arrayTablero[p.getFila()][p.getColumna()].descolorearBorde();
                    }
                    // limpiamos hsPosiblesMovimientos y seteamos a null ultCasillaAux
                    hsPosiblesMovimientos.clear();
                    ultCasillaAux = null;
                }
            }
        }
    }

    public void cambiarTurno() {
        if (miTurno) {
            miTurno = false;
        } else {
            miTurno = true;
        }
    }

    public void moverFigura(Posicion posOrigen, Posicion posDestino) {
        Casilla casillaOrigen = arrayTablero[posOrigen.getFila()][posOrigen.getColumna()];
        Casilla casillaDestino = arrayTablero[posDestino.getFila()][posDestino.getColumna()];
        // actualizamos casillaDestino y casillaOrigen
        casillaDestino.getBtnCasilla().setIcon(casillaOrigen.getBtnCasilla().getIcon());
        casillaDestino.setFigura(casillaOrigen.getFigura());
        casillaOrigen.getBtnCasilla().setIcon(null);
        casillaOrigen.setFigura(null);

        // si se tratara de un peón, deberemos setear su primerMovimiento como false
        if (casillaDestino.getFigura() instanceof Peon) {
            Peon peon = (Peon) casillaDestino.getFigura();
            if (peon.esPrimerMovimiento()) {
                peon.setPrimerMovimiento(false);
            }
        }
    }

    /**
     * Metodo privado que comprueba si la figura indicada en la casilla pone en jaque al rey rival.
     *
     * @param casilla
     */
    public Posicion comprobarJaque(Casilla casilla) {
        Figura figura = casilla.getFigura();
        Figura figuraAux;
        int fila, columna;
        Posicion pos = null;
        // limpiamos hsPosiblesMovimientos y detectamos los posibles movimientos de la figura indicada
        hsPosiblesMovimientos.clear();
        hsPosiblesMovimientos = figura.getPosiblesMovimientos(casilla.getPosicion(), arrayTablero, false);
        // recorremos hsPosiblesMovimientos y comprobamos si hay posibilidad de comerse al rey rival.
        for (Posicion posicion : hsPosiblesMovimientos) {
            fila = posicion.getFila();
            columna = posicion.getColumna();
            figuraAux = arrayTablero[fila][columna].getFigura();
            if (figuraAux instanceof Rey) {
                pos = posicion;
                break; // salimos del bucle
            }
        }
        return pos;
    }

    /**
     * Método que comprueba si se hace jaque mate.
     *
     * @param posicion del rey rival
     * @return un booleano que indica si jaque mate o no.
     */
    public boolean comprobarJaqueMate(Posicion posicion) {
        boolean jaqueMate = true;

        // detectamos los posibles movimientos del rey rival
        int filaRey = posicion.getFila();
        int colRey = posicion.getColumna();
        Figura reyRival = arrayTablero[filaRey][colRey].getFigura();
        HashSet<Posicion> hsMovimientosReyRival = reyRival.getPosiblesMovimientos(posicion, arrayTablero, false);
        hsMovimientosReyRival.add(posicion); // añadimos la actual posición del rey, además

        // creamos un HashMap con las Casillas de hsMovimientosReyRival como clave, y una variable array de Posicion
        // como clave que comprende las posiciones de las figuras que pueden llegar a la posición que está de clave.
        // si el tamaño de ese array es de 1, debemos comprobar si la figura en cuestión que puede llegar, puede ser
        // comida previamente por equipo rival desmantelando el JAQUE MATE
        HashMap<Posicion, ArrayList<Casilla>> hmJaqueMate = new HashMap<>();

        Figura figura;
        Posicion pos;
        HashSet<Posicion> hsMovimientosAux = null;
        ArrayList<Casilla> arrayCasillas;
        for (int fila = 0; fila < DIM_TABLERO; fila++) {
            for (int col = 0; col < DIM_TABLERO; col++) {
                figura = arrayTablero[fila][col].getFigura();
                pos = arrayTablero[fila][col].getPosicion();
                if (figura != null && (figura.esMia() != arrayTablero[posicion.getFila()][posicion.getColumna()].getFigura().esMia())) {
                    hsMovimientosAux = figura.getPosiblesMovimientos(pos, arrayTablero, true);
                    // recorremos hsMovimientosAux y hsJaqueMate en busca de coincidencia
                    for (Posicion p : hsMovimientosAux) {
                        for (Posicion p1 : hsMovimientosReyRival) {
                            if (p.getFila() == p1.getFila() && p.getColumna() == p1.getColumna()) {
                                // si no existe un registro para p1, inicializamos arrayCasillas
                                if (hmJaqueMate.get(p1) == null) {
                                    arrayCasillas = new ArrayList<>();
                                } else { // de lo contrario, tomamos el valor del array y deberemos actualizarlo
                                    arrayCasillas = hmJaqueMate.get(p1);
                                }
                                arrayCasillas.add(arrayTablero[fila][col]);
                                hmJaqueMate.put(p1, arrayCasillas);
                            }
                        }
                    }
                }
            }
        }

        // si el tamaño de hmJaqueMate es igual al de hsMovimientosReyRival, cubre todas las posibles posiciones
        // que pueda tomar el rey y aparentemente puede ser jaque mate
        if (hmJaqueMate.size() != hsMovimientosReyRival.size()) {
            jaqueMate = false;
        } else {
            // comprobamos que figuras pueden comerse al rey en su actual posición
            // si el tamaño de dicho array es de 1, debemos comprobar si a esa figura se le puede "taponar" el
            // movimiento y/o, directamente, comérsela para evitar el jaque mate
            // (partimos de la base de que no se pueden taponar 2 movimientos a la vez)
            arrayCasillas = hmJaqueMate.get(posicion);
            if (arrayCasillas.size() == 1) {
                HashSet<Posicion> hsRutaJaque = new HashSet<>();
                Casilla casilla = arrayCasillas.get(0);
                figura = casilla.getFigura();
                pos = casilla.getPosicion();
                // las figuras que se pueden "taponar" son aquellas cuya lógica de movimiento responde a un bucle
                // que les permite avanzar más de una casilla adyacente para comer (Torre, Alfil, Reina)
                if (figura instanceof FiguraBucle) {
                    FiguraBucle figuraBucle = (FiguraBucle) figura;
                    hsRutaJaque = figuraBucle.detectarRutaJaque(pos, arrayTablero);
                } else { // debemos añadir en hsRutaJaque pra las FiguraNoBucle su posición actual
                    hsRutaJaque.add(pos);
                }
                // comprobamos si las figuras rivales pueden evitar el jaquemate buscando coincidencias entre
                // sus posibles movimientos y los de hsMovimientosAux
                for (int fila = 0; fila < DIM_TABLERO; fila++) {
                    for (int col = 0; col < DIM_TABLERO; col++) {
                        if (arrayTablero[fila][col].getFigura() != null
                                && arrayTablero[fila][col].getFigura().esMia() == arrayTablero[posicion.getFila()][posicion.getColumna()].getFigura().esMia()) {
                            figura = arrayTablero[fila][col].getFigura();
                            if (!(figura instanceof Rey)) {
                                for (Posicion p : hsRutaJaque) {
                                    for (Posicion p1 : figura.getPosiblesMovimientos(new Posicion(fila, col), arrayTablero, false)) {
                                        if (p.getFila() == p1.getFila() && p.getColumna() == p1.getColumna()) {
                                            jaqueMate = false;
                                            break;
                                        }
                                    }
                                    if (!jaqueMate) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return jaqueMate;
    }
}
