package wolves;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextInput {

	public static int getIntFromUser(String prompt) {
		while (true) {
			try {
				return Integer.parseInt(getUserInput(prompt));
			} catch (Exception e) {
				System.out.println("FUCK OFF THATS NOT A NUMBER");
			}
		}
	}

	public static String getNameFromUser(String prompt, String[] names) {
		while (true) {
			String s = getUserInput(prompt);
			for (int i = 0; i < names.length; i++) {
				if (names[i].equals(s)) {
					return s;
				}
			}
			System.out.println("FUCK OFF THATS NOT A NAME");
		}
	}

private static String getUserInput(String prompt) {
	BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
	System.out.println(prompt);
	try {
		return c.readLine();
	} catch (IOException e) {
		throw (new RuntimeException(e));
	}
}

}
