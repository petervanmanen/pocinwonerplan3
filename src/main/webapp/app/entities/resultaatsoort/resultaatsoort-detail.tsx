import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './resultaatsoort.reducer';

export const ResultaatsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const resultaatsoortEntity = useAppSelector(state => state.resultaatsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="resultaatsoortDetailsHeading">Resultaatsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{resultaatsoortEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{resultaatsoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{resultaatsoortEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/resultaatsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resultaatsoort/${resultaatsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResultaatsoortDetail;
