import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './betaling.reducer';

export const BetalingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const betalingEntity = useAppSelector(state => state.betaling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="betalingDetailsHeading">Betaling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{betalingEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{betalingEntity.bedrag}</dd>
          <dt>
            <span id="datumtijd">Datumtijd</span>
          </dt>
          <dd>{betalingEntity.datumtijd}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{betalingEntity.omschrijving}</dd>
          <dt>
            <span id="valuta">Valuta</span>
          </dt>
          <dd>{betalingEntity.valuta}</dd>
          <dt>Komtvoorop Bankafschriftregel</dt>
          <dd>{betalingEntity.komtvooropBankafschriftregel ? betalingEntity.komtvooropBankafschriftregel.id : ''}</dd>
          <dt>Van Bankrekening</dt>
          <dd>{betalingEntity.vanBankrekening ? betalingEntity.vanBankrekening.id : ''}</dd>
          <dt>Naar Bankrekening</dt>
          <dd>{betalingEntity.naarBankrekening ? betalingEntity.naarBankrekening.id : ''}</dd>
          <dt>Heeftbetaling Zaak</dt>
          <dd>{betalingEntity.heeftbetalingZaak ? betalingEntity.heeftbetalingZaak.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/betaling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/betaling/${betalingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BetalingDetail;
