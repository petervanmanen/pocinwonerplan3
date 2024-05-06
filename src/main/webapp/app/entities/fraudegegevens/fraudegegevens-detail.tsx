import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fraudegegevens.reducer';

export const FraudegegevensDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fraudegegevensEntity = useAppSelector(state => state.fraudegegevens.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fraudegegevensDetailsHeading">Fraudegegevens</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{fraudegegevensEntity.id}</dd>
          <dt>
            <span id="bedragfraude">Bedragfraude</span>
          </dt>
          <dd>{fraudegegevensEntity.bedragfraude}</dd>
          <dt>
            <span id="datumeindefraude">Datumeindefraude</span>
          </dt>
          <dd>{fraudegegevensEntity.datumeindefraude}</dd>
          <dt>
            <span id="datumgeconstateerd">Datumgeconstateerd</span>
          </dt>
          <dd>{fraudegegevensEntity.datumgeconstateerd}</dd>
          <dt>
            <span id="datumstartfraude">Datumstartfraude</span>
          </dt>
          <dd>{fraudegegevensEntity.datumstartfraude}</dd>
          <dt>
            <span id="verrekening">Verrekening</span>
          </dt>
          <dd>{fraudegegevensEntity.verrekening}</dd>
          <dt>
            <span id="vorderingen">Vorderingen</span>
          </dt>
          <dd>{fraudegegevensEntity.vorderingen}</dd>
          <dt>Isvansoort Fraudesoort</dt>
          <dd>{fraudegegevensEntity.isvansoortFraudesoort ? fraudegegevensEntity.isvansoortFraudesoort.id : ''}</dd>
          <dt>Heeftfraudegegevens Client</dt>
          <dd>{fraudegegevensEntity.heeftfraudegegevensClient ? fraudegegevensEntity.heeftfraudegegevensClient.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/fraudegegevens" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fraudegegevens/${fraudegegevensEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FraudegegevensDetail;
