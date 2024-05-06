import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kadastraleonroerendezaakaantekening.reducer';

export const KadastraleonroerendezaakaantekeningDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kadastraleonroerendezaakaantekeningEntity = useAppSelector(state => state.kadastraleonroerendezaakaantekening.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kadastraleonroerendezaakaantekeningDetailsHeading">Kadastraleonroerendezaakaantekening</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{kadastraleonroerendezaakaantekeningEntity.id}</dd>
          <dt>
            <span id="aardaantekeningkadastraalobject">Aardaantekeningkadastraalobject</span>
          </dt>
          <dd>{kadastraleonroerendezaakaantekeningEntity.aardaantekeningkadastraalobject}</dd>
          <dt>
            <span id="beschrijvingaantekeningkadastraalobject">Beschrijvingaantekeningkadastraalobject</span>
          </dt>
          <dd>{kadastraleonroerendezaakaantekeningEntity.beschrijvingaantekeningkadastraalobject}</dd>
          <dt>
            <span id="datumbeginaantekeningkadastraalobject">Datumbeginaantekeningkadastraalobject</span>
          </dt>
          <dd>
            {kadastraleonroerendezaakaantekeningEntity.datumbeginaantekeningkadastraalobject ? (
              <TextFormat
                value={kadastraleonroerendezaakaantekeningEntity.datumbeginaantekeningkadastraalobject}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="datumeindeaantekeningkadastraalobject">Datumeindeaantekeningkadastraalobject</span>
          </dt>
          <dd>
            {kadastraleonroerendezaakaantekeningEntity.datumeindeaantekeningkadastraalobject ? (
              <TextFormat
                value={kadastraleonroerendezaakaantekeningEntity.datumeindeaantekeningkadastraalobject}
                type="date"
                format={APP_LOCAL_DATE_FORMAT}
              />
            ) : null}
          </dd>
          <dt>
            <span id="kadasteridentificatieaantekening">Kadasteridentificatieaantekening</span>
          </dt>
          <dd>{kadastraleonroerendezaakaantekeningEntity.kadasteridentificatieaantekening}</dd>
        </dl>
        <Button tag={Link} to="/kadastraleonroerendezaakaantekening" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/kadastraleonroerendezaakaantekening/${kadastraleonroerendezaakaantekeningEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default KadastraleonroerendezaakaantekeningDetail;
