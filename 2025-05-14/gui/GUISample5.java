import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.stream.IntStream;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;

public class GUISample5 extends Application {

    // Fixed, though sustainability questionable. Show Elegit examples.
    public static void main(String[] args) {
        launch(args);
    }

    public static class Worker extends Thread {
        GridPane grid;
        Label label;
        ProgressBar bar;
        public Worker(GridPane grid, Label label, ProgressBar bar) {
            this.grid = grid;
            this.label = label;
            this.bar = bar;
        }

        @Override
        public void run() {
            double answer = IntStream.range(0, 100_000_000)
                                     .mapToDouble(e -> Math.sin(e))
                                     .reduce(0, (a,b) -> a + b);

            Platform.runLater(() -> {
                label.setText("The answer is " + answer);
                grid.getChildren().remove(bar);

            });

        }
    }

    @Override
    public void start(Stage primaryStage) {

        Button btn = new Button();
        btn.setText("Click me");
        btn.setFont(new Font("Arial", 24));
        GridPane grid = new GridPane();
        grid.add(btn,0,0);


        Label label = new Label("The answer will appear here");
        label.setFont(new Font("Arial", 24));
        grid.add(label, 0, 1);


        Observable<ActionEvent> btnEvents =
            JavaFxObservable.eventsOf(btn, ActionEvent.ACTION);

        final ProgressBar bar = new ProgressBar();

        btnEvents
            .doOnNext((event) -> {
                bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
                grid.add(bar,0,2);
                })

            .observeOn(Schedulers.computation())

            .map((dummy) -> {
                    return IntStream.range(0, 100_000_000)
                        .mapToDouble(e -> Math.sin(e))
                        .reduce(0, (a,b) -> a + b);
                })

            .observeOn(JavaFxScheduler.platform())

            .subscribe((answer) -> {
                    label.setText("The answer is " + answer);
                    grid.getChildren().remove(bar);
                });

        primaryStage.setScene(new Scene(grid, 500, 250));
        primaryStage.show();
    }
}
