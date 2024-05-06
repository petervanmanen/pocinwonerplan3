import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './client.reducer';

export const ClientDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const clientEntity = useAppSelector(state => state.client.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clientDetailsHeading">Client</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{clientEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{clientEntity.code}</dd>
          <dt>
            <span id="gezagsdragergekend">Gezagsdragergekend</span>
          </dt>
          <dd>{clientEntity.gezagsdragergekend ? 'true' : 'false'}</dd>
          <dt>
            <span id="juridischestatus">Juridischestatus</span>
          </dt>
          <dd>{clientEntity.juridischestatus}</dd>
          <dt>
            <span id="wettelijkevertegenwoordiging">Wettelijkevertegenwoordiging</span>
          </dt>
          <dd>{clientEntity.wettelijkevertegenwoordiging}</dd>
          <dt>Heeft Participatiedossier</dt>
          <dd>{clientEntity.heeftParticipatiedossier ? clientEntity.heeftParticipatiedossier.id : ''}</dd>
          <dt>Heeftvoorziening Inkomensvoorziening</dt>
          <dd>{clientEntity.heeftvoorzieningInkomensvoorziening ? clientEntity.heeftvoorzieningInkomensvoorziening.id : ''}</dd>
          <dt>Valtbinnendoelgroep Doelgroep</dt>
          <dd>{clientEntity.valtbinnendoelgroepDoelgroep ? clientEntity.valtbinnendoelgroepDoelgroep.id : ''}</dd>
          <dt>Heeftrelatie Relatie</dt>
          <dd>
            {clientEntity.heeftrelatieRelaties
              ? clientEntity.heeftrelatieRelaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {clientEntity.heeftrelatieRelaties && i === clientEntity.heeftrelatieRelaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Voorzieningbijstandspartij Inkomensvoorziening</dt>
          <dd>
            {clientEntity.voorzieningbijstandspartijInkomensvoorzienings
              ? clientEntity.voorzieningbijstandspartijInkomensvoorzienings.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {clientEntity.voorzieningbijstandspartijInkomensvoorzienings &&
                    i === clientEntity.voorzieningbijstandspartijInkomensvoorzienings.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Maaktonderdeeluitvan Huishouden</dt>
          <dd>
            {clientEntity.maaktonderdeeluitvanHuishoudens
              ? clientEntity.maaktonderdeeluitvanHuishoudens.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {clientEntity.maaktonderdeeluitvanHuishoudens && i === clientEntity.maaktonderdeeluitvanHuishoudens.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Heefttaalniveau Taalniveau</dt>
          <dd>
            {clientEntity.heefttaalniveauTaalniveaus
              ? clientEntity.heefttaalniveauTaalniveaus.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {clientEntity.heefttaalniveauTaalniveaus && i === clientEntity.heefttaalniveauTaalniveaus.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>Ondersteuntclient Clientbegeleider</dt>
          <dd>
            {clientEntity.ondersteuntclientClientbegeleiders
              ? clientEntity.ondersteuntclientClientbegeleiders.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {clientEntity.ondersteuntclientClientbegeleiders && i === clientEntity.ondersteuntclientClientbegeleiders.length - 1
                      ? ''
                      : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/client" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/client/${clientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClientDetail;
