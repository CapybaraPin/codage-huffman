/**
 * ConroleurCompression.java		28/05/2024
 * IUT DE RODEZ						Pas de copyrights
 */
package iut.info1.huffman.gui;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import iut.info1.huffman.ApplicationHuffman;

/**
 * Gère les actions de l'utilisateur sur
 * la page de décompression
 */
public class ControleurCompression {
	
	private String cheminFichierTxtChoisi;

	private String cheminDossierCompressionChoisi;
	
	private double nombreCaracteres;
	
	private String[][] donneesFrequencesCaracteres;

	@FXML
	private Button ABHAffichage;

	@FXML
	private Button cheminChoisi;

	@FXML
	private Label cheminDossier;

	@FXML
	private Label cheminFichierChoisi;

	@FXML
	private Button compresser;

	@FXML
	private Label nomFichier;

	@FXML
	private Button retour;

	@FXML
	private Button tabFrequence;

	@FXML
	private Label tailleFichier;

	private Object[] elementsDeCompression;
	
	private String[][] occurencesFichierCompresse;
	
	@FXML
	private void initialize() {

		cheminFichierTxtChoisi = null;
		cheminDossierCompressionChoisi = null;
	}

	@FXML
	void choisirFichier(ActionEvent event) {
		FileChooser choixFichier = new FileChooser();

		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		choixFichier.getExtensionFilters().add(extFilter);

		File fichierChoisi = choixFichier.showOpenDialog(null);

		cheminFichierTxtChoisi = null;
		if (fichierChoisi != null) {
			cheminFichierTxtChoisi = fichierChoisi.getAbsolutePath();
			cheminFichierChoisi.setText(cheminFichierTxtChoisi);
			nomFichier.setText(fichierChoisi.getName());
			tailleFichier.setText(String.valueOf(fichierChoisi.length()) + " octet(s)");

		} else {
			cheminFichierChoisi.setText("...");
			nomFichier.setText("...");
			tailleFichier.setText("...");
		}

		actualisationUtilisabiliteOutilsCompression();
		actualisationValiditeCompression();
	}

	@FXML
	private void saisirCheminCompression() {

		DirectoryChooser parcoursDossier = new DirectoryChooser();

		File dossierChoisi = parcoursDossier.showDialog(null);

		cheminDossierCompressionChoisi = null;
		if (dossierChoisi != null) {
			cheminDossierCompressionChoisi = dossierChoisi.getPath();
			cheminDossier.setText(dossierChoisi.getPath());
		} else {
			cheminDossier.setText("...");
		}

		actualisationUtilisabiliteOutilsCompression();
		actualisationValiditeCompression();
	}

	/**
	 * Permet de réactiver ou de désactiver le bouton de décompression
	 * si les paramètres mis en place sont vérifiées
	 */
	private void actualisationValiditeCompression() {

		compresser.setDisable(true);

		if (   cheminFichierTxtChoisi         != null
				&& cheminDossierCompressionChoisi != null) {

			compresser.setDisable(false);
		}
	}
	
	/**
	 * Permet de réactiver ou de désactiver le bouton de décompression
	 * si les paramètres mis en place sont vérifiées
	 */
	private void actualisationUtilisabiliteOutilsCompression() {

		ABHAffichage.setDisable(true);
		tabFrequence.setDisable(true);

		if (   cheminFichierTxtChoisi         != null
				&& cheminDossierCompressionChoisi != null
				&& !compresser.isDisable()) {

			ABHAffichage.setDisable(false);
			tabFrequence.setDisable(false);
		}
	}

	@FXML
	void compresser(ActionEvent event) {
		Alert boiteAlerte = new Alert(Alert.AlertType.CONFIRMATION,
				"Êtes-vous sûr de vouloir compresser ce fichier ?",
				ButtonType.YES, ButtonType.NO);

		Optional<ButtonType> option = boiteAlerte.showAndWait();
		if (option.get() == ButtonType.YES) {
				ApplicationHuffman.compression(cheminFichierTxtChoisi, cheminDossierCompressionChoisi);
			
			actualisationValiditeCompression();
			actualisationUtilisabiliteOutilsCompression();
		} // else
	}
	
	@FXML
    private void afficherTableauFrequences() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Table Popup");
        TableView<String[]> tableauFrequences = new TableView<>();
        
        TableColumn<String[], String> colonneCaracteres = new TableColumn<>("Caracteres");
        colonneCaracteres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        
        TableColumn<String[], String> colonneOccurences = new TableColumn<>("Occurences");
        colonneOccurences.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        
        TableColumn<String[], String> colonneFrequences = new TableColumn<>("Frequences");
        colonneFrequences.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        
        tableauFrequences.getColumns().add(colonneCaracteres);
        tableauFrequences.getColumns().add(colonneOccurences);
        tableauFrequences.getColumns().add(colonneFrequences);
        tableauFrequences.getColumns().get(0).setPrefWidth(100);
        tableauFrequences.getColumns().get(1).setPrefWidth(100);
        tableauFrequences.getColumns().get(2).setPrefWidth(100);
        
        // Ajouter des données
        ObservableList<String[]> donneesTableauFrequences = FXCollections.observableArrayList();
        occurencesFichierCompresse = (String[][]) elementsDeCompression[0];

        nombreCaracteres = 0;
		for (String[] occurenceCaractere : occurencesFichierCompresse) {
			nombreCaracteres += Integer.parseInt(occurenceCaractere[1]);
		}

        donneesFrequencesCaracteres = new String[occurencesFichierCompresse.length][3];
        // TODO initialiser les variables (si besoin)
		for (int indiceParcours = 0;
			 indiceParcours < donneesFrequencesCaracteres.length;
			 indiceParcours++) {
			
			donneesFrequencesCaracteres[indiceParcours][0] 
					= occurencesFichierCompresse[indiceParcours][0];
		    
			donneesFrequencesCaracteres[indiceParcours][1] 
		    		= occurencesFichierCompresse[indiceParcours][1];
		    
			donneesFrequencesCaracteres[indiceParcours][2] 
		    	= String.valueOf(
		    		Math.round(
		    			Double.valueOf(occurencesFichierCompresse[indiceParcours][1])
		    			/nombreCaracteres * 1e8)/1e8);
						
		}

		for (String[] FrequencesCaractere : donneesFrequencesCaracteres) {
			
			donneesTableauFrequences.add(FrequencesCaractere);
		}
        
        tableauFrequences.setItems(donneesTableauFrequences);
        VBox vbox = new VBox(tableauFrequences);
        Scene scene = new Scene(vbox);
        System.out.println("OK");
        popupStage.setScene(scene);
        popupStage.setWidth(320);
        popupStage.showAndWait();

    }
	
	

    @FXML
    void annuler(ActionEvent event) {
    	Main.activerMenu();
    }

}