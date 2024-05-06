import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './soortspoor.reducer';

export const SoortspoorDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const soortspoorEntity = useAppSelector(state => state.soortspoor.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="soortspoorDetailsHeading">Soortspoor</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{soortspoorEntity.id}</dd>
          <dt>
            <span id="functiespoor">Functiespoor</span>
          </dt>
          <dd>{soortspoorEntity.functiespoor}</dd>
          <dt>
            <span id="indicatieplusbrpopulatie">Indicatieplusbrpopulatie</span>
          </dt>
          <dd>{soortspoorEntity.indicatieplusbrpopulatie}</dd>
        </dl>
        <Button tag={Link} to="/soortspoor" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/soortspoor/${soortspoorEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SoortspoorDetail;
