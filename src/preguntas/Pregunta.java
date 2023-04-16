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

    /**
     * Método que devuelve la opción correcta de la pregunta
     * @return String correcta
     */
    public String getOpcionCorrecta() {
        String correcta = "";
        for (Opcion opcion : opciones) {
            if (opcion.isCorrecta()) {
                correcta = opcion.getEnunciado();
            }
        }
        return correcta;
    }

    public boolean esCorrecta(int indice) {
        return opciones[indice].isCorrecta();
    }
}
