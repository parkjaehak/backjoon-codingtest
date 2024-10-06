package silver.I;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TreeTraversal1991 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        //노드를 관리하는 트리 맵
        Map<Character, Node> tree = new HashMap<>();

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            char parent = st.nextToken().charAt(0);
            char left = st.nextToken().charAt(0);
            char right = st.nextToken().charAt(0);

            tree.putIfAbsent(parent, new Node(parent));
            Node parentNode = tree.get(parent);

            if (left != '.') {
                tree.putIfAbsent(left, new Node(left));
                parentNode.left = tree.get(left);
            }

            if (right != '.') {
                tree.putIfAbsent(right, new Node(right));
                parentNode.right = tree.get(right);
            }
        }

        Node rootNode = tree.get('A');
        preorder(rootNode);
        sb.append("\n");
        inorder(rootNode);
        sb.append("\n");
        postorder(rootNode);

        bw.write(sb.toString());
        bw.flush();

    }

    static void preorder(Node node) {
        if (node == null) {
            return;
        }
        sb.append(node.value);
        preorder(node.left);
        preorder(node.right);
    }

    static void inorder(Node node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        sb.append(node.value);
        inorder(node.right);
    }

    static void postorder(Node node) {
        if (node == null) {
            return;
        }
        postorder(node.left);
        postorder(node.right);
        sb.append(node.value);
    }



    static class Node {
        char value;
        Node left;
        Node right;

        Node(char value) {
            this.value = value;
        }
    }
}
