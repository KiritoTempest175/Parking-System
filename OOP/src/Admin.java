import java.io.*;

class Admin {
    private static String admin1;
    private static String admin2;
    private static String admin3;
    private static String pass1;
    private static String pass2;
    private static String pass3;

    private static String Login;
    private static String Password;

    public static void defaultAdmin(String admin, String password)
    {
        Login = admin;
        Password = password;
    }

    public static void login1(String admin, String pass) {
        admin1 = admin;
        pass1 = pass;
    }

    public static void login2(String admin, String pass) {
        admin2 = admin;
        pass2 = pass;
    }

    public static void login3(String admin, String pass) {
        admin3 = admin;
        pass3 = pass;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public String getAdmin1() {
        return admin1;
    }

    public String getAdmin2() {
        return admin2;
    }

    public String getAdmin3() {
        return admin3;
    }

    public String getPass1() {
        return pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public String getPass3() {
        return pass3;
    }
public static void main(String[] args) {
        Admin admin = new Admin();
        Admin.defaultAdmin("admin@gmail.com", "1234");
        Admin.login1("huzaifa@gmail.com", "7096");
        Admin.login2("maaz@gmail.com", "7091");
        Admin.login3("ijlal@gmail.com", "7072");

        try {
            FileWriter w = new FileWriter("LoginInfo.txt");
            w.write(admin.getLogin() + " " + admin.getPassword() + " " + "X" + " " + "Admin" + " " + "\n");
            w.write(admin.getAdmin1() + " " + admin.getPass1() + " " + "A" + " " + "Huzaifa" + " " +"\n");
            w.write(admin.getAdmin2() + " " + admin.getPass2() + " " + "B" + " " + "Maaz" + " " +"\n");
            w.write(admin.getAdmin3() + " " + admin.getPass3() + " " + "C" + "Ijlal" + " " +"\n");
            w.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
