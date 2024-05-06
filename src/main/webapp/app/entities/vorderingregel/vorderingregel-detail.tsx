import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vorderingregel.reducer';

export const VorderingregelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vorderingregelEntity = useAppSelector(state => state.vorderingregel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vorderingregelDetailsHeading">Vorderingregel</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vorderingregelEntity.id}</dd>
          <dt>
            <span id="aangemaaktdoor">Aangemaaktdoor</span>
          </dt>
          <dd>{vorderingregelEntity.aangemaaktdoor}</dd>
          <dt>
            <span id="aanmaakdatum">Aanmaakdatum</span>
          </dt>
          <dd>
            {vorderingregelEntity.aanmaakdatum ? (
              <TextFormat value={vorderingregelEntity.aanmaakdatum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bedragexclbtw">Bedragexclbtw</span>
          </dt>
          <dd>{vorderingregelEntity.bedragexclbtw}</dd>
          <dt>
            <span id="bedraginclbtw">Bedraginclbtw</span>
          </dt>
          <dd>{vorderingregelEntity.bedraginclbtw}</dd>
          <dt>
            <span id="btwcategorie">Btwcategorie</span>
          </dt>
          <dd>{vorderingregelEntity.btwcategorie}</dd>
          <dt>
            <span id="gemuteerddoor">Gemuteerddoor</span>
          </dt>
          <dd>{vorderingregelEntity.gemuteerddoor}</dd>
          <dt>
            <span id="mutatiedatum">Mutatiedatum</span>
          </dt>
          <dd>
            {vorderingregelEntity.mutatiedatum ? (
              <TextFormat value={vorderingregelEntity.mutatiedatum} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{vorderingregelEntity.omschrijving}</dd>
          <dt>
            <span id="periodiek">Periodiek</span>
          </dt>
          <dd>{vorderingregelEntity.periodiek}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{vorderingregelEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/vorderingregel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vorderingregel/${vorderingregelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VorderingregelDetail;
