package com.zipcodewilmington.jdbc.tools.connection;

import com.github.git_leon.collectionutils.MapCollection;
import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.database.DatabaseTable;
import com.zipcodewilmington.jdbc.tools.executor.StatementExecutor;
import com.zipcodewilmington.jdbc.tools.resultset.ResultSetHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StatementExecutorTest {
    private StatementExecutor executor;
    private Database database;

    @Before
    public void setup() {
        this.database = Database.UAT;
        this.database.drop();
        this.database.create();
        this.database.use();
        this.executor = database.getStatementExecutor();
    }

    @Test
    public void executeCreate() {
        // given
        String createStatement = "CREATE TABLE IF NOT EXISTS uat.person %s;";

        // when
        executor.execute(createStatement,
                "(personID int null," +
                        "firstName text null," +
                        "lastName text null)");

        // then
        DatabaseTable personTable = database.getTable("person");
        Assert.assertNotNull(personTable);
    }


    @Test
    public void executeInsert() {
        // given
        executeCreate();
        String expected = "{firstName=leon, lastName=hunter, personID=0}";

        // when
        executor.execute("INSERT INTO uat.person(personID, firstName, lastName) VALUES (0, 'leon', 'hunter');");

        // then
        DatabaseTable personTable = database.getTable("person");
        ResultSetHandler rs = personTable.where("personId = 'leon'");
        String actual = rs.toStack().pop().toString();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void executeSelect() {
        // given
        executeInsert();

        // when
        ResultSetHandler rsh = executor.executeQuery("SELECT * FROM person;");

        // then
        MapCollection<String, String> mapCollection =rsh.toMapCollection();
        int numberOfRows = mapCollection.size();
        Assert.assertTrue(numberOfRows > 0);
    }
}
