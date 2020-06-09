package Cliente.Interfaz.Sonidos;

import java.applet.AudioClip;

public class Sonidos {

    // CONSTANTES DE CLASE
    private final static String MOV_FIGURA = "movFigura.wav";
    private final static String JAQUE = "jaque.wav";
    private final static String FIN = "fin.wav";
    private final static String SOLICITUDEMPATE = "empate.wav";

    // VARIABLES DE INSTANCIA
    private AudioClip sonido;

    public void reproducirMovFigura() {
        sonido = java.applet.Applet.newAudioClip(Sonidos.class.getResource(MOV_FIGURA));
        sonido.play();
    }

    public void reproducirJaque() {
        sonido = java.applet.Applet.newAudioClip(Sonidos.class.getResource(JAQUE));
        sonido.play();
    }

    public void reproducirFin() {
        sonido = java.applet.Applet.newAudioClip(Sonidos.class.getResource(FIN));
        sonido.play();
    }

    public void reproducirSolicitudEmpate() {
        sonido = java.applet.Applet.newAudioClip(Sonidos.class.getResource(SOLICITUDEMPATE));
        sonido.play();
    }
}