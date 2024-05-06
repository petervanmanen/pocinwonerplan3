import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './inkomensvoorzieningsoort.reducer';

export const InkomensvoorzieningsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const inkomensvoorzieningsoortEntity = useAppSelector(state => state.inkomensvoorzieningsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="inkomensvoorzieningsoortDetailsHeading">Inkomensvoorzieningsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.code}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.omschrijving}</dd>
          <dt>
            <span id="regeling">Regeling</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.regeling}</dd>
          <dt>
            <span id="regelingcode">Regelingcode</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.regelingcode}</dd>
          <dt>
            <span id="vergoeding">Vergoeding</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.vergoeding}</dd>
          <dt>
            <span id="vergoedingscode">Vergoedingscode</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.vergoedingscode}</dd>
          <dt>
            <span id="wet">Wet</span>
          </dt>
          <dd>{inkomensvoorzieningsoortEntity.wet}</dd>
        </dl>
        <Button tag={Link} to="/inkomensvoorzieningsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/inkomensvoorzieningsoort/${inkomensvoorzieningsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InkomensvoorzieningsoortDetail;
