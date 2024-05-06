package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "contactsoort")
    private String contactsoort;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "tekst")
    private String tekst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftWerkgelegenheid", "heeftalslocatieadresNummeraanduiding", "bijContacts" }, allowSetters = true)
    private Vestiging bijVestiging;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Contact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactsoort() {
        return this.contactsoort;
    }

    public Contact contactsoort(String contactsoort) {
        this.setContactsoort(contactsoort);
        return this;
    }

    public void setContactsoort(String contactsoort) {
        this.contactsoort = contactsoort;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Contact datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getTekst() {
        return this.tekst;
    }

    public Contact tekst(String tekst) {
        this.setTekst(tekst);
        return this;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Vestiging getBijVestiging() {
        return this.bijVestiging;
    }

    public void setBijVestiging(Vestiging vestiging) {
        this.bijVestiging = vestiging;
    }

    public Contact bijVestiging(Vestiging vestiging) {
        this.setBijVestiging(vestiging);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }
        return getId() != null && getId().equals(((Contact) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", contactsoort='" + getContactsoort() + "'" +
            ", datum='" + getDatum() + "'" +
            ", tekst='" + getTekst() + "'" +
            "}";
    }
}
