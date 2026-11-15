package modelo;

import java.time.LocalDate;

public class Pase {

    private int idPase;
    private Socio socio;
    private int cantEntrada;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private double costo;
    private boolean estado;

    public static final int DIAS_VALIDEZ = 30;
    public static final int[] PAQUETES_ENTRADAS = {8, 12, 20};

    public Pase() {
    }

    public Pase(Socio socio, int cantEntrada, LocalDate fechaInicio, LocalDate fechaFin, double costo, boolean estado) {
        this.socio = socio;
        this.cantEntrada = cantEntrada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costo = costo;
        this.estado = estado;
    }

    public Pase(int idPase, Socio socio, int cantEntrada, LocalDate fechaInicio, LocalDate fechaFin, double costo, boolean estado) {
        this.idPase = idPase;
        this.socio = socio;
        this.cantEntrada = cantEntrada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

    public boolean estaVigente(java.time.LocalDate dia) {
        return estado
                && cantEntrada > 0
                && !dia.isBefore(fechaInicio)
                && !dia.isAfter(fechaFin);
    }

    public static boolean esPaqueteValido(int entradas) {
        for (int paquete : PAQUETES_ENTRADAS) {
            if (paquete == entradas) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Pase{" + "idPase=" + idPase + ", socio=" + socio + ", cantEntrada=" + cantEntrada + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", costo=" + costo + ", estado=" + estado + '}';
    }

}
