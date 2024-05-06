import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vaartuig.reducer';

export const VaartuigDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vaartuigEntity = useAppSelector(state => state.vaartuig.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vaartuigDetailsHeading">Vaartuig</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vaartuigEntity.id}</dd>
          <dt>
            <span id="breedte">Breedte</span>
          </dt>
          <dd>{vaartuigEntity.breedte}</dd>
          <dt>
            <span id="hoogte">Hoogte</span>
          </dt>
          <dd>{vaartuigEntity.hoogte}</dd>
          <dt>
            <span id="kleur">Kleur</span>
          </dt>
          <dd>{vaartuigEntity.kleur}</dd>
          <dt>
            <span id="lengte">Lengte</span>
          </dt>
          <dd>{vaartuigEntity.lengte}</dd>
          <dt>
            <span id="naamvaartuig">Naamvaartuig</span>
          </dt>
          <dd>{vaartuigEntity.naamvaartuig}</dd>
          <dt>
            <span id="registratienummer">Registratienummer</span>
          </dt>
          <dd>{vaartuigEntity.registratienummer}</dd>
        </dl>
        <Button tag={Link} to="/vaartuig" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vaartuig/${vaartuigEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VaartuigDetail;
