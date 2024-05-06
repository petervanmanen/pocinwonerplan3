import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vthzaak.reducer';

export const VthzaakDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vthzaakEntity = useAppSelector(state => state.vthzaak.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vthzaakDetailsHeading">Vthzaak</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vthzaakEntity.id}</dd>
          <dt>
            <span id="behandelaar">Behandelaar</span>
          </dt>
          <dd>{vthzaakEntity.behandelaar}</dd>
          <dt>
            <span id="bevoegdgezag">Bevoegdgezag</span>
          </dt>
          <dd>{vthzaakEntity.bevoegdgezag}</dd>
          <dt>
            <span id="prioritering">Prioritering</span>
          </dt>
          <dd>{vthzaakEntity.prioritering}</dd>
          <dt>
            <span id="teambehandelaar">Teambehandelaar</span>
          </dt>
          <dd>{vthzaakEntity.teambehandelaar}</dd>
          <dt>
            <span id="uitvoerendeinstantie">Uitvoerendeinstantie</span>
          </dt>
          <dd>{vthzaakEntity.uitvoerendeinstantie}</dd>
          <dt>
            <span id="verkamering">Verkamering</span>
          </dt>
          <dd>{vthzaakEntity.verkamering}</dd>
        </dl>
        <Button tag={Link} to="/vthzaak" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vthzaak/${vthzaakEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VthzaakDetail;
