package ir.ac.ut.ie.Bolbolestan06.services;

import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan06.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan06.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan06.exceptions.ForbiddenException;

public class AuthService {
    public static void authUser(Login login) throws Exception{
        System.out.println("in auth user");
        if(login.getEmail() == null)
            throw new ForbiddenException("Fields most have values");
        Bolbolestan bolbolestan = Bolbolestan.getInstance();
        Student student = bolbolestan.getStudentById(login.getEmail());
        if (student != null) {
            bolbolestan.makeLoggedIn(student.getId());
        }

        System.out.println("logged in std is : " +bolbolestan.getLoggedInId());
    }
}
