import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kadastraleonroerendezaak.reducer';

export const KadastraleonroerendezaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kadastraleonroerendezaakEntity = useAppSelector(state => state.kadastraleonroerendezaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kadastraleonroerendezaakDetailsHeading">Kadastraleonroerendezaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.id}</dd>
          <dt>
            <span id="empty">Empty</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.empty}</dd>
          <dt>
            <span id="appartementsrechtvolgnummer">Appartementsrechtvolgnummer</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.appartementsrechtvolgnummer}</dd>
          <dt>
            <span id="begrenzing">Begrenzing</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.begrenzing}</dd>
          <dt>
            <span id="cultuurcodeonbebouwd">Cultuurcodeonbebouwd</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.cultuurcodeonbebouwd}</dd>
          <dt>
            <span id="datumbegingeldigheidkadastraleonroerendezaak">Datumbegingeldigheidkadastraleonroerendezaak</span>
          </dt>
          <dd>
            {kadastraleonroerendezaakEntity.datumbegingeldigheidkadastraleonroerendezaak ? (
              <TextFormat
                value={kadastraleonroerendezaakEntity.datumbegingeldigheidkadastraleonroerendezaak}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidkadastraleonroerendezaak">Datumeindegeldigheidkadastraleonroerendezaak</span>
          </dt>
          <dd>
            {kadastraleonroerendezaakEntity.datumeindegeldigheidkadastraleonroerendezaak ? (
              <TextFormat
                value={kadastraleonroerendezaakEntity.datumeindegeldigheidkadastraleonroerendezaak}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.identificatie}</dd>
          <dt>
            <span id="kadastralegemeente">Kadastralegemeente</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.kadastralegemeente}</dd>
          <dt>
            <span id="kadastralegemeentecode">Kadastralegemeentecode</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.kadastralegemeentecode}</dd>
          <dt>
            <span id="koopjaar">Koopjaar</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.koopjaar}</dd>
          <dt>
            <span id="koopsom">Koopsom</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.koopsom}</dd>
          <dt>
            <span id="landinrichtingrentebedrag">Landinrichtingrentebedrag</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.landinrichtingrentebedrag}</dd>
          <dt>
            <span id="landinrichtingrenteeindejaar">Landinrichtingrenteeindejaar</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.landinrichtingrenteeindejaar}</dd>
          <dt>
            <span id="ligging">Ligging</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.ligging}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.locatieomschrijving}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.oppervlakte}</dd>
          <dt>
            <span id="oud">Oud</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.oud}</dd>
          <dt>
            <span id="perceelnummer">Perceelnummer</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.perceelnummer}</dd>
          <dt>
            <span id="sectie">Sectie</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.sectie}</dd>
          <dt>
            <span id="valutacode">Valutacode</span>
          </dt>
          <dd>{kadastraleonroerendezaakEntity.valutacode}</dd>
        </dl>
        <Button tag={Link} to="/kadastraleonroerendezaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kadastraleonroerendezaak/${kadastraleonroerendezaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KadastraleonroerendezaakDetail;
