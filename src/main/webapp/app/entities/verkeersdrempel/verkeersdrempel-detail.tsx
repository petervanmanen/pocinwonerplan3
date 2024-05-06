import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verkeersdrempel.reducer';

export const VerkeersdrempelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verkeersdrempelEntity = useAppSelector(state => state.verkeersdrempel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verkeersdrempelDetailsHeading">Verkeersdrempel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verkeersdrempelEntity.id}</dd>
          <dt>
            <span id="ontwerpsnelheid">Ontwerpsnelheid</span>
          </dt>
          <dd>{verkeersdrempelEntity.ontwerpsnelheid}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{verkeersdrempelEntity.type}</dd>
          <dt>
            <span id="typeplus">Typeplus</span>
          </dt>
          <dd>{verkeersdrempelEntity.typeplus}</dd>
        </dl>
        <Button tag={Link} to="/verkeersdrempel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verkeersdrempel/${verkeersdrempelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerkeersdrempelDetail;
