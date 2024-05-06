package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Documenttype.
 */
@Entity
@Table(name = "documenttype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Documenttype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheiddocumenttype")
    private String datumbegingeldigheiddocumenttype;

    @Column(name = "datumeindegeldigheiddocumenttype")
    private String datumeindegeldigheiddocumenttype;

    @Column(name = "documentcategorie")
    private String documentcategorie;

    @Column(name = "documenttypeomschrijving")
    private String documenttypeomschrijving;

    @Column(name = "documenttypeomschrijvinggeneriek")
    private String documenttypeomschrijvinggeneriek;

    @Column(name = "documenttypetrefwoord")
    private String documenttypetrefwoord;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvanDocumenttype")
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
    private Set<Document> isvanDocuments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Documenttype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumbegingeldigheiddocumenttype() {
        return this.datumbegingeldigheiddocumenttype;
    }

    public Documenttype datumbegingeldigheiddocumenttype(String datumbegingeldigheiddocumenttype) {
        this.setDatumbegingeldigheiddocumenttype(datumbegingeldigheiddocumenttype);
        return this;
    }

    public void setDatumbegingeldigheiddocumenttype(String datumbegingeldigheiddocumenttype) {
        this.datumbegingeldigheiddocumenttype = datumbegingeldigheiddocumenttype;
    }

    public String getDatumeindegeldigheiddocumenttype() {
        return this.datumeindegeldigheiddocumenttype;
    }

    public Documenttype datumeindegeldigheiddocumenttype(String datumeindegeldigheiddocumenttype) {
        this.setDatumeindegeldigheiddocumenttype(datumeindegeldigheiddocumenttype);
        return this;
    }

    public void setDatumeindegeldigheiddocumenttype(String datumeindegeldigheiddocumenttype) {
        this.datumeindegeldigheiddocumenttype = datumeindegeldigheiddocumenttype;
    }

    public String getDocumentcategorie() {
        return this.documentcategorie;
    }

    public Documenttype documentcategorie(String documentcategorie) {
        this.setDocumentcategorie(documentcategorie);
        return this;
    }

    public void setDocumentcategorie(String documentcategorie) {
        this.documentcategorie = documentcategorie;
    }

    public String getDocumenttypeomschrijving() {
        return this.documenttypeomschrijving;
    }

    public Documenttype documenttypeomschrijving(String documenttypeomschrijving) {
        this.setDocumenttypeomschrijving(documenttypeomschrijving);
        return this;
    }

    public void setDocumenttypeomschrijving(String documenttypeomschrijving) {
        this.documenttypeomschrijving = documenttypeomschrijving;
    }

    public String getDocumenttypeomschrijvinggeneriek() {
        return this.documenttypeomschrijvinggeneriek;
    }

    public Documenttype documenttypeomschrijvinggeneriek(String documenttypeomschrijvinggeneriek) {
        this.setDocumenttypeomschrijvinggeneriek(documenttypeomschrijvinggeneriek);
        return this;
    }

    public void setDocumenttypeomschrijvinggeneriek(String documenttypeomschrijvinggeneriek) {
        this.documenttypeomschrijvinggeneriek = documenttypeomschrijvinggeneriek;
    }

    public String getDocumenttypetrefwoord() {
        return this.documenttypetrefwoord;
    }

    public Documenttype documenttypetrefwoord(String documenttypetrefwoord) {
        this.setDocumenttypetrefwoord(documenttypetrefwoord);
        return this;
    }

    public void setDocumenttypetrefwoord(String documenttypetrefwoord) {
        this.documenttypetrefwoord = documenttypetrefwoord;
    }

    public Set<Document> getIsvanDocuments() {
        return this.isvanDocuments;
    }

    public void setIsvanDocuments(Set<Document> documents) {
        if (this.isvanDocuments != null) {
            this.isvanDocuments.forEach(i -> i.setIsvanDocumenttype(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setIsvanDocumenttype(this));
        }
        this.isvanDocuments = documents;
    }

    public Documenttype isvanDocuments(Set<Document> documents) {
        this.setIsvanDocuments(documents);
        return this;
    }

    public Documenttype addIsvanDocument(Document document) {
        this.isvanDocuments.add(document);
        document.setIsvanDocumenttype(this);
        return this;
    }

    public Documenttype removeIsvanDocument(Document document) {
        this.isvanDocuments.remove(document);
        document.setIsvanDocumenttype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documenttype)) {
            return false;
        }
        return getId() != null && getId().equals(((Documenttype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documenttype{" +
            "id=" + getId() +
            ", datumbegingeldigheiddocumenttype='" + getDatumbegingeldigheiddocumenttype() + "'" +
            ", datumeindegeldigheiddocumenttype='" + getDatumeindegeldigheiddocumenttype() + "'" +
            ", documentcategorie='" + getDocumentcategorie() + "'" +
            ", documenttypeomschrijving='" + getDocumenttypeomschrijving() + "'" +
            ", documenttypeomschrijvinggeneriek='" + getDocumenttypeomschrijvinggeneriek() + "'" +
            ", documenttypetrefwoord='" + getDocumenttypetrefwoord() + "'" +
            "}";
    }
}
