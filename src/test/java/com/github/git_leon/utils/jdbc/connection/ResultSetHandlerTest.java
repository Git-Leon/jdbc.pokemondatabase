package com.github.git_leon.utils.jdbc.connection;

import com.github.git_leon.utils.jdbc.database.Database;
import com.github.git_leon.utils.jdbc.testutils.SeedRefresher;
import com.github.git_leon.utils.jdbc.resultset.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;

public class ResultSetHandlerTest {
    @Before
    public void setup() {
        SeedRefresher.refresh(Database.UAT);
    }

    @Test
    public void test() {
        ResultSetHandler rsh = Database.UAT.getTable("persons").all();
        System.out.println(rsh.toStack());
    }
}
