import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;

public class GridMain extends BorderPane {
	private GridPane gridpane = new GridPane();

	private TextField txtType = new TextField();
	private TextField txtBrand = new TextField();
	private TextField txtPrice = new TextField();
	private Label lableType = new Label("Type:");
	private Label lableBrand = new Label("Brand:");
	private Label lablePrice = new Label("Price:");

	public GridMain() {
		final int GAP = 10;

		int row = 0;
		txtType.setEditable(false);
		txtBrand.setEditable(false);
		txtPrice.setEditable(false);
		gridpane.addRow(row++, lableType, txtType);
		gridpane.addRow(row++, lableBrand, txtBrand);
		gridpane.addRow(row++, lablePrice, txtPrice);

		gridpane.setHgap(GAP);
		gridpane.setVgap(GAP);

		txtBrand.setPromptText("No Item");
		txtPrice.setPromptText("No Item");
		txtType.setPromptText("No Item");

		gridpane.setAlignment(Pos.CENTER);
		setCenter(gridpane);

	}

	public void putInfo(MusicalInstrument m) {
		txtType.setText(m.getClass().getCanonicalName());
		txtBrand.setText(m.getBrand());
		txtPrice.setText(String.format("%.1f", m.getPrice().doubleValue()));

	}

	public TextField getTxtType() {
		return txtType;
	}

	public TextField getTxtBrand() {
		return txtBrand;
	}

	public TextField getTxtPrice() {
		return txtPrice;
	}

}
