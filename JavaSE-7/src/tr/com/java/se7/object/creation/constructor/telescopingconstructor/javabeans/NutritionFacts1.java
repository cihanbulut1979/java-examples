package tr.com.java.se7.object.creation.constructor.telescopingconstructor.javabeans;

//JavaBeans Pattern - allows inconsistency, mandates mutability
public class NutritionFacts1 {
	// Parameters initialized to default values (if any)
	private int servingSize = -1; // Required; no default value
	private int servings = -1; // " " " "
	private int calories = 0;
	private int fat = 0;
	private int sodium = 0;
	private int carbohydrate = 0;

	public NutritionFacts1() {

	}

	// Setters
	public void setServingSize(int val) {
		servingSize = val;
	}

	public void setServings(int val) {
		servings = val;
	}

	public void setCalories(int val) {
		calories = val;
	}

	public void setFat(int val) {
		fat = val;
	}

	public void setSodium(int val) {
		sodium = val;
	}

	public void setCarbohydrate(int val) {
		carbohydrate = val;
	}

	public void print() {

		StringBuffer str = new StringBuffer();
		str.append("nservingSize 	: " + servingSize);
		str.append("\nservings 		: " + servings);
		str.append("\ncalories 		: " + calories);
		str.append("\nfat 			: " + fat);
		str.append("\nsodium 		: " + sodium);
		str.append("\ncarbohydrate 	: " + carbohydrate);

		System.out.println(str.toString());
	}

	public static void main(String[] args) {
		NutritionFacts1 cocaCola = new NutritionFacts1();
		
		cocaCola.setServingSize(240);
		cocaCola.setServings(8);
		cocaCola.setCalories(100);
		cocaCola.setFat(0);
		cocaCola.setSodium(35);
		cocaCola.setCarbohydrate(27);
		
		cocaCola.print();
	}
}
