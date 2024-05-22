/* ArbreBinaireHuffman.java        XX/XX/2024  
 * IUT De Rodez             Pas de copyrights
 */

package iut.info1.huffman.arbre;

import java.util.Arrays;

/**
 * AB de test
 */
public class ArbreBinaireHuffman {

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
	 * Permet de récupérer l'arbre binaire huffman
	 * provenant d'une compression
	 * @param tableauCaractereCompresse un tableau contenant un 
	 * 		  caractère puis son code huffman associé
	 * @return l'arbre binaire regénéré
	 * TODO Faire la méthode (TDD)
	 * @throws IllegalArgumentException si le tableau n'est pas valide
	 */
	public static ArbreBinaireHuffman recuperationArbreHuffman(String[][] tableauCaractereCompresse) throws IllegalArgumentException {

		ArbreBinaireHuffman nouvelArbre;
		String[] tableauCodesHuffman = new String[tableauCaractereCompresse.length];
		Object[] tableauLiensHuffman = new Object[tableauCaractereCompresse.length];
		Object[] tableauCheminsSousJacentHuffman 
					= new String[tableauCaractereCompresse.length*2];
		
		Object[] tableauCheminsHuffman 
				= new Object[tableauCheminsSousJacentHuffman.length 
				             + tableauLiensHuffman.length];

		
		int niveauProfondeur;
		int positionChemin;
		
		boolean presenceChemins;
		boolean estValide;
		
		for (int indiceParcours = 0;
				indiceParcours < tableauCodesHuffman.length;
				indiceParcours++) {
			
			tableauCodesHuffman[indiceParcours] 
					= tableauCaractereCompresse[indiceParcours][1];
		}
		
		Arrays.sort(tableauCodesHuffman);
			
		estValide = true;
		for (int indiceParcours = 0;
				indiceParcours < tableauCodesHuffman.length - 1;
				indiceParcours++) {

			if (tableauCodesHuffman[indiceParcours]
					.equals(tableauCodesHuffman[indiceParcours + 1])) {
				
				estValide = false;
			}
		}
		
		for (int indiceParcours = 0;
				indiceParcours < tableauLiensHuffman.length;
				indiceParcours++) {
						
			if (tableauCodesHuffman[indiceParcours].length() > 1) {
				tableauLiensHuffman[indiceParcours] 
						= tableauCodesHuffman[indiceParcours].substring(
								0, tableauCodesHuffman[indiceParcours].length() - 1);
			}
		}
		
		tableauLiensHuffman = Arrays.stream(tableauLiensHuffman).distinct().toArray();

		positionChemin = 0;
		niveauProfondeur = 1;
		presenceChemins= true;
		while (presenceChemins) {
			
			presenceChemins = false;
			for (int indiceParcours = 0;
					indiceParcours < tableauLiensHuffman.length;
					indiceParcours++) {
				
				
				if (tableauLiensHuffman[indiceParcours] instanceof String 
					&& tableauLiensHuffman[indiceParcours].toString()
														  .length() >
														   niveauProfondeur) {

					tableauCheminsSousJacentHuffman[positionChemin] 
							= tableauLiensHuffman
									[indiceParcours].toString()
													.substring(0,
													 niveauProfondeur + 1);
					positionChemin += 1;
					presenceChemins = true;
				}
			}
		
			niveauProfondeur += 1;
		}

		tableauCheminsSousJacentHuffman = Arrays.stream(tableauCheminsSousJacentHuffman).distinct().toArray();

	    System.arraycopy(tableauCheminsSousJacentHuffman, 0, tableauCheminsHuffman
	    				 , 0, tableauCheminsSousJacentHuffman.length);
	    
	    System.arraycopy(tableauLiensHuffman, 0, tableauCheminsHuffman
	    				 , tableauCheminsSousJacentHuffman.length
	    				 , tableauLiensHuffman.length);
	    
		tableauCheminsHuffman = Arrays.stream(tableauCheminsHuffman).distinct().toArray();
		
		for (Object lienHuffman : tableauCheminsHuffman) {
			
			for (Object codeHuffman : tableauCodesHuffman) {
			
				if (lienHuffman != null && lienHuffman.toString().equals(codeHuffman.toString())) {
					estValide = false;
				}
				
			}
			
		}
		
		if (!estValide) {
			System.out.println("REUSSITE");
			throw new IllegalArgumentException("Le tableau d'encodage est invalide !");
		}
		
		nouvelArbre = new ArbreBinaireHuffman("lien", null, null);
		for (String[] caractereCompresse : tableauCaractereCompresse) {
			
			nouvelArbre.insertionCaractere(caractereCompresse[0], caractereCompresse[1]);
		}
		
		
		
		return nouvelArbre;
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
        double sommeFrequences;
        int indexPlusPetiteFrequence;
        int indexSecondePlusPetiteFrequence;
        int indexDeReequilibrage;
        
        Object[] tblDeCaracteres = Arrays.copyOf(caracteres, caracteres.length);
        double[] frequenceDesCaracteres = Arrays.copyOf(frequences, frequences.length);

        indexDeReequilibrage = 0;
        // Initialisation avec des valeures minimales absurdes
        plusPetiteFrequence = secondePlusPetiteFrequence = 2.0f;
        indexPlusPetiteFrequence = indexSecondePlusPetiteFrequence = -1;

        
        sommeFrequences = 0;
        for (double frequence : frequenceDesCaracteres) {
			sommeFrequences += frequence;
		}
        
        if (   0.99 > sommeFrequences 
        	||        sommeFrequences > 1.01) {
        	
        	throw new IllegalArgumentException("Erreur de frequencage");
        	
        }
        
        
        while (frequenceDesCaracteres.length > 1) {


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

            Object[] nouvelleTblDeCaracteres 
                        = new Object[tblDeCaracteres.length - 1];
            double[] nouvellesFrequencesDesCaracteres 
                        = new double[frequenceDesCaracteres.length - 1];

            for (int indexDeParcours = 0; 
                indexDeParcours < frequenceDesCaracteres.length;
                indexDeParcours++) {

                    if (indexDeParcours == Math.min(indexPlusPetiteFrequence, indexSecondePlusPetiteFrequence)) {

                        nouvelleTblDeCaracteres[indexDeParcours] 
                        =  lienEntreDeuxObjets(
                            tblDeCaracteres[indexPlusPetiteFrequence]
                            , tblDeCaracteres[indexSecondePlusPetiteFrequence]
                            , frequenceDesCaracteres[indexPlusPetiteFrequence]
                            , frequenceDesCaracteres[indexSecondePlusPetiteFrequence]);

                        nouvellesFrequencesDesCaracteres[indexDeParcours] 
                            = plusPetiteFrequence + secondePlusPetiteFrequence;
        
                    } else if (indexDeParcours == Math.max(indexPlusPetiteFrequence, indexSecondePlusPetiteFrequence)) {
                        indexDeReequilibrage = 1;
                    }else {
                        nouvelleTblDeCaracteres[indexDeParcours - indexDeReequilibrage] 
                            = tblDeCaracteres[indexDeParcours];
                        nouvellesFrequencesDesCaracteres[indexDeParcours - indexDeReequilibrage] 
                            = frequenceDesCaracteres[indexDeParcours];

                    }

            }

            tblDeCaracteres = nouvelleTblDeCaracteres;
            frequenceDesCaracteres = nouvellesFrequencesDesCaracteres;

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
        String[][] nouvellesDonneesArbres;
                
        profondeurEstValide = true;
        indiceDonneesArbre = 0;
        nombreDeLiens = 0;
        donneesArbre = new String[0][2];
        nouvellesDonneesArbres = new String[1][2];
        
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
                        
                        
                        nombreDeLiens += 1;
    
                    } else if (valeurChemin != null) {
                    	
                    	nouvellesDonneesArbres
                    				= new String[donneesArbre.length + 1][2];
                    	
                    	
                    	for (int indiceRemplacement = 0;
                    		 indiceRemplacement < donneesArbre.length;
                    		 indiceRemplacement++) {
                    		
                    		nouvellesDonneesArbres[indiceRemplacement] 
                    				= donneesArbre[indiceRemplacement];
                    		
                    	}
                    	
                    	
                    	nouvellesDonneesArbres[indiceDonneesArbre][0] 
                    			= valeurChemin;
                    	
                    	nouvellesDonneesArbres[indiceDonneesArbre][1] = 
                                cheminsPossibles[indiceParcours];

                    	donneesArbre = nouvellesDonneesArbres;
                    	
                        indiceDonneesArbre++;
                    }
                }

            }

            
            if (liensExistants.length == 0) {
                profondeurEstValide = false;
            }
            

            nouveauxCheminsPossibles = new String[nombreDeLiens * 2];


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
    
    /**
     * Insere dans un arbre binaire une valeur donnée grace
     * au chemin qui lui est propre
     * @param caractereInsere Caractère à insérer dans l'arboresence
     * @param cheminInsertion Chemin du caractère
     */
    
    public void insertionCaractere(String caractereInsere, String cheminInsertion) {
    	
    	
    	char cheminActuel;
    	
    	cheminActuel = cheminInsertion.charAt(0);
    	
    	if (cheminActuel == 'f') {
    		System.out.println("OUI");
    	}
    	
    	if (cheminInsertion.length() == 1) {
    		
    		if (cheminActuel == '0') {
    			this.noeudGauche = new ArbreBinaireHuffman(caractereInsere, null, null);
    		} else {
    			this.noeudDroit =  new ArbreBinaireHuffman(caractereInsere, null, null);
    		}
    	} else {
    		
    		if (cheminActuel == '0') {
    			
    			if (this.noeudGauche == null) {
    				this.noeudGauche = new ArbreBinaireHuffman("lien", null, null);
    			}
    			
    			this.noeudGauche.insertionCaractere(caractereInsere, cheminInsertion.substring(1, cheminInsertion.length()));
    			
    		} else {

        		if (this.noeudDroit == null) {
        			this.noeudDroit = new ArbreBinaireHuffman("lien", null, null);
        		}
        			
        		this.noeudDroit.insertionCaractere(caractereInsere, cheminInsertion.substring(1, cheminInsertion.length()));  			
    			
    		}
    		    		
    	}
    	
    }
    
    public ArbreBinaireHuffman getNoeudDroit() {
        if (noeudDroit != null) {
            return noeudDroit;
        } 
        return null;
    }

    public ArbreBinaireHuffman getNoeudGauche() {
        if (noeudGauche != null) {
            return noeudGauche;
        } 
        return null;

    }

    /**
     * Permet d'accéder à la feuille correspondante à la séquence
     * de 1 et de 0 donnés. Respectivement le noeud droit et le
     * noeud gauche d'une séquence donnée
     * @param tblDeRecherche un tableau de 0 et de 1 pour retrouver
     * un caractère
     * @return le caractère cherché stockés dans l'arborescence
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

    /**
     * Affiche l'arbre dans la console
     */
    public void afficherArbre() {
        afficherArbre("", true);
    }

    private void afficherArbre(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + valeurDuNoeud);
        if (noeudGauche != null) {
            noeudGauche.afficherArbre(prefix + (isTail ? "    " : "│   "), noeudDroit == null);
        }
        if (noeudDroit != null) {
            noeudDroit.afficherArbre(prefix + (isTail ? "    " : "│   "), true);
        }
    }
    
    @Override
    public String toString() {
        return this.valeurDuNoeud;
    }

}
