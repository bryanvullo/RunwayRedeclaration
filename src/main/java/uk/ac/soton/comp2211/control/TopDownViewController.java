package uk.ac.soton.comp2211.control;// TopDownViewController.java

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uk.ac.soton.comp2211.dataStructure.ErrorNotification;
import uk.ac.soton.comp2211.dataStructure.Notification;

public class TopDownViewController {

    @FXML
    private VBox messagesVBox; // 确保这个ID与FXML文件中的VBox ID匹配

    // 这个方法添加一条新的通知到VBox中
    public void addNotification(String date, String time, String notificationMessage) {
        // 确保UI更新在JavaFX主线程上执行
        Platform.runLater(() -> {
            HBox newMessageHBox = new HBox();
            newMessageHBox.setPadding(new Insets(5, 10, 5, 10));
            newMessageHBox.setSpacing(10);
            newMessageHBox.setStyle("-fx-background-color: #ececec; -fx-background-radius: 10;");

            Text dateText = new Text(date);
            Label timeLabel = new Label(time);
            Label messageLabel = new Label(notificationMessage);

            // 设置样式
            dateText.setStyle("-fx-font-size: 10;");
            timeLabel.setStyle("-fx-font-size: 10;");
            messageLabel.setStyle("-fx-font-size: 10;");

            // 添加子元素到HBox
            newMessageHBox.getChildren().addAll(dateText, timeLabel, messageLabel);

            // 将HBox添加到VBox中
            messagesVBox.getChildren().add(newMessageHBox); // 如果你希望它在底部添加消息，不要使用0作为索引
        });
    }

    // if u want use it, such as：
    @FXML
    private void handleAddNotificationClick() {
        addNotification("10-10-2024", "12:00", "This is a test notification.");
    }


    public void addNotification(Notification notification) {
        Platform.runLater(() -> {
            HBox newMessageHBox = new HBox();
            newMessageHBox.setPadding(new Insets(5, 10, 5, 10));
            newMessageHBox.setSpacing(10);
            newMessageHBox.setStyle("-fx-background-color: #ececec; -fx-background-radius: 10;");

            Text dateText = new Text(notification.getDate());
            Label timeLabel = new Label(notification.getTime());
            Label messageLabel = new Label(notification.getMessage());

            // Set styles
            dateText.setStyle("-fx-font-size: 10;");
            timeLabel.setStyle("-fx-font-size: 10;");
            messageLabel.setStyle("-fx-font-size: 10;");

            newMessageHBox.getChildren().addAll(dateText, timeLabel, messageLabel);
            messagesVBox.getChildren().add(newMessageHBox);
        });
    }

    public void addErrorNotification(ErrorNotification errorNotification) {
        Platform.runLater(() -> {
            HBox newErrorHBox = new HBox();
            newErrorHBox.setPadding(new Insets(5, 10, 5, 10));
            newErrorHBox.setSpacing(10);
            newErrorHBox.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 10;"); // 使用红色背景

            Text errorText = new Text(errorNotification.getTimestamp() + ": " + errorNotification.getErrorMessage());
            errorText.setStyle("-fx-fill: red; -fx-font-size: 10; -fx-font-weight: bold;");

            newErrorHBox.getChildren().add(errorText);
            messagesVBox.getChildren().add(0, newErrorHBox); // 将新错误消息添加到VBox的顶部
        });
    }
}
