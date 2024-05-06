import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bord.reducer';

export const BordDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bordEntity = useAppSelector(state => state.bord.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bordDetailsHeading">Bord</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bordEntity.id}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{bordEntity.breedte}</dd>
          <dt>
            <span id="diameter">Diameter</span>
          </dt>
          <dd>{bordEntity.diameter}</dd>
          <dt>
            <span id="drager">Drager</span>
          </dt>
          <dd>{bordEntity.drager}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{bordEntity.hoogte}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{bordEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{bordEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{bordEntity.leverancier}</dd>
          <dt>
            <span id="materiaal">Materiaal</span>
          </dt>
          <dd>{bordEntity.materiaal}</dd>
          <dt>
            <span id="vorm">Vorm</span>
          </dt>
          <dd>{bordEntity.vorm}</dd>
        </dl>
        <Button tag={Link} to="/bord" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bord/${bordEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BordDetail;
