package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Subsidiecomponent.
 */
@Entity
@Table(name = "subsidiecomponent")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Subsidiecomponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gereserveerdbedrag", precision = 21, scale = 2)
    private BigDecimal gereserveerdbedrag;

    @Column(name = "toegekendbedrag", precision = 21, scale = 2)
    private BigDecimal toegekendbedrag;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSubsidiecomponent")
    @JsonIgnoreProperties(value = { "heeftSubsidiecomponent" }, allowSetters = true)
    private Set<Betaalmoment> heeftBetaalmoments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftVastgoedobjects",
            "heeftWerkorders",
            "heeftSubrekenings",
            "heeftInkooporders",
            "heeftTaakvelds",
            "heeftProgrammas",
            "heeftSubsidies",
            "heeftSubsidiecomponents",
            "betreftBegrotingregels",
            "schrijftopFactuurs",
            "heeftbetrekkingopMutaties",
            "heeftProducts",
            "heeftHoofdrekenings",
            "heeftProjects",
        },
        allowSetters = true
    )
    private Kostenplaats heeftKostenplaats;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Subsidiecomponent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGereserveerdbedrag() {
        return this.gereserveerdbedrag;
    }

    public Subsidiecomponent gereserveerdbedrag(BigDecimal gereserveerdbedrag) {
        this.setGereserveerdbedrag(gereserveerdbedrag);
        return this;
    }

    public void setGereserveerdbedrag(BigDecimal gereserveerdbedrag) {
        this.gereserveerdbedrag = gereserveerdbedrag;
    }

    public BigDecimal getToegekendbedrag() {
        return this.toegekendbedrag;
    }

    public Subsidiecomponent toegekendbedrag(BigDecimal toegekendbedrag) {
        this.setToegekendbedrag(toegekendbedrag);
        return this;
    }

    public void setToegekendbedrag(BigDecimal toegekendbedrag) {
        this.toegekendbedrag = toegekendbedrag;
    }

    public Set<Betaalmoment> getHeeftBetaalmoments() {
        return this.heeftBetaalmoments;
    }

    public void setHeeftBetaalmoments(Set<Betaalmoment> betaalmoments) {
        if (this.heeftBetaalmoments != null) {
            this.heeftBetaalmoments.forEach(i -> i.setHeeftSubsidiecomponent(null));
        }
        if (betaalmoments != null) {
            betaalmoments.forEach(i -> i.setHeeftSubsidiecomponent(this));
        }
        this.heeftBetaalmoments = betaalmoments;
    }

    public Subsidiecomponent heeftBetaalmoments(Set<Betaalmoment> betaalmoments) {
        this.setHeeftBetaalmoments(betaalmoments);
        return this;
    }

    public Subsidiecomponent addHeeftBetaalmoment(Betaalmoment betaalmoment) {
        this.heeftBetaalmoments.add(betaalmoment);
        betaalmoment.setHeeftSubsidiecomponent(this);
        return this;
    }

    public Subsidiecomponent removeHeeftBetaalmoment(Betaalmoment betaalmoment) {
        this.heeftBetaalmoments.remove(betaalmoment);
        betaalmoment.setHeeftSubsidiecomponent(null);
        return this;
    }

    public Kostenplaats getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Subsidiecomponent heeftKostenplaats(Kostenplaats kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subsidiecomponent)) {
            return false;
        }
        return getId() != null && getId().equals(((Subsidiecomponent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Subsidiecomponent{" +
            "id=" + getId() +
            ", gereserveerdbedrag=" + getGereserveerdbedrag() +
            ", toegekendbedrag=" + getToegekendbedrag() +
            "}";
    }
}
