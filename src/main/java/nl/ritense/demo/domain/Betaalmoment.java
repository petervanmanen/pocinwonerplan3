package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Betaalmoment.
 */
@Entity
@Table(name = "betaalmoment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Betaalmoment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag", precision = 21, scale = 2)
    private BigDecimal bedrag;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "voorschot")
    private Boolean voorschot;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "heeftBetaalmoments", "heeftKostenplaats" }, allowSetters = true)
    private Subsidiecomponent heeftSubsidiecomponent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Betaalmoment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBedrag() {
        return this.bedrag;
    }

    public Betaalmoment bedrag(BigDecimal bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(BigDecimal bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Betaalmoment datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Boolean getVoorschot() {
        return this.voorschot;
    }

    public Betaalmoment voorschot(Boolean voorschot) {
        this.setVoorschot(voorschot);
        return this;
    }

    public void setVoorschot(Boolean voorschot) {
        this.voorschot = voorschot;
    }

    public Subsidiecomponent getHeeftSubsidiecomponent() {
        return this.heeftSubsidiecomponent;
    }

    public void setHeeftSubsidiecomponent(Subsidiecomponent subsidiecomponent) {
        this.heeftSubsidiecomponent = subsidiecomponent;
    }

    public Betaalmoment heeftSubsidiecomponent(Subsidiecomponent subsidiecomponent) {
        this.setHeeftSubsidiecomponent(subsidiecomponent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Betaalmoment)) {
            return false;
        }
        return getId() != null && getId().equals(((Betaalmoment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Betaalmoment{" +
            "id=" + getId() +
            ", bedrag=" + getBedrag() +
            ", datum='" + getDatum() + "'" +
            ", voorschot='" + getVoorschot() + "'" +
            "}";
    }
}
