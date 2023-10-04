module sio.tp2cabinetmedical {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens sio.tp2cabinetmedical to javafx.fxml;
    exports sio.tp2cabinetmedical;
}