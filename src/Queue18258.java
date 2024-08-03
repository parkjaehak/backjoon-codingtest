import java.io.*;
import java.util.StringTokenizer;

public class Queue18258 {

    static int size = 0;
    static int front = -1;
    static int back = -1;
    static int[] queue = new int[2000000];	// 명령의 수는 2,000,000을 안넘음


    public Queue18258() throws IOException {
        // InputStreamReader와 BufferedReader를 사용하여 빠르게 입력을 읽음
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int inputNumber = Integer.parseInt(br.readLine());
        if(inputNumber<1||inputNumber>2000000)
            return;

        // 출력 버퍼
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<inputNumber;i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String menu = st.nextToken();

            switch (menu) {
                case "push":
                    push(Integer.parseInt(st.nextToken()));
                    break;
                case "pop" :
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

    public void push(int number) {
        if (front == -1 && back == -1) {
            front++; back ++;
        }
        else {
            back++;
        }
        queue[back] = number;
        size++;
    }

    public int pop() {
        if (size <= 0) {
            return -1;
        }
        int pop = queue[front++];
        size--;
        return pop;
    }

    public int size() {
        if (back < front || (back == -1 && front == -1)) {
            return 0;
        }
        return back - front + 1;
    }

    public int empty() {
        if(size()<=0)
            return 1;
        else
            return 0;
    }

    public int front() {
        return queue[front];
    }

    public int back() {
        return queue[back];
    }
}

