import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bijzonderheid.reducer';

export const BijzonderheidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bijzonderheidEntity = useAppSelector(state => state.bijzonderheid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bijzonderheidDetailsHeading">Bijzonderheid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bijzonderheidEntity.id}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{bijzonderheidEntity.omschrijving}</dd>
          <dt>Isvansoort Bijzonderheidsoort</dt>
          <dd>{bijzonderheidEntity.isvansoortBijzonderheidsoort ? bijzonderheidEntity.isvansoortBijzonderheidsoort.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/bijzonderheid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bijzonderheid/${bijzonderheidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BijzonderheidDetail;
