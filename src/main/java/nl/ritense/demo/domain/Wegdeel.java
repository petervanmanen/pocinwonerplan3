package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wegdeel.
 */
@Entity
@Table(name = "wegdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wegdeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidwegdeel")
    private LocalDate datumbegingeldigheidwegdeel;

    @Column(name = "datumeindegeldigheidwegdeel")
    private LocalDate datumeindegeldigheidwegdeel;

    @Column(name = "functiewegdeel")
    private String functiewegdeel;

    @Column(name = "fysiekvoorkomenwegdeel")
    private String fysiekvoorkomenwegdeel;

    @Column(name = "geometriewegdeel")
    private String geometriewegdeel;

    @Column(name = "identificatiewegdeel")
    private String identificatiewegdeel;

    @Column(name = "kruinlijngeometriewegdeel")
    private String kruinlijngeometriewegdeel;

    @Column(name = "lod_0_geometriewegdeel")
    private String lod0geometriewegdeel;

    @Column(name = "plusfunctiewegdeel")
    private String plusfunctiewegdeel;

    @Column(name = "plusfysiekvoorkomenwegdeel")
    private String plusfysiekvoorkomenwegdeel;

    @Column(name = "relatievehoogteliggingwegdeel")
    private String relatievehoogteliggingwegdeel;

    @Column(name = "statuswegdeel")
    private String statuswegdeel;

    @Column(name = "wegdeeloptalud")
    private String wegdeeloptalud;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftWegdeels")
    @JsonIgnoreProperties(value = { "betreftWegdeels", "ingevoerddoorMedewerker", "gewijzigddoorMedewerker" }, allowSetters = true)
    private Set<Stremming> betreftStremmings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wegdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidwegdeel() {
        return this.datumbegingeldigheidwegdeel;
    }

    public Wegdeel datumbegingeldigheidwegdeel(LocalDate datumbegingeldigheidwegdeel) {
        this.setDatumbegingeldigheidwegdeel(datumbegingeldigheidwegdeel);
        return this;
    }

    public void setDatumbegingeldigheidwegdeel(LocalDate datumbegingeldigheidwegdeel) {
        this.datumbegingeldigheidwegdeel = datumbegingeldigheidwegdeel;
    }

    public LocalDate getDatumeindegeldigheidwegdeel() {
        return this.datumeindegeldigheidwegdeel;
    }

    public Wegdeel datumeindegeldigheidwegdeel(LocalDate datumeindegeldigheidwegdeel) {
        this.setDatumeindegeldigheidwegdeel(datumeindegeldigheidwegdeel);
        return this;
    }

    public void setDatumeindegeldigheidwegdeel(LocalDate datumeindegeldigheidwegdeel) {
        this.datumeindegeldigheidwegdeel = datumeindegeldigheidwegdeel;
    }

    public String getFunctiewegdeel() {
        return this.functiewegdeel;
    }

    public Wegdeel functiewegdeel(String functiewegdeel) {
        this.setFunctiewegdeel(functiewegdeel);
        return this;
    }

    public void setFunctiewegdeel(String functiewegdeel) {
        this.functiewegdeel = functiewegdeel;
    }

    public String getFysiekvoorkomenwegdeel() {
        return this.fysiekvoorkomenwegdeel;
    }

    public Wegdeel fysiekvoorkomenwegdeel(String fysiekvoorkomenwegdeel) {
        this.setFysiekvoorkomenwegdeel(fysiekvoorkomenwegdeel);
        return this;
    }

    public void setFysiekvoorkomenwegdeel(String fysiekvoorkomenwegdeel) {
        this.fysiekvoorkomenwegdeel = fysiekvoorkomenwegdeel;
    }

    public String getGeometriewegdeel() {
        return this.geometriewegdeel;
    }

    public Wegdeel geometriewegdeel(String geometriewegdeel) {
        this.setGeometriewegdeel(geometriewegdeel);
        return this;
    }

    public void setGeometriewegdeel(String geometriewegdeel) {
        this.geometriewegdeel = geometriewegdeel;
    }

    public String getIdentificatiewegdeel() {
        return this.identificatiewegdeel;
    }

    public Wegdeel identificatiewegdeel(String identificatiewegdeel) {
        this.setIdentificatiewegdeel(identificatiewegdeel);
        return this;
    }

    public void setIdentificatiewegdeel(String identificatiewegdeel) {
        this.identificatiewegdeel = identificatiewegdeel;
    }

    public String getKruinlijngeometriewegdeel() {
        return this.kruinlijngeometriewegdeel;
    }

    public Wegdeel kruinlijngeometriewegdeel(String kruinlijngeometriewegdeel) {
        this.setKruinlijngeometriewegdeel(kruinlijngeometriewegdeel);
        return this;
    }

    public void setKruinlijngeometriewegdeel(String kruinlijngeometriewegdeel) {
        this.kruinlijngeometriewegdeel = kruinlijngeometriewegdeel;
    }

    public String getLod0geometriewegdeel() {
        return this.lod0geometriewegdeel;
    }

    public Wegdeel lod0geometriewegdeel(String lod0geometriewegdeel) {
        this.setLod0geometriewegdeel(lod0geometriewegdeel);
        return this;
    }

    public void setLod0geometriewegdeel(String lod0geometriewegdeel) {
        this.lod0geometriewegdeel = lod0geometriewegdeel;
    }

    public String getPlusfunctiewegdeel() {
        return this.plusfunctiewegdeel;
    }

    public Wegdeel plusfunctiewegdeel(String plusfunctiewegdeel) {
        this.setPlusfunctiewegdeel(plusfunctiewegdeel);
        return this;
    }

    public void setPlusfunctiewegdeel(String plusfunctiewegdeel) {
        this.plusfunctiewegdeel = plusfunctiewegdeel;
    }

    public String getPlusfysiekvoorkomenwegdeel() {
        return this.plusfysiekvoorkomenwegdeel;
    }

    public Wegdeel plusfysiekvoorkomenwegdeel(String plusfysiekvoorkomenwegdeel) {
        this.setPlusfysiekvoorkomenwegdeel(plusfysiekvoorkomenwegdeel);
        return this;
    }

    public void setPlusfysiekvoorkomenwegdeel(String plusfysiekvoorkomenwegdeel) {
        this.plusfysiekvoorkomenwegdeel = plusfysiekvoorkomenwegdeel;
    }

    public String getRelatievehoogteliggingwegdeel() {
        return this.relatievehoogteliggingwegdeel;
    }

    public Wegdeel relatievehoogteliggingwegdeel(String relatievehoogteliggingwegdeel) {
        this.setRelatievehoogteliggingwegdeel(relatievehoogteliggingwegdeel);
        return this;
    }

    public void setRelatievehoogteliggingwegdeel(String relatievehoogteliggingwegdeel) {
        this.relatievehoogteliggingwegdeel = relatievehoogteliggingwegdeel;
    }

    public String getStatuswegdeel() {
        return this.statuswegdeel;
    }

    public Wegdeel statuswegdeel(String statuswegdeel) {
        this.setStatuswegdeel(statuswegdeel);
        return this;
    }

    public void setStatuswegdeel(String statuswegdeel) {
        this.statuswegdeel = statuswegdeel;
    }

    public String getWegdeeloptalud() {
        return this.wegdeeloptalud;
    }

    public Wegdeel wegdeeloptalud(String wegdeeloptalud) {
        this.setWegdeeloptalud(wegdeeloptalud);
        return this;
    }

    public void setWegdeeloptalud(String wegdeeloptalud) {
        this.wegdeeloptalud = wegdeeloptalud;
    }

    public Set<Stremming> getBetreftStremmings() {
        return this.betreftStremmings;
    }

    public void setBetreftStremmings(Set<Stremming> stremmings) {
        if (this.betreftStremmings != null) {
            this.betreftStremmings.forEach(i -> i.removeBetreftWegdeel(this));
        }
        if (stremmings != null) {
            stremmings.forEach(i -> i.addBetreftWegdeel(this));
        }
        this.betreftStremmings = stremmings;
    }

    public Wegdeel betreftStremmings(Set<Stremming> stremmings) {
        this.setBetreftStremmings(stremmings);
        return this;
    }

    public Wegdeel addBetreftStremming(Stremming stremming) {
        this.betreftStremmings.add(stremming);
        stremming.getBetreftWegdeels().add(this);
        return this;
    }

    public Wegdeel removeBetreftStremming(Stremming stremming) {
        this.betreftStremmings.remove(stremming);
        stremming.getBetreftWegdeels().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wegdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Wegdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wegdeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidwegdeel='" + getDatumbegingeldigheidwegdeel() + "'" +
            ", datumeindegeldigheidwegdeel='" + getDatumeindegeldigheidwegdeel() + "'" +
            ", functiewegdeel='" + getFunctiewegdeel() + "'" +
            ", fysiekvoorkomenwegdeel='" + getFysiekvoorkomenwegdeel() + "'" +
            ", geometriewegdeel='" + getGeometriewegdeel() + "'" +
            ", identificatiewegdeel='" + getIdentificatiewegdeel() + "'" +
            ", kruinlijngeometriewegdeel='" + getKruinlijngeometriewegdeel() + "'" +
            ", lod0geometriewegdeel='" + getLod0geometriewegdeel() + "'" +
            ", plusfunctiewegdeel='" + getPlusfunctiewegdeel() + "'" +
            ", plusfysiekvoorkomenwegdeel='" + getPlusfysiekvoorkomenwegdeel() + "'" +
            ", relatievehoogteliggingwegdeel='" + getRelatievehoogteliggingwegdeel() + "'" +
            ", statuswegdeel='" + getStatuswegdeel() + "'" +
            ", wegdeeloptalud='" + getWegdeeloptalud() + "'" +
            "}";
    }
}
