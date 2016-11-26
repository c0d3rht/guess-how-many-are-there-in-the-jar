import com.hariidaran.Jar;
import com.hariidaran.Prompter;
import com.hariidaran.ReadWriter;

public class Game {

    public static void main(String[] args) {

        // File from which data is to be imported from and exported to
        final String SAVE_FILE = "data.txt";

        // Send data to prompter
        Prompter prompter = new Prompter(new Jar());

        // I/O object gets to know the prompter
        ReadWriter readWriter = new ReadWriter(prompter);

        // Import data from file
        readWriter.readFrom(SAVE_FILE);

        // Start prompting
        prompter.play();

        // Save data to file
        readWriter.saveTo(SAVE_FILE);
    }
}