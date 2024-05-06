import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './organisatorischeeenheid.reducer';

export const OrganisatorischeeenheidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const organisatorischeeenheidEntity = useAppSelector(state => state.organisatorischeeenheid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="organisatorischeeenheidDetailsHeading">Organisatorischeeenheid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.id}</dd>
          <dt>
            <span id="datumontstaan">Datumontstaan</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.datumontstaan}</dd>
          <dt>
            <span id="datumopheffing">Datumopheffing</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.datumopheffing}</dd>
          <dt>
            <span id="emailadres">Emailadres</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.emailadres}</dd>
          <dt>
            <span id="faxnummer">Faxnummer</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.faxnummer}</dd>
          <dt>
            <span id="formatie">Formatie</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.formatie}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.naam}</dd>
          <dt>
            <span id="naamverkort">Naamverkort</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.naamverkort}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.omschrijving}</dd>
          <dt>
            <span id="organisatieidentificatie">Organisatieidentificatie</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.organisatieidentificatie}</dd>
          <dt>
            <span id="telefoonnummer">Telefoonnummer</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.telefoonnummer}</dd>
          <dt>
            <span id="toelichting">Toelichting</span>
          </dt>
          <dd>{organisatorischeeenheidEntity.toelichting}</dd>
        </dl>
        <Button tag={Link} to="/organisatorischeeenheid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organisatorischeeenheid/${organisatorischeeenheidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrganisatorischeeenheidDetail;
