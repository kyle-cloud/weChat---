import java.io.File;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("F:/1.txt");
		if ( file.exists() && file.isFile() ) {
			file.delete();
			file = null;
		}
		file = new File("F:/1.txt");
		System.out.println(file.exists()?"not delete":"hase been delete");
	}

}
