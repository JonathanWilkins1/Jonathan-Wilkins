import java.util.Scanner;

/**
 * A program that performs basic calculations for the user.
 * It currently contains many errors.
 * 
 * @author Chad Hogg
 */
public class Calculator {

	/**
	 * Performs an arithmetic operation.
	 * 
	 * @param firstOperand The first number to operate on.
	 * @param secondOperand The second number to operate on.
	 * @param operation Either "+", "-", "*", or "/".
	 * @return The result of the operation.
	 */
	public static double calculate(double firstOperand, double secondOperand, String operation) {
		if(!operation.equals("+") && !operation.equals("-") && !operation.equals("*") && !operation.equals("/")) {
			throw new IllegalArgumentException("The operation must be +, -, *, or /.");
		}
		double result = 0.0;
		if(operation.equals("+")) {
			result = firstOperand + secondOperand;
		}
		else if(operation.equals("-")) {
			result = firstOperand - secondOperand;
		}
		else if(operation.equals("*")) {
			result = firstOperand * secondOperand;
		}
		else {
			result = firstOperand / secondOperand;
		}
		return result;
	}
	
	/**
	 * Gets the special name of an operand based on the operator and its position.
	 * The rules follow the table below:
	 *    1st           2nd
	 *  + augend        addend
	 *  - minuend       subtrahend
	 *  * multiplicand  multiplier
	 *  / dividend      divisor
	 * 
	 * @param operator Either +, -, *, or /.
	 * @param isFirst Whether or not we are looking for the name of the first operand.
	 * @return The name of the operand.
	 */
	public static String getOperandName(String operator, boolean isFirst) {
		String answer = "operand";
		if(operator.equals("+")) {
			if(isFirst) {
				answer = "Augend";
			}
			else {
				answer = "Addend";
			}
		}
		else if(operator.equals("-")) {
			if(isFirst) {
				answer = "Minuend";
			}
			else {
				answer = "Subtrahend";
			}
		}
		else if(operator.equals("*")) {
			if(isFirst) {
				answer = "Multiplicand";
			}
			else {
				answer = "Multiplier";
			}
		}
		else if(operator.equals("/")) {
			if(isFirst) {
				answer = "Dividend";
			}
			else {
				answer = "Divisor";
			}
		}
		else {
			throw new IllegalArgumentException("Operator must be +, -, *, or /.");
		}
		return answer;
	}

	/**
	 * Asks the user what operation to do.
	 * Repeats the question until it gets 
	 * 
	 * @param console A Scanner from which we can read input.
	 * @return The operator that the user chose, either "+", "-", "*", or "/".
	 */
	public static String getOperation(Scanner console) {
		System.out.print("Enter +, -, *, or /: ");
		String operation = console.next();
		while(!operation.equals("+") && !operation.equals("-") && !operation.equals("*") && !operation.equals("/")) {
			System.out.println("Please try again.");
			System.out.print("Enter +, -, *, or /: ");
			operation = console.next();
		}
		return operation;
	}
	
	/**
	 * Asks the user for a number.
	 * 
	 * @param console A Scanner from which we can read input.
	 * @param prompt The String that should be printed to prompt the user.
	 * @return The number that the user typed in.
	 */
	public static double getDouble(Scanner console, String prompt) {
		double answer = 0.0;
		System.out.print(prompt);
		while(!console.hasNextDouble()) {
			System.out.println("That is not a number.");
			System.out.print(prompt);
			console.next(); // Dummy call to clear buffer.
		}
		answer = console.nextDouble();
		return answer;
	}
		
	/**
	 * Runs the calculator program.
	 * 
	 * @param console A Scanner from which we can get user input.
	 */
	public static void runProgram(Scanner console) {
		String doAgain = "yes";
		while(!doAgain.equals("no")) {
			if(doAgain.equals("yes")) {
				String operation = getOperation(console);
				double firstOperand = getDouble(console, getOperandName(operation, true) + ": ");
				double secondOperand = getDouble(console, getOperandName(operation, false) + ": ");
				double answer = calculate(firstOperand, secondOperand, operation);
				System.out.println(firstOperand + " " + operation + " " + secondOperand + " = " + answer);
				System.out.print("Would you like to do another calculation?: ");
				doAgain = console.next();
			}
			else {
				System.out.println("That was not an acceptable answer.");
				System.out.print("Would you like to do another calculation?: ");
				doAgain = console.next();
			}
		}
		System.out.println("Goodbye!");
	}
	
	/**
	 * Runs tests to ensure that the calculate method works correctly.
	 */
	public static void testCalculate() {
		double expectedAnswer;
		double receivedAnswer;
		
		receivedAnswer = calculate(10.0, 7.0, "+");
		expectedAnswer = 17.0;
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = calculate(10.0, 5.3, "-");
		expectedAnswer = 4.7;
		System.out.println("Second test should have been " + expectedAnswer + " was " + receivedAnswer);

		receivedAnswer = calculate(3.4, 6.1, "*");
		expectedAnswer = 20.74;
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = calculate(4.0, 0.4, "/");
		expectedAnswer = 10.0;
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = calculate(12.0, 1.0, "%");
		expectedAnswer = 0.0;
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
	}
	
	/**
	 * Runs tests to ensure that the getOperandName method works correctly.
	 */
	public static void testGetOperandName() {
		String expectedAnswer;
		String receivedAnswer;
		
		receivedAnswer = getOperandName("+", true);
		expectedAnswer = "Augend";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("-", true);
		expectedAnswer = "Minuend";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("*", true);
		expectedAnswer = "Multiplicand";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("/", true);
		expectedAnswer = "Dividend";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("+", false);
		expectedAnswer = "Addend";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("-", false);
		expectedAnswer = "Subtrahend";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("*", false);
		expectedAnswer = "Multiplier";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("/", false);
		expectedAnswer = "Divisor";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
		
		receivedAnswer = getOperandName("%", true);
		expectedAnswer = "Exception";
		System.out.println("First test should have been " + expectedAnswer + " was " + receivedAnswer);
	}
	
	/**
	 * Runs the program.
	 * 
	 * @param args Not yet explained or used.
	 */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		runProgram(console);
	}
}
