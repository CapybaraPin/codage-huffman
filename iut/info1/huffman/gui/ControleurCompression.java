
/**
 * ConroleurCompression.java		28/05/2024
 * IUT DE RODEZ						Pas de copyrights
 */
package iut.info1.huffman.gui;

import java.io.File;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import iut.info1.huffman.Compression;
import iut.info1.huffman.arbre.ArbreBinaireHuffman;

/**
 * Gère les actions de l'utilisateur sur
 * la page de décompression
 */
public class ControleurCompression {

    private double mouseX;

    public Pane zoneAffichage;

    private double mouseY;

    private double initialVGap;

    private double vGap;

    private double scaleFactor;    

    private double niveauZoom;

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
	    cheminDossierCompressionChoisi = dossierChoisi.getPath() + "\\";
	    cheminDossier.setText(dossierChoisi.getPath() + "\\");
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
	Compression compression;

	Alert boiteAlerte = new Alert(Alert.AlertType.CONFIRMATION,
		"Êtes-vous sûr de vouloir compresser ce fichier ?",
		ButtonType.YES, ButtonType.NO);

	Optional<ButtonType> option = boiteAlerte.showAndWait();
	if (option.get() == ButtonType.YES) {

	    compression = new Compression(cheminFichierTxtChoisi, cheminDossierCompressionChoisi);
	    elementsDeCompression = (Object[]) compression.execute();

	    actualisationValiditeCompression();
	    actualisationUtilisabiliteOutilsCompression();
	} // else
    }

    @FXML
    private void afficherTableauFrequences() {
	Stage popupStage = new Stage();
	popupStage.setResizable(false);
	popupStage.initModality(Modality.APPLICATION_MODAL);
	popupStage.setTitle(String.format("Table des fréquences pour %s :", "NOM FICHIER")); //TODO
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
	popupStage.setScene(scene);
	popupStage.setWidth(320);
	popupStage.showAndWait();

    }

    @FXML
    private void afficherArbreBinaire() {

	initialVGap = 50;
	vGap = 100;
	scaleFactor = 1.0;
	niveauZoom = 1.0;

	zoneAffichage = new Pane();

	Stage popupStage = new Stage();
	popupStage.setResizable(true);
	popupStage.initModality(Modality.APPLICATION_MODAL);
	popupStage.setTitle(String.format("Arbre binaire du fichier  %s :", "NOM FICHIER")); //TODO

	ArbreBinaireHuffman.drawTree(zoneAffichage, (ArbreBinaireHuffman)elementsDeCompression[1], 540, 100, 800, initialVGap);

	ScrollPane scrollPane = new ScrollPane(zoneAffichage);
	scrollPane.setPannable(true); // Permettre le déplacement avec la souris

	// Ajout de l'échelle pour le zoom
	Scale scale = new Scale(1, 1, 0, 0);
	zoneAffichage.getTransforms().add(scale);

	// Ajout d'un événement de scroll pour le zoom
	zoneAffichage.setOnScroll(event -> {
	    scaleFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
	    scale.setX(scale.getX() / scaleFactor);
	    scale.setY(scale.getY() / scaleFactor);



	    zoomTree(scaleFactor * (scaleFactor > 1 ? 4 : 0.25));

	});

	zoneAffichage.setOnMousePressed(event -> {
	    mouseX = event.getSceneX();
	    mouseY = event.getSceneY();
	});

	// Ajout d'événements de souris pour le déplacement
	Translate mouvementSouris = new Translate();
	zoneAffichage.getTransforms().add(mouvementSouris);

	zoneAffichage.setOnMouseDragged(event -> {
	    double deltaX = event.getSceneX() - mouseX;
	    double deltaY = event.getSceneY() - mouseY;
	    mouvementSouris.setX(mouvementSouris.getX() + deltaX);
	    mouvementSouris.setY(mouvementSouris.getY() + deltaY);
	    mouseX = event.getSceneX();
	    mouseY = event.getSceneY();
	});


	Scene scene = new Scene(zoneAffichage, 1080, 720);
	popupStage.setTitle("Arbre Binaire de Huffman Visualization");
	popupStage.setScene(scene);
	popupStage.show();
    }

    public void zoomTree(double newScaleFactor) {
	niveauZoom *= newScaleFactor;
	zoneAffichage.getChildren().clear();
	ArbreBinaireHuffman.drawTree(zoneAffichage, (ArbreBinaireHuffman)elementsDeCompression[1], 540, 100, initialVGap * niveauZoom, 100);
    }

    @FXML
    void annuler(ActionEvent event) {
	Main.activerMenu();
    }

}
