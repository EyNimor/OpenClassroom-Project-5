package com.openclassroom.safetynetalerts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class personsDaoImpl /** implements personsDao */ {

    /*

    public static DataBaseConfig dbConfig = new DataBaseConfig();

    private static final Logger logger = LogManager.getLogger("personsDaoImpl");

    private static List<Persons> personsDB = new ArrayList<>();

    public List<Persons> getDB() {
        Connection con = null;
        Persons person;
        List<Persons> personsList = new ArrayList<>();
        logger.info("getDataBase");
        try {
            con = dbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement("select * from persons");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                person = new Persons();
                person.setFirstName(rs.getString(2));
                person.setLastName(rs.getString(3));
                person.setAddress(rs.getString(4));
                person.setCity(rs.getString(5));
                person.setZip(rs.getLong(6));
                person.setPhone(rs.getString(7));
                person.setEmail(rs.getString(8));
                personsList.add(person);
            }
            dbConfig.closeResultSet(rs);
            dbConfig.closePreparedStatement(ps);
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
        }finally {
            dbConfig.closeConnection(con);
        }
        return personsList;
    }

    public List<Persons> findAll() {
        personsDB = getDB();
        return personsDB;
    }

    public boolean delete(String firstName, String lastName) {
        return false;
    }

    public List<String> findEmailByCity(String city) {
        // TODO Auto-generated method stub
        return null;
    }

    public Persons save(Persons personToSave, boolean haveId) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean update(Persons personToUpdate) {
        // TODO Auto-generated method stub
        return false;
    }

    */

}
