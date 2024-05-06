import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './sbiactiviteitvestiging.reducer';

export const SbiactiviteitvestigingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sbiactiviteitvestigingEntity = useAppSelector(state => state.sbiactiviteitvestiging.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sbiactiviteitvestigingDetailsHeading">Sbiactiviteitvestiging</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{sbiactiviteitvestigingEntity.id}</dd>
          <dt>
            <span id="indicatiehoofdactiviteit">Indicatiehoofdactiviteit</span>
          </dt>
          <dd>{sbiactiviteitvestigingEntity.indicatiehoofdactiviteit}</dd>
          <dt>
            <span id="sbicode">Sbicode</span>
          </dt>
          <dd>{sbiactiviteitvestigingEntity.sbicode}</dd>
        </dl>
        <Button tag={Link} to="/sbiactiviteitvestiging" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sbiactiviteitvestiging/${sbiactiviteitvestigingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SbiactiviteitvestigingDetail;
