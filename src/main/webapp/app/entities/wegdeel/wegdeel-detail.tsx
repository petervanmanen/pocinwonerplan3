import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wegdeel.reducer';

export const WegdeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wegdeelEntity = useAppSelector(state => state.wegdeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wegdeelDetailsHeading">Wegdeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{wegdeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidwegdeel">Datumbegingeldigheidwegdeel</span>
          </dt>
          <dd>
            {wegdeelEntity.datumbegingeldigheidwegdeel ? (
              <TextFormat value={wegdeelEntity.datumbegingeldigheidwegdeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidwegdeel">Datumeindegeldigheidwegdeel</span>
          </dt>
          <dd>
            {wegdeelEntity.datumeindegeldigheidwegdeel ? (
              <TextFormat value={wegdeelEntity.datumeindegeldigheidwegdeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="functiewegdeel">Functiewegdeel</span>
          </dt>
          <dd>{wegdeelEntity.functiewegdeel}</dd>
          <dt>
            <span id="fysiekvoorkomenwegdeel">Fysiekvoorkomenwegdeel</span>
          </dt>
          <dd>{wegdeelEntity.fysiekvoorkomenwegdeel}</dd>
          <dt>
            <span id="geometriewegdeel">Geometriewegdeel</span>
          </dt>
          <dd>{wegdeelEntity.geometriewegdeel}</dd>
          <dt>
            <span id="identificatiewegdeel">Identificatiewegdeel</span>
          </dt>
          <dd>{wegdeelEntity.identificatiewegdeel}</dd>
          <dt>
            <span id="kruinlijngeometriewegdeel">Kruinlijngeometriewegdeel</span>
          </dt>
          <dd>{wegdeelEntity.kruinlijngeometriewegdeel}</dd>
          <dt>
            <span id="lod0geometriewegdeel">Lod 0 Geometriewegdeel</span>
          </dt>
          <dd>{wegdeelEntity.lod0geometriewegdeel}</dd>
          <dt>
            <span id="plusfunctiewegdeel">Plusfunctiewegdeel</span>
          </dt>
          <dd>{wegdeelEntity.plusfunctiewegdeel}</dd>
          <dt>
            <span id="plusfysiekvoorkomenwegdeel">Plusfysiekvoorkomenwegdeel</span>
          </dt>
          <dd>{wegdeelEntity.plusfysiekvoorkomenwegdeel}</dd>
          <dt>
            <span id="relatievehoogteliggingwegdeel">Relatievehoogteliggingwegdeel</span>
          </dt>
          <dd>{wegdeelEntity.relatievehoogteliggingwegdeel}</dd>
          <dt>
            <span id="statuswegdeel">Statuswegdeel</span>
          </dt>
          <dd>{wegdeelEntity.statuswegdeel}</dd>
          <dt>
            <span id="wegdeeloptalud">Wegdeeloptalud</span>
          </dt>
          <dd>{wegdeelEntity.wegdeeloptalud}</dd>
          <dt>Betreft Stremming</dt>
          <dd>
            {wegdeelEntity.betreftStremmings
              ? wegdeelEntity.betreftStremmings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {wegdeelEntity.betreftStremmings && i === wegdeelEntity.betreftStremmings.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/wegdeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wegdeel/${wegdeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WegdeelDetail;
