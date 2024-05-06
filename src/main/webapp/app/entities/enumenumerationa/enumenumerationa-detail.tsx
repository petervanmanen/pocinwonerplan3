import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './enumenumerationa.reducer';

export const EnumenumerationaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const enumenumerationaEntity = useAppSelector(state => state.enumenumerationa.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="enumenumerationaDetailsHeading">Enumenumerationa</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="optie1">Optie 1</span>
          </dt>
          <dd>{enumenumerationaEntity.optie1}</dd>
          <dt>
            <span id="optie2">Optie 2</span>
          </dt>
          <dd>{enumenumerationaEntity.optie2}</dd>
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{enumenumerationaEntity.id}</dd>
        </dl>
        <Button tag={Link} to="/enumenumerationa" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/enumenumerationa/${enumenumerationaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnumenumerationaDetail;
