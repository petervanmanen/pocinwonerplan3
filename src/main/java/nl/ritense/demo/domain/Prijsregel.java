package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Prijsregel.
 */
@Entity
@Table(name = "prijsregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prijsregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "credit")
    private String credit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftPrijsregels" }, allowSetters = true)
    private Prijsafspraak heeftPrijsafspraak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prijsregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Prijsregel bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public String getCredit() {
        return this.credit;
    }

    public Prijsregel credit(String credit) {
        this.setCredit(credit);
        return this;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public Prijsafspraak getHeeftPrijsafspraak() {
        return this.heeftPrijsafspraak;
    }

    public void setHeeftPrijsafspraak(Prijsafspraak prijsafspraak) {
        this.heeftPrijsafspraak = prijsafspraak;
    }

    public Prijsregel heeftPrijsafspraak(Prijsafspraak prijsafspraak) {
        this.setHeeftPrijsafspraak(prijsafspraak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prijsregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Prijsregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prijsregel{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", credit='" + getCredit() + "'" +
            "}";
    }
}
