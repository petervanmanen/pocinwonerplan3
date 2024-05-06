import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './soortoverigbouwwerk.reducer';

export const SoortoverigbouwwerkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const soortoverigbouwwerkEntity = useAppSelector(state => state.soortoverigbouwwerk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="soortoverigbouwwerkDetailsHeading">Soortoverigbouwwerk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{soortoverigbouwwerkEntity.id}</dd>
          <dt>
            <span id="indicatieplusbrpopulatie">Indicatieplusbrpopulatie</span>
          </dt>
          <dd>{soortoverigbouwwerkEntity.indicatieplusbrpopulatie}</dd>
          <dt>
            <span id="typeoverigbouwwerk">Typeoverigbouwwerk</span>
          </dt>
          <dd>{soortoverigbouwwerkEntity.typeoverigbouwwerk}</dd>
        </dl>
        <Button tag={Link} to="/soortoverigbouwwerk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/soortoverigbouwwerk/${soortoverigbouwwerkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SoortoverigbouwwerkDetail;
