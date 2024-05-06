package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Identificatiekenmerk.
 */
@Entity
@Table(name = "identificatiekenmerk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Identificatiekenmerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "kenmerk", length = 20)
    private String kenmerk;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftkenmerkIdentificatiekenmerk")
    @JsonIgnoreProperties(
        value = {
            "heeftkenmerkIdentificatiekenmerk",
            "isvanDocumenttype",
            "isvastgelegdinVerkeersbesluit",
            "isvastgelegdinBesluit",
            "inspectierapportBinnenlocatie",
            "heeftRapportagemoment",
            "heeftSubsidies",
            "heeftdocumentenApplicaties",
            "kanvastgelegdzijnalsBesluits",
            "kentZaaks",
        },
        allowSetters = true
    )
    private Set<Document> heeftkenmerkDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Identificatiekenmerk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKenmerk() {
        return this.kenmerk;
    }

    public Identificatiekenmerk kenmerk(String kenmerk) {
        this.setKenmerk(kenmerk);
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public Set<Document> getHeeftkenmerkDocuments() {
        return this.heeftkenmerkDocuments;
    }

    public void setHeeftkenmerkDocuments(Set<Document> documents) {
        if (this.heeftkenmerkDocuments != null) {
            this.heeftkenmerkDocuments.forEach(i -> i.setHeeftkenmerkIdentificatiekenmerk(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setHeeftkenmerkIdentificatiekenmerk(this));
        }
        this.heeftkenmerkDocuments = documents;
    }

    public Identificatiekenmerk heeftkenmerkDocuments(Set<Document> documents) {
        this.setHeeftkenmerkDocuments(documents);
        return this;
    }

    public Identificatiekenmerk addHeeftkenmerkDocument(Document document) {
        this.heeftkenmerkDocuments.add(document);
        document.setHeeftkenmerkIdentificatiekenmerk(this);
        return this;
    }

    public Identificatiekenmerk removeHeeftkenmerkDocument(Document document) {
        this.heeftkenmerkDocuments.remove(document);
        document.setHeeftkenmerkIdentificatiekenmerk(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Identificatiekenmerk)) {
            return false;
        }
        return getId() != null && getId().equals(((Identificatiekenmerk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Identificatiekenmerk{" +
            "id=" + getId() +
            ", kenmerk='" + getKenmerk() + "'" +
            "}";
    }
}
