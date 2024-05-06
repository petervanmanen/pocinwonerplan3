import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './ecomponentsoort.reducer';

export const EcomponentsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ecomponentsoortEntity = useAppSelector(state => state.ecomponentsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ecomponentsoortDetailsHeading">Ecomponentsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ecomponentsoortEntity.id}</dd>
          <dt>
            <span id="ecomponent">Ecomponent</span>
          </dt>
          <dd>{ecomponentsoortEntity.ecomponent}</dd>
          <dt>
            <span id="ecomponentcode">Ecomponentcode</span>
          </dt>
          <dd>{ecomponentsoortEntity.ecomponentcode}</dd>
          <dt>
            <span id="kolom">Kolom</span>
          </dt>
          <dd>{ecomponentsoortEntity.kolom}</dd>
          <dt>
            <span id="kolomcode">Kolomcode</span>
          </dt>
          <dd>{ecomponentsoortEntity.kolomcode}</dd>
          <dt>
            <span id="regeling">Regeling</span>
          </dt>
          <dd>{ecomponentsoortEntity.regeling}</dd>
          <dt>
            <span id="regelingcode">Regelingcode</span>
          </dt>
          <dd>{ecomponentsoortEntity.regelingcode}</dd>
        </dl>
        <Button tag={Link} to="/ecomponentsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/ecomponentsoort/${ecomponentsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EcomponentsoortDetail;
