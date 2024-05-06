import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vomaanvraagofmelding.reducer';

export const VomaanvraagofmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vomaanvraagofmeldingEntity = useAppSelector(state => state.vomaanvraagofmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vomaanvraagofmeldingDetailsHeading">Vomaanvraagofmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.id}</dd>
          <dt>
            <span id="activiteiten">Activiteiten</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.activiteiten}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.adres}</dd>
          <dt>
            <span id="bagid">Bagid</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.bagid}</dd>
          <dt>
            <span id="dossiernummer">Dossiernummer</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.dossiernummer}</dd>
          <dt>
            <span id="intaketype">Intaketype</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.intaketype}</dd>
          <dt>
            <span id="internnummer">Internnummer</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.internnummer}</dd>
          <dt>
            <span id="kadastraleaanduiding">Kadastraleaanduiding</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.kadastraleaanduiding}</dd>
          <dt>
            <span id="kenmerk">Kenmerk</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.kenmerk}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.locatie}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.locatieomschrijving}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{vomaanvraagofmeldingEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/vomaanvraagofmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vomaanvraagofmelding/${vomaanvraagofmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VomaanvraagofmeldingDetail;
