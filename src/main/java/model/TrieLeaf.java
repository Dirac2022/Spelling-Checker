/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author mitch
 */

/**
 * Representa un nodo hoja en una estructura de datos Trie.
 * Extiende la clase TrieNode.
 */
public class TrieLeaf extends TrieNode {
    
    /**
     * Sufijo almacenado en el nodo hoja.
     */
    public String suffix;
    
    /**
     * Constructor predeterminado que inicializa un nodo hoja.
     */
    public TrieLeaf() {
        isLeaf = true;
    }
    
    /**
     * Constructor que inicializa el nodo hoja con un sufijo dado.
     * 
     * @param suffix El sufijo a almacenar en el nodo hoja.
     */
    public TrieLeaf(String suffix) {
        this.suffix = new String(suffix);
        isLeaf = true;
    }
}
