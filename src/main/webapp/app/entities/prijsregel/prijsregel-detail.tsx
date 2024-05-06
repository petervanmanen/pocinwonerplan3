import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prijsregel.reducer';

export const PrijsregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const prijsregelEntity = useAppSelector(state => state.prijsregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="prijsregelDetailsHeading">Prijsregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{prijsregelEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{prijsregelEntity.bedrag}</dd>
          <dt>
            <span id="credit">Credit</span>
          </dt>
          <dd>{prijsregelEntity.credit}</dd>
          <dt>Heeft Prijsafspraak</dt>
          <dd>{prijsregelEntity.heeftPrijsafspraak ? prijsregelEntity.heeftPrijsafspraak.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/prijsregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prijsregel/${prijsregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PrijsregelDetail;
