package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Overlijdeningeschrevennatuurlijkpersoon.
 */
@Entity
@Table(name = "overlijdeningeschrevennatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overlijdeningeschrevennatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "buitenlandseplaatsoverlijden")
    private String buitenlandseplaatsoverlijden;

    @Column(name = "buitenlandseregiooverlijden")
    private String buitenlandseregiooverlijden;

    @Column(name = "datumoverlijden")
    private String datumoverlijden;

    @Column(name = "gemeenteoverlijden")
    private String gemeenteoverlijden;

    @Column(name = "landofgebiedoverlijden")
    private String landofgebiedoverlijden;

    @Column(name = "omschrijvinglocatieoverlijden")
    private String omschrijvinglocatieoverlijden;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overlijdeningeschrevennatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuitenlandseplaatsoverlijden() {
        return this.buitenlandseplaatsoverlijden;
    }

    public Overlijdeningeschrevennatuurlijkpersoon buitenlandseplaatsoverlijden(String buitenlandseplaatsoverlijden) {
        this.setBuitenlandseplaatsoverlijden(buitenlandseplaatsoverlijden);
        return this;
    }

    public void setBuitenlandseplaatsoverlijden(String buitenlandseplaatsoverlijden) {
        this.buitenlandseplaatsoverlijden = buitenlandseplaatsoverlijden;
    }

    public String getBuitenlandseregiooverlijden() {
        return this.buitenlandseregiooverlijden;
    }

    public Overlijdeningeschrevennatuurlijkpersoon buitenlandseregiooverlijden(String buitenlandseregiooverlijden) {
        this.setBuitenlandseregiooverlijden(buitenlandseregiooverlijden);
        return this;
    }

    public void setBuitenlandseregiooverlijden(String buitenlandseregiooverlijden) {
        this.buitenlandseregiooverlijden = buitenlandseregiooverlijden;
    }

    public String getDatumoverlijden() {
        return this.datumoverlijden;
    }

    public Overlijdeningeschrevennatuurlijkpersoon datumoverlijden(String datumoverlijden) {
        this.setDatumoverlijden(datumoverlijden);
        return this;
    }

    public void setDatumoverlijden(String datumoverlijden) {
        this.datumoverlijden = datumoverlijden;
    }

    public String getGemeenteoverlijden() {
        return this.gemeenteoverlijden;
    }

    public Overlijdeningeschrevennatuurlijkpersoon gemeenteoverlijden(String gemeenteoverlijden) {
        this.setGemeenteoverlijden(gemeenteoverlijden);
        return this;
    }

    public void setGemeenteoverlijden(String gemeenteoverlijden) {
        this.gemeenteoverlijden = gemeenteoverlijden;
    }

    public String getLandofgebiedoverlijden() {
        return this.landofgebiedoverlijden;
    }

    public Overlijdeningeschrevennatuurlijkpersoon landofgebiedoverlijden(String landofgebiedoverlijden) {
        this.setLandofgebiedoverlijden(landofgebiedoverlijden);
        return this;
    }

    public void setLandofgebiedoverlijden(String landofgebiedoverlijden) {
        this.landofgebiedoverlijden = landofgebiedoverlijden;
    }

    public String getOmschrijvinglocatieoverlijden() {
        return this.omschrijvinglocatieoverlijden;
    }

    public Overlijdeningeschrevennatuurlijkpersoon omschrijvinglocatieoverlijden(String omschrijvinglocatieoverlijden) {
        this.setOmschrijvinglocatieoverlijden(omschrijvinglocatieoverlijden);
        return this;
    }

    public void setOmschrijvinglocatieoverlijden(String omschrijvinglocatieoverlijden) {
        this.omschrijvinglocatieoverlijden = omschrijvinglocatieoverlijden;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overlijdeningeschrevennatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Overlijdeningeschrevennatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overlijdeningeschrevennatuurlijkpersoon{" +
            "id=" + getId() +
            ", buitenlandseplaatsoverlijden='" + getBuitenlandseplaatsoverlijden() + "'" +
            ", buitenlandseregiooverlijden='" + getBuitenlandseregiooverlijden() + "'" +
            ", datumoverlijden='" + getDatumoverlijden() + "'" +
            ", gemeenteoverlijden='" + getGemeenteoverlijden() + "'" +
            ", landofgebiedoverlijden='" + getLandofgebiedoverlijden() + "'" +
            ", omschrijvinglocatieoverlijden='" + getOmschrijvinglocatieoverlijden() + "'" +
            "}";
    }
}
