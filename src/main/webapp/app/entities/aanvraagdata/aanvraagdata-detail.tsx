import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aanvraagdata.reducer';

export const AanvraagdataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aanvraagdataEntity = useAppSelector(state => state.aanvraagdata.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aanvraagdataDetailsHeading">Aanvraagdata</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aanvraagdataEntity.id}</dd>
          <dt>
            <span id="data">Data</span>
          </dt>
          <dd>{aanvraagdataEntity.data}</dd>
          <dt>
            <span id="veld">Veld</span>
          </dt>
          <dd>{aanvraagdataEntity.veld}</dd>
          <dt>Isconform Formuliersoortveld</dt>
          <dd>{aanvraagdataEntity.isconformFormuliersoortveld ? aanvraagdataEntity.isconformFormuliersoortveld.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/aanvraagdata" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aanvraagdata/${aanvraagdataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AanvraagdataDetail;
