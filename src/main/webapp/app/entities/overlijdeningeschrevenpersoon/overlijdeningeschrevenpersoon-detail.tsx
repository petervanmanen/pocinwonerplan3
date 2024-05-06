import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overlijdeningeschrevenpersoon.reducer';

export const OverlijdeningeschrevenpersoonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overlijdeningeschrevenpersoonEntity = useAppSelector(state => state.overlijdeningeschrevenpersoon.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overlijdeningeschrevenpersoonDetailsHeading">Overlijdeningeschrevenpersoon</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overlijdeningeschrevenpersoonEntity.id}</dd>
          <dt>
            <span id="datumoverlijden">Datumoverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevenpersoonEntity.datumoverlijden}</dd>
          <dt>
            <span id="landoverlijden">Landoverlijden</span>
          </dt>
          <dd>{overlijdeningeschrevenpersoonEntity.landoverlijden}</dd>
          <dt>
            <span id="overlijdensplaats">Overlijdensplaats</span>
          </dt>
          <dd>{overlijdeningeschrevenpersoonEntity.overlijdensplaats}</dd>
        </dl>
        <Button tag={Link} to="/overlijdeningeschrevenpersoon" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overlijdeningeschrevenpersoon/${overlijdeningeschrevenpersoonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverlijdeningeschrevenpersoonDetail;
