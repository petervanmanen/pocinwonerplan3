package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bankafschrift.
 */
@Entity
@Table(name = "bankafschrift")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bankafschrift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private String datum;

    @Column(name = "nummer")
    private String nummer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftBankafschrift")
    @JsonIgnoreProperties(value = { "leidttotMutatie", "heeftBankafschrift", "komtvooropBetalings" }, allowSetters = true)
    private Set<Bankafschriftregel> heeftBankafschriftregels = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftBankafschrifts", "vanBetalings", "naarBetalings" }, allowSetters = true)
    private Bankrekening heeftBankrekening;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bankafschrift id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatum() {
        return this.datum;
    }

    public Bankafschrift datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Bankafschrift nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public Set<Bankafschriftregel> getHeeftBankafschriftregels() {
        return this.heeftBankafschriftregels;
    }

    public void setHeeftBankafschriftregels(Set<Bankafschriftregel> bankafschriftregels) {
        if (this.heeftBankafschriftregels != null) {
            this.heeftBankafschriftregels.forEach(i -> i.setHeeftBankafschrift(null));
        }
        if (bankafschriftregels != null) {
            bankafschriftregels.forEach(i -> i.setHeeftBankafschrift(this));
        }
        this.heeftBankafschriftregels = bankafschriftregels;
    }

    public Bankafschrift heeftBankafschriftregels(Set<Bankafschriftregel> bankafschriftregels) {
        this.setHeeftBankafschriftregels(bankafschriftregels);
        return this;
    }

    public Bankafschrift addHeeftBankafschriftregel(Bankafschriftregel bankafschriftregel) {
        this.heeftBankafschriftregels.add(bankafschriftregel);
        bankafschriftregel.setHeeftBankafschrift(this);
        return this;
    }

    public Bankafschrift removeHeeftBankafschriftregel(Bankafschriftregel bankafschriftregel) {
        this.heeftBankafschriftregels.remove(bankafschriftregel);
        bankafschriftregel.setHeeftBankafschrift(null);
        return this;
    }

    public Bankrekening getHeeftBankrekening() {
        return this.heeftBankrekening;
    }

    public void setHeeftBankrekening(Bankrekening bankrekening) {
        this.heeftBankrekening = bankrekening;
    }

    public Bankafschrift heeftBankrekening(Bankrekening bankrekening) {
        this.setHeeftBankrekening(bankrekening);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bankafschrift)) {
            return false;
        }
        return getId() != null && getId().equals(((Bankafschrift) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bankafschrift{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", nummer='" + getNummer() + "'" +
            "}";
    }
}
