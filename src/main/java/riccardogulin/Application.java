package riccardogulin;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d13pu");

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
