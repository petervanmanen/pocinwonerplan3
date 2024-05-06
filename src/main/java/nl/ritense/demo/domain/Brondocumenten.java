package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Brondocumenten.
 */
@Entity
@Table(name = "brondocumenten")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Brondocumenten implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aktegemeente")
    private String aktegemeente;

    @Column(name = "datumdocument")
    private String datumdocument;

    @Column(name = "documentgemeente")
    private String documentgemeente;

    @Size(max = 20)
    @Column(name = "documentidentificatie", length = 20)
    private String documentidentificatie;

    @Column(name = "documentomschrijving")
    private String documentomschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Brondocumenten id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAktegemeente() {
        return this.aktegemeente;
    }

    public Brondocumenten aktegemeente(String aktegemeente) {
        this.setAktegemeente(aktegemeente);
        return this;
    }

    public void setAktegemeente(String aktegemeente) {
        this.aktegemeente = aktegemeente;
    }

    public String getDatumdocument() {
        return this.datumdocument;
    }

    public Brondocumenten datumdocument(String datumdocument) {
        this.setDatumdocument(datumdocument);
        return this;
    }

    public void setDatumdocument(String datumdocument) {
        this.datumdocument = datumdocument;
    }

    public String getDocumentgemeente() {
        return this.documentgemeente;
    }

    public Brondocumenten documentgemeente(String documentgemeente) {
        this.setDocumentgemeente(documentgemeente);
        return this;
    }

    public void setDocumentgemeente(String documentgemeente) {
        this.documentgemeente = documentgemeente;
    }

    public String getDocumentidentificatie() {
        return this.documentidentificatie;
    }

    public Brondocumenten documentidentificatie(String documentidentificatie) {
        this.setDocumentidentificatie(documentidentificatie);
        return this;
    }

    public void setDocumentidentificatie(String documentidentificatie) {
        this.documentidentificatie = documentidentificatie;
    }

    public String getDocumentomschrijving() {
        return this.documentomschrijving;
    }

    public Brondocumenten documentomschrijving(String documentomschrijving) {
        this.setDocumentomschrijving(documentomschrijving);
        return this;
    }

    public void setDocumentomschrijving(String documentomschrijving) {
        this.documentomschrijving = documentomschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Brondocumenten)) {
            return false;
        }
        return getId() != null && getId().equals(((Brondocumenten) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Brondocumenten{" +
            "id=" + getId() +
            ", aktegemeente='" + getAktegemeente() + "'" +
            ", datumdocument='" + getDatumdocument() + "'" +
            ", documentgemeente='" + getDocumentgemeente() + "'" +
            ", documentidentificatie='" + getDocumentidentificatie() + "'" +
            ", documentomschrijving='" + getDocumentomschrijving() + "'" +
            "}";
    }
}
