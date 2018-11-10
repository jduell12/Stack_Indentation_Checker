
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
		} catch (IndentException error) {
			System.out.println(error);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open: " + fileName);
		} finally {
			if (input != null) {
				input.close();
			}
		}
		if (indentStack.peek() == 0) {
			System.out.println("***************" + fileName + " must be properly indented.");
		}

	}

	//finds the index of the first non blank character. If a line is empty the program prints the line number and moves to the next line
	private int findFirstNonBlank(String line) {
		int index = 0;

		if (line.isEmpty()) {
			index = -1;
		} else {
			char[] letters = line.toCharArray();
			for (int i = 0; i < letters.length; i++) {
				if (Character.isWhitespace(letters[i])) {
					index = -1;
				} else {
					index = i;
					i = letters.length;
				}
			}
		}
		return index;

	}

	// processes each line of the file. Checks the index of the first non blank character against the indices in the stack
	private void processLine(String line, int lineNumber) {
		int index = findFirstNonBlank(line);
		Object[] stackArr = indentStack.toArray();

		int a = 10;
		if (index == -1) {
			a = -1;
		} else if (indentStack.isEmpty()) {
			a = 0;
		} else if (indentStack.peek() < index) {
			a = 1;
		} else if (indentStack.peek() > index) {
			a = 2;
		}

		switch (a) {
		case -1:
			break;
		case 0:
			indentStack.push(index);
			break;
		case 1:
			indentStack.push(index);
			break;
		case 2:
			for (int i = 0; i < stackArr.length; i++) {
				if ((Integer) stackArr[i] > index) {
					indentStack.pop();
				}
			}

			if (indentStack.peek() != index) {
				String error1 = "Bad indentation syntax found at lineNumber: " + lineNumber;
				throw new IndentException(error1);
			}
			break;
		default:
			System.out.println("default");
			break;
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
