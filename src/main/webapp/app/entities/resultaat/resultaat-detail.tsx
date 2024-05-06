import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './resultaat.reducer';

export const ResultaatDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const resultaatEntity = useAppSelector(state => state.resultaat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="resultaatDetailsHeading">Resultaat</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{resultaatEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{resultaatEntity.datum}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{resultaatEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{resultaatEntity.omschrijving}</dd>
          <dt>Soortresultaat Resultaatsoort</dt>
          <dd>{resultaatEntity.soortresultaatResultaatsoort ? resultaatEntity.soortresultaatResultaatsoort.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/resultaat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resultaat/${resultaatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResultaatDetail;
