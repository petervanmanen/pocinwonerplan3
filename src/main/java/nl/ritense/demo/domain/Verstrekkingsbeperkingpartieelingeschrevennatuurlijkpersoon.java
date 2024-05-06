package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.
 */
@Entity
@Table(name = "verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gemeenteverordening")
    private String gemeenteverordening;

    @Column(name = "omschrijvingderde")
    private String omschrijvingderde;

    @Column(name = "partij")
    private String partij;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGemeenteverordening() {
        return this.gemeenteverordening;
    }

    public Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon gemeenteverordening(String gemeenteverordening) {
        this.setGemeenteverordening(gemeenteverordening);
        return this;
    }

    public void setGemeenteverordening(String gemeenteverordening) {
        this.gemeenteverordening = gemeenteverordening;
    }

    public String getOmschrijvingderde() {
        return this.omschrijvingderde;
    }

    public Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon omschrijvingderde(String omschrijvingderde) {
        this.setOmschrijvingderde(omschrijvingderde);
        return this;
    }

    public void setOmschrijvingderde(String omschrijvingderde) {
        this.omschrijvingderde = omschrijvingderde;
    }

    public String getPartij() {
        return this.partij;
    }

    public Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon partij(String partij) {
        this.setPartij(partij);
        return this;
    }

    public void setPartij(String partij) {
        this.partij = partij;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon{" +
            "id=" + getId() +
            ", gemeenteverordening='" + getGemeenteverordening() + "'" +
            ", omschrijvingderde='" + getOmschrijvingderde() + "'" +
            ", partij='" + getPartij() + "'" +
            "}";
    }
}
