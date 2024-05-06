package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Versie.
 */
@Entity
@Table(name = "versie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Versie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantal")
    private String aantal;

    @Column(name = "datumeindesupport")
    private LocalDate datumeindesupport;

    @Column(name = "kosten", precision = 21, scale = 2)
    private BigDecimal kosten;

    @Column(name = "licentie")
    private String licentie;

    @Column(name = "status")
    private String status;

    @Size(max = 8)
    @Column(name = "versienummer", length = 8)
    private String versienummer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "heeftversiesVersies",
            "bevatGegevens",
            "heeftherkomstBatches",
            "heeftnotitiesNotities",
            "heeftleverancierLeverancier",
            "heeftdocumentenDocuments",
            "rollenMedewerkers",
        },
        allowSetters = true
    )
    private Applicatie heeftversiesApplicatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Versie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantal() {
        return this.aantal;
    }

    public Versie aantal(String aantal) {
        this.setAantal(aantal);
        return this;
    }

    public void setAantal(String aantal) {
        this.aantal = aantal;
    }

    public LocalDate getDatumeindesupport() {
        return this.datumeindesupport;
    }

    public Versie datumeindesupport(LocalDate datumeindesupport) {
        this.setDatumeindesupport(datumeindesupport);
        return this;
    }

    public void setDatumeindesupport(LocalDate datumeindesupport) {
        this.datumeindesupport = datumeindesupport;
    }

    public BigDecimal getKosten() {
        return this.kosten;
    }

    public Versie kosten(BigDecimal kosten) {
        this.setKosten(kosten);
        return this;
    }

    public void setKosten(BigDecimal kosten) {
        this.kosten = kosten;
    }

    public String getLicentie() {
        return this.licentie;
    }

    public Versie licentie(String licentie) {
        this.setLicentie(licentie);
        return this;
    }

    public void setLicentie(String licentie) {
        this.licentie = licentie;
    }

    public String getStatus() {
        return this.status;
    }

    public Versie status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersienummer() {
        return this.versienummer;
    }

    public Versie versienummer(String versienummer) {
        this.setVersienummer(versienummer);
        return this;
    }

    public void setVersienummer(String versienummer) {
        this.versienummer = versienummer;
    }

    public Applicatie getHeeftversiesApplicatie() {
        return this.heeftversiesApplicatie;
    }

    public void setHeeftversiesApplicatie(Applicatie applicatie) {
        this.heeftversiesApplicatie = applicatie;
    }

    public Versie heeftversiesApplicatie(Applicatie applicatie) {
        this.setHeeftversiesApplicatie(applicatie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Versie)) {
            return false;
        }
        return getId() != null && getId().equals(((Versie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Versie{" +
            "id=" + getId() +
            ", aantal='" + getAantal() + "'" +
            ", datumeindesupport='" + getDatumeindesupport() + "'" +
            ", kosten=" + getKosten() +
            ", licentie='" + getLicentie() + "'" +
            ", status='" + getStatus() + "'" +
            ", versienummer='" + getVersienummer() + "'" +
            "}";
    }
}
