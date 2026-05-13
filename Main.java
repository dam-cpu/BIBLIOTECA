

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        
        // Iniciar la aplicación GUI en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BibliotecaGUI();
            }
        });
    }
}
