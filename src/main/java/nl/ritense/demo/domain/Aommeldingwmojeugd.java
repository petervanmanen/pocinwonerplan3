package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Aommeldingwmojeugd.
 */
@Entity
@Table(name = "aommeldingwmojeugd")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aommeldingwmojeugd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "aanmelder", length = 100)
    private String aanmelder;

    @Size(max = 100)
    @Column(name = "aanmeldingdoor", length = 100)
    private String aanmeldingdoor;

    @Size(max = 100)
    @Column(name = "aanmeldingdoorlandelijk", length = 100)
    private String aanmeldingdoorlandelijk;

    @Column(name = "aanmeldwijze")
    private String aanmeldwijze;

    @Column(name = "deskundigheid")
    private String deskundigheid;

    @Column(name = "isclientopdehoogte")
    private String isclientopdehoogte;

    @Column(name = "onderzoekswijze")
    private String onderzoekswijze;

    @Column(name = "redenafsluiting")
    private String redenafsluiting;

    @Column(name = "vervolg")
    private String vervolg;

    @Column(name = "verwezen")
    private String verwezen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aommeldingwmojeugd id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanmelder() {
        return this.aanmelder;
    }

    public Aommeldingwmojeugd aanmelder(String aanmelder) {
        this.setAanmelder(aanmelder);
        return this;
    }

    public void setAanmelder(String aanmelder) {
        this.aanmelder = aanmelder;
    }

    public String getAanmeldingdoor() {
        return this.aanmeldingdoor;
    }

    public Aommeldingwmojeugd aanmeldingdoor(String aanmeldingdoor) {
        this.setAanmeldingdoor(aanmeldingdoor);
        return this;
    }

    public void setAanmeldingdoor(String aanmeldingdoor) {
        this.aanmeldingdoor = aanmeldingdoor;
    }

    public String getAanmeldingdoorlandelijk() {
        return this.aanmeldingdoorlandelijk;
    }

    public Aommeldingwmojeugd aanmeldingdoorlandelijk(String aanmeldingdoorlandelijk) {
        this.setAanmeldingdoorlandelijk(aanmeldingdoorlandelijk);
        return this;
    }

    public void setAanmeldingdoorlandelijk(String aanmeldingdoorlandelijk) {
        this.aanmeldingdoorlandelijk = aanmeldingdoorlandelijk;
    }

    public String getAanmeldwijze() {
        return this.aanmeldwijze;
    }

    public Aommeldingwmojeugd aanmeldwijze(String aanmeldwijze) {
        this.setAanmeldwijze(aanmeldwijze);
        return this;
    }

    public void setAanmeldwijze(String aanmeldwijze) {
        this.aanmeldwijze = aanmeldwijze;
    }

    public String getDeskundigheid() {
        return this.deskundigheid;
    }

    public Aommeldingwmojeugd deskundigheid(String deskundigheid) {
        this.setDeskundigheid(deskundigheid);
        return this;
    }

    public void setDeskundigheid(String deskundigheid) {
        this.deskundigheid = deskundigheid;
    }

    public String getIsclientopdehoogte() {
        return this.isclientopdehoogte;
    }

    public Aommeldingwmojeugd isclientopdehoogte(String isclientopdehoogte) {
        this.setIsclientopdehoogte(isclientopdehoogte);
        return this;
    }

    public void setIsclientopdehoogte(String isclientopdehoogte) {
        this.isclientopdehoogte = isclientopdehoogte;
    }

    public String getOnderzoekswijze() {
        return this.onderzoekswijze;
    }

    public Aommeldingwmojeugd onderzoekswijze(String onderzoekswijze) {
        this.setOnderzoekswijze(onderzoekswijze);
        return this;
    }

    public void setOnderzoekswijze(String onderzoekswijze) {
        this.onderzoekswijze = onderzoekswijze;
    }

    public String getRedenafsluiting() {
        return this.redenafsluiting;
    }

    public Aommeldingwmojeugd redenafsluiting(String redenafsluiting) {
        this.setRedenafsluiting(redenafsluiting);
        return this;
    }

    public void setRedenafsluiting(String redenafsluiting) {
        this.redenafsluiting = redenafsluiting;
    }

    public String getVervolg() {
        return this.vervolg;
    }

    public Aommeldingwmojeugd vervolg(String vervolg) {
        this.setVervolg(vervolg);
        return this;
    }

    public void setVervolg(String vervolg) {
        this.vervolg = vervolg;
    }

    public String getVerwezen() {
        return this.verwezen;
    }

    public Aommeldingwmojeugd verwezen(String verwezen) {
        this.setVerwezen(verwezen);
        return this;
    }

    public void setVerwezen(String verwezen) {
        this.verwezen = verwezen;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aommeldingwmojeugd)) {
            return false;
        }
        return getId() != null && getId().equals(((Aommeldingwmojeugd) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aommeldingwmojeugd{" +
            "id=" + getId() +
            ", aanmelder='" + getAanmelder() + "'" +
            ", aanmeldingdoor='" + getAanmeldingdoor() + "'" +
            ", aanmeldingdoorlandelijk='" + getAanmeldingdoorlandelijk() + "'" +
            ", aanmeldwijze='" + getAanmeldwijze() + "'" +
            ", deskundigheid='" + getDeskundigheid() + "'" +
            ", isclientopdehoogte='" + getIsclientopdehoogte() + "'" +
            ", onderzoekswijze='" + getOnderzoekswijze() + "'" +
            ", redenafsluiting='" + getRedenafsluiting() + "'" +
            ", vervolg='" + getVervolg() + "'" +
            ", verwezen='" + getVerwezen() + "'" +
            "}";
    }
}
