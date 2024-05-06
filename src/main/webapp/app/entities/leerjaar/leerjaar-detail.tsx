import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leerjaar.reducer';

export const LeerjaarDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leerjaarEntity = useAppSelector(state => state.leerjaar.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leerjaarDetailsHeading">Leerjaar</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leerjaarEntity.id}</dd>
          <dt>
            <span id="jaareinde">Jaareinde</span>
          </dt>
          <dd>{leerjaarEntity.jaareinde}</dd>
          <dt>
            <span id="jaarstart">Jaarstart</span>
          </dt>
          <dd>{leerjaarEntity.jaarstart}</dd>
        </dl>
        <Button tag={Link} to="/leerjaar" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leerjaar/${leerjaarEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeerjaarDetail;
