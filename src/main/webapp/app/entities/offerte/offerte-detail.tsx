import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './offerte.reducer';

export const OfferteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const offerteEntity = useAppSelector(state => state.offerte.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="offerteDetailsHeading">Offerte</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{offerteEntity.id}</dd>
          <dt>Betreft Aanbesteding</dt>
          <dd>{offerteEntity.betreftAanbesteding ? offerteEntity.betreftAanbesteding.id : ''}</dd>
          <dt>Ingedienddoor Leverancier</dt>
          <dd>{offerteEntity.ingedienddoorLeverancier ? offerteEntity.ingedienddoorLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/offerte" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/offerte/${offerteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OfferteDetail;
