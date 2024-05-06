import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './raadsstuk.reducer';

export const RaadsstukDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const raadsstukEntity = useAppSelector(state => state.raadsstuk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="raadsstukDetailsHeading">Raadsstuk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{raadsstukEntity.id}</dd>
          <dt>
            <span id="besloten">Besloten</span>
          </dt>
          <dd>{raadsstukEntity.besloten}</dd>
          <dt>
            <span id="datumexpiratie">Datumexpiratie</span>
          </dt>
          <dd>{raadsstukEntity.datumexpiratie}</dd>
          <dt>
            <span id="datumpublicatie">Datumpublicatie</span>
          </dt>
          <dd>{raadsstukEntity.datumpublicatie}</dd>
          <dt>
            <span id="datumregistratie">Datumregistratie</span>
          </dt>
          <dd>{raadsstukEntity.datumregistratie}</dd>
          <dt>
            <span id="typeraadsstuk">Typeraadsstuk</span>
          </dt>
          <dd>{raadsstukEntity.typeraadsstuk}</dd>
          <dt>Heeft Taakveld</dt>
          <dd>{raadsstukEntity.heeftTaakveld ? raadsstukEntity.heeftTaakveld.id : ''}</dd>
          <dt>Behandelt Agendapunt</dt>
          <dd>
            {raadsstukEntity.behandeltAgendapunts
              ? raadsstukEntity.behandeltAgendapunts.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {raadsstukEntity.behandeltAgendapunts && i === raadsstukEntity.behandeltAgendapunts.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Hoortbij Programma</dt>
          <dd>
            {raadsstukEntity.hoortbijProgrammas
              ? raadsstukEntity.hoortbijProgrammas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {raadsstukEntity.hoortbijProgrammas && i === raadsstukEntity.hoortbijProgrammas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Wordtbehandeldin Vergadering</dt>
          <dd>
            {raadsstukEntity.wordtbehandeldinVergaderings
              ? raadsstukEntity.wordtbehandeldinVergaderings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {raadsstukEntity.wordtbehandeldinVergaderings && i === raadsstukEntity.wordtbehandeldinVergaderings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Categorie</dt>
          <dd>{raadsstukEntity.heeftCategorie ? raadsstukEntity.heeftCategorie.id : ''}</dd>
          <dt>Hoortbij Dossier</dt>
          <dd>
            {raadsstukEntity.hoortbijDossiers
              ? raadsstukEntity.hoortbijDossiers.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {raadsstukEntity.hoortbijDossiers && i === raadsstukEntity.hoortbijDossiers.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heeft Indiener</dt>
          <dd>
            {raadsstukEntity.heeftIndieners
              ? raadsstukEntity.heeftIndieners.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {raadsstukEntity.heeftIndieners && i === raadsstukEntity.heeftIndieners.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/raadsstuk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/raadsstuk/${raadsstukEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RaadsstukDetail;
