package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Redenverkrijgingnationaliteit.
 */
@Entity
@Table(name = "redenverkrijgingnationaliteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Redenverkrijgingnationaliteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumaanvanggeldigheidverkrijging")
    private LocalDate datumaanvanggeldigheidverkrijging;

    @Column(name = "datumeindegeldigheidverkrijging")
    private LocalDate datumeindegeldigheidverkrijging;

    @Column(name = "omschrijvingverkrijging")
    private String omschrijvingverkrijging;

    @Column(name = "redennummerverkrijging")
    private String redennummerverkrijging;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Redenverkrijgingnationaliteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumaanvanggeldigheidverkrijging() {
        return this.datumaanvanggeldigheidverkrijging;
    }

    public Redenverkrijgingnationaliteit datumaanvanggeldigheidverkrijging(LocalDate datumaanvanggeldigheidverkrijging) {
        this.setDatumaanvanggeldigheidverkrijging(datumaanvanggeldigheidverkrijging);
        return this;
    }

    public void setDatumaanvanggeldigheidverkrijging(LocalDate datumaanvanggeldigheidverkrijging) {
        this.datumaanvanggeldigheidverkrijging = datumaanvanggeldigheidverkrijging;
    }

    public LocalDate getDatumeindegeldigheidverkrijging() {
        return this.datumeindegeldigheidverkrijging;
    }

    public Redenverkrijgingnationaliteit datumeindegeldigheidverkrijging(LocalDate datumeindegeldigheidverkrijging) {
        this.setDatumeindegeldigheidverkrijging(datumeindegeldigheidverkrijging);
        return this;
    }

    public void setDatumeindegeldigheidverkrijging(LocalDate datumeindegeldigheidverkrijging) {
        this.datumeindegeldigheidverkrijging = datumeindegeldigheidverkrijging;
    }

    public String getOmschrijvingverkrijging() {
        return this.omschrijvingverkrijging;
    }

    public Redenverkrijgingnationaliteit omschrijvingverkrijging(String omschrijvingverkrijging) {
        this.setOmschrijvingverkrijging(omschrijvingverkrijging);
        return this;
    }

    public void setOmschrijvingverkrijging(String omschrijvingverkrijging) {
        this.omschrijvingverkrijging = omschrijvingverkrijging;
    }

    public String getRedennummerverkrijging() {
        return this.redennummerverkrijging;
    }

    public Redenverkrijgingnationaliteit redennummerverkrijging(String redennummerverkrijging) {
        this.setRedennummerverkrijging(redennummerverkrijging);
        return this;
    }

    public void setRedennummerverkrijging(String redennummerverkrijging) {
        this.redennummerverkrijging = redennummerverkrijging;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Redenverkrijgingnationaliteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Redenverkrijgingnationaliteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Redenverkrijgingnationaliteit{" +
            "id=" + getId() +
            ", datumaanvanggeldigheidverkrijging='" + getDatumaanvanggeldigheidverkrijging() + "'" +
            ", datumeindegeldigheidverkrijging='" + getDatumeindegeldigheidverkrijging() + "'" +
            ", omschrijvingverkrijging='" + getOmschrijvingverkrijging() + "'" +
            ", redennummerverkrijging='" + getRedennummerverkrijging() + "'" +
            "}";
    }
}
