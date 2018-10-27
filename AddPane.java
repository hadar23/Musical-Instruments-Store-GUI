import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddPane extends BorderPane {
	private GridPane gridpane = new GridPane();
	final ObservableList<String> INSTRUMENTS = FXCollections
			.observableList(Arrays.asList("Guitar", "Bass", "Flute", "Saxophone"));
	final ObservableList<String> GUITAR_TYPES = FXCollections
			.observableList(Arrays.asList("Electric", "Acoustic", "Classical"));
	final ObservableList<String> MATERIAL = FXCollections.observableList(Arrays.asList("Metal", "Wood", "Plastic"));
	final ObservableList<String> FLUTE_TYPES = FXCollections
			.observableList(Arrays.asList("Transverse", "Bass", "Recorder"));

	private ComboBox<String> comboGuitarType = new ComboBox<>(GUITAR_TYPES);

	private VBox vbox = new VBox();
	private int row = 0;

	private TextField txtBrand = new TextField();
	private TextField txtPrice = new TextField();
	private ComboBox<String> combo = new ComboBox<>(INSTRUMENTS);
	private ComboBox<String> comboTypeFlute = new ComboBox<>(FLUTE_TYPES);
	private ComboBox<String> comboMaterialFlute = new ComboBox<>(MATERIAL);
	private Button btnAdd = new Button("Add");
	private TextField txtStringGuitar = new TextField();
	private TextField txtNumStringBass = new TextField();
	private CheckBox checkBass = new CheckBox();

	public AddPane() {
		final int GAP = 20;
		vbox.getChildren().addAll(combo);
		vbox.setPadding(new Insets(5, 1, 5, 1));
		gridpane.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);
		row = 1;
		setCenter(vbox);
		gridpane.setVgap(GAP);
		gridpane.setHgap(GAP);
		vbox.getChildren().add(gridpane);
		combo.setPromptText("choose an instrument");
		combo.setOnAction(e -> setGrid());
	}

	public boolean addinst(ArrayList<MusicalInstrument> musicArray) {
		try {
			checkIfComboeEmpty(combo);
			String s = combo.getValue();
			switch (s) {
			case "Guitar": {
				checkIfPriceAndBrandempty(txtPrice, txtBrand);
				checkIfStringEmpty(txtStringGuitar);
				checkIfTypeEmpty(comboGuitarType);

				musicArray.add(new Guitar(changedoube(txtPrice.getText()), txtBrand.getText(),
						changInt(txtStringGuitar.getText()), comboGuitarType.getValue()));
			}
				break;

			case "Saxophone": {
				checkIfPriceAndBrandempty(txtPrice, txtBrand);

				musicArray.add(new Saxophone(changedoube(txtPrice.getText()), txtBrand.getText()));
			}
				break;

			case "Flute": {
				checkIfPriceAndBrandempty(txtPrice, txtBrand);
				checkIfTypeEmpty(comboTypeFlute);
				checkIfmaterialEmpty(comboMaterialFlute);

				musicArray.add(new Flute(changedoube(txtPrice.getText()), txtBrand.getText(),
						comboMaterialFlute.getValue(), comboTypeFlute.getValue()));
			}
				break;

			case "Bass": {
				checkIfPriceAndBrandempty(txtPrice, txtBrand);
				checkIfStringEmpty(txtNumStringBass);

				musicArray.add(new Bass(changedoube(txtPrice.getText()), txtBrand.getText(),
						changInt(txtNumStringBass.getText()), checkBass.isSelected()));
			}
				break;

			}
			return true;

		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("File Error!");
			alert.setContentText("\n" + ex.getMessage());
			Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			stage.setAlwaysOnTop(true);
			alert.showAndWait();
		}
		return false;

	}

	// Exceptions
	public void checkIfPriceAndBrandempty(TextField price, TextField brand) {
		if (brand.getText().isEmpty())
			throw new IllegalArgumentException("Brand field cannot be empty");
		if (price.getText().isEmpty())
			throw new IllegalArgumentException("Price field cannot be empty");
	}

	public void checkIfStringEmpty(TextField string) {
		if (string.getText().isEmpty())
			throw new IllegalArgumentException("Number Of String field cannot be empty");
	}

	public void checkIfTypeEmpty(ComboBox<String> type) {
		if (type.getValue() == null)
			throw new IllegalArgumentException("Type box cannot be empty");
	}

	public void checkIfComboeEmpty(ComboBox<String> type) {
		if (type.getValue() == null)
			throw new IllegalArgumentException("please choose instrumnt");
	}

	public void checkIfmaterialEmpty(ComboBox<String> material) {
		if (material.getValue() == null)
			throw new IllegalArgumentException("Material box cannot be empty");
	}

	public int changInt(String s) {
		int price;
		try {
			price = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("string input is not valid!");
		}
		return price;

	}

	public double changedoube(String s) {
		Double price;
		try {
			price = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Price must be a number!");
		}
		return price;

	}

	public void mainSet() {
		vbox.getChildren().removeAll();
		vbox.getChildren().add(combo);
	}

	public void firstSet() {

		while (row > 1) {
			gridpane.getChildren().clear();
			row = 1;
		}
		txtBrand = new TextField();
		txtPrice = new TextField();
		gridpane.addRow(row++, new Label("Brand:"), txtBrand);
		gridpane.addRow(row++, new Label("Price:"), txtPrice);
		btnAdd.setAlignment(Pos.BOTTOM_RIGHT);
	}

	public void SaxsopgoneSet() {
		firstSet();
		txtBrand.setPromptText("");
		txtPrice.setPromptText(null);
	}

	public void GuitarSet() {
		firstSet();
		txtBrand.setPromptText("EX: Gibson");
		txtPrice.setPromptText("EX:7500");

		txtStringGuitar = new TextField();
		gridpane.addRow(row++, new Label("Number of string:"), txtStringGuitar);
		txtStringGuitar.setPromptText("EX:6");

		comboGuitarType = new ComboBox<>(GUITAR_TYPES);
		comboGuitarType.setPromptText("Type");

		gridpane.addRow(row++, new Label("Guitar type:"), comboGuitarType);
	}

	public void BassSet() {
		firstSet();
		txtBrand.setPromptText("EX: Fender Jazz");
		txtPrice.setPromptText("EX: 7500");

		txtNumStringBass = new TextField();
		gridpane.addRow(row++, new Label("Number of string:"), txtNumStringBass);
		checkBass = new CheckBox();
		gridpane.addRow(row++, new Label("Fretless:"), checkBass);

		txtNumStringBass.setPromptText("EX:4");
	}

	public void FlutSet() {

		firstSet();
		txtBrand.setPromptText("EX: Gibson");
		txtPrice.setPromptText("EX:7500");

		comboMaterialFlute = new ComboBox<>(MATERIAL);

		comboMaterialFlute.setPromptText("Material");

		comboTypeFlute = new ComboBox<>(FLUTE_TYPES);

		comboTypeFlute.setPromptText("Type");

		gridpane.addRow(row++, new Label("Material:"), comboMaterialFlute);
		gridpane.addRow(row++, new Label("Flute type:"), comboTypeFlute);
	}

	public void setGrid() {

		if (combo.getValue().compareTo("Guitar") == 0)
			GuitarSet();
		if (combo.getValue().compareTo("Bass") == 0)
			BassSet();
		if (combo.getValue().compareTo("Flute") == 0)
			FlutSet();
		if (combo.getValue().compareTo("Saxophone") == 0)
			SaxsopgoneSet();

	}

	public VBox getVbox() {
		return vbox;
	}

	public String getComboInfo() {
		return combo.getValue();
	}

	public ComboBox<String> getInstrumentTypeCombo() {
		return combo;
	}
}