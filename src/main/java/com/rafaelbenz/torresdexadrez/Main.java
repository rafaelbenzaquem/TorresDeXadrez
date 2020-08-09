package com.rafaelbenz.torresdexadrez;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Rafael Benzaquem Neto
 *
 */
public class Main {

    //total de vértices do conjunto N do grafo bipartido
    private final int N;
    //total de vértices do conjunto M do grafo bipartido
    private final int M;

    public Main(int N, int M) {
        this.N = N;
        this.M = M;
    }

    /**
     * Uma função que é baseada no algoritmo recursivo da busca em profundidade
     * DFS( aqui é possível usar o algoritmo de busca em largura BFS, todos com
     * complexidade linear)
     *
     * @param grafoBp Matriz de adjacência de uma grafo bipartido
     * @param u vertice no qual será verificada, se é possivel um emparelhamento
     * @param visitados lista de vértices visitados do grafo bipartido
     * @param emparelhados
     * @return retorna true se for possível o emparelhamento do vértice 'u'
     */
    private boolean buscaEmparelhar(boolean grafoBp[][], int u,
            boolean visitados[], int emparelhados[]) {
        //itera por todos os vértices do conjunto N do grafo bipartido
        for (int v = 0; v < N; v++) {

            /*se o vértice 'u' em M e o vertice 'v' em N forma uma aresta
            no grafo bipartido e se 'v' não foi visitado*/
            if (grafoBp[u][v] && !visitados[v]) {

                // marca 'v' como visitado
                visitados[v] = true;

                /*
                   Se o vértice 'v' em N não está emparelhado em um 
                   vértice 'emparelhados[v]' em M ou chama a função recursiva para emperelhar o próximo vértice
                   
                 */
                if (emparelhados[v] < 0 || buscaEmparelhar(grafoBp, emparelhados[v], visitados, emparelhados)) {
                    emparelhados[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param grafoBp matriz de adjacência de um grafo bipartido
     * @return o emparelhamento máximo do do grafo bipartido
     */
    public int emparelhamentoMaximo(boolean grafoBp[][]) {
        /*
        Um array para verificar os vértices de N que estão emparelhados com os 
        vertices de M, o valor em 'emparelhados[i]' é o vétice do conjunto M e 'i' 
        é o vertice do conjunto N. Caso o valor de 'emparelhados[i]' seja -1, indica 
        que o vertice 'i' em N não está emparelhado com nenhum vertice do conjunto M.
         */
        int emparelhados[] = new int[N];

        // inicializa o array de verificação 
        for (int i = 0; i < N; ++i) {
            emparelhados[i] = -1;
        }

        // contador de emparelhamento
        int result = 0;
        //itera nos vertices do conjunto M
        for (int u = 0; u < M; u++) {
            // array de verificação para busca em pofundidade, ele verifica se os
            //vértices do conjunto N foram visitados pela função isEmparelhado
            boolean visitados[] = new boolean[N];
            for (int i = 0; i < N; ++i) {
                visitados[i] = false;
            }

            // Verifica se o vértice 'u' em M pode ser emparelhado em um vértice 
            //em N que não foi visitado
            if (buscaEmparelhar(grafoBp, u, visitados, emparelhados)) {
                result++;
            }
        }
        return result;
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

        Main m = new Main(totalColunas, totalLinhas);

        return m.emparelhamentoMaximo(grafoBp);

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
