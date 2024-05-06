import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gemeente.reducer';

export const GemeenteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gemeenteEntity = useAppSelector(state => state.gemeente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gemeenteDetailsHeading">Gemeente</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gemeenteEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>
            {gemeenteEntity.datumbegingeldigheid ? (
              <TextFormat value={gemeenteEntity.datumbegingeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {gemeenteEntity.datumeinde ? <TextFormat value={gemeenteEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {gemeenteEntity.datumeindegeldigheid ? (
              <TextFormat value={gemeenteEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumingang">Datumingang</span>
          </dt>
          <dd>
            {gemeenteEntity.datumingang ? (
              <TextFormat value={gemeenteEntity.datumingang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geconstateerd">Geconstateerd</span>
          </dt>
          <dd>{gemeenteEntity.geconstateerd ? 'true' : 'false'}</dd>
          <dt>
            <span id="gemeentecode">Gemeentecode</span>
          </dt>
          <dd>{gemeenteEntity.gemeentecode}</dd>
          <dt>
            <span id="gemeentenaam">Gemeentenaam</span>
          </dt>
          <dd>{gemeenteEntity.gemeentenaam}</dd>
          <dt>
            <span id="gemeentenaamnen">Gemeentenaamnen</span>
          </dt>
          <dd>{gemeenteEntity.gemeentenaamnen}</dd>
          <dt>
            <span id="geometrie">Geometrie</span>
          </dt>
          <dd>{gemeenteEntity.geometrie}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{gemeenteEntity.identificatie}</dd>
          <dt>
            <span id="inonderzoek">Inonderzoek</span>
          </dt>
          <dd>{gemeenteEntity.inonderzoek ? 'true' : 'false'}</dd>
          <dt>
            <span id="versie">Versie</span>
          </dt>
          <dd>{gemeenteEntity.versie}</dd>
        </dl>
        <Button tag={Link} to="/gemeente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gemeente/${gemeenteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GemeenteDetail;
