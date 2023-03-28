package fr.blagnac.com.control.actors;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface Actor {

    public ResultSet get(List<String> filtres, Map<String, String> parametresWhere) throws SQLException;

    public void add(Map<String, String> parametresValues) throws SQLException, Exception;

    public void set(Map<String, String> parametresValues, Map<String, String> parametresWhere) throws SQLException, Exception;

    public void remove(Map<String, String> parametresWhere) throws SQLException;

}
