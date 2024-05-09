package labs.database

class SQLCommands {
    companion object {
        val tablesCreation =
            """
            DO $$ BEGIN
                CREATE TYPE EYE_COLOR AS ENUM(
                    'GREEN', 
                    'YELLOW', 
                    'WHITE'
                );
            EXCEPTION
                WHEN duplicate_object THEN NULL;
            END $$;
            DO $$ BEGIN
                CREATE TYPE HAIR_COLOR AS ENUM(
                    'GREEN',
                    'BLACK',
                    'ORANGE',
                    'WHITE'
                );
            EXCEPTION
                WHEN duplicate_object THEN NULL;
            END $$;
            DO $$ BEGIN
                CREATE TYPE COUNTRY AS ENUM(
                    'RUSSIA',
                    'UNITED_KINGDOM',
                    'ITALY'
                );
            EXCEPTION
                WHEN duplicate_object THEN NULL;
            END $$;
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                login TEXT,
                password TEXT,
                salt TEXT
            );
            CREATE TABLE IF NOT EXISTS person(
                id SERIAL PRIMARY KEY,
                name TEXT NOT NULL,
                cord_x NUMERIC NOT NULL,
                cord_y NUMERIC NOT NULL,
                creation_date TIMESTAMP NOT NULL,
                height INT NOT NULL,
                eye_color EYE_COLOR NOT NULL,
                hair_color HAIR_COLOR NOT NULL,
                nationality COUNTRY NOT NULL,
                location_x NUMERIC NOT NULL,
                location_y NUMERIC NOT NULL,
                location_name TEXT,
                creator_login TEXT REFERENCES users(login)
            );
            """.trimIndent()

        val insertUser =
            """
            INSERT INTO users(login, password, salt) VALUES (?, ?, ?)
            RETURNING id;
            """.trimIndent()

        val getUser =
            """
            SELECT * FROM users WHERE (login = ?);
            """.trimIndent()

        val insertObject =
            """
            INSERT INTO person(name, cord_x, cord_y, creation_date, height, eye_color, hair_color, nationality, location_x, location_y, location_name, creator_login)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id;
            """.trimIndent()

        val getAllObjects =
            """
            SELECT * FROM person;
            """.trimIndent()

        val deleteUserCreatedObject =
            """
            DELETE FROM person WHERE (creator_login = ?) AND (id = ?) RETURNING id;
            """.trimIndent()

        val updateUserObject =
            """
            UPDATE person
            SET (name, cord_x, cord_y, creation_date, height, eye_color, hair_color, nationality, location_x, location_y, location_name)
            = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            WHERE (id = ?) AND (creator_login = ?)
            RETURNING id;
            """.trimIndent()
    }
}
