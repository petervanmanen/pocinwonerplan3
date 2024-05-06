package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Vloginfo.
 */
@Entity
@Table(name = "vloginfo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vloginfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "detectieverkeer")
    private String detectieverkeer;

    @Column(name = "eindegroen")
    private Boolean eindegroen;

    @Column(name = "snelheid")
    private String snelheid;

    @Column(name = "startgroen")
    private Boolean startgroen;

    @Column(name = "tijdstip")
    private String tijdstip;

    @Column(name = "verkeerwilgroen")
    private Boolean verkeerwilgroen;

    @Column(name = "wachttijd")
    private String wachttijd;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vloginfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetectieverkeer() {
        return this.detectieverkeer;
    }

    public Vloginfo detectieverkeer(String detectieverkeer) {
        this.setDetectieverkeer(detectieverkeer);
        return this;
    }

    public void setDetectieverkeer(String detectieverkeer) {
        this.detectieverkeer = detectieverkeer;
    }

    public Boolean getEindegroen() {
        return this.eindegroen;
    }

    public Vloginfo eindegroen(Boolean eindegroen) {
        this.setEindegroen(eindegroen);
        return this;
    }

    public void setEindegroen(Boolean eindegroen) {
        this.eindegroen = eindegroen;
    }

    public String getSnelheid() {
        return this.snelheid;
    }

    public Vloginfo snelheid(String snelheid) {
        this.setSnelheid(snelheid);
        return this;
    }

    public void setSnelheid(String snelheid) {
        this.snelheid = snelheid;
    }

    public Boolean getStartgroen() {
        return this.startgroen;
    }

    public Vloginfo startgroen(Boolean startgroen) {
        this.setStartgroen(startgroen);
        return this;
    }

    public void setStartgroen(Boolean startgroen) {
        this.startgroen = startgroen;
    }

    public String getTijdstip() {
        return this.tijdstip;
    }

    public Vloginfo tijdstip(String tijdstip) {
        this.setTijdstip(tijdstip);
        return this;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public Boolean getVerkeerwilgroen() {
        return this.verkeerwilgroen;
    }

    public Vloginfo verkeerwilgroen(Boolean verkeerwilgroen) {
        this.setVerkeerwilgroen(verkeerwilgroen);
        return this;
    }

    public void setVerkeerwilgroen(Boolean verkeerwilgroen) {
        this.verkeerwilgroen = verkeerwilgroen;
    }

    public String getWachttijd() {
        return this.wachttijd;
    }

    public Vloginfo wachttijd(String wachttijd) {
        this.setWachttijd(wachttijd);
        return this;
    }

    public void setWachttijd(String wachttijd) {
        this.wachttijd = wachttijd;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vloginfo)) {
            return false;
        }
        return getId() != null && getId().equals(((Vloginfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vloginfo{" +
            "id=" + getId() +
            ", detectieverkeer='" + getDetectieverkeer() + "'" +
            ", eindegroen='" + getEindegroen() + "'" +
            ", snelheid='" + getSnelheid() + "'" +
            ", startgroen='" + getStartgroen() + "'" +
            ", tijdstip='" + getTijdstip() + "'" +
            ", verkeerwilgroen='" + getVerkeerwilgroen() + "'" +
            ", wachttijd='" + getWachttijd() + "'" +
            "}";
    }
}
