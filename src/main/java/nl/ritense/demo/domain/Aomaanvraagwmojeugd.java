package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aomaanvraagwmojeugd.
 */
@Entity
@Table(name = "aomaanvraagwmojeugd")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aomaanvraagwmojeugd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "clientreactie", length = 100)
    private String clientreactie;

    @Column(name = "datumbeschikking")
    private LocalDate datumbeschikking;

    @Column(name = "datumeersteafspraak")
    private LocalDate datumeersteafspraak;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumplanvastgesteld")
    private LocalDate datumplanvastgesteld;

    @Column(name = "datumstartaanvraag")
    private LocalDate datumstartaanvraag;

    @Column(name = "deskundigheid")
    private String deskundigheid;

    @Column(name = "doorloopmethodiek")
    private String doorloopmethodiek;

    @Size(max = 20)
    @Column(name = "maximaledoorlooptijd", length = 20)
    private String maximaledoorlooptijd;

    @Column(name = "redenafsluiting")
    private String redenafsluiting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aomaanvraagwmojeugd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientreactie() {
        return this.clientreactie;
    }

    public Aomaanvraagwmojeugd clientreactie(String clientreactie) {
        this.setClientreactie(clientreactie);
        return this;
    }

    public void setClientreactie(String clientreactie) {
        this.clientreactie = clientreactie;
    }

    public LocalDate getDatumbeschikking() {
        return this.datumbeschikking;
    }

    public Aomaanvraagwmojeugd datumbeschikking(LocalDate datumbeschikking) {
        this.setDatumbeschikking(datumbeschikking);
        return this;
    }

    public void setDatumbeschikking(LocalDate datumbeschikking) {
        this.datumbeschikking = datumbeschikking;
    }

    public LocalDate getDatumeersteafspraak() {
        return this.datumeersteafspraak;
    }

    public Aomaanvraagwmojeugd datumeersteafspraak(LocalDate datumeersteafspraak) {
        this.setDatumeersteafspraak(datumeersteafspraak);
        return this;
    }

    public void setDatumeersteafspraak(LocalDate datumeersteafspraak) {
        this.datumeersteafspraak = datumeersteafspraak;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Aomaanvraagwmojeugd datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumplanvastgesteld() {
        return this.datumplanvastgesteld;
    }

    public Aomaanvraagwmojeugd datumplanvastgesteld(LocalDate datumplanvastgesteld) {
        this.setDatumplanvastgesteld(datumplanvastgesteld);
        return this;
    }

    public void setDatumplanvastgesteld(LocalDate datumplanvastgesteld) {
        this.datumplanvastgesteld = datumplanvastgesteld;
    }

    public LocalDate getDatumstartaanvraag() {
        return this.datumstartaanvraag;
    }

    public Aomaanvraagwmojeugd datumstartaanvraag(LocalDate datumstartaanvraag) {
        this.setDatumstartaanvraag(datumstartaanvraag);
        return this;
    }

    public void setDatumstartaanvraag(LocalDate datumstartaanvraag) {
        this.datumstartaanvraag = datumstartaanvraag;
    }

    public String getDeskundigheid() {
        return this.deskundigheid;
    }

    public Aomaanvraagwmojeugd deskundigheid(String deskundigheid) {
        this.setDeskundigheid(deskundigheid);
        return this;
    }

    public void setDeskundigheid(String deskundigheid) {
        this.deskundigheid = deskundigheid;
    }

    public String getDoorloopmethodiek() {
        return this.doorloopmethodiek;
    }

    public Aomaanvraagwmojeugd doorloopmethodiek(String doorloopmethodiek) {
        this.setDoorloopmethodiek(doorloopmethodiek);
        return this;
    }

    public void setDoorloopmethodiek(String doorloopmethodiek) {
        this.doorloopmethodiek = doorloopmethodiek;
    }

    public String getMaximaledoorlooptijd() {
        return this.maximaledoorlooptijd;
    }

    public Aomaanvraagwmojeugd maximaledoorlooptijd(String maximaledoorlooptijd) {
        this.setMaximaledoorlooptijd(maximaledoorlooptijd);
        return this;
    }

    public void setMaximaledoorlooptijd(String maximaledoorlooptijd) {
        this.maximaledoorlooptijd = maximaledoorlooptijd;
    }

    public String getRedenafsluiting() {
        return this.redenafsluiting;
    }

    public Aomaanvraagwmojeugd redenafsluiting(String redenafsluiting) {
        this.setRedenafsluiting(redenafsluiting);
        return this;
    }

    public void setRedenafsluiting(String redenafsluiting) {
        this.redenafsluiting = redenafsluiting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aomaanvraagwmojeugd)) {
            return false;
        }
        return getId() != null && getId().equals(((Aomaanvraagwmojeugd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aomaanvraagwmojeugd{" +
            "id=" + getId() +
            ", clientreactie='" + getClientreactie() + "'" +
            ", datumbeschikking='" + getDatumbeschikking() + "'" +
            ", datumeersteafspraak='" + getDatumeersteafspraak() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumplanvastgesteld='" + getDatumplanvastgesteld() + "'" +
            ", datumstartaanvraag='" + getDatumstartaanvraag() + "'" +
            ", deskundigheid='" + getDeskundigheid() + "'" +
            ", doorloopmethodiek='" + getDoorloopmethodiek() + "'" +
            ", maximaledoorlooptijd='" + getMaximaledoorlooptijd() + "'" +
            ", redenafsluiting='" + getRedenafsluiting() + "'" +
            "}";
    }
}
