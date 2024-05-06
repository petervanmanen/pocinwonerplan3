import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artefactsoort.reducer';

export const ArtefactsoortDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artefactsoortEntity = useAppSelector(state => state.artefactsoort.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artefactsoortDetailsHeading">Artefactsoort</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artefactsoortEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{artefactsoortEntity.code}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{artefactsoortEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{artefactsoortEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/artefactsoort" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artefactsoort/${artefactsoortEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtefactsoortDetail;
