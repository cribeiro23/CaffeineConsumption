package rand;

import java.io.IOException;

public class Controller {

	public static void main(String[] args) {
		Person person1 = new Person("John", "GENOMELINKTEST");
		
		try {
			person1.setScore("caffeine-metabolite-ratio","european");
		}
		catch( IOException e ) {
			System.out.println("IO exception occured!");
			System.out.println(e);
			System.exit(1);
		}
		
		System.out.println(person1.getScore());
		
		person1.setDailyAmount();
		System.out.println(person1.getDailyAmount());
		
		person1.deductCaffeine(100);
		person1.deductCaffeine(100);
		person1.deductCaffeine(100);
		person1.deductCaffeine(100);
		person1.deductCaffeine(100);
	}

}
