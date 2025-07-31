package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class FahrzeugJdbcRepository {

  private final JdbcTemplate jdbcTemplate;

  public FahrzeugJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public FahrzeugEntity save(FahrzeugEntity fahrzeug) {
    if (fahrzeug.id() == null) {
      return insert(fahrzeug);
    } else {
      return update(fahrzeug);
    }
  }

  public Optional<FahrzeugEntity> findById(Long id) {
    String sql = "SELECT * FROM fahrzeug WHERE id = ?";
    List<FahrzeugEntity> results = jdbcTemplate.query(sql, FAHRZEUG_ROW_MAPPER, id);
    return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
  }

  public List<FahrzeugEntity> findAll() {
    String sql = "SELECT * FROM fahrzeug";
    return jdbcTemplate.query(sql, FAHRZEUG_ROW_MAPPER);
  }

  public boolean existsById(Long id) {
    String sql = "SELECT COUNT(*) FROM fahrzeug WHERE id = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
    return count != null && count > 0;
  }

  public void deleteById(Long id) {
    String sql = "DELETE FROM fahrzeug WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }

  private FahrzeugEntity insert(FahrzeugEntity fahrzeug) {
    String sql = "INSERT INTO fahrzeug (hersteller, modell, jahr) VALUES (?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          ps.setString(1, fahrzeug.hersteller());
          ps.setString(2, fahrzeug.modell());
          ps.setInt(3, fahrzeug.jahr());
          return ps;
        },
        keyHolder);

    Long generatedId = (Long) keyHolder.getKeys().get("ID");
    return new FahrzeugEntity(
        generatedId, fahrzeug.hersteller(), fahrzeug.modell(), fahrzeug.jahr());
  }

  private FahrzeugEntity update(FahrzeugEntity fahrzeug) {
    String sql = "UPDATE fahrzeug SET hersteller = ?, modell = ?, jahr = ? WHERE id = ?";
    jdbcTemplate.update(
        sql, fahrzeug.hersteller(), fahrzeug.modell(), fahrzeug.jahr(), fahrzeug.id());
    return fahrzeug;
  }

  private static final RowMapper<FahrzeugEntity> FAHRZEUG_ROW_MAPPER =
      (rs, rowNum) ->
          new FahrzeugEntity(
              rs.getLong("id"),
              rs.getString("hersteller"),
              rs.getString("modell"),
              rs.getInt("jahr"));
}
