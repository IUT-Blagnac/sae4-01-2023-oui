package fr.blagnac.com.control.actors;


import java.sql.ResultSet;


public interface Actor {

    public ResultSet get();

    public void add();

    public void set();

    public void remove();

}
