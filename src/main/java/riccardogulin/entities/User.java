package riccardogulin.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private UUID userId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String surname;
	// 1 to 1 BIDIREZIONALE
	@OneToOne(mappedBy = "owner")
	// Se inserisco un attributo anche su questo lato con annotazione obbligatoria @OneToOne
	// la relazione diventa bidirezionale. Questo significa che avrò un getter getDocument
	// anche da questo lato. Ciò mi permetterà dopo di poter, una volta letto un utente dal db
	// di poter risalire al suo documento facendo utente.getDocument()
	// mappedBy = "owner" deve puntare al NOME dell'attributo nell'altra classe/entity
	// N.B. La bidirezionalità NON E'OBBLIGATORIA!!!!!!!!!!!
	// N.B. La bidirezionalità NON IMPLICA LA CREAZIONE DI NUOVE COLONNE!!!!
	private Document document;

	// 1 to Many BIDIREZIONALE
	@OneToMany(mappedBy = "author")
	private List<Blog> blogs;

	public User() {
	}

	public User(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public Document getDocument() {
		return document;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				//", document=" + document +
				'}';
	}
}
