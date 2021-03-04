import InterfaceServer.InterfaceServer;

public class Main {
    public static void main(String[] args) {
        final String STUDENTS_URL = "http://138.197.181.131:5000/api/students";
        final String GRADES_URL = "http://138.197.181.131:5000/api/grades";
        final String COURSES_URL = "http://138.197.181.131:5000/api/courses";
        final int PORT = 8080; //?
        InterfaceServer interfaceServer = new InterfaceServer();
        interfaceServer.start(STUDENTS_URL, COURSES_URL, GRADES_URL, PORT);
    }
}
