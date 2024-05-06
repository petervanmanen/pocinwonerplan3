import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leerling.reducer';

export const LeerlingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leerlingEntity = useAppSelector(state => state.leerling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leerlingDetailsHeading">Leerling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leerlingEntity.id}</dd>
          <dt>
            <span id="kwetsbarejongere">Kwetsbarejongere</span>
          </dt>
          <dd>{leerlingEntity.kwetsbarejongere ? 'true' : 'false'}</dd>
          <dt>Heeft Startkwalificatie</dt>
          <dd>{leerlingEntity.heeftStartkwalificatie ? leerlingEntity.heeftStartkwalificatie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/leerling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leerling/${leerlingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeerlingDetail;
