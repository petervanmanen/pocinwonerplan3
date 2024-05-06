import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './functie.reducer';

export const FunctieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const functieEntity = useAppSelector(state => state.functie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="functieDetailsHeading">Functie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{functieEntity.id}</dd>
          <dt>
            <span id="groep">Groep</span>
          </dt>
          <dd>{functieEntity.groep}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{functieEntity.naam}</dd>
        </dl>
        <Button tag={Link} to="/functie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/functie/${functieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FunctieDetail;
