
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class SearchBox extends BorderPane {
	private HBox hbox = new HBox();
	private TextField textSearch = new TextField();
	private Button btnGo = new Button("GO!");
	final int GAP = 10;

	public SearchBox() {
		hbox.getChildren().addAll(textSearch, btnGo);
		hbox.setAlignment(Pos.CENTER);
		setTop(hbox);
		hbox.setPadding(new Insets(12, 1, 1, 1));
		textSearch.setPromptText("search...");
		textSearch.setPrefWidth(550);
		hbox.setSpacing(GAP);

	}

	public Button getGO() {
		return (Button) hbox.getChildren().get(1);
	}

	public TextField getTextField() {
		return textSearch;
	}

	public void setTextField(TextField t) {
		textSearch = t;
	}
}