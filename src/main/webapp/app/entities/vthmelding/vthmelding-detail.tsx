import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vthmelding.reducer';

export const VthmeldingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vthmeldingEntity = useAppSelector(state => state.vthmelding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vthmeldingDetailsHeading">Vthmelding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vthmeldingEntity.id}</dd>
          <dt>
            <span id="activiteit">Activiteit</span>
          </dt>
          <dd>{vthmeldingEntity.activiteit}</dd>
          <dt>
            <span id="beoordeling">Beoordeling</span>
          </dt>
          <dd>{vthmeldingEntity.beoordeling}</dd>
          <dt>
            <span id="datumseponering">Datumseponering</span>
          </dt>
          <dd>
            {vthmeldingEntity.datumseponering ? (
              <TextFormat value={vthmeldingEntity.datumseponering} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtijdtot">Datumtijdtot</span>
          </dt>
          <dd>{vthmeldingEntity.datumtijdtot}</dd>
          <dt>
            <span id="geseponeerd">Geseponeerd</span>
          </dt>
          <dd>{vthmeldingEntity.geseponeerd}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{vthmeldingEntity.locatie}</dd>
          <dt>
            <span id="organisatieonderdeel">Organisatieonderdeel</span>
          </dt>
          <dd>{vthmeldingEntity.organisatieonderdeel}</dd>
          <dt>
            <span id="overtredingscode">Overtredingscode</span>
          </dt>
          <dd>{vthmeldingEntity.overtredingscode}</dd>
          <dt>
            <span id="overtredingsgroep">Overtredingsgroep</span>
          </dt>
          <dd>{vthmeldingEntity.overtredingsgroep}</dd>
          <dt>
            <span id="referentienummer">Referentienummer</span>
          </dt>
          <dd>{vthmeldingEntity.referentienummer}</dd>
          <dt>
            <span id="resultaat">Resultaat</span>
          </dt>
          <dd>{vthmeldingEntity.resultaat}</dd>
          <dt>
            <span id="soortvthmelding">Soortvthmelding</span>
          </dt>
          <dd>{vthmeldingEntity.soortvthmelding}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{vthmeldingEntity.status}</dd>
          <dt>
            <span id="straatnaam">Straatnaam</span>
          </dt>
          <dd>{vthmeldingEntity.straatnaam}</dd>
          <dt>
            <span id="taaktype">Taaktype</span>
          </dt>
          <dd>{vthmeldingEntity.taaktype}</dd>
          <dt>
            <span id="zaaknummer">Zaaknummer</span>
          </dt>
          <dd>{vthmeldingEntity.zaaknummer}</dd>
        </dl>
        <Button tag={Link} to="/vthmelding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vthmelding/${vthmeldingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VthmeldingDetail;
