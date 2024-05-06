import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subproducttype.reducer';

export const SubproducttypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subproducttypeEntity = useAppSelector(state => state.subproducttype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subproducttypeDetailsHeading">Subproducttype</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subproducttypeEntity.id}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{subproducttypeEntity.omschrijving}</dd>
          <dt>
            <span id="prioriteit">Prioriteit</span>
          </dt>
          <dd>{subproducttypeEntity.prioriteit}</dd>
        </dl>
        <Button tag={Link} to="/subproducttype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subproducttype/${subproducttypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubproducttypeDetail;
