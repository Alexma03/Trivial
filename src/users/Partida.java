package users;

import java.util.Date;

public class Partida {
    private final Date date;
    private int puntuacion;
    private final Player player;

    public Partida(Date date, int puntuacion, Player player) {
        this.date = date;
        this.puntuacion = puntuacion;
        this.player = player;
    }

    public int sumarPunto(int puntos){
        this.puntuacion += puntos;
        return this.puntuacion;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "date = " + date +
                ", puntuacion = " + puntuacion +
                ", player = " + player +
                '}';
    }
}
