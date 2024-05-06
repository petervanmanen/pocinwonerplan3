import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doelstellingsoort.reducer';

export const DoelstellingsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doelstellingsoortEntity = useAppSelector(state => state.doelstellingsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doelstellingsoortDetailsHeading">Doelstellingsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{doelstellingsoortEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{doelstellingsoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{doelstellingsoortEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/doelstellingsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doelstellingsoort/${doelstellingsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoelstellingsoortDetail;
