package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.BlogsDAO;
import riccardogulin.dao.DocumentsDAO;
import riccardogulin.dao.UsersDAO;
import riccardogulin.entities.Blog;
import riccardogulin.entities.User;
import riccardogulin.exceptions.NotFoundException;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d13pu");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();

		UsersDAO ud = new UsersDAO(em);
		DocumentsDAO dd = new DocumentsDAO(em);
		BlogsDAO bd = new BlogsDAO(em);

		User aldo = new User("Aldo", "Baglio");
		User giovanni = new User("Giovanni", "Storti");
		User giacomo = new User("Giacomo", "Poretti");

//		ud.save(aldo);
//		ud.save(giovanni);
//		ud.save(giacomo);

		// ******************************************* 1to1 *******************************

		// Document aldoDoc = new Document("12321321", "Italy", aldo);
		// dd.save(aldoDoc);
		// Non posso usare aldo direttamente (a meno che io non lo stia salvando in questo momento)
		// perché non fa parte del Persistence Context (NON E' MANAGED!!) è un oggetto Java semplicissimo (non ha neanche l'ID!)
		// Per poter instaurare una relazione e quindi passare uno user al nuovo document o passo un oggetto di cui è stato fatto
		// il persist, oppure leggo l'oggetto dal DB!

		try {
			User aldoFromDB = ud.findById("9f0918b6-749f-4d6f-9c32-4b0c5d51255b");
			System.out.println("Il documento di Aldo è: " + aldoFromDB.getDocument());
			// Document aldoDoc = new Document("123213213", "Italy", aldoFromDB);
			// dd.save(aldoDoc);

			Blog react = new Blog("React", "React è meglio di Java", aldoFromDB);
			// bd.save(react);
			aldoFromDB.getBlogs().forEach(blog -> System.out.println(blog));

			Blog blogFromDB = bd.findById("08474098-7d4d-42a9-8e66-99fde118d78f");
			System.out.println(blogFromDB.getAuthor());
		} catch (NotFoundException ex) {
			System.out.println(ex.getMessage());
		}


		em.close();
		emf.close();

	}
}
