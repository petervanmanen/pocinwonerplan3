import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cmdbitem.reducer';

export const CmdbitemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cmdbitemEntity = useAppSelector(state => state.cmdbitem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cmdbitemDetailsHeading">Cmdbitem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cmdbitemEntity.id}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{cmdbitemEntity.beschrijving}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{cmdbitemEntity.naam}</dd>
        </dl>
        <Button tag={Link} to="/cmdbitem" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cmdbitem/${cmdbitemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CmdbitemDetail;
