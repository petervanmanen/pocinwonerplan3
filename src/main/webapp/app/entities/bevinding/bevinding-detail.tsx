import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bevinding.reducer';

export const BevindingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bevindingEntity = useAppSelector(state => state.bevinding.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bevindingDetailsHeading">Bevinding</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{bevindingEntity.id}</dd>
          <dt>
            <span id="aangemaaktdoor">Aangemaaktdoor</span>
          </dt>
          <dd>{bevindingEntity.aangemaaktdoor}</dd>
          <dt>
            <span id="activiteit">Activiteit</span>
          </dt>
          <dd>{bevindingEntity.activiteit}</dd>
          <dt>
            <span id="controleelement">Controleelement</span>
          </dt>
          <dd>{bevindingEntity.controleelement}</dd>
          <dt>
            <span id="controleniveau">Controleniveau</span>
          </dt>
          <dd>{bevindingEntity.controleniveau}</dd>
          <dt>
            <span id="datumaanmaak">Datumaanmaak</span>
          </dt>
          <dd>
            {bevindingEntity.datumaanmaak ? (
              <TextFormat value={bevindingEntity.datumaanmaak} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="datummutatie">Datummutatie</span>
          </dt>
          <dd>
            {bevindingEntity.datummutatie ? (
              <TextFormat value={bevindingEntity.datummutatie} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="diepte">Diepte</span>
          </dt>
          <dd>{bevindingEntity.diepte}</dd>
          <dt>
            <span id="fase">Fase</span>
          </dt>
          <dd>{bevindingEntity.fase}</dd>
          <dt>
            <span id="gemuteerddoor">Gemuteerddoor</span>
          </dt>
          <dd>{bevindingEntity.gemuteerddoor}</dd>
          <dt>
            <span id="resultaat">Resultaat</span>
          </dt>
          <dd>{bevindingEntity.resultaat}</dd>
          <dt>
            <span id="risico">Risico</span>
          </dt>
          <dd>{bevindingEntity.risico}</dd>
        </dl>
        <Button tag={Link} to="/bevinding" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bevinding/${bevindingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default BevindingDetail;
