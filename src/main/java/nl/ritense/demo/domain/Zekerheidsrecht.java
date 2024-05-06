package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Zekerheidsrecht.
 */
@Entity
@Table(name = "zekerheidsrecht")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zekerheidsrecht implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aandeelinbetrokkenrecht")
    private String aandeelinbetrokkenrecht;

    @Column(name = "datumeinderecht")
    private LocalDate datumeinderecht;

    @Column(name = "datumingangrecht")
    private LocalDate datumingangrecht;

    @Column(name = "identificatiezekerheidsrecht")
    private String identificatiezekerheidsrecht;

    @Column(name = "omschrijvingbetrokkenrecht")
    private String omschrijvingbetrokkenrecht;

    @Column(name = "typezekerheidsrecht")
    private String typezekerheidsrecht;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "emptyAantekenings", "heeftRechtspersoon", "bezwaartZekerheidsrechts" }, allowSetters = true)
    private Tenaamstelling bezwaartTenaamstelling;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zekerheidsrecht id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAandeelinbetrokkenrecht() {
        return this.aandeelinbetrokkenrecht;
    }

    public Zekerheidsrecht aandeelinbetrokkenrecht(String aandeelinbetrokkenrecht) {
        this.setAandeelinbetrokkenrecht(aandeelinbetrokkenrecht);
        return this;
    }

    public void setAandeelinbetrokkenrecht(String aandeelinbetrokkenrecht) {
        this.aandeelinbetrokkenrecht = aandeelinbetrokkenrecht;
    }

    public LocalDate getDatumeinderecht() {
        return this.datumeinderecht;
    }

    public Zekerheidsrecht datumeinderecht(LocalDate datumeinderecht) {
        this.setDatumeinderecht(datumeinderecht);
        return this;
    }

    public void setDatumeinderecht(LocalDate datumeinderecht) {
        this.datumeinderecht = datumeinderecht;
    }

    public LocalDate getDatumingangrecht() {
        return this.datumingangrecht;
    }

    public Zekerheidsrecht datumingangrecht(LocalDate datumingangrecht) {
        this.setDatumingangrecht(datumingangrecht);
        return this;
    }

    public void setDatumingangrecht(LocalDate datumingangrecht) {
        this.datumingangrecht = datumingangrecht;
    }

    public String getIdentificatiezekerheidsrecht() {
        return this.identificatiezekerheidsrecht;
    }

    public Zekerheidsrecht identificatiezekerheidsrecht(String identificatiezekerheidsrecht) {
        this.setIdentificatiezekerheidsrecht(identificatiezekerheidsrecht);
        return this;
    }

    public void setIdentificatiezekerheidsrecht(String identificatiezekerheidsrecht) {
        this.identificatiezekerheidsrecht = identificatiezekerheidsrecht;
    }

    public String getOmschrijvingbetrokkenrecht() {
        return this.omschrijvingbetrokkenrecht;
    }

    public Zekerheidsrecht omschrijvingbetrokkenrecht(String omschrijvingbetrokkenrecht) {
        this.setOmschrijvingbetrokkenrecht(omschrijvingbetrokkenrecht);
        return this;
    }

    public void setOmschrijvingbetrokkenrecht(String omschrijvingbetrokkenrecht) {
        this.omschrijvingbetrokkenrecht = omschrijvingbetrokkenrecht;
    }

    public String getTypezekerheidsrecht() {
        return this.typezekerheidsrecht;
    }

    public Zekerheidsrecht typezekerheidsrecht(String typezekerheidsrecht) {
        this.setTypezekerheidsrecht(typezekerheidsrecht);
        return this;
    }

    public void setTypezekerheidsrecht(String typezekerheidsrecht) {
        this.typezekerheidsrecht = typezekerheidsrecht;
    }

    public Tenaamstelling getBezwaartTenaamstelling() {
        return this.bezwaartTenaamstelling;
    }

    public void setBezwaartTenaamstelling(Tenaamstelling tenaamstelling) {
        this.bezwaartTenaamstelling = tenaamstelling;
    }

    public Zekerheidsrecht bezwaartTenaamstelling(Tenaamstelling tenaamstelling) {
        this.setBezwaartTenaamstelling(tenaamstelling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zekerheidsrecht)) {
            return false;
        }
        return getId() != null && getId().equals(((Zekerheidsrecht) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zekerheidsrecht{" +
            "id=" + getId() +
            ", aandeelinbetrokkenrecht='" + getAandeelinbetrokkenrecht() + "'" +
            ", datumeinderecht='" + getDatumeinderecht() + "'" +
            ", datumingangrecht='" + getDatumingangrecht() + "'" +
            ", identificatiezekerheidsrecht='" + getIdentificatiezekerheidsrecht() + "'" +
            ", omschrijvingbetrokkenrecht='" + getOmschrijvingbetrokkenrecht() + "'" +
            ", typezekerheidsrecht='" + getTypezekerheidsrecht() + "'" +
            "}";
    }
}
