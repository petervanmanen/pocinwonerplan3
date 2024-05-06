import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './woonplaats.reducer';

export const WoonplaatsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const woonplaatsEntity = useAppSelector(state => state.woonplaats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="woonplaatsDetailsHeading">Woonplaats</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{woonplaatsEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {woonplaatsEntity.datumbegingeldigheid ? (
              <TextFormat value={woonplaatsEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {woonplaatsEntity.datumeinde ? (
              <TextFormat value={woonplaatsEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {woonplaatsEntity.datumeindegeldigheid ? (
              <TextFormat value={woonplaatsEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {woonplaatsEntity.datumingang ? (
              <TextFormat value={woonplaatsEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{woonplaatsEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{woonplaatsEntity.geometrie}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{woonplaatsEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{woonplaatsEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{woonplaatsEntity.status}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{woonplaatsEntity.versie}</dd>
          <dt>
            <span id="woonplaatsnaam">Woonplaatsnaam</span>
          </dt>
          <dd>{woonplaatsEntity.woonplaatsnaam}</dd>
          <dt>
            <span id="woonplaatsnaamnen">Woonplaatsnaamnen</span>
          </dt>
          <dd>{woonplaatsEntity.woonplaatsnaamnen}</dd>
        </dl>
        <Button tag={Link} to="/woonplaats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/woonplaats/${woonplaatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WoonplaatsDetail;
