import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './putdeksel.reducer';

export const PutdekselDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const putdekselEntity = useAppSelector(state => state.putdeksel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="putdekselDetailsHeading">Putdeksel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{putdekselEntity.id}</dd>
          <dt>
            <span id="diameter">Diameter</span>
          </dt>
          <dd>{putdekselEntity.diameter}</dd>
          <dt>
            <span id="put">Put</span>
          </dt>
          <dd>{putdekselEntity.put}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{putdekselEntity.type}</dd>
          <dt>
            <span id="vorm">Vorm</span>
          </dt>
          <dd>{putdekselEntity.vorm}</dd>
        </dl>
        <Button tag={Link} to="/putdeksel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/putdeksel/${putdekselEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PutdekselDetail;
