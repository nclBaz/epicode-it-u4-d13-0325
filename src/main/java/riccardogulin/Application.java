package riccardogulin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import riccardogulin.dao.BlogsDAO;
import riccardogulin.dao.CategoriesDAO;
import riccardogulin.dao.DocumentsDAO;
import riccardogulin.dao.UsersDAO;
import riccardogulin.entities.Blog;
import riccardogulin.entities.Category;
import riccardogulin.entities.User;
import riccardogulin.exceptions.NotFoundException;

import java.util.ArrayList;

public class Application {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("u4d13pu");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();

		UsersDAO ud = new UsersDAO(em);
		DocumentsDAO dd = new DocumentsDAO(em);
		BlogsDAO bd = new BlogsDAO(em);
		CategoriesDAO cd = new CategoriesDAO(em);

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

		// ******************************** MANY TO MANY ********************************
		Category category = new Category("Backend");
		Category category1 = new Category("Frontend");
		Category category2 = new Category("OOP");
		Category category3 = new Category("Databases");

//		cd.save(category);
//		cd.save(category1);
//		cd.save(category2);
//		cd.save(category3);

		// Per assegnare ad un blog tot categorie dobbiamo:
		// 0. Leggo da db il blog da modificare
		Blog javaBlogFromDB = bd.findById("9d82ab1e-dce5-495c-87b6-1501408c9762");

		// 1. Leggo le categorie che mi interessano dal database
		Category backendCatFromDB = cd.findById("d05646a4-a174-48e0-9c5d-c4e415f47095");
		Category oopCatFromDB = cd.findById("62df16a8-0a7f-4304-8e57-e0eb52f33d38");

		// 2. Con esse creo una List
		ArrayList<Category> categories = new ArrayList<>();
		categories.add(backendCatFromDB);
		categories.add(oopCatFromDB);

		// 3. Tramite setCategories gliele passo al blog di mio interesse
		javaBlogFromDB.setCategories(categories);

		// 4. Risalvo il blog
		// bd.save(javaBlogFromDB);
		// Fare una save su un oggetto proveniente dal db non vuol dire crearne uno nuovo, bensì fare un UPDATE di
		// quello pre-esistente

		System.out.println("Tutte le categorie del blog su Java sono: ");
		javaBlogFromDB.getCategories().forEach(cat -> System.out.println(cat));

		System.out.println("Tutti i blog associati alla categoria backend sono: ");
		backendCatFromDB.getBlogs().forEach(blog -> System.out.println(blog));

		em.close();
		emf.close();

	}
}
