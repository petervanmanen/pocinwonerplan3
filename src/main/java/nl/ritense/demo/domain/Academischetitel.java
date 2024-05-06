package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Academischetitel.
 */
@Entity
@Table(name = "academischetitel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Academischetitel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codeacademischetitel")
    private String codeacademischetitel;

    @Column(name = "datumbegingeldigheidtitel")
    private String datumbegingeldigheidtitel;

    @Column(name = "datumeindegeldigheidtitel")
    private String datumeindegeldigheidtitel;

    @Column(name = "omschrijvingacademischetitel")
    private String omschrijvingacademischetitel;

    @Column(name = "positieacademischetiteltovnaam")
    private String positieacademischetiteltovnaam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Academischetitel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeacademischetitel() {
        return this.codeacademischetitel;
    }

    public Academischetitel codeacademischetitel(String codeacademischetitel) {
        this.setCodeacademischetitel(codeacademischetitel);
        return this;
    }

    public void setCodeacademischetitel(String codeacademischetitel) {
        this.codeacademischetitel = codeacademischetitel;
    }

    public String getDatumbegingeldigheidtitel() {
        return this.datumbegingeldigheidtitel;
    }

    public Academischetitel datumbegingeldigheidtitel(String datumbegingeldigheidtitel) {
        this.setDatumbegingeldigheidtitel(datumbegingeldigheidtitel);
        return this;
    }

    public void setDatumbegingeldigheidtitel(String datumbegingeldigheidtitel) {
        this.datumbegingeldigheidtitel = datumbegingeldigheidtitel;
    }

    public String getDatumeindegeldigheidtitel() {
        return this.datumeindegeldigheidtitel;
    }

    public Academischetitel datumeindegeldigheidtitel(String datumeindegeldigheidtitel) {
        this.setDatumeindegeldigheidtitel(datumeindegeldigheidtitel);
        return this;
    }

    public void setDatumeindegeldigheidtitel(String datumeindegeldigheidtitel) {
        this.datumeindegeldigheidtitel = datumeindegeldigheidtitel;
    }

    public String getOmschrijvingacademischetitel() {
        return this.omschrijvingacademischetitel;
    }

    public Academischetitel omschrijvingacademischetitel(String omschrijvingacademischetitel) {
        this.setOmschrijvingacademischetitel(omschrijvingacademischetitel);
        return this;
    }

    public void setOmschrijvingacademischetitel(String omschrijvingacademischetitel) {
        this.omschrijvingacademischetitel = omschrijvingacademischetitel;
    }

    public String getPositieacademischetiteltovnaam() {
        return this.positieacademischetiteltovnaam;
    }

    public Academischetitel positieacademischetiteltovnaam(String positieacademischetiteltovnaam) {
        this.setPositieacademischetiteltovnaam(positieacademischetiteltovnaam);
        return this;
    }

    public void setPositieacademischetiteltovnaam(String positieacademischetiteltovnaam) {
        this.positieacademischetiteltovnaam = positieacademischetiteltovnaam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Academischetitel)) {
            return false;
        }
        return getId() != null && getId().equals(((Academischetitel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Academischetitel{" +
            "id=" + getId() +
            ", codeacademischetitel='" + getCodeacademischetitel() + "'" +
            ", datumbegingeldigheidtitel='" + getDatumbegingeldigheidtitel() + "'" +
            ", datumeindegeldigheidtitel='" + getDatumeindegeldigheidtitel() + "'" +
            ", omschrijvingacademischetitel='" + getOmschrijvingacademischetitel() + "'" +
            ", positieacademischetiteltovnaam='" + getPositieacademischetiteltovnaam() + "'" +
            "}";
    }
}
