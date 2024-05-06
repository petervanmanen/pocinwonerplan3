import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './index.reducer';

export const IndexDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const indexEntity = useAppSelector(state => state.index.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="indexDetailsHeading">Index</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{indexEntity.id}</dd>
          <dt>
            <span id="indexnaam">Indexnaam</span>
          </dt>
          <dd>{indexEntity.indexnaam}</dd>
          <dt>
            <span id="indexwaarde">Indexwaarde</span>
          </dt>
          <dd>{indexEntity.indexwaarde}</dd>
        </dl>
        <Button tag={Link} to="/index" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/index/${indexEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IndexDetail;
