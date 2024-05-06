import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bouwwerk.reducer';

export const BouwwerkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bouwwerkEntity = useAppSelector(state => state.bouwwerk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bouwwerkDetailsHeading">Bouwwerk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bouwwerkEntity.id}</dd>
          <dt>
            <span id="aanleghoogte">Aanleghoogte</span>
          </dt>
          <dd>{bouwwerkEntity.aanleghoogte}</dd>
          <dt>
            <span id="bouwwerkmateriaal">Bouwwerkmateriaal</span>
          </dt>
          <dd>{bouwwerkEntity.bouwwerkmateriaal}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{bouwwerkEntity.breedte}</dd>
          <dt>
            <span id="fabrikant">Fabrikant</span>
          </dt>
          <dd>{bouwwerkEntity.fabrikant}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{bouwwerkEntity.hoogte}</dd>
          <dt>
            <span id="jaaronderhouduitgevoerd">Jaaronderhouduitgevoerd</span>
          </dt>
          <dd>{bouwwerkEntity.jaaronderhouduitgevoerd}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{bouwwerkEntity.lengte}</dd>
          <dt>
            <span id="leverancier">Leverancier</span>
          </dt>
          <dd>{bouwwerkEntity.leverancier}</dd>
          <dt>
            <span id="oppervlakte">Oppervlakte</span>
          </dt>
          <dd>{bouwwerkEntity.oppervlakte}</dd>
          <dt>
            <span id="typefundering">Typefundering</span>
          </dt>
          <dd>{bouwwerkEntity.typefundering}</dd>
        </dl>
        <Button tag={Link} to="/bouwwerk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bouwwerk/${bouwwerkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BouwwerkDetail;
