package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Reisdocumentsoort.
 */
@Entity
@Table(name = "reisdocumentsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reisdocumentsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidreisdocumentsoort")
    private LocalDate datumbegingeldigheidreisdocumentsoort;

    @Column(name = "datumeindegeldigheidreisdocumentsoort")
    private LocalDate datumeindegeldigheidreisdocumentsoort;

    @Column(name = "reisdocumentcode")
    private String reisdocumentcode;

    @Column(name = "reisdocumentomschrijving")
    private String reisdocumentomschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reisdocumentsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidreisdocumentsoort() {
        return this.datumbegingeldigheidreisdocumentsoort;
    }

    public Reisdocumentsoort datumbegingeldigheidreisdocumentsoort(LocalDate datumbegingeldigheidreisdocumentsoort) {
        this.setDatumbegingeldigheidreisdocumentsoort(datumbegingeldigheidreisdocumentsoort);
        return this;
    }

    public void setDatumbegingeldigheidreisdocumentsoort(LocalDate datumbegingeldigheidreisdocumentsoort) {
        this.datumbegingeldigheidreisdocumentsoort = datumbegingeldigheidreisdocumentsoort;
    }

    public LocalDate getDatumeindegeldigheidreisdocumentsoort() {
        return this.datumeindegeldigheidreisdocumentsoort;
    }

    public Reisdocumentsoort datumeindegeldigheidreisdocumentsoort(LocalDate datumeindegeldigheidreisdocumentsoort) {
        this.setDatumeindegeldigheidreisdocumentsoort(datumeindegeldigheidreisdocumentsoort);
        return this;
    }

    public void setDatumeindegeldigheidreisdocumentsoort(LocalDate datumeindegeldigheidreisdocumentsoort) {
        this.datumeindegeldigheidreisdocumentsoort = datumeindegeldigheidreisdocumentsoort;
    }

    public String getReisdocumentcode() {
        return this.reisdocumentcode;
    }

    public Reisdocumentsoort reisdocumentcode(String reisdocumentcode) {
        this.setReisdocumentcode(reisdocumentcode);
        return this;
    }

    public void setReisdocumentcode(String reisdocumentcode) {
        this.reisdocumentcode = reisdocumentcode;
    }

    public String getReisdocumentomschrijving() {
        return this.reisdocumentomschrijving;
    }

    public Reisdocumentsoort reisdocumentomschrijving(String reisdocumentomschrijving) {
        this.setReisdocumentomschrijving(reisdocumentomschrijving);
        return this;
    }

    public void setReisdocumentomschrijving(String reisdocumentomschrijving) {
        this.reisdocumentomschrijving = reisdocumentomschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reisdocumentsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Reisdocumentsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reisdocumentsoort{" +
            "id=" + getId() +
            ", datumbegingeldigheidreisdocumentsoort='" + getDatumbegingeldigheidreisdocumentsoort() + "'" +
            ", datumeindegeldigheidreisdocumentsoort='" + getDatumeindegeldigheidreisdocumentsoort() + "'" +
            ", reisdocumentcode='" + getReisdocumentcode() + "'" +
            ", reisdocumentomschrijving='" + getReisdocumentomschrijving() + "'" +
            "}";
    }
}
