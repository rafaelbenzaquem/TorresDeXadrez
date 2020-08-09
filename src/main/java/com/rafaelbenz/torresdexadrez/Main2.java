package com.rafaelbenz.torresdexadrez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author Rafael Benzaquem Neto
 *
 */
public class Main2 {

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

    public static int totalDeTorres(char tabuleiro[][], int n) {

        int[][] linhas = new int[n][n];
        int[][] colunas = new int[n][n];

        int countColunas = 0, countLinhas = 0, totalLinhas = 0, totalColunas = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tabuleiro[i][j] != 'X') {
                    linhas[i][j] = i + countLinhas;
                    totalLinhas = i + countLinhas + 1;
                } else {
                    linhas[i][j] = -1;
                }
                if (j > 0 && j < n - 1) {
                    if (tabuleiro[i][j] == 'X' && tabuleiro[i][j - 1] != 'X') {
                        countLinhas++;
                    }
                }

                if (tabuleiro[j][i] != 'X') {
                    colunas[j][i] = i + countColunas;
                    totalColunas = i + countColunas + 1;
                } else {
                    colunas[j][i] = -1;
                }
                if (j > 0 && j < n - 1) {
                    if (tabuleiro[j][i] == 'X' && tabuleiro[j - 1][i] != 'X') {
                        countColunas++;
                    }
                }
            }
        }
        boolean grafoBp[][] = new boolean[totalLinhas][totalColunas];
        for (int i = 0; i < linhas.length; i++) {
            for (int j = 0; j < linhas[0].length; j++) {
                if (linhas[i][j] != -1) {
                    grafoBp[linhas[i][j]][colunas[i][j]] = true;
                }
            }
        }

       return maxMatching(grafoBp);

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                int n = Integer.parseInt(br.readLine());
                char tabuleiro[][] = new char[n][n];
                for (int i = 0; i < n; i++) {
                    String linha = br.readLine();
                    for (int j = 0; j < n; j++) {
                        tabuleiro[i][j] = linha.charAt(j);
                    }
                }
                System.out.println(totalDeTorres(tabuleiro, n));
            } catch (NumberFormatException ex) {

            }
        } while (br.ready());
    }

}
