import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


public class FileExplorer {
	public static void main(String[] args) throws IOException
	{
		Desktop desktop = Desktop.getDesktop();
		File directory = new File("C:\\");
		
		try {
			desktop.open(directory);
		} catch (IllegalArgumentException e) {
			System.out.println("File not found");
		}
	}
}