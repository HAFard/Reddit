import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        runMenu();
    }

    public static void runMenu() {

        while (true) {
            System.out.println("START MENU : ");
            System.out.println("1 - Sign up                   2 - Log in ");
            System.out.println("3 - Close program ");
            System.out.println("Please choose a number : ");

            Scanner In = new Scanner(System.in);
            int enteredNumber = In.nextInt();

            switch (enteredNumber) {
                case 1:
                    Scanner in1 = new Scanner(System.in);
                    System.out.println("please Enter your email : ");
                    String enteredEmail =  in1.nextLine();
                    System.out.println("please Enter your password : ");
                    String enteredPassword =  in1.nextLine();
                    if (User.validateEmail(enteredEmail)){
                        if (!Reddit.IsUser(enteredEmail)) {
                            new User(enteredEmail,enteredPassword);
                            System.out.println("Your account is created...Please log in!");
                        }
                        else System.out.println("There is an account with this email address...Please log in!");
                    }
                    else System.out.println("Not valid Email!");
                    break;


                case 2:
                    Scanner in2 = new Scanner(System.in);
                    System.out.println("please Enter your email : ");
                    String userEmail =  in2.nextLine();
                    System.out.println("please Enter your password : ");
                    String userPassword =  in2.nextLine();
                    if (Reddit.userLogin(userEmail,userPassword) != null) {
                        System.out.println("Welcome to Your account!");
                        Reddit.userLogin(userEmail,userPassword).runUserMenu();
                    }
                    break;


                case 3:
                    break;
            }
            if (enteredNumber == 3) break;
        }
    }
}