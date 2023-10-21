import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private String rollNumber;
    private String grade;

    public Student(String name, String rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private static final String DATA_FILE = "students.txt";

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean removeStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                students.remove(student);
                return true;
            }
        }
        return false;
    }

    public Student searchStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE)) {
            for (Student student : students) {
                writer.println(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    students.add(new Student(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class StudentManagementApp {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.loadStudentsFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Management System Menu:");
            System.out.println("1. Add a student");
            System.out.println("2. Remove a student");
            System.out.println("3. Search for a student");
            System.out.println("4. Display all students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student roll number: ");
                    String rollNumber = scanner.nextLine();
                    System.out.print("Enter student grade: ");
                    String grade = scanner.nextLine();

                    if (!name.isEmpty() && !rollNumber.isEmpty() && !grade.isEmpty()) {
                        Student student = new Student(name, rollNumber, grade);
                        sms.addStudent(student);
                        sms.saveStudentsToFile();
                        System.out.println("Student added successfully.");
                    } else {
                        System.out.println("Please fill in all required fields.");
                    }
                    break;

                case 2:
                    System.out.print("Enter the roll number of the student to remove: ");
                    String removeRollNumber = scanner.nextLine();
                    if (!removeRollNumber.isEmpty()) {
                        if (sms.removeStudent(removeRollNumber)) {
                            sms.saveStudentsToFile();
                            System.out.println("Student removed successfully.");
                        } else {
                            System.out.println("Student not found.");
                        }
                    } else {
                        System.out.println("Please enter a roll number.");
                    }
                    break;

                case 3:
                    System.out.print("Enter the roll number of the student to search: ");
                    String searchRollNumber = scanner.nextLine();
                    if (!searchRollNumber.isEmpty()) {
                        Student student = sms.searchStudent(searchRollNumber);
                        if (student != null) {
                            System.out.println(student);
                        } else {
                            System.out.println("Student not found.");
                        }
                    } else {
                        System.out.println("Please enter a roll number.");
                    }
                    break;

                case 4:
                    sms.displayAllStudents();
                    break;

                case 5:
                    System.out.println("Exiting the Student Management System.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
