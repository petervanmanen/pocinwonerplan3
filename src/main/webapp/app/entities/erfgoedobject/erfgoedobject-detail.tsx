import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './erfgoedobject.reducer';

export const ErfgoedobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const erfgoedobjectEntity = useAppSelector(state => state.erfgoedobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="erfgoedobjectDetailsHeading">Erfgoedobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{erfgoedobjectEntity.id}</dd>
          <dt>
            <span id="dateringtot">Dateringtot</span>
          </dt>
          <dd>{erfgoedobjectEntity.dateringtot}</dd>
          <dt>
            <span id="dateringvanaf">Dateringvanaf</span>
          </dt>
          <dd>{erfgoedobjectEntity.dateringvanaf}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{erfgoedobjectEntity.omschrijving}</dd>
          <dt>
            <span id="titel">Titel</span>
          </dt>
          <dd>{erfgoedobjectEntity.titel}</dd>
        </dl>
        <Button tag={Link} to="/erfgoedobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/erfgoedobject/${erfgoedobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ErfgoedobjectDetail;
