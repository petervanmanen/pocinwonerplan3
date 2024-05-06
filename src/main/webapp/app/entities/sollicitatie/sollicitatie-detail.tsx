import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sollicitatie.reducer';

export const SollicitatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sollicitatieEntity = useAppSelector(state => state.sollicitatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sollicitatieDetailsHeading">Sollicitatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sollicitatieEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {sollicitatieEntity.datum ? <TextFormat value={sollicitatieEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>Opvacature Vacature</dt>
          <dd>{sollicitatieEntity.opvacatureVacature ? sollicitatieEntity.opvacatureVacature.id : ''}</dd>
          <dt>Solliciteertopfunctie Sollicitant</dt>
          <dd>{sollicitatieEntity.solliciteertopfunctieSollicitant ? sollicitatieEntity.solliciteertopfunctieSollicitant.id : ''}</dd>
          <dt>Solliciteert Werknemer</dt>
          <dd>{sollicitatieEntity.solliciteertWerknemer ? sollicitatieEntity.solliciteertWerknemer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sollicitatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sollicitatie/${sollicitatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SollicitatieDetail;
