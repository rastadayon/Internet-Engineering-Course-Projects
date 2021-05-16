package ir.ac.ut.ie.Bolbolestan06.services;

import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.exeptions.BolbolestanStudentNotFoundError;
import ir.ac.ut.ie.Bolbolestan06.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan06.exceptions.ForbiddenException;
import ir.ac.ut.ie.Bolbolestan06.repository.BolbolestanRepository;

public class AuthService {
    public static void authUser(Login login) throws Exception{
        System.out.println("in auth user");
        if(login.getEmail() == null)
            throw new ForbiddenException("Fields most have values");
        Student student = BolbolestanRepository.getInstance().getStudent(login.getEmail());
        if (student != null) {
            Bolbolestan.getInstance().makeLoggedIn(student.getId());
        }
        else
            throw new BolbolestanStudentNotFoundError();
        System.out.println("logged in std is : " +Bolbolestan.getInstance().getLoggedInId());
    }
}
