import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mutatie.reducer';

export const MutatieDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mutatieEntity = useAppSelector(state => state.mutatie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mutatieDetailsHeading">Mutatie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{mutatieEntity.id}</dd>
          <dt>
            <span id="bedrag">Bedrag</span>
          </dt>
          <dd>{mutatieEntity.bedrag}</dd>
          <dt>
            <span id="datum">Datum</span>
          </dt>
          <dd>{mutatieEntity.datum}</dd>
          <dt>Van Hoofdrekening</dt>
          <dd>{mutatieEntity.vanHoofdrekening ? mutatieEntity.vanHoofdrekening.id : ''}</dd>
          <dt>Naar Hoofdrekening</dt>
          <dd>{mutatieEntity.naarHoofdrekening ? mutatieEntity.naarHoofdrekening.id : ''}</dd>
          <dt>Heeftbetrekkingop Kostenplaats</dt>
          <dd>{mutatieEntity.heeftbetrekkingopKostenplaats ? mutatieEntity.heeftbetrekkingopKostenplaats.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/mutatie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mutatie/${mutatieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MutatieDetail;
