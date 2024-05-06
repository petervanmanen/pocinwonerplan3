import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './moormelding.reducer';

export const MoormeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const moormeldingEntity = useAppSelector(state => state.moormelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="moormeldingDetailsHeading">Moormelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{moormeldingEntity.id}</dd>
          <dt>
            <span id="adresaanduiding">Adresaanduiding</span>
          </dt>
          <dd>{moormeldingEntity.adresaanduiding}</dd>
          <dt>
            <span id="datumaanmelding">Datumaanmelding</span>
          </dt>
          <dd>{moormeldingEntity.datumaanmelding}</dd>
          <dt>
            <span id="datumgoedkeuring">Datumgoedkeuring</span>
          </dt>
          <dd>{moormeldingEntity.datumgoedkeuring}</dd>
          <dt>
            <span id="eindtijd">Eindtijd</span>
          </dt>
          <dd>{moormeldingEntity.eindtijd}</dd>
          <dt>
            <span id="goedgekeurd">Goedgekeurd</span>
          </dt>
          <dd>{moormeldingEntity.goedgekeurd ? 'true' : 'false'}</dd>
          <dt>
            <span id="herstelwerkzaamhedenvereist">Herstelwerkzaamhedenvereist</span>
          </dt>
          <dd>{moormeldingEntity.herstelwerkzaamhedenvereist ? 'true' : 'false'}</dd>
          <dt>
            <span id="omschrijvingherstelwerkzaamheden">Omschrijvingherstelwerkzaamheden</span>
          </dt>
          <dd>{moormeldingEntity.omschrijvingherstelwerkzaamheden}</dd>
          <dt>
            <span id="publiceren">Publiceren</span>
          </dt>
          <dd>{moormeldingEntity.publiceren ? 'true' : 'false'}</dd>
          <dt>
            <span id="starttijd">Starttijd</span>
          </dt>
          <dd>{moormeldingEntity.starttijd}</dd>
          <dt>
            <span id="wegbeheerder">Wegbeheerder</span>
          </dt>
          <dd>{moormeldingEntity.wegbeheerder}</dd>
        </dl>
        <Button tag={Link} to="/moormelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/moormelding/${moormeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MoormeldingDetail;
