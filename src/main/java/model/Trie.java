/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author mitch
 */

/**
 * Clase que representa una estructura de datos Trie.
 * Un Trie es una estructura de árbol especializada para almacenar y buscar palabras o cadenas eficientemente.
 * Se compone de nodos hoja y no hoja que forman una estructura de árbol basada en prefijos de palabras.
 */
public class Trie {

    /**
     * El nodo raíz del Trie.
     */
    public TrieNonLeaf root;
    
    /**
     * Valor constante para indicar que un elemento no ha sido encontrado.
     */
    public final int notFound = -1;

    /**
     * Constructor por defecto
     */
    public Trie() {
        
    }

    /**
     * Constructor que inicializa el Trie con una palabra.
     * 
     * @param word La palabra para inicializar el Trie.
     */
    public Trie(String word) {
        root = new TrieNonLeaf(word.charAt(0)); // initialize the root
        createLeaf(word.charAt(0), word.substring(1), root); // to avoid later
    } // test;

    
    /**
     * Método para imprimir visualmente la estructura del Trie.
     */
    public void printTrie() {   
        if (root == null) {
            System.out.println("Root is null");
            return;
        }
        printTrie(0, root, new String());
    }

    
    /**
    * Método para imprimir visualmente la estructura del Trie, comenzando desde el nodo raíz.
    * 
    * @param depth  La profundidad actual dentro del Trie.
    * @param p      El nodo actual que se está examinando.
    * @param prefix El prefijo acumulado hasta el nodo actual.
    */
    protected void printTrie(int depth, TrieNode p, String prefix) {

        if (p.isLeaf) {
            for (int j = 1; j <= depth; j++) {
                System.out.print(" ");
            }
            System.out.println(" >" + prefix + "|" + ((TrieLeaf) p).suffix);
        } else {
            for (int i = ((TrieNonLeaf) p).letters.length() - 1; i >= 0; i--) {
                if (((TrieNonLeaf) p).ptrs[i] != null) {
                    // Agrega la letra correspondiente a la posición i al prefijo
                    prefix = prefix.substring(0, depth)
                            + ((TrieNonLeaf) p).letters.charAt(i);
                    sideView(depth + 1, ((TrieNonLeaf) p).ptrs[i], prefix);
                } else { // Si la hoja esta vacía;           
                    for (int j = 1; j <= depth + 1; j++) {
                        System.out.print(" ");
                    }
                    System.out.println(" >>" + prefix.substring(0, depth)
                            + ((TrieNonLeaf) p).letters.charAt(i));
                }

            }
            if (((TrieNonLeaf) p).endOfWord) {
                for (int j = 1; j <= depth + 1; j++) {
                    System.out.print(" ");
                }
                System.out.println(" >>" + prefix.substring(0, depth));
            }
        }
    }
    
    

    /**
     * Método interno para encontrar la posición de un carácter dentro de un nodo no hoja.
     * 
     * @param p  El nodo no hoja en el que se busca el carácter.
     * @param ch El carácter a buscar.
     * @return La posición del carácter si se encuentra, de lo contrario, notFound.
     */
    private int position(TrieNonLeaf p, char ch) {
        int i = 0;
        for (; i < p.letters.length() && p.letters.charAt(i) != ch; i++);
        if (i < p.letters.length()) {
            return i;
        } else {
            return notFound;
        }
    }

    
    /**
     * Método para comprobar si una palabra está presente en el Trie.
     * 
     * @param word La palabra a buscar en el Trie.
     * @return true si la palabra está presente, false de lo contrario.
     */
    public boolean found(String word) {

        TrieNode p = root;
        int pos, i = 0;
        while (true) {
            if (p.isLeaf) { // El nodo p es una hoja
                TrieLeaf lf = (TrieLeaf) p; // donde debe encontrarse
                if (word.substring(i).equals(lf.suffix)) // el sufijo de la palabra que coincide
                {
                    return true; 
                } else {
                    return false;
                }
            } else if ((pos = position((TrieNonLeaf) p, word.charAt(i))) != notFound
                    && i + 1 == word.length()) // el final de la palabra debe corresponder a una hoja vacía
            {
                if (((TrieNonLeaf) p).ptrs[pos] == null)
                {
                    return true;
                } else if (!(((TrieNonLeaf) p).ptrs[pos]).isLeaf
                        && ((TrieNonLeaf) ((TrieNonLeaf) p).ptrs[pos]).endOfWord) {
                    return true; // o el marcado endOfWord esta activo
                } else {
                    return false;
                }
            } else if (pos != notFound && ((TrieNonLeaf) p).ptrs[pos] != null) {
                p = ((TrieNonLeaf) p).ptrs[pos];// continua el camino si es posible
                i++;
            } else {
                return false; // de lo contrario fracasa
            }
        }
    }

