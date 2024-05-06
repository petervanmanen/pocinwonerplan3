import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './scheiding.reducer';

export const ScheidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const scheidingEntity = useAppSelector(state => state.scheiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="scheidingDetailsHeading">Scheiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{scheidingEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{scheidingEntity.aanleghoogte}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{scheidingEntity.breedte}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{scheidingEntity.hoogte}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{scheidingEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{scheidingEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{scheidingEntity.leverancier}</dd>
          <dt>
            <span id="eobjectnaam">Eobjectnaam</span>
          </dt>
          <dd>{scheidingEntity.eobjectnaam}</dd>
          <dt>
            <span id="eobjectnummer">Eobjectnummer</span>
          </dt>
          <dd>{scheidingEntity.eobjectnummer}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{scheidingEntity.oppervlakte}</dd>
          <dt>
            <span id="scheidingmateriaal">Scheidingmateriaal</span>
          </dt>
          <dd>{scheidingEntity.scheidingmateriaal}</dd>
          <dt>
            <span id="verplaatsbaar">Verplaatsbaar</span>
          </dt>
          <dd>{scheidingEntity.verplaatsbaar ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/scheiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/scheiding/${scheidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ScheidingDetail;
