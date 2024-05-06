package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Asielstatushouder.
 */
@Entity
@Table(name = "asielstatushouder")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Asielstatushouder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "digidaangevraagd")
    private String digidaangevraagd;

    @Column(name = "emailadresverblijfazc")
    private String emailadresverblijfazc;

    @Column(name = "isgekoppeldaan")
    private String isgekoppeldaan;

    @Column(name = "landrijbewijs")
    private String landrijbewijs;

    @Column(name = "rijbewijs")
    private String rijbewijs;

    @Size(max = 10)
    @Column(name = "telefoonnummerverblijfazc", length = 10)
    private String telefoonnummerverblijfazc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isgekoppeldaanAsielstatushouders" }, allowSetters = true)
    private Gemeente isgekoppeldaanGemeente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Asielstatushouder id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDigidaangevraagd() {
        return this.digidaangevraagd;
    }

    public Asielstatushouder digidaangevraagd(String digidaangevraagd) {
        this.setDigidaangevraagd(digidaangevraagd);
        return this;
    }

    public void setDigidaangevraagd(String digidaangevraagd) {
        this.digidaangevraagd = digidaangevraagd;
    }

    public String getEmailadresverblijfazc() {
        return this.emailadresverblijfazc;
    }

    public Asielstatushouder emailadresverblijfazc(String emailadresverblijfazc) {
        this.setEmailadresverblijfazc(emailadresverblijfazc);
        return this;
    }

    public void setEmailadresverblijfazc(String emailadresverblijfazc) {
        this.emailadresverblijfazc = emailadresverblijfazc;
    }

    public String getIsgekoppeldaan() {
        return this.isgekoppeldaan;
    }

    public Asielstatushouder isgekoppeldaan(String isgekoppeldaan) {
        this.setIsgekoppeldaan(isgekoppeldaan);
        return this;
    }

    public void setIsgekoppeldaan(String isgekoppeldaan) {
        this.isgekoppeldaan = isgekoppeldaan;
    }

    public String getLandrijbewijs() {
        return this.landrijbewijs;
    }

    public Asielstatushouder landrijbewijs(String landrijbewijs) {
        this.setLandrijbewijs(landrijbewijs);
        return this;
    }

    public void setLandrijbewijs(String landrijbewijs) {
        this.landrijbewijs = landrijbewijs;
    }

    public String getRijbewijs() {
        return this.rijbewijs;
    }

    public Asielstatushouder rijbewijs(String rijbewijs) {
        this.setRijbewijs(rijbewijs);
        return this;
    }

    public void setRijbewijs(String rijbewijs) {
        this.rijbewijs = rijbewijs;
    }

    public String getTelefoonnummerverblijfazc() {
        return this.telefoonnummerverblijfazc;
    }

    public Asielstatushouder telefoonnummerverblijfazc(String telefoonnummerverblijfazc) {
        this.setTelefoonnummerverblijfazc(telefoonnummerverblijfazc);
        return this;
    }

    public void setTelefoonnummerverblijfazc(String telefoonnummerverblijfazc) {
        this.telefoonnummerverblijfazc = telefoonnummerverblijfazc;
    }

    public Gemeente getIsgekoppeldaanGemeente() {
        return this.isgekoppeldaanGemeente;
    }

    public void setIsgekoppeldaanGemeente(Gemeente gemeente) {
        this.isgekoppeldaanGemeente = gemeente;
    }

    public Asielstatushouder isgekoppeldaanGemeente(Gemeente gemeente) {
        this.setIsgekoppeldaanGemeente(gemeente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asielstatushouder)) {
            return false;
        }
        return getId() != null && getId().equals(((Asielstatushouder) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asielstatushouder{" +
            "id=" + getId() +
            ", digidaangevraagd='" + getDigidaangevraagd() + "'" +
            ", emailadresverblijfazc='" + getEmailadresverblijfazc() + "'" +
            ", isgekoppeldaan='" + getIsgekoppeldaan() + "'" +
            ", landrijbewijs='" + getLandrijbewijs() + "'" +
            ", rijbewijs='" + getRijbewijs() + "'" +
            ", telefoonnummerverblijfazc='" + getTelefoonnummerverblijfazc() + "'" +
            "}";
    }
}