    /**
     * Método para insertar una palabra en el Trie.
     * 
     * @param word La palabra a insertar en el Trie.
     */
    private void addCell(char ch, TrieNonLeaf p, int stop) {

        int i;
        int len = p.letters.length();
        char[] s = new char[len + 1];
        TrieNode[] tmp = p.ptrs;
        p.ptrs = new TrieNode[len + 1];

        for (i = 0; i < len + 1; i++) {
            p.ptrs[i] = null;
        }
        if (stop < len) // si 'ch' no sigue todas las letras de 'p'
        {
            for (i = len; i >= stop + 1; i--) { // copia de tmp las letras > ch;
                p.ptrs[i] = tmp[i - 1];
                s[i] = p.letters.charAt(i - 1);
            }
        }
        s[stop] = ch;

        for (i = stop - 1; i >= 0; i--) { // y las letras < ch;
            p.ptrs[i] = tmp[i];
            s[i] = p.letters.charAt(i);
        }

        p.letters = new String(s);
    }

    
     /**
     * Método interno para crear un nodo hoja dentro del Trie.
     * 
     * @param ch      El carácter para crear el nodo hoja.
     * @param suffix  El sufijo a almacenar en el nodo hoja.
     * @param p       El nodo no hoja donde se creará el nodo hoja.
     */
    private void createLeaf(char ch, String suffix, TrieNonLeaf p) {
        int pos = position(p, ch);
        TrieLeaf lf = null;
        if (suffix != null && suffix.length() > 0) // No crea ninguna hoja si no hay sufijo
        {
            lf = new TrieLeaf(suffix);
        }
        if (pos == notFound) {
            for (pos = 0; pos < p.letters.length()
                    && p.letters.charAt(pos) < ch; pos++);
            addCell(ch, p, pos);
        }
        p.ptrs[pos] = lf;
    }

    
    /**
     * Método para insertar una palabra en el Trie.
     * 
     * @param word La palabra a insertar en el Trie.
     */
    public void insert(String word) {
        TrieNonLeaf p = root;
        TrieLeaf lf;
        int offset, pos, i = 0;
        while (true) {
            if (i == word.length()) { // si se llega al final de la palabra, entonces endOfWord es true
                if (p.endOfWord) {
                    System.out.println("duplicate entry1: " + word);
                }
                p.endOfWord = true; // Establece endOfWord como true
                return;
            } // Si se indico la posicion en 'p'
            pos = position(p, word.charAt(i));
            if (pos == notFound) { // por la primera letra de la palabra
                createLeaf(word.charAt(i), word.substring(i + 1), p);
                // No existe, crea una hoja y almacena en ella el sufijo sin procesar de la palabra
                return; 
            } 
            else if (pos != notFound &&  p.ptrs[pos] == null ) { // La hoja vacia en posicion pos  
                if (i + 1 == word.length()) {
                    System.out.println("duplicate entry1: " + word);
                    return;
                }
                p.ptrs[pos] = new TrieNonLeaf(word.charAt(i + 1));
                ((TrieNonLeaf) (p.ptrs[pos])).endOfWord = true;
                // Revisa si queda algun sufijo
                String s = (word.length() > i + 2) ? word.substring(i + 2) : null;
                createLeaf(word.charAt(i + 1), s, (TrieNonLeaf) (p.ptrs[pos]));
                return;
            } else if (pos != notFound
                    && // Si la posicion esta ocupada por una hoja
                    p.ptrs[pos].isLeaf) { 
                lf = (TrieLeaf) p.ptrs[pos]; // Almacena esta hoja
                if (lf.suffix.equals(word.substring(i + 1))) {
                    System.out.println("duplicate entry2: " + word);
                    return;
                }
                offset = 0;
                // Crear tantos nodos no hoja como la longitud del prefijo idéntico de la palabra y la cadena en la hoja 
                //(para la celda 'R', la hoja "EP", y la palabra "REAR", 
                // se crean dos nodos de este tipo);
                do {
                    pos = position(p, word.charAt(i + offset));
                    // word = "ABC", leaf = "ABCDEF" => leaf = "DEF";
                    if (word.length() == i + offset + 1) {
                        p.ptrs[pos] = new TrieNonLeaf(lf.suffix.charAt(offset));
                        p = (TrieNonLeaf) p.ptrs[pos];
                        p.endOfWord = true;
                        createLeaf(lf.suffix.charAt(offset),
                                lf.suffix.substring(offset + 1), p);
                        return;
                    } // word = "ABCDEF", leaf = "ABC" => leaf = "DEF";
                    else if (lf.suffix.length() == offset) {
                        p.ptrs[pos] = new TrieNonLeaf(word.charAt(i + offset + 1));
                        p = (TrieNonLeaf) p.ptrs[pos];
                        p.endOfWord = true;
                        createLeaf(word.charAt(i + offset + 1),
                                word.substring(i + offset + 2), p);
                        return;
                    }
                    p.ptrs[pos] = new TrieNonLeaf(word.charAt(i + offset + 1));
                    p = (TrieNonLeaf) p.ptrs[pos];
                    offset++;
                } while (word.charAt(i + offset) == lf.suffix.charAt(offset - 1));
                offset--;
                // word = "ABCDEF", leaf = "ABCPQR" =>
                // leaf('D') = "EF", leaf('P') = "QR";
                // Revisa si queda algun sufijo
                // word = "ABCD", leaf = "ABCPQR" =>
                // leaf('D') = null, leaf('P') = "QR";
                String s = null;
                if (word.length() > i + offset + 2) {
                    s = word.substring(i + offset + 2);
                }
                createLeaf(word.charAt(i + offset + 1), s, p);
                // Revisa si queda algun sufijo
                // word = "ABCDEF", leaf = "ABCP" =>
                // leaf('D') = "EF", leaf('P') = null;
                if (lf.suffix.length() > offset + 1) {
                    s = lf.suffix.substring(offset + 1);
                } else {
                    s = null;
                }
                createLeaf(lf.suffix.charAt(offset), s, p);
                return;
            } else {
                p = (TrieNonLeaf) p.ptrs[pos];
                i++;
            }
        }
    }
    
    
    /**
     * Método para obtener una visualización lateral del Trie.
     * Muestra las palabras que contiene el Trie y su estructura.
     * 
     * @param depth  La profundidad actual dentro del Trie.
     * @param node   El nodo actual que se está examinando.
     * @param prefix El prefijo acumulado hasta el nodo actual.
     */
    private void sideView(int depth, TrieNode node, String prefix) {
        if (node.isLeaf) {
            // Imprime el nodo hoja
            for (int j = 0; j < depth; j++) {
                System.out.print(" ");
            }
            System.out.println(prefix + ((TrieLeaf) node).suffix);
        } else {
            TrieNonLeaf nonLeafNode = (TrieNonLeaf) node;
            for (int i = 0; i < nonLeafNode.letters.length(); i++) {
                if (nonLeafNode.ptrs[i] != null) {
                    // Recursive call for each child
                    String newPrefix = prefix + nonLeafNode.letters.charAt(i);
                    sideView(depth + 1, nonLeafNode.ptrs[i], newPrefix);
                }
            }

            if (nonLeafNode.endOfWord) {
                // Imprime el prefijo actual si es el final de una palabra
                for (int j = 0; j < depth; j++) {
                    System.out.print(" ");
                }
                System.out.println(prefix + "*"); // '*' indica el final de la plabra
            }
        }
    }
    

}


