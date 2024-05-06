import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './server.reducer';

export const ServerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serverEntity = useAppSelector(state => state.server.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serverDetailsHeading">Server</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{serverEntity.id}</dd>
          <dt>
            <span id="actief">Actief</span>
          </dt>
          <dd>{serverEntity.actief ? 'true' : 'false'}</dd>
          <dt>
            <span id="ipadres">Ipadres</span>
          </dt>
          <dd>{serverEntity.ipadres}</dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{serverEntity.locatie}</dd>
          <dt>
            <span id="organisatie">Organisatie</span>
          </dt>
          <dd>{serverEntity.organisatie}</dd>
          <dt>
            <span id="serienummer">Serienummer</span>
          </dt>
          <dd>{serverEntity.serienummer}</dd>
          <dt>
            <span id="serverid">Serverid</span>
          </dt>
          <dd>{serverEntity.serverid}</dd>
          <dt>
            <span id="servertype">Servertype</span>
          </dt>
          <dd>{serverEntity.servertype}</dd>
          <dt>
            <span id="vlan">Vlan</span>
          </dt>
          <dd>{serverEntity.vlan}</dd>
          <dt>Heeftleverancier Leverancier</dt>
          <dd>{serverEntity.heeftleverancierLeverancier ? serverEntity.heeftleverancierLeverancier.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/server" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/server/${serverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServerDetail;
