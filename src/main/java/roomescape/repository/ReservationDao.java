package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.Theme;
import roomescape.domain.TimeSlot;
import roomescape.domain.dto.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationDao {
    private static final RowMapper<ReservationResponse> rowMapper =
            (resultSet, rowNum) -> new ReservationResponse(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    LocalDate.parse(resultSet.getString("date")),
                    new TimeSlot(resultSet.getLong("time_id"), resultSet.getString("time_value")),
                    new Theme(resultSet.getLong("theme_id"), resultSet.getString("theme_name"), resultSet.getString("theme_description"), resultSet.getString("theme_thumbnail"))
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingColumns("name", "date", "time_id", "theme_id")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationResponse> findAll() {
        String sql = """
                SELECT
                    r.id as reservation_id,
                    r.name,
                    r.date,
                    t.id as time_id,
                    t.start_at as time_value,
                    th.id as theme_id,
                    th.name as theme_name,
                    th.description as theme_description,
                    th.thumbnail as theme_thumbnail
                FROM reservation as r
                INNER JOIN reservation_time as t ON r.time_id = t.id
                INNER JOIN theme as th ON r.theme_id = th.id;
                """;
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Long create(final Reservation reservation) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", reservation.getName())
                .addValue("date", reservation.getDate())
                .addValue("time_id", reservation.getTime().getId())
                .addValue("theme_id", reservation.getTheme().getId());
        return jdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    public void delete(final Long id) {
        String sql = "delete from reservation where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean isExists(final LocalDate date, final Long timeId, final Long themeId) {
        String sql = """
                SELECT
                    count(*)
                FROM reservation
                WHERE date = ? AND time_id = ? AND theme_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, date, timeId, themeId) != 0;
    }

    public boolean isExistsTimeId(final Long timeId) {
        String sql = "select count(*) from reservation where time_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, timeId) != 0;
    }

    public boolean isExistsThemeId(final Long themeId) {
        String sql = "select count(*) from reservation where theme_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, themeId) != 0;
    }
}
