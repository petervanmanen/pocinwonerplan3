import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fietsregistratie.reducer';

export const FietsregistratieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fietsregistratieEntity = useAppSelector(state => state.fietsregistratie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fietsregistratieDetailsHeading">Fietsregistratie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fietsregistratieEntity.id}</dd>
          <dt>
            <span id="gelabeld">Gelabeld</span>
          </dt>
          <dd>{fietsregistratieEntity.gelabeld}</dd>
          <dt>
            <span id="verwijderd">Verwijderd</span>
          </dt>
          <dd>{fietsregistratieEntity.verwijderd}</dd>
        </dl>
        <Button tag={Link} to="/fietsregistratie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fietsregistratie/${fietsregistratieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FietsregistratieDetail;
