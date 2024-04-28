/* ArbreBinaireHuffman.java        XX/XX/2024  
 * IUT De Rodez             Pas de copyrights
 */

package iut.info1.huffman.arbre;

import java.util.Arrays;

/**
 * AB de test
 */
class ArbreBinaireHuffman {

    private String valeurDuNoeud;
    private ArbreBinaireHuffman noeudDroit;
    private ArbreBinaireHuffman noeudGauche;

    /**
     * Constructeur
     * @param valeurDuNoeud le caractère représenté par le noeud
     * @param noeudDroit le noeud fils droite du noeud actuel
     * @param noeudGauche le noeud fils gauche du noeud actuel
     */
    public ArbreBinaireHuffman(String valeurDuNoeud
                        , ArbreBinaireHuffman noeudGauche
                        , ArbreBinaireHuffman noeudDroit) {

        this.valeurDuNoeud = valeurDuNoeud;
        this.noeudGauche = noeudGauche;
        this.noeudDroit = noeudDroit;
    }

    /**
     * Permet de lier 2 éléments en fonction de leur
     * fréquence dans un arbre binaire lié à l'architecture
     * huffman
     * @param premierObject le premier élément que l'on veut lié
     * @param secondObject le second élément que l'on veut lié
     * @param frequencePremierObjet la fréquence du premier élément
     * @param frequenceSecondObjet la fréquence du second élément
     * @return l'arbre binaire ayant pour fils les 2 éléments 
     *         (dans l'ordre des fréquences)
     */
    public static ArbreBinaireHuffman lienEntreDeuxObjets(Object premierObject
                                                 , Object secondObject
                                                 , double frequencePremierObjet
                                                 , double frequenceSecondObjet) {

        ArbreBinaireHuffman premierArbre;
        ArbreBinaireHuffman secondArbre;

        if (premierObject instanceof ArbreBinaireHuffman) {
            premierArbre = (ArbreBinaireHuffman)premierObject;
        } else {
            premierArbre = new ArbreBinaireHuffman(premierObject.toString(), null, null);
        }

        if (secondObject instanceof ArbreBinaireHuffman) {
            secondArbre = (ArbreBinaireHuffman)secondObject;
        } else {
            secondArbre = new ArbreBinaireHuffman(secondObject.toString(), null, null);
        }

        return new ArbreBinaireHuffman("lien", (frequencePremierObjet > frequenceSecondObjet
                                         ? premierArbre
                                         : secondArbre), 
                                         (frequencePremierObjet > frequenceSecondObjet
                                         ? secondArbre
                                         : premierArbre)); 
    }

