import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mjopitem.reducer';

export const MjopitemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mjopitemEntity = useAppSelector(state => state.mjopitem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mjopitemDetailsHeading">Mjopitem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{mjopitemEntity.id}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{mjopitemEntity.code}</dd>
          <dt>
            <span id="datumeinde">Datumeinde</span>
          </dt>
          <dd>
            {mjopitemEntity.datumeinde ? <TextFormat value={mjopitemEntity.datumeinde} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="datumopzeggingaanbieder">Datumopzeggingaanbieder</span>
          </dt>
          <dd>
            {mjopitemEntity.datumopzeggingaanbieder ? (
              <TextFormat value={mjopitemEntity.datumopzeggingaanbieder} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumopzeggingontvanger">Datumopzeggingontvanger</span>
          </dt>
          <dd>
            {mjopitemEntity.datumopzeggingontvanger ? (
              <TextFormat value={mjopitemEntity.datumopzeggingontvanger} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {mjopitemEntity.datumstart ? <TextFormat value={mjopitemEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="kosten">Kosten</span>
          </dt>
          <dd>{mjopitemEntity.kosten}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{mjopitemEntity.omschrijving}</dd>
          <dt>
            <span id="opzegtermijnaanbieder">Opzegtermijnaanbieder</span>
          </dt>
          <dd>{mjopitemEntity.opzegtermijnaanbieder}</dd>
          <dt>
            <span id="opzegtermijnontvanger">Opzegtermijnontvanger</span>
          </dt>
          <dd>{mjopitemEntity.opzegtermijnontvanger}</dd>
        </dl>
        <Button tag={Link} to="/mjopitem" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mjopitem/${mjopitemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MjopitemDetail;
