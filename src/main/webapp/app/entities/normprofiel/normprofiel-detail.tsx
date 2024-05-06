import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './normprofiel.reducer';

export const NormprofielDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const normprofielEntity = useAppSelector(state => state.normprofiel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="normprofielDetailsHeading">Normprofiel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{normprofielEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{normprofielEntity.code}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{normprofielEntity.omschrijving}</dd>
          <dt>
            <span id="schaal">Schaal</span>
          </dt>
          <dd>{normprofielEntity.schaal}</dd>
        </dl>
        <Button tag={Link} to="/normprofiel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/normprofiel/${normprofielEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NormprofielDetail;
