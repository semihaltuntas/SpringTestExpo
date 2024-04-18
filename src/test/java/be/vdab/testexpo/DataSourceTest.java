package be.vdab.testexpo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import static org.assertj.core.api.Assertions.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@JdbcTest
public class DataSourceTest {
    private final DataSource dataSource;

    public DataSourceTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Test
    void setDataSourceKanEenConnectionGeven() throws SQLException {
        try (Connection connection = dataSource.getConnection()){
            assertThat(connection.getCatalog()).isEqualTo("expo");
        }
    }
}
