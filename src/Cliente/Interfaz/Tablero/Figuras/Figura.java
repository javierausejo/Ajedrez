package Cliente.Interfaz.Tablero.Figuras;

import Cliente.Interfaz.Tablero.Casilla;
import Cliente.Interfaz.Tablero.Posicion;

import java.io.Serializable;
import java.util.HashSet;

public abstract class Figura implements Serializable {

    // VARIABLES DE INSTANCIA
    private String nom;
    private boolean mia;

    public Figura(String nom, boolean mia) {
        this.nom = nom;
        this.mia = mia;
    }

    /**
     * Método abstracto que devuelve un hashset de instancias Posicion.java con los
     * posibles movimientos que puede hacer una figura en una posición determinada
     * del tablero.
     *
     * @param posicion     la posición actual de la figura
     * @param arrayTablero el estado del tablero
     * @param detectarJaqueMate si hay que detectar jaque mate o no el comportamiento cambia según figuras
     * @return hashset de instancias Posicion.java
     */
    public abstract HashSet<Posicion> getPosiblesMovimientos(Posicion posicion,
                                                             Casilla[][] arrayTablero,
                                                             boolean detectarJaqueMate);

    public String getNom() {
        return nom;
    }

    public boolean esMia() {
        return mia;
    }
}
