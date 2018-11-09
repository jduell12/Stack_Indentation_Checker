
import java.io.*;
import java.util.*;

public class Stack_indentation_checker {

	class IndentException extends RuntimeException {
		IndentException(String error) {
			super(error);
		}
	}

	private Stack<Integer> indentStack = new Stack<Integer>();

	public void checkIndent(String fileName) {
		Scanner input = null;
		indentStack.clear();
		try {
			input = new Scanner(new File(fileName));
			int lineNumber = 1;
			while (input.hasNextLine()) {
				String line = input.nextLine();
				System.out.println(lineNumber + ":" + line);
				processLine(line, lineNumber);

				lineNumber += 1;
			}
			if (indentStack.isEmpty()) {
				System.out.println(fileName + " has proper indentation.");
			} else {
				while (!indentStack.isEmpty()) {

				}
			}
		} catch (IndentException error) {
			System.out.println(error);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open: " + fileName);
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	private int findFirstNonBlank(String line) {
		int index = 0;
		
		if (line.isEmpty()) {
			index = -1;
		} else {
			char [] letters = line.toCharArray();
			for (int i = 0; i < letters.length; i++ ) {
				if (Character.isWhitespace(letters[i])) {
					index = i;
				} else {
					index = i;
					i = letters.length;
				}
			}
		}
		System.out.println("index: " + index + " ");
		return index;
		
	}

	private void processLine(String line, int lineNumber) {
		int index = findFirstNonBlank(line);
		

		if (index == -1) {
			System.out.print("");
		} else {
			if (indentStack.isEmpty()) {
				indentStack.push(index);
			} else if (indentStack.peek() < index) {
				indentStack.push(index);
			} else if (indentStack.peek() > index) {
				indentStack.pop();
			}

			if (index != indentStack.peek()) {
				String error1 = "Bad indentation syntax found at lineNumber: " + lineNumber;
				throw new IndentException(error1);
			}
		}
		
	}

	public static void main(String[] args) {
		Stack_indentation_checker stackCheck = new Stack_indentation_checker();

		for (int i = 0; i < args.length; i++) {
			System.out.println("Processing file " + args[i]);
			stackCheck.checkIndent(args[i]);
		}
	}
}
