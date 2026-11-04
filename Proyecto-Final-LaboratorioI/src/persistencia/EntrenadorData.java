package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Entrenador;

public class EntrenadorData {

    private final Connection cn;

    public EntrenadorData(Connection cn) {
        this.cn = cn;
    }

    public boolean alta(Entrenador entrenador) throws SQLException {
        String sql = "INSERT INTO entrenador (Dni, Nombre, Apellido, Especialidad, Estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, String.valueOf(entrenador.getDni()));
            ps.setString(2, entrenador.getNombre());
            ps.setString(3, entrenador.getApellido());
            ps.setString(4, entrenador.getEspecialidad());
            ps.setBoolean(5, entrenador.isEstado());

            if (ps.executeUpdate() == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entrenador.setIdEntrenador(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public Entrenador buscar(int idEntrenador) throws SQLException {
        String sql = "SELECT id_Entrenador, Dni, Nombre, Apellido, Especialidad, Estado FROM entrenador WHERE id_Entrenador = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public Entrenador buscarPorDni(String dni) throws SQLException {
        String sql = "SELECT id_Entrenador, Dni, Nombre, Apellido, Especialidad, Estado FROM entrenador WHERE Dni = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Entrenador> listar() throws SQLException {
        return listarPorEstado(null);
    }

    public List<Entrenador> listarActivos() throws SQLException {
        return listarPorEstado(1);
    }

    private List<Entrenador> listarPorEstado(Integer estado) throws SQLException {
        String sql = "SELECT id_Entrenador, Dni, Nombre, Apellido, Especialidad, Estado FROM entrenador";
        if (estado != null) {
            sql += " WHERE Estado = ?";
        }
        sql += " ORDER BY Apellido, Nombre";

        List<Entrenador> entrenadores = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            if (estado != null) {
                ps.setInt(1, estado);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entrenadores.add(mapear(rs));
                }
            }
        }
        return entrenadores;
    }

    public boolean actualizar(Entrenador entrenador) throws SQLException {
        String sql = "UPDATE entrenador SET Dni = ?, Nombre = ?, Apellido = ?, Especialidad = ?, Estado = ? WHERE id_Entrenador = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(entrenador.getDni()));
            ps.setString(2, entrenador.getNombre());
            ps.setString(3, entrenador.getApellido());
            ps.setString(4, entrenador.getEspecialidad());
            ps.setBoolean(5, entrenador.isEstado());
            ps.setInt(6, entrenador.getIdEntrenador());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean bajaLogica(int idEntrenador) throws SQLException {
        return cambiarEstado(idEntrenador, false);
    }

    public boolean altaLogica(int idEntrenador) throws SQLException {
        return cambiarEstado(idEntrenador, true);
    }

    private boolean cambiarEstado(int idEntrenador, boolean estado) throws SQLException {
        String sql = "UPDATE entrenador SET Estado = ? WHERE id_Entrenador = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, estado);
            ps.setInt(2, idEntrenador);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean borrar(int idEntrenador) throws SQLException {
        String sql = "DELETE FROM entrenador WHERE id_Entrenador = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idEntrenador);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                return false;
            }
            throw e;
        }
    }

    private Entrenador mapear(ResultSet rs) throws SQLException {
        Entrenador entrenador = new Entrenador();
        entrenador.setIdEntrenador(rs.getInt("id_Entrenador"));
        entrenador.setDni(rs.getInt("Dni"));
        entrenador.setNombre(rs.getString("Nombre"));
        entrenador.setApellido(rs.getString("Apellido"));
        entrenador.setEspecialidad(rs.getString("Especialidad"));
        entrenador.setEstado(rs.getBoolean("Estado"));
        return entrenador;
    }
}
