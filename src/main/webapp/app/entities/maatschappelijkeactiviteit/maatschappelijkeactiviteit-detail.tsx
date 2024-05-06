import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './maatschappelijkeactiviteit.reducer';

export const MaatschappelijkeactiviteitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const maatschappelijkeactiviteitEntity = useAppSelector(state => state.maatschappelijkeactiviteit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="maatschappelijkeactiviteitDetailsHeading">Maatschappelijkeactiviteit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.id}</dd>
          <dt>
            <span id="adresbinnenland">Adresbinnenland</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.adresbinnenland}</dd>
          <dt>
            <span id="adrescorrespondentie">Adrescorrespondentie</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.adrescorrespondentie}</dd>
          <dt>
            <span id="datumaanvang">Datumaanvang</span>
          </dt>
          <dd>
            {maatschappelijkeactiviteitEntity.datumaanvang ? (
              <TextFormat value={maatschappelijkeactiviteitEntity.datumaanvang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindegeldig">Datumeindegeldig</span>
          </dt>
          <dd>
            {maatschappelijkeactiviteitEntity.datumeindegeldig ? (
              <TextFormat value={maatschappelijkeactiviteitEntity.datumeindegeldig} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumfaillisement">Datumfaillisement</span>
          </dt>
          <dd>
            {maatschappelijkeactiviteitEntity.datumfaillisement ? (
              <TextFormat value={maatschappelijkeactiviteitEntity.datumfaillisement} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="indicatieeconomischactief">Indicatieeconomischactief</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.indicatieeconomischactief}</dd>
          <dt>
            <span id="kvknummer">Kvknummer</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.kvknummer}</dd>
          <dt>
            <span id="rechtsvorm">Rechtsvorm</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.rechtsvorm}</dd>
          <dt>
            <span id="rsin">Rsin</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.rsin}</dd>
          <dt>
            <span id="statutairenaam">Statutairenaam</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.statutairenaam}</dd>
          <dt>
            <span id="telefoonnummer">Telefoonnummer</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.telefoonnummer}</dd>
          <dt>
            <span id="url">Url</span>
          </dt>
          <dd>{maatschappelijkeactiviteitEntity.url}</dd>
        </dl>
        <Button tag={Link} to="/maatschappelijkeactiviteit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/maatschappelijkeactiviteit/${maatschappelijkeactiviteitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MaatschappelijkeactiviteitDetail;
