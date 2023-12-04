/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myprojects.spellingchecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import model.Trie;

/**
 *
 * @author mitch
 */

/**
 * Representa una utilidad de Revisión Ortográfica.
 */
public class SpellCheck {

    /**
     * Número de línea actual.
     */
    static int lineNum = 1;
    
    /**
     * Almacena la palabra actual.
     */
    static String s;
    
    /**
     * Almacena el carácter actual.
     */
    static int ch;
    
    
    
    /**
     * Lee una palabra desde el flujo de entrada.
     * 
     * @param fIn El flujo de entrada del que leer.
     */
    static void readWord(InputStream fIn) {

        try {
            while (true)
                if (ch > -1 && !Character.isLetter((char) ch)) { // skip
                    ch = fIn.read(); // nonletters;
                    if (ch == '\n') {
                        lineNum++;
                    }
                } else 
                    break;

            if (ch == -1) {
                return;
            }
            s = "";
            while (ch > -1 && Character.isLetter((char) ch)) {
                s += Character.toUpperCase((char) ch);
                ch = fIn.read();
            }
        } catch (IOException io) {
            System.out.println("Problem with input.");
        }
    }
    
    /**
    * Realiza la verificación ortográfica en un archivo.
    * 
    * @return Una lista de palabras mal escritas y sus líneas correspondientes.
    */
    public ArrayList<String> checkSpelling() {
        
        ArrayList<String> misspelledWords = new ArrayList<>();
        String fileName = "";
        InputStream fIn = null, dictionary = null;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(isr);
        Trie trie = null;
        ch = ' ';
        lineNum = 1;
        try {
            dictionary = new FileInputStream("C:\\Users\\mitch\\OneDrive\\Documentos\\NetBeansProjects\\232CC232Project\\src\\main\\java\\uni\\aed\\spellChecker\\dictionary");
            readWord(dictionary);
            trie = new Trie(s.toUpperCase()); // initialize root;

            while (ch > -1) {
                readWord(dictionary);
                if (ch == -1){
                    break;
                }
                    
                trie.insert(s);
            }
            
            dictionary.close();

        } catch (IOException io) {
            System.err.println("Cannot open dictionary");
        } finally {
            try {
                dictionary.close();
            } catch (IOException ex) {
                System.out.println("Error closing file dictionary");
            }
        }
        

        System.out.println("\nTrie: ");
        trie.printTrie();
        ch = ' ';
        lineNum = 1;
        
        
        try {   
            fIn = new FileInputStream("C:\\Users\\mitch\\OneDrive\\Documentos\\NetBeansProjects\\232CC232Project\\src\\main\\java\\uni\\aed\\spellChecker\\test.txt");

            System.out.println("Misspelled words:");
            while (true) {
                readWord(fIn);
                if (ch == -1) {
                    break;
                }
                if (!trie.found(s)) {
                    System.out.println(s + " on line " + lineNum);
                    String str = "Error: " + s + " on line " + lineNum ;
                    misspelledWords.add(str);
                }
            }
            fIn.close();
        } catch (IOException io) {
            System.err.println("Cannot open " + fileName);
        } finally {
            try {
                fIn.close();
            } catch (IOException ex) {
                System.out.println("Eror closing file test.txt");
            }
        }
        
        return misspelledWords;
    }
       
    /**
    * Agrega una palabra al diccionario.
    * 
    * @param newWord La palabra a agregar al diccionario.
    */
    public void addToDictionary(String newWord) {
        
        //BufferedWriter writer = null;
        String fileName = "C:\\Users\\mitch\\OneDrive\\Documentos\\NetBeansProjects\\232CC232Project\\src\\main\\java\\uni\\aed\\spellChecker\\dictionary";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(newWord);
                writer.newLine(); // Agrega un salto de línea si lo deseas
                System.out.println("Word add to dictionary successfully");
                
            } catch (IOException e) {
                System.err.println("Error adding new word to dictionary: " + e.getMessage());
            } 
    }
    
    /**
    * Verifica la integridad del diccionario.
    */
    public ArrayList<String> checkDictionary() {
        
       InputStream dictionary;
       Trie trie = null;
       ch = ' ';
       lineNum = 1;
       ArrayList<String> dictionaryList = new ArrayList<>();
       
        try {
            dictionary = new FileInputStream("C:\\Users\\mitch\\OneDrive\\Documentos\\NetBeansProjects\\232CC232Project\\src\\main\\java\\uni\\aed\\spellChecker\\dictionary");
            readWord(dictionary);
            trie = new Trie(s.toUpperCase()); // initialize root;

            while (ch > -1) {
                readWord(dictionary);
                if (ch == -1)
                    break;
                trie.insert(s);
                dictionaryList.add(s);
            }

            dictionary.close();
        } catch (IOException io) {
            System.err.println("Cannot open dictionary");
        }
            
        return dictionaryList;
    }
}
    
