import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './begroting.reducer';

export const BegrotingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const begrotingEntity = useAppSelector(state => state.begroting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="begrotingDetailsHeading">Begroting</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{begrotingEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{begrotingEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{begrotingEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{begrotingEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/begroting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/begroting/${begrotingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BegrotingDetail;
