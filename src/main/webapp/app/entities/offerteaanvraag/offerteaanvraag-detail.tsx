import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './offerteaanvraag.reducer';

export const OfferteaanvraagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const offerteaanvraagEntity = useAppSelector(state => state.offerteaanvraag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="offerteaanvraagDetailsHeading">Offerteaanvraag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{offerteaanvraagEntity.id}</dd>
          <dt>
            <span id="datumaanvraag">Datumaanvraag</span>
          </dt>
          <dd>
            {offerteaanvraagEntity.datumaanvraag ? (
              <TextFormat value={offerteaanvraagEntity.datumaanvraag} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumsluiting">Datumsluiting</span>
          </dt>
          <dd>{offerteaanvraagEntity.datumsluiting}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{offerteaanvraagEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{offerteaanvraagEntity.omschrijving}</dd>
          <dt>Betreft Aanbesteding</dt>
          <dd>{offerteaanvraagEntity.betreftAanbesteding ? offerteaanvraagEntity.betreftAanbesteding.id : ''}</dd>
          <dt>Gerichtaan Leverancier</dt>
          <dd>{offerteaanvraagEntity.gerichtaanLeverancier ? offerteaanvraagEntity.gerichtaanLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/offerteaanvraag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/offerteaanvraag/${offerteaanvraagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OfferteaanvraagDetail;
