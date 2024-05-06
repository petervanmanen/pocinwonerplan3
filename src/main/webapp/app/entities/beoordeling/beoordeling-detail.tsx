import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './beoordeling.reducer';

export const BeoordelingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const beoordelingEntity = useAppSelector(state => state.beoordeling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="beoordelingDetailsHeading">Beoordeling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{beoordelingEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>
            {beoordelingEntity.datum ? <TextFormat value={beoordelingEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{beoordelingEntity.omschrijving}</dd>
          <dt>
            <span id="oordeel">Oordeel</span>
          </dt>
          <dd>{beoordelingEntity.oordeel}</dd>
          <dt>Beoordeeltdoor Werknemer</dt>
          <dd>{beoordelingEntity.beoordeeltdoorWerknemer ? beoordelingEntity.beoordeeltdoorWerknemer.id : ''}</dd>
          <dt>Beoordelingvan Werknemer</dt>
          <dd>{beoordelingEntity.beoordelingvanWerknemer ? beoordelingEntity.beoordelingvanWerknemer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/beoordeling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/beoordeling/${beoordelingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BeoordelingDetail;
