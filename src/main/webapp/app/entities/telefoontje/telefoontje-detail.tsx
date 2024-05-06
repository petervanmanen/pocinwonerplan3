import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './telefoontje.reducer';

export const TelefoontjeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const telefoontjeEntity = useAppSelector(state => state.telefoontje.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telefoontjeDetailsHeading">Telefoontje</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{telefoontjeEntity.id}</dd>
          <dt>
            <span id="afhandeltijdnagesprek">Afhandeltijdnagesprek</span>
          </dt>
          <dd>{telefoontjeEntity.afhandeltijdnagesprek}</dd>
          <dt>
            <span id="deltaisdnconnectie">Deltaisdnconnectie</span>
          </dt>
          <dd>{telefoontjeEntity.deltaisdnconnectie}</dd>
          <dt>
            <span id="eindtijd">Eindtijd</span>
          </dt>
          <dd>{telefoontjeEntity.eindtijd}</dd>
          <dt>
            <span id="starttijd">Starttijd</span>
          </dt>
          <dd>{telefoontjeEntity.starttijd}</dd>
          <dt>
            <span id="totaleonholdtijd">Totaleonholdtijd</span>
          </dt>
          <dd>{telefoontjeEntity.totaleonholdtijd}</dd>
          <dt>
            <span id="totalespreektijd">Totalespreektijd</span>
          </dt>
          <dd>{telefoontjeEntity.totalespreektijd}</dd>
          <dt>
            <span id="totalewachttijd">Totalewachttijd</span>
          </dt>
          <dd>{telefoontjeEntity.totalewachttijd}</dd>
          <dt>
            <span id="totlatetijdsduur">Totlatetijdsduur</span>
          </dt>
          <dd>{telefoontjeEntity.totlatetijdsduur}</dd>
          <dt>
            <span id="trackid">Trackid</span>
          </dt>
          <dd>{telefoontjeEntity.trackid}</dd>
          <dt>Heeft Telefoonstatus</dt>
          <dd>{telefoontjeEntity.heeftTelefoonstatus ? telefoontjeEntity.heeftTelefoonstatus.id : ''}</dd>
          <dt>Heeft Telefoononderwerp</dt>
          <dd>{telefoontjeEntity.heeftTelefoononderwerp ? telefoontjeEntity.heeftTelefoononderwerp.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/telefoontje" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telefoontje/${telefoontjeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TelefoontjeDetail;
