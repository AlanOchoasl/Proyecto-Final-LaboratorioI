package modelo;

import java.time.LocalTime;

public class ClaseGym {
    private int idClase;
    private String disciplina;
    private Entrenador entrenador;
    private LocalTime Horario;
    private int capacidad;
    private boolean estado;

    public ClaseGym() {
    }

    public ClaseGym(String disciplina, Entrenador entrenador, LocalTime Horario, int capacidad, boolean estado) {
        this.disciplina = disciplina;
        this.entrenador = entrenador;
        this.Horario = Horario;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public ClaseGym(int idClase, String disciplina, Entrenador entrenador, LocalTime Horario, int capacidad, boolean estado) {
        this.idClase = idClase;
        this.disciplina = disciplina;
        this.entrenador = entrenador;
        this.Horario = Horario;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public LocalTime getHorario() {
        return Horario;
    }

    public void setHorario(LocalTime Horario) {
        this.Horario = Horario;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ClaseGym{" + "idClase=" + idClase + ", entrenador=" + entrenador + '}';
    }
    
    
}
