import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './brondocumenten.reducer';

export const BrondocumentenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const brondocumentenEntity = useAppSelector(state => state.brondocumenten.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="brondocumentenDetailsHeading">Brondocumenten</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{brondocumentenEntity.id}</dd>
          <dt>
            <span id="aktegemeente">Aktegemeente</span>
          </dt>
          <dd>{brondocumentenEntity.aktegemeente}</dd>
          <dt>
            <span id="datumdocument">Datumdocument</span>
          </dt>
          <dd>{brondocumentenEntity.datumdocument}</dd>
          <dt>
            <span id="documentgemeente">Documentgemeente</span>
          </dt>
          <dd>{brondocumentenEntity.documentgemeente}</dd>
          <dt>
            <span id="documentidentificatie">Documentidentificatie</span>
          </dt>
          <dd>{brondocumentenEntity.documentidentificatie}</dd>
          <dt>
            <span id="documentomschrijving">Documentomschrijving</span>
          </dt>
          <dd>{brondocumentenEntity.documentomschrijving}</dd>
        </dl>
        <Button tag={Link} to="/brondocumenten" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/brondocumenten/${brondocumentenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BrondocumentenDetail;
