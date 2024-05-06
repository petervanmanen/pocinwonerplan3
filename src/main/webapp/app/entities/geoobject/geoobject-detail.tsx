import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './geoobject.reducer';

export const GeoobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const geoobjectEntity = useAppSelector(state => state.geoobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="geoobjectDetailsHeading">Geoobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{geoobjectEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>{geoobjectEntity.datumbegingeldigheid}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>{geoobjectEntity.datumeindegeldigheid}</dd>
          <dt>
            <span id="geometriesoort">Geometriesoort</span>
          </dt>
          <dd>{geoobjectEntity.geometriesoort}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{geoobjectEntity.identificatie}</dd>
        </dl>
        <Button tag={Link} to="/geoobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/geoobject/${geoobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GeoobjectDetail;
