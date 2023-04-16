package preguntas;

public class Pregunta {
    private String pregunta;
    private Opcion[] opciones;

    public Pregunta(String pregunta, Opcion[] opciones) {
        this.pregunta = pregunta;
        this.opciones = opciones;
    }

    public String getPregunta() {
        return pregunta;
    }

    public Opcion[] getOpciones() {
        return opciones;
    }

    public String getOpcionCorrecta() {
        return null;
    }

    public boolean esCorrecta(int indice) {
        return false;
    }
}
