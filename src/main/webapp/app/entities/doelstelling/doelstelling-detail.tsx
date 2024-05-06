import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doelstelling.reducer';

export const DoelstellingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doelstellingEntity = useAppSelector(state => state.doelstelling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doelstellingDetailsHeading">Doelstelling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{doelstellingEntity.id}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{doelstellingEntity.omschrijving}</dd>
          <dt>Isvansoort Doelstellingsoort</dt>
          <dd>{doelstellingEntity.isvansoortDoelstellingsoort ? doelstellingEntity.isvansoortDoelstellingsoort.id : ''}</dd>
          <dt>Heeft Hoofdstuk</dt>
          <dd>{doelstellingEntity.heeftHoofdstuk ? doelstellingEntity.heeftHoofdstuk.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/doelstelling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doelstelling/${doelstellingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoelstellingDetail;
