package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Pase;
import modelo.Socio;

public class PaseData {

    private final Connection cn;

    public PaseData(Connection cn) {
        this.cn = cn;
    }

    public boolean alta(Pase pase) throws SQLException {
        if (pase.getSocio() == null || pase.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("El pase debe tener un socio con id valido.");
        }
        String sql = "INSERT INTO pase (id_Socio, cantidadEntradas, fecha_Inicio, fecha_Fin, costo, Estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pase.getSocio().getIdSocio());
            ps.setInt(2, pase.getCantEntrada());
            ps.setDate(3, Date.valueOf(pase.getFechaOut()));
            ps.setDate(4, Date.valueOf(pase.getFechaIn()));
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

    public boolean actualizar(Pase pase) throws SQLException {
        if (pase.getSocio() == null || pase.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("El pase debe tener un socio con id valido.");
        }
        String sql = "UPDATE pase SET id_Socio = ?, cantidadEntradas = ?, fecha_Inicio = ?, fecha_Fin = ?, costo = ?, Estado = ? WHERE id_Pase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, pase.getSocio().getIdSocio());
            ps.setInt(2, pase.getCantEntrada());
            ps.setDate(3, Date.valueOf(pase.getFechaOut()));
            ps.setDate(4, Date.valueOf(pase.getFechaIn()));
            ps.setDouble(5, pase.getCosto());
            ps.setBoolean(6, pase.isEstado());
            ps.setInt(7, pase.getIdPase());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean bajaLogica(int idPase) throws SQLException {
        return cambiarEstado(idPase, false);
    }

    public boolean altaLogica(int idPase) throws SQLException {
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
        socio.setTelefono(rs.getInt("SocioTelefono"));
        socio.setEstado(rs.getBoolean("SocioEstado"));

        Pase pase = new Pase();
        pase.setIdPase(rs.getInt("id_Pase"));
        pase.setSocio(socio);
        pase.setCantEntrada(rs.getInt("cantidadEntradas"));
        pase.setFechaOut(rs.getDate("fecha_Inicio").toLocalDate());
        pase.setFechaIn(rs.getDate("fecha_Fin").toLocalDate());
        pase.setCosto(rs.getDouble("costo"));
        pase.setEstado(rs.getBoolean("Estado"));
        return pase;
    }
}