    /**
     * 
     * Permet de creer un arbre binaire correspondant à
     * une structure huffmanienne.
     * 
     * @param caracteres
     * @param frequences
     */
    public static ArbreBinaireHuffman insertionHuffman(Object[] caracteres, double[] frequences) {

        double plusPetiteFrequence;
        double secondePlusPetiteFrequence;
        int indexPlusPetiteFrequence;
        int indexSecondePlusPetiteFrequence;
        int indexDeReequilibrage;

        Object[] tblDeCaracteres = Arrays.copyOf(caracteres, caracteres.length);
        double[] frequenceDesCaracteres = Arrays.copyOf(frequences, frequences.length);

        indexDeReequilibrage = 0;
        // Initialisation avec des valeures minimales absurdes
        plusPetiteFrequence = secondePlusPetiteFrequence = 2.0f;
        indexPlusPetiteFrequence = indexSecondePlusPetiteFrequence = -1;
        while (frequenceDesCaracteres.length > 1) {

            // System.out.print(Arrays.toString(tblDeCaracteres));
            // System.out.println(Arrays.toString(frequenceDesCaracteres));
            
            for (int indexDeParcours = 0;
                indexDeParcours < frequenceDesCaracteres.length;
                indexDeParcours++) {

                if (frequenceDesCaracteres[indexDeParcours] < plusPetiteFrequence) {
                    plusPetiteFrequence = frequenceDesCaracteres[indexDeParcours];

                    indexPlusPetiteFrequence = indexDeParcours;
                }
            }

                
            for (int indexDeParcours = 0;
                indexDeParcours < frequenceDesCaracteres.length;
                indexDeParcours++) {


                if (frequenceDesCaracteres[indexDeParcours] <= secondePlusPetiteFrequence 
                    && frequenceDesCaracteres[indexDeParcours] >= plusPetiteFrequence
                    && indexDeParcours != indexPlusPetiteFrequence) {
                        
                    secondePlusPetiteFrequence = frequenceDesCaracteres[indexDeParcours];
                    indexSecondePlusPetiteFrequence = indexDeParcours;
                }
            }

            // System.out.println("------------------------");
            // System.out.println(plusPetiteFrequence);
            // System.out.println(secondePlusPetiteFrequence);
            // System.out.println("------------------------");

            //Nous avons les 2 plus petites fréquences

            Object[] nouvelleTblDeCaracteres 
                        = new Object[tblDeCaracteres.length - 1];
            double[] nouvellesFrequencesDesCaracteres 
                        = new double[frequenceDesCaracteres.length - 1];

            for (int indexDeParcours = 0; 
                indexDeParcours < frequenceDesCaracteres.length;
                indexDeParcours++) {

                    // Cette condition est respectée exactement 
                    // une fois par itération  

                    if (indexDeParcours == Math.min(indexPlusPetiteFrequence, indexSecondePlusPetiteFrequence)) {

                        nouvelleTblDeCaracteres[indexDeParcours] 
                        =  lienEntreDeuxObjets(
                            tblDeCaracteres[indexPlusPetiteFrequence]
                            , tblDeCaracteres[indexSecondePlusPetiteFrequence]
                            , frequenceDesCaracteres[indexPlusPetiteFrequence]
                            , frequenceDesCaracteres[indexSecondePlusPetiteFrequence]);

                        nouvellesFrequencesDesCaracteres[indexDeParcours] 
                            = plusPetiteFrequence + secondePlusPetiteFrequence;

                        //System.out.printf("A frequence : %f indice : %d les frequences : %d %d\n", frequenceDesCaracteres[indexDeParcours], indexDeParcours, indexPlusPetiteFrequence, indexSecondePlusPetiteFrequence);

                                
                    } else if (indexDeParcours == Math.max(indexPlusPetiteFrequence, indexSecondePlusPetiteFrequence)) {
                        indexDeReequilibrage = 1;
                    }else {

                        

                        // System.out.printf("B frequence : %f indice : %d\n", frequenceDesCaracteres[indexDeParcours], indexDeParcours);

                        // L'index de rééquilibrage évite les problèmes d'indexage
                        nouvelleTblDeCaracteres[indexDeParcours - indexDeReequilibrage] 
                            = tblDeCaracteres[indexDeParcours];
                        nouvellesFrequencesDesCaracteres[indexDeParcours - indexDeReequilibrage] 
                            = frequenceDesCaracteres[indexDeParcours];

                    }

            }

            tblDeCaracteres = nouvelleTblDeCaracteres;
            frequenceDesCaracteres = nouvellesFrequencesDesCaracteres;

            // Reinitailisation des attributs pour la prochaine itération
            indexPlusPetiteFrequence = -1;
            indexSecondePlusPetiteFrequence = -1; 
            plusPetiteFrequence = 1;
            secondePlusPetiteFrequence = 1;
            indexDeReequilibrage = 0;

        }        

        return (ArbreBinaireHuffman)(tblDeCaracteres[0]);

    }

