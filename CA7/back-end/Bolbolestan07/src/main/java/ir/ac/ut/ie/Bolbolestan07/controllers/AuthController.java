package ir.ac.ut.ie.Bolbolestan07.controllers;

import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Bolbolestan;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.SignUp;
import ir.ac.ut.ie.Bolbolestan07.exceptions.BadCharactersException;
import ir.ac.ut.ie.Bolbolestan07.controllers.models.Login;
import ir.ac.ut.ie.Bolbolestan07.controllers.domain.Bolbolestan.Student.Student;
import ir.ac.ut.ie.Bolbolestan07.services.AuthService;
import ir.ac.ut.ie.Bolbolestan07.utils.JWTUtils;
import ir.ac.ut.ie.Bolbolestan07.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ir.ac.ut.ie.Bolbolestan07.security.JWTAuthFilter;
import ir.ac.ut.ie.Bolbolestan07.utils.HTTPRequestHandler.HTTPRequestHandler;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final static String SEND_MAIL_URL = "http://138.197.181.131:5200/api/send_mail";
    final static String CHANGE_PASS_URL = "http://localhost:8080/changePassword/";

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
            @RequestParam String email) throws IOException {
        System.out.println("in forget");
        System.out.println("email is " + email);
        try {
            if(Utils.hasIllegalChars(email)){
                throw new BadCharactersException();
            }
            if (AuthService.isStudentInDB(email)) {
                String url = CHANGE_PASS_URL + JWTUtils.createJWT(email);
                String request = SEND_MAIL_URL + "?" + "url=" + url + "&" + "email=" + email;
                HTTPRequestHandler.postRequest(request);
                return ResponseEntity.status(HttpStatus.OK).body("OK");
            }
            else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("student not found. invalid login");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity signup(
            @RequestBody SignUp signUpData) throws IOException {
        System.out.println("in signup");
        String email = signUpData.getEmail();
        String password = signUpData.getPassword();
        try {
            if(Utils.hasIllegalChars(email) || Utils.hasIllegalChars(password)){
                throw new BadCharactersException();
            }
            AuthService.signUpUser(signUpData);
            System.out.println("sign up successfull");
            return ResponseEntity.status(HttpStatus.OK).body("OK - sign up successfull");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("sign up unsuccessful");
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
