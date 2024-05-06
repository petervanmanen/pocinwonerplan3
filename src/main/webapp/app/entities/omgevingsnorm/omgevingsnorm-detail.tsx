import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './omgevingsnorm.reducer';

export const OmgevingsnormDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const omgevingsnormEntity = useAppSelector(state => state.omgevingsnorm.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="omgevingsnormDetailsHeading">Omgevingsnorm</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{omgevingsnormEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{omgevingsnormEntity.naam}</dd>
          <dt>
            <span id="omgevingsnormgroep">Omgevingsnormgroep</span>
          </dt>
          <dd>{omgevingsnormEntity.omgevingsnormgroep}</dd>
          <dt>Beschrijft Omgevingswaarderegel</dt>
          <dd>
            {omgevingsnormEntity.beschrijftOmgevingswaarderegels
              ? omgevingsnormEntity.beschrijftOmgevingswaarderegels.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {omgevingsnormEntity.beschrijftOmgevingswaarderegels &&
                    i === omgevingsnormEntity.beschrijftOmgevingswaarderegels.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/omgevingsnorm" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/omgevingsnorm/${omgevingsnormEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OmgevingsnormDetail;
