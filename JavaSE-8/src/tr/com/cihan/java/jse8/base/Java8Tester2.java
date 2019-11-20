package tr.com.cihan.java.jse8.base;

public class Java8Tester2 {
	   public static void main(String args[]){
		   Java8Tester2 tester = new Java8Tester2();
		   
		   //Method ad�n� yazm�yosun paramtre ve d�n��� yaz�yosun, d�n�� tipi function oluyor
		   //o function�n i�lemin�i tan�mlama laz�m operate gibi
			
	      //with type declaration
	      MathOperation addition = (int a, int b) -> a + b;
			
	      //with out type declaration
	      MathOperation subtraction = (a, b) -> a - b;
			
	      //with return statement along with curly braces
	      MathOperation multiplication = (int a, int b) -> { return a * b; };
			
	      //without return statement and without curly braces
	      MathOperation division = (int a, int b) -> a / b;
			
	      System.out.println("10 + 5 = " + tester.operate(10, 5, addition));
	      System.out.println("10 - 5 = " + tester.operate(10, 5, subtraction));
	      System.out.println("10 x 5 = " + tester.operate(10, 5, multiplication));
	      System.out.println("10 / 5 = " + tester.operate(10, 5, division));
			
	      //with parenthesis
	      GreetingService greetService1 = message ->
	      System.out.println("Hello " + message);
			
	      //without parenthesis
	      GreetingService greetService2 = (message) ->
	      System.out.println("Hello " + message);
			
	      greetService1.sayMessage("Mahesh");
	      greetService2.sayMessage("Suresh");
	   }
		
	   interface MathOperation {
	      int operation(int a, int b);
	   }
		
	   interface GreetingService {
	      void sayMessage(String message);
	   }
		
	   public int operate(int a, int b, MathOperation mathOperation){
	      return mathOperation.operation(a, b);
	   }
	}