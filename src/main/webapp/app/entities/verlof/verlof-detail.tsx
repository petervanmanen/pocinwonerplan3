import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './verlof.reducer';

export const VerlofDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const verlofEntity = useAppSelector(state => state.verlof.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="verlofDetailsHeading">Verlof</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{verlofEntity.id}</dd>
          <dt>
            <span id="datumaanvraag">Datumaanvraag</span>
          </dt>
          <dd>
            {verlofEntity.datumaanvraag ? (
              <TextFormat value={verlofEntity.datumaanvraag} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumtijdeinde">Datumtijdeinde</span>
          </dt>
          <dd>{verlofEntity.datumtijdeinde}</dd>
          <dt>
            <span id="datumtijdstart">Datumtijdstart</span>
          </dt>
          <dd>{verlofEntity.datumtijdstart}</dd>
          <dt>
            <span id="datumtoekenning">Datumtoekenning</span>
          </dt>
          <dd>
            {verlofEntity.datumtoekenning ? (
              <TextFormat value={verlofEntity.datumtoekenning} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="goedgekeurd">Goedgekeurd</span>
          </dt>
          <dd>{verlofEntity.goedgekeurd ? 'true' : 'false'}</dd>
          <dt>Soortverlof Verlofsoort</dt>
          <dd>{verlofEntity.soortverlofVerlofsoort ? verlofEntity.soortverlofVerlofsoort.id : ''}</dd>
          <dt>Heeftverlof Werknemer</dt>
          <dd>{verlofEntity.heeftverlofWerknemer ? verlofEntity.heeftverlofWerknemer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/verlof" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/verlof/${verlofEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default VerlofDetail;
