import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './installatie.reducer';

export const InstallatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const installatieEntity = useAppSelector(state => state.installatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="installatieDetailsHeading">Installatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{installatieEntity.id}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{installatieEntity.breedte}</dd>
          <dt>
            <span id="eancode">Eancode</span>
          </dt>
          <dd>{installatieEntity.eancode}</dd>
          <dt>
            <span id="fabrikant">Fabrikant</span>
          </dt>
          <dd>{installatieEntity.fabrikant}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{installatieEntity.hoogte}</dd>
          <dt>
            <span id="inbelgegevens">Inbelgegevens</span>
          </dt>
          <dd>{installatieEntity.inbelgegevens}</dd>
          <dt>
            <span id="installateur">Installateur</span>
          </dt>
          <dd>{installatieEntity.installateur}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{installatieEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{installatieEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{installatieEntity.leverancier}</dd>
          <dt>
            <span id="typecommunicatie">Typecommunicatie</span>
          </dt>
          <dd>{installatieEntity.typecommunicatie}</dd>
        </dl>
        <Button tag={Link} to="/installatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/installatie/${installatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InstallatieDetail;
