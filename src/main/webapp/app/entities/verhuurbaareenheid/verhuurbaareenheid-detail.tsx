import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verhuurbaareenheid.reducer';

export const VerhuurbaareenheidDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verhuurbaareenheidEntity = useAppSelector(state => state.verhuurbaareenheid.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verhuurbaareenheidDetailsHeading">Verhuurbaareenheid</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.id}</dd>
          <dt>
            <span id="adres">Adres</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.adres}</dd>
          <dt>
            <span id="afmeting">Afmeting</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.afmeting}</dd>
          <dt>
            <span id="bezetting">Bezetting</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.bezetting}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {verhuurbaareenheidEntity.datumeinde ? (
              <TextFormat value={verhuurbaareenheidEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {verhuurbaareenheidEntity.datumstart ? (
              <TextFormat value={verhuurbaareenheidEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumwerkelijkbegin">Datumwerkelijkbegin</span>
          </dt>
          <dd>
            {verhuurbaareenheidEntity.datumwerkelijkbegin ? (
              <TextFormat value={verhuurbaareenheidEntity.datumwerkelijkbegin} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumwerkelijkeinde">Datumwerkelijkeinde</span>
          </dt>
          <dd>
            {verhuurbaareenheidEntity.datumwerkelijkeinde ? (
              <TextFormat value={verhuurbaareenheidEntity.datumwerkelijkeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="huurprijs">Huurprijs</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.huurprijs}</dd>
          <dt>
            <span id="identificatie">Identificatie</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.identificatie}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.naam}</dd>
          <dt>
            <span id="nettoomtrek">Nettoomtrek</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.nettoomtrek}</dd>
          <dt>
            <span id="nettooppervlak">Nettooppervlak</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.nettooppervlak}</dd>
          <dt>
            <span id="opmerkingen">Opmerkingen</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.opmerkingen}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{verhuurbaareenheidEntity.type}</dd>
        </dl>
        <Button tag={Link} to="/verhuurbaareenheid" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verhuurbaareenheid/${verhuurbaareenheidEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerhuurbaareenheidDetail;
