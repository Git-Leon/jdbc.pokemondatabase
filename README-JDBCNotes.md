# JDBC Notes

## What is a `DataSource` object?
* The `DataSource` interface, new in the JDBC 2.0 API, provides another way to connect to a data source.
* The use of a `DataSource` object is the preferred means of connecting to a data source. 

## What is a `MySqlDataSource` object?
* A Java Native Database Interface that returns a connection to a database defined in the connection params.

## What is a `DriverManager` object?
* A Java Native Database Interface that returns a connection to a database defined in the connection params.

```java
DriverManager.getConnection("jdbc:mysql://localhost/"+
                            "MyDatabaseName?" +
                            "user=MyUserName&"+
                            "password=MyPassWord");
```

## What is a `Connection` object? 
* A `Connection` (session) with a specific database.
    * SQL statements are executed and results are returned within the context of a connection.
* The `DriverManager.getConnection()` method is used to create a JDBC `Connection`.
* The URL used is dependent upon the particular database and JDBC `Driver`.
* A `Connection` object's database is able to provide information describing its tables, its supported SQL grammar, its stored procedures, the capabilities of this connection, and so on.
* This information is obtained with the getMetaData method.


## What is a `Driver`?
* A `JDBC` Driver is an object that allows java to interact with a database.

## What is the `DriverManager` ?
* The basic service for managing a set of JDBC drivers.
* Attempts to load as many drivers as it can find and for any given connection request, it will ask each driver in turn to try to connect to the target URL.


## What is a `ResultSet` ?
* Java representation of the result set of a database query.
* Maintains a cursor that points to the current row in the result set.
* The term "result set" refers to the row and column data contained in a ResultSet object.


## What is `JDBC Driver Registration?`
* If a `Driver` is not registered to the `DriverManager`,  
* Driver-registration is the mechanism by which the `DriverManager` decides which drivers to try to make connections with.
* Driver-registration is typically done one of two ways:
    * Using `Class.forName()` on the class that implements the `java.sql.Driver` interface.
    * Using `DriverManager.register(new my.jdbc.provider.Driver())`

