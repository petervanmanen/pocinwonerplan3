import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mailing.reducer';

export const MailingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mailingEntity = useAppSelector(state => state.mailing.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mailingDetailsHeading">Mailing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{mailingEntity.id}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{mailingEntity.datum ? <TextFormat value={mailingEntity.datum} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{mailingEntity.naam}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{mailingEntity.omschrijving}</dd>
          <dt>Versturenaan Museumrelatie</dt>
          <dd>
            {mailingEntity.versturenaanMuseumrelaties
              ? mailingEntity.versturenaanMuseumrelaties.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {mailingEntity.versturenaanMuseumrelaties && i === mailingEntity.versturenaanMuseumrelaties.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/mailing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mailing/${mailingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MailingDetail;
