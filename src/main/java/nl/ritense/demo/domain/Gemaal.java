package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Gemaal.
 */
@Entity
@Table(name = "gemaal")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gemaal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalbedrijfsaansluitingen")
    private String aantalbedrijfsaansluitingen;

    @Column(name = "aantalhuisaansluitingen")
    private String aantalhuisaansluitingen;

    @Column(name = "aantalpompen")
    private String aantalpompen;

    @Column(name = "bedienaar")
    private String bedienaar;

    @Column(name = "effectievegemaalcapaciteit")
    private String effectievegemaalcapaciteit;

    @Column(name = "hijsinrichting")
    private Boolean hijsinrichting;

    @Column(name = "lanceerinrichting")
    private Boolean lanceerinrichting;

    @Column(name = "pompeninsamenloop")
    private Boolean pompeninsamenloop;

    @Column(name = "type")
    private String type;

    @Column(name = "veiligheidsrooster")
    private Boolean veiligheidsrooster;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gemaal id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalbedrijfsaansluitingen() {
        return this.aantalbedrijfsaansluitingen;
    }

    public Gemaal aantalbedrijfsaansluitingen(String aantalbedrijfsaansluitingen) {
        this.setAantalbedrijfsaansluitingen(aantalbedrijfsaansluitingen);
        return this;
    }

    public void setAantalbedrijfsaansluitingen(String aantalbedrijfsaansluitingen) {
        this.aantalbedrijfsaansluitingen = aantalbedrijfsaansluitingen;
    }

    public String getAantalhuisaansluitingen() {
        return this.aantalhuisaansluitingen;
    }

    public Gemaal aantalhuisaansluitingen(String aantalhuisaansluitingen) {
        this.setAantalhuisaansluitingen(aantalhuisaansluitingen);
        return this;
    }

    public void setAantalhuisaansluitingen(String aantalhuisaansluitingen) {
        this.aantalhuisaansluitingen = aantalhuisaansluitingen;
    }

    public String getAantalpompen() {
        return this.aantalpompen;
    }

    public Gemaal aantalpompen(String aantalpompen) {
        this.setAantalpompen(aantalpompen);
        return this;
    }

    public void setAantalpompen(String aantalpompen) {
        this.aantalpompen = aantalpompen;
    }

    public String getBedienaar() {
        return this.bedienaar;
    }

    public Gemaal bedienaar(String bedienaar) {
        this.setBedienaar(bedienaar);
        return this;
    }

    public void setBedienaar(String bedienaar) {
        this.bedienaar = bedienaar;
    }

    public String getEffectievegemaalcapaciteit() {
        return this.effectievegemaalcapaciteit;
    }

    public Gemaal effectievegemaalcapaciteit(String effectievegemaalcapaciteit) {
        this.setEffectievegemaalcapaciteit(effectievegemaalcapaciteit);
        return this;
    }

    public void setEffectievegemaalcapaciteit(String effectievegemaalcapaciteit) {
        this.effectievegemaalcapaciteit = effectievegemaalcapaciteit;
    }

    public Boolean getHijsinrichting() {
        return this.hijsinrichting;
    }

    public Gemaal hijsinrichting(Boolean hijsinrichting) {
        this.setHijsinrichting(hijsinrichting);
        return this;
    }

    public void setHijsinrichting(Boolean hijsinrichting) {
        this.hijsinrichting = hijsinrichting;
    }

    public Boolean getLanceerinrichting() {
        return this.lanceerinrichting;
    }

    public Gemaal lanceerinrichting(Boolean lanceerinrichting) {
        this.setLanceerinrichting(lanceerinrichting);
        return this;
    }

    public void setLanceerinrichting(Boolean lanceerinrichting) {
        this.lanceerinrichting = lanceerinrichting;
    }

    public Boolean getPompeninsamenloop() {
        return this.pompeninsamenloop;
    }

    public Gemaal pompeninsamenloop(Boolean pompeninsamenloop) {
        this.setPompeninsamenloop(pompeninsamenloop);
        return this;
    }

    public void setPompeninsamenloop(Boolean pompeninsamenloop) {
        this.pompeninsamenloop = pompeninsamenloop;
    }

    public String getType() {
        return this.type;
    }

    public Gemaal type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getVeiligheidsrooster() {
        return this.veiligheidsrooster;
    }

    public Gemaal veiligheidsrooster(Boolean veiligheidsrooster) {
        this.setVeiligheidsrooster(veiligheidsrooster);
        return this;
    }

    public void setVeiligheidsrooster(Boolean veiligheidsrooster) {
        this.veiligheidsrooster = veiligheidsrooster;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gemaal)) {
            return false;
        }
        return getId() != null && getId().equals(((Gemaal) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gemaal{" +
            "id=" + getId() +
            ", aantalbedrijfsaansluitingen='" + getAantalbedrijfsaansluitingen() + "'" +
            ", aantalhuisaansluitingen='" + getAantalhuisaansluitingen() + "'" +
            ", aantalpompen='" + getAantalpompen() + "'" +
            ", bedienaar='" + getBedienaar() + "'" +
            ", effectievegemaalcapaciteit='" + getEffectievegemaalcapaciteit() + "'" +
            ", hijsinrichting='" + getHijsinrichting() + "'" +
            ", lanceerinrichting='" + getLanceerinrichting() + "'" +
            ", pompeninsamenloop='" + getPompeninsamenloop() + "'" +
            ", type='" + getType() + "'" +
            ", veiligheidsrooster='" + getVeiligheidsrooster() + "'" +
            "}";
    }
}
