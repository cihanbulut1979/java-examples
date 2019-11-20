package tr.com.java.se7.object.creation.constructor.telescopingconstructor.builder;

//Builder Pattern
public class NutritionFacts2 {
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;

	public static class Builder {
		// Required parameters
		private final int servingSize;
		private final int servings;
		// Optional parameters - initialized to default values
		private int calories = 0;
		private int fat = 0;
		private int carbohydrate = 0;
		private int sodium = 0;

		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}

		public Builder calories(int val) {
			calories = val;
			return this;
		}

		public Builder fat(int val) {
			fat = val;
			return this;
		}

		public Builder carbohydrate(int val) {
			carbohydrate = val;
			return this;
		}

		public Builder sodium(int val) {
			sodium = val;
			return this;
		}

		public NutritionFacts2 build() {
			return new NutritionFacts2(this);
		}
	}

	private NutritionFacts2(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
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
		NutritionFacts2 cocaCola = new NutritionFacts2.Builder(240, 8)
				.calories(100)
				.sodium(35)
				.carbohydrate(27)
				.build();

		cocaCola.print();
	}
}