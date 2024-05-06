package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tenaamstelling.
 */
@Entity
@Table(name = "tenaamstelling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tenaamstelling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aandeelinrecht")
    private String aandeelinrecht;

    @Column(name = "burgerlijkestaattentijdevanverkrijging")
    private String burgerlijkestaattentijdevanverkrijging;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "exploitantcode")
    private String exploitantcode;

    @Column(name = "identificatietenaamstelling")
    private String identificatietenaamstelling;

    @Size(max = 100)
    @Column(name = "verklaringinzakederdenbescherming", length = 100)
    private String verklaringinzakederdenbescherming;

    @Column(name = "verkregennamenssamenwerkingsverband")
    private String verkregennamenssamenwerkingsverband;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emptyTenaamstelling")
    @JsonIgnoreProperties(value = { "emptyTenaamstelling" }, allowSetters = true)
    private Set<Aantekening> emptyAantekenings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    private Rechtspersoon heeftRechtspersoon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bezwaartTenaamstelling")
    @JsonIgnoreProperties(value = { "bezwaartTenaamstelling" }, allowSetters = true)
    private Set<Zekerheidsrecht> bezwaartZekerheidsrechts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tenaamstelling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAandeelinrecht() {
        return this.aandeelinrecht;
    }

    public Tenaamstelling aandeelinrecht(String aandeelinrecht) {
        this.setAandeelinrecht(aandeelinrecht);
        return this;
    }

    public void setAandeelinrecht(String aandeelinrecht) {
        this.aandeelinrecht = aandeelinrecht;
    }

    public String getBurgerlijkestaattentijdevanverkrijging() {
        return this.burgerlijkestaattentijdevanverkrijging;
    }

    public Tenaamstelling burgerlijkestaattentijdevanverkrijging(String burgerlijkestaattentijdevanverkrijging) {
        this.setBurgerlijkestaattentijdevanverkrijging(burgerlijkestaattentijdevanverkrijging);
        return this;
    }

    public void setBurgerlijkestaattentijdevanverkrijging(String burgerlijkestaattentijdevanverkrijging) {
        this.burgerlijkestaattentijdevanverkrijging = burgerlijkestaattentijdevanverkrijging;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Tenaamstelling datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Tenaamstelling datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getExploitantcode() {
        return this.exploitantcode;
    }

    public Tenaamstelling exploitantcode(String exploitantcode) {
        this.setExploitantcode(exploitantcode);
        return this;
    }

    public void setExploitantcode(String exploitantcode) {
        this.exploitantcode = exploitantcode;
    }

    public String getIdentificatietenaamstelling() {
        return this.identificatietenaamstelling;
    }

    public Tenaamstelling identificatietenaamstelling(String identificatietenaamstelling) {
        this.setIdentificatietenaamstelling(identificatietenaamstelling);
        return this;
    }

    public void setIdentificatietenaamstelling(String identificatietenaamstelling) {
        this.identificatietenaamstelling = identificatietenaamstelling;
    }

    public String getVerklaringinzakederdenbescherming() {
        return this.verklaringinzakederdenbescherming;
    }

    public Tenaamstelling verklaringinzakederdenbescherming(String verklaringinzakederdenbescherming) {
        this.setVerklaringinzakederdenbescherming(verklaringinzakederdenbescherming);
        return this;
    }

    public void setVerklaringinzakederdenbescherming(String verklaringinzakederdenbescherming) {
        this.verklaringinzakederdenbescherming = verklaringinzakederdenbescherming;
    }

    public String getVerkregennamenssamenwerkingsverband() {
        return this.verkregennamenssamenwerkingsverband;
    }

    public Tenaamstelling verkregennamenssamenwerkingsverband(String verkregennamenssamenwerkingsverband) {
        this.setVerkregennamenssamenwerkingsverband(verkregennamenssamenwerkingsverband);
        return this;
    }

    public void setVerkregennamenssamenwerkingsverband(String verkregennamenssamenwerkingsverband) {
        this.verkregennamenssamenwerkingsverband = verkregennamenssamenwerkingsverband;
    }

    public Set<Aantekening> getEmptyAantekenings() {
        return this.emptyAantekenings;
    }

    public void setEmptyAantekenings(Set<Aantekening> aantekenings) {
        if (this.emptyAantekenings != null) {
            this.emptyAantekenings.forEach(i -> i.setEmptyTenaamstelling(null));
        }
        if (aantekenings != null) {
            aantekenings.forEach(i -> i.setEmptyTenaamstelling(this));
        }
        this.emptyAantekenings = aantekenings;
    }

    public Tenaamstelling emptyAantekenings(Set<Aantekening> aantekenings) {
        this.setEmptyAantekenings(aantekenings);
        return this;
    }

    public Tenaamstelling addEmptyAantekening(Aantekening aantekening) {
        this.emptyAantekenings.add(aantekening);
        aantekening.setEmptyTenaamstelling(this);
        return this;
    }

    public Tenaamstelling removeEmptyAantekening(Aantekening aantekening) {
        this.emptyAantekenings.remove(aantekening);
        aantekening.setEmptyTenaamstelling(null);
        return this;
    }

    public Rechtspersoon getHeeftRechtspersoon() {
        return this.heeftRechtspersoon;
    }

    public void setHeeftRechtspersoon(Rechtspersoon rechtspersoon) {
        this.heeftRechtspersoon = rechtspersoon;
    }

    public Tenaamstelling heeftRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setHeeftRechtspersoon(rechtspersoon);
        return this;
    }

    public Set<Zekerheidsrecht> getBezwaartZekerheidsrechts() {
        return this.bezwaartZekerheidsrechts;
    }

    public void setBezwaartZekerheidsrechts(Set<Zekerheidsrecht> zekerheidsrechts) {
        if (this.bezwaartZekerheidsrechts != null) {
            this.bezwaartZekerheidsrechts.forEach(i -> i.setBezwaartTenaamstelling(null));
        }
        if (zekerheidsrechts != null) {
            zekerheidsrechts.forEach(i -> i.setBezwaartTenaamstelling(this));
        }
        this.bezwaartZekerheidsrechts = zekerheidsrechts;
    }

    public Tenaamstelling bezwaartZekerheidsrechts(Set<Zekerheidsrecht> zekerheidsrechts) {
        this.setBezwaartZekerheidsrechts(zekerheidsrechts);
        return this;
    }

    public Tenaamstelling addBezwaartZekerheidsrecht(Zekerheidsrecht zekerheidsrecht) {
        this.bezwaartZekerheidsrechts.add(zekerheidsrecht);
        zekerheidsrecht.setBezwaartTenaamstelling(this);
        return this;
    }

    public Tenaamstelling removeBezwaartZekerheidsrecht(Zekerheidsrecht zekerheidsrecht) {
        this.bezwaartZekerheidsrechts.remove(zekerheidsrecht);
        zekerheidsrecht.setBezwaartTenaamstelling(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tenaamstelling)) {
            return false;
        }
        return getId() != null && getId().equals(((Tenaamstelling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tenaamstelling{" +
            "id=" + getId() +
            ", aandeelinrecht='" + getAandeelinrecht() + "'" +
            ", burgerlijkestaattentijdevanverkrijging='" + getBurgerlijkestaattentijdevanverkrijging() + "'" +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", exploitantcode='" + getExploitantcode() + "'" +
            ", identificatietenaamstelling='" + getIdentificatietenaamstelling() + "'" +
            ", verklaringinzakederdenbescherming='" + getVerklaringinzakederdenbescherming() + "'" +
            ", verkregennamenssamenwerkingsverband='" + getVerkregennamenssamenwerkingsverband() + "'" +
            "}";
    }
}
