package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Pase;
import modelo.Socio;
import java.time.LocalDate;
import modelo.ReglaNegocioException;

public class PaseData {

    private final Connection cn;

    public PaseData(Connection cn) {
        this.cn = cn;
    }

    public boolean alta(Pase pase) throws SQLException, ReglaNegocioException {
        if (pase.getSocio() == null || pase.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("El pase debe tener un socio con id valido.");
        }
        validar(pase, 0);
        String sql = "INSERT INTO pase (id_Socio, cantidadEntradas, fecha_Inicio, fecha_Fin, costo, Estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pase.getSocio().getIdSocio());
            ps.setInt(2, pase.getCantEntrada());
            ps.setDate(3, Date.valueOf(pase.getFechaInicio()));
            ps.setDate(4, Date.valueOf(pase.getFechaFin()));
            ps.setDouble(5, pase.getCosto());
            ps.setBoolean(6, pase.isEstado());

            if (ps.executeUpdate() == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pase.setIdPase(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public Pase buscar(int idPase) throws SQLException {
        String sql = selectBase() + " WHERE p.id_Pase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPase);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Pase> listar() throws SQLException {
        return listarPorEstado(null);
    }

    public List<Pase> listarActivos() throws SQLException {
        return listarPorEstado(1);
    }

    private List<Pase> listarPorEstado(Integer estado) throws SQLException {
        String sql = selectBase();
        if (estado != null) {
            sql += " WHERE p.Estado = ?";
        }
        sql += " ORDER BY p.fecha_Inicio, p.id_Pase";

        List<Pase> pases = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            if (estado != null) {
                ps.setInt(1, estado);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pases.add(mapear(rs));
                }
            }
        }
        return pases;
    }

    public boolean actualizar(Pase pase) throws SQLException, ReglaNegocioException {
        if (pase.getSocio() == null || pase.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("El pase debe tener un socio con id valido.");
        }
        validar(pase, pase.getIdPase());
        String sql = "UPDATE pase SET id_Socio = ?, cantidadEntradas = ?, fecha_Inicio = ?, fecha_Fin = ?, costo = ?, Estado = ? WHERE id_Pase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, pase.getSocio().getIdSocio());
            ps.setInt(2, pase.getCantEntrada());
            ps.setDate(3, Date.valueOf(pase.getFechaInicio()));
            ps.setDate(4, Date.valueOf(pase.getFechaFin()));
            ps.setDouble(5, pase.getCosto());
            ps.setBoolean(6, pase.isEstado());
            ps.setInt(7, pase.getIdPase());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean bajaLogica(int idPase) throws SQLException {
        return cambiarEstado(idPase, false);
    }

    public boolean altaLogica(int idPase) throws SQLException, ReglaNegocioException {
        Pase pase = buscar(idPase);
        if (pase == null) {
            return false;
        }
        pase.setEstado(true);
        validar(pase, idPase);
        return cambiarEstado(idPase, true);
    }

    private boolean cambiarEstado(int idPase, boolean estado) throws SQLException {
        String sql = "UPDATE pase SET Estado = ? WHERE id_Pase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, estado);
            ps.setInt(2, idPase);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean borrar(int idPase) throws SQLException {
        String sql = "DELETE FROM pase WHERE id_Pase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPase);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                return false;
            }
            throw e;
        }
    }

    private String selectBase() {
        return "SELECT p.id_Pase, p.cantidadEntradas, p.fecha_Inicio, p.fecha_Fin, p.costo, p.Estado, "
                + "s.id_Socio, s.dni AS SocioDni, s.Nombre AS SocioNombre, s.Apellido AS SocioApellido, "
                + "s.Edad AS SocioEdad, s.Correo AS SocioCorreo, s.telefono AS SocioTelefono, "
                + "s.Estado AS SocioEstado "
                + "FROM pase p JOIN socio s ON p.id_Socio = s.id_Socio";
    }

    private Pase mapear(ResultSet rs) throws SQLException {
        Socio socio = new Socio();
        socio.setIdSocio(rs.getInt("id_Socio"));
        socio.setDni(rs.getInt("SocioDni"));
        socio.setNombre(rs.getString("SocioNombre"));
        socio.setApellido(rs.getString("SocioApellido"));
        socio.setEdad(rs.getInt("SocioEdad"));
        socio.setCorreo(rs.getString("SocioCorreo"));
        socio.setTelefono(rs.getString("SocioTelefono"));
        socio.setEstado(rs.getBoolean("SocioEstado"));

        Pase pase = new Pase();
        pase.setIdPase(rs.getInt("id_Pase"));
        pase.setSocio(socio);
        pase.setCantEntrada(rs.getInt("cantidadEntradas"));
        pase.setFechaInicio(rs.getDate("fecha_Inicio").toLocalDate());
        pase.setFechaFin(rs.getDate("fecha_Fin").toLocalDate());
        pase.setCosto(rs.getDouble("costo"));
        pase.setEstado(rs.getBoolean("Estado"));
        return pase;
    }
    
    private void validar(Pase pase, int excluirId) throws SQLException, ReglaNegocioException {
        if (pase.getSocio() == null || pase.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("El pase debe tener un socio con id valido.");
        }
        
        if (pase.getFechaInicio() == null) {
            throw new ReglaNegocioException("La fecha de inicio es obligatoria.");
        }
        
        if (pase.getCantEntrada() <= 0) {
            throw new ReglaNegocioException("La cantidad de entradas debe ser mayor que cero.");
        }
        
        if (pase.getCosto() < 0) {
            throw new ReglaNegocioException("El costo no puede ser negativo.");
        }
        
        pase.setFechaFin(pase.getFechaInicio().plusDays(Pase.DIAS_VALIDEZ));
        
        if (pase.isEstado()) {
            if (!socioActivo(pase.getSocio().getIdSocio())) {
                throw new ReglaNegocioException("El socio no existe o está dado de baja.");
            }
            
            String sql = "SELECT COUNT(*) FROM pase WHERE id_Socio = ? AND Estado = 1 AND id_Pase <> ? "
                    + "AND fecha_Inicio <= ? AND fecha_Fin >= ?";
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, pase.getSocio().getIdSocio());
                ps.setInt(2, excluirId);
                ps.setDate(3, Date.valueOf(pase.getFechaFin()));
                ps.setDate(4, Date.valueOf(pase.getFechaInicio()));
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        throw new ReglaNegocioException(
                                "El socio ya tiene un pase activo que se superpone con esas fechas.");
                    }
                }
            }
        }
    }
    
    private boolean socioActivo(int idSocio) throws SQLException {
        String sql = "SELECT Estado FROM socio WHERE id_Socio = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        }
    }
    
    public Pase buscarVigente(int idSocio, LocalDate dia) throws SQLException {
        String sql = selectBase()
                + " WHERE p.id_Socio = ? AND p.Estado = 1 AND p.cantidadEntradas > 0"
                + " AND ? BETWEEN p.fecha_Inicio AND p.fecha_Fin"
                + " ORDER BY p.fecha_Inicio DESC LIMIT 1";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ps.setDate(2, Date.valueOf(dia));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }
    
    public boolean descontarEntrada(int idPase) throws SQLException {
        String restar = "UPDATE pase SET cantidadEntradas = cantidadEntradas - 1 WHERE id_Pase = ? AND cantidadEntradas > 0";
        try (PreparedStatement ps = cn.prepareStatement(restar)) {
            ps.setInt(1, idPase);
            if (ps.executeUpdate() == 0) {
                return false;
            }
        }
        String cerrar = "UPDATE pase SET Estado = 0 WHERE id_Pase = ? AND cantidadEntradas <= 0";
        try (PreparedStatement ps = cn.prepareStatement(cerrar)) {
            ps.setInt(1, idPase);
            ps.executeUpdate();
        }
        return true;
    }
    
    public boolean devolverEntrada(int idSocio, LocalDate fecha) throws SQLException {
        String sql = "UPDATE pase SET "
                + "Estado = IF(cantidadEntradas = 0 AND fecha_Fin >= ?, 1, Estado), "
                + "cantidadEntradas = cantidadEntradas + 1 "
                + "WHERE id_Pase = (SELECT id_Pase FROM (SELECT id_Pase FROM pase "
                + "WHERE id_Socio = ? AND ? BETWEEN fecha_Inicio AND fecha_Fin "
                + "ORDER BY fecha_Inicio DESC LIMIT 1) AS ultimo)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.setInt(2, idSocio);
            ps.setDate(3, Date.valueOf(fecha));
            return ps.executeUpdate() > 0;
        }
    }
    
    public int cerrarPasesVencidos(LocalDate hoy) throws SQLException {
        String sql = "UPDATE pase SET Estado = 0 WHERE Estado = 1 AND fecha_Fin < ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(hoy));
            return ps.executeUpdate();
        }
    }
}
