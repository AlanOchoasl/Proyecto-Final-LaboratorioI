package persistencia;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.Asistencia;
import modelo.ClaseGym;
import modelo.Entrenador;
import modelo.Socio;
import modelo.Pase;
import modelo.ReglaNegocioException;

public class AsistenciaData {

    private final Connection cn;
    private final PaseData paseData;

    public AsistenciaData(Connection cn) {
        this.cn = cn;
        this.paseData = new PaseData(cn);
    }

    public int alta(Asistencia asistencia) throws SQLException, ReglaNegocioException {
        validarDatosBasicos(asistencia);

        int idSocio = asistencia.getSocio().getIdSocio();
        int idClase = asistencia.getClase().getIdClase();
        LocalDate dia = asistencia.getFecha();

        boolean autoCommitOriginal = cn.getAutoCommit();
        cn.setAutoCommit(false);
        try {
            if (!socioActivo(idSocio)) {
                throw new ReglaNegocioException("El socio no existe o está dado de baja.");
            }

            int capacidad = verificarYBloquearClase(idClase);

            if (contarAsistencias(idSocio, idClase, dia, 0) > 0) {
                throw new ReglaNegocioException("El socio ya está anotado a esta clase en esa fecha.");
            }

            int inscriptos = contarAsistenciasClase(idClase, dia, 0);
            if (inscriptos >= capacidad) {
                throw new ReglaNegocioException("No hay cupo disponible en la clase para esa fecha ("
                        + inscriptos + " de " + capacidad + " lugares ocupados).");
            }

            Pase pase = paseData.buscarVigente(idSocio, dia);
            if (pase == null) {
                throw new ReglaNegocioException(
                        "El socio no tiene un pase vigente para esa fecha (no tiene pase, está vencido o se quedó sin entradas).");
            }

            String sql = "INSERT INTO asistencia (id_Socio, id_Clase, fecha_Asistencia) VALUES (?, ?, ?)";
            try (PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, idSocio);
                ps.setInt(2, idClase);
                ps.setDate(3, Date.valueOf(dia));
                ps.executeUpdate();
                
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        asistencia.setIdAsistencia(rs.getInt(1));
                    }
                }
            }

            if (!paseData.descontarEntrada(pase.getIdPase())) {
                throw new ReglaNegocioException("No se pudo descontar la entrada del pase.");
            }

            cn.commit();
            return pase.getCantEntrada() - 1;

        } catch (SQLException | ReglaNegocioException | RuntimeException e) {
            cn.rollback();
            throw e;
        } finally {
            cn.setAutoCommit(autoCommitOriginal);
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

    public int actualizar(Asistencia asistencia) throws SQLException, ReglaNegocioException {
        validarDatosBasicos(asistencia);
        if (asistencia.getIdAsistencia() <= 0) {
            throw new IllegalArgumentException("La asistencia debe tener un ID válido para actualizar.");
        }

        int idSocio = asistencia.getSocio().getIdSocio();
        int idClase = asistencia.getClase().getIdClase();
        LocalDate dia = asistencia.getFecha();
        int idAsistenciaActual = asistencia.getIdAsistencia();

        boolean autoCommitOriginal = cn.getAutoCommit();
        cn.setAutoCommit(false);
        try {
            // 1. Devolver la entrada de la asistencia anterior
            Asistencia anterior = buscar(idAsistenciaActual);
            if (anterior == null) {
                throw new ReglaNegocioException("La asistencia a modificar ya no existe.");
            }
            paseData.devolverEntrada(anterior.getSocio().getIdSocio(), anterior.getFecha());

            if (!socioActivo(idSocio)) {
                throw new ReglaNegocioException("El socio no existe o está dado de baja.");
            }

            int capacidad = verificarYBloquearClase(idClase);

            // Se excluye la propia asistencia al contar para permitir modificar la misma fecha/clase sin conflicto falso
            if (contarAsistencias(idSocio, idClase, dia, idAsistenciaActual) > 0) {
                throw new ReglaNegocioException("El socio ya está anotado a esta clase en esa fecha.");
            }

            int inscriptos = contarAsistenciasClase(idClase, dia, idAsistenciaActual);
            if (inscriptos >= capacidad) {
                throw new ReglaNegocioException("No hay cupo disponible en la clase para esa fecha ("
                        + inscriptos + " de " + capacidad + " lugares ocupados).");
            }

            Pase pase = paseData.buscarVigente(idSocio, dia);
            if (pase == null) {
                throw new ReglaNegocioException(
                        "El socio no tiene un pase vigente para esa fecha (no tiene pase, está vencido o se quedó sin entradas).");
            }

            // 2. Actualizar el registro en la base de datos
            String sql = "UPDATE asistencia SET id_Socio = ?, id_Clase = ?, fecha_Asistencia = ? WHERE id_Asistencia = ?";
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, idSocio);
                ps.setInt(2, idClase);
                ps.setDate(3, Date.valueOf(dia));
                ps.setInt(4, idAsistenciaActual);
                ps.executeUpdate();
            }

            // 3. Descontar la entrada del nuevo pase
            if (!paseData.descontarEntrada(pase.getIdPase())) {
                throw new ReglaNegocioException("No se pudo descontar la entrada del pase.");
            }

            cn.commit();
            return pase.getCantEntrada() - 1;

        } catch (SQLException | ReglaNegocioException | RuntimeException e) {
            cn.rollback();
            throw e;
        } finally {
            cn.setAutoCommit(autoCommitOriginal);
        }
    }
    
    public boolean anular(int idAsistencia) throws SQLException {
        boolean autoCommitOriginal = cn.getAutoCommit();
        cn.setAutoCommit(false);
        try {
            Asistencia a = buscar(idAsistencia);
            if (a == null) {
                cn.rollback();
                return false;
            }
            String sql = "DELETE FROM asistencia WHERE id_Asistencia = ?";
            try (PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setInt(1, idAsistencia);
                ps.executeUpdate();
            }
            paseData.devolverEntrada(a.getSocio().getIdSocio(), a.getFecha());
            cn.commit();
            return true;
        } catch (SQLException | RuntimeException e) {
            cn.rollback();
            throw e;
        } finally {
            cn.setAutoCommit(autoCommitOriginal);
        }
    }
    
    private void validarDatosBasicos(Asistencia asistencia) throws ReglaNegocioException {
        if (asistencia == null) {
            throw new IllegalArgumentException("La asistencia no puede ser nula.");
        }
        if (asistencia.getSocio() == null || asistencia.getSocio().getIdSocio() <= 0) {
            throw new IllegalArgumentException("La asistencia debe tener un socio con id valido.");
        }
        if (asistencia.getClase() == null || asistencia.getClase().getIdClase() <= 0) {
            throw new IllegalArgumentException("La asistencia debe tener una clase con id valido.");
        }
        if (asistencia.getFecha() == null) {
            throw new ReglaNegocioException("La fecha es obligatoria.");
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
    
    private int verificarYBloquearClase(int idClase) throws SQLException, ReglaNegocioException {
        String sqlClase = "SELECT Capacidad, Estado FROM clasegym WHERE id_Clase = ? FOR UPDATE";
        try (PreparedStatement ps = cn.prepareStatement(sqlClase)) {
            ps.setInt(1, idClase);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new ReglaNegocioException("La clase no existe.");
                }
                if (!rs.getBoolean("Estado")) {
                    throw new ReglaNegocioException("La clase está dada de baja.");
                }
                return rs.getInt("Capacidad");
            }
        }
    }
    
    private int contarAsistencias(int idSocio, int idClase, LocalDate dia, int excluirId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM asistencia WHERE id_Socio = ? AND id_Clase = ? AND fecha_Asistencia = ? AND id_Asistencia <> ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ps.setInt(2, idClase);
            ps.setDate(3, Date.valueOf(dia));
            ps.setInt(4, excluirId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        }
    }
    
    private int contarAsistenciasClase(int idClase, LocalDate dia, int excluirId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM asistencia WHERE id_Clase = ? AND fecha_Asistencia = ? AND id_Asistencia <> ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {;
            ps.setInt(1, idClase);
            ps.setDate(2, Date.valueOf(dia));
            ps.setInt(3, excluirId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
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
        socio.setTelefono(rs.getString("SocioTelefono"));
        socio.setEstado(rs.getBoolean("SocioEstado"));

        Asistencia asistencia = new Asistencia();
        asistencia.setIdAsistencia(rs.getInt("id_Asistencia"));
        asistencia.setSocio(socio);
        asistencia.setClase(clase);
        asistencia.setFecha(rs.getDate("fecha_Asistencia").toLocalDate());
        return asistencia;
    }
}
