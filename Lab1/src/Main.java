public class Main {
    public static void main(String[] args) {
        // Step 1
        System.out.println("Hello World!");

        // Step 2
        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

        int n = (int) (Math.random() * 1_000_000);
        System.out.println("random number: " + n);

        long result = n * 3L;
        System.out.println(result);

        result += 0b10101;
        System.out.println(result);

        result += 0xFF;
        System.out.println(result);

        result *= 6;
        System.out.println(result);

        int digitSum = 0;
        long tempNumber = result;

        while (tempNumber > 0) {
            digitSum += tempNumber % 10;
            tempNumber /= 10;
        }

        while (digitSum >= 10) {
            int sum = 0;
            long temp = digitSum;

            while (temp > 0) {
                sum += temp % 10;
                temp /= 10;
            }

            digitSum = sum;
        }

        System.out.println("sum of digits: " + digitSum);

        System.out.println("Willy-nilly, this semester I will learn " + languages[digitSum]);
    }
}