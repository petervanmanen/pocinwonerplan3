import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vestiging.reducer';

export const VestigingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vestigingEntity = useAppSelector(state => state.vestiging.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vestigingDetailsHeading">Vestiging</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{vestigingEntity.id}</dd>
          <dt>
            <span id="commercielevestiging">Commercielevestiging</span>
          </dt>
          <dd>{vestigingEntity.commercielevestiging}</dd>
          <dt>
            <span id="datumaanvang">Datumaanvang</span>
          </dt>
          <dd>
            {vestigingEntity.datumaanvang ? (
              <TextFormat value={vestigingEntity.datumaanvang} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {vestigingEntity.datumeinde ? (
              <TextFormat value={vestigingEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumvoortzetting">Datumvoortzetting</span>
          </dt>
          <dd>
            {vestigingEntity.datumvoortzetting ? (
              <TextFormat value={vestigingEntity.datumvoortzetting} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fulltimewerkzamemannen">Fulltimewerkzamemannen</span>
          </dt>
          <dd>{vestigingEntity.fulltimewerkzamemannen}</dd>
          <dt>
            <span id="fulltimewerkzamevrouwen">Fulltimewerkzamevrouwen</span>
          </dt>
          <dd>{vestigingEntity.fulltimewerkzamevrouwen}</dd>
          <dt>
            <span id="handelsnaam">Handelsnaam</span>
          </dt>
          <dd>{vestigingEntity.handelsnaam}</dd>
          <dt>
            <span id="parttimewerkzamemannen">Parttimewerkzamemannen</span>
          </dt>
          <dd>{vestigingEntity.parttimewerkzamemannen}</dd>
          <dt>
            <span id="parttimewerkzamevrouwen">Parttimewerkzamevrouwen</span>
          </dt>
          <dd>{vestigingEntity.parttimewerkzamevrouwen}</dd>
          <dt>
            <span id="toevoegingadres">Toevoegingadres</span>
          </dt>
          <dd>{vestigingEntity.toevoegingadres}</dd>
          <dt>
            <span id="totaalwerkzamepersonen">Totaalwerkzamepersonen</span>
          </dt>
          <dd>{vestigingEntity.totaalwerkzamepersonen}</dd>
          <dt>
            <span id="verkortenaam">Verkortenaam</span>
          </dt>
          <dd>{vestigingEntity.verkortenaam}</dd>
          <dt>
            <span id="vestigingsnummer">Vestigingsnummer</span>
          </dt>
          <dd>{vestigingEntity.vestigingsnummer}</dd>
          <dt>Heeft Werkgelegenheid</dt>
          <dd>{vestigingEntity.heeftWerkgelegenheid ? vestigingEntity.heeftWerkgelegenheid.id : ''}</dd>
          <dt>Heeftalslocatieadres Nummeraanduiding</dt>
          <dd>{vestigingEntity.heeftalslocatieadresNummeraanduiding ? vestigingEntity.heeftalslocatieadresNummeraanduiding.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/vestiging" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vestiging/${vestigingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VestigingDetail;
