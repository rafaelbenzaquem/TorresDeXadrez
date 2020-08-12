package com.rafaelbenz.torresdexadrez;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author Rafael Benzaquem Neto
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
     * @param grafoBp      Matriz de adjacência de uma grafo bipartido
     * @param u            vertice no qual será verificada, se é possivel um emparelhamento
     * @param visitados    lista de vértices visitados do grafo bipartido
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

    private static class Node implements Comparable<Node> {
        Integer linha;
        Integer coluna;

        public Node(Integer linha, Integer coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(linha, node.linha) &&
                    Objects.equals(coluna, node.coluna);
        }

        @Override
        public int hashCode() {
            return Objects.hash(linha, coluna);
        }

        @Override
        public int compareTo(Node o) {
            if (this.equals(o))
                return 0;
            if (this.linha > o.linha)
                return 1;
            else if (this.linha < o.linha)
                return -1;
            else if (Objects.equals(this.linha, o.linha))
                if (this.coluna > o.coluna)
                    return 1;
                else if (this.coluna < o.coluna)
                    return -1;
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
                if (j > 0 && j < n - 1) {
                    if (tabuleiro[i][j] == 'X' && tabuleiro[i][j - 1] != 'X') {
                        countLinhas++;
                    }
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
                    if (tabuleiro[j][i] == 'X' && tabuleiro[j - 1][i] != 'X') {
                        countColunas++;
                    }
                }
            }
        }
        boolean grafoBp[][] = new boolean[totalLinhas][totalColunas];
        nodes.values().forEach(node -> grafoBp[node.linha][node.coluna] = true);
        Main m = new Main(totalColunas, totalLinhas);
        return m.emparelhamentoMaximo(grafoBp);

    }

    public static void main(String[] args) throws IOException {
        long ti = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                int n = Integer.parseInt(br.readLine());
                if (ti == 0)
                    ti = System.currentTimeMillis();
                char tabuleiro[][] = new char[n][n];
                for (int i = 0; i < n; i++) {
                    tabuleiro[i] = br.readLine().toCharArray();
                }
                System.out.println(totalDeTorres(tabuleiro, n));
            } catch (NumberFormatException ex) {

            }
        } while (br.ready());

        System.out.printf("%.4f", ((double) (System.currentTimeMillis() - ti)) / 1000.00);

    }
}
