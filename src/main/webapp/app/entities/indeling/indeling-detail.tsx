import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './indeling.reducer';

export const IndelingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const indelingEntity = useAppSelector(state => state.indeling.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="indelingDetailsHeading">Indeling</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{indelingEntity.id}</dd>
          <dt>
            <span id="indelingsoort">Indelingsoort</span>
          </dt>
          <dd>{indelingEntity.indelingsoort}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{indelingEntity.naam}</dd>
          <dt>
            <span id="nummer">Nummer</span>
          </dt>
          <dd>{indelingEntity.nummer}</dd>
          <dt>
            <span id="omschrijving">Omschrijving</span>
          </dt>
          <dd>{indelingEntity.omschrijving}</dd>
          <dt>Hoortbij Archief</dt>
          <dd>{indelingEntity.hoortbijArchief ? indelingEntity.hoortbijArchief.id : ''}</dd>
          <dt>Valtbinnen Indeling 2</dt>
          <dd>{indelingEntity.valtbinnenIndeling2 ? indelingEntity.valtbinnenIndeling2.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/indeling" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/indeling/${indelingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default IndelingDetail;
