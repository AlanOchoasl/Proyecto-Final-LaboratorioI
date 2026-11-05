package modelo;

public class Entrenador {
    private int idEntrenador;
    private String nombre;
    private String apellido;
    private String dni;
    private String especialidad;
    private boolean estado;

    public Entrenador() {
    }

    public Entrenador(String nombre, String apellido, String dni, String especialidad, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Entrenador(int idEntrenador, String nombre, String apellido, String dni, String especialidad, boolean estado) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return apellido + ", " + nombre + " (" + especialidad + ")";
    }
    
    
}
