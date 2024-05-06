package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Adresaanduiding.
 */
@Entity
@Table(name = "adresaanduiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Adresaanduiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresaanduiding")
    private String adresaanduiding;

    @JsonIgnoreProperties(
        value = {
            "ligtinWoonplaats",
            "ligtinBuurt",
            "ligtinGebieds",
            "verwijstnaarAdresaanduiding",
            "emptyBriefadres",
            "heeftalslocatieadresVestigings",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Nummeraanduiding verwijstnaarNummeraanduiding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Adresaanduiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresaanduiding() {
        return this.adresaanduiding;
    }

    public Adresaanduiding adresaanduiding(String adresaanduiding) {
        this.setAdresaanduiding(adresaanduiding);
        return this;
    }

    public void setAdresaanduiding(String adresaanduiding) {
        this.adresaanduiding = adresaanduiding;
    }

    public Nummeraanduiding getVerwijstnaarNummeraanduiding() {
        return this.verwijstnaarNummeraanduiding;
    }

    public void setVerwijstnaarNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.verwijstnaarNummeraanduiding = nummeraanduiding;
    }

    public Adresaanduiding verwijstnaarNummeraanduiding(Nummeraanduiding nummeraanduiding) {
        this.setVerwijstnaarNummeraanduiding(nummeraanduiding);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Adresaanduiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Adresaanduiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adresaanduiding{" +
            "id=" + getId() +
            ", adresaanduiding='" + getAdresaanduiding() + "'" +
            "}";
    }
}
