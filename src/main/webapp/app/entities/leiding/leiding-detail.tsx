import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './leiding.reducer';

export const LeidingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const leidingEntity = useAppSelector(state => state.leiding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="leidingDetailsHeading">Leiding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{leidingEntity.id}</dd>
          <dt>
            <span id="afwijkendedieptelegging">Afwijkendedieptelegging</span>
          </dt>
          <dd>{leidingEntity.afwijkendedieptelegging}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{leidingEntity.breedte}</dd>
          <dt>
            <span id="diameter">Diameter</span>
          </dt>
          <dd>{leidingEntity.diameter}</dd>
          <dt>
            <span id="diepte">Diepte</span>
          </dt>
          <dd>{leidingEntity.diepte}</dd>
          <dt>
            <span id="eisvoorzorgsmaatregel">Eisvoorzorgsmaatregel</span>
          </dt>
          <dd>{leidingEntity.eisvoorzorgsmaatregel}</dd>
          <dt>
            <span id="geonauwkeurigheidxy">Geonauwkeurigheidxy</span>
          </dt>
          <dd>{leidingEntity.geonauwkeurigheidxy}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{leidingEntity.hoogte}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{leidingEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{leidingEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{leidingEntity.leverancier}</dd>
          <dt>
            <span id="materiaal">Materiaal</span>
          </dt>
          <dd>{leidingEntity.materiaal}</dd>
          <dt>
            <span id="themaimkl">Themaimkl</span>
          </dt>
          <dd>{leidingEntity.themaimkl}</dd>
          <dt>
            <span id="verhoogdrisico">Verhoogdrisico</span>
          </dt>
          <dd>{leidingEntity.verhoogdrisico}</dd>
        </dl>
        <Button tag={Link} to="/leiding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leiding/${leidingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default LeidingDetail;
