import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bergingsbassin.reducer';

export const BergingsbassinDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bergingsbassinEntity = useAppSelector(state => state.bergingsbassin.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bergingsbassinDetailsHeading">Bergingsbassin</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bergingsbassinEntity.id}</dd>
          <dt>
            <span id="bergendvermogen">Bergendvermogen</span>
          </dt>
          <dd>{bergingsbassinEntity.bergendvermogen}</dd>
          <dt>
            <span id="pompledigingsvoorziening">Pompledigingsvoorziening</span>
          </dt>
          <dd>{bergingsbassinEntity.pompledigingsvoorziening}</dd>
          <dt>
            <span id="pompspoelvoorziening">Pompspoelvoorziening</span>
          </dt>
          <dd>{bergingsbassinEntity.pompspoelvoorziening}</dd>
          <dt>
            <span id="spoelleiding">Spoelleiding</span>
          </dt>
          <dd>{bergingsbassinEntity.spoelleiding}</dd>
          <dt>
            <span id="vorm">Vorm</span>
          </dt>
          <dd>{bergingsbassinEntity.vorm}</dd>
        </dl>
        <Button tag={Link} to="/bergingsbassin" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bergingsbassin/${bergingsbassinEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BergingsbassinDetail;
