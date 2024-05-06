import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ondersteunendwegdeel.reducer';

export const OndersteunendwegdeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ondersteunendwegdeelEntity = useAppSelector(state => state.ondersteunendwegdeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ondersteunendwegdeelDetailsHeading">Ondersteunendwegdeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidondersteunendwegdeel">Datumbegingeldigheidondersteunendwegdeel</span>
          </dt>
          <dd>
            {ondersteunendwegdeelEntity.datumbegingeldigheidondersteunendwegdeel ? (
              <TextFormat
                value={ondersteunendwegdeelEntity.datumbegingeldigheidondersteunendwegdeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidondersteunendwegdeel">Datumeindegeldigheidondersteunendwegdeel</span>
          </dt>
          <dd>
            {ondersteunendwegdeelEntity.datumeindegeldigheidondersteunendwegdeel ? (
              <TextFormat
                value={ondersteunendwegdeelEntity.datumeindegeldigheidondersteunendwegdeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="functieondersteunendwegdeel">Functieondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.functieondersteunendwegdeel}</dd>
          <dt>
            <span id="fysiekvoorkomenondersteunendwegdeel">Fysiekvoorkomenondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.fysiekvoorkomenondersteunendwegdeel}</dd>
          <dt>
            <span id="geometrieondersteunendwegdeel">Geometrieondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.geometrieondersteunendwegdeel}</dd>
          <dt>
            <span id="identificatieondersteunendwegdeel">Identificatieondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.identificatieondersteunendwegdeel}</dd>
          <dt>
            <span id="kruinlijngeometrieondersteunendwegdeel">Kruinlijngeometrieondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.kruinlijngeometrieondersteunendwegdeel}</dd>
          <dt>
            <span id="lod0geometrieondersteunendwegdeel">Lod 0 Geometrieondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.lod0geometrieondersteunendwegdeel}</dd>
          <dt>
            <span id="ondersteunendwegdeeloptalud">Ondersteunendwegdeeloptalud</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.ondersteunendwegdeeloptalud}</dd>
          <dt>
            <span id="plusfunctieondersteunendwegdeel">Plusfunctieondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.plusfunctieondersteunendwegdeel}</dd>
          <dt>
            <span id="plusfysiekvoorkomenondersteunendwegdeel">Plusfysiekvoorkomenondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.plusfysiekvoorkomenondersteunendwegdeel}</dd>
          <dt>
            <span id="relatievehoogteliggingondersteunendwegdeel">Relatievehoogteliggingondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.relatievehoogteliggingondersteunendwegdeel}</dd>
          <dt>
            <span id="statusondersteunendwegdeel">Statusondersteunendwegdeel</span>
          </dt>
          <dd>{ondersteunendwegdeelEntity.statusondersteunendwegdeel}</dd>
        </dl>
        <Button tag={Link} to="/ondersteunendwegdeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ondersteunendwegdeel/${ondersteunendwegdeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OndersteunendwegdeelDetail;
