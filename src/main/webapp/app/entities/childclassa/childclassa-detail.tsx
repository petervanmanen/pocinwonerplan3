import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './childclassa.reducer';

export const ChildclassaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const childclassaEntity = useAppSelector(state => state.childclassa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="childclassaDetailsHeading">Childclassa</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{childclassaEntity.id}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{childclassaEntity.kleur}</dd>
        </dl>
        <Button tag={Link} to="/childclassa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/childclassa/${childclassaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChildclassaDetail;
