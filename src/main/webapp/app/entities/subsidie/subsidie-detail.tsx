import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subsidie.reducer';

export const SubsidieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subsidieEntity = useAppSelector(state => state.subsidie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subsidieDetailsHeading">Subsidie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subsidieEntity.id}</dd>
          <dt>
            <span id="accountantscontrole">Accountantscontrole</span>
          </dt>
          <dd>{subsidieEntity.accountantscontrole}</dd>
          <dt>
            <span id="cofinanciering">Cofinanciering</span>
          </dt>
          <dd>{subsidieEntity.cofinanciering}</dd>
          <dt>
            <span id="datumbehandeltermijn">Datumbehandeltermijn</span>
          </dt>
          <dd>
            {subsidieEntity.datumbehandeltermijn ? (
              <TextFormat value={subsidieEntity.datumbehandeltermijn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumbewaartermijn">Datumbewaartermijn</span>
          </dt>
          <dd>
            {subsidieEntity.datumbewaartermijn ? (
              <TextFormat value={subsidieEntity.datumbewaartermijn} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {subsidieEntity.datumeinde ? <TextFormat value={subsidieEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {subsidieEntity.datumstart ? <TextFormat value={subsidieEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumsubsidievaststelling">Datumsubsidievaststelling</span>
          </dt>
          <dd>
            {subsidieEntity.datumsubsidievaststelling ? (
              <TextFormat value={subsidieEntity.datumsubsidievaststelling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumverzendingeindeafrekening">Datumverzendingeindeafrekening</span>
          </dt>
          <dd>
            {subsidieEntity.datumverzendingeindeafrekening ? (
              <TextFormat value={subsidieEntity.datumverzendingeindeafrekening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="deadlineindiening">Deadlineindiening</span>
          </dt>
          <dd>
            {subsidieEntity.deadlineindiening ? (
              <TextFormat value={subsidieEntity.deadlineindiening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="doelstelling">Doelstelling</span>
          </dt>
          <dd>{subsidieEntity.doelstelling}</dd>
          <dt>
            <span id="gerealiseerdeprojectkosten">Gerealiseerdeprojectkosten</span>
          </dt>
          <dd>
            {subsidieEntity.gerealiseerdeprojectkosten ? (
              <TextFormat value={subsidieEntity.gerealiseerdeprojectkosten} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="hoogtesubsidie">Hoogtesubsidie</span>
          </dt>
          <dd>{subsidieEntity.hoogtesubsidie}</dd>
          <dt>
            <span id="niveau">Niveau</span>
          </dt>
          <dd>{subsidieEntity.niveau}</dd>
          <dt>
            <span id="onderwerp">Onderwerp</span>
          </dt>
          <dd>{subsidieEntity.onderwerp}</dd>
          <dt>
            <span id="ontvangenbedrag">Ontvangenbedrag</span>
          </dt>
          <dd>{subsidieEntity.ontvangenbedrag}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{subsidieEntity.opmerkingen}</dd>
          <dt>
            <span id="opmerkingenvoorschotten">Opmerkingenvoorschotten</span>
          </dt>
          <dd>{subsidieEntity.opmerkingenvoorschotten}</dd>
          <dt>
            <span id="prestatiesubsidie">Prestatiesubsidie</span>
          </dt>
          <dd>{subsidieEntity.prestatiesubsidie}</dd>
          <dt>
            <span id="socialreturnbedrag">Socialreturnbedrag</span>
          </dt>
          <dd>{subsidieEntity.socialreturnbedrag}</dd>
          <dt>
            <span id="socialreturnnagekomen">Socialreturnnagekomen</span>
          </dt>
          <dd>{subsidieEntity.socialreturnnagekomen}</dd>
          <dt>
            <span id="socialreturnverplichting">Socialreturnverplichting</span>
          </dt>
          <dd>{subsidieEntity.socialreturnverplichting}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{subsidieEntity.status}</dd>
          <dt>
            <span id="subsidiebedrag">Subsidiebedrag</span>
          </dt>
          <dd>{subsidieEntity.subsidiebedrag}</dd>
          <dt>
            <span id="subsidiesoort">Subsidiesoort</span>
          </dt>
          <dd>{subsidieEntity.subsidiesoort}</dd>
          <dt>
            <span id="subsidievaststellingbedrag">Subsidievaststellingbedrag</span>
          </dt>
          <dd>{subsidieEntity.subsidievaststellingbedrag}</dd>
          <dt>
            <span id="uitgaandesubsidie">Uitgaandesubsidie</span>
          </dt>
          <dd>{subsidieEntity.uitgaandesubsidie}</dd>
          <dt>
            <span id="verantwoordenop">Verantwoordenop</span>
          </dt>
          <dd>
            {subsidieEntity.verantwoordenop ? (
              <TextFormat value={subsidieEntity.verantwoordenop} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Heeft Zaak</dt>
          <dd>{subsidieEntity.heeftZaak ? subsidieEntity.heeftZaak.id : ''}</dd>
          <dt>Valtbinnen Sector</dt>
          <dd>{subsidieEntity.valtbinnenSector ? subsidieEntity.valtbinnenSector.id : ''}</dd>
          <dt>Behandelaar Medewerker</dt>
          <dd>{subsidieEntity.behandelaarMedewerker ? subsidieEntity.behandelaarMedewerker.id : ''}</dd>
          <dt>Verstrekker Rechtspersoon</dt>
          <dd>{subsidieEntity.verstrekkerRechtspersoon ? subsidieEntity.verstrekkerRechtspersoon.id : ''}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>{subsidieEntity.heeftKostenplaats ? subsidieEntity.heeftKostenplaats.id : ''}</dd>
          <dt>Heeft Document</dt>
          <dd>{subsidieEntity.heeftDocument ? subsidieEntity.heeftDocument.id : ''}</dd>
          <dt>Aanvrager Rechtspersoon</dt>
          <dd>{subsidieEntity.aanvragerRechtspersoon ? subsidieEntity.aanvragerRechtspersoon.id : ''}</dd>
          <dt>Aanvrager Medewerker</dt>
          <dd>{subsidieEntity.aanvragerMedewerker ? subsidieEntity.aanvragerMedewerker.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/subsidie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subsidie/${subsidieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubsidieDetail;
