package arieluch0.usuarios.evomaster;

import arieluch0.usuarios.UsuariosApplication;
import org.evomaster.client.java.controller.EmbeddedSutController;
import org.evomaster.client.java.controller.InstrumentedSutStarter;
import org.evomaster.client.java.controller.api.dto.SutInfoDto;
import org.evomaster.client.java.controller.api.dto.auth.AuthenticationDto;
import org.evomaster.client.java.controller.api.dto.database.schema.DatabaseType;
import org.evomaster.client.java.controller.internal.SutController;
import org.evomaster.client.java.controller.problem.ProblemInfo;
import org.evomaster.client.java.controller.problem.RestProblem;
import org.evomaster.client.java.sql.DbCleaner;
import org.evomaster.client.java.sql.DbSpecification;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ControladorEvoMaster extends EmbeddedSutController {
    private final String url = "http://localhost";
    private ConfigurableApplicationContext contextoAplicacion;
    private Connection conexionSql;
    private List<DbSpecification> dbSpecification;

    public static void main(String[] args) {
        SutController sutController = new ControladorEvoMaster();
        InstrumentedSutStarter instrumentedSutStarter = new InstrumentedSutStarter(sutController);

        instrumentedSutStarter.start();
    }

    @Override
    public boolean isSutRunning() {
        return this.contextoAplicacion != null && this.contextoAplicacion.isRunning();
    }

    @Override
    public String getPackagePrefixesToCover() {
        return "arieluch0.usuarios.";
    }

    @Override
    public List<AuthenticationDto> getInfoForAuthentication() {
        return null;
    }

    @Override
    public ProblemInfo getProblemInfo() {
        return new RestProblem(this.url + ":" + this.getSutPort() + "/v3/api-docs", null);
    }

    @Override
    public SutInfoDto.OutputFormat getPreferredOutputFormat() {
        return SutInfoDto.OutputFormat.JAVA_JUNIT_5;
    }

    @Override
    public String startSut() {
        this.contextoAplicacion = SpringApplication.run(UsuariosApplication.class, "--server.port=0");

        if (this.conexionSql != null) {
            try {
                this.conexionSql.close();
            } catch (SQLException e) {
                throw new RuntimeException(e); // Así lo hacen en el ejemplo de EvoMaster (repositorio EMB).
            }
        }

        JdbcTemplate jdbc = this.contextoAplicacion.getBean(JdbcTemplate.class);

        try {
            this.conexionSql = jdbc.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.dbSpecification = List.of(new DbSpecification(DatabaseType.MYSQL, this.conexionSql));

        System.out.println("*** Puerto del SUT: " + this.getSutPort() + " ***");

        return this.url + ":" + this.getSutPort();
    }

    private int getSutPort() {
        return (Integer) ((Map) this.contextoAplicacion.getEnvironment()
                .getPropertySources().get("server.ports").getSource())
                .get("local.server.port");
    }

    @Override
    public void stopSut() {
        this.contextoAplicacion.stop();
    }

    @Override
    public void resetStateOfSUT() {
        DbCleaner.clearDatabase(this.conexionSql, "usuarios", null, DatabaseType.MYSQL);
    }

    @Override
    public List<DbSpecification> getDbSpecifications() {
        return this.dbSpecification;
    }
}
