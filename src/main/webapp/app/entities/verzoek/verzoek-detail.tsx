import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verzoek.reducer';

export const VerzoekDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verzoekEntity = useAppSelector(state => state.verzoek.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verzoekDetailsHeading">Verzoek</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verzoekEntity.id}</dd>
          <dt>
            <span id="akkoordverklaring">Akkoordverklaring</span>
          </dt>
          <dd>{verzoekEntity.akkoordverklaring ? 'true' : 'false'}</dd>
          <dt>
            <span id="ambtshalve">Ambtshalve</span>
          </dt>
          <dd>{verzoekEntity.ambtshalve ? 'true' : 'false'}</dd>
          <dt>
            <span id="datumindiening">Datumindiening</span>
          </dt>
          <dd>
            {verzoekEntity.datumindiening ? (
              <TextFormat value={verzoekEntity.datumindiening} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="doel">Doel</span>
          </dt>
          <dd>{verzoekEntity.doel}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{verzoekEntity.naam}</dd>
          <dt>
            <span id="referentieaanvrager">Referentieaanvrager</span>
          </dt>
          <dd>{verzoekEntity.referentieaanvrager}</dd>
          <dt>
            <span id="toelichtinglateraantelevereninformatie">Toelichtinglateraantelevereninformatie</span>
          </dt>
          <dd>{verzoekEntity.toelichtinglateraantelevereninformatie}</dd>
          <dt>
            <span id="toelichtingnietaantelevereninformatie">Toelichtingnietaantelevereninformatie</span>
          </dt>
          <dd>{verzoekEntity.toelichtingnietaantelevereninformatie}</dd>
          <dt>
            <span id="toelichtingverzoek">Toelichtingverzoek</span>
          </dt>
          <dd>{verzoekEntity.toelichtingverzoek}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{verzoekEntity.type}</dd>
          <dt>
            <span id="verzoeknummer">Verzoeknummer</span>
          </dt>
          <dd>{verzoekEntity.verzoeknummer}</dd>
          <dt>
            <span id="volgnummer">Volgnummer</span>
          </dt>
          <dd>{verzoekEntity.volgnummer}</dd>
          <dt>Leidttot Zaak</dt>
          <dd>{verzoekEntity.leidttotZaak ? verzoekEntity.leidttotZaak.id : ''}</dd>
          <dt>Betrefteerderverzoek Verzoek</dt>
          <dd>{verzoekEntity.betrefteerderverzoekVerzoek ? verzoekEntity.betrefteerderverzoekVerzoek.id : ''}</dd>
          <dt>Betreft Projectactiviteit</dt>
          <dd>
            {verzoekEntity.betreftProjectactiviteits
              ? verzoekEntity.betreftProjectactiviteits.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {verzoekEntity.betreftProjectactiviteits && i === verzoekEntity.betreftProjectactiviteits.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Betreft Projectlocatie</dt>
          <dd>
            {verzoekEntity.betreftProjectlocaties
              ? verzoekEntity.betreftProjectlocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {verzoekEntity.betreftProjectlocaties && i === verzoekEntity.betreftProjectlocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Betreft Activiteit</dt>
          <dd>
            {verzoekEntity.betreftActiviteits
              ? verzoekEntity.betreftActiviteits.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {verzoekEntity.betreftActiviteits && i === verzoekEntity.betreftActiviteits.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Betreft Locatie</dt>
          <dd>
            {verzoekEntity.betreftLocaties
              ? verzoekEntity.betreftLocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {verzoekEntity.betreftLocaties && i === verzoekEntity.betreftLocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeftalsverantwoordelijke Initiatiefnemer</dt>
          <dd>{verzoekEntity.heeftalsverantwoordelijkeInitiatiefnemer ? verzoekEntity.heeftalsverantwoordelijkeInitiatiefnemer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/verzoek" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verzoek/${verzoekEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerzoekDetail;
