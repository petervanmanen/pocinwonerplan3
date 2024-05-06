import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sociaalteamdossiersoort.reducer';

export const SociaalteamdossiersoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sociaalteamdossiersoortEntity = useAppSelector(state => state.sociaalteamdossiersoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sociaalteamdossiersoortDetailsHeading">Sociaalteamdossiersoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sociaalteamdossiersoortEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{sociaalteamdossiersoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{sociaalteamdossiersoortEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/sociaalteamdossiersoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sociaalteamdossiersoort/${sociaalteamdossiersoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SociaalteamdossiersoortDetail;
