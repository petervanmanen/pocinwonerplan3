import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bemalingsgebied.reducer';

export const BemalingsgebiedDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bemalingsgebiedEntity = useAppSelector(state => state.bemalingsgebied.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bemalingsgebiedDetailsHeading">Bemalingsgebied</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bemalingsgebiedEntity.id}</dd>
          <dt>
            <span id="rioleringsgebied">Rioleringsgebied</span>
          </dt>
          <dd>{bemalingsgebiedEntity.rioleringsgebied}</dd>
        </dl>
        <Button tag={Link} to="/bemalingsgebied" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bemalingsgebied/${bemalingsgebiedEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BemalingsgebiedDetail;
