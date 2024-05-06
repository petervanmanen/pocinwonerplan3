import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './regeltekst.reducer';

export const RegeltekstDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const regeltekstEntity = useAppSelector(state => state.regeltekst.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="regeltekstDetailsHeading">Regeltekst</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{regeltekstEntity.id}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{regeltekstEntity.identificatie}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{regeltekstEntity.omschrijving}</dd>
          <dt>
            <span id="tekst">Tekst</span>
          </dt>
          <dd>{regeltekstEntity.tekst}</dd>
          <dt>Heeftthema Thema</dt>
          <dd>
            {regeltekstEntity.heeftthemaThemas
              ? regeltekstEntity.heeftthemaThemas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {regeltekstEntity.heeftthemaThemas && i === regeltekstEntity.heeftthemaThemas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeftidealisatie Idealisatie</dt>
          <dd>
            {regeltekstEntity.heeftidealisatieIdealisaties
              ? regeltekstEntity.heeftidealisatieIdealisaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {regeltekstEntity.heeftidealisatieIdealisaties && i === regeltekstEntity.heeftidealisatieIdealisaties.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Werkingsgebied Locatie</dt>
          <dd>
            {regeltekstEntity.werkingsgebiedLocaties
              ? regeltekstEntity.werkingsgebiedLocaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {regeltekstEntity.werkingsgebiedLocaties && i === regeltekstEntity.werkingsgebiedLocaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Bevat Omgevingsdocument</dt>
          <dd>{regeltekstEntity.bevatOmgevingsdocument ? regeltekstEntity.bevatOmgevingsdocument.id : ''}</dd>
          <dt>Werkingsgebied Regeltekst 2</dt>
          <dd>{regeltekstEntity.werkingsgebiedRegeltekst2 ? regeltekstEntity.werkingsgebiedRegeltekst2.id : ''}</dd>
          <dt>Isgerelateerd Regeltekst 2</dt>
          <dd>{regeltekstEntity.isgerelateerdRegeltekst2 ? regeltekstEntity.isgerelateerdRegeltekst2.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/regeltekst" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/regeltekst/${regeltekstEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RegeltekstDetail;
