package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class ConferenceAssign1931 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static Info[] array;


    public static void main(String[] args) throws IOException {
        //회의가 겹치지 않으면서 회의실을 사용가능한 회의의 최대 개수
        N = Integer.parseInt(br.readLine());
        array = new Info[N];

        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            array[n] = new Info(start, end);
        }


        //1. 회의 정렬
        Arrays.sort(array, new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                //우선 종료시간을 기준을 오름차순 정렬한다. 종료시간이 같은 경우 시작시간을 기준으로 오름차순정렬한다.
                if (o1.et == o2.et) {
                    return Integer.compare(o1.st, o2.st);
                }
                return Integer.compare(o1.et, o2.et);
            }
        });

        //2. 회의 선택
        //다음 선택한 회의는 현재 선택된 회의의 종료 시간보다 시작 시간이 늦거나 같아야 한다.
        //즉, 첫 번째 회의를 선택한 후, 그 회의가 끝난 후에 시작할 수 있는 회의를 찾아 선택한다.
        int count = 1;
        int lastEndTime = array[0].et;

        for (int i = 1; i < array.length; i++) {
            if (array[i].st >= lastEndTime) {
                //현재 회의가 끝나는 시간보다 다음 회의 시작시간이 더 늦게 와야한다.
                count++;
                lastEndTime = array[i].et; //선택된 회의의 종료시간으로 갱신한다.
            }
        }
        //3. 최대 개수 계산
        System.out.println(count);
    }

    static class Info {
        int st, et;

        public Info(int st, int et) {
            this.st = st;
            this.et = et;
        }
    }

}
