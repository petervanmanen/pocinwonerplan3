import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './heffinggrondslag.reducer';

export const HeffinggrondslagDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const heffinggrondslagEntity = useAppSelector(state => state.heffinggrondslag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="heffinggrondslagDetailsHeading">Heffinggrondslag</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{heffinggrondslagEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{heffinggrondslagEntity.bedrag}</dd>
          <dt>
            <span id="domein">Domein</span>
          </dt>
          <dd>{heffinggrondslagEntity.domein}</dd>
          <dt>
            <span id="hoofdstuk">Hoofdstuk</span>
          </dt>
          <dd>{heffinggrondslagEntity.hoofdstuk}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{heffinggrondslagEntity.omschrijving}</dd>
          <dt>
            <span id="paragraaf">Paragraaf</span>
          </dt>
          <dd>{heffinggrondslagEntity.paragraaf}</dd>
          <dt>Vermeldin Heffingsverordening</dt>
          <dd>{heffinggrondslagEntity.vermeldinHeffingsverordening ? heffinggrondslagEntity.vermeldinHeffingsverordening.id : ''}</dd>
          <dt>Heeft Zaaktype</dt>
          <dd>{heffinggrondslagEntity.heeftZaaktype ? heffinggrondslagEntity.heeftZaaktype.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/heffinggrondslag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/heffinggrondslag/${heffinggrondslagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default HeffinggrondslagDetail;
