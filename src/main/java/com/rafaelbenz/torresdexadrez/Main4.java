package com.rafaelbenz.torresdexadrez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Rafael Benzaquem Neto
 */
public class Main4 {

    private static class Node implements Comparable<Node> {

        Integer linha;
        Integer coluna;

        public Node(Integer linha, Integer coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return Objects.equals(linha, node.linha)
                    && Objects.equals(coluna, node.coluna);
        }

        @Override
        public int hashCode() {
            return Objects.hash(linha, coluna);
        }

        @Override
        public int compareTo(Node o) {
            if (this.equals(o)) {
                return 0;
            }
            if (this.linha > o.linha) {
                return 1;
            } else if (this.linha < o.linha) {
                return -1;
            } else if (Objects.equals(this.linha, o.linha)) {
                if (this.coluna > o.coluna) {
                    return 1;
                } else if (this.coluna < o.coluna) {
                    return -1;
                }
            }
            return 0;
        }
    }

    public static int totalDeTorres(char tabuleiro[][], int n) {

        int countColunas = 0, countLinhas = 0, totalLinhas = 0, totalColunas = 0;

        Map<Node, Node> nodes = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tabuleiro[i][j] != 'X') {
                    int linha = i + countLinhas;
                    Node key = new Node(i, j);
                    if (nodes.containsKey(key)) {
                        nodes.get(key).linha = linha;
                    } else {
                        nodes.put(key, new Node(linha, null));
                    }
                    totalLinhas = i + countLinhas + 1;
                }

                if (tabuleiro[j][i] != 'X') {
                    int coluna = i + countColunas;
                    Node key = new Node(j, i);
                    if (nodes.containsKey(key)) {
                        nodes.get(key).coluna = coluna;
                    } else {
                        nodes.put(key, new Node(null, coluna));
                    }
                    totalColunas = i + countColunas + 1;
                }
                if (j > 0 && j < n - 1) {
                    if (tabuleiro[i][j] == 'X' && tabuleiro[i][j - 1] != 'X') {
                        countLinhas++;
                    }
                    if (tabuleiro[j][i] == 'X' && tabuleiro[j - 1][i] != 'X') {
                        countColunas++;
                    }
                }

            }
        }
        List<Integer>[] g = new List[totalLinhas];
        Arrays.fill(g, new ArrayList<>());
        nodes.values().forEach(node -> g[node.linha].add(node.coluna));
        return maxMatching(g, totalColunas);
    }

    public static void main(String[] args) throws IOException {
        long ti = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                int n = Integer.parseInt(br.readLine());
                if (ti == 0) {
                    ti = System.currentTimeMillis();
                }
                char tabuleiro[][] = new char[n][n];
                for (int i = 0; i < n; i++) {
                    tabuleiro[i] = br.readLine().toCharArray();
                }
                System.out.println(totalDeTorres(tabuleiro, n));
            } catch (NumberFormatException ex) {

            }
        } while (br.ready());

        System.out.printf("%.4f", ((double) System.currentTimeMillis() - ti) / 1000.00);
    }

    public static int maxMatching(List<Integer>[] graph, int n2) {
        int n1 = graph.length;
        int[] matching = new int[n2];
        Arrays.fill(matching, -1);
        int matches = 0;
        for (int u = 0; u < n1; u++) {
            if (findPath(graph, u, matching, new boolean[n1])) {
                ++matches;
            }
        }
        return matches;
    }

    static boolean findPath(List<Integer>[] graph, int u1, int[] matching, boolean[] vis) {
        vis[u1] = true;
        for (int v : graph[u1]) {
            int u2 = matching[v];
            if (u2 == -1 || !vis[u2] && findPath(graph, u2, matching, vis)) {
                matching[v] = u1;
                return true;
            }
        }
        return false;
    }

}
