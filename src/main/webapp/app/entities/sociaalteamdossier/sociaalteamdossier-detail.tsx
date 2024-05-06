import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sociaalteamdossier.reducer';

export const SociaalteamdossierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sociaalteamdossierEntity = useAppSelector(state => state.sociaalteamdossier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sociaalteamdossierDetailsHeading">Sociaalteamdossier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sociaalteamdossierEntity.id}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>{sociaalteamdossierEntity.datumeinde}</dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>{sociaalteamdossierEntity.datumstart}</dd>
          <dt>
            <span id="datumvaststelling">Datumvaststelling</span>
          </dt>
          <dd>{sociaalteamdossierEntity.datumvaststelling}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{sociaalteamdossierEntity.omschrijving}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{sociaalteamdossierEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/sociaalteamdossier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sociaalteamdossier/${sociaalteamdossierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SociaalteamdossierDetail;
