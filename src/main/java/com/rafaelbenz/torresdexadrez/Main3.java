/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rafaelbenz.torresdexadrez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author Rafael Benzaquem Neto
 */
public class Main3 {

    public static void main(String args[]) throws IOException {

        boolean res[][];
        boolean colunasUsadas[];
        boolean linhasUsadas[];
        int no;
        int N;
        int linhas[];
        int colunas[];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                N = Integer.parseInt(br.readLine());
                no = 0;
                res = new boolean[N * N + N][N * N + N];
                colunasUsadas = new boolean[N];
                linhasUsadas = new boolean[N];

                linhas = new int[N];
                colunas = new int[N];
                for (int i = 0; i < N; i++) {
                    linhas[i] = no++;
                    colunas[i] = no++;
                }

                for (int i = 0; i < N; i++) {
                    String entrada = br.readLine();
                    for (int j = 0; j < N; j++) {

                        if (entrada.charAt(j) == 'X') {
                            if (linhasUsadas[i]) {
                                linhas[i] = no++;
                            }
                            if (colunasUsadas[j]) {
                                colunas[j] = no++;
                            }
                        } else {
                            linhasUsadas[i] = true;
                            colunasUsadas[j] = true;
                            res[linhas[i]][colunas[j]] = true;

                        }

                    }
                }

                System.out.println(maxMatching(res));

            } catch (NumberFormatException ex) {

            } finally {
                //tira referência da meméria e limpa com System.gc()
                res = null;
                colunasUsadas = null;
                linhasUsadas = null;

                linhas = null;
                colunas = null;
                System.gc();
            }
        } while (br.ready());

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