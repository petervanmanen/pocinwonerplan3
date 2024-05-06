import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overiggebouwdobject.reducer';

export const OveriggebouwdobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overiggebouwdobjectEntity = useAppSelector(state => state.overiggebouwdobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overiggebouwdobjectDetailsHeading">Overiggebouwdobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overiggebouwdobjectEntity.id}</dd>
          <dt>
            <span id="bouwjaar">Bouwjaar</span>
          </dt>
          <dd>{overiggebouwdobjectEntity.bouwjaar}</dd>
          <dt>
            <span id="indicatieplanobject">Indicatieplanobject</span>
          </dt>
          <dd>{overiggebouwdobjectEntity.indicatieplanobject}</dd>
          <dt>
            <span id="overiggebouwdobjectidentificatie">Overiggebouwdobjectidentificatie</span>
          </dt>
          <dd>{overiggebouwdobjectEntity.overiggebouwdobjectidentificatie}</dd>
        </dl>
        <Button tag={Link} to="/overiggebouwdobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overiggebouwdobject/${overiggebouwdobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OveriggebouwdobjectDetail;
