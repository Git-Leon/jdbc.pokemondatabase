package com.zipcodewilmington.jdbc.tools.connection;

import com.zipcodewilmington.jdbc.tools.database.Database;
import com.zipcodewilmington.jdbc.tools.resultset.ResultSetHandler;
import com.zipcodewilmington.jdbc.tools.testutils.SeedRefresher;
import org.junit.Before;
import org.junit.Test;

public class ResultSetHandlerTest {
    @Before
    public void setup() {
        SeedRefresher.refresh(Database.UAT);
    }

    @Test
    public void test() {
        ResultSetHandler rsh = Database.UAT.getTable("person").all();
    }
}
