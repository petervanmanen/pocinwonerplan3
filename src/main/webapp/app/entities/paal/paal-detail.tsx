import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './paal.reducer';

export const PaalDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const paalEntity = useAppSelector(state => state.paal.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paalDetailsHeading">Paal</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{paalEntity.id}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{paalEntity.breedte}</dd>
          <dt>
            <span id="diameter">Diameter</span>
          </dt>
          <dd>{paalEntity.diameter}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{paalEntity.hoogte}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{paalEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="kwaliteitsniveauactueel">Kwaliteitsniveauactueel</span>
          </dt>
          <dd>{paalEntity.kwaliteitsniveauactueel}</dd>
          <dt>
            <span id="kwaliteitsniveaugewenst">Kwaliteitsniveaugewenst</span>
          </dt>
          <dd>{paalEntity.kwaliteitsniveaugewenst}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{paalEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{paalEntity.leverancier}</dd>
          <dt>
            <span id="materiaal">Materiaal</span>
          </dt>
          <dd>{paalEntity.materiaal}</dd>
          <dt>
            <span id="vorm">Vorm</span>
          </dt>
          <dd>{paalEntity.vorm}</dd>
        </dl>
        <Button tag={Link} to="/paal" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paal/${paalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaalDetail;
