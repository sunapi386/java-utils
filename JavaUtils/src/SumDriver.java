public class SumDriver {

	/**
	 * Enter a number after calling program
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		if (args.length < 1) {
			System.err.println("Usage: Summation <int value>");
			System.exit(-1);
		}
		int upper = Integer.parseInt(args[0]);
		if (upper < 0) {
			System.err.println(args[0] + " must be positive");
			System.exit(-1);
		}
		SumObject s_obj = new SumObject();
		Thread thrd = new Thread(new Summation(upper, s_obj));
		thrd.start();
		try {
			// Wait for thread to finish
			thrd.join();
			System.out.println(s_obj.getSum());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
