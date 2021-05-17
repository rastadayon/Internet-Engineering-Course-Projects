package ir.ac.ut.ie.Bolbolestan07.services;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan07.exceptions.ForbiddenException;
import ir.ac.ut.ie.Bolbolestan07.repository.BolbolestanRepository;
import org.apache.commons.codec.digest.DigestUtils;

public class AuthService {
    public static Student authUser(Login login) throws Exception{
        System.out.println("in auth user");
        if(login.getEmail() == null)
            throw new ForbiddenException("Fields most have values");
        Student student = BolbolestanRepository.getInstance().getStudent(login.getEmail());
        if (student != null) {
//            DigestUtils.sha256Hex(login.getPassword().getBytes()))
            if (!student.getPassword().equals(login.getPassword()))
                throw new Exception("passwords does not match");
            else 
                Bolbolestan.getInstance().makeLoggedIn(student.getId());
                return student;
        }
        else
            throw new BolbolestanStudentNotFoundError();
        //System.out.println("logged in std is : " +Bolbolestan.getInstance().getLoggedInId());
    }
}
