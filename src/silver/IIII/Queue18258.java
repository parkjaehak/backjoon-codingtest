package silver.IIII;

import java.io.*;
import java.util.StringTokenizer;

public class Queue18258 {

    static int size = 0;
    static int front = -1;
    static int back = -1;
    static int[] queue = new int[2000000];


    public Queue18258() throws IOException {
        // InputStreamReader와 BufferedReader를 사용하여 빠르게 입력을 읽음
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int inputNumber = Integer.parseInt(br.readLine());
        if (inputNumber < 1 || inputNumber > 2000000)
            return;

        // 출력 버퍼
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inputNumber; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String menu = st.nextToken();

            switch (menu) {
                case "push":
                    push(Integer.parseInt(st.nextToken()));
                    break;
                case "pop":
                    sb.append(pop()).append("\n");
                    break;
                case "size":
                    sb.append(size()).append("\n");
                    break;
                case "empty":
                    sb.append(empty()).append("\n");
                    break;
                case "front":
                    sb.append(front()).append("\n");
                    break;
                case "back":
                    sb.append(back()).append("\n");
                    break;
            }
        }
        bw.write(sb.toString());
        bw.flush();
        bw.close();
        br.close();
    }

    public static void push(int number) {
        if (front == -1 && back == -1) {
            front++;back++;
        } else {
            back++;
        }
        queue[back] = number;
        size++;
    }

    public static int pop() {
        if (size <= 0) {
            front = -1;
            back = -1;
            return -1;
        }
        int pop = queue[front++];
        size--;
        return pop;
    }

    public static int size() {
        if (back < front || (back == -1 && front == -1)) {
            return 0;
        }
        return back - front + 1;
    }

    public static int empty() {
        if (size() <= 0)
            return 1;
        else
            return 0;
    }

    public static int front() {
        if (size() <= 0)
            return -1;
        return queue[front];
    }

    public static int back() {
        if (size() <= 0)
            return -1;
        return queue[back];
    }
}

