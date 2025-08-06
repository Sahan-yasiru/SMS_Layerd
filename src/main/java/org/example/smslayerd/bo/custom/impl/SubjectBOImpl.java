package org.example.smslayerd.bo.custom.impl;

import org.example.smslayerd.bo.custom.SubjectBO;
import org.example.smslayerd.dao.DAOFactory;
import org.example.smslayerd.dao.custom.impl.SubjectDAOImpl;
import org.example.smslayerd.entity.Subject;
import org.example.smslayerd.model.DtoSubject;
import org.example.smslayerd.view.tdm.SubjectTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectBOImpl implements SubjectBO {

    SubjectDAOImpl subjectDAO=(SubjectDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.Subject);
    @Override
    public ArrayList<DtoSubject> getAll() throws SQLException {
        ArrayList<DtoSubject> dtoSubjects=new ArrayList<>();
        subjectDAO.getAll().forEach(subjectEty ->{
            dtoSubjects.add(new DtoSubject(subjectEty.getSubjectID(),subjectEty.getName()));
        });
        return dtoSubjects;
    }

    @Override
    public boolean save(DtoSubject dto) throws SQLException {
        return subjectDAO.save(new Subject(dto.getSubjectID(),dto.getName()));
    }

    @Override
    public boolean update(DtoSubject dto) throws SQLException {
        return subjectDAO.update(new Subject(dto.getSubjectID(),dto.getName()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return subjectDAO.delete(id);
    }

    @Override
    public boolean ifExit(String id) throws SQLException {
        return false;
    }

    @Override
    public String getNewId() throws SQLException {
        return subjectDAO.getNewId();
    }

    @Override
    public DtoSubject search(String id) throws SQLException {
        Subject subjectEty=subjectDAO.search(id);
        return new DtoSubject(subjectEty.getSubjectID(),subjectEty.getName());
    }

    @Override
    public String getNumber() throws SQLException {
        return subjectDAO.getNumber();
    }
}
