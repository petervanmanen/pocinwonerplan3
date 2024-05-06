import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './prijzenboekitem.reducer';

export const PrijzenboekitemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const prijzenboekitemEntity = useAppSelector(state => state.prijzenboekitem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="prijzenboekitemDetailsHeading">Prijzenboekitem</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{prijzenboekitemEntity.id}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>
            {prijzenboekitemEntity.datumeindegeldigheid ? (
              <TextFormat value={prijzenboekitemEntity.datumeindegeldigheid} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datumstart">Datumstart</span>
          </dt>
          <dd>
            {prijzenboekitemEntity.datumstart ? (
              <TextFormat value={prijzenboekitemEntity.datumstart} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="prijs">Prijs</span>
          </dt>
          <dd>{prijzenboekitemEntity.prijs}</dd>
          <dt>
            <span id="verrichting">Verrichting</span>
          </dt>
          <dd>{prijzenboekitemEntity.verrichting}</dd>
        </dl>
        <Button tag={Link} to="/prijzenboekitem" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/prijzenboekitem/${prijzenboekitemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PrijzenboekitemDetail;