    /**
     * Permet le parcours préfixe d'un Arbre binaire huffman
     * @param arbreBinaire l'arbre sur qui le parcours est opéré
     * @return tableau ayant pour chaque sous-tableau le caractère 
     *         et son code huffman
     */
    public String[][] parcoursProfondeur() {

        boolean profondeurEstValide;

        int indiceDonneesArbre;
        int nombreDeLiens;

        String valeurChemin;
        String[] liensExistants;
        String[] cheminsPossibles;
        String[] nouveauxCheminsPossibles;

        String[] cheminPartitionne;
        int[] cheminPossible;

        String[][] donneesArbre;
                
        profondeurEstValide = true;
        indiceDonneesArbre = 0;
        nombreDeLiens = 0;
        donneesArbre = new String[50][2];
        cheminsPossibles = new String[]{"0", "1"};
        liensExistants = new String[4];
        nouveauxCheminsPossibles = new String[4];
        valeurChemin = "";    
        while (profondeurEstValide) {


            for (int indiceParcours = 0;
                 indiceParcours < cheminsPossibles.length;
                 indiceParcours++) {

                if (cheminsPossibles[indiceParcours] != null 
                    && !cheminsPossibles[indiceParcours].contains("null")) {

                    System.out.println(Arrays.toString(cheminsPossibles));

                    cheminPartitionne = cheminsPossibles[indiceParcours]
                                        .split(", ");

                    cheminPossible = new int[cheminPartitionne.length];
    
                    for (int indiceRemplacement = 0; 
                         indiceRemplacement < cheminPartitionne.length;
                         indiceRemplacement++) {
    
                        cheminPossible[indiceRemplacement] = 
                            Integer.parseInt(cheminPartitionne[indiceRemplacement]);
    
                    }  
    
                    valeurChemin = this.valeurDuNoeudCherche(cheminPossible);

                    if (valeurChemin != null && valeurChemin.equals("lien")) {

                        liensExistants[nombreDeLiens] = 
                                cheminsPossibles[indiceParcours];

                        nombreDeLiens++;
    
                    } else if (valeurChemin != null) {
                        donneesArbre[indiceDonneesArbre][0] = valeurChemin;
                        donneesArbre[indiceDonneesArbre][1] = 
                                cheminsPossibles[indiceParcours];

                        indiceDonneesArbre++;
                    }
                }

            }


            if (liensExistants.length == 0) {
                profondeurEstValide = false;
            }

            if (profondeurEstValide) {
                
                for (int indiceParcours = 0;
                indiceParcours < nouveauxCheminsPossibles.length;
                indiceParcours+=2) {


               nouveauxCheminsPossibles[indiceParcours] = 
                           liensExistants[indiceParcours / 2] + ", 0"; 

               nouveauxCheminsPossibles[indiceParcours + 1] = 
                           liensExistants[indiceParcours / 2] + ", 1";
               
                }
            }


            liensExistants = new String[nombreDeLiens * 2];
            cheminsPossibles = nouveauxCheminsPossibles;
            nombreDeLiens = 0;
        }         

        return donneesArbre; 

    }
    
    public ArbreBinaireHuffman getNoeudDroit() {
        if (noeudDroit != null) {
            return noeudDroit;
        } else {
            return null;
        }
    }

    public ArbreBinaireHuffman getNoeudGauche() {
        if (noeudGauche != null) {
            return noeudGauche;
        } else {
            return null;
        }
    }

    /**
     * Permet d'accéder à la feuille correspondante à la séquence
     * de 1 et de 0 donnés. Respectivement le noeud droit et le
     * noeud gauche d'une séquence donnée
     * @param tblDeRecherche un tableau de 0 et de 1 pour retrouver
     * un caractère
     * @return le caractère cherché stockée dans l'arborescence
     */
    public String valeurDuNoeudCherche(int[] tblDeRecherche) {

        int[] nouveauTblDeRecherche;

        if (tblDeRecherche.length == 0) {
            return valeurDuNoeud;   
        }

        nouveauTblDeRecherche = new int[tblDeRecherche.length - 1];

        for (int indexDeRecherche = 0;
             indexDeRecherche < nouveauTblDeRecherche.length;
            indexDeRecherche++) {
            
            nouveauTblDeRecherche[indexDeRecherche] 
                 = tblDeRecherche[indexDeRecherche + 1];
        }


        // Si le chemin suivant est 0, on va dans le noeud gauche
        if (tblDeRecherche[0] == 1) {

            if (getNoeudDroit() == null) {
                return null;
            }

            return noeudDroit.valeurDuNoeudCherche(nouveauTblDeRecherche);
        }

        if (getNoeudGauche() == null) {
            return null;
        }

        //Sinon (le chemin suivant est 1), on va dans le noeud droit
        return noeudGauche.valeurDuNoeudCherche(nouveauTblDeRecherche);
        
    }

    @Override
    public String toString() {
        return this.valeurDuNoeud;
    }

}