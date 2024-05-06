package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Statustype.
 */
@Entity
@Table(name = "statustype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Statustype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidstatustype")
    private String datumbegingeldigheidstatustype;

    @Column(name = "datumeindegeldigheidstatustype")
    private String datumeindegeldigheidstatustype;

    @Column(name = "doorlooptijdstatus")
    private String doorlooptijdstatus;

    @Column(name = "statustypeomschrijving")
    private String statustypeomschrijving;

    @Column(name = "statustypeomschrijvinggeneriek")
    private String statustypeomschrijvinggeneriek;

    @Column(name = "statustypevolgnummer")
    private String statustypevolgnummer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "heeftProducttype",
            "heeftHeffinggrondslags",
            "heeftStatustypes",
            "betreftProduct",
            "heeftBedrijfsprocestype",
            "isverantwoordelijkevoorMedewerker",
            "startDiensts",
            "isvanZaaks",
            "isaanleidingvoorFormuliersoorts",
        },
        allowSetters = true
    )
    private Zaaktype heeftZaaktype;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvanStatustype")
    @JsonIgnoreProperties(value = { "isvanStatustype", "heeftZaak" }, allowSetters = true)
    private Set<Status> isvanStatuses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Statustype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumbegingeldigheidstatustype() {
        return this.datumbegingeldigheidstatustype;
    }

    public Statustype datumbegingeldigheidstatustype(String datumbegingeldigheidstatustype) {
        this.setDatumbegingeldigheidstatustype(datumbegingeldigheidstatustype);
        return this;
    }

    public void setDatumbegingeldigheidstatustype(String datumbegingeldigheidstatustype) {
        this.datumbegingeldigheidstatustype = datumbegingeldigheidstatustype;
    }

    public String getDatumeindegeldigheidstatustype() {
        return this.datumeindegeldigheidstatustype;
    }

    public Statustype datumeindegeldigheidstatustype(String datumeindegeldigheidstatustype) {
        this.setDatumeindegeldigheidstatustype(datumeindegeldigheidstatustype);
        return this;
    }

    public void setDatumeindegeldigheidstatustype(String datumeindegeldigheidstatustype) {
        this.datumeindegeldigheidstatustype = datumeindegeldigheidstatustype;
    }

    public String getDoorlooptijdstatus() {
        return this.doorlooptijdstatus;
    }

    public Statustype doorlooptijdstatus(String doorlooptijdstatus) {
        this.setDoorlooptijdstatus(doorlooptijdstatus);
        return this;
    }

    public void setDoorlooptijdstatus(String doorlooptijdstatus) {
        this.doorlooptijdstatus = doorlooptijdstatus;
    }

    public String getStatustypeomschrijving() {
        return this.statustypeomschrijving;
    }

    public Statustype statustypeomschrijving(String statustypeomschrijving) {
        this.setStatustypeomschrijving(statustypeomschrijving);
        return this;
    }

    public void setStatustypeomschrijving(String statustypeomschrijving) {
        this.statustypeomschrijving = statustypeomschrijving;
    }

    public String getStatustypeomschrijvinggeneriek() {
        return this.statustypeomschrijvinggeneriek;
    }

    public Statustype statustypeomschrijvinggeneriek(String statustypeomschrijvinggeneriek) {
        this.setStatustypeomschrijvinggeneriek(statustypeomschrijvinggeneriek);
        return this;
    }

    public void setStatustypeomschrijvinggeneriek(String statustypeomschrijvinggeneriek) {
        this.statustypeomschrijvinggeneriek = statustypeomschrijvinggeneriek;
    }

    public String getStatustypevolgnummer() {
        return this.statustypevolgnummer;
    }

    public Statustype statustypevolgnummer(String statustypevolgnummer) {
        this.setStatustypevolgnummer(statustypevolgnummer);
        return this;
    }

    public void setStatustypevolgnummer(String statustypevolgnummer) {
        this.statustypevolgnummer = statustypevolgnummer;
    }

    public Zaaktype getHeeftZaaktype() {
        return this.heeftZaaktype;
    }

    public void setHeeftZaaktype(Zaaktype zaaktype) {
        this.heeftZaaktype = zaaktype;
    }

    public Statustype heeftZaaktype(Zaaktype zaaktype) {
        this.setHeeftZaaktype(zaaktype);
        return this;
    }

    public Set<Status> getIsvanStatuses() {
        return this.isvanStatuses;
    }

    public void setIsvanStatuses(Set<Status> statuses) {
        if (this.isvanStatuses != null) {
            this.isvanStatuses.forEach(i -> i.setIsvanStatustype(null));
        }
        if (statuses != null) {
            statuses.forEach(i -> i.setIsvanStatustype(this));
        }
        this.isvanStatuses = statuses;
    }

    public Statustype isvanStatuses(Set<Status> statuses) {
        this.setIsvanStatuses(statuses);
        return this;
    }

    public Statustype addIsvanStatus(Status status) {
        this.isvanStatuses.add(status);
        status.setIsvanStatustype(this);
        return this;
    }

    public Statustype removeIsvanStatus(Status status) {
        this.isvanStatuses.remove(status);
        status.setIsvanStatustype(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Statustype)) {
            return false;
        }
        return getId() != null && getId().equals(((Statustype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Statustype{" +
            "id=" + getId() +
            ", datumbegingeldigheidstatustype='" + getDatumbegingeldigheidstatustype() + "'" +
            ", datumeindegeldigheidstatustype='" + getDatumeindegeldigheidstatustype() + "'" +
            ", doorlooptijdstatus='" + getDoorlooptijdstatus() + "'" +
            ", statustypeomschrijving='" + getStatustypeomschrijving() + "'" +
            ", statustypeomschrijvinggeneriek='" + getStatustypeomschrijvinggeneriek() + "'" +
            ", statustypevolgnummer='" + getStatustypevolgnummer() + "'" +
            "}";
    }
}
