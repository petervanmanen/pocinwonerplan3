package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Belprovider.
 */
@Entity
@Table(name = "belprovider")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Belprovider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leverancierBelprovider")
    @JsonIgnoreProperties(value = { "leverancierBelprovider", "betreftVoertuig", "resulteertParkeervergunning" }, allowSetters = true)
    private Set<Parkeerrecht> leverancierParkeerrechts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Belprovider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Belprovider code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Parkeerrecht> getLeverancierParkeerrechts() {
        return this.leverancierParkeerrechts;
    }

    public void setLeverancierParkeerrechts(Set<Parkeerrecht> parkeerrechts) {
        if (this.leverancierParkeerrechts != null) {
            this.leverancierParkeerrechts.forEach(i -> i.setLeverancierBelprovider(null));
        }
        if (parkeerrechts != null) {
            parkeerrechts.forEach(i -> i.setLeverancierBelprovider(this));
        }
        this.leverancierParkeerrechts = parkeerrechts;
    }

    public Belprovider leverancierParkeerrechts(Set<Parkeerrecht> parkeerrechts) {
        this.setLeverancierParkeerrechts(parkeerrechts);
        return this;
    }

    public Belprovider addLeverancierParkeerrecht(Parkeerrecht parkeerrecht) {
        this.leverancierParkeerrechts.add(parkeerrecht);
        parkeerrecht.setLeverancierBelprovider(this);
        return this;
    }

    public Belprovider removeLeverancierParkeerrecht(Parkeerrecht parkeerrecht) {
        this.leverancierParkeerrechts.remove(parkeerrecht);
        parkeerrecht.setLeverancierBelprovider(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Belprovider)) {
            return false;
        }
        return getId() != null && getId().equals(((Belprovider) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Belprovider{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
