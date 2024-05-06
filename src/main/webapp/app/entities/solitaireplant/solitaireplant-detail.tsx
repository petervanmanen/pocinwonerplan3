import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './solitaireplant.reducer';

export const SolitaireplantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const solitaireplantEntity = useAppSelector(state => state.solitaireplant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="solitaireplantDetailsHeading">Solitaireplant</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{solitaireplantEntity.id}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{solitaireplantEntity.hoogte}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{solitaireplantEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/solitaireplant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/solitaireplant/${solitaireplantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SolitaireplantDetail;
