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
 * Clase que representa un nodo no hoja en un Trie, extendiendo la clase TrieNode.
 */
public class TrieNonLeaf extends TrieNode{
    
    /** 
     * Booleano que indica si este nodo representa el final de una palabra.
     */
    public boolean endOfWord = false;
    
    /**
     * Cadena que contiene las letras representadas por este nodo.
     */
    public String letters;
    
    /**
     * Arreglo de punteros a nodos hijos.
     */
    public TrieNode[] ptrs = new TrieNode[1];
    
    public TrieNonLeaf() {
        isLeaf = false;
    }
    
    /**
     * Constructor para un nodo no hoja con un carácter dado.
     * 
     * @param ch Carácter para inicializar el nodo.
     */
    public TrieNonLeaf(char ch) {
        letters = new String();
        letters += ch;
        isLeaf = false;
    }
}
