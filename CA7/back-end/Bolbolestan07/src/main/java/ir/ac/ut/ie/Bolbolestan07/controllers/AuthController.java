package ir.ac.ut.ie.Bolbolestan07.controllers;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan07.exceptions.BadCharactersException;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan07.services.AuthService;
import ir.ac.ut.ie.Bolbolestan07.utils.JWTUtils;
import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ir.ac.ut.ie.Bolbolestan07.security.JWTAuthFilter;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody Login loginData) throws IOException {
        System.out.println("in login");
        System.out.println("email is " + loginData.getEmail());
        String email = loginData.getEmail();
        String password = loginData.getPassword();
        try {
            if(Utils.hasIllegalChars(email) || Utils.hasIllegalChars(password)){
                throw new BadCharactersException();
            }
            Student student = AuthService.authUser(loginData);
            String answer = JWTUtils.createJWT(student.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
        }
    }

    @PostMapping("/forget")
    public ResponseEntity forget(
            @RequestBody Login loginData) throws IOException {
        System.out.println("in login");
        System.out.println("email is " + loginData.getEmail());
        String email = loginData.getEmail();
        try {
            if(Utils.hasIllegalChars(email)){
                throw new BadCharactersException();
            }
            Student student = AuthService.authUser(loginData);
            String answer = JWTUtils.createJWT(student.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(answer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
        try {
            Bolbolestan.getInstance().makeLoggedOut();
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
        }
    }
}
