import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bedrijfsproces.reducer';

export const BedrijfsprocesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bedrijfsprocesEntity = useAppSelector(state => state.bedrijfsproces.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bedrijfsprocesDetailsHeading">Bedrijfsproces</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bedrijfsprocesEntity.id}</dd>
          <dt>
            <span id="afgerond">Afgerond</span>
          </dt>
          <dd>{bedrijfsprocesEntity.afgerond}</dd>
          <dt>
            <span id="datumeind">Datumeind</span>
          </dt>
          <dd>
            {bedrijfsprocesEntity.datumeind ? (
              <TextFormat value={bedrijfsprocesEntity.datumeind} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {bedrijfsprocesEntity.datumstart ? (
              <TextFormat value={bedrijfsprocesEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{bedrijfsprocesEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{bedrijfsprocesEntity.omschrijving}</dd>
          <dt>Uitgevoerdbinnen Zaak</dt>
          <dd>
            {bedrijfsprocesEntity.uitgevoerdbinnenZaaks
              ? bedrijfsprocesEntity.uitgevoerdbinnenZaaks.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {bedrijfsprocesEntity.uitgevoerdbinnenZaaks && i === bedrijfsprocesEntity.uitgevoerdbinnenZaaks.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bedrijfsproces" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bedrijfsproces/${bedrijfsprocesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BedrijfsprocesDetail;
