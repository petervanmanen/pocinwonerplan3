import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './omgevingswaarderegel.reducer';

export const OmgevingswaarderegelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const omgevingswaarderegelEntity = useAppSelector(state => state.omgevingswaarderegel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="omgevingswaarderegelDetailsHeading">Omgevingswaarderegel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{omgevingswaarderegelEntity.id}</dd>
          <dt>
            <span id="groep">Groep</span>
          </dt>
          <dd>{omgevingswaarderegelEntity.groep}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{omgevingswaarderegelEntity.naam}</dd>
          <dt>Beschrijft Omgevingsnorm</dt>
          <dd>
            {omgevingswaarderegelEntity.beschrijftOmgevingsnorms
              ? omgevingswaarderegelEntity.beschrijftOmgevingsnorms.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {omgevingswaarderegelEntity.beschrijftOmgevingsnorms &&
                    i === omgevingswaarderegelEntity.beschrijftOmgevingsnorms.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Beschrijft Omgevingswaarde</dt>
          <dd>
            {omgevingswaarderegelEntity.beschrijftOmgevingswaardes
              ? omgevingswaarderegelEntity.beschrijftOmgevingswaardes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {omgevingswaarderegelEntity.beschrijftOmgevingswaardes &&
                    i === omgevingswaarderegelEntity.beschrijftOmgevingswaardes.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/omgevingswaarderegel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/omgevingswaarderegel/${omgevingswaarderegelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OmgevingswaarderegelDetail;
