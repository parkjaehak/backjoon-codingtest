package silver.IIII;

import java.io.*;
import java.util.*;

public class NotHearSee1764 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static int N, M;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //듣지 못한 사람
        M = Integer.parseInt(st.nextToken()); //보지 못한 사람

        Set<String> nSet = new HashSet<>();
        for (int i = 0; i < N; i++) {
            nSet.add(br.readLine());
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            String name = br.readLine();
            if (nSet.contains(name)) {
                result.add(name); //nSet과 같은 이름을 가진 사람만 저장한다.
            }
        }

        Collections.sort(result);

        sb.append(result.size()).append('\n');
        for (int i = 0; i < result.size(); i++) {
            sb.append(result.get(i)).append('\n');
        }
        bw.write(sb.toString());
        bw.flush();
    }
}
