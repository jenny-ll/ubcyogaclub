package ui;

import java.util.Scanner;

// Yoga Member Application
// Source: parts of the Teller example

import model.Member;
import model.MembershipList;

public class YogaApp {

    private MembershipList ourMembers;
    private Member firstMember;
    private Scanner input;

    // EFFECTS: runs the yoga application
    public YogaApp() {
        runYogaApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runYogaApp() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye! Namaste!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddMember();
        } else if (command.equals("s")) {
            doViewStudents();
        } else if (command.equals("n")) {
            doViewNonStudents();
        } else if (command.equals("c")) {
            doChangeEmail();
        } else if (command.equals("d")) {
            doDeleteMember();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes memberships
    private void init() {
        firstMember = new Member("Jenny Liu", "jennyjn@students.cs.ubc.ca",
                true);
        ourMembers = new MembershipList();
        ourMembers.addMember(firstMember);
        input = new Scanner(System.in);
    }

    // EFFECTS: displays menu to the user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add member");
        System.out.println("\ts -> view students");
        System.out.println("\tn -> view non-students");
        System.out.println("\tc -> change email");
        System.out.println("\td -> delete member");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts addition of a member
    private void doAddMember() {
        System.out.print("Enter name of member: ");
        String memberName = input.nextLine();
        System.out.print("Enter email of member: ");
        String memberEmail = input.nextLine();
        System.out.print("Is member student? 1 for yes, anything else for no");
        int memberIsStudentInput = input.nextInt();

        boolean memberIsStudent = true;

        if (memberIsStudentInput == 1) {
            memberIsStudent = true;
        } else {
            memberIsStudent = false;
        }

        Member newMember = new Member(memberName, memberEmail, memberIsStudent);
        ourMembers.addMember(newMember);
    }

    // MODIFIES: this
    // EFFECTS: views all student members
    private void doViewStudents() {
        System.out.println("Here are all of our student members: "
                + ourMembers.showStudentMembers());
    }

    // MODIFIES: this
    // EFFECTS: views all non-student members
    private void doViewNonStudents() {
        System.out.println("Here are all of our non-student members: "
                + ourMembers.showNonStudentMembers());
    }

    // MODIFIES: this
    // EFFECTS: changes email of member










}
