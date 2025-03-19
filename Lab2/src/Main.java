import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main demonstration class for the Student Project Allocation Problem.
 * Shows the creation and usage of all classes in the system.
 */
public class Main {
    public static void main(String[] args) {
        // Create a SimpleDateFormat for parsing dates
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Create teachers
            Teacher t1 = new Teacher("T1", "Prof. Johnson", sdf.parse("1970-05-15"));
            Teacher t2 = new Teacher("T2", "Prof. Smith", sdf.parse("1975-08-22"));

            // Create projects
            Project p1 = new Project("P1", "Machine Learning", ProjectType.THEORETICAL);
            Project p2 = new Project("P2", "Web Development", ProjectType.PRACTICAL);
            Project p3 = new Project("P3", "Artificial Intelligence", ProjectType.THEORETICAL);
            Project p4 = new Project("P4", "Java Programming", ProjectType.PRACTICAL);

            // Assign teachers to projects
            t1.addProposedProject(p1);
            t1.addProposedProject(p3);
            t2.addProposedProject(p2);
            t2.addProposedProject(p4);

            // Create students
            Student s1 = new Student("S1", "Alice", sdf.parse("1998-03-10"));
            Student s2 = new Student("S2", "Bob", sdf.parse("1999-07-20"));
            Student s3 = new Student("S3", "Charlie", sdf.parse("1997-11-05"));
            Student s4 = new Student("S4", "Diana", sdf.parse("1998-09-15"));

            // Set student preferences
            s1.addAcceptableProject(p1);
            s1.addAcceptableProject(p2);

            s2.addAcceptableProject(p1);
            s2.addAcceptableProject(p3);

            s3.addAcceptableProject(p3);
            s3.addAcceptableProject(p4);

            s4.addAcceptableProject(p1);
            s4.addAcceptableProject(p4);

            // Create problem instance
            Problem problem = new Problem();

            // Add students
            problem.addStudent(s1);
            problem.addStudent(s2);
            problem.addStudent(s3);
            problem.addStudent(s4);

            // Add teachers
            problem.addTeacher(t1);
            problem.addTeacher(t2);

            // Add projects
            problem.addProject(p1);
            problem.addProject(p2);
            problem.addProject(p3);
            problem.addProject(p4);

            // Print the problem details
            System.out.println("Problem Details:");
            System.out.println(problem);

            // Show all persons involved
            System.out.println("\nAll Persons Involved:");
            Person[] allPersons = problem.getAllPersons();
            for (Person person : allPersons) {
                System.out.println(person);
            }

            // Solve the problem using greedy algorithm
            Solution solution = problem.solveGreedy();

            // Print the solution
            System.out.println("\nGreedy Solution:");
            System.out.println(solution);

            // Also print assigned projects for each student
            System.out.println("\nStudent Assignments:");
            for (Student student : problem.getStudents()) {
                System.out.println(student.getId() + ": " +
                        (student.getAssignedProject() != null ?
                                student.getAssignedProject().getId() : "No project assigned"));
            }

            // Test the equals method by trying to add the same student twice
            Student duplicateStudent = new Student("S1", "Alice", sdf.parse("1998-03-10"));
            boolean added = problem.addStudent(duplicateStudent);
            System.out.println("\nTrying to add duplicate student S1: " +
                    (added ? "Added" : "Not added (already exists)"));

        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
    }
}