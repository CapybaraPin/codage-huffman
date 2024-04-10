/**
 * GestionFichier.java          09/04/2024
 * IUT DE RODEZ            Pas de copyrights
 */
package iut.info1.huffman.fichier;


/**
 * TODO commenter cette classe
 */
public class GestionFichier {

    /**
     * L'objectif est d'afficher le contenu d'un tableau de
     * chaîne de caractères, pour qui chaque éléments de
     * cette chaîne est la ligne d'un fichier.
     * \n
     * Dans le cas où il existe plusieurs saut à la ligne vide, ces
     * derniers seront conservés.
     * 
     * @param contenuFichier Tableau de chaîne de caractère
     */
    public static void affichageFichier(String[] contenuFichier){
        for (String ligne : contenuFichier) {
            System.out.println(ligne);
        }
    }
}