package persistencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Asistencia;
import modelo.ClaseGym;
import modelo.Entrenador;
import modelo.Socio;

public class AsistenciaData {

    private final Connection cn;

    public AsistenciaData(Connection cn) {
        this.cn = cn;
    }

    public boolean alta(Asistencia asistencia) throws SQLException {
        if (asistencia.getSocio() == null || asistencia.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("La asistencia debe tener un socio con id valido.");
        }
        if (asistencia.getClase() == null || asistencia.getClase().getIdClase() <= 0) {
            throw new IllegalArgumentException("La asistencia debe tener una clase con id valido.");
        }
        String sql = "INSERT INTO asistencia (id_Socio, id_Clase, fecha_Asistencia) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, asistencia.getSocio().getIdSocio());
            ps.setInt(2, asistencia.getClase().getIdClase());
            ps.setDate(3, Date.valueOf(asistencia.getFecha()));

            if (ps.executeUpdate() == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    asistencia.setIdAsistencia(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public Asistencia buscar(int idAsistencia) throws SQLException {
        String sql = selectBase() + " WHERE a.id_Asistencia = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idAsistencia);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Asistencia> listar() throws SQLException {
        String sql = selectBase() + " ORDER BY a.fecha_Asistencia, a.id_Asistencia";
        List<Asistencia> asistencias = new ArrayList<>();
        try (PreparedStatement ps = cn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                asistencias.add(mapear(rs));
            }
        }
        return asistencias;
    }

    public boolean actualizar(Asistencia asistencia) throws SQLException {
        if (asistencia.getSocio() == null || asistencia.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("La asistencia debe tener un socio con id valido.");
        }
        if (asistencia.getClase() == null || asistencia.getClase().getIdClase() <= 0) {
            throw new IllegalArgumentException("La asistencia debe tener una clase con id valido.");
        }
        String sql = "UPDATE asistencia SET id_Socio = ?, id_Clase = ?, fecha_Asistencia = ? WHERE id_Asistencia = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, asistencia.getSocio().getIdSocio());
            ps.setInt(2, asistencia.getClase().getIdClase());
            ps.setDate(3, Date.valueOf(asistencia.getFecha()));
            ps.setInt(4, asistencia.getIdAsistencia());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean borrar(int idAsistencia) throws SQLException {
        String sql = "DELETE FROM asistencia WHERE id_Asistencia = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idAsistencia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) {
                return false;
            }
            throw e;
        }
    }

    private String selectBase() {
        return "SELECT a.id_Asistencia, a.fecha_Asistencia, "
                + "s.id_Socio, s.dni AS SocioDni, s.Nombre AS SocioNombre, s.Apellido AS SocioApellido, "
                + "s.Edad AS SocioEdad, s.Correo AS SocioCorreo, s.telefono AS SocioTelefono, "
                + "s.Estado AS SocioEstado, "
                + "c.id_Clase, c.Disciplina, c.Horario, c.Capacidad, c.Estado AS ClaseEstado, "
                + "e.id_Entrenador, e.Dni AS EntrenadorDni, e.Nombre AS EntrenadorNombre, "
                + "e.Apellido AS EntrenadorApellido, e.Especialidad AS EntrenadorEspecialidad, "
                + "e.Estado AS EntrenadorEstado "
                + "FROM asistencia a "
                + "JOIN socio s ON a.id_Socio = s.id_Socio "
                + "JOIN clasegym c ON a.id_Clase = c.id_Clase "
                + "JOIN entrenador e ON c.id_Entrenador = e.id_Entrenador";
    }

    private Asistencia mapear(ResultSet rs) throws SQLException {
        Entrenador entrenador = new Entrenador();
        entrenador.setIdEntrenador(rs.getInt("id_Entrenador"));
        entrenador.setDni(rs.getInt("EntrenadorDni"));
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
        clase.setEstado(rs.getBoolean("ClaseEstado"));

        Socio socio = new Socio();
        socio.setIdSocio(rs.getInt("id_Socio"));
        socio.setDni(rs.getInt("SocioDni"));
        socio.setNombre(rs.getString("SocioNombre"));
        socio.setApellido(rs.getString("SocioApellido"));
        socio.setEdad(rs.getInt("SocioEdad"));
        socio.setCorreo(rs.getString("SocioCorreo"));
        socio.setTelefono(rs.getInt("SocioTelefono"));
        socio.setEstado(rs.getBoolean("SocioEstado"));

        Asistencia asistencia = new Asistencia();
        asistencia.setIdAsistencia(rs.getInt("id_Asistencia"));
        asistencia.setSocio(socio);
        asistencia.setClase(clase);
        asistencia.setFecha(rs.getDate("fecha_Asistencia").toLocalDate());
        return asistencia;
    }
}
