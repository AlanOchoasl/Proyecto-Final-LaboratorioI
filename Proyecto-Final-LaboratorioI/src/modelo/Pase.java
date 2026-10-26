package modelo;

import java.time.LocalDate;

public class Pase {
    private int idPase;
    private Socio socio;
    private int cantEntrada;
    private LocalDate fechaOut;
    private LocalDate fechaIn;
    private double costo;
    private boolean estado;

    public Pase() {
    }

    public Pase(Socio socio, int cantEntrada, LocalDate fechaOut, LocalDate fechaIn, double costo, boolean estado) {
        this.socio = socio;
        this.cantEntrada = cantEntrada;
        this.fechaOut = fechaOut;
        this.fechaIn = fechaIn;
        this.costo = costo;
        this.estado = estado;
    }

    public Pase(int idPase, Socio socio, int cantEntrada, LocalDate fechaOut, LocalDate fechaIn, double costo, boolean estado) {
        this.idPase = idPase;
        this.socio = socio;
        this.cantEntrada = cantEntrada;
        this.fechaOut = fechaOut;
        this.fechaIn = fechaIn;
        this.costo = costo;
        this.estado = estado;
    }

    public int getIdPase() {
        return idPase;
    }

    public void setIdPase(int idPase) {
        this.idPase = idPase;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public int getCantEntrada() {
        return cantEntrada;
    }

    public void setCantEntrada(int cantEntrada) {
        this.cantEntrada = cantEntrada;
    }

    public LocalDate getFechaOut() {
        return fechaOut;
    }

    public void setFechaOut(LocalDate fechaOut) {
        this.fechaOut = fechaOut;
    }

    public LocalDate getFechaIn() {
        return fechaIn;
    }

    public void setFechaIn(LocalDate fechaIn) {
        this.fechaIn = fechaIn;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pase{" + "idPase=" + idPase + ", socio=" + socio + ", cantEntrada=" + cantEntrada + ", fechaOut=" + fechaOut + ", fechaIn=" + fechaIn + ", costo=" + costo + ", estado=" + estado + '}';
    }
    
    
    
}
