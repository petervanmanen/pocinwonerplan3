import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './onbegroeidterreindeel.reducer';

export const OnbegroeidterreindeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const onbegroeidterreindeelEntity = useAppSelector(state => state.onbegroeidterreindeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="onbegroeidterreindeelDetailsHeading">Onbegroeidterreindeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidonbegroeidterreindeel">Datumbegingeldigheidonbegroeidterreindeel</span>
          </dt>
          <dd>
            {onbegroeidterreindeelEntity.datumbegingeldigheidonbegroeidterreindeel ? (
              <TextFormat
                value={onbegroeidterreindeelEntity.datumbegingeldigheidonbegroeidterreindeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidonbegroeidterreindeel">Datumeindegeldigheidonbegroeidterreindeel</span>
          </dt>
          <dd>
            {onbegroeidterreindeelEntity.datumeindegeldigheidonbegroeidterreindeel ? (
              <TextFormat
                value={onbegroeidterreindeelEntity.datumeindegeldigheidonbegroeidterreindeel}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="fysiekvoorkomenonbegroeidterreindeel">Fysiekvoorkomenonbegroeidterreindeel</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.fysiekvoorkomenonbegroeidterreindeel}</dd>
          <dt>
            <span id="geometrieonbegroeidterreindeel">Geometrieonbegroeidterreindeel</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.geometrieonbegroeidterreindeel}</dd>
          <dt>
            <span id="identificatieonbegroeidterreindeel">Identificatieonbegroeidterreindeel</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.identificatieonbegroeidterreindeel}</dd>
          <dt>
            <span id="kruinlijngeometrieonbegroeidterreindeel">Kruinlijngeometrieonbegroeidterreindeel</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.kruinlijngeometrieonbegroeidterreindeel}</dd>
          <dt>
            <span id="onbegroeidterreindeeloptalud">Onbegroeidterreindeeloptalud</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.onbegroeidterreindeeloptalud}</dd>
          <dt>
            <span id="plusfysiekvoorkomenonbegroeidterreindeel">Plusfysiekvoorkomenonbegroeidterreindeel</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.plusfysiekvoorkomenonbegroeidterreindeel}</dd>
          <dt>
            <span id="relatievehoogteliggingonbegroeidterreindeel">Relatievehoogteliggingonbegroeidterreindeel</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.relatievehoogteliggingonbegroeidterreindeel}</dd>
          <dt>
            <span id="statusonbegroeidterreindeel">Statusonbegroeidterreindeel</span>
          </dt>
          <dd>{onbegroeidterreindeelEntity.statusonbegroeidterreindeel}</dd>
        </dl>
        <Button tag={Link} to="/onbegroeidterreindeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/onbegroeidterreindeel/${onbegroeidterreindeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OnbegroeidterreindeelDetail;
