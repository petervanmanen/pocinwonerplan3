import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tunneldeel.reducer';

export const TunneldeelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tunneldeelEntity = useAppSelector(state => state.tunneldeel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tunneldeelDetailsHeading">Tunneldeel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tunneldeelEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidtunneldeel">Datumbegingeldigheidtunneldeel</span>
          </dt>
          <dd>
            {tunneldeelEntity.datumbegingeldigheidtunneldeel ? (
              <TextFormat value={tunneldeelEntity.datumbegingeldigheidtunneldeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldigheidtunneldeel">Datumeindegeldigheidtunneldeel</span>
          </dt>
          <dd>
            {tunneldeelEntity.datumeindegeldigheidtunneldeel ? (
              <TextFormat value={tunneldeelEntity.datumeindegeldigheidtunneldeel} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="geometrietunneldeel">Geometrietunneldeel</span>
          </dt>
          <dd>{tunneldeelEntity.geometrietunneldeel}</dd>
          <dt>
            <span id="identificatietunneldeel">Identificatietunneldeel</span>
          </dt>
          <dd>{tunneldeelEntity.identificatietunneldeel}</dd>
          <dt>
            <span id="lod0geometrietunneldeel">Lod 0 Geometrietunneldeel</span>
          </dt>
          <dd>{tunneldeelEntity.lod0geometrietunneldeel}</dd>
          <dt>
            <span id="relatievehoogteliggingtunneldeel">Relatievehoogteliggingtunneldeel</span>
          </dt>
          <dd>{tunneldeelEntity.relatievehoogteliggingtunneldeel}</dd>
          <dt>
            <span id="statustunneldeel">Statustunneldeel</span>
          </dt>
          <dd>{tunneldeelEntity.statustunneldeel}</dd>
        </dl>
        <Button tag={Link} to="/tunneldeel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tunneldeel/${tunneldeelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TunneldeelDetail;
