import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './winkelvoorraaditem.reducer';

export const WinkelvoorraaditemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const winkelvoorraaditemEntity = useAppSelector(state => state.winkelvoorraaditem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="winkelvoorraaditemDetailsHeading">Winkelvoorraaditem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{winkelvoorraaditemEntity.id}</dd>
          <dt>
            <span id="aantal">Aantal</span>
          </dt>
          <dd>{winkelvoorraaditemEntity.aantal}</dd>
          <dt>
            <span id="aantalinbestelling">Aantalinbestelling</span>
          </dt>
          <dd>{winkelvoorraaditemEntity.aantalinbestelling}</dd>
          <dt>
            <span id="datumleveringbestelling">Datumleveringbestelling</span>
          </dt>
          <dd>
            {winkelvoorraaditemEntity.datumleveringbestelling ? (
              <TextFormat value={winkelvoorraaditemEntity.datumleveringbestelling} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="locatie">Locatie</span>
          </dt>
          <dd>{winkelvoorraaditemEntity.locatie}</dd>
          <dt>Betreft Product</dt>
          <dd>{winkelvoorraaditemEntity.betreftProduct ? winkelvoorraaditemEntity.betreftProduct.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/winkelvoorraaditem" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/winkelvoorraaditem/${winkelvoorraaditemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default WinkelvoorraaditemDetail;
