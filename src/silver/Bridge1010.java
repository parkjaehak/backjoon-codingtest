package silver;

import java.io.*;
import java.util.StringTokenizer;

public class Bridge1010 {

    public Bridge1010() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int inputNumber = Integer.parseInt(bufferedReader.readLine());
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < inputNumber; i++) {
            StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            int r = Integer.parseInt(stringTokenizer.nextToken());
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int nr = n - r;

            if (nr < r) {
               r = nr;
            }
            int result = 1;
            for (int j = 1; j <= r; j++) {
                result = result * (n - j + 1) / j; // nCr = n(n-1)(n-2) ... (n- (k-1)) / k(k-1)...1
            }
            stringBuilder.append(result).append("\n");
        }
        System.out.println(stringBuilder);
    }
}
