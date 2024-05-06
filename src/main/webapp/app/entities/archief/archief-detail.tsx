import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './archief.reducer';

export const ArchiefDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const archiefEntity = useAppSelector(state => state.archief.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="archiefDetailsHeading">Archief</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{archiefEntity.id}</dd>
          <dt>
            <span id="archiefnummer">Archiefnummer</span>
          </dt>
          <dd>{archiefEntity.archiefnummer}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{archiefEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{archiefEntity.omschrijving}</dd>
          <dt>
            <span id="openbaarheidsbeperking">Openbaarheidsbeperking</span>
          </dt>
          <dd>{archiefEntity.openbaarheidsbeperking}</dd>
          <dt>Heeft Rechthebbende</dt>
          <dd>{archiefEntity.heeftRechthebbende ? archiefEntity.heeftRechthebbende.id : ''}</dd>
          <dt>Valtbinnen Archiefcategorie</dt>
          <dd>
            {archiefEntity.valtbinnenArchiefcategories
              ? archiefEntity.valtbinnenArchiefcategories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {archiefEntity.valtbinnenArchiefcategories && i === archiefEntity.valtbinnenArchiefcategories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Stamtuit Periode</dt>
          <dd>
            {archiefEntity.stamtuitPeriodes
              ? archiefEntity.stamtuitPeriodes.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {archiefEntity.stamtuitPeriodes && i === archiefEntity.stamtuitPeriodes.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/archief" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/archief/${archiefEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArchiefDetail;
