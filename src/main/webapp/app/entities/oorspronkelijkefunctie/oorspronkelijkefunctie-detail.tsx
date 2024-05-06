import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './oorspronkelijkefunctie.reducer';

export const OorspronkelijkefunctieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const oorspronkelijkefunctieEntity = useAppSelector(state => state.oorspronkelijkefunctie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="oorspronkelijkefunctieDetailsHeading">Oorspronkelijkefunctie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.id}</dd>
          <dt>
            <span id="functie">Functie</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.functie}</dd>
          <dt>
            <span id="functiesoort">Functiesoort</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.functiesoort}</dd>
          <dt>
            <span id="hoofdcategorie">Hoofdcategorie</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.hoofdcategorie}</dd>
          <dt>
            <span id="hoofdfunctie">Hoofdfunctie</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.hoofdfunctie}</dd>
          <dt>
            <span id="subcategorie">Subcategorie</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.subcategorie}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.toelichting}</dd>
          <dt>
            <span id="verbijzondering">Verbijzondering</span>
          </dt>
          <dd>{oorspronkelijkefunctieEntity.verbijzondering}</dd>
        </dl>
        <Button tag={Link} to="/oorspronkelijkefunctie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/oorspronkelijkefunctie/${oorspronkelijkefunctieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OorspronkelijkefunctieDetail;
