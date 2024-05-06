import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './aantekening.reducer';

export const AantekeningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const aantekeningEntity = useAppSelector(state => state.aantekening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="aantekeningDetailsHeading">Aantekening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{aantekeningEntity.id}</dd>
          <dt>
            <span id="aard">Aard</span>
          </dt>
          <dd>{aantekeningEntity.aard}</dd>
          <dt>
            <span id="begrenzing">Begrenzing</span>
          </dt>
          <dd>{aantekeningEntity.begrenzing}</dd>
          <dt>
            <span id="betreftgedeeltevanperceel">Betreftgedeeltevanperceel</span>
          </dt>
          <dd>{aantekeningEntity.betreftgedeeltevanperceel ? 'true' : 'false'}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {aantekeningEntity.datumeinde ? (
              <TextFormat value={aantekeningEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinderecht">Datumeinderecht</span>
          </dt>
          <dd>
            {aantekeningEntity.datumeinderecht ? (
              <TextFormat value={aantekeningEntity.datumeinderecht} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{aantekeningEntity.identificatie}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{aantekeningEntity.omschrijving}</dd>
          <dt>Empty Tenaamstelling</dt>
          <dd>{aantekeningEntity.emptyTenaamstelling ? aantekeningEntity.emptyTenaamstelling.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/aantekening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/aantekening/${aantekeningEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AantekeningDetail;
