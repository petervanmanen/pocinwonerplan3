package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bankrekening.
 */
@Entity
@Table(name = "bankrekening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bankrekening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bank")
    private String bank;

    @Column(name = "nummer")
    private String nummer;

    @Column(name = "tennaamstelling")
    private String tennaamstelling;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftBankrekening")
    @JsonIgnoreProperties(value = { "heeftBankafschriftregels", "heeftBankrekening" }, allowSetters = true)
    private Set<Bankafschrift> heeftBankafschrifts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vanBankrekening")
    @JsonIgnoreProperties(
        value = { "komtvooropBankafschriftregel", "vanBankrekening", "naarBankrekening", "heeftbetalingZaak" },
        allowSetters = true
    )
    private Set<Betaling> vanBetalings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "naarBankrekening")
    @JsonIgnoreProperties(
        value = { "komtvooropBankafschriftregel", "vanBankrekening", "naarBankrekening", "heeftbetalingZaak" },
        allowSetters = true
    )
    private Set<Betaling> naarBetalings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bankrekening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBank() {
        return this.bank;
    }

    public Bankrekening bank(String bank) {
        this.setBank(bank);
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNummer() {
        return this.nummer;
    }

    public Bankrekening nummer(String nummer) {
        this.setNummer(nummer);
        return this;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }

    public String getTennaamstelling() {
        return this.tennaamstelling;
    }

    public Bankrekening tennaamstelling(String tennaamstelling) {
        this.setTennaamstelling(tennaamstelling);
        return this;
    }

    public void setTennaamstelling(String tennaamstelling) {
        this.tennaamstelling = tennaamstelling;
    }

    public Set<Bankafschrift> getHeeftBankafschrifts() {
        return this.heeftBankafschrifts;
    }

    public void setHeeftBankafschrifts(Set<Bankafschrift> bankafschrifts) {
        if (this.heeftBankafschrifts != null) {
            this.heeftBankafschrifts.forEach(i -> i.setHeeftBankrekening(null));
        }
        if (bankafschrifts != null) {
            bankafschrifts.forEach(i -> i.setHeeftBankrekening(this));
        }
        this.heeftBankafschrifts = bankafschrifts;
    }

    public Bankrekening heeftBankafschrifts(Set<Bankafschrift> bankafschrifts) {
        this.setHeeftBankafschrifts(bankafschrifts);
        return this;
    }

    public Bankrekening addHeeftBankafschrift(Bankafschrift bankafschrift) {
        this.heeftBankafschrifts.add(bankafschrift);
        bankafschrift.setHeeftBankrekening(this);
        return this;
    }

    public Bankrekening removeHeeftBankafschrift(Bankafschrift bankafschrift) {
        this.heeftBankafschrifts.remove(bankafschrift);
        bankafschrift.setHeeftBankrekening(null);
        return this;
    }

    public Set<Betaling> getVanBetalings() {
        return this.vanBetalings;
    }

    public void setVanBetalings(Set<Betaling> betalings) {
        if (this.vanBetalings != null) {
            this.vanBetalings.forEach(i -> i.setVanBankrekening(null));
        }
        if (betalings != null) {
            betalings.forEach(i -> i.setVanBankrekening(this));
        }
        this.vanBetalings = betalings;
    }

    public Bankrekening vanBetalings(Set<Betaling> betalings) {
        this.setVanBetalings(betalings);
        return this;
    }

    public Bankrekening addVanBetaling(Betaling betaling) {
        this.vanBetalings.add(betaling);
        betaling.setVanBankrekening(this);
        return this;
    }

    public Bankrekening removeVanBetaling(Betaling betaling) {
        this.vanBetalings.remove(betaling);
        betaling.setVanBankrekening(null);
        return this;
    }

    public Set<Betaling> getNaarBetalings() {
        return this.naarBetalings;
    }

    public void setNaarBetalings(Set<Betaling> betalings) {
        if (this.naarBetalings != null) {
            this.naarBetalings.forEach(i -> i.setNaarBankrekening(null));
        }
        if (betalings != null) {
            betalings.forEach(i -> i.setNaarBankrekening(this));
        }
        this.naarBetalings = betalings;
    }

    public Bankrekening naarBetalings(Set<Betaling> betalings) {
        this.setNaarBetalings(betalings);
        return this;
    }

    public Bankrekening addNaarBetaling(Betaling betaling) {
        this.naarBetalings.add(betaling);
        betaling.setNaarBankrekening(this);
        return this;
    }

    public Bankrekening removeNaarBetaling(Betaling betaling) {
        this.naarBetalings.remove(betaling);
        betaling.setNaarBankrekening(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bankrekening)) {
            return false;
        }
        return getId() != null && getId().equals(((Bankrekening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bankrekening{" +
            "id=" + getId() +
            ", bank='" + getBank() + "'" +
            ", nummer='" + getNummer() + "'" +
            ", tennaamstelling='" + getTennaamstelling() + "'" +
            "}";
    }
}
