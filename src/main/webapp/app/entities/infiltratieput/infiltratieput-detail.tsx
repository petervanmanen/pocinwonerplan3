import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './infiltratieput.reducer';

export const InfiltratieputDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const infiltratieputEntity = useAppSelector(state => state.infiltratieput.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="infiltratieputDetailsHeading">Infiltratieput</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{infiltratieputEntity.id}</dd>
          <dt>
            <span id="porositeit">Porositeit</span>
          </dt>
          <dd>{infiltratieputEntity.porositeit}</dd>
          <dt>
            <span id="risicogebied">Risicogebied</span>
          </dt>
          <dd>{infiltratieputEntity.risicogebied}</dd>
        </dl>
        <Button tag={Link} to="/infiltratieput" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/infiltratieput/${infiltratieputEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InfiltratieputDetail;