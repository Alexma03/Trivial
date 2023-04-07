package users;

import java.util.Date;

public class Partida {
    private Date date;
    private int puntuacion;
    private Player player;

    public int sumarPunto(int puntos){
        this.puntuacion += puntos;
        return this.puntuacion;
    }
}
