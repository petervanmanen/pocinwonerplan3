import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './uitstroomredensoort.reducer';

export const UitstroomredensoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const uitstroomredensoortEntity = useAppSelector(state => state.uitstroomredensoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="uitstroomredensoortDetailsHeading">Uitstroomredensoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{uitstroomredensoortEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{uitstroomredensoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{uitstroomredensoortEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/uitstroomredensoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/uitstroomredensoort/${uitstroomredensoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default UitstroomredensoortDetail;
