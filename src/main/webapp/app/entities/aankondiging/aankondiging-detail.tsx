import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aankondiging.reducer';

export const AankondigingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aankondigingEntity = useAppSelector(state => state.aankondiging.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aankondigingDetailsHeading">Aankondiging</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aankondigingEntity.id}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{aankondigingEntity.beschrijving}</dd>
          <dt>
            <span id="categorie">Categorie</span>
          </dt>
          <dd>{aankondigingEntity.categorie}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{aankondigingEntity.datum}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{aankondigingEntity.naam}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{aankondigingEntity.type}</dd>
          <dt>Mondtuit Aanbesteding</dt>
          <dd>{aankondigingEntity.mondtuitAanbesteding ? aankondigingEntity.mondtuitAanbesteding.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/aankondiging" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aankondiging/${aankondigingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AankondigingDetail;
