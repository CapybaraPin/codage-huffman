/**
 * ConroleurDecompression.java		28/05/2024
 * IUT DE RODEZ						Pas de copyrights
 */
package iut.info1.huffman.gui;

import java.io.File;

import iut.info1.huffman.Decompression;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * Gère les actions de l'utilisateur sur
 * la page de décompression
 */
public class ControleurDecompression {

    private String cheminFichierBinChoisi;

    private String cheminFichierCleChoisi;

    private String cheminDossierDecompressionChoisi;

    @FXML
    private Label cheminFichierBin;

    @FXML 
    private Label nomFichierBin;

    @FXML 
    private Label tailleFichierBin;

    @FXML 
    private Label cheminFichierCle;

    @FXML 
    private Label cheminDossierResultat;

    @FXML
    private Button boutonDecompression;

    @FXML
    private Button retour;

    @FXML
    private void initialize() {
	cheminFichierBinChoisi = null;
	cheminFichierCleChoisi = null;
	cheminDossierDecompressionChoisi = null;
    }

    @FXML
    private void saisirCheminBin() {

	FileChooser choixFichier = new FileChooser();

	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("BIN files (*.bin)", "*.bin");
	choixFichier.getExtensionFilters().add(extFilter);

	File fichierChoisi = choixFichier.showOpenDialog(null);

	cheminFichierBinChoisi = null;
	if (fichierChoisi != null) {
	    cheminFichierBinChoisi = fichierChoisi.getAbsolutePath();
	    cheminFichierBin.setText(cheminFichierBinChoisi);
	    nomFichierBin.setText(fichierChoisi.getName());
	    tailleFichierBin.setText(String.valueOf(fichierChoisi.length()) + " octet(s)");

	} else {
	    cheminFichierBin.setText("...");
	    nomFichierBin.setText("...");
	    tailleFichierBin.setText("...");
	}
	actualisationValiditeDecompression();
    }

    @FXML
    private void saisirCheminFichierCle() {

	FileChooser choixFichier = new FileChooser();

	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("fichiers encode (_EncodeH.txt)", "*_EncodeH.txt");
	choixFichier.getExtensionFilters().add(extFilter);

	File fichierChoisi = choixFichier.showOpenDialog(null); 
	
	cheminFichierCleChoisi = null;
	if (fichierChoisi != null) {
	    cheminFichierCleChoisi = fichierChoisi.getAbsolutePath();
	    cheminFichierCle.setText(cheminFichierCleChoisi);
	} else {
	    cheminFichierCle.setText("...");
	}
	actualisationValiditeDecompression();
    }

    @FXML
    private void saisirCheminDecompression() {

	DirectoryChooser parcoursDossier = new DirectoryChooser();

	File dossierChoisi = parcoursDossier.showDialog(null);

	cheminDossierDecompressionChoisi = null;
	if (dossierChoisi != null) {

	    cheminDossierDecompressionChoisi = dossierChoisi.getPath();

	    cheminDossierResultat.setText(dossierChoisi.getPath());
	} else {
	    cheminDossierResultat.setText("...");

	}
	actualisationValiditeDecompression();
    }

    @FXML
    private void lancerDecompression() {

	Decompression decompression;

	decompression = new Decompression(cheminFichierBinChoisi, cheminFichierCleChoisi, cheminDossierDecompressionChoisi);
	decompression.execute();
    }

    /**
     * Permet de réactiver ou de désactiver le bouton de décompression
     * si les paramètres mis en place sont vérifiées
     */
    private void actualisationValiditeDecompression() {

	boutonDecompression.setDisable(true);

	if (   cheminFichierBinChoisi != null 
		&& cheminDossierDecompressionChoisi != null
		&& cheminFichierCleChoisi != null) {

	    boutonDecompression.setDisable(false);
	}
    }

    @FXML
    void annuler(ActionEvent event) {
	Main.activerMenu();
    }
}