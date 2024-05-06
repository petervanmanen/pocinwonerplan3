import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './waterdeel.reducer';

export const WaterdeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const waterdeelEntity = useAppSelector(state => state.waterdeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="waterdeelDetailsHeading">Waterdeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{waterdeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidwaterdeel">Datumbegingeldigheidwaterdeel</span>
          </dt>
          <dd>
            {waterdeelEntity.datumbegingeldigheidwaterdeel ? (
              <TextFormat value={waterdeelEntity.datumbegingeldigheidwaterdeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidwaterdeel">Datumeindegeldigheidwaterdeel</span>
          </dt>
          <dd>
            {waterdeelEntity.datumeindegeldigheidwaterdeel ? (
              <TextFormat value={waterdeelEntity.datumeindegeldigheidwaterdeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geometriewaterdeel">Geometriewaterdeel</span>
          </dt>
          <dd>{waterdeelEntity.geometriewaterdeel}</dd>
          <dt>
            <span id="identificatiewaterdeel">Identificatiewaterdeel</span>
          </dt>
          <dd>{waterdeelEntity.identificatiewaterdeel}</dd>
          <dt>
            <span id="plustypewaterdeel">Plustypewaterdeel</span>
          </dt>
          <dd>{waterdeelEntity.plustypewaterdeel}</dd>
          <dt>
            <span id="relatievehoogteliggingwaterdeel">Relatievehoogteliggingwaterdeel</span>
          </dt>
          <dd>{waterdeelEntity.relatievehoogteliggingwaterdeel}</dd>
          <dt>
            <span id="statuswaterdeel">Statuswaterdeel</span>
          </dt>
          <dd>{waterdeelEntity.statuswaterdeel}</dd>
          <dt>
            <span id="typewaterdeel">Typewaterdeel</span>
          </dt>
          <dd>{waterdeelEntity.typewaterdeel}</dd>
        </dl>
        <Button tag={Link} to="/waterdeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/waterdeel/${waterdeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WaterdeelDetail;
