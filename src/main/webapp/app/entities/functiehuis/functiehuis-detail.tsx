import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './functiehuis.reducer';

export const FunctiehuisDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const functiehuisEntity = useAppSelector(state => state.functiehuis.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="functiehuisDetailsHeading">Functiehuis</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{functiehuisEntity.id}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{functiehuisEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{functiehuisEntity.omschrijving}</dd>
        </dl>
        <Button tag={Link} to="/functiehuis" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/functiehuis/${functiehuisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FunctiehuisDetail;
