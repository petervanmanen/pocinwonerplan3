import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './participatiedossier.reducer';

export const ParticipatiedossierDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const participatiedossierEntity = useAppSelector(state => state.participatiedossier.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="participatiedossierDetailsHeading">Participatiedossier</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{participatiedossierEntity.id}</dd>
          <dt>
            <span id="arbeidsvermogen">Arbeidsvermogen</span>
          </dt>
          <dd>{participatiedossierEntity.arbeidsvermogen}</dd>
          <dt>
            <span id="begeleiderscode">Begeleiderscode</span>
          </dt>
          <dd>{participatiedossierEntity.begeleiderscode}</dd>
          <dt>
            <span id="beschutwerk">Beschutwerk</span>
          </dt>
          <dd>{participatiedossierEntity.beschutwerk}</dd>
          <dt>
            <span id="clientbegeleiderid">Clientbegeleiderid</span>
          </dt>
          <dd>{participatiedossierEntity.clientbegeleiderid}</dd>
          <dt>
            <span id="datumeindebegeleiding">Datumeindebegeleiding</span>
          </dt>
          <dd>{participatiedossierEntity.datumeindebegeleiding}</dd>
          <dt>
            <span id="datumstartbegeleiding">Datumstartbegeleiding</span>
          </dt>
          <dd>{participatiedossierEntity.datumstartbegeleiding}</dd>
          <dt>
            <span id="indicatiedoelgroepregister">Indicatiedoelgroepregister</span>
          </dt>
          <dd>{participatiedossierEntity.indicatiedoelgroepregister}</dd>
          <dt>Empty Clientbegeleider</dt>
          <dd>{participatiedossierEntity.emptyClientbegeleider ? participatiedossierEntity.emptyClientbegeleider.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/participatiedossier" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/participatiedossier/${participatiedossierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParticipatiedossierDetail;
