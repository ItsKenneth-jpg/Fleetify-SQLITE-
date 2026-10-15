package com.scc;


import com.scc.dao.UserDAO;
import com.scc.dao.adminDAO;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDAO USER_DAO = new UserDAO();

    public static void main(String[] args){

        System.out.println("\n================================");
        System.out.println("           FLEETIFY");
        System.out.println("================================");
        System.out.println("1. LOGIN");
        System.out.println("2. REGISTER");
        System.out.println("3. EXIT");
        System.out.print("Enter option: ");

        String choice = sc.nextLine();

        switch (choice){
            case "1":
                break;
            case "2":
                System.out.println("\n================================");
                System.out.println("            REGISTER");
                System.out.println("================================");
                System.out.println("1. ADMIN");
                System.out.println("2. STAFF");
                System.out.print("Enter option: ");

                String option = sc.nextLine();

                switch (option){
                    case "1":

                        System.out.print("Enter Username: ");
                        String username = sc.nextLine();
                        System.out.print("Enter Password: ");
                        String password = sc.nextLine();
                        System.out.print("Enter Security Code: ");
                        String securityCode = sc.nextLine();

                        adminDAO AdminDAO = new adminDAO();

                        if (AdminDAO.registerAdmin(username, password, securityCode)) {
                            System.out.println("Admin account registered successfully");
                        }
                        break;
                    case "2":

                }



        }

    }

}
