package iut.info1.huffman.arbre;

import java.util.Arrays;

import arbre.ArbreBinaireHuffman;

public class EssaiArbreBinaireHuffman {
    public static void main(String[] args) {
        ArbreBinaireHuffman arbreA;
        ArbreBinaireHuffman secondArbreA;
        
        ArbreBinaireHuffman noeudA = new ArbreBinaireHuffman("a", null, null);
        ArbreBinaireHuffman noeudB = new ArbreBinaireHuffman("b", null, null);
        ArbreBinaireHuffman noeudC = new ArbreBinaireHuffman("c", null, null);
        ArbreBinaireHuffman noeudD = new ArbreBinaireHuffman("d", null, null);
        ArbreBinaireHuffman noeudE = new ArbreBinaireHuffman("e", null, null);
        ArbreBinaireHuffman noeudLienA =  ArbreBinaireHuffman.lienEntreDeuxObjets(noeudD, noeudE, 0, 0);
        ArbreBinaireHuffman noeudLienB =  ArbreBinaireHuffman.lienEntreDeuxObjets(noeudLienA, noeudA, 0, 0);



         arbreA = ArbreBinaireHuffman.insertionHuffman(new Object[]{'a', 'b', 'c', 'd',
                                                              'e', 'f', 'g'}
                                                , new double[]{0.34, 0.33, 0.1,
                                                               0.1, 0.05, 0.05,
                                                             0.02});

        System.out.println(arbreA.valeurDuNoeudCherche(new int[]{1}));
        System.out.println(arbreA.valeurDuNoeudCherche(new int[]{0, 0}));
        System.out.println(arbreA.valeurDuNoeudCherche(new int[]{0, 1, 0, 1}));
        System.out.println(arbreA.valeurDuNoeudCherche(new int[]{0, 1, 0, 0}));
        System.out.println(arbreA.valeurDuNoeudCherche(new int[]{0, 1, 1, 1}));
        System.out.println(arbreA.valeurDuNoeudCherche(new int[]{0, 1, 1, 0, 0}));
        System.out.println(arbreA.valeurDuNoeudCherche(new int[]{0, 1, 1, 0, 1}));
    
        secondArbreA = ArbreBinaireHuffman.insertionHuffman(new Object[]{'a', 'b', 'c', 'd'}
                                                      , new double[]{0.3, 0.25, 0.1, 0.35});    

        System.out.println(secondArbreA.valeurDuNoeudCherche(new int[]{0, 0}));
        System.out.println(secondArbreA.valeurDuNoeudCherche(new int[]{0, 1}));
        System.out.println(secondArbreA.valeurDuNoeudCherche(new int[]{1, 0}));
        System.out.println(secondArbreA.valeurDuNoeudCherche(new int[]{1, 1}));

        System.out.println(Arrays.deepToString(arbreA.parcoursProfondeur()));
        // System.out.println("---------------------------------------------------");
        // System.out.println(Arrays.deepToString(secondArbreA.parcoursProfondeur()));
    }
}
