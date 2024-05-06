import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tunnelobject.reducer';

export const TunnelobjectDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tunnelobjectEntity = useAppSelector(state => state.tunnelobject.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tunnelobjectDetailsHeading">Tunnelobject</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{tunnelobjectEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{tunnelobjectEntity.aanleghoogte}</dd>
          <dt>
            <span id="aantaltunnelbuizen">Aantaltunnelbuizen</span>
          </dt>
          <dd>{tunnelobjectEntity.aantaltunnelbuizen}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{tunnelobjectEntity.breedte}</dd>
          <dt>
            <span id="doorrijbreedte">Doorrijbreedte</span>
          </dt>
          <dd>{tunnelobjectEntity.doorrijbreedte}</dd>
          <dt>
            <span id="doorrijhoogte">Doorrijhoogte</span>
          </dt>
          <dd>{tunnelobjectEntity.doorrijhoogte}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{tunnelobjectEntity.hoogte}</dd>
          <dt>
            <span id="jaarconserveren">Jaarconserveren</span>
          </dt>
          <dd>{tunnelobjectEntity.jaarconserveren}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{tunnelobjectEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{tunnelobjectEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{tunnelobjectEntity.leverancier}</dd>
          <dt>
            <span id="eobjectnaam">Eobjectnaam</span>
          </dt>
          <dd>{tunnelobjectEntity.eobjectnaam}</dd>
          <dt>
            <span id="eobjectnummer">Eobjectnummer</span>
          </dt>
          <dd>{tunnelobjectEntity.eobjectnummer}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{tunnelobjectEntity.oppervlakte}</dd>
          <dt>
            <span id="tunnelobjectmateriaal">Tunnelobjectmateriaal</span>
          </dt>
          <dd>{tunnelobjectEntity.tunnelobjectmateriaal}</dd>
        </dl>
        <Button tag={Link} to="/tunnelobject" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tunnelobject/${tunnelobjectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TunnelobjectDetail;
