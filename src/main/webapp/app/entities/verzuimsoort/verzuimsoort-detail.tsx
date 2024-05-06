import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verzuimsoort.reducer';

export const VerzuimsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verzuimsoortEntity = useAppSelector(state => state.verzuimsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verzuimsoortDetailsHeading">Verzuimsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verzuimsoortEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{verzuimsoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{verzuimsoortEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/verzuimsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verzuimsoort/${verzuimsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerzuimsoortDetail;
