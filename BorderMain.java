import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class BorderMain extends Application {

	private ButtomPart buttomPart = new ButtomPart();
	private GridMain gridMain = new GridMain();
	private SearchBox searchBox = new SearchBox();
	private BorderPane borderPane = new BorderPane();
	private AddPane addpane = new AddPane();
	private ArrayList<MusicalInstrument> musical = new ArrayList<>();
	private ArrayList<MusicalInstrument> arrayToShow = new ArrayList<>();
	private Button btnright = new Button(">");
	private Button btnleft = new Button("<");
	private int index = 0;
	private Scene scene = new Scene(borderPane);
	private SplitPane split = new SplitPane();
	private SplitPane split1 = new SplitPane();
	private ArrayList<String> fileList = new ArrayList<>();
	private boolean filesOk;

	public ArrayList<MusicalInstrument> getarray() {
		return arrayToShow;
	}

	public BorderMain() throws IOException {
		getInstFromUserFile();
		arrayToShow = new ArrayList<MusicalInstrument>(musical);
		gridMain.putInfo(arrayToShow.get(0));
	}

	// -------------------------------------------------
	public static void main(String[] args) {
		launch(args);
	}

	// -------------------------------------------------
	private void getInstFromUserFile() throws IOException {
		boolean continueTheLoop;
		readFilesFromDirectory(fileList);
		do {
			continueTheLoop = true;
			ChoiceDialog<String> fileChoiceDialog = new ChoiceDialog<>("Choose your file:", fileList);
			fileChoiceDialog.setTitle("please Choose your file");
			fileChoiceDialog.setHeaderText("Load Instruments From File");
			fileChoiceDialog.setContentText("Please choose a file:");
			((Stage) fileChoiceDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
			Optional<String> result = fileChoiceDialog.showAndWait();

			continueTheLoop = result.isPresent();
			result.ifPresent(file -> {
				try {
					AfekaInstruments.loadInstrumentsFromFile(new Scanner(new File(file)), musical);
					filesOk = true;
				} catch (FileNotFoundException e) {
					setErrorDialog("File not found, please choose another file");
				} catch (NoSuchElementException | IllegalArgumentException ex) {
					setErrorDialog(ex.getMessage());
				}
			});
		} while (!filesOk && continueTheLoop);

		if (!continueTheLoop)
			System.exit(0);

	}

	// read files from directory
	public void readFilesFromDirectory(ArrayList<String> files) throws IOException {
		try (Stream<Path> streanPath = Files.walk(Paths.get(""))) {
			streanPath.filter(Files::isRegularFile).forEach(path -> {
				if (path.toString().contains(".txt"))
					files.add(path.toString());
			});
		}
	}

	// set error dialog
	public void setErrorDialog(String s) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("File Error!");
		alert.setContentText(s);

		alert.showAndWait();
	}

	@Override
	public void start(Stage primaryStage) {

		final int H = 300, W = 450;
		split.getItems().addAll(searchBox, split1, buttomPart);
		split.setOrientation(Orientation.VERTICAL);
		split1.getItems().add(gridMain);
		split1.setOrientation(Orientation.HORIZONTAL);

		primaryStage.setTitle("Afeka Instrument Music Store");
		borderPane.setCenter(split);
		borderPane.setTop(searchBox);
		borderPane.setBottom(buttomPart);

		BorderPane.setAlignment(btnright, Pos.CENTER);
		BorderPane.setAlignment(btnleft, Pos.CENTER);

		borderPane.setLeft(btnleft);
		borderPane.setRight(btnright);

		borderPane.setPadding(new Insets(15, 15, 15, 15));

		// scene2.set
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setHeight(H);
		primaryStage.setHeight(W);
		primaryStage.show();

		// Keyboard Action
		setKeybordListeners();

		// <--- action
		btnleft.setOnAction(e -> btnLeftAction());
		// ---> action
		btnright.setOnAction(e -> btnRightAction());

		// Delete action

		buttomPart.getDeleteButton().setOnAction(e -> dltAction());

		// Add Tab action
		buttomPart.getAddTabButton().setOnAction(e -> addTabAction());

		// search box action
		searchBox.getGO().setOnAction(e -> {
			if (!musical.isEmpty())
				SearchAction();
		});
		// Add ins action
		buttomPart.getAddIns().setOnAction(e -> addinsAction());

		// cancel action
		buttomPart.getCancel().setOnAction(e -> {
			split1.getItems().remove(0);
			split1.getItems().add(gridMain);
			buttomPart.getvbox().getChildren().set(0, buttomPart.gethabox1());

		});

		buttomPart.getClear().setOnAction(e -> {
			arrayToShow.clear();
			musical.clear();
			clearshow();
		});

	}

	// Keyboard Action
	private void setKeybordListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if (split1.getItems().get(0).getClass() == gridMain.getClass()) {
				if (e.getCode() == KeyCode.A && !searchBox.getTextField().isFocused())
					addTabAction();

				if (e.getCode() == KeyCode.DELETE && !searchBox.getTextField().isFocused())
					dltAction();

				if (e.getCode() == KeyCode.ENTER) {
					if (!musical.isEmpty())
						SearchAction();
				}
				if (e.getCode() == KeyCode.LEFT)
					btnLeftAction();
				if (e.getCode() == KeyCode.RIGHT)
					btnRightAction();
			}
		});
	}

	// -----------------------------------------------------
	// btnLeftAction
	public void btnLeftAction() {
		index--;
		if (arrayToShow.isEmpty()) {
			clearshow();
		} else {
			if (index < 0)
				index = arrayToShow.size() - 1;
			gridMain.putInfo(arrayToShow.get(index));
		}
	}

	// btnRightAction
	public void btnRightAction() {
		index++;
		if (arrayToShow.isEmpty()) {
			clearshow();
		} else {
			if (index > arrayToShow.size() - 1)
				index = 0;// back to the start;
			gridMain.putInfo(arrayToShow.get(index));
		}

	}

	// addTabAction
	public void addTabAction() {

		split1.getItems().remove(0);
		addpane = new AddPane();
		split1.getItems().add(addpane);
		buttomPart.getvbox().getChildren().set(0, buttomPart.gethabox2());
	}

	public SplitPane getSplit() {
		return split;
	}

	// addinsAction
	public void addinsAction() {

		boolean isInsAdded = addpane.addinst(musical);
		if (isInsAdded == true) {
			arrayToShow = new ArrayList<MusicalInstrument>(musical);
			split1.getItems().remove(0);
			split1.getItems().add(gridMain);
			buttomPart.getvbox().getChildren().set(0, buttomPart.gethabox1());
			gridMain.putInfo(arrayToShow.get(arrayToShow.size() - 1));

		}
	}

	// clear show
	public void clearshow() {
		gridMain.getTxtBrand().setText(null);
		gridMain.getTxtPrice().setText("");
		gridMain.getTxtType().setText(null);
		split1.getItems().remove(0);
		split1.getItems().add(gridMain);

	}

	// delete Action
	public void dltAction() {
		if (arrayToShow.isEmpty())
			clearshow();
		else {
			MusicalInstrument m = arrayToShow.remove(index--);
			musical.remove(m);
			if (arrayToShow.size() != 0) {
				if (index < 0) {
					index = arrayToShow.size() - 1;
				}
				gridMain.putInfo(arrayToShow.get(index));
			} else {
				clearshow();
			}
		}
	}

	// Search action
	public void SearchAction() {
		clearshow();
		buttomPart.getvbox().getChildren().set(0, buttomPart.gethabox1());
		arrayToShow.clear();

		if (!searchBox.getTextField().getText().isEmpty()) {
			for (int i = 0; i < musical.size(); i++) {
				MusicalInstrument m = musical.get(i);
				if (m.toString().toLowerCase().contains(searchBox.getTextField().getText().toLowerCase())) {
					arrayToShow.add(m);
				}
			}

		} else {
			arrayToShow = new ArrayList<MusicalInstrument>(musical);
		}

		index = 0;
		if (!arrayToShow.isEmpty()) {
			gridMain.putInfo(arrayToShow.get(index));
		}

	}

}
