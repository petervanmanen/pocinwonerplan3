package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Reservering.
 */
@Entity
@Table(name = "reservering")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservering implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Size(max = 8)
    @Column(name = "btw", length = 8)
    private String btw;

    @Column(name = "tijdtot")
    private String tijdtot;

    @Column(name = "tijdvanaf")
    private String tijdvanaf;

    @Column(name = "totaalprijs")
    private String totaalprijs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftTariefs", "valtbinnenVoorzieningsoort", "betreftReserverings", "voorzieningLeverings" },
        allowSetters = true
    )
    private Voorziening betreftVoorziening;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "betreftReserverings" }, allowSetters = true)
    private Zaal betreftZaal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "gerelateerdeactiviteitActiviteit",
            "bovenliggendeactiviteitActiviteit",
            "bestaatuitActiviteits",
            "heeftReserverings",
            "heeftRondleiding",
            "vansoortActiviteitsoort",
            "isverbondenmetLocaties",
            "gerelateerdeactiviteitActiviteit2",
            "bovenliggendeactiviteitActiviteit2",
            "bestaatuitActiviteit2",
            "bestaatuitProgramma",
            "betreftVerzoeks",
        },
        allowSetters = true
    )
    private Activiteit heeftActiviteit;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservering id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Reservering aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public String getBtw() {
        return this.btw;
    }

    public Reservering btw(String btw) {
        this.setBtw(btw);
        return this;
    }

    public void setBtw(String btw) {
        this.btw = btw;
    }

    public String getTijdtot() {
        return this.tijdtot;
    }

    public Reservering tijdtot(String tijdtot) {
        this.setTijdtot(tijdtot);
        return this;
    }

    public void setTijdtot(String tijdtot) {
        this.tijdtot = tijdtot;
    }

    public String getTijdvanaf() {
        return this.tijdvanaf;
    }

    public Reservering tijdvanaf(String tijdvanaf) {
        this.setTijdvanaf(tijdvanaf);
        return this;
    }

    public void setTijdvanaf(String tijdvanaf) {
        this.tijdvanaf = tijdvanaf;
    }

    public String getTotaalprijs() {
        return this.totaalprijs;
    }

    public Reservering totaalprijs(String totaalprijs) {
        this.setTotaalprijs(totaalprijs);
        return this;
    }

    public void setTotaalprijs(String totaalprijs) {
        this.totaalprijs = totaalprijs;
    }

    public Voorziening getBetreftVoorziening() {
        return this.betreftVoorziening;
    }

    public void setBetreftVoorziening(Voorziening voorziening) {
        this.betreftVoorziening = voorziening;
    }

    public Reservering betreftVoorziening(Voorziening voorziening) {
        this.setBetreftVoorziening(voorziening);
        return this;
    }

    public Zaal getBetreftZaal() {
        return this.betreftZaal;
    }

    public void setBetreftZaal(Zaal zaal) {
        this.betreftZaal = zaal;
    }

    public Reservering betreftZaal(Zaal zaal) {
        this.setBetreftZaal(zaal);
        return this;
    }

    public Activiteit getHeeftActiviteit() {
        return this.heeftActiviteit;
    }

    public void setHeeftActiviteit(Activiteit activiteit) {
        this.heeftActiviteit = activiteit;
    }

    public Reservering heeftActiviteit(Activiteit activiteit) {
        this.setHeeftActiviteit(activiteit);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservering)) {
            return false;
        }
        return getId() != null && getId().equals(((Reservering) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservering{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", btw='" + getBtw() + "'" +
            ", tijdtot='" + getTijdtot() + "'" +
            ", tijdvanaf='" + getTijdvanaf() + "'" +
            ", totaalprijs='" + getTotaalprijs() + "'" +
            "}";
    }
}
