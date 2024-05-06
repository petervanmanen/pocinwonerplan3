import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doorgeleidingom.reducer';

export const DoorgeleidingomDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doorgeleidingomEntity = useAppSelector(state => state.doorgeleidingom.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doorgeleidingomDetailsHeading">Doorgeleidingom</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{doorgeleidingomEntity.id}</dd>
          <dt>
            <span id="afdoening">Afdoening</span>
          </dt>
          <dd>{doorgeleidingomEntity.afdoening}</dd>
        </dl>
        <Button tag={Link} to="/doorgeleidingom" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doorgeleidingom/${doorgeleidingomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoorgeleidingomDetail;
