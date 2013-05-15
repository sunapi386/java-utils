//A thread class
public class Summation implements Runnable {
	private final int upper;
	private final SumObject s_obj;

	public Summation(int sumTo, SumObject sum_object) {
		upper = sumTo;
		s_obj = sum_object;
	}

	@Override
	public void run() {
		int current_sum = 0;
		for (int i = 0; i <= upper; i++) {
			current_sum += 1;
		}
		s_obj.setSum(current_sum);
	}

}
