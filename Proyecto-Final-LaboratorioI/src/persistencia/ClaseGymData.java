package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.ClaseGym;
import modelo.Entrenador;

public class ClaseGymData {

    private final Connection cn;

    public ClaseGymData(Connection cn) {
        this.cn = cn;
    }

    public boolean alta(ClaseGym clase) throws SQLException {
        if (clase.getEntrenador() == null || clase.getEntrenador().getIdEntrenador() <= 0) {
            throw new IllegalArgumentException("La clase debe tener un entrenador con id valido.");
        }
        String sql = "INSERT INTO clasegym (Disciplina, id_Entrenador, Horario, Capacidad, Estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, clase.getDisciplina());
            ps.setInt(2, clase.getEntrenador().getIdEntrenador());
            ps.setTime(3, Time.valueOf(clase.getHorario()));
            ps.setInt(4, clase.getCapacidad());
            ps.setBoolean(5, clase.isEstado());

            if (ps.executeUpdate() == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    clase.setIdClase(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public ClaseGym buscar(int idClase) throws SQLException {
        String sql = selectBase() + " WHERE c.id_Clase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idClase);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<ClaseGym> listar() throws SQLException {
        return listarPorEstado(null);
    }

    public List<ClaseGym> listarActivos() throws SQLException {
        return listarPorEstado(1);
    }

    private List<ClaseGym> listarPorEstado(Integer estado) throws SQLException {
        String sql = selectBase();
        if (estado != null) {
            sql += " WHERE c.Estado = ?";
        }
        sql += " ORDER BY c.Horario, c.Disciplina";

        List<ClaseGym> clases = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            if (estado != null) {
                ps.setInt(1, estado);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clases.add(mapear(rs));
                }
            }
        }
        return clases;
    }

    public boolean actualizar(ClaseGym clase) throws SQLException {
        if (clase.getEntrenador() == null || clase.getEntrenador().getIdEntrenador() <= 0) {
            throw new IllegalArgumentException("La clase debe tener un entrenador con id valido.");
        }
        String sql = "UPDATE clasegym SET Disciplina = ?, id_Entrenador = ?, Horario = ?, Capacidad = ?, Estado = ? WHERE id_Clase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, clase.getDisciplina());
            ps.setInt(2, clase.getEntrenador().getIdEntrenador());
            ps.setTime(3, Time.valueOf(clase.getHorario()));
            ps.setInt(4, clase.getCapacidad());
            ps.setBoolean(5, clase.isEstado());
            ps.setInt(6, clase.getIdClase());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean bajaLogica(int idClase) throws SQLException {
        return cambiarEstado(idClase, false);
    }

    public boolean altaLogica(int idClase) throws SQLException {
        return cambiarEstado(idClase, true);
    }

    private boolean cambiarEstado(int idClase, boolean estado) throws SQLException {
        String sql = "UPDATE clasegym SET Estado = ? WHERE id_Clase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setBoolean(1, estado);
            ps.setInt(2, idClase);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean borrar(int idClase) throws SQLException {
        String sql = "DELETE FROM clasegym WHERE id_Clase = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idClase);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                return false;
            }
            throw e;
        }
    }

    private String selectBase() {
        return "SELECT c.id_Clase, c.Disciplina, c.Horario, c.Capacidad, c.Estado, "
                + "e.id_Entrenador, e.Dni AS EntrenadorDni, e.Nombre AS EntrenadorNombre, "
                + "e.Apellido AS EntrenadorApellido, e.Especialidad AS EntrenadorEspecialidad, "
                + "e.Estado AS EntrenadorEstado "
                + "FROM clasegym c JOIN entrenador e ON c.id_Entrenador = e.id_Entrenador";
    }

    private ClaseGym mapear(ResultSet rs) throws SQLException {
        Entrenador entrenador = new Entrenador();
        entrenador.setIdEntrenador(rs.getInt("id_Entrenador"));
        entrenador.setDni(rs.getString("EntrenadorDni"));
        entrenador.setNombre(rs.getString("EntrenadorNombre"));
        entrenador.setApellido(rs.getString("EntrenadorApellido"));
        entrenador.setEspecialidad(rs.getString("EntrenadorEspecialidad"));
        entrenador.setEstado(rs.getBoolean("EntrenadorEstado"));

        ClaseGym clase = new ClaseGym();
        clase.setIdClase(rs.getInt("id_Clase"));
        clase.setDisciplina(rs.getString("Disciplina"));
        clase.setEntrenador(entrenador);
        clase.setHorario(rs.getTime("Horario").toLocalTime());
        clase.setCapacidad(rs.getInt("Capacidad"));
        clase.setEstado(rs.getBoolean("Estado"));
        return clase;
    }
}
