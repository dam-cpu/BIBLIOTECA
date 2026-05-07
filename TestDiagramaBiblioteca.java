import java.awt.*;

public class TestDiagramaBiblioteca extends Frame {

    private DiagramaBiblioteca diagrama;

    public TestDiagramaBiblioteca(String titulo) {
        super(titulo);
        diagrama = new DiagramaBiblioteca();
        add(diagrama, "Center");
    }

    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY) {
            hide();
            dispose();
            return true;
        }
        return super.handleEvent(e);
    }
}