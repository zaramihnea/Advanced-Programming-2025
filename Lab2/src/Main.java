public class Main {
    public static void main(String[] args) {
        Project p1 = new Project("P1", "Machine Learning", ProjectType.THEORETICAL);
        Project p2 = new Project("P2", "Web Development", ProjectType.PRACTICAL);
        Project p3 = new Project("P3", "Artificial Intelligence", ProjectType.THEORETICAL);
        Project p4 = new Project("P4", "Java", ProjectType.PRACTICAL);

        Student s1 = new Student("S1", "Alice");
        Student s2 = new Student("S2", "Bob");
        Student s3 = new Student("S3", "Charlie");
        Student s4 = new Student("S4", "Diana");

        s1.addAcceptableProject(p1);
        s1.addAcceptableProject(p2);

        s2.addAcceptableProject(p1);
        s2.addAcceptableProject(p3);

        s3.addAcceptableProject(p3);
        s3.addAcceptableProject(p4);

        s4.addAcceptableProject(p1);
        s4.addAcceptableProject(p4);

        s1.setAssignedProject(p2);
        s2.setAssignedProject(p1);
        s3.setAssignedProject(p3);
        s4.setAssignedProject(p4);

        System.out.println("Projects:");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        System.out.println("\nStudents:");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);

        System.out.println("\nSolution:");
        System.out.println("[(S1:" + s1.getAssignedProject().getId() + ")," +
                "(S2:" + s2.getAssignedProject().getId() + ")," +
                "(S3:" + s3.getAssignedProject().getId() + ")," +
                "(S4:" + s4.getAssignedProject().getId() + ")]");
    }
}