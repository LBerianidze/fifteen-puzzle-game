package com.fifteenpuzzle.fifteenpuzzlegame.database;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class DBClient
{
    private static final Logger log = LoggerFactory.getLogger(DBClient.class);

    private static String DatabaseURL;
    private static String DatabaseUserName;
    private static String DatabasePassword;

    static
    {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties"))
        {

            Properties prop = new Properties();
            prop.load(input);
            DatabaseURL = prop.getProperty("db.url");
            DatabaseUserName = prop.getProperty("db.username");
            DatabasePassword = prop.getProperty("db.password");


        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    JdbcDataSource dataSource;

    /*
        name რა თქმა უნდა მეორდება ბევრი ადამიანისთვის, მაგრამ პირობითად ავიღე
     */
    public DBClient()
    {
        try
        {
            this.dataSource = new JdbcDataSource();
            this.dataSource.setURL(DatabaseURL);
            this.dataSource.setUser(DatabaseUserName);
            this.dataSource.setPassword(DatabasePassword);
        } catch (Exception e)
        {
            log.error("Connection to database failed", e);

            throw new RuntimeException(e);
        }
    }

    public Integer getUserByName(String name)
    {
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select id from Player where name = ?"))
            {
                preparedStatement.setObject(1, name.toLowerCase());
                preparedStatement.execute();
                ResultSet result = preparedStatement.getResultSet();
                if (result.next())
                    return result.getInt(1);
                return -1;
            }
        } catch (SQLException e)
        {
            log.error("User search failed", e);

            throw new RuntimeException(e);
        }
    }

    public int insertUser(String name)
    {
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Player(NAME) VALUES(?);", Statement.RETURN_GENERATED_KEYS))
            {
                preparedStatement.setObject(1, name.toLowerCase());
                preparedStatement.executeUpdate();
                ResultSet result = preparedStatement.getGeneratedKeys();
                if (result.next())
                {
                    log.info(String.format("New user %s with id %d was inserted.", name, result.getInt(1)));

                    return result.getInt(1);
                }
                throw new SQLException("Can not insert user");
            }
        } catch (SQLException e)
        {
            log.error("Can't insert new user to database", e);

            throw new RuntimeException(e);
        }

    }

    public int getUserPlayedGamesCount(int id)
    {
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from Game where user_id = ?"))
            {
                preparedStatement.setObject(1, id);
                preparedStatement.execute();
                ResultSet result = preparedStatement.getResultSet();
                if (result.next())
                    return result.getInt(1);
                return 0;
            }
        } catch (SQLException e)
        {
            log.error("Can't get user games count", e);
            throw new RuntimeException(e);
        }
    }

    public void insertGame(int currentUserId, LocalDateTime startDateTime, LocalDateTime endDateTime, int movesCount)
    {
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Game(user_id,start_date,end_date,moves_count) VALUES(?,?,?,?);"))
            {
                preparedStatement.setObject(1, currentUserId);
                preparedStatement.setObject(2, startDateTime);
                preparedStatement.setObject(3, endDateTime);
                preparedStatement.setObject(4, movesCount);

                preparedStatement.execute();
            }
        } catch (SQLException e)
        {
            log.error("Can't insert new game to database", e);

            throw new RuntimeException(e);
        }
    }
}
