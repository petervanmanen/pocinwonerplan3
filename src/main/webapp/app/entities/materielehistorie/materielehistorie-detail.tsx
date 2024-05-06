import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './materielehistorie.reducer';

export const MaterielehistorieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const materielehistorieEntity = useAppSelector(state => state.materielehistorie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="materielehistorieDetailsHeading">Materielehistorie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{materielehistorieEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheidgegevens">Datumbegingeldigheidgegevens</span>
          </dt>
          <dd>{materielehistorieEntity.datumbegingeldigheidgegevens}</dd>
          <dt>
            <span id="datumeindegeldigheidgegevens">Datumeindegeldigheidgegevens</span>
          </dt>
          <dd>{materielehistorieEntity.datumeindegeldigheidgegevens}</dd>
        </dl>
        <Button tag={Link} to="/materielehistorie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/materielehistorie/${materielehistorieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaterielehistorieDetail;
