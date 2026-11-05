package modelo;

public class Socio {
    private int idSocio;
    private String nombre;
    private String apellido;
    private String dni;
    private int edad;
    private String correo;
    private int telefono;
    private boolean estado;

    public Socio() {
    }

    public Socio(String nombre, String apellido, String dni, int edad, String correo, int telefono, boolean estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.estado = estado;
    }

    public Socio(int idSocio, String nombre, String apellido, String dni, int edad, String correo, int telefono, boolean estado) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.estado = estado;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return apellido + ", " + nombre + " (DNI " + dni + ")";
    }
    
    
}
