package modelo;

import java.time.LocalDate;

public class Asistencia {
    private int idAsistencia;
    private Socio socio;
    private ClaseGym clase;
    private LocalDate fecha;

    public Asistencia() {
    }

    public Asistencia(Socio socio, ClaseGym clase, LocalDate fecha) {
        this.socio = socio;
        this.clase = clase;
        this.fecha = fecha;
    }

    public Asistencia(int idAsistencia, Socio socio, ClaseGym clase, LocalDate fecha) {
        this.idAsistencia = idAsistencia;
        this.socio = socio;
        this.clase = clase;
        this.fecha = fecha;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public ClaseGym getClase() {
        return clase;
    }

    public void setClase(ClaseGym clase) {
        this.clase = clase;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Asistencia{" + "socio=" + socio + ", clase=" + clase + ", fecha=" + fecha + '}';
    }
    
    
}
