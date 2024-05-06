package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sensor.
 */
@Entity
@Table(name = "sensor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanleghoogte")
    private String aanleghoogte;

    @Column(name = "elektrakast")
    private String elektrakast;

    @Column(name = "frequentieomvormer")
    private String frequentieomvormer;

    @Column(name = "hoogte")
    private String hoogte;

    @Column(name = "jaaronderhouduitgevoerd")
    private String jaaronderhouduitgevoerd;

    @Column(name = "leverancier")
    private String leverancier;

    @Column(name = "meetpunt")
    private String meetpunt;

    @Column(name = "plc")
    private String plc;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gegenereerddoorSensor")
    @JsonIgnoreProperties(value = { "gegenereerddoorSensor" }, allowSetters = true)
    private Set<Verkeerstelling> gegenereerddoorVerkeerstellings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sensor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleghoogte() {
        return this.aanleghoogte;
    }

    public Sensor aanleghoogte(String aanleghoogte) {
        this.setAanleghoogte(aanleghoogte);
        return this;
    }

    public void setAanleghoogte(String aanleghoogte) {
        this.aanleghoogte = aanleghoogte;
    }

    public String getElektrakast() {
        return this.elektrakast;
    }

    public Sensor elektrakast(String elektrakast) {
        this.setElektrakast(elektrakast);
        return this;
    }

    public void setElektrakast(String elektrakast) {
        this.elektrakast = elektrakast;
    }

    public String getFrequentieomvormer() {
        return this.frequentieomvormer;
    }

    public Sensor frequentieomvormer(String frequentieomvormer) {
        this.setFrequentieomvormer(frequentieomvormer);
        return this;
    }

    public void setFrequentieomvormer(String frequentieomvormer) {
        this.frequentieomvormer = frequentieomvormer;
    }

    public String getHoogte() {
        return this.hoogte;
    }

    public Sensor hoogte(String hoogte) {
        this.setHoogte(hoogte);
        return this;
    }

    public void setHoogte(String hoogte) {
        this.hoogte = hoogte;
    }

    public String getJaaronderhouduitgevoerd() {
        return this.jaaronderhouduitgevoerd;
    }

    public Sensor jaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.setJaaronderhouduitgevoerd(jaaronderhouduitgevoerd);
        return this;
    }

    public void setJaaronderhouduitgevoerd(String jaaronderhouduitgevoerd) {
        this.jaaronderhouduitgevoerd = jaaronderhouduitgevoerd;
    }

    public String getLeverancier() {
        return this.leverancier;
    }

    public Sensor leverancier(String leverancier) {
        this.setLeverancier(leverancier);
        return this;
    }

    public void setLeverancier(String leverancier) {
        this.leverancier = leverancier;
    }

    public String getMeetpunt() {
        return this.meetpunt;
    }

    public Sensor meetpunt(String meetpunt) {
        this.setMeetpunt(meetpunt);
        return this;
    }

    public void setMeetpunt(String meetpunt) {
        this.meetpunt = meetpunt;
    }

    public String getPlc() {
        return this.plc;
    }

    public Sensor plc(String plc) {
        this.setPlc(plc);
        return this;
    }

    public void setPlc(String plc) {
        this.plc = plc;
    }

    public Set<Verkeerstelling> getGegenereerddoorVerkeerstellings() {
        return this.gegenereerddoorVerkeerstellings;
    }

    public void setGegenereerddoorVerkeerstellings(Set<Verkeerstelling> verkeerstellings) {
        if (this.gegenereerddoorVerkeerstellings != null) {
            this.gegenereerddoorVerkeerstellings.forEach(i -> i.setGegenereerddoorSensor(null));
        }
        if (verkeerstellings != null) {
            verkeerstellings.forEach(i -> i.setGegenereerddoorSensor(this));
        }
        this.gegenereerddoorVerkeerstellings = verkeerstellings;
    }

    public Sensor gegenereerddoorVerkeerstellings(Set<Verkeerstelling> verkeerstellings) {
        this.setGegenereerddoorVerkeerstellings(verkeerstellings);
        return this;
    }

    public Sensor addGegenereerddoorVerkeerstelling(Verkeerstelling verkeerstelling) {
        this.gegenereerddoorVerkeerstellings.add(verkeerstelling);
        verkeerstelling.setGegenereerddoorSensor(this);
        return this;
    }

    public Sensor removeGegenereerddoorVerkeerstelling(Verkeerstelling verkeerstelling) {
        this.gegenereerddoorVerkeerstellings.remove(verkeerstelling);
        verkeerstelling.setGegenereerddoorSensor(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sensor)) {
            return false;
        }
        return getId() != null && getId().equals(((Sensor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sensor{" +
            "id=" + getId() +
            ", aanleghoogte='" + getAanleghoogte() + "'" +
            ", elektrakast='" + getElektrakast() + "'" +
            ", frequentieomvormer='" + getFrequentieomvormer() + "'" +
            ", hoogte='" + getHoogte() + "'" +
            ", jaaronderhouduitgevoerd='" + getJaaronderhouduitgevoerd() + "'" +
            ", leverancier='" + getLeverancier() + "'" +
            ", meetpunt='" + getMeetpunt() + "'" +
            ", plc='" + getPlc() + "'" +
            "}";
    }
}
