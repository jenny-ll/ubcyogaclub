package ui;

// Yoga Member Application
// Citation: parts of the Teller example, JSonSerializationDemo

import exception.InvalidEmailException;
import model.Member;
import model.MembershipList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the Yoga Membership Application

public class YogaApp {

    private static final String JSON_STORE = "./data/membershiplist.json";
    private MembershipList ourMembers;
    private Member firstMember;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the yoga application
    public YogaApp() throws FileNotFoundException, InvalidEmailException {
        input = new Scanner(System.in);
        ourMembers = new MembershipList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runYogaApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runYogaApp() throws InvalidEmailException {
        boolean keepGoing = true;
        String command = null;

        init();

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
    private void processCommand(String command) throws InvalidEmailException {
        if (command.equals("a")) {
            doAddMember();
        } else if (command.equals("s")) {
            doViewStudents();
        } else if (command.equals("n")) {
            doViewNonStudents();
        } else if (command.equals("d")) {
            doDeleteMember();
        } else if (command.equals("f")) {
            doFindMember();
        } else if (command.equals("y")) {
            saveMembershipList();
        } else if (command.equals("z")) {
            loadMembershipList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes memberships
    private void init() throws InvalidEmailException {
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
        System.out.println("\td -> delete member");
        System.out.println("\tf -> find member");
        System.out.println("\ty -> save membership list to file");
        System.out.println("\tz -> load membership list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts addition of a member
    private void doAddMember() throws InvalidEmailException {
        System.out.print("Enter name of member: ");
        String memberName = input.next();
        input.nextLine();
        System.out.print("Enter email of member: ");
        String memberEmail = input.nextLine();
        System.out.print("Is member student? 1 for yes, any other number for no: ");
        int memberIsStudentInput = input.nextInt();

        boolean memberIsStudent = true;

        if (memberIsStudentInput == 1) {
            memberIsStudent = true;
        } else {
            memberIsStudent = false;
        }

        Member newMember;
        newMember = new Member(memberName, memberEmail, memberIsStudent);
        ourMembers.addMember(newMember);
        System.out.println("Member added.");
    }

    // MODIFIES: this
    // EFFECTS: views all student members
    private void doViewStudents() {
        List<String> ourStudents = new ArrayList<>();
        for (Member member : ourMembers.showStudentMembers()) {
            ourStudents.add(member.getName());
        }
        System.out.println("Here are all of our student members: "
                + ourStudents);
    }

    // MODIFIES: this
    // EFFECTS: views all non-student members
    private void doViewNonStudents() {
        List<String> ourNonStudents = new ArrayList<>();
        for (Member member : ourMembers.showNonStudentMembers()) {
            ourNonStudents.add(member.getName());
        }
        System.out.println("Here are all of our non-student members: "
                + ourNonStudents);
    }

    // MODIFIES: this
    // EFFECTS: deletes the member
    private void doDeleteMember() {
        System.out.println("ID of member you wish to delete: ");
        int deleteMemberID = input.nextInt();

        if (!(ourMembers.findMember(deleteMemberID).isEmpty())) {
            ourMembers.deleteMember(deleteMemberID);
            System.out.println("Successfully processed.");
        } else {
            System.out.println("Sorry, that's not a valid ID.");
        }
    }

    // EFFECTS: finds the member with ID number
    private void doFindMember() {
        System.out.println("ID of member you wish to find: ");
        int findMemberID = input.nextInt();

        if (!(ourMembers.findMember(findMemberID).isEmpty())) {
            System.out.println("The name of that member is: "
                    + ourMembers.findMember(findMemberID));
        } else {
            System.out.println("Sorry, that's not a valid ID.");
        }
    }

    // EFFECTS: saves the membership list to file
    private void saveMembershipList() {
        try {
            jsonWriter.open();
            jsonWriter.write(ourMembers);
            jsonWriter.close();
            System.out.println("Saved membership list to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads membership list from file
    private void loadMembershipList() {
        try {
            ourMembers = jsonReader.read();
            System.out.println("Loaded membership list from " + JSON_STORE);
        } catch (IOException | InvalidEmailException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}