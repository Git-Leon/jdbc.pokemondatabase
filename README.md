# JDBC & JPA Demo 

# How do I orient myself in this project?
* `Database`
    * An enum with each enumeration referencing a respective connection to a database.
    * Provides a mechanism for
        * creating Database
        * dropping Database
        * using Database
        * getting a table from connection
 

* `DatabaseTable`
    * A class-representation of a Table in a Database.
    * Very limited features:
        * `SELECT {ColumnNames} WHERE {Condition} LIMIT {NumberOfRows}`
    * Provides a mechanism for
        * Selecting from specified columns
        * Selecting from specified columns where some condition
        * Selecting from specified columns where some condition limited by `n` number of results.

* `ConnectionBuilder`
    * A class which continually pends instructions for how to construct the subsequent `MySQLDataSource`. 

* `ConnectionWrapper`
    * A class which wraps `Connection` method invocations in a `try`/`catch` clause.
    
* `ResultSetHandler`
    * A class which wraps `ResultSet` method invocations in a `try`/`catch` clause.
    * Provides a mechanism for
        * representing this `ResultSet` as a `List<Map<String,String>>`
        * representing this `ResultSet` as a `Stack<Map<String,String>>`

* `StatementExecutor`
    * A class which wraps `Statement` method invocations in a `try`/`catch` clause.
    * Provides a mechanism for
        * executing a statement
        * executing a query statement
        * executing an update statement
        * committing statement executions
