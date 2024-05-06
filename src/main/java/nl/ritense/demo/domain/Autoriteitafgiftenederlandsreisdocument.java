package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Autoriteitafgiftenederlandsreisdocument.
 */
@Entity
@Table(name = "autoriteitafgiftenederlandsreisdocument")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Autoriteitafgiftenederlandsreisdocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "datumbegingeldigheidautoriteitvanafgifte")
    private LocalDate datumbegingeldigheidautoriteitvanafgifte;

    @Column(name = "datumeindegeldigheidautoriteitvanafgifte")
    private LocalDate datumeindegeldigheidautoriteitvanafgifte;

    @Column(name = "omschrijving")
    private String omschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Autoriteitafgiftenederlandsreisdocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Autoriteitafgiftenederlandsreisdocument code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDatumbegingeldigheidautoriteitvanafgifte() {
        return this.datumbegingeldigheidautoriteitvanafgifte;
    }

    public Autoriteitafgiftenederlandsreisdocument datumbegingeldigheidautoriteitvanafgifte(
        LocalDate datumbegingeldigheidautoriteitvanafgifte
    ) {
        this.setDatumbegingeldigheidautoriteitvanafgifte(datumbegingeldigheidautoriteitvanafgifte);
        return this;
    }

    public void setDatumbegingeldigheidautoriteitvanafgifte(LocalDate datumbegingeldigheidautoriteitvanafgifte) {
        this.datumbegingeldigheidautoriteitvanafgifte = datumbegingeldigheidautoriteitvanafgifte;
    }

    public LocalDate getDatumeindegeldigheidautoriteitvanafgifte() {
        return this.datumeindegeldigheidautoriteitvanafgifte;
    }

    public Autoriteitafgiftenederlandsreisdocument datumeindegeldigheidautoriteitvanafgifte(
        LocalDate datumeindegeldigheidautoriteitvanafgifte
    ) {
        this.setDatumeindegeldigheidautoriteitvanafgifte(datumeindegeldigheidautoriteitvanafgifte);
        return this;
    }

    public void setDatumeindegeldigheidautoriteitvanafgifte(LocalDate datumeindegeldigheidautoriteitvanafgifte) {
        this.datumeindegeldigheidautoriteitvanafgifte = datumeindegeldigheidautoriteitvanafgifte;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Autoriteitafgiftenederlandsreisdocument omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autoriteitafgiftenederlandsreisdocument)) {
            return false;
        }
        return getId() != null && getId().equals(((Autoriteitafgiftenederlandsreisdocument) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autoriteitafgiftenederlandsreisdocument{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", datumbegingeldigheidautoriteitvanafgifte='" + getDatumbegingeldigheidautoriteitvanafgifte() + "'" +
            ", datumeindegeldigheidautoriteitvanafgifte='" + getDatumeindegeldigheidautoriteitvanafgifte() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
