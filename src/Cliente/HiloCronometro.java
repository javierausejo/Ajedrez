package Cliente;

import Cliente.Interfaz.Interfaz;

public class HiloCronometro implements Runnable {

    // CONSTANTES DE CLASE
    private final static int TIEMPO_TURNO = 30;

    // VARIABLES DE INSTANCIA
    private Interfaz interfaz;
    private boolean esMiTurno;
    private boolean control;
    private boolean pausa;

    public HiloCronometro(Interfaz interfaz, boolean esMiTurno) {
        this.interfaz = interfaz;
        this.esMiTurno = esMiTurno;
        control = true; // variable que controla la vida del hilo
        pausa = false; // variable que bloquea hilo en caso de espera de coronación
                        // o espera respuesta solicitud empate
    }

    @Override
    public void run() {
        int tiempo = TIEMPO_TURNO;
        interfaz.reiniciarTiempos();
        interfaz.setTiempoTurno(esMiTurno, tiempo + "");
        while (control) {
            try {
                Thread.sleep(1000);
                if (!pausa) {
                    if (tiempo != 0) {
                        tiempo -= 1;
                        interfaz.setTiempoTurno(esMiTurno, tiempo + "");
                        if (tiempo == 0) {
                            apagarCronometro();
                            interfaz.desconectar();
                        }
                    }
                } else {
                    synchronized (this) {
                        wait(); // hacemos que no se quede dando vueltas si no tiene nada que hacer
                    }
                }
            } catch (InterruptedException e) { }
        }
    }

    public void apagarCronometro() {
        control = false;
        pausa = true;
    }

    public void pausar() {
        pausa = true;
    }

    public void resume() {
        pausa = false;
        synchronized (this) {
            notify();
        }
    }
}
