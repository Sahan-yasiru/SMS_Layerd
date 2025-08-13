package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.TimeTableBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.TimeTableDao;
import org.example.smslayerd.dao.custom.impl.TimeTableDAOImpl;
import org.example.smslayerd.entity.TimeTable;
import org.example.smslayerd.model.DtoTimeTable;

import java.sql.SQLException;
import java.util.ArrayList;

public class TimeTableBOImpl implements TimeTableBO {
    private TimeTableDao timeTableDAO=(TimeTableDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.TimeTable);
    @Override
    public ArrayList<DtoTimeTable> getAll() throws SQLException {
        ArrayList<DtoTimeTable> dtoTimeTables=new ArrayList<>();
        timeTableDAO.getAll().forEach(timeTable -> {
            dtoTimeTables.add(new DtoTimeTable(timeTable));
        });
        return dtoTimeTables;
    }

    @Override
    public boolean save(DtoTimeTable dto) throws SQLException {
        return false;
    }

    @Override
    public boolean update(DtoTimeTable dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        return "";
    }

    @Override
    public DtoTimeTable search(String id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<DtoTimeTable> getAllByDate() throws SQLException {
         ArrayList<DtoTimeTable> dtoTimeTables=new ArrayList<>();
         timeTableDAO.getAllByDate().forEach(timeTableEty ->{
             dtoTimeTables.add(new DtoTimeTable(timeTableEty.getTimeTableID(),timeTableEty.getSubjectID(),timeTableEty.getStartTime(),
                     timeTableEty.getEndTime(),timeTableEty.getDayOfWeek(),timeTableEty.getSubjectName()));
         });
         return dtoTimeTables;
    }
}
