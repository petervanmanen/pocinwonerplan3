import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gebouwdobject.reducer';

export const GebouwdobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gebouwdobjectEntity = useAppSelector(state => state.gebouwdobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gebouwdobjectDetailsHeading">Gebouwdobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{gebouwdobjectEntity.id}</dd>
          <dt>
            <span id="bouwkundigebestemmingactueel">Bouwkundigebestemmingactueel</span>
          </dt>
          <dd>{gebouwdobjectEntity.bouwkundigebestemmingactueel}</dd>
          <dt>
            <span id="brutoinhoud">Brutoinhoud</span>
          </dt>
          <dd>{gebouwdobjectEntity.brutoinhoud}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{gebouwdobjectEntity.identificatie}</dd>
          <dt>
            <span id="inwinningoppervlakte">Inwinningoppervlakte</span>
          </dt>
          <dd>{gebouwdobjectEntity.inwinningoppervlakte}</dd>
          <dt>
            <span id="oppervlakteobject">Oppervlakteobject</span>
          </dt>
          <dd>{gebouwdobjectEntity.oppervlakteobject}</dd>
          <dt>
            <span id="statusvoortgangbouw">Statusvoortgangbouw</span>
          </dt>
          <dd>{gebouwdobjectEntity.statusvoortgangbouw}</dd>
        </dl>
        <Button tag={Link} to="/gebouwdobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gebouwdobject/${gebouwdobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default GebouwdobjectDetail;
