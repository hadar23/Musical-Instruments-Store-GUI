
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ButtomPart extends BorderPane {
	private HBox hbox1 = new HBox();
	private HBox hbox2 = new HBox();
	private VBox vbox = new VBox();
	PathTransition move = new PathTransition();
	Text bottomText = new Text();
	final String string1 = "Afeka instrument Music Store $$$ ON SALE!!! $$$ Guitars, Bass, Flutes, Saxpphones and more!";

	// @Override
	public ButtomPart() {
		final int GAP = 10;
		hbox2.getChildren().addAll(new Button("Add"), new Button("cancel"));
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setSpacing(GAP);
		hbox1.getChildren().addAll(new Button("Add"), new Button("Delete"), new Button("Clear"));
		hbox1.setAlignment(Pos.CENTER);

		bottomText = new Text(getTime() + string1);
		bottomText.setFill(javafx.scene.paint.Color.RED);
		bottomText.setFont(Font.font(null, FontWeight.BOLD, 12));
		hbox1.setPadding(new Insets(12, 12, 12, 12));

		hbox1.setSpacing(GAP);
		hbox1.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(hbox1, bottomText);
		vbox.setSpacing(20);
		setBottom(vbox);
		vbox.setPadding(new Insets(0, 0, 15, 0));

		KeyValue initKeyValue = new KeyValue(bottomText.translateXProperty(),
				bottomText.getLayoutBounds().getWidth() * -0.25);// start from first quarter in the right side
		KeyFrame initFrame = new KeyFrame(Duration.seconds(10), initKeyValue);

		KeyValue endKeyValue = new KeyValue(bottomText.translateXProperty(),
				0.25 * bottomText.getLayoutBounds().getWidth());
		KeyFrame endFrame = new KeyFrame(Duration.seconds(10), endKeyValue);

		// string movement
		Timeline timeline = new Timeline(initFrame, endFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		timeline.play();

		// clock changing time
		final Timeline clock = new Timeline(
				(new KeyFrame(Duration.seconds(1), e -> bottomText.setText(getTime() + string1))));
		clock.setCycleCount(Timeline.INDEFINITE);
		clock.play();

		// pause/play of text movement
		bottomText.setOnMouseMoved(e -> timeline.pause());
		bottomText.setOnMouseExited(e -> timeline.play());
	}

	public Button getAddTabButton() {
		return (Button) hbox1.getChildren().get(0);
	}

	public Button getDeleteButton() {
		return (Button) hbox1.getChildren().get(1);
	}

	public Button getClear() {
		return (Button) hbox1.getChildren().get(2);
	}

	public Button getAddIns() {
		return (Button) hbox2.getChildren().get(0);
	}

	public Button getCancel() {
		return (Button) hbox2.getChildren().get(1);
	}

	public HBox gethabox1() {
		return hbox1;
	}

	public HBox gethabox2() {
		return hbox2;
	}

	public VBox getvbox() {
		return vbox;
	}

	private String getTime() {

		String time1 = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String time = date + " " + time1 + " ";
		return time;
	}
}
