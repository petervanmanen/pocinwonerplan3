package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Geboorteingeschrevennatuurlijkpersoon.
 */
@Entity
@Table(name = "geboorteingeschrevennatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Geboorteingeschrevennatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buitenlandseplaatsgeboorte")
    private String buitenlandseplaatsgeboorte;

    @Column(name = "buitenlandseregiogeboorte")
    private String buitenlandseregiogeboorte;

    @Column(name = "datumgeboorte")
    private String datumgeboorte;

    @Column(name = "gemeentegeboorte")
    private String gemeentegeboorte;

    @Column(name = "landofgebiedgeboorte")
    private String landofgebiedgeboorte;

    @Column(name = "omschrijvinglocatiegeboorte")
    private String omschrijvinglocatiegeboorte;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Geboorteingeschrevennatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuitenlandseplaatsgeboorte() {
        return this.buitenlandseplaatsgeboorte;
    }

    public Geboorteingeschrevennatuurlijkpersoon buitenlandseplaatsgeboorte(String buitenlandseplaatsgeboorte) {
        this.setBuitenlandseplaatsgeboorte(buitenlandseplaatsgeboorte);
        return this;
    }

    public void setBuitenlandseplaatsgeboorte(String buitenlandseplaatsgeboorte) {
        this.buitenlandseplaatsgeboorte = buitenlandseplaatsgeboorte;
    }

    public String getBuitenlandseregiogeboorte() {
        return this.buitenlandseregiogeboorte;
    }

    public Geboorteingeschrevennatuurlijkpersoon buitenlandseregiogeboorte(String buitenlandseregiogeboorte) {
        this.setBuitenlandseregiogeboorte(buitenlandseregiogeboorte);
        return this;
    }

    public void setBuitenlandseregiogeboorte(String buitenlandseregiogeboorte) {
        this.buitenlandseregiogeboorte = buitenlandseregiogeboorte;
    }

    public String getDatumgeboorte() {
        return this.datumgeboorte;
    }

    public Geboorteingeschrevennatuurlijkpersoon datumgeboorte(String datumgeboorte) {
        this.setDatumgeboorte(datumgeboorte);
        return this;
    }

    public void setDatumgeboorte(String datumgeboorte) {
        this.datumgeboorte = datumgeboorte;
    }

    public String getGemeentegeboorte() {
        return this.gemeentegeboorte;
    }

    public Geboorteingeschrevennatuurlijkpersoon gemeentegeboorte(String gemeentegeboorte) {
        this.setGemeentegeboorte(gemeentegeboorte);
        return this;
    }

    public void setGemeentegeboorte(String gemeentegeboorte) {
        this.gemeentegeboorte = gemeentegeboorte;
    }

    public String getLandofgebiedgeboorte() {
        return this.landofgebiedgeboorte;
    }

    public Geboorteingeschrevennatuurlijkpersoon landofgebiedgeboorte(String landofgebiedgeboorte) {
        this.setLandofgebiedgeboorte(landofgebiedgeboorte);
        return this;
    }

    public void setLandofgebiedgeboorte(String landofgebiedgeboorte) {
        this.landofgebiedgeboorte = landofgebiedgeboorte;
    }

    public String getOmschrijvinglocatiegeboorte() {
        return this.omschrijvinglocatiegeboorte;
    }

    public Geboorteingeschrevennatuurlijkpersoon omschrijvinglocatiegeboorte(String omschrijvinglocatiegeboorte) {
        this.setOmschrijvinglocatiegeboorte(omschrijvinglocatiegeboorte);
        return this;
    }

    public void setOmschrijvinglocatiegeboorte(String omschrijvinglocatiegeboorte) {
        this.omschrijvinglocatiegeboorte = omschrijvinglocatiegeboorte;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Geboorteingeschrevennatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Geboorteingeschrevennatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Geboorteingeschrevennatuurlijkpersoon{" +
            "id=" + getId() +
            ", buitenlandseplaatsgeboorte='" + getBuitenlandseplaatsgeboorte() + "'" +
            ", buitenlandseregiogeboorte='" + getBuitenlandseregiogeboorte() + "'" +
            ", datumgeboorte='" + getDatumgeboorte() + "'" +
            ", gemeentegeboorte='" + getGemeentegeboorte() + "'" +
            ", landofgebiedgeboorte='" + getLandofgebiedgeboorte() + "'" +
            ", omschrijvinglocatiegeboorte='" + getOmschrijvinglocatiegeboorte() + "'" +
            "}";
    }
}
