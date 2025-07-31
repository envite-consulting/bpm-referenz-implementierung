package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class BestellungJdbcRepository {

  private final JdbcTemplate jdbcTemplate;
  private final FahrzeugJdbcRepository fahrzeugRepository;

  public BestellungJdbcRepository(
      JdbcTemplate jdbcTemplate, FahrzeugJdbcRepository fahrzeugRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.fahrzeugRepository = fahrzeugRepository;
  }

  public BestellungEntity save(BestellungEntity bestellung) {
    if (bestellung.id() == null) {
      return insert(bestellung);
    } else {
      return update(bestellung);
    }
  }

  public Optional<BestellungEntity> findById(Long id) {
    String sql = "SELECT * FROM bestellung WHERE id = ?";
    List<BestellungEntity> results = jdbcTemplate.query(sql, bestellungRowMapper, id);
    return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
  }

  public List<BestellungEntity> findAll() {
    String sql = "SELECT * FROM bestellung";
    return jdbcTemplate.query(sql, bestellungRowMapper);
  }

  public boolean existsById(Long id) {
    String sql = "SELECT COUNT(*) FROM bestellung WHERE id = ?";
    Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
    return count != null && count > 0;
  }

  public void deleteById(Long id) {
    String sql = "DELETE FROM bestellung WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }

  private BestellungEntity insert(BestellungEntity bestellung) {
    String sql =
        "INSERT INTO bestellung (fahrzeug_id, antragsteller_id, bestelldatum, status) VALUES (?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          ps.setLong(1, bestellung.fahrzeug().id());
          ps.setLong(2, bestellung.antragstellerId());
          ps.setTimestamp(3, Timestamp.valueOf(bestellung.bestelldatum()));
          ps.setString(4, bestellung.status());
          return ps;
        },
        keyHolder);

    Long generatedId = (Long) keyHolder.getKeys().get("ID");
    return new BestellungEntity(
        generatedId,
        bestellung.fahrzeug(),
        bestellung.antragstellerId(),
        bestellung.bestelldatum(),
        bestellung.status());
  }

  private BestellungEntity update(BestellungEntity bestellung) {
    String sql =
        "UPDATE bestellung SET fahrzeug_id = ?, antragsteller_id = ?, bestelldatum = ?, status = ? WHERE id = ?";
    jdbcTemplate.update(
        sql,
        bestellung.fahrzeug().id(),
        bestellung.antragstellerId(),
        Timestamp.valueOf(bestellung.bestelldatum()),
        bestellung.status(),
        bestellung.id());
    return bestellung;
  }

  private final RowMapper<BestellungEntity> bestellungRowMapper =
      new RowMapper<>() {

        @Override
        public BestellungEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
          Long fahrzeugId = rs.getLong("fahrzeug_id");
          FahrzeugEntity fahrzeug =
              fahrzeugRepository
                  .findById(fahrzeugId)
                  .orElseThrow(
                      () -> new RuntimeException("Fahrzeug nicht gefunden: " + fahrzeugId));

          return new BestellungEntity(
              rs.getLong("id"),
              fahrzeug,
              rs.getLong("antragsteller_id"),
              rs.getTimestamp("bestelldatum").toLocalDateTime(),
              rs.getString("status"));
        }
      };
}
