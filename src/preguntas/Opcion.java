package preguntas;

public class Opcion {
    private String enunciado;
    private boolean correcta;

    public Opcion(String enunciado, boolean correcta) {
        this.enunciado = enunciado;
        this.correcta = correcta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    @Override
    public String toString() {
        return enunciado;
    }


}
