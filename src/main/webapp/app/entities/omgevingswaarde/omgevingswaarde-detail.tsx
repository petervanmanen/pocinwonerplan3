import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './omgevingswaarde.reducer';

export const OmgevingswaardeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const omgevingswaardeEntity = useAppSelector(state => state.omgevingswaarde.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="omgevingswaardeDetailsHeading">Omgevingswaarde</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{omgevingswaardeEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{omgevingswaardeEntity.naam}</dd>
          <dt>
            <span id="omgevingswaardegroep">Omgevingswaardegroep</span>
          </dt>
          <dd>{omgevingswaardeEntity.omgevingswaardegroep}</dd>
          <dt>Beschrijft Omgevingswaarderegel</dt>
          <dd>
            {omgevingswaardeEntity.beschrijftOmgevingswaarderegels
              ? omgevingswaardeEntity.beschrijftOmgevingswaarderegels.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {omgevingswaardeEntity.beschrijftOmgevingswaarderegels &&
                    i === omgevingswaardeEntity.beschrijftOmgevingswaarderegels.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/omgevingswaarde" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/omgevingswaarde/${omgevingswaardeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OmgevingswaardeDetail;
