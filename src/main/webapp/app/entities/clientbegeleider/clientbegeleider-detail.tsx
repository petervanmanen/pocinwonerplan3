import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './clientbegeleider.reducer';

export const ClientbegeleiderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientbegeleiderEntity = useAppSelector(state => state.clientbegeleider.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientbegeleiderDetailsHeading">Clientbegeleider</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{clientbegeleiderEntity.id}</dd>
          <dt>
            <span id="begeleiderscode">Begeleiderscode</span>
          </dt>
          <dd>{clientbegeleiderEntity.begeleiderscode}</dd>
          <dt>Maaktonderdeeluitvan Team</dt>
          <dd>{clientbegeleiderEntity.maaktonderdeeluitvanTeam ? clientbegeleiderEntity.maaktonderdeeluitvanTeam.id : ''}</dd>
          <dt>Begeleidt Traject</dt>
          <dd>{clientbegeleiderEntity.begeleidtTraject ? clientbegeleiderEntity.begeleidtTraject.id : ''}</dd>
          <dt>Ondersteuntclient Client</dt>
          <dd>
            {clientbegeleiderEntity.ondersteuntclientClients
              ? clientbegeleiderEntity.ondersteuntclientClients.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {clientbegeleiderEntity.ondersteuntclientClients && i === clientbegeleiderEntity.ondersteuntclientClients.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/clientbegeleider" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/clientbegeleider/${clientbegeleiderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientbegeleiderDetail;
