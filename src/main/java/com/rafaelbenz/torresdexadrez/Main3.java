/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafaelbenz.torresdexadrez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Rafael Benzaquem Neto
 */
class Node {

    int linha;
    int coluna;
    boolean linhaUsada;
    boolean colunaUsada;

    public Node(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    @Override
    public String toString() {
        return "Node{" + "linha=" + linha + ", coluna=" + coluna + ", linhaUsada=" + linhaUsada + ", colunaUsada=" + colunaUsada + '}';
    }

}

public class Main3 {

    public static void main(String args[]) throws IOException {
        long ti = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {

                int N = Integer.parseInt(br.readLine());
                if (ti == 0) {
                    ti = System.currentTimeMillis();
                }
                int no = 0;
                List<Node> res = new ArrayList<>();

                List<Node> nodes = new ArrayList<>();

                for (int i = 0; i < N; i++) {
                    nodes.add(new Node(no++, no++));
                }

                for (int i = 0; i < N; i++) {
                    String entrada = br.readLine();
                    for (int j = 0; j < N; j++) {
                        if (entrada.charAt(j) == 'X') {
                            Node linha = nodes.get(i);
                            Node coluna = nodes.get(j);
                            if (linha.linhaUsada) {
                                linha.linha = no++;
                            }
                            if (coluna.colunaUsada) {
                                coluna.coluna = no++;
                            }
                        } else {
                            Node linha = nodes.get(i);
                            linha.linhaUsada = true;
                            Node coluna = nodes.get(j);
                            coluna.colunaUsada = true;
                            res.add(new Node(linha.linha, coluna.coluna));
                        }
                    }
                }

                boolean grafoBp[][] = new boolean[no][no];
                res.forEach(g -> grafoBp[g.linha][g.coluna] = true);
                System.out.println(maxMatching(grafoBp));
                res = null;
                nodes = null;
            } catch (NumberFormatException ex) {

            }

        } while (br.ready());
        System.out.printf("%.4f", ((double) System.currentTimeMillis() - ti) / 1000.00);

    }

    public static int maxMatching(boolean[][] graph) {
        int n1 = graph.length;
        int n2 = n1 == 0 ? 0 : graph[0].length;
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

    static boolean findPath(boolean[][] graph, int u1, int[] matching, boolean[] vis) {
        vis[u1] = true;
        for (int v = 0; v < matching.length; ++v) {
            int u2 = matching[v];
            if (graph[u1][v] && (u2 == -1 || !vis[u2] && findPath(graph, u2, matching, vis))) {
                matching[v] = u1;
                return true;
            }
        }
        return false;
    }
}
