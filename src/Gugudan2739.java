import java.util.Scanner;

public class Gugudan2739 {
    int num;
    int result;

    public Gugudan2739() {
        Scanner sc = new Scanner(System.in);
        num = sc.nextInt();

        for (int i = 1; i < 10; i++) {
            result = num * i;
            System.out.println(num + " * " + i + " = " + result);
        }
        sc.close();
    }
}
