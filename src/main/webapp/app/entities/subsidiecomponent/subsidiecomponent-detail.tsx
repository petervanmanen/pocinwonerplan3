import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './subsidiecomponent.reducer';

export const SubsidiecomponentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const subsidiecomponentEntity = useAppSelector(state => state.subsidiecomponent.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subsidiecomponentDetailsHeading">Subsidiecomponent</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{subsidiecomponentEntity.id}</dd>
          <dt>
            <span id="gereserveerdbedrag">Gereserveerdbedrag</span>
          </dt>
          <dd>{subsidiecomponentEntity.gereserveerdbedrag}</dd>
          <dt>
            <span id="toegekendbedrag">Toegekendbedrag</span>
          </dt>
          <dd>{subsidiecomponentEntity.toegekendbedrag}</dd>
          <dt>Heeft Kostenplaats</dt>
          <dd>{subsidiecomponentEntity.heeftKostenplaats ? subsidiecomponentEntity.heeftKostenplaats.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/subsidiecomponent" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/subsidiecomponent/${subsidiecomponentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubsidiecomponentDetail;
