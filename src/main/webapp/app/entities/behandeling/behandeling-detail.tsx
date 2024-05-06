import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './behandeling.reducer';

export const BehandelingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const behandelingEntity = useAppSelector(state => state.behandeling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="behandelingDetailsHeading">Behandeling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{behandelingEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{behandelingEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{behandelingEntity.datumstart}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{behandelingEntity.toelichting}</dd>
          <dt>Isvansoort Behandelsoort</dt>
          <dd>{behandelingEntity.isvansoortBehandelsoort ? behandelingEntity.isvansoortBehandelsoort.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/behandeling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/behandeling/${behandelingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BehandelingDetail;
