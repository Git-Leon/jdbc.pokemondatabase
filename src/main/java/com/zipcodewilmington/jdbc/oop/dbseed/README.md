# Database Seeding
* This package contains different implementations of seeding a database.
* Wilhem's model
    * checks if `MigrationsTable` exists.
        * if no, creates a `MigrationsTable`
    * check each file in `resources/migrations` directory.
        * check if file exists in `MigrationsTable`
            * if yes, do not execute file
            * else, execute script 
* Leon's model
    * drops all pre-existing tables
    * populates each table with the files in `resource/migrations`
