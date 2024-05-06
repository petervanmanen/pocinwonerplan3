import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './overstortconstructie.reducer';

export const OverstortconstructieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const overstortconstructieEntity = useAppSelector(state => state.overstortconstructie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="overstortconstructieDetailsHeading">Overstortconstructie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{overstortconstructieEntity.id}</dd>
          <dt>
            <span id="bassin">Bassin</span>
          </dt>
          <dd>{overstortconstructieEntity.bassin}</dd>
          <dt>
            <span id="drempelbreedte">Drempelbreedte</span>
          </dt>
          <dd>{overstortconstructieEntity.drempelbreedte}</dd>
          <dt>
            <span id="drempelniveau">Drempelniveau</span>
          </dt>
          <dd>{overstortconstructieEntity.drempelniveau}</dd>
          <dt>
            <span id="klep">Klep</span>
          </dt>
          <dd>{overstortconstructieEntity.klep ? 'true' : 'false'}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{overstortconstructieEntity.type}</dd>
          <dt>
            <span id="vormdrempel">Vormdrempel</span>
          </dt>
          <dd>{overstortconstructieEntity.vormdrempel}</dd>
          <dt>
            <span id="waking">Waking</span>
          </dt>
          <dd>{overstortconstructieEntity.waking}</dd>
        </dl>
        <Button tag={Link} to="/overstortconstructie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/overstortconstructie/${overstortconstructieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OverstortconstructieDetail;
