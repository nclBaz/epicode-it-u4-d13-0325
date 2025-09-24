package riccardogulin.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "documents")
public class Document {
	@Id
	@GeneratedValue
	@Column(name = "document_id")
	private UUID documentId;
	@Column(nullable = false)
	private String code;
	@Column(nullable = false)
	private String country;
	@Column(nullable = false)
	private LocalDate issueDate;
	@Column(nullable = false)
	private LocalDate expirationDate;

	@OneToOne
	// @OneToOne è obbligatoria se dichiaro un attributo di un tipo che rappresenta un'entità
	// User è un'entità quindi se non metto @OneToOne otterrò un'eccezione
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	// JoinColumn serve per personalizzare la FK
	private User owner;

	public Document() {
	}

	public Document(String code, String country, User owner) {
		this.code = code;
		this.country = country;
		this.issueDate = LocalDate.now();
		this.expirationDate = LocalDate.now().plusYears(10);
		this.owner = owner;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public String getCode() {
		return code;
	}


	public String getCountry() {
		return country;
	}


	public LocalDate getIssueDate() {
		return issueDate;
	}


	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public User getOwner() {
		return owner;
	}

	@Override
	public String toString() {
		return "Document{" +
				"documentId=" + documentId +
				", code='" + code + '\'' +
				", country='" + country + '\'' +
				", issueDate=" + issueDate +
				", expirationDate=" + expirationDate +
				", owner=" + owner +
				'}';
	}
}
