import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vulgraadmeting.reducer';

export const VulgraadmetingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vulgraadmetingEntity = useAppSelector(state => state.vulgraadmeting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vulgraadmetingDetailsHeading">Vulgraadmeting</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vulgraadmetingEntity.id}</dd>
          <dt>
            <span id="tijdstip">Tijdstip</span>
          </dt>
          <dd>{vulgraadmetingEntity.tijdstip}</dd>
          <dt>
            <span id="vulgraad">Vulgraad</span>
          </dt>
          <dd>{vulgraadmetingEntity.vulgraad}</dd>
          <dt>
            <span id="vullinggewicht">Vullinggewicht</span>
          </dt>
          <dd>{vulgraadmetingEntity.vullinggewicht}</dd>
          <dt>Heeft Container</dt>
          <dd>{vulgraadmetingEntity.heeftContainer ? vulgraadmetingEntity.heeftContainer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vulgraadmeting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vulgraadmeting/${vulgraadmetingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VulgraadmetingDetail;
