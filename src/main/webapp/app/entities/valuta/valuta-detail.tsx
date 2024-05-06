import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './valuta.reducer';

export const ValutaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const valutaEntity = useAppSelector(state => state.valuta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="valutaDetailsHeading">Valuta</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{valutaEntity.id}</dd>
          <dt>
            <span id="datumbegingeldigheid">Datumbegingeldigheid</span>
          </dt>
          <dd>{valutaEntity.datumbegingeldigheid}</dd>
          <dt>
            <span id="datumeindegeldigheid">Datumeindegeldigheid</span>
          </dt>
          <dd>{valutaEntity.datumeindegeldigheid}</dd>
          <dt>
            <span id="naam">Naam</span>
          </dt>
          <dd>{valutaEntity.naam}</dd>
          <dt>
            <span id="valutacode">Valutacode</span>
          </dt>
          <dd>{valutaEntity.valutacode}</dd>
        </dl>
        <Button tag={Link} to="/valuta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/valuta/${valutaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ValutaDetail;
