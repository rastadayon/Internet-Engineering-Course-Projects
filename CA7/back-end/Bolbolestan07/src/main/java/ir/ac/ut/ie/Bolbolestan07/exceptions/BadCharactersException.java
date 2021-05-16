package ir.ac.ut.ie.Bolbolestan07.exceptions;

public class BadCharactersException extends Exception{
    @Override
    public String getMessage() {
        return "Illegal use of tags or special characters";
    }
}
