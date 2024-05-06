import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './woonfraudeaanvraagofmelding.reducer';

export const WoonfraudeaanvraagofmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const woonfraudeaanvraagofmeldingEntity = useAppSelector(state => state.woonfraudeaanvraagofmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="woonfraudeaanvraagofmeldingDetailsHeading">Woonfraudeaanvraagofmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{woonfraudeaanvraagofmeldingEntity.id}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{woonfraudeaanvraagofmeldingEntity.adres}</dd>
          <dt>
            <span id="categorie">Categorie</span>
          </dt>
          <dd>{woonfraudeaanvraagofmeldingEntity.categorie}</dd>
          <dt>
            <span id="locatieomschrijving">Locatieomschrijving</span>
          </dt>
          <dd>{woonfraudeaanvraagofmeldingEntity.locatieomschrijving}</dd>
          <dt>
            <span id="meldingomschrijving">Meldingomschrijving</span>
          </dt>
          <dd>{woonfraudeaanvraagofmeldingEntity.meldingomschrijving}</dd>
          <dt>
            <span id="meldingtekst">Meldingtekst</span>
          </dt>
          <dd>{woonfraudeaanvraagofmeldingEntity.meldingtekst}</dd>
        </dl>
        <Button tag={Link} to="/woonfraudeaanvraagofmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/woonfraudeaanvraagofmelding/${woonfraudeaanvraagofmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WoonfraudeaanvraagofmeldingDetail;
