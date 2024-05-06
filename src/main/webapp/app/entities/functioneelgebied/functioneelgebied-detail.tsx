import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './functioneelgebied.reducer';

export const FunctioneelgebiedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const functioneelgebiedEntity = useAppSelector(state => state.functioneelgebied.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="functioneelgebiedDetailsHeading">Functioneelgebied</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{functioneelgebiedEntity.id}</dd>
          <dt>
            <span id="functioneelgebiedcode">Functioneelgebiedcode</span>
          </dt>
          <dd>{functioneelgebiedEntity.functioneelgebiedcode}</dd>
          <dt>
            <span id="functioneelgebiednaam">Functioneelgebiednaam</span>
          </dt>
          <dd>{functioneelgebiedEntity.functioneelgebiednaam}</dd>
          <dt>
            <span id="omtrek">Omtrek</span>
          </dt>
          <dd>{functioneelgebiedEntity.omtrek}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{functioneelgebiedEntity.oppervlakte}</dd>
        </dl>
        <Button tag={Link} to="/functioneelgebied" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/functioneelgebied/${functioneelgebiedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FunctioneelgebiedDetail;
