import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './examen.reducer';

export const ExamenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const examenEntity = useAppSelector(state => state.examen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="examenDetailsHeading">Examen</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{examenEntity.id}</dd>
          <dt>Afgerondmet Inburgeringstraject</dt>
          <dd>{examenEntity.afgerondmetInburgeringstraject ? examenEntity.afgerondmetInburgeringstraject.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/examen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/examen/${examenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExamenDetail;
