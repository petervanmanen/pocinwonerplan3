import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './archiefstuk.reducer';

export const ArchiefstukDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const archiefstukEntity = useAppSelector(state => state.archiefstuk.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="archiefstukDetailsHeading">Archiefstuk</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{archiefstukEntity.id}</dd>
          <dt>
            <span id="beschrijving">Beschrijving</span>
          </dt>
          <dd>{archiefstukEntity.beschrijving}</dd>
          <dt>
            <span id="inventarisnummer">Inventarisnummer</span>
          </dt>
          <dd>{archiefstukEntity.inventarisnummer}</dd>
          <dt>
            <span id="omvang">Omvang</span>
          </dt>
          <dd>{archiefstukEntity.omvang}</dd>
          <dt>
            <span id="openbaarheidsbeperking">Openbaarheidsbeperking</span>
          </dt>
          <dd>{archiefstukEntity.openbaarheidsbeperking}</dd>
          <dt>
            <span id="trefwoorden">Trefwoorden</span>
          </dt>
          <dd>{archiefstukEntity.trefwoorden}</dd>
          <dt>
            <span id="uiterlijkevorm">Uiterlijkevorm</span>
          </dt>
          <dd>{archiefstukEntity.uiterlijkevorm}</dd>
          <dt>Isonderdeelvan Archief</dt>
          <dd>{archiefstukEntity.isonderdeelvanArchief ? archiefstukEntity.isonderdeelvanArchief.id : ''}</dd>
          <dt>Heeft Uitgever</dt>
          <dd>{archiefstukEntity.heeftUitgever ? archiefstukEntity.heeftUitgever.id : ''}</dd>
          <dt>Heeft Vindplaats</dt>
          <dd>{archiefstukEntity.heeftVindplaats ? archiefstukEntity.heeftVindplaats.id : ''}</dd>
          <dt>Heeft Ordeningsschema</dt>
          <dd>
            {archiefstukEntity.heeftOrdeningsschemas
              ? archiefstukEntity.heeftOrdeningsschemas.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {archiefstukEntity.heeftOrdeningsschemas && i === archiefstukEntity.heeftOrdeningsschemas.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Stamtuit Periode</dt>
          <dd>
            {archiefstukEntity.stamtuitPeriodes
              ? archiefstukEntity.stamtuitPeriodes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {archiefstukEntity.stamtuitPeriodes && i === archiefstukEntity.stamtuitPeriodes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Valtbinnen Indeling</dt>
          <dd>{archiefstukEntity.valtbinnenIndeling ? archiefstukEntity.valtbinnenIndeling.id : ''}</dd>
          <dt>Voor Aanvraag</dt>
          <dd>
            {archiefstukEntity.voorAanvraags
              ? archiefstukEntity.voorAanvraags.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {archiefstukEntity.voorAanvraags && i === archiefstukEntity.voorAanvraags.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/archiefstuk" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/archiefstuk/${archiefstukEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArchiefstukDetail;
